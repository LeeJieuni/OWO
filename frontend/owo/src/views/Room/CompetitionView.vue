<template>
  <div class="d-flex justify-content-center" style="width: 100%; height:100vh">
    <div style="width:1600px;">
    <!-- <div style="width:90%;"> -->
      <div class="d-flex justify-content-between align-items-center" style="height: 80px">
        <h3 class="game-name m-0">{{ gameName }}</h3>
        <Timer />
      </div>
      <!-- <div>
        <p class="text-center">
          <button class="btn btn-lg btn-success" @click="makeRoom()">세션열기</button>
        </p>
        <p class="text-center">
          <button class="btn btn-lg btn-success" @click="getRoomList(mode[0])">자유방 목록</button>
        </p>
        <p class="text-center">
          <button class="btn btn-lg btn-success" @click="getRoomList(mode[1])">
            스트리밍방 목록
          </button>
        </p>
        <p class="text-center">
          <button class="btn btn-lg btn-success" @click="getRoomList(mode[2])">게임방 목록</button>
        </p>
      </div> -->
      <!-- 세션 없다면 이동 -->
      <div id="join" v-if="!session">
        <div id="img-div">
          <img src="resources/images/openvidu_grey_bg_transp_cropped.png" alt="" />
        </div>
        <div id="join-dialog" class="jumbotron vertical-center">
          <h1>Join a video session</h1>
          <div class="form-group">
            <p>
              <label for="participant">Participant
              <input for="participant" v-model="myUserName"
              class="form-control" type="text" required /></label>
            </p>
            <p>
              <label for="session">Session
              <input for="session" v-model="sessionId"
              class="form-control" type="text" required /></label>
            </p>
            <p class="text-center">
              <button class="btn btn-lg btn-success" @click="joinSession(sessionId)">
                Join!
              </button>
            </p>
          </div>
        </div>
      </div>
      <!-- 세션 열리는 동안 -->
      <div id="session" v-if="session">
        <!-- <div id="session-header" class="d-flex">
          <h1 id="session-title">{{ mySessionId }}</h1>
          <input class="btn btn-large btn-danger" type="button"
            id="buttonLeaveSession" @click="leaveSession" value="Leave session"/>
        </div> -->
        <!-- WebRTC 목록 -->
        <div>
          <div id="" class="row d-flex align-items-start justify-content-center">
            <WebRTC :stream-manager="mainStreamManager"/>
            <WebRTC :stream-manager="sub"
              v-for="sub in subscribers"
              :key="sub.stream.connection.connectionId"
              @click="updateMainVideoStreamManager(sub)"
            />
            <div v-if="this.subscribers.length <= 0" class="webrtcetc col-4 m0p0 mb-2 mx-1"></div>
            <div v-if="this.subscribers.length <= 1" class="webrtcetc col-4 m0p0 mb-2 mx-1"></div>
            <div v-if="this.subscribers.length <= 2" class="webrtcetc col-4 m0p0 mb-2 mx-1"></div>
            <div v-if="this.subscribers.length <= 3" class="webrtcetc col-4 m0p0 mb-2 mx-1"></div>
            <div v-if="this.subscribers.length <= 4" class="webrtcetc col-4 m0p0 mb-2 mx-1"></div>
          </div>
        </div>
      </div>
      <!-- <p class="text-center">
        <button class="btn btn-lg btn-success" @click="start()">게임시작</button>
      </p>
      <p class="text-center">
        <button class="btn btn-lg btn-success" @click="end()">게임종료</button>
      </p> -->
      <!-- Rooom 버튼 -->
      <div>
        <div class="d-flex justify-content-center">
          <div class="d-flex justify-content-start align-items-center">
            <button class="btn btn-outline-secondary m-2 " style="width:145px;"
            @click="mic_off" v-if="mic">
              <img class="menu_icon1" src="@/assets/icon/mic_off.png" alt="mic_on">음소거 해제
            </button>
            <button class="btn btn-outline-secondary m-2 " style="width:145px;"
            @click="mic_on" v-if="!mic">
              <img class="menu_icon1" src="@/assets/icon/mic_on.png" alt="mic_off">음소거
            </button>
            <button class="btn btn-outline-secondary m-2" style="width:145px;"
            @click="video_off" v-if="video">
              <img class="menu_icon1" src="@/assets/icon/video_off.png" alt="video_on">비디오 시작
            </button>
            <button class="btn btn-outline-secondary m-2" style="width:145px;"
              @click="video_on" v-if="!video">
              <img class="menu_icon1" src="@/assets/icon/video_on.png" alt="video_off">비디오 중지
            </button>
            <div class="m-2">
              <b-dropdown split @click="take_photo" variant="outline-secondary">
                <template #button-content >
                  <img class="menu_icon1" src="@/assets/icon/photo.png" alt="photo">&ensp;사진촬영
                </template>
                <b-dropdown-item :value="timer" @click="set_timer_3">3초</b-dropdown-item>
                <b-dropdown-item :value="timer" @click="set_timer_5">5초</b-dropdown-item>
                <b-dropdown-item :value="timer" @click="set_timer_10">10초</b-dropdown-item>
              </b-dropdown>
            </div>
            <!-- 운동 셋팅 모달 -->
            <div>
              <button v-b-modal.room-setting-modal
              class="btn btn-outline-secondary m-2">
                <img class="menu_icon1" src="@/assets/icon/room_setting.png" alt="mic_off">설정
              </button>
              <b-modal id="room-setting-modal" title="#오운완" size="lg" hide-footer>
                <div class="after-exercise">
                  <div class="d-flex justify-content-start align-items-end">
                    <img class="menu_icon3" src="@/assets/icon/room_setting.png" alt="mic_off">
                    <div class="d-flex justify-content-center align-items-end">
                      <h2 style="line-height: 0.8;">&ensp;운동 설정&ensp;&ensp;&ensp;</h2>
                      <div v-if="lockroomcheck" class="d-flex align-items-center">
                        <img class="menu_icon1" src="@/assets/icon/unlock.png" alt="lock">
                        <div class="text-center">&ensp;비밀 방&ensp;</div>
                        <label class="form-check-label"
                        for="flexCheckDefault"><input
                        class="form-check-input"
                        type="checkbox" value=""
                        @click="lockroom" id="flexCheckDefault"></label>
                        &ensp;&ensp;&ensp;&ensp;비밀번호&ensp;
                        <label for="form-password-label"><input class="form-password-input"
                        type="password" maxlength="4" size="10" disabled></label>
                      </div>
                      <div v-if="!lockroomcheck" class="d-flex align-items-center">
                        <img class="menu_icon1" src="@/assets/icon/lock.png" alt="lock">
                        <div class="text-center">&ensp;비밀 방&ensp;</div>
                        <label class="form-check-label" for="flexCheckDefault">
                        <input class="form-check-input" type="checkbox" value=""
                        @click="lockroom" id="flexCheckDefault"></label>
                        &ensp;&ensp;&ensp;&ensp;비밀번호&ensp;
                        <label for="form-password-label"><input class="form-password-input"
                        type="password" maxlength="4" size="10"></label>
                      </div>
                    </div>
                  </div>
                  <br>
                  <br>
                  <div>방 모드 변경하기</div>
                  <br>
                  <div class="d-flex justify-content-around">
                      <button class="btn btn-primary">친구들과 운동하기</button>
                      <button class="btn btn">영상보며 운동하기</button>
                  </div>
                  <br>
                  <br>
                  <div class="d-flex justify-content-between">
                    <div>
                      <div>방 이름 변경</div>
                      <br>
                      <label for="roomNameChange" class="roomNameChange">
                      <input v-model="this.sessionId" type="text" class="roomNameChange" size="30">
                      </label>
                    </div>
                    <div>
                      <div>운동 변경</div>
                      <br>
                      <label for="exerciseChange" class="exerciseChange">
                      <input type="text" class="exerciseChange" rows="2" size="30"></label>
                    </div>
                  </div>
                  <br>
                  <br>
                  <div v-if="this.mode">
                    <div>운동 영상 링크</div>
                    <br>
                    <label for="youtubelink"><textarea id="youtubelink"
                    name="youtubelink" size="77" rows="2" cols="77"></textarea></label>
                  </div>
                  <br>
                  <br>
                  <div class="d-flex justify-content-center">
                    <button class="btn btn-primary">저장</button>&ensp;&ensp;&ensp;&ensp;
                    <button class="btn btn">취소</button>
                  </div>
                </div>
              </b-modal>
            </div>
            <!-- 운동 종료 버튼 / 모달 -->
            <div>
                <button @click="leaveSession"
                v-b-modal.after-exercise-modal class="btn btn-outline-secondary m-2">
                  <img class="menu_icon1" src="@/assets/icon/room_end.png" alt="mic_off">
                  운동 종료
                </button>
                <b-modal id="after-exercise-modal" size="lg" hide-footer hide-header>
                  <div>
                    <h1 class="title text-center mt-4">운동일지 작성하기</h1>
                    <div class="wrapper">
                      <div class="md-title text-center">오늘의 운동 사진 고르기📷</div>
                      <div class="container mycontainer">
                        <div class="row row-cols-3">
                          <div v-for="(photo, i) in this.pictures" :key="i" class="col photo">
                            <div class="col p-1">
                              <div class="card">
                                <img :src="photo.src" alt="" class=" img-fluid">
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>
                    </div>
                    <div class="wrapper">
                      <div class="md-title text-center">운동 종류 기록하기</div>
                      <!-- 나만의 태그 기록 -->
                      <!-- <form action="">
                        <label for="my-tag">
                        <input v-model="inputMyTag" type="text">
                        <button @click="addMyTag($event)">추가</button>
                        </label>
                      </form>
                      <div class="mytags row">
                        <div>hi</div>
                        <div v-for="(mytag, index) in roomexercise.mytag" :key="index">
                          <p>#{{ roomexercise.mytag }}</p>
                        </div>
                      </div> -->
                      <div class="d-flex justify-content-center">
                        <button class="mybutton btn btn-primary m-2">#유산소</button>
                        <button class="mybutton btn btn-primary m-2">#헬스</button>
                        <button class="mybutton btn btn-primary m-2">#스트레칭</button>
                        <button class="mybutton btn btn-primary m-2">#맨몸운동</button>
                        <button class="mybutton btn btn-primary m-2">#요가</button>
                        <button class="mybutton btn btn-primary m-2">#필라테스</button>
                        <button class="mybutton btn btn-primary m-2">#기타</button>
                      </div>
                    </div>
                    <div class="wrapper">
                      <div class="md-title text-center">메모 남기기</div>
                      <div class="d-flex justify-content-center">
                        <form action="" id="exerciseMemoForm">
                          <label for="exerciseMemo">
                            <textarea
                              id="exerciseMemo"
                              name="exerciseJournal"
                              rows="5"
                              cols="90">
                            </textarea>
                          </label>
                        </form>
                      </div>
                    </div>
                    <div class="d-flex justify-content-center">
                      <button on @click="exerciseJournalSubmit($event)"
                        class="mybutton  btn btn-primary" form="exerciseMemoForm">작성</button>
                    </div>
                  </div>
                </b-modal>
              </div>
          </div>
          <!-- 촬영 시 배경 -->
          <div class="d-flex justify-content-center align-items-center"
          v-if="is_take_photo" id="take_photo_background"></div>
          <div class="d-flex justify-content-center align-items-center text-white"
          v-if="is_take_photo" id="">
            <WebRTCPhoto id="take_photo_WebRTC" :stream-manager="mainStreamManager"/>
          </div>
          <div class="d-flex justify-content-center align-items-center text-white mt-4"
          v-if="is_take_photo" id="take_photo_timer">{{ timer }}</div>
        </div>
        <!-- <div v-for="(picture, index) in pictures" :key=index><img :src="picture" alt="">
        </div> -->
      </div>
      <!-- 이모티콘 영역 -->
      <div class="emoji_position" v-if="Emoji_ONOFF">
        <div class="row">
          <!-- apple, google, twitter, facebook -->
          <Picker :data="emojiIndex" set="twitter" @select="showEmoji" />
        </div>
      </div>
      <button @click="open_emoji" class="open_emoji">
        <img class="menu_icon2" src="@/assets/icon/emoji.png" alt="emoji">
      </button>
      <!-- 채팅 영역 -->
      <div v-if="this.session">
        <button v-if="chatONOFF" @click="chatoff" class="chat">
          <img class="chatimg" src="@/assets/icon/commentoff.png" alt="">
        </button>
        <button v-if="!chatONOFF" @click="chaton" class="chat">
          <img class="chatimg" src="@/assets/icon/commenton.png" alt="">
        </button>
        <div v-if="chatONOFF" class="achat d-flex justify-content-center align-items-center">
          <div class="d-flex align-items-center achat-submit">
            <label class="m-0" for="chatting">
              <textarea @keyup.enter="sendMassage" id="mychat"
              name="chatting" type="text"
              v-model="myChat" style="resize:none; border-radius:5px;" placeholder=" 채팅 입력"
              rows="2" cols="25"></textarea>
            </label>
            <button @click="sendMassage" class="btn btn-light achat-submit2">
            Send</button>
          </div>
          <div id="chattingList" class="fluid achat-content"
          style="overflow:auto; height:490px;">
            <div v-for="(item, i) in recvList" :key="i">
              <div class="mychatting p-0" style="margin-top:0px; margin-bottom:10px;
              margin-left:auto; margin-right:30px; width:220px; height:90px;"
              v-if="item.p === this.userInfo.nick">
                <div style="text-align:left; margin-top:5px; margin-left:5px; font-size:large;">
                  <strong>{{item.p}}</strong>
                </div>
                <div style="word-wrap:break-word; text-align:left; margin-top:5px;
                margin-left:5px; font-size:medium;">
                  {{item.m}}
                </div>
              </div>
              <div class="yourchatting p-0" style="margin-top:0px; margin-bottom:10px;
              margin-right:auto; margin-left:30px; width:220px; height:90px;"
              v-if="item.p !== this.userInfo.nick">
                <div style="text-align:left; margin-top:5px; margin-left:5px; font-size:large;">
                  <strong>{{item.p}}</strong>
                </div>
                <div style="word-wrap:break-word; text-align:left; margin-top:5px;
                margin-left:5px; font-size:medium;">
                  {{item.m}}
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <!-- 채팅영역 끝 -->
    </div>
  </div>
