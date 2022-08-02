package com.sos.owo.controller;

import com.sos.owo.dto.MeetingRoomMakeRequestDto;
import com.sos.owo.dto.MeetingRoomResponseDto;
import com.sos.owo.service.MeetingRoomService;
import io.openvidu.java.client.OpenVidu;
import io.openvidu.java.client.OpenViduHttpException;
import io.openvidu.java.client.OpenViduJavaClientException;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
public class MeetingRoomController {

    private final int LIMIT = 6;
    private final MeetingRoomService roomService;

    // 오픈 비두 객체
    private OpenVidu openVidu;

    // 방(세션) 관리
    private Map<String, Integer> roomSession = new ConcurrentHashMap<>();

    // 오픈비두 서버 관련 변수
    private String OPENVIDU_URL;
    private String OPENVIDU_SECRET;


    // MeetingRoomController 접근시에 오픈비두 서버 관련 변수를 얻음
    @Autowired
    public MeetingRoomController(MeetingRoomService roomService, @Value("${openvidu.secret}") String secret, @Value("${openvidu.url}") String openviduUrl){
        this.roomService = roomService;
        this.OPENVIDU_SECRET = secret;
        this.OPENVIDU_URL = openviduUrl;
        this.openVidu = new OpenVidu(OPENVIDU_URL, OPENVIDU_SECRET);
    }

//    @PostMapping
//    @ApiOperation(value="운동방을 만드는 API", notes = "방만들기를 통해 화상방에 대한 세션과 토큰을 생성한 후에 토큰, 방에 대한 정보를 반환")
//    public ResponseEntity<?> makeMeetingRoom(@ResponseBody MeetingRoomMakeRequestDto requestDto) throws OpenViduJavaClientException, OpenViduHttpException {
//        // 방을 DB에 저장
//        roomService.createRoom(requestDto);
//        // 방 (세션) 관리를 map에 저장
//        this.roomSession.put()
//    }


}
