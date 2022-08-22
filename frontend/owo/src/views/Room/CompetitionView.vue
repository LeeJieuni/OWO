<template>
  <!-- eslint-disable-next-line -->
  <div class="d-flex justify-content-center" style="width: 100%; height:100vh; background-color:#eaf1ff;">
    <div style="width:1600px;" class="viewsetting">
      <!-- eslint-disable-next-line -->
      <div class="d-flex justify-content-center align-items-center" style="width: 100%; height: 80px;">
      <!-- eslint-disable-next-line -->
        <h3 class="game-name m-0" style="font-size:3rem; font-family: 'LeferiPoint-WhiteObliqueA';"><strong>{{ roomName }}</strong><span v-if="roundGameName"> | <span style="color:#274c95"><strong>{{ roundGameName }}</strong></span></span></h3>
      </div>
      <!-- 세션 -->
      <div id="session" v-if="session">
        <div>
          <!-- eslint-disable-next-line -->
          <div style="position:relative;" class="row d-flex align-items-start justify-content-center">
            <!-- eslint-disable-next-line -->
            <canvas style="position:absolute; top:10px; left:20px; padding:0; width:480px; height:340px; opacity:0;" class="mx-2 my-2" id="canvasTM"></canvas>
            <WebRTC ref="webrtc" :stream-manager="mainStreamManager"/>
            <WebRTC :stream-manager="sub"
              v-for="sub in subscribers"
              :key="sub.stream.connection.connectionId"
            />
            <div v-show="this.subscribers.length <= 0" class="webrtcetc col-4 m0p0 my-2 mx-2"></div>
            <div v-show="this.subscribers.length <= 1" class="webrtcetc col-4 m0p0 my-2 mx-2"></div>
            <div v-show="this.subscribers.length <= 2" class="webrtcetc col-4 m0p0 my-2 mx-2"></div>
            <div v-show="this.subscribers.length <= 3" class="webrtcetc col-4 m0p0 my-2 mx-2"></div>
            <div v-show="this.subscribers.length <= 4" class="webrtcetc col-4 m0p0 my-2 mx-2"></div>
          </div>
        </div>
      </div>
      <!-- 채팅 -->
      <div v-if="this.session">
        <div v-if="!isExercising">
          <button v-show="chatONOFF" @click="chatoff" class="chat">
            <img class="chatimg" src="@/assets/icon/speak.png" alt="">
          </button>
          <button v-show="!chatONOFF" @click="chaton" class="chat">
            <img class="chatimg" src="@/assets/icon/speak.png" alt="">
          </button>
          <div v-if="chatONOFF" class="achat d-flex justify-content-center align-items-center">
            <div class="d-flex align-items-center achat-submit">
              <label class="m-0" for="chatting">
                <textarea @keyup.enter="sendMassage" id="mychat"
                name="chatting" type="text"
                v-model="myChat" style="resize:none; border:none;
                border-radius:5px;" placeholder=" 채팅 입력"
                rows="2" cols="25"></textarea>
              </label>
              <!-- eslint-disable-next-line -->
              <button @click="sendMassage" style="color:#4e8aff;" class="btn btn-light achat-submit2">
              전송</button>
            </div>
            <div id="chattingList" class="fluid achat-content"
            style="overflow:auto; height:490px;">
              <div v-for="(allchatting, i) in allchattingList" :key="i">
                <div class="mychatting p-0" style="margin-top:0px; margin-bottom:10px;
                margin-left:auto; margin-right:30px; width:220px; height:90px;"
                v-if="allchatting.userId == this.credentialsUser.memberId">
                  <div style="text-align:left; margin-top:5px; margin-left:5px; font-size:large;">
                    <strong>{{allchatting.userNickName}}</strong>
                  </div>
                  <div style="word-wrap:break-word; text-align:left; margin-top:5px;
                  margin-left:5px; font-size:medium;">
                    {{allchatting.userChat}}
                  </div>
                </div>
                <div class="yourchatting p-0" style="margin-top:0px; margin-bottom:10px;
                margin-right:auto; margin-left:30px; width:220px; height:90px;"
                v-if="allchatting.userId != this.credentialsUser.memberId">
                  <div style="text-align:left; margin-top:5px; margin-left:5px; font-size:large;">
                    <strong>{{allchatting.userNickName}}</strong>
                  </div>
                  <div style="word-wrap:break-word; text-align:left; margin-top:5px;
                  margin-left:5px; font-size:medium;">
                    {{allchatting.userChat}}
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <!-- 버튼 -->
      <div>
        <button v-if="!isExercising" @click="open_emoji" class="open_emoji">
          <img class="menu_icon2" src="@/assets/icon/emoji.png" alt="emoji">
        </button>
        <button class="mybtn1" @click="mic_off" v-show="mic">
          <img class="menu_icon2" src="@/assets/icon/mic_on.png" alt="mic_off">
          <div style="color:#4e8aff; font-size:12px;">
            음소거
          </div>
        </button>
        <button class="mybtn1" @click="mic_on" v-show="!mic">
          <img class=" menu_icon2" src="@/assets/icon/mic_off.png" alt="mic_on">
          <div style="color:#de7474; font-size:12px;">
            음소거
          </div>
          <div style="color:#de7474; font-size:12px; line-height:0.8;">
            해제
          </div>
        </button>
        <button class="mybtn2" @click="video_off" v-show="video">
          <img class="menu_icon2" src="@/assets/icon/video_off.png" alt="video_on">
          <div style="color:#de7474; font-size:12px;" >
            비디오
          </div>
          <div style="color:#de7474; font-size:12px; line-height:0.8;">
            시작
          </div>
        </button>
        <button class="mybtn2" @click="video_on" v-show="!video">
          <img class="menu_icon2" src="@/assets/icon/video_on.png" alt="video_off">
          <div style="color:#4e8aff; font-size:12px;">
            비디오
          </div>
          <div style="color:#4e8aff; font-size:12px; line-height:0.8;">
            중지
          </div>
        </button>
        <button v-if="!this.gameType" @click="leaveSession" class="mybtn6">
          <img class="menu_icon2" src="@/assets/icon/roomout.png" alt="leaveSession">
        </button>
        <!-- eslint-disable-next-line -->
        <div v-if="(!this.subscribers.length && !this.isStartedGame)" class="mybtn7">2명 이상 모여야 시작 가능!!</div>
        <!-- eslint-disable-next-line -->
        <div v-if="!(this.credentialsUser.memberId === this.masterId) & !this.gameType & (this.subscribers.length >= 1) && !this.isStartedGame" class="mybtn7">방장 >> 오른쪽 위 START 버튼!</div>
        <!-- eslint-disable-next-line -->
        <button v-if="(this.credentialsUser.memberId === this.masterId) & !this.gameType & (this.subscribers.length >= 1) && !this.isStartedGame" class="mybtn5" @click="startround">
          <img class="menu_icon4" src="@/assets/icon/start.png" alt="Start">
        </button>
      </div>
      <!-- 이모티콘 -->
      <div class="emoji_position" v-if="Emoji_ONOFF & !this.isExercising">
        <div class="row">
          <!-- apple, google, twitter, facebook -->
          <Picker :data="emojiIndex" set="twitter" @select="showEmoji" />
        </div>
      </div>
      <!-- 타이머 -->
      <div class="setTimer2position">
        <setTimer3 ref="setTimer3" />
        <setTimer4 ref="setTimer4" />
      </div>
      <div v-show="isStarted" class="myBackGroundSetting">
        <setTimer2 ref="setTimer2"/>
      </div>
      <!-- eslint-disable-next-line -->
      <div v-if="round1Game" class="roundGame" style="font-size:5rem; color:white; font-family: 'LeferiPoint-BlackObliqueA';">#Round1&ensp;:&ensp;{{ roundGameName }}</div>
      <!-- eslint-disable-next-line -->
      <div v-if="round2Game" class="roundGame" style="font-size:5rem; color:white; font-family: 'LeferiPoint-BlackObliqueA';">#Round2&ensp;:&ensp;{{ roundGameName }}</div>
      <!-- eslint-disable-next-line -->
      <div v-if="round3Game" class="roundGame" style="font-size:5rem; color:white; font-family: 'LeferiPoint-BlackObliqueA';">#Final Round&ensp;:&ensp;{{ roundGameName }}</div>
      <!-- eslint-disable-next-line -->
      <div v-if="restTime" class="roundGame" style="font-size:5rem; color:white; font-family: 'LeferiPoint-BlackObliqueA';">휴식 시간</div>
    </div>
  </div>