</template>
<script>
import html2canvas from 'html2canvas';
import Timer from '@/components/SetTimer.vue';
import WebRTC from '@/components/Room/WebRTC.vue';
import WebRTCPhoto from '@/components/Room/WebRTCPhoto.vue';
import axios from 'axios';
import { OpenVidu } from 'openvidu-browser';
import emojidata from 'emoji-mart-vue-fast/data/all.json';
import 'emoji-mart-vue-fast/css/emoji-mart.css';
import { Picker, EmojiIndex } from 'emoji-mart-vue-fast/src';
import { mapState, mapActions, mapMutations } from 'vuex';
// import * as tf from '@tensorflow/tfjs';
import * as tmPose from '@teachablemachine/pose';

axios.defaults.headers.post['Content-Type'] = 'application/json';

const openvidu = 'openvidu';
const accounts = 'accounts';
const meetingroom = 'meetingroom';
const emojiIndex = new EmojiIndex(emojidata);
const emoji = 'emoji';
// const URL = '{{URL}}';
// let model, maxPredictions;

export default {
  name: 'CompetitionView',
  metaInfo: {
    script: [
      { src: 'https://cdn.jsdelivr.net/npm/@tensorflow/tfjs@1.3.1/dist/tf.min.js', async: true, defer: true },
      { src: 'https://cdn.jsdelivr.net/npm/@teachablemachine/pose@0.8/dist/teachablemachine-pose.min.js', async: true, defer: true },
    ],
  },
  components: {
    Timer,
    WebRTC,
    WebRTCPhoto,
    Picker,
  },
  data() {
    return {
      lockroomcheck: false,
      isMaster: true,
      video: false,
      mic: false,
      timer: 3,
      temp_timer: 3,
      take_photo_timer: null,
      is_take_photo: false,
      // pictures: [],
      emojiIndex,
      emojisOutput: '',
      OV: undefined,
      session: undefined,
      mainStreamManager: undefined,
      publisher: undefined,
      subscribers: [],
      roommode: 'GAME',
      mode: ['FREE', 'STREAMING', 'GAME'],
      myUserName: '',
      sessionId: 'SessionA',
      myChat: '',
      recvList: [],
      chatONOFF: false,
      Emoji_ONOFF: null,
      roomName: '붙어보자!',
      gameName: '스쿼트',
      myemoji: '',
      emojiList: [],
      pictures: [
        { src: 'https://cdn.pixabay.com/photo/2017/08/07/14/02/man-2604149_960_720.jpg' },
        { src: 'https://cdn.pixabay.com/photo/2017/08/07/14/02/man-2604149_960_720.jpg' },
        { src: 'https://cdn.pixabay.com/photo/2017/08/07/14/02/man-2604149_960_720.jpg' },
        { src: 'https://cdn.pixabay.com/photo/2017/08/07/14/02/man-2604149_960_720.jpg' },
        { src: 'https://cdn.pixabay.com/photo/2017/08/07/14/02/man-2604149_960_720.jpg' },
        { src: 'https://cdn.pixabay.com/photo/2017/08/07/14/02/man-2604149_960_720.jpg' },
      ],
      webcam: undefined,
      URL: undefined,
      model: undefined,
      status: 'ready',
      check: false,
      count: 0,
      // gameType: 'pushUp',
      gameType: 1,
    };
  },
  setup() {},
  created() {
    this.sessionId = this.$route.params.sessionId;
    this.joinSession(this.sessionId);
  },
  moundted() {
  },
  unmounted() {},
  watch: {
    mySessionId() {},
    camera() {
      if (this.publisher !== undefined) {
        if (this.camera) {
          this.publisher.publishVideo(true);
        } else {
          this.publisher.publishVideo(false);
        }
      }
    },
    mic() {
      if (this.publisher !== undefined) {
        if (this.mic) {
          this.publisher.publishAudio(true);
        } else {
          this.publisher.publishAudio(false);
        }
      }
    },
  },

  computed: {
    ...mapState(emoji, ['allEmojiList']),
    ...mapState(accounts, ['accessToken', 'userInfo']),
    ...mapState(openvidu, ['OPENVIDU_SERVER_URL', 'OPENVIDU_SERVER_SECRET']),
    ...mapState(meetingroom, ['mySessionId', 'meetingRoomList', 'camera', 'mic']),
    // ...openviduHelper.mapState(["OPENVIDU_SERVER_URL", "OPENVIDU_SERVER_SECRET"]),
    // ...meetingRoomHelper.mapState(["sessionID", "meetingRoomList"]),
  },
  methods: {
    ...mapActions(emoji, ['changeEmojiList', 'removeEmojiList']),
    ...mapMutations(meetingroom, ['SET_SESSION_ID']),
    ...mapActions(meetingroom, [
      'makeSession',
      'getMeetingRoomList',
      'enterMeetingRoom',
      'leaveMeetingRoom',
      'startMeetingRoom',
    ]),
    async makeRoom() {
      const requestDto = {
        accesstoken: this.accessToken,
        memberId: '2',
        secret: false,
        password: '',
        mode: 'GAME',
        roomName: '방1',
        type: 'GAME',
        link: '',
      };
      await this.makeSession(requestDto);
      console.log('=========================');
      console.log(this.mySessionId);
      this.joinSession(this.mySessionId);
    },
    getRoomList(m) {
      const requestDto = {
        accesstoken: this.accessToken,
        mode: m,
      };
      this.getMeetingRoomList(requestDto);
    },
    joinSession(sessionNum) {
      this.SET_SESSION_ID(sessionNum);
      console.log(`sessionID = ${sessionNum}`);
      const requestDto = {
        accesstoken: this.accessToken,
        roomId: String(sessionNum),
      };
      console.log(sessionNum);
      this.enterMeetingRoom(requestDto);
      // --- Get an OpenVidu object ---
      this.OV = new OpenVidu();

      // --- Init a session ---
      this.session = this.OV.initSession();

      // --- Specify the actions when events take place in the session ---

      // On every new Stream received...
      this.session.on('streamCreated', ({ stream }) => {
        const subscriber = this.session.subscribe(stream);
        this.subscribers.push(subscriber);
      });

      // On every Stream destroyed...
      this.session.on('streamDestroyed', ({ stream }) => {
        const index = this.subscribers.indexOf(stream.streamManager, 0);
        if (index >= 0) {
          this.subscribers.splice(index, 1);
        }
      });

      // On every asynchronous exception...
      this.session.on('exception', ({ exception }) => {
        console.warn(exception);
      });

      // --- Connect to the session with a valid user token ---

      // 'getToken' method is simulating what your server-side should do.
      // 'token' parameter should be retrieved and returned by your own backend
      this.getToken(this.mySessionId).then((token) => {
        this.session
          .connect(token, { clientData: this.userInfo.nick })
          .then(() => {
            // --- Get your own camera stream with the desired properties ---

            const publisher = this.OV.initPublisher(undefined, {
              audioSource: undefined, // The source of audio. If undefined default microphone
              videoSource: undefined, // The source of video. If undefined default webcam
              publishAudio: true,
              // Whether you want to start publishing with your audio unmuted or not
              publishVideo: true,
              // Whether you want to start publishing with your video enabled or not
              resolution: '250x150', // The resolution of your video
              frameRate: 30, // The frame rate of your video
              insertMode: 'APPEND',
              // How the video is inserted in the target element 'video-container'
              mirror: false, // Whether to mirror your local video or not
            });

            this.mainStreamManager = publisher;
            this.publisher = publisher;

            // --- Publish your stream ---

            this.session.publish(this.publisher);
          })
          .catch((error) => {
            console.log('There was an error connecting to the session:', error.code, error.message);
          });
      });
      // Receiver of the message (usually before calling 'session.connect')
      this.session.on('signal:my-chat', (event) => {
        const chatdata = event.data.split(',');
        // console.log(event.from); // Connection object of the sender //누가 보냈는지가 아니네..?
        // console.log(event.type); // The type of message ("my-chat")
        // this.allChat = event.data;
        // this.who = event.from; //누가 보냈는지
        const obj = {
          m: chatdata[0],
          p: chatdata[1],
        };
        this.recvList.push(obj);
        // console.log(this.recvList[0].m);
        const chat = document.querySelector('#chattingList');
        chat.scrollTop = chat.scrollHeight + 10000000;
      });

      this.session.on('signal:my-emoji', (event) => {
        const chatdata2 = event.data.split(',');
        const obj = [chatdata2[1], chatdata2[0]];
        this.emojiList.push(obj);
        this.changeEmojiList(this.emojiList);
      });

      // Receiver of the message (usually before calling 'session.connect')
      this.session.on('signal:start', (event) => {
        this.count = 0;
        console.log(event);
        console.log('게임! start');
      });

      this.session.on('signal:end', (event) => {
        console.log(event);
        console.log('게임! end');
        this.leaveSession();
      });

      window.addEventListener('beforeunload', this.leaveSession);
    },

    sendEmoji() {
      this.session
        .signal({
          data: `${this.myemoji},${this.userInfo.nick}`,
          to: [],
          type: 'my-emoji',
        })
        .then(() => {
        })
        .catch(() => {});
    },

    sendMassage() {
      // Sender of the message (after 'session.connect')
      this.session
        .signal({
          data: `${this.myChat},${this.userInfo.nick}`, // Any string (optional)
          to: [], // Array of Connection objects (optional. Broadcast to everyone if empty)
          type: 'my-chat', // The type of message (optional)
        })
        .then(() => {
          const resetText = document.querySelector('#mychat');
          resetText.value = '';
          const chat = document.querySelector('#chattingList');
          chat.scrollTop = chat.scrollHeight + 1000000;
          console.log('Message successfully sent');
          this.myChat = '';
        })
        .catch((error) => {
          console.error(error);
        });
    },

    end() {
      const requestDto = {
        accesstoken: this.accessToken,
        roomId: this.mySessionId,
      };

      axios({
        url: `https://i7c202.p.ssafy.io:8282/api/room/end/${Number(requestDto.roomId)}`,
        method: 'put',
        headers: {
          'X-AUTH-TOKEN': requestDto.accesstoken,
        },
      })
        .then((res) => {
          console.log(res.data);
        })
        .catch((err) => {
          console.log(err);
        });

      this.session
        .signal({
          data: 'stameetingRoomEnd', // Any string (optional)
          to: [], // Array of Connection objects (optional. Broadcast to everyone if empty)
          type: 'end', // The type of message (optional)
        })
        .then(() => {
          console.log('Message successfully sent(end)');
        })
        .catch((error) => {
          console.error(error);
        });
    },

    start() {
      const requestDto = {
        accesstoken: this.accessToken,
        roomId: this.mySessionId,
      };

      axios({
        url: `https://i7c202.p.ssafy.io:8282/api/room/start/${Number(requestDto.roomId)}`,
        method: 'put',
        headers: {
          'X-AUTH-TOKEN': requestDto.accesstoken,
        },
      })
        .then((res) => {
          console.log(res.data);
        })
        .catch((err) => {
          console.log(err);
        });

      this.session
        .signal({
          data: 'stameetingRoomStartrt', // Any string (optional)
          to: [], // Array of Connection objects (optional. Broadcast to everyone if empty)
          type: 'start', // The type of message (optional)
        })
        .then(() => {
          console.log('Message successfully sent(start)');
        })
        .catch((error) => {
          console.error(error);
        });
    },

    async leaveSession() {
      this.removeEmojiList();
      this.removeEmoji();
      // --- Leave the session by calling 'disconnect' method over the Session object ---
      if (this.session) this.session.disconnect();
      const requestDto = {
        accesstoken: this.accessToken,
        roomId: this.mySessionId,
      };
      await this.leaveMeetingRoom(requestDto);
      this.session = undefined;
      this.mainStreamManager = undefined;
      this.publisher = undefined;
      this.subscribers = [];
      this.OV = undefined;
      window.removeEventListener('beforeunload', this.leaveSession);
      this.$router.push('/');
    },

    updateMainVideoStreamManager(stream) {
      if (this.mainStreamManager === stream) return;
      this.mainStreamManager = stream;
    },

    /**
     * --------------------------
     * SERVER-SIDE RESPONSIBILITY
     * --------------------------
     * These methods retrieve the mandatory user token from OpenVidu Server.
     * This behavior MUST BE IN YOUR SERVER-SIDE IN PRODUCTION (by using
     * the API REST, openvidu-java-client or openvidu-node-client):
     *   1) Initialize a Session in OpenVidu Server(POST /openvidu/api/sessions)
     *   2) Create a Connection in OpenVidu Server
     * (POST /openvidu/api/sessions/<SESSION_ID>/connection)
     *   3) The Connection.token must be consumed in Session.connect() method
     */

    getToken(mySessionId) {
      return this.createSession(mySessionId).then((sessionId) => this.createToken(sessionId));
    },
    // See https://docs.openvidu.io/en/stable/reference-docs/REST-API/#post-session
    createSession(sessionId) {
      return new Promise((resolve, reject) => {
        axios
          .post(
            `${this.OPENVIDU_SERVER_URL}/openvidu/api/sessions`,
            JSON.stringify({
              customSessionId: sessionId,
            }),
            {
              auth: {
                username: 'OPENVIDUAPP',
                password: this.OPENVIDU_SERVER_SECRET,
              },
            },
          )
          .then((response) => response.data)
          .then((data) => resolve(data.id))
          .catch((error) => {
            if (error.response.status === 409) {
              resolve(sessionId);
            } else {
              console.warn(
                `No connection to OpenVidu Server.
                This may be a certificate error
                at ${this.OPENVIDU_SERVER_URL}`,
              );
              if (
                window.confirm(
                  `No connection to OpenVidu Server. T
                  his may be a certificate error
                  at ${this.OPENVIDU_SERVER_URL}\n\nClick OK to navigate and accept it.
                  If no certificate warning is shown,
                  then check that your OpenVidu Server is up and
                  running at "${this.OPENVIDU_SERVER_URL}"`,
                )
              ) {
                window.location.assign(`${this.OPENVIDU_SERVER_URL}/accept-certificate`);
              }
              reject(error.response);
            }
          });
      });
    },

    // See https://docs.openvidu.io/en/stable/reference-docs/REST-API/#post-connection
    createToken(sessionId) {
      return new Promise((resolve, reject) => {
        axios
          .post(
            `${this.OPENVIDU_SERVER_URL}/openvidu/api/sessions/${sessionId}/connection`,
            {},
            {
              auth: {
                username: 'OPENVIDUAPP',
                password: this.OPENVIDU_SERVER_SECRET,
              },
            },
          )
          .then((response) => response.data)
          .then((data) => resolve(data.token))
          .catch((error) => reject(error.response));
      });
    },
    chatoff() {
      this.chatONOFF = false;
    },
    chaton() {
      this.chatONOFF = true;
    },
    open_emoji() {
      this.Emoji_ONOFF = !this.Emoji_ONOFF;
    },
    showEmoji(e) {
      this.myemoji = e.native;
      this.sendEmoji();
      this.open_emoji();
    },
    removeEmoji() {
      this.emojiList = [];
    },
    ...mapMutations(meetingroom, ['SET_CAMERA', 'SET_MIC']),
    mic_on() {
      this.mic = true;
      this.SET_MIC(false);
    },
    mic_off() {
      this.mic = false;
      this.SET_MIC(true);
    },
    video_on() {
      this.video = true;
      this.SET_CAMERA(false);
    },
    video_off() {
      this.video = false;
      this.SET_CAMERA(true);
    },
    take_photo() {
      this.temp_timer = this.timer;
      this.is_take_photo = true;
      this.take_photo_timer = setInterval(() => {
        this.timer -= 1;
        console.log(this.timer);
        if (this.timer === 0) {
          console.log('사진찍는 모션');
          const el = document.querySelector('#take_photo_WebRTC');
          // eslint-disable-next-line
          html2canvas(el).then((canvas) => {
            // document.body.appendChild(canvas);
            const link = document.createElement('a');
            document.body.appendChild(link);
            link.href = canvas.toDataURL('image/png', 1.0);
            localStorage.setItem('userImage', link);
            link.download = '안녕하세요?.png';
            link.click();
            document.body.removeChild(link);
          });
          clearInterval(this.take_photo_timer);
          this.is_take_photo = false;
          this.timer = this.temp_timer;
        }
      }, 1000);
    },
    set_timer_3() {
      this.timer = 3;
      console.log(this.timer);
    },
    set_timer_5() {
      this.timer = 5;
      console.log(this.timer);
    },
    set_timer_10() {
      this.timer = 10;
      console.log(this.timer);
    },
    lockroom() {
      this.lockroomcheck = !this.lockroomcheck;
    },
    exerciseJournalSubmit(event) {
      event.preventDefault();
    },
    async setmodel() {
      switch (this.gameType) {
        case 1: // 스쿼트
          this.URL = 'https://teachablemachine.withgoogle.com/models/np91IAbMN/';
          break;
        case 2: // 런지
          this.URL = 'https://teachablemachine.withgoogle.com/models/qsNO7nn-l/';
          break;
        case 3: // 버피
          this.URL = 'https://teachablemachine.withgoogle.com/models/fR-T-F0cP/';
          break;
        default:
          break;
      }
      const modelURL = `${this.URL}model.json`;
      const metadataURL = `${this.URL}metadata.json`;
      this.model = await tmPose.load(modelURL, metadataURL);
    },

    async init() {
      const size = 200;
      const flip = true;
      this.webcam = new tmPose.Webcam(size, size, flip);
      await this.webcam.setup();
      await this.webcam.play();
      window.requestAnimationFrame(this.loop);
    },

    async loop() {
      this.webcam.update();
      switch (this.gameType) {
        case 1:
          await this.squatpredict();
          break;
        case 2:
          await this.lungepredict();
          break;
        case 3:
          await this.burpeepredict();
          break;
        default:
          break;
      }
      window.requestAnimationFrame(this.loop);
    },

    async squatpredict() {
      // Prediction #1: run input through posenet
      // estimatePose can take in an image, video or canvas html element
      const { posenetOutput } = await this.model.estimatePose(
        this.webcam.canvas,
      );
      // Prediction 2: run input through teachable machine classification model
      const prediction = await this.model.predict(posenetOutput);

      if (prediction[0].probability.toFixed(2) > 0.99) {
        if (this.check) {
          this.setState({
            count: this.count + 1,
          });
          this.session
            .signal({
              data: `${this.myUserName},${this.count}`, // Any string (optional)
              to: [], // Array of Connection objects (optional. Broadcast to everyone if empty)
              type: 'count', // The type of message (optional)
            })
            .then(() => {
              this.setState({ check: false });
            })
            .catch(() => {});
        }
        this.setState({ status: 'ready' });
      } else if (prediction[1].probability.toFixed(2) > 0.99) {
        this.setState({ status: 'squat' });
        this.setState({ check: true });
      }
    },

    async lungepredict() {
      // Prediction #1: run input through posenet
      // estimatePose can take in an image, video or canvas html element
      const { posenetOutput } = await this.model.estimatePose(
        this.webcam.canvas,
      );
      // Prediction 2: run input through teachable machine classification model
      const prediction = await this.model.predict(posenetOutput);
      if (prediction[0].probability.toFixed(2) > 0.99) {
        if (this.check) {
          this.setState({
            count: this.count + 1,
          });
          this.session
            .signal({
              data: `${this.myUserName},${this.count}`, // Any string (optional)
              to: [], // Array of Connection objects (optional. Broadcast to everyone if empty)
              type: 'count', // The type of message (optional)
            })
            .then(() => {
              this.setState({ check: false });
            })
            .catch(() => {});
        }
        this.setState({ status: 'ready' });
      } else if (prediction[1].probability.toFixed(2) > 0.99) {
        this.setState({ status: 'lunge' });
        this.setState({ check: true });
      }
    },

    async burpeepredict() {
      // Prediction #1: run input through posenet
      // estimatePose can take in an image, video or canvas html element
      const { posenetOutput } = await this.model.estimatePose(
        this.webcam.canvas,
      );
      // Prediction 2: run input through teachable machine classification model
      const prediction = await this.model.predict(posenetOutput);
      if (prediction[0].probability.toFixed(2) > 0.99) {
        if (this.check) {
          this.setState({
            count: this.state.count + 1,
          });
          this.session
            .signal({
              data: `${this.myUserName},${this.count}`, // Any string (optional)
              to: [], // Array of Connection objects (optional. Broadcast to everyone if empty)
              type: 'count', // The type of message (optional)
            })
            .then(() => {
              this.setState({ check: false });
            })
            .catch(() => {});
        }
        this.setState({ status: 'up' });
      } else if (prediction[1].probability.toFixed(2) > 0.99) {
        this.setState({ status: 'down' });
        this.setState({ check: true });
      }
    },
  },
};
</script>
<style scoped>
div {
  color: black;
}

