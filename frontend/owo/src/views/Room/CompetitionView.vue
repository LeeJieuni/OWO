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
        <div id="session-header" class="d-flex">
          <h1 id="session-title">{{ mySessionId }}</h1>
          <input class="btn btn-large btn-danger" type="button"
            id="buttonLeaveSession" @click="leaveSession" value="Leave session"/>
        </div>
        <!-- WebRTC 목록 -->
        <div>
          <div class="row d-flex align-items-start justify-content-center">
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
      <RoomButton></RoomButton>
      <!-- 이모티콘 영역 -->
      <div class="emoji_position" v-if="Emoji_ONOFF">
        <div class="row">
          <!-- apple, google, twitter, facebook -->
          <Picker :data="emojiIndex" set="twitter" @select="showEmoji" />
        </div>
      </div>
      <button @click="open_emoji" class="open_emoji">
        <img class="menu_icon" src="@/assets/icon/emoji.png" alt="emoji">
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
              <!-- @keyup="checkmychatlength(this)" -->
              <textarea @keyup.enter="sendMassage" id="mychat" name="chatting" type="text"
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
import Timer from '@/components/SetTimer.vue';
import WebRTC from '@/components/Room/WebRTC.vue';
import RoomButton from '@/components/Room/RoomButton.vue';
import axios from 'axios';
import { OpenVidu } from 'openvidu-browser';
import emojidata from 'emoji-mart-vue-fast/data/all.json';
import 'emoji-mart-vue-fast/css/emoji-mart.css';
import { Picker, EmojiIndex } from 'emoji-mart-vue-fast/src';
import { mapState, mapActions, mapMutations } from 'vuex';

axios.defaults.headers.post['Content-Type'] = 'application/json';

const openvidu = 'openvidu';
const accounts = 'accounts';
const meetingroom = 'meetingroom';
const emojiIndex = new EmojiIndex(emojidata);
const emoji = 'emoji';

export default {
  name: 'CompetitionView',
  components: {
    Timer,
    WebRTC,
    RoomButton,
    Picker,
  },
  data() {
    return {
      emojiIndex,
      emojisOutput: '',
      OV: undefined,
      session: undefined,
      mainStreamManager: undefined,
      publisher: undefined,
      subscribers: [],
      mode: ['FREE', 'STREAMING', 'GAME'],
      myUserName: '',
      sessionId: 'SessionA',
      myChat: '',
      recvList: [],
      chatONOFF: false,
      Emoji_ONOFF: null,
      roomName: '붙어보자!',
      gameName: '팔굽혀펴기',
      myemoji: '',
      emojiList: [],
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
          const chat = document.querySelector('#chattingList');
          chat.scrollTop = chat.scrollHeight + 1000;
          console.log('Message successfully sent');
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
    },
    removeEmoji() {
      this.emojiList = [];
    },
  },
};
</script>
<style scoped>
div {
  color: black;
}
/* .container {
  width: 100vw;
  height: 100vh;
} */

.menu_icon {
  width:50px;
}

.emoji_position {
  position:fixed;
  bottom: 100px;
  left: 20px;
}

.open_emoji {
  position:fixed;
  bottom: 30px;
  left: 30px;
}

.chat {
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
  height:360px;
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
</style>
