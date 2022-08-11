<template>
  <div class="d-flex justify-content-center" style="width: 100%; height:100vh">
    <div style="width:1600px;" class="viewsetting">
      <!-- eslint-disable-next-line -->
      <div class="d-flex justify-content-center align-items-center" style="width: 100%; height: 80px;">
        <h3 class="game-name m-0">{{ gameName }}</h3>
      </div>
      <!-- 세션 -->
      <div id="session" v-if="session">
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
          <!-- eslint-disable-next-line -->
          <div v-for="sub in subscribers" :key="sub.stream.connection.connectionId"> {{sub.stream.connection.connectionId}} </div>
        </div>
      </div>
      <!-- 운동 종료 모달 -->
      <div>
        <div class="d-flex justify-content-center align-items-center">
          <button
          v-b-modal.after-exercise-modal class="btn btn-outline-secondary m-2">
            <img class="menu_icon1" src="@/assets/icon/room_end.png" alt="mic_off">
            운동 종료
          </button>
          <!-- eslint-disable-next-line -->
          <b-modal id="after-exercise-modal" size="xl" no-close-on-esc no-close-on-backdrop hide-footer hide-header>
            <form @submit.prevent="sendRecord(credentials, credentialsUser)">
              <div class="checkboxposition">
                <h1 class="title text-center mt-4">🏃‍♂️운동 일지</h1>
                <label for="my-checkbox m-0" class="secretcheckbox d-flex align-items-center">
                  <input v-model="credentials.secret" type="checkbox">
                  <div v-if="credentials.secret">
                    <!-- eslint-disable-next-line -->
                    &ensp;&ensp;<span style="color:#de7474;">비공개</span>&ensp;<img class="menu_icon1" src="@/assets/icon/lock.png" alt="">
                  </div>
                  <div  v-if="!credentials.secret">
                    <!-- eslint-disable-next-line -->
                    &ensp;&ensp;&ensp;&ensp;<span style="color:#4e8aff;">공개</span>&ensp;<img class="menu_icon1" src="@/assets/icon/unlock.png" alt="">
                  </div>
                  <!-- eslint-disable-next-line -->
                </label>
              </div>
              <div>
                <br>
                <!-- 운동일지, 사진 -->
                <div>
                  <div class="md-title2 text-center">📷오늘의 운동 사진</div>
                  <br>
                    <!-- eslint-disable-next-line -->
                </div>
                <div class="row d-flex align-items-start justify-content-center">
                  <!-- eslint-disable-next-line -->
                  <button @click.prevenet="pickmyImg(`${mypicture}`)" v-for="(mypicture, i) in mypictures" :key="i" class="col-4 m0p0" style="padding:0px; margin:0px; width:330px;">
                    <img :src="mypicture" alt="img" style="width:328px;">
                  </button>
                </div>
                <!-- 메모 남기기 버튼 -->
                <div>
                  <div class="md-title">
                    <div class="text-center">운동 종류</div>
                    <div>
                      <div class="d-flex justify-content-center" style="flex-flow:row wrap;">
                        <!-- eslint-disable-next-line -->
                        <button @click.prevent="myTagList('#유산소')" id="#유산소" class="mybutton btn btn-secondary m-2 p-2">&ensp;#유산소&ensp;</button>
                        <!-- eslint-disable-next-line -->
                        <button @click.prevent="myTagList('#헬스')" id="#헬스" class="mybutton btn btn-secondary m-2 p-2">&ensp;#헬스&ensp;</button>
                        <!-- eslint-disable-next-line -->
                        <button @click.prevent="myTagList('#스트레칭')" id="#스트레칭" class="mybutton btn btn-secondary m-2 p-2">&ensp;#스트레칭&ensp;</button>
                        <!-- eslint-disable-next-line -->
                        <button @click.prevent="myTagList('#맨몸운동')" id="#맨몸운동" class="mybutton btn btn-secondary m-2 p-2">&ensp;#맨몸운동&ensp;</button>
                        <!-- eslint-disable-next-line -->
                        <button @click.prevent="myTagList('#요가')" id="#요가" class="mybutton btn btn-secondary m-2 p-2">&ensp;#요가&ensp;</button>
                        <!-- eslint-disable-next-line -->
                        <button @click.prevent="myTagList('#필라테스')" id="#필라테스" class="mybutton btn btn-secondary m-2 p-2">&ensp;#필라테스&ensp;</button>
                        <!-- eslint-disable-next-line -->
                        <button @click.prevent="myTagList('#기타')" id="#기타" class="mybutton btn btn-secondary m-2 p-2">&ensp;#기타&ensp;</button>
                      </div>
                      <!-- eslint-disable-next-line -->
                      <div class="d-flex justify-content-center" style="flex-flow:row wrap;">
                        <!-- eslint-disable-next-line -->
                        <div v-for="(myTag, i) in myTags" :key="i" class="m-0 d-flex">
                          <!-- eslint-disable-next-line -->
                          <button @click.prevent="myTagList(`${myTag}`)" v-bind:id="`${myTag}`" class="mybutton btn btn-secondary m-2 p-2">&ensp;#{{myTag}}&ensp;</button><button @click.prevent="deletemyTagList(`${myTag}`)" class="mybutton">X</button>
                        </div>
                        <form @submit.prevent="newtag(newTagContent)">
                            <!-- eslint-disable-next-line -->
                          <button class="mybutton btn btn-secondary m-2 p-2 d-flex align-items-center" type="submit">
                            <!-- eslint-disable-next-line -->
                            <label for="my-tag" class="m-0 p-0" style="width:100% - 120px;">
                              <!-- eslint-disable-next-line -->
                              <input id="myTagInput" v-model="newTagContent" type="text" style="text-align: center; width:100px; color:white; border: none; border-bottom:1px solid white; background: transparent;" placeholder="#나만의 태그">
                            </label>&ensp;추가&ensp;
                          </button>
                        </form>
                      </div>
                    </div>
                  </div>
                </div>
                <div>
                  <div class="md-title text-center">메모 남기기</div>
                  <!-- eslint-disable-next-line -->
                  <div class="bytepositionsub d-flex justify-content-center" style="width:100%;">
                    <label for="exerciseMemo" class="d-flex justify-content-center">
                      <!-- eslint-disable-next-line -->
                      <textarea v-model="credentials.recordMemo" id="exerciseMemo" rows="4" style="width:95%" @keyup="fn_checkByte(this)"></textarea>
                    </label>
                    <div class="btyeposition">(<span id="nowByte">0</span>/200bytes)</div>
                  </div>
                </div>
                <div class="d-flex justify-content-center">
                  <!-- eslint-disable-next-line -->
                  <b-button type="submit" class="mybutton btn btn-success m-2 p-2">&ensp;작성 완료&ensp;</b-button>
                </div>
              </div>
              <br>
              <div class="d-flex justify-content-center">
                <!-- eslint-disable-next-line -->
                <div style="width: 95%; text-align: right; color:red;">강제 종료 / 새로 고침하면 기록이 저장되지 않아요!</div>
              </div>
              <div class="text-center">{{credentials}}</div>
            </form>
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
      <!-- 이모티콘 영역 -->
      <div class="emoji_position" v-if="Emoji_ONOFF">
        <div class="row">
          <!-- apple, google, twitter, facebook -->
          <Picker :data="emojiIndex" set="twitter" @select="showEmoji" />
        </div>
      </div>
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
            <div v-for="(item, i) in allchattingList" :key="i">
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
      <!-- 버튼 -->
      <div>
        <button @click="open_emoji" class="open_emoji">
          <img class="menu_icon2" src="@/assets/icon/emoji.png" alt="emoji">
        </button>
        <button class="mybtn1" @click="mic_off" v-if="mic">
          <img class=" menu_icon2" src="@/assets/icon/mic_off.png" alt="mic_on">
        </button>
        <button class="mybtn1" @click="mic_on" v-if="!mic">
          <img class="menu_icon2" src="@/assets/icon/mic_on.png" alt="mic_off">
        </button>
        <button class="mybtn2" @click="video_off" v-if="video">
          <img class="menu_icon2" src="@/assets/icon/video_off.png" alt="video_on">
        </button>
        <button class="mybtn2" @click="video_on" v-if="!video">
          <img class="menu_icon2" src="@/assets/icon/video_on.png" alt="video_off">
        </button>
        <button class="mybtn3" @click="take_photo">
          <img class="menu_icon2" src="@/assets/icon/photo.png" alt="photo">
        </button>
        <button class="mybtn6" @click="roomOut">
          <img class="menu_icon2" src="@/assets/icon/roomout.png" alt="leaveSession">
        </button>
        <button v-if="isStarted" class="mybtn5" @click="time">
          <img class="menu_icon4" src="@/assets/icon/start.png" alt="Start">
        </button>
        <!-- eslint-disable-next-line -->
        <button class="mybtn4 dropdown dropright dropright-toggle-no-caret text-decoration-none" type="button" data-bs-toggle="dropdown" aria-expanded="false">
          <img class="menu_icon2" src="@/assets/icon/phototimer.png" alt="photo">
        </button>
        <ul class="dropdown-menu" role="menu" style="width:50px;">
        <!-- eslint-disable-next-line -->
          <li role="presentation" style="width:100%;"><button @click="set_timer_3" class="dropdown-item text-center" type="button" target="_self">타이머 : 3초</button></li>
        <!-- eslint-disable-next-line -->
          <li role="presentation" style="width:100%;"><button @click="set_timer_5" class="dropdown-item text-center" type="button" target="_self">타이머 : 5초</button></li>
        <!-- eslint-disable-next-line -->
          <li role="presentation" style="width:100%;"><button @click="set_timer_10" class="dropdown-item text-center" type="button" target="_self">타이머 : 10초</button></li>
        </ul>
      </div>
      <div class="setTimer2position">
        <setTimer2 ref="setTimer2" />
      </div>
    </div>
  </div>