</template>
<script>
import html2canvas from 'html2canvas';
import WebRTC from '@/components/Room/WebRTC.vue';
import setTimer2 from '@/components/Room/setTimer2.vue';
import setTimer3 from '@/components/Room/setTimer3.vue';
import setTimer4 from '@/components/Room/setTimer4.vue';
import axios from 'axios';
import { OpenVidu } from 'openvidu-browser';
import emojidata from 'emoji-mart-vue-fast/data/all.json';
import 'emoji-mart-vue-fast/css/emoji-mart.css';
import { Picker, EmojiIndex } from 'emoji-mart-vue-fast/src';
import { mapState, mapActions, mapMutations } from 'vuex';
import swal from 'sweetalert2';
// eslint-disable-next-line
import * as tf from '@tensorflow/tfjs';
import * as tmPose from '@teachablemachine/pose';

window.Swal = swal;

axios.defaults.headers.post['Content-Type'] = 'application/json';

const openvidu = 'openvidu';
const accounts = 'accounts';
const meetingroom = 'meetingroom';
const emojiIndex = new EmojiIndex(emojidata);
const emoji = 'emoji';
const exercise = 'exercise';

// eslint-disable-next-line
const audioBattle = new Audio(require('@/assets/music/battle.mp3'));

export default {
  name: 'CompetitionView',
  metaInfo: {
  },
  components: {
    WebRTC,
    Picker,
    setTimer2,
    setTimer3,
    setTimer4,
  },
  data() {
    return {
      CamOnOff: true,
      myExerciseTime: undefined, // 운동 시간 소요 (초)
      myRestTime: undefined, // 휴식 시간 소요 (초)
      // 타이머 셋팅
      timer: 3,
      temp_timer: 3,
      temp_timer_2: 3,
      take_photo_timer: null,
      is_take_photo: false,
      // 이모지 셋팅
      myemoji: '',
      emojiIndex,
      Emoji_ONOFF: null,
      emojiList: [],
      // 채팅
      myChat: '',
      allchattingList: [],
      chatONOFF: false,
      // openvidu 셋팅
      OV: undefined,
      session: undefined,
      video: false,
      mic: true,
      mainStreamManager: undefined,
      publisher: undefined,
      subscribers: [],
      // 방 설정
      startTime: undefined,
      photoDisplay: false,
      youtubeURL: '',
      lockroomcheck: false,
      isMaster: true,
      roommode: 'GAME',
      mode: ['FRIEND', 'YOUTUBE', 'GAME'],
      sessionId: null,
      connectionId: null,
      // roomName: '붙어보자!',
      gameName: ['스쿼트', '런지', '버피테스트'],
      roundGameName: '',
      round1Game: undefined,
      round2Game: undefined,
      round3Game: undefined,
      restTime: false,
      credentialsUser: {
        memberId: null,
        meetingRoomId: null,
      },
      newTagContent: '',
      myTags: [],
      mypictures: [],
      roomTime: null,
      isStarted: false,
      isExercising: false,
      isStartedGame: false,
      RoundStartTimer: null,
      // tm 영역
      webcam: undefined,
      URL: undefined,
      model: undefined,
      status: 'ready',
      check: false,
      check2: false,
      count: 0,
      gameType: undefined, // 1:squat, 2:lunge, 3:burpee
      ctx: undefined,
      // 각 운동의 카운트를 memberId와 함께 session.on으로 보내주고 데이터 받아서 저장한다.
      squatCount: 0,
      lungeCount: 0,
      burpeeCount: 0,
      myBestSquatCount: 0,
      myBestLungeCount: 0,
      myBestBurpeeCount: 0,
      // 운동이 끝나면 count는 서버에 보내고, counts에 따라 임의의 score를 저장한다.
      Score: 5,
    };
  },
  setup() {
  },
  created() {
    this.sessionId = this.$route.params.sessionId;
    this.joinSession(this.sessionId);
    this.credentialsUser.memberId = this.userInfo.id;
    this.credentialsUser.meetingRoomId = this.mySessionId;
    document.body.style.backgroundColor = '#eaf1ff';
  },
  moundted() {
  },
  unmounted() {
    this.leaveSession();
    document.body.style.backgroundColor = 'white';
    audioBattle.stop();
  },
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
    ...mapState(accounts, ['accessToken', 'userInfo', 'refreshToken', 'roomName', 'masterId']),
    ...mapState(openvidu, ['OPENVIDU_SERVER_URL', 'OPENVIDU_SERVER_SECRET']),
    ...mapState(meetingroom, ['mySessionId', 'meetingRoomList', 'camera', 'mic']),
    ...mapState(exercise, [
      'allSquatCountListSorted',
      'allSquatCountList',
      'allLungeCountListSorted',
      'allLungeCountList',
      'allBurpeeCountListSorted',
      'allBurpeeCountList',
      'allScoreListSorted',
      'allScoreList',
    ]),
    myExercisePoints() {
      if (!this.burpeeCount && !this.squatCount && !this.lungeCount) {
        return 0;
      }
      // eslint-disable-next-line
      if (this.allScoreList.length === 2) {
        if (this.Score === this.allScoreListSorted[0]) {
          return 15;
        }
        if (this.Score === this.allScoreListSorted[1]) {
          return 10;
        }
      } else if (this.allScoreList.length === 3) {
        if (this.Score === this.allScoreListSorted[0]) {
          return 20;
        }
        if (this.Score === this.allScoreListSorted[1]) {
          return 15;
        }
        if (this.Score === this.allScoreListSorted[2]) {
          return 10;
        }
      } else if (this.allScoreList.length === 4) {
        if (this.Score === this.allScoreListSorted[0]) {
          return 25;
        }
        if (this.Score === this.allScoreListSorted[1]) {
          return 20;
        }
        if (this.Score === this.allScoreListSorted[2]) {
          return 15;
        }
        if (this.Score === this.allScoreListSorted[3]) {
          return 10;
        }
      } else if (this.allScoreList.length === 5) {
        if (this.Score === this.allScoreListSorted[0]) {
          return 30;
        }
        if (this.Score === this.allScoreListSorted[1]) {
          return 25;
        }
        if (this.Score === this.allScoreListSorted[2]) {
          return 20;
        }
        if (this.Score === this.allScoreListSorted[3]) {
          return 15;
        }
        if (this.Score === this.allScoreListSorted[4]) {
          return 10;
        }
      } else if (this.allScoreList.length === 6) {
        if (this.Score === this.allScoreListSorted[0]) {
          return 35;
        }
        if (this.Score === this.allScoreListSorted[1]) {
          return 30;
        }
        if (this.Score === this.allScoreListSorted[2]) {
          return 25;
        }
        if (this.Score === this.allScoreListSorted[3]) {
          return 20;
        }
        if (this.Score === this.allScoreListSorted[4]) {
          return 15;
        }
        if (this.Score === this.allScoreListSorted[5]) {
          return 10;
        }
      }
      return 10;
    },
  },
  methods: {
    async tempInit() {
      this.gameType = 3;
      await this.setmodel();
      const flip = false;
      this.webcam = new tmPose.Webcam(400, 400, flip);
      await this.webcam.setup();
      await this.webcam.play();
      await window.requestAnimationFrame(this.loop);
      // // const canvas2 = this.webcam.canvas;
      const canvas2 = document.getElementById('canvasTM');
      canvas2.width = 400;
      canvas2.height = 400;
      this.ctx = canvas2.getContext('2d');
    },
    sendMyRecords() {
      axios({
        url: 'https://i7c202.p.ssafy.io:8282/api/user/compete',
        method: 'post',
        headers: {
          'X-AUTH-TOKEN': this.accessToken,
          'REFRESH-TOKEN': this.refreshToken,
        },
        data: {
          memberId: parseInt(this.credentialsUser.memberId, 10),
          score1: parseInt(this.$refs.webrtc.mySquat.userSquatCount, 10),
          score2: parseInt(this.$refs.webrtc.myLunge.userLungeCount, 10),
          score3: parseInt(this.$refs.webrtc.myBurpee.userBurpeeCount, 10),
        },
      });
    },
    sendMyPoints() {
      axios({
        url: `https://i7c202.p.ssafy.io:8282/api/user/point/${this.myExercisePoints}/${this.credentialsUser.memberId}`,
        method: 'put',
        headers: {
          'X-AUTH-TOKEN': this.accessToken,
        },
      }).then(() => {
        this.gameType = undefined;
      });
    },
    myBestRecord(memberId) {
      axios({
        url: `https://i7c202.p.ssafy.io:8282/api/user/compete/${memberId}`,
        method: 'get',
        headers: {
          'X-AUTH-TOKEN': this.accessToken,
          'REFRESH-TOKEN': this.refreshToken,
        },
      }).then((res) => {
        // eslint-disable-next-line
        this.myBestSquatCount = res.data.data[0];
        // eslint-disable-next-line
        this.myBestLungeCount = res.data.data[1];
        // eslint-disable-next-line
        this.myBestBurpeeCount = res.data.data[2];
      });
    },
    drawPose(pose) {
      if (this.webcam.canvas) {
        this.ctx.drawImage(this.webcam.canvas, 0, 0);
        if (pose) {
          const minPartConfidence = 0.5;
          tmPose.drawKeypoints(pose.keypoints, minPartConfidence, this.ctx);
          tmPose.drawSkeleton(pose.keypoints, minPartConfidence, this.ctx);
        }
      }
    },
    ...mapActions(emoji, ['changeEmojiList', 'removeEmojiList']),
    ...mapMutations(meetingroom, ['SET_SESSION_ID']),
    ...mapActions(meetingroom, [
      'enterMeetingRoom',
      'leaveMeetingRoom',
    ]),
    ...mapActions(exercise, [
      'changeExerciseName',
      'changeSquatCountList',
      'changeLungeCountList',
      'changeBurpeeCountList',
      'changeSquatCountListSorted',
      'changeLungeCountListSorted',
      'changeBurpeeCountListSorted',
      'resetAllCountList',
      'changeScoreList',
      'changeScoreListSorted',
    ]),
    joinSession(sessionNum) {
      this.SET_SESSION_ID(sessionNum);
      console.log(`sessionID = ${sessionNum}`);
      const requestDto = {
        accesstoken: this.accessToken,
        roomId: String(sessionNum),
      };
      this.enterMeetingRoom(requestDto);
      // --- Get an OpenVidu object ---
      this.OV = new OpenVidu();

      // --- Init a session ---
      this.session = this.OV.initSession();

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
              resolution: '260x180', // The resolution of your video
              frameRate: 60, // The frame rate of your video
              insertMode: 'APPEND',
              // How the video is inserted in the target element 'video-container'
              mirror: false, // Whether to mirror your local video or not
            });
            this.mainStreamManager = publisher;
            this.connectionId = this.mainStreamManager.stream.session.connection.connectionId;
            this.publisher = publisher;
            // --- Publish your stream ---
            this.session.publish(this.publisher);
          });
      });
      // 사용자 정의 함수 영역
      this.session.on('signal:myScore', (event) => {
        const sendScoreData = event.data.split(',');
        const obj = {
          connectionId: sendScoreData[0],
          userScore: sendScoreData[1],
        };
        this.changeScoreList(obj);
        this.changeScoreListSorted(obj);
      });

      this.session.on('signal:my-emoji', (event) => {
        const sendEmojiData = event.data.split(',');
        const obj = {
          connectionId: sendEmojiData[0],
          userEmoji: sendEmojiData[1],
        };
        this.changeEmojiList(obj);
      });

      this.session.on('signal:squatCount', (event) => {
        const sendSquatCountData = event.data.split(',');
        const obj = {
          connectionId: sendSquatCountData[0],
          allUserSquatCount: sendSquatCountData[1],
        };
        this.changeSquatCountList(obj);
        this.changeSquatCountListSorted();
      });

      this.session.on('signal:lungeCount', (event) => {
        const sendLungeCountData = event.data.split(',');
        const obj = {
          connectionId: sendLungeCountData[0],
          allUserLungeCount: sendLungeCountData[1],
        };
        this.changeLungeCountList(obj);
        this.changeLungeCountListSorted();
      });

      this.session.on('signal:burpeeCount', (event) => {
        const sendBurpeeCountData = event.data.split(',');
        const obj = {
          connectionId: sendBurpeeCountData[0],
          allUserBurpeeCount: sendBurpeeCountData[1],
        };
        this.changeBurpeeCountList(obj);
        this.changeBurpeeCountListSorted();
      });

      this.session.on('signal:my-chat', (event) => {
        const chatdata = event.data.split(',');
        const obj = {
          userId: String(chatdata[0]),
          userNickName: chatdata[1],
          userChat: chatdata[2],
        };
        this.allchattingList.push(obj);
        const chat = document.querySelector('#chattingList');
        chat.scrollTop = chat.scrollHeight;
      });

      this.session.on('signal:startround', () => {
        this.startround1();
      });
    },
    startround() {
      this.session
        .signal({
          data: '', // Any string (optional)
          to: [], // Array of Connection objects (optional. Broadcast to everyone if empty)
          type: 'startround', // The type of message (optional)
        });
    },
    startround1() {
      this.myBestRecord(this.credentialsUser.memberId);
      // eslint-disable-next-line
      const audio = new Audio(require('@/assets/music/round1.mp3'));
      audio.play();
      this.isExercising = true;
      this.isStarted = true;
      this.isStartedGame = true;
      this.round1Game = true;
      this.roundGameName = '버피테스트';
      setTimeout(() => {
        this.gameType = 3;
        this.tempInit();
        this.changeExerciseName(3);
        this.round1Game = false;
        // eslint-disable-next-line
        const audio = new Audio(require('@/assets/music/321.mp3'));
        audio.play();
        this.$refs.setTimer2.pauseTimer();
        document.getElementsByClassName('webrtcetc')[0].remove();
        document.getElementsByClassName('webrtcetc')[0].remove();
        document.getElementsByClassName('webrtcetc')[0].remove();
        document.getElementsByClassName('webrtcetc')[0].remove();
        document.getElementsByClassName('webrtcetc')[0].remove();
        // setTimeout(() => {
        const el = document.getElementsByClassName('webrtctag');
        switch (el.length) {
          case 2:
            document.getElementsByClassName('webrtctag')[0].style.width = '49%';
            document.getElementsByClassName('webrtctag')[0].style.height = '50%';
            document.getElementsByClassName('webrtctag')[0].style.backgroundColor = 'transparent';
            document.getElementsByClassName('webrtctag')[0].style.border = 'none';
            document.getElementsByClassName('webrtctag')[1].style.width = '49%';
            document.getElementsByClassName('webrtctag')[1].style.height = '50%';
            document.getElementsByClassName('webrtctag')[1].style.backgroundColor = 'transparent';
            document.getElementsByClassName('webrtctag')[1].style.border = 'none';
            break;
          case 3:
            document.getElementsByClassName('webrtctag')[0].style.width = '33%';
            document.getElementsByClassName('webrtctag')[0].style.height = '33%';
            document.getElementsByClassName('webrtctag')[0].style.backgroundColor = 'transparent';
            document.getElementsByClassName('webrtctag')[0].style.border = 'none';
            document.getElementsByClassName('webrtctag')[1].style.width = '33%';
            document.getElementsByClassName('webrtctag')[1].style.height = '33%';
            document.getElementsByClassName('webrtctag')[1].style.backgroundColor = 'transparent';
            document.getElementsByClassName('webrtctag')[1].style.border = 'none';
            document.getElementsByClassName('webrtctag')[2].style.width = '33%';
            document.getElementsByClassName('webrtctag')[2].style.height = '33%';
            document.getElementsByClassName('webrtctag')[2].style.backgroundColor = 'transparent';
            document.getElementsByClassName('webrtctag')[2].style.border = 'none';
            break;
          case 4:
            document.getElementsByClassName('webrtctag')[0].style.width = '33%';
            document.getElementsByClassName('webrtctag')[0].style.height = '33%';
            document.getElementsByClassName('webrtctag')[0].style.backgroundColor = 'transparent';
            document.getElementsByClassName('webrtctag')[0].style.border = 'none';
            document.getElementsByClassName('webrtctag')[1].style.width = '33%';
            document.getElementsByClassName('webrtctag')[1].style.height = '33%';
            document.getElementsByClassName('webrtctag')[1].style.backgroundColor = 'transparent';
            document.getElementsByClassName('webrtctag')[1].style.border = 'none';
            document.getElementsByClassName('webrtctag')[2].style.width = '33%';
            document.getElementsByClassName('webrtctag')[2].style.height = '33%';
            document.getElementsByClassName('webrtctag')[2].style.backgroundColor = 'transparent';
            document.getElementsByClassName('webrtctag')[2].style.border = 'none';
            document.getElementsByClassName('webrtctag')[3].style.width = '33%';
            document.getElementsByClassName('webrtctag')[3].style.height = '33%';
            document.getElementsByClassName('webrtctag')[3].style.backgroundColor = 'transparent';
            document.getElementsByClassName('webrtctag')[3].style.border = 'none';
            break;
          case 5:
            document.getElementsByClassName('webrtctag')[0].style.backgroundColor = 'transparent';
            document.getElementsByClassName('webrtctag')[0].style.border = 'none';
            document.getElementsByClassName('webrtctag')[1].style.backgroundColor = 'transparent';
            document.getElementsByClassName('webrtctag')[1].style.border = 'none';
            document.getElementsByClassName('webrtctag')[2].style.backgroundColor = 'transparent';
            document.getElementsByClassName('webrtctag')[2].style.border = 'none';
            document.getElementsByClassName('webrtctag')[3].style.backgroundColor = 'transparent';
            document.getElementsByClassName('webrtctag')[3].style.border = 'none';
            document.getElementsByClassName('webrtctag')[4].style.backgroundColor = 'transparent';
            document.getElementsByClassName('webrtctag')[4].style.border = 'none';
            break;
          case 6:
            document.getElementsByClassName('webrtcetc')[0].style.width = 0;
            document.getElementsByClassName('webrtcetc')[0].style.height = 0;
            document.getElementsByClassName('webrtctag')[0].style.backgroundColor = 'transparent';
            document.getElementsByClassName('webrtctag')[0].style.border = 'none';
            document.getElementsByClassName('webrtctag')[1].style.backgroundColor = 'transparent';
            document.getElementsByClassName('webrtctag')[1].style.border = 'none';
            document.getElementsByClassName('webrtctag')[2].style.backgroundColor = 'transparent';
            document.getElementsByClassName('webrtctag')[2].style.border = 'none';
            document.getElementsByClassName('webrtctag')[3].style.backgroundColor = 'transparent';
            document.getElementsByClassName('webrtctag')[3].style.border = 'none';
            document.getElementsByClassName('webrtctag')[4].style.backgroundColor = 'transparent';
            document.getElementsByClassName('webrtctag')[4].style.border = 'none';
            document.getElementsByClassName('webrtctag')[5].style.backgroundColor = 'transparent';
            document.getElementsByClassName('webrtctag')[5].style.border = 'none';
            break;
          default:
            break;
        }
      }, 2000);
      setTimeout(() => {
        this.isStarted = false;
        this.$refs.setTimer3.pauseTimer();
        audioBattle.play();
      }, 6000);
      setTimeout(() => {
        this.isExercising = false;
        this.restTime = true;
        // eslint-disable-next-line
        const audio = new Audio(require('@/assets/music/rest.mp3'));
        audio.play();
        this.$refs.setTimer4.pauseTimer();
        if (this.allBurpeeCountList.length === 2) {
          if (this.$refs.webrtc.myBurpeeRanking === 1) {
            this.Score += 10;
          } else if (this.$refs.webrtc.myBurpeeRanking === 2) {
            this.Score += 5;
          }
        } else if (this.allBurpeeCountList.length === 3) {
          if (this.$refs.webrtc.myBurpeeRanking === 1) {
            this.Score += 15;
          } else if (this.$refs.webrtc.myBurpeeRanking === 2) {
            this.Score += 10;
          } else if (this.$refs.webrtc.myBurpeeRanking === 3) {
            this.Score += 5;
          }
        } else if (this.allBurpeeCountList.length === 4) {
          if (this.$refs.webrtc.myBurpeeRanking === 1) {
            this.Score += 20;
          } else if (this.$refs.webrtc.myBurpeeRanking === 2) {
            this.Score += 15;
          } else if (this.$refs.webrtc.myBurpeeRanking === 3) {
            this.Score += 10;
          } else if (this.$refs.webrtc.myBurpeeRanking === 4) {
            this.Score += 5;
          }
        } else if (this.allBurpeeCountList.length === 5) {
          if (this.$refs.webrtc.myBurpeeRanking === 1) {
            this.Score += 25;
          } else if (this.$refs.webrtc.myBurpeeRanking === 2) {
            this.Score += 20;
          } else if (this.$refs.webrtc.myBurpeeRanking === 3) {
            this.Score += 15;
          } else if (this.$refs.webrtc.myBurpeeRanking === 4) {
            this.Score += 10;
          } else if (this.$refs.webrtc.myBurpeeRanking === 5) {
            this.Score += 5;
          }
        } else if (this.allBurpeeCountList.length === 6) {
          if (this.$refs.webrtc.myBurpeeRanking === 1) {
            this.Score += 30;
          } else if (this.$refs.webrtc.myBurpeeRanking === 2) {
            this.Score += 25;
          } else if (this.$refs.webrtc.myBurpeeRanking === 3) {
            this.Score += 20;
          } else if (this.$refs.webrtc.myBurpeeRanking === 4) {
            this.Score += 15;
          } else if (this.$refs.webrtc.myBurpeeRanking === 5) {
            this.Score += 10;
          } else if (this.$refs.webrtc.myBurpeeRanking === 6) {
            this.Score += 5;
          }
        }
        // this.webcam.stop();
        audioBattle.pause();
      }, 21000);
      setTimeout(() => {
        this.restTime = false;
        this.startround2();
        this.isExercising = true;
      }, 24000);
      // setTimeout(() => {
      // }, 46000);
    },
    startround2() {
      // eslint-disable-next-line
      const audio = new Audio(require('@/assets/music/round2.mp3'));
      audio.play();
      this.isStarted = true;
      this.round2Game = true;
      this.roundGameName = '런지';
      setTimeout(() => {
        this.changeExerciseName(2);
        this.round2Game = false;
        // eslint-disable-next-line
        const audio = new Audio(require('@/assets/music/321.mp3'));
        audio.play();
        this.$refs.setTimer2.pauseTimer();
      }, 2000);
      setTimeout(() => {
        this.gameType = 2;
        this.setmodel();
        // this.webcam.play();
        this.isStarted = false;
        this.$refs.setTimer3.pauseTimer();
        audioBattle.play();
      }, 6000);
      setTimeout(() => {
        this.isExercising = false;
        this.restTime = true;
        this.$refs.setTimer4.pauseTimer();
        // eslint-disable-next-line
        const audio = new Audio(require('@/assets/music/rest.mp3'));
        audio.play();
        if (this.allLungeCountList.length === 2) {
          if (this.$refs.webrtc.myLungeRanking === 1) {
            this.Score += 10;
          } else if (this.$refs.webrtc.myLungeRanking === 2) {
            this.Score += 5;
          }
        } else if (this.allLungeCountList.length === 3) {
          if (this.$refs.webrtc.myLungeRanking === 1) {
            this.Score += 15;
          } else if (this.$refs.webrtc.myLungeRanking === 2) {
            this.Score += 10;
          } else if (this.$refs.webrtc.myLungeRanking === 3) {
            this.Score += 5;
          }
        } else if (this.allLungeCountList.length === 4) {
          if (this.$refs.webrtc.myLungeRanking === 1) {
            this.Score += 20;
          } else if (this.$refs.webrtc.myLungeRanking === 2) {
            this.Score += 15;
          } else if (this.$refs.webrtc.myLungeRanking === 3) {
            this.Score += 10;
          } else if (this.$refs.webrtc.myLungeRanking === 4) {
            this.Score += 5;
          }
        } else if (this.allLungeCountList.length === 5) {
          if (this.$refs.webrtc.myLungeRanking === 1) {
            this.Score += 25;
          } else if (this.$refs.webrtc.myLungeRanking === 2) {
            this.Score += 20;
          } else if (this.$refs.webrtc.myLungeRanking === 3) {
            this.Score += 15;
          } else if (this.$refs.webrtc.myLungeRanking === 4) {
            this.Score += 10;
          } else if (this.$refs.webrtc.myLungeRanking === 5) {
            this.Score += 5;
          }
        } else if (this.allLungeCountList.length === 6) {
          if (this.$refs.webrtc.myLungeRanking === 1) {
            this.Score += 30;
          } else if (this.$refs.webrtc.myLungeRanking === 2) {
            this.Score += 25;
          } else if (this.$refs.webrtc.myLungeRanking === 3) {
            this.Score += 20;
          } else if (this.$refs.webrtc.myLungeRanking === 4) {
            this.Score += 15;
          } else if (this.$refs.webrtc.myLungeRanking === 5) {
            this.Score += 10;
          } else if (this.$refs.webrtc.myLungeRanking === 6) {
            this.Score += 5;
          }
        }
        // this.gameType = 4;
        // this.webcam.stop();
      }, 21000);
      setTimeout(() => {
        this.restTime = false;
        this.isExercising = true;
        this.startround3();
        audioBattle.pause();
      }, 24000);
      // setTimeout(() => {
      // }, 46000);
    },
    startround3() {
      // eslint-disable-next-line
      const audio = new Audio(require('@/assets/music/roundfinal.mp3'));
      audio.play();
      this.isStarted = true;
      this.round3Game = true;
      this.roundGameName = '스쿼트';
      setTimeout(() => {
        this.changeExerciseName(1);
        this.round3Game = false;
        // eslint-disable-next-line
        const audio = new Audio(require('@/assets/music/321.mp3'));
        audio.play();
        this.$refs.setTimer2.pauseTimer();
      }, 2000);
      setTimeout(() => {
        this.gameType = 1;
        this.setmodel();
        // this.webcam.play();
        this.isStarted = false;
        this.$refs.setTimer3.pauseTimer();
        audioBattle.play();
      }, 6000);
      setTimeout(() => {
        if (this.allSquatCountList.length === 2) {
          if (this.$refs.webrtc.mySquatRanking === 1) {
            this.Score += 10;
          } else if (this.$refs.webrtc.mySquatRanking === 2) {
            this.Score += 5;
          }
        } else if (this.allSquatCountList.length === 3) {
          if (this.$refs.webrtc.mySquatRanking === 1) {
            this.Score += 15;
          } else if (this.$refs.webrtc.mySquatRanking === 2) {
            this.Score += 10;
          } else if (this.$refs.webrtc.mySquatRanking === 3) {
            this.Score += 5;
          }
        } else if (this.allSquatCountList.length === 4) {
          if (this.$refs.webrtc.mySquatRanking === 1) {
            this.Score += 20;
          } else if (this.$refs.webrtc.mySquatRanking === 2) {
            this.Score += 15;
          } else if (this.$refs.webrtc.mySquatRanking === 3) {
            this.Score += 10;
          } else if (this.$refs.webrtc.mySquatRanking === 4) {
            this.Score += 5;
          }
        } else if (this.allSquatCountList.length === 5) {
          if (this.$refs.webrtc.mySquatRanking === 1) {
            this.Score += 25;
          } else if (this.$refs.webrtc.mySquatRanking === 2) {
            this.Score += 20;
          } else if (this.$refs.webrtc.mySquatRanking === 3) {
            this.Score += 15;
          } else if (this.$refs.webrtc.mySquatRanking === 4) {
            this.Score += 10;
          } else if (this.$refs.webrtc.mySquatRanking === 5) {
            this.Score += 5;
          }
        } else if (this.allSquatCountList.length === 6) {
          if (this.$refs.webrtc.mySquatRanking === 1) {
            this.Score += 30;
          } else if (this.$refs.webrtc.mySquatRanking === 2) {
            this.Score += 25;
          } else if (this.$refs.webrtc.mySquatRanking === 3) {
            this.Score += 20;
          } else if (this.$refs.webrtc.mySquatRanking === 4) {
            this.Score += 15;
          } else if (this.$refs.webrtc.mySquatRanking === 5) {
            this.Score += 10;
          } else if (this.$refs.webrtc.mySquatRanking === 6) {
            this.Score += 5;
          }
        }
        this.webcam.stop();
        this.sendScore();
        this.sendMyRecords();
        this.isExercising = false;
        audioBattle.pause();
        // eslint-disable-next-line
        const audio6 = new Audio(require('@/assets/music/setting.mp3'));
        audio6.play();
        swal.fire({
          icon: 'info',
          title: '획득 포인트 정산중입니다...',
          text: '잠시만 기다려주세요!',
        });
      }, 21000);
      setTimeout(() => {
        this.sendMyPoints();
      }, 22000);
      setTimeout(() => {
        // eslint-disable-next-line
        const audio = new Audio(require('@/assets/music/record.mp3'));
        audio.play();
        swal.fire({
          icon: 'success',
          // eslint-disable-next-line
          showDenyButton: true,
          confirmButtonText: '머무르기',
          denyButtonText: '나가기',
          html: `방 제목 : <strong>${this.roomName}</strong> | 참여자 : <strong>${this.subscribers.length + 1} 명</strong><br>
          <br>
          <strong>${this.userInfo.nick}</strong>님의 기록입니다.<br>
          <br>
          <strong>#Round 1. </strong>Burpee : <strong>${this.$refs.webrtc.myBurpee.userBurpeeCount}회</strong> / 최고 기록 : <strong>${this.myBestBurpeeCount}회</strong><br>
          <strong>#Round 2. </strong>Lunge  : <strong>${this.$refs.webrtc.myLunge.userLungeCount}회</strong> / 최고 기록 : <strong>${this.myBestLungeCount}회</strong><br>
          <strong>#Round 3. </strong>Squat  : <strong>${this.$refs.webrtc.mySquat.userSquatCount}회</strong> / 최고 기록 : <strong>${this.myBestSquatCount}회</strong><br>
          <br>
          이번 경쟁의 포인트 변동 : <strong><U>${this.myExercisePoints}</U></strong> point<br>
          <br>
          <div style="color:gray;">기록이 저장되었습니다.</div>`,
        }).then((result) => {
          if (result.isDenied) {
            this.leaveSession();
          } else if (result.isConfirmed) {
            this.resetAllCountList();
          }
        });
        this.webcam.stop();
        this.isStartedGame = true;
      }, 23000);
    },
    sendScore() {
      this.session
        .signal({
          data: `${this.connectionId},${this.Score}`,
          to: [],
          type: 'myScore',
        });
    },
    sendEmoji() {
      this.session
        .signal({
          data: `${this.connectionId},${this.myemoji}`,
          to: [],
          type: 'my-emoji',
        });
    },
    sendMassage() {
      this.session
        .signal({
          data: `${this.credentialsUser.memberId},${this.userInfo.nick},${this.myChat}`,
          to: [],
          type: 'my-chat',
        })
        .then(() => {
          const resetText = document.querySelector('#mychat');
          resetText.value = '';
          const chat = document.querySelector('#chattingList');
          chat.scrollTop = chat.scrollHeight + 1000000;
          this.myChat = '';
        });
    },
    async leaveSession() {
      this.resetAllCountList();
      this.changeExerciseName(0);
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
      this.SET_SESSION_ID('');
      this.$router.push('/');
    },
    getToken(mySessionId) {
      return this.createSession(mySessionId).then((sessionId) => this.createToken(sessionId));
    },
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
              if (
                window.confirm(
                  `No connection to OpenVidu Server. This may be a certificate error at ${this.OPENVIDU_SERVER_URL}\n\nClick OK to navigate and accept it. If no certificate warning is shown, then check that your OpenVidu Server is up and running at "${this.OPENVIDU_SERVER_URL}"`,
                )
              ) {
                window.location.assign(`${this.OPENVIDU_SERVER_URL}/accept-certificate`);
              }
              reject(error.response);
            }
          });
      });
    },
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
      const chat = document.querySelector('#chattingList');
      chat.scrollTop = chat.scrollHeight + 10000000;
    },
    chaton() {
      this.chatONOFF = true;
      const chat = document.querySelector('#chattingList');
      chat.scrollTop = chat.scrollHeight + 10000000;
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
      this.temp_timer2 = this.timer;
      this.is_take_photo = true;
      // eslint-disable-next-line
      const audio3 = new Audio(require('@/assets/music/10seconds.mp3'));
      audio3.play();
      setTimeout(() => {
        audio3.pause();
      }, this.timer * 1000);
      this.take_photo_timer = setInterval(() => {
        this.timer -= 1;
        this.temp_timer_2 -= 1;
        if (this.timer === 1) {
          setTimeout(() => {
            const el = document.querySelector('#take_photo_WebRTC');
            html2canvas(el).then((canvas) => {
              this.mypictures.unshift(canvas.toDataURL('image/png', 1.0));
              if (this.mypictures.length >= 3) { this.mypictures.pop(); }
            });
          }, 800);
        }
        if (this.timer === 0) {
          const el = document.querySelector('#take_photo_WebRTC');
          el.style.opacity = '0';
          this.temp_timer_2 = '';
          // eslint-disable-next-line
          const audio2 = new Audio(require('@/assets/music/takePhoto.mp3'));
          audio2.play();
        } // 0초에 찰칵 사인 -> 소리가 나오는 시간을 체크해야겠네
        if (this.timer === -1) {
          this.temp_timer_2 = '';
          this.photoDisplay = true;
          setTimeout(() => {
            this.photoDisplay = false;
          }, 2000);
          clearInterval(this.take_photo_timer);
          setTimeout(() => {
            this.is_take_photo = false;
            this.timer = this.temp_timer;
          }, 2000);
        }
        // if (this.timer === -1) {
        //   const el = document.querySelector('#take_photo_WebRTC');
        //   html2canvas(el).then((canvas) => {
        //     this.mypictures.unshift(canvas.toDataURL('image/png', 1.0));
        //     this.photoDisplay = true;
        //     setTimeout(() => {
        //       this.photoDisplay = false;
        //     }, 2000);
        //   });
        //   if (this.mypictures.length >= 3) { this.mypictures.pop(); }
        //   clearInterval(this.take_photo_timer);
        //   setTimeout(() => {
        //     this.is_take_photo = false;
        //     this.timer = this.temp_timer;
        //   }, 2000);
        // }
      }, 1000);
      this.temp_timer_2 = this.temp_timer;
    },
    set_timer_3() { this.timer = 3; },
    set_timer_5() { this.timer = 5; },
    set_timer_10() { this.timer = 10; },
    lockroom() {
      this.lockroomcheck = !this.lockroomcheck;
    },
    exerciseJournalSubmit(event) {
      event.preventDefault();
    },
    async setmodel() {
      switch (this.gameType) {
        case 1: // 스쿼트
          this.URL = 'https://teachablemachine.withgoogle.com/models/mtTsf3dWh/';
          break;
        case 2: // 런지
          this.URL = 'https://teachablemachine.withgoogle.com/models/rX6NKe6V_/';
          break;
        case 3: // 버피
          this.URL = 'https://teachablemachine.withgoogle.com/models/bLxzzIAS3/';
          break;
        default:
          break;
      }
      const modelURL = `${this.URL}model.json`;
      const metadataURL = `${this.URL}metadata.json`;
      this.model = Object.freeze(await tmPose.load(modelURL, metadataURL));
    },

    async init() {
      await this.setmodel();
      const flip = false;
      this.webcam = new tmPose.Webcam(800, 700, flip);
      await this.webcam.setup();
      await this.webcam.play();
      await window.requestAnimationFrame(this.loop);
      // const canvas2 = this.webcam.canvas;
      const canvas2 = document.getElementById('canvasTM');
      canvas2.width = 800;
      canvas2.height = 700;
      this.ctx = canvas2.getContext('2d');
    },
    async loop() {
      await this.webcam.update();
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
      await window.requestAnimationFrame(this.loop);
    },

    async squatpredict() {
      const { pose, posenetOutput } = await this.model.estimatePose(
        this.webcam.canvas,
      );
      const prediction = await this.model.predict(posenetOutput);
      if (prediction[1].probability.toFixed(2) > 0.99) { // 스쿼트
        if (this.check && !this.video && !this.restTime) {
          this.squatCount += 1;
          console.log('squatCount', this.squatCount);
          this.session
            .signal({
              data: `${this.connectionId},${this.squatCount}`, // Any string (optional)
              to: [], // Array of Connection objects (optional. Broadcast to everyone if empty)
              type: 'squatCount', // The type of message (optional)
            })
            .then(() => {
              // this.setState({ check: false });
              this.check = false;
            })
            .catch(() => {});
        }
        this.status = 'squat';
      } else if (prediction[0].probability.toFixed(2) > 0.99) { // 서 있는 자세
        this.status = 'ready';
        this.check = true;
      }
      this.drawPose(pose);
    },

    async lungepredict() {
      const { pose, posenetOutput } = await this.model.estimatePose(
        this.webcam.canvas,
      );
      const prediction = await this.model.predict(posenetOutput);
      if (prediction[1].probability.toFixed(2) > 0.99) { // 런지
        if (this.check && !this.video && !this.restTime) {
          this.lungeCount += 1;
          console.log('lungeCount', this.lungeCount);
          this.session
            .signal({
              data: `${this.connectionId},${this.lungeCount}`, // Any string (optional)
              to: [], // Array of Connection objects (optional. Broadcast to everyone if empty)
              type: 'lungeCount', // The type of message (optional)
            })
            .then(() => {
              // this.setState({ check: false });
              this.check = false;
            })
            .catch(() => {});
        }
        this.status = 'lunge';
        // this.setState({ status: 'ready' });
      } else if (prediction[0].probability.toFixed(2) > 0.99) { // 서 있는 자세
        this.status = 'ready';
        this.check = true;
      }
      this.drawPose(pose);
    },

    async burpeepredict() {
      const { pose, posenetOutput } = await this.model.estimatePose(
        this.webcam.canvas,
      );
      const prediction = await this.model.predict(posenetOutput);
      if (prediction[2].probability.toFixed(2) > 0.99) { // 서 있는 자세
        if (this.check && this.check2 && !this.video && !this.restTime) {
          this.burpeeCount += 1;
          console.log('burpeeCount', this.burpeeCount);
          this.session
            .signal({
              data: `${this.connectionId},${this.burpeeCount}`, // Any string (optional)
              to: [], // Array of Connection objects (optional. Broadcast to everyone if empty)
              type: 'burpeeCount', // The type of message (optional)
            })
            .then(() => {
              this.check = false;
              this.check2 = false;
            })
            .catch(() => {});
        }
        this.status = 'go';
      } else if (prediction[1].probability.toFixed(2) > 0.99) { // 쪼그려 앉아 있는 자세
        this.status = 'ready';
        this.check = true;
      } else if (prediction[0].probability.toFixed(2) > 0.99) { // 엎드려 있는 자세
        this.status = 'set';
        this.check2 = true;
      }
      this.drawPose(pose);
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

.menu_icon4 {
  width:100px;
}

.emoji_position {
  position:fixed;
  bottom: 100px;
  left: 20px;
  /* z-index:1000; */
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
  bottom: 29px;
  right: 30px;
}

.chatimg {
  width:50px;
}

.achat {
  position:fixed;
  border: 1px solid white;
  border-radius: 30px;
  width:320px;
  height:600px;
  background: rgb(206,223,255);
  background: radial-gradient(circle, rgba(206,223,255,1) 0%,
  rgba(78,138,255,1) 95%, rgba(39,76,149,1) 100%);
  bottom: 100px;
  right: 20px;
}

.achat-content {
  position:fixed;
  border: 1px solid white;
  width:320px;
  height:490px;
  background-color: #c5a180;
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

#mychat:focus {
  outline:1px solid #4e8aff;
}

.mychatting {position:relative; margin: 50px; padding: 20px; width:180px; height:90px;
border:1px solid #4e8aff; border-radius: 10px; background-color:  #4e8aff;}
.mychatting:after {content:""; position: absolute; top: 21px; right: -28px; border-left: 28px
solid  #4e8aff; border-top: 10px solid transparent; border-bottom: 10px solid transparent;}

.yourchatting {position:relative; margin: 50px; padding: 20px; width:180px; height:90px;
border:1px solid #cedfff; border-radius: 10px; background-color: #cedfff;}
.yourchatting:after {content:""; position: absolute; top: 21px; left: -28px; border-right: 28px
solid #cedfff; border-top: 10px solid transparent; border-bottom: 10px solid transparent;}

.webrtcetc {
  /* width: 30%;
  height:100%; */
  width:520px;
  height:360px;
  text-align:center;
  vertical-align: middle;
  background-image: url('../../assets/icon/Loading2.gif');
  background-position: center;
  background-size: 30px 30px;
  background-repeat: no-repeat;
  border-radius: 20px;
  border: 4px solid #4e8aff;
  background-color:white;
  transition-property: width, height;
  transition-duration: 2s;
}

.Emoji {
  position:fixed;
  top:100px;
  left:100px;
  /* z-index: 103; */
}

.m0p0 {
  margin:0;
  padding:0;
}

.row { display: flex; }
.row > * { margin: auto; }

#take_photo_background {
    background-color: black;
    position: fixed;
    overflow: hidden;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    /* z-index: 600; */
    opacity: 0.7;
    font-size:599px;
}

#take_photo_WebRTC {
  background-color:transparent;
  transition-property: opacity;
  transition-duration: 2s;
  transition-timing-function: ease;
  position: fixed;
  width: 60%;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  /* height: 80%; */
  /* z-index: 601; */
  opacity: 1;
}

#take_photo_WebRTC_photo {
  background-color:transparent;
  position: fixed;
  width: 60%;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  /* height: 80%; */
  /* z-index: 602; */
}

#take_photo_WebRTC_warning {
  position:fixed;
  font-size:30px;
  top: 5%;
  left: 50%;
  transform: translate(-50%, 0);
  color:white;
  padding:3px;
  margin:3px;
  background-color:#4e8aff;
  /* z-index:603; */
  border-radius: 10px;
}

#take_photo_timer {
    position:fixed;
    top: 0%;
    left: 0%;
    width: 100%;
    height: 100%;
    /* z-index: 602; */
    font-size:300px;
}

