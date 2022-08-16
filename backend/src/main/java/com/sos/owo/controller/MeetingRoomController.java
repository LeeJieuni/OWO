package com.sos.owo.controller;

import com.sos.owo.domain.MeetingRoom;
import com.sos.owo.domain.Mode;
import com.sos.owo.domain.RoomStatus;
import com.sos.owo.dto.*;
import com.sos.owo.service.MeetingRoomService;
import com.sos.owo.service.MemberService;
import io.openvidu.java.client.OpenVidu;
import io.openvidu.java.client.OpenViduHttpException;
import io.openvidu.java.client.OpenViduJavaClientException;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@RestController
@RequiredArgsConstructor
public class MeetingRoomController {

    private final int LIMIT = 6;
    private final MeetingRoomService roomService;
    private final MemberService memberService;

    // 오픈 비두 객체
    private OpenVidu openVidu;

    // 방(세션) 관리
    private Map<Integer, Integer> roomSession = new ConcurrentHashMap<>();

    // 오픈비두 서버 관련 변수
    private String OPENVIDU_URL;
    private String OPENVIDU_SECRET;


    // MeetingRoomController 접근시에 오픈비두 서버 관련 변수를 얻음
    @Autowired
    public MeetingRoomController(MeetingRoomService roomService, MemberService memberService, @Value("${openvidu.secret}") String secret, @Value("${openvidu.url}") String openviduUrl){
        this.roomService = roomService;
        this.memberService = memberService;
        this.OPENVIDU_SECRET = secret;
        this.OPENVIDU_URL = openviduUrl;
        this.openVidu = new OpenVidu(OPENVIDU_URL, OPENVIDU_SECRET);
    }

    @PostMapping("/api/user/room")
    @ApiOperation(value="운동방을 만드는 API", notes = "방만들기를 통해 화상방에 대한 세션과 토큰을 생성한 후에 토큰, 방에 대한 정보를 반환")
    public ResponseEntity<?> makeMeetingRoom(@RequestBody MeetingRoomMakeRequestDto requestDto) throws OpenViduJavaClientException, OpenViduHttpException {
        Message message = new Message();
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        // 방을 DB에 저장
        int roomId = roomService.createRoom(requestDto);
        // 방 (세션) 관리를 map에 저장
        this.roomSession.put(roomId, 1);
        message.setMessage("운동방 생성 성공");
        message.setStatus(StatusEnum.OK);
        message.setData(new MeetingRoomResponseDto(roomId));

        System.out.println(roomSession);
        return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }

    @GetMapping("/room/{mode}")
    @ApiImplicitParam(name = "mode",value = "방 모드(FREE, STREAMING, GAME)",paramType = "path")
    @ApiOperation(value="활성화된 모든 운동방 불러오는 API", notes = "특정 방모드(FREE/STREAMING/GAME)에 맞춰 활성화된 운동방의 정보를 모두 반환")
    public ResponseEntity<?> getMeetingRoom(@PathVariable Mode mode) throws OpenViduJavaClientException, OpenViduHttpException {
        Message message = new Message();
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        // 해당 특정 모드의 활성화된 운동방 리스트 불러오기
        List<MeetingRoom> meetingRoomList = roomService.getMeetingRoomList(mode);
        if(meetingRoomList == null) {
            message.setMessage("현재 활성화된 운동방이 존재하지 않습니다.");
            return new ResponseEntity<>(message, headers, HttpStatus.OK);
        } else {
            message.setMessage("운동방 불러오기 성공");
            List<MeetingListRoomResponse> meetingRooomDtoList = new ArrayList<>();

            for(MeetingRoom meetingRoom:meetingRoomList){
                if(!roomSession.containsKey(meetingRoom.getId())) continue;
                if(meetingRoom.getStatus() != RoomStatus.WAIT) continue;
                meetingRooomDtoList.add(MeetingListRoomResponse.builder()
                        .roomId(meetingRoom.getId())
                        .memberId(meetingRoom.getManager())
                        .manger_point(memberService.getMemberPoint(meetingRoom.getManager()))
                        .person(this.roomSession.get(meetingRoom.getId()))
                        .secret(meetingRoom.isSecret())
                        .password(meetingRoom.getPassword())
                        .mode(meetingRoom.getMode())
                        .roomName(meetingRoom.getName())
                        .type(meetingRoom.getType())
                        .link(meetingRoom.getLink()).build());
            }

            if(meetingRooomDtoList.size() == 0){
                message.setMessage("현재 활성화된 운동방이 존재하지 않습니다.");
                return new ResponseEntity<>(message, headers, HttpStatus.OK);
            }
            message.setData(meetingRooomDtoList);
            message.setStatus(StatusEnum.OK);
            return new ResponseEntity<>(message, headers, HttpStatus.OK);
        }
    }