</template>
<script>
import html2canvas from 'html2canvas';
import WebRTC from '@/components/Room/WebRTC.vue';
import WebRTCPhoto from '@/components/Room/WebRTCPhoto.vue';
import setTimer2 from '@/components/Room/setTimer2.vue';
import axios from 'axios';
import { OpenVidu } from 'openvidu-browser';
import emojidata from 'emoji-mart-vue-fast/data/all.json';
import 'emoji-mart-vue-fast/css/emoji-mart.css';
import { Picker, EmojiIndex } from 'emoji-mart-vue-fast/src';
import { mapState, mapActions, mapMutations } from 'vuex';
import swal from 'sweetalert2';
// import { onUnmounted } from 'vue';

window.Swal = swal;

axios.defaults.headers.post['Content-Type'] = 'application/json';

const openvidu = 'openvidu';
const accounts = 'accounts';
const meetingroom = 'meetingroom';
const emojiIndex = new EmojiIndex(emojidata);
const emoji = 'emoji';
// const record = 'record';

const today = new Date();
const year = today.getFullYear();
const month = today.getMonth() + 1;
const day = today.getDate();
// eslint-disable-next-line
const format = year + '-' + (('00' + month.toString()).slice(-2)) + '-' +
((`00${day.toString()}`).slice(-2));