.menu_icon1 {
  width:30px;
}

.menu_icon2 {
  width:50px;
}

.menu_icon3 {
  height:40px;
  width:40px;
}

.emoji_position {
  position:fixed;
  bottom: 100px;
  left: 20px;
}

.open_emoji {
  background-color:transparent;
  border:none;
  position:fixed;
  bottom: 30px;
  left: 30px;
}

.chat {
  border:none;
  background-color:transparent;
  position:fixed;
  bottom: 30px;
  right: 30px;
}

.chatimg {
  width:50px;
}

.achat {
  position:fixed;
  border: 2px solid #4e8aff;
  border-radius: 30px;
  width:320px;
  height:600px;
  background-color: #4e8aff;
  bottom: 100px;
  right: 20px;
}

.achat-content {
  position:fixed;
  border: 2px solid #4e8aff;
  width:320px;
  height:490px;
  background-color: white;
  bottom: 180px;
  right: 20px;
}

.achat-submit {
  position:fixed;
  width:300px;
  height:70px;
  bottom: 105px;
  right: 19px;
}

.achat-submit2 {
  position: fixed;
  bottom: 124px;
  right: 40px;
}

.mychatting {position:relative; margin: 50px; padding: 20px; width:180px; height:90px;
border:1px solid #C5a180; border-radius: 10px; background-color:  #C5a180;}
.mychatting:after {content:""; position: absolute; top: 21px; right: -30px; border-left: 30px
solid  #C5a180; border-top: 10px solid transparent; border-bottom: 10px solid transparent;}

.yourchatting {position:relative; margin: 50px; padding: 20px; width:180px; height:90px;
border:1px solid #ccb9a8; border-radius: 10px; background-color: #ccb9a8;}
.yourchatting:after {content:""; position: absolute; top: 21px; left: -30px; border-right: 30px
solid #ccb9a8; border-top: 10px solid transparent; border-bottom: 10px solid transparent;}

.webrtcetc {
  /* width: 30%;
  height:100%; */
  width:500px;
  height:300px;
  text-align:center;
  vertical-align: middle;
  background-image: url('../../assets/icon/Loading2.gif');
  background-position: center;
  background-size: 30px 30px;
  background-repeat: no-repeat;
}

.Emoji {
  position:fixed;
  top:100px;
  left:100px;
  z-index: 103;
}

.m0p0 {
  margin:0;
  padding:0;
}

.row { display: flex; }
.row > * { margin: auto; }

#take_photo_background {
    background-color: gray;
    position: fixed;
    overflow: hidden;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    z-index: 99;
    opacity: 0.7;
    font-size:300px;
}

#take_photo_WebRTC {
  margin:0px;
  padding:0px;
  background-color:gray;
  position: fixed;
  top: 10%;
  left: 20%;
  width: 60%;
  /* height: 80%; */
  z-index: 100;
}

#take_photo_timer {
    position:fixed;
    top: 0%;
    left: 0%;
    width: 100%;
    height: 100%;
    z-index: 101;
    font-size:300px;
}

.after-exercise {
    width:600px;
    margin:auto;
}

.mybutton {
  padding: 0;
  border: none;
  background: none;
}

#after-exercise-modal {
  font-family: 'NanumSquareRound', 'Noto Sans KR', sans-serif;
  /* width: 900px; */
}

.title {
  font-weight: 900;
}

.md-title {
  font-size: 24px;
  font-weight: 800;
  margin: 20px 0;
}

.mycontainer {
  height: 400px;
}

.wrapper {
  padding: 20px 0;
}

.photo-row {
  /* width:100%; */
}

.photo {
  /* border: solid 1px black; */
  /* padding: 0 10px; */
  /* margin: 10px; */
  /* background-color:gray; */
  /* width:10%; */
  /* width: 100px;
  height:100px; */
}

.exerciseMemo {
  font-size: 20px;
  font-weight: 700;
}

.socialbtn {
  margin-top: 20px;
}

.input_email {
  display: flex;
  margin-bottom: 20px;
}

.input_password {
  display: flex;
  margin-bottom: 20px;
}

.alreadyUser {
  text-align: center;
  font-size: 20px;
}

.buttons {
  margin-top: 50px;
  display: flex;
  justify-content: center;
  align-items: center;
}

.form-check-input {
  position:relative;
  top:6px;
  left:0px;
}

.form-password-input {
  position:relative;
  top:7px;
  left:0px;
}
</style>