    @PostMapping("/api/user/room/enter")
    @ApiOperation(value="방 입장 처리에 대한 API", notes = "특정 방번호(roomId)를 통해 방 입장에 대한 요청 처리를 수행합니다.")
    public ResponseEntity<?> enterMeetingRoom(@RequestBody EnterRoomDto enterRoomDto) throws OpenViduJavaClientException, OpenViduHttpException {
        Message message = new Message();
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        if(!this.roomSession.containsKey(enterRoomDto.getRoomId()) || this.roomSession.get(enterRoomDto.getRoomId()) == 0){
            message.setMessage("해당 운동방이 존재하지 않습니다.");
            return new ResponseEntity<>(message, headers, HttpStatus.BAD_REQUEST);
        }

        if(!roomService.checkWAIT(enterRoomDto.getRoomId())){
            message.setMessage("입장 가능한 방이 아닙니다.");
            return new ResponseEntity<>(message, headers, HttpStatus.BAD_REQUEST);
        }

        if(!roomService.checkSecret(enterRoomDto.getRoomId(), enterRoomDto.getPassword())){
            message.setMessage("입장 비밀번호가 틀렸습니다.");
            return new ResponseEntity<>(message, headers, HttpStatus.BAD_REQUEST);
        }

        if(this.roomSession.get(enterRoomDto.getRoomId()) >= LIMIT){
            message.setMessage("해당 운동방의 인원이 꽉 찼습니다.");
            return new ResponseEntity<>(message, headers, HttpStatus.BAD_REQUEST);
        } else {
            message.setMessage("운동방 입장 성공");
            this.roomSession.put(enterRoomDto.getRoomId(), this.roomSession.get(enterRoomDto.getRoomId())+1);
            message.setData(new MeetingRoomResponseDto(enterRoomDto.getRoomId()));
            message.setStatus(StatusEnum.OK);
            return new ResponseEntity<>(message, headers, HttpStatus.OK);
        }
    }

    @PutMapping("/api/user/room/exit/{roomId}")
    @ApiOperation(value="방 퇴장 처리에 대한 API", notes = "특정 방번호(roomId)를 통해 방 퇴장에 대한 요청 처리를 수행합니다.")
    @ApiImplicitParam(name = "roomId",value = "방의 번호",paramType = "path")
    public ResponseEntity<?> exit(@PathVariable int roomId) throws OpenViduJavaClientException, OpenViduHttpException {
        Message message = new Message();
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        if(!this.roomSession.containsKey(roomId) || this.roomSession.get(roomId) == 0){
            message.setMessage("해당 운동방이 존재하지 않습니다.");
            return new ResponseEntity<>(message, headers, HttpStatus.BAD_REQUEST);
        }

        if(this.roomSession.get(roomId) >= 2){
            message.setMessage("운동방 퇴장 성공");
            this.roomSession.put(roomId, this.roomSession.get(roomId)-1);
            return new ResponseEntity<>(message, headers, HttpStatus.OK);
        } else {
            message.setMessage("운동방 퇴장 성공 및 방 사라짐");
            this.roomSession.remove(roomId);
            roomService.updateMeetingRoomEndStatus(roomId);
            return new ResponseEntity<>(message, headers, HttpStatus.OK);
        }
    }

    @PutMapping("/api/user/room/start/{roomId}")
    @ApiOperation(value = "방 시작",notes = "방 시작. status start로 변경. start_date 저장")
    @ApiImplicitParam(name = "roomId",value = "방의 번호",paramType = "path")
    public ResponseEntity<?> startRoom(@PathVariable int roomId){
        Message message = new Message();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application","json", Charset.forName("UTF-8")));

        roomService.startRoom(roomId);
        message.setStatus(StatusEnum.OK);
        message.setMessage("방 시작 성공");
        return new ResponseEntity<>(message,httpHeaders, HttpStatus.OK);
    }

    @PutMapping("/api/user/room/end/{roomId}")
    @ApiOperation(value = "방 종료",notes = "방(운동) 끝. status end로 변경. end_date 저장")
    @ApiImplicitParam(name = "roomId",value = "방의 번호",paramType = "path")
    public ResponseEntity<?> endRoom(@PathVariable("roomId") int roomId){
        Message message = new Message();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application","json", Charset.forName("UTF-8")));

        roomService.endRoom(roomId);

        /* 만약에 최종 한명 남아있다면 세션에서 방을 지우기 */
        if(this.roomSession.get(roomId)<=1 || this.roomSession.get(roomId)==null){
            this.roomSession.remove(roomId);
        }
        System.out.println(roomSession);
        message.setStatus(StatusEnum.OK);
        message.setMessage("방 종료 성공");
        return new ResponseEntity<>(message, httpHeaders, HttpStatus.OK);
    }





}