export default {
  name: 'CompetitionView',
  components: {
    WebRTC,
    WebRTCPhoto,
    Picker,
    setTimer2,
  },
  data() {
    return {
      // 타이머 셋팅
      timer: 3,
      temp_timer: 3,
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
      mic: false,
      mainStreamManager: undefined,
      publisher: undefined,
      subscribers: [],
      // 방 설정
      youtubeURL: '',
      lockroomcheck: false,
      isMaster: true,
      roommode: 'GAME',
      mode: ['FRIEND', 'YOUTUBE', 'GAME'],
      sessionId: null,
      // roomName: '붙어보자!',
      gameName: ['팔굽혀펴기', '런지', '버피테스트'],
      credentials: {
        recordImg: '',
        recordDatetime: format,
        recordMemo: null,
        secret: false,
        // recordimg: null,
        tagList: [],
      },
      credentialsUser: {
        memberId: null,
        meetingRoomId: null,
      },
      newTagContent: '',
      myTags: [],
      mypictures: [],
      roomTime: null,
    };
  },
  setup() {
    // onUnmounted(() => {
    //   // alert('hello');
    //   this.leavepeople();
    // });
  },
  created() {
    this.sessionId = this.$route.params.sessionId;
    this.joinSession(this.sessionId);
    this.credentialsUser.memberId = this.userInfo.id;
    this.credentialsUser.meetingRoomId = this.mySessionId;
  },
  moundted() {
  },
  unmounted() {
    this.leaveSession();
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
    ...mapState(accounts, ['accessToken', 'userInfo']),
    ...mapState(openvidu, ['OPENVIDU_SERVER_URL', 'OPENVIDU_SERVER_SECRET']),
    ...mapState(meetingroom, ['myRoomName', 'mySessionId', 'meetingRoomList', 'camera', 'mic']),
    // ...openviduHelper.mapState(["OPENVIDU_SERVER_URL", "OPENVIDU_SERVER_SECRET"]),
    // ...meetingRoomHelper.mapState(["sessionID", "meetingRoomList"]),
  },
  methods: {
    pickmyImg(Img) {
      this.credentials.recordImg = Img;
    },
    roomOut() {
      // if (!this.roomTime) {
      //   swal.fire({
      //     icon: 'warning',
      //     title: '알림',
      //     text: '최대 200bytes까지 입력가능해요!',
      //   });
      // }
      this.leaveSession();
    },
    // textarea 바이트 수 체크하는 함수
    fn_checkByte() {
      const maxByte = 200; // 최대 200바이트
      const textVal = document.getElementById('exerciseMemo').value; // 입력한 문자
      const textLen = textVal.length; // 입력한 문자수
      let totalByte = 0;

      for (let i = 0; i < textLen; i += 1) {
        const eachChar = textVal.charAt(i);
        console.log(eachChar);
        const uniChar = escape(eachChar); // 유니코드 형식으로 변환
        console.log(uniChar);
        if (uniChar.length > 4) {
          // 한글 : 2Byte
          totalByte += 2;
        } else {
          // 영문,숫자,특수문자 : 1Byte
          totalByte += 1;
        }
      }
      if (totalByte > maxByte) {
        swal.fire({
          icon: 'warning',
          title: '알림',
          text: '최대 200bytes까지 입력가능해요!',
        });
        document.getElementById('nowByte').innerText = totalByte;
        document.getElementById('nowByte').style.color = 'red';
      } else {
        document.getElementById('nowByte').innerText = totalByte;
        document.getElementById('nowByte').style.color = 'green';
      }
    },
    newtag(newTagContent) {
      if (this.myTags.length >= 5) {
        swal.fire({
          icon: 'warning',
          title: '알림',
          text: '추가 태그는 5개까지 가능해요!',
          closeOnEsc: true,
        });
      } else {
        if (newTagContent !== '') {
          if (this.myTags.indexOf(newTagContent.replace(' ', '').replace('#', '')) < 0) {
            this.myTags.push(newTagContent.replace(' ', '').replace('#', ''));
            this.newTagContent = '';
            this.myTagList(this.myTags[this.myTags.length - 1]);
          }
          this.newTagContent = '';
        }
        this.newTagContent = '';
      }
    },
    deletemyTagList(newTagContent) {
      this.myTags.splice(this.myTags.indexOf(newTagContent.replace(' ', '').replace('#', '')), 1);
      if (this.credentials.tagList.indexOf(newTagContent.replace(' ', '').replace('#', '')) >= 0) {
        this.credentials.tagList.splice(this.credentials.tagList.indexOf(newTagContent.replace(' ', '').replace('#', '')), 1);
      }
    },
    myTagList(tag) {
      // this.credentials.tagList.push(tag);
      // console.log(this.credentials.tagList.indexOf(tag));
      if (this.credentials.tagList.indexOf(tag) >= 0) {
        document.getElementById(`${tag}`).classList.add('btn-secondary');
        document.getElementById(`${tag}`).classList.remove('btn-primary');
        this.credentials.tagList.splice(this.credentials.tagList.indexOf(tag), 1);
      } else {
        document.getElementById(`${tag}`).classList.add('btn-primary');
        document.getElementById(`${tag}`).classList.remove('btn-secondary');
        this.credentials.tagList.push(tag);
      }
    },
    time() {
      this.startround1();
    },
    sendRecord(credentials, credentialsUser) {
      this.credentialsUser.memberId = encodeURI(this.credentialsUser.memberId);
      this.credentialsUser.meetingRoomId = encodeURI(this.credentialsUser.meetingRoomId);
      console.log(this.credentialsUser.memberId);
      console.log(this.credentialsUser.meetingRoomId);
      console.log('보내는 데이터 양식', this.credentials);
      axios.post(`https://i7c202.p.ssafy.io:8282/api/user/record/finish/${credentialsUser.memberId}/${credentialsUser.meetingRoomId}`, credentials)
        .then((res) => {
          console.log('성공', res.data);
          this.$router.push('/');
          document.getElementsByClassName('modal-backdrop')[0].remove();
          document.getElementsByClassName('modal-open')[0].removeClass('modal-open');
          this.leaveSession();
        })
        .catch((err) => {
          console.log('실패', err);
          // this.$router.push('/');
          // document.getElementsByClassName('modal-backdrop')[0].remove();
          // document.getElementsByClassName('modal-open')[0].removeClass('modal-open');
          // this.leaveSession();
          // this.mySessionId = '';
        });
    },
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
        memberId: '2', // state와 연결하는 항목
        secret: false, // 방 만들 때 바인딩해야하는 항목
        password: '', // 방 만들 때 바인딩해야하는 항목
        mode: 'GAME', // 방 만들 때 바인딩해야하는 항목
        roomName: '방1', // 방 만들 때 바인딩해야하는 항목
        type: 'GAME', // 방 만들 때 바인딩해야하는 항목
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
      console.log('점원목록', this.subscribers);

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
              resolution: '260x180', // The resolution of your video
              frameRate: 60, // The frame rate of your video
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
        this.allchattingList.push(obj);
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
        console.log(event);
        console.log('게임! start');
      });

      this.session.on('signal:end', (event) => {
        console.log(event);
        console.log('게임! end');
        this.leaveSession();
      });

      this.session.on('signal:leaveRoomMe', (event) => {
        console.log(event.data);
        for (let i = 0; i < this.subscribers.length; i += 1) {
          if (this.subscribers[i].stream.connection.connectionId === event.data) {
            this.subscribers.splice(this.subscribers[i], 1);
          }
        }
      });

      this.session.on('signal:startround1', () => {
        this.$refs.setTimer2.pauseTimer();
        this.youtubeURL = 'https://www.youtube.com/embed/gTowV_F07uI';
      });

      window.addEventListener('beforeunload', this.leavepeople);
    },

    leavepeople() {
      alert('나가는중!');
      this.leaveSession();
      this.session
        .signal({
          data: `${this.streamManager.stream.connection.connectionId}`,
          to: [],
          type: 'leaveRoomMe',
        })
        .then(() => {
        })
        .catch(() => {});
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

    startround1() {
      this.session
        .signal({
          data: '', // Any string (optional)
          to: [], // Array of Connection objects (optional. Broadcast to everyone if empty)
          type: 'startround1', // The type of message (optional)
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
      window.removeEventListener('beforeunload', this.leavepeople);
      this.SET_SESSION_ID('');
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
                `No connection to OpenVidu Server. This may be a certificate error at ${this.OPENVIDU_SERVER_URL}`,
              );
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
            if (this.mypictures.length >= 3) { this.mypictures.pop(); }
            console.log(canvas.toDataURL('image/png', 1.0));
            // this.mypictures.unshift(canvas.toDataURL('image/png', 1.0));
            this.mypictures.unshift(canvas.toDataURL('image/png', 1.0));
            // const link = document.createElement('a');
            // document.body.appendChild(link);
            // link.href = canvas.toDataURL('image/png', 1.0);
            // localStorage.setItem('userImage', link);
            // link.download = '안녕하세요?.png';
            // link.click();
            // document.body.removeChild(link);
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
  z-index:1000;
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
  border: 4px solid #4e8aff;
  border-radius: 30px;
  width:320px;
  height:600px;
  background-color: #4e8aff;
  bottom: 100px;
  right: 20px;
}

.achat-content {
  position:fixed;
  border: 4px solid #4e8aff;
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
  background-color:#eaf1ff;
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
    z-index: 600;
    opacity: 0.7;
    font-size:599px;
}

#take_photo_WebRTC {
  background-color:gray;
  position: fixed;
  width: 60%;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  /* height: 80%; */
  z-index: 601;
}

#take_photo_timer {
    position:fixed;
    top: 0%;
    left: 0%;
    width: 100%;
    height: 100%;
    z-index: 602;
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

.mybtn1 {
  background-color:transparent;
  border:none;
  position:fixed;
  top:10%;
  left:30px;
  z-index: 500;
}

.mybtn2 {
  background-color:transparent;
  border:none;
  position:fixed;
  top:20%;
  left:30px;
  z-index: 500;
}

.mybtn3 {
  background-color:transparent;
  border:none;
  position:fixed;
  top:30%;
  left:30px;
  z-index: 500;
}

.mybtn4 {
  background-color:transparent;
  border:none;
  position:fixed;
  top:40%;
  left:30px;
  z-index: 500;
}

.mybtn5 {
  background-color:transparent;
  border:none;
  position:fixed;
  top: 6px;
  right: 120px;
  z-index: 500;
}

.mybtn6 {
  background-color:transparent;
  border:none;
  position:fixed;
  top: 30px;
  right: 30px;
  z-index: 500;
}

.setTimer2position {
  position:fixed;
  top:90px;
  right:30px;
  z-index:500;
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
</style>