.after-exercise {
    width:600px;
    margin:auto;
}

.mybutton {
  padding: 0;
  border: none;
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

.md-title2 {
  font-size: 24px;
  font-weight: 800;
  margin: 0;
}

.mycontainer {
  width: 100%;
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

.mybtn1 {
  background-color:transparent;
  border:none;
  position:fixed;
  top:10%;
  left:30px;
  /* z-index: 500; */
}

.mybtn2 {
  background-color:transparent;
  border:none;
  position:fixed;
  top:20%;
  left:30px;
  /* z-index: 500; */
}

.mybtn3 {
  background-color:transparent;
  border:none;
  position:fixed;
  top:30%;
  left:30px;
  /* z-index: 500; */
}

.mybtn4 {
  background-color:transparent;
  border:none;
  position:fixed;
  top:40%;
  left:30px;
  /* z-index: 500; */
}

.mybtn5 {
  background-color:transparent;
  border:none;
  position:fixed;
  top: 16px;
  right: 120px;
  /* z-index: 500; */
}

.mybtn6 {
  background-color:transparent;
  border:none;
  position:fixed;
  top: 40px;
  right: 30px;
  /* z-index: 500; */
}

.mybtn7 {
  color:gray;
  opacity: 0.5;
  background-color:transparent;
  border:none;
  position:fixed;
  top: 37px;
  font-size:2vw;
  right: 150px;
  font-family: 'LeferiPoint-BlackObliqueA';
  /* z-index: 500; */
}

.setTimer2position {
  position:fixed;
  top:90px;
  right:30px;
  /* z-index:500; */
}

.startbtnpositionsub {
  position:relative;
}

.startbtnposition {
  position: absolute;
  top: 50%;
  right:20px;
  transform: translate(0, -50%);
}

.checkboxposition {
  position:relative;
}

.secretcheckbox {
  position: absolute;
  top: 50%;
  right:20px;
  transform: translate(0, -50%);
  /* line-height: 0.8; */
}

::placeholder {
  color:white;
  text-align: center;
}

.bytepositionsub {
  position: relative;
}

.btyeposition {
  position: absolute;
  bottom:15px;
  right:35px;
}

.viewsetting {
  margin:auto;
}

.myBackGroundSetting {
  background-color: #000000;
  position:fixed;
  top: 0%;
  left: 0%;
  width: 100%;
  height: 100%;
  /* z-index:900; */
  opacity: 0.7;
}

.roundGame {
  font-size:5rem;
  color:white;
  background-color:#4e8aff;
  position: absolute;
  display:flex;
  align-items: center;
  justify-content: center;
  width:100%;
  height:40%;
  top: 50%;
  left:0%;
  transform: translate(0, -50%);
  opacity: 0.9;
}
.roundGame2 {
  font-size:1rem;
  color:gray;
  position: absolute;
  display:flex;
  align-items: center;
  justify-content: center;
  width:100%;
  height:40%;
  top: 58%;
  left:0%;
  transform: translate(0, -50%);
  opacity: 0.9;
}
.roundGame3 {
  font-size:1rem;
  color:gray;
  position: absolute;
  display:flex;
  align-items: center;
  justify-content: center;
  width:100%;
  height:40%;
  top: 64%;
  left:0%;
  transform: translate(0, -50%);
  opacity: 0.9;
}
</style>
