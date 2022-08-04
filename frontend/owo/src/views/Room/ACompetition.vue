<template>
  <div class="d-flex justify-content-center">
    <div>
      <div class="d-flex justify-content-between align-items-center" style="height: 80px">
        <h3 class="m-0">팔굽혀펴기</h3>
        <Timer />
      </div>
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
      <div id="join" v-if="!session">
        <div id="img-div">
          <img src="resources/images/openvidu_grey_bg_transp_cropped.png" />
        </div>
        <div id="join-dialog" class="jumbotron vertical-center">
          <h1>Join a video session</h1>
          <div class="form-group">
            <p>
              <label>Participant</label>
              <input v-model="myUserName" class="form-control" type="text" required />
            </p>
            <p>
              <label>Session</label>
              <input v-model="sessionId" class="form-control" type="text" required />
            </p>
            <p class="text-center">
              <button class="btn btn-lg btn-success" @click="joinSession(sessionId)">
                Join!
              </button>
            </p>
          </div>
        </div>
      </div>
      <div id="session" v-if="session">
        <div id="session-header">
          <h1 id="session-title">{{ mySessionId }}</h1>
          <input
            class="btn btn-large btn-danger"
            type="button"
            id="buttonLeaveSession"
            @click="leaveSession"
            value="Leave session"
          />
        </div>
        <div class="d-flex align-items-start justify-content-between">
          <div class="row">
          <!-- <div id="main-video"></div> -->
            <!-- <div> -->
            <WebRTC :stream-manager="mainStreamManager"/>
            <!-- </div> -->
          <!-- </div> -->
          <!-- <div id="video-container" class="row"> -->
            <!-- <WebRTC
              :stream-manager="publisher"
              @click="updateMainVideoStreamManager(publisher)"
            /> -->
            <WebRTC
            v-for="sub in subscribers"
            :key="sub.stream.connection.connectionId"
            :stream-manager="sub"
            @click="updateMainVideoStreamManager(sub)"/>
            </div>
          </div>
        </div>
        <RoomButton></RoomButton>
      </div>
    </div>
</template>
<script>
import Timer from "@/components/SetTimer.vue";
import WebRTC from "@/components/Room/WebRTC.vue";
import RoomButton from "@/components/Room/RoomButton.vue";
import AfterExerciseModal from "@/components/Room/AfterExerciseModal.vue";
import axios from "axios";
import { OpenVidu } from "openvidu-browser";

axios.defaults.headers.post["Content-Type"] = "application/json";
import { mapState, mapActions, mapMutations } from "vuex";
const openvidu = "openvidu";
const accounts = "accounts";
const meetingroom = "meetingroom";
export default {
  name: "CompetitionView",
  components: {
    Timer,
    WebRTC,
    RoomButton,
    AfterExerciseModal,
  },
  data() {
    return {
      OV: undefined,
      session: undefined,
      mainStreamManager: undefined,
      publisher: undefined,
      subscribers: [],
      mode: ["FREE", "STREAMING", "GAME"],
      // mySessionId: "SessionA",
      // myUserName: `Participant${Math.floor(Math.random() * 100)}`,
      myUserName: "",
      sessionId: 'SessionA',
    };
  },
  watch: {
    mySessionId: function () {},
  },
  setup() {},
  created() {},
  moundted() {},
  unmounted() {},
  computed: {
    ...mapState(accounts, ["accessToken", "userInfo"]),
    ...mapState(openvidu, ["OPENVIDU_SERVER_URL", "OPENVIDU_SERVER_SECRET"]),
    ...mapState(meetingroom, ["mySessionId", "meetingRoomList"]),
    // ...openviduHelper.mapState(["OPENVIDU_SERVER_URL", "OPENVIDU_SERVER_SECRET"]),
    // ...meetingRoomHelper.mapState(["sessionID", "meetingRoomList"]),
  },
  methods: {
    ...mapMutations(meetingroom, ["SET_SESSION_ID"]),
    ...mapActions(meetingroom, [
      "makeSession",
      "getMeetingRoomList",
      "enterMeetingRoom",
      "leaveMeetingRoom",
    ]),
    async makeRoom() {
      const requestDto = {
        accesstoken: this.accessToken,
        memberId: "2",
        secret: false,
        password: "",
        mode: "GAME",
        roomName: "방1",
        type: "GAME",
        link: "",
      };
      await this.makeSession(requestDto);
      this.joinSession(this.mySessionId);
    },
    getRoomList(m) {
      const requestDto = {
        accesstoken: this.accessToken,
        mode: m,
      };
      this.getMeetingRoomList(requestDto);
    },
    getToken(mySessionId) {
      return this.createSession(mySessionId).then((sessionId) => this.createToken(sessionId));
    },
    async joinSession(sessionNum) {
      this.SET_SESSION_ID(sessionNum);
      console.log("sessionID = " + sessionNum);
      const requestDto = {
        accesstoken: this.accessToken,
        roomId: sessionNum,
      };
      console.log(sessionNum);
      await this.enterMeetingRoom(requestDto);
      // --- Get an OpenVidu object ---
      this.OV = new OpenVidu();

      // --- Init a session ---
      this.session = this.OV.initSession();

      // --- Specify the actions when events take place in the session ---

      // On every new Stream received...
      this.session.on("streamCreated", ({ stream }) => {
        const subscriber = this.session.subscribe(stream);
        this.subscribers.push(subscriber);
      });

      // On every Stream destroyed...
      this.session.on("streamDestroyed", ({ stream }) => {
        const index = this.subscribers.indexOf(stream.streamManager, 0);
        if (index >= 0) {
          this.subscribers.splice(index, 1);
        }
      });

      // On every asynchronous exception...
      this.session.on("exception", ({ exception }) => {
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
              resolution: "250x150", // The resolution of your video
              frameRate: 30, // The frame rate of your video
              insertMode: "APPEND",
              // How the video is inserted in the target element 'video-container'
              mirror: false, // Whether to mirror your local video or not
            });

            this.mainStreamManager = publisher;
            this.publisher = publisher;

            // --- Publish your stream ---

            this.session.publish(this.publisher);
          })
          .catch((error) => {
            console.log("There was an error connecting to the session:", error.code, error.message);
          });
      });

      window.addEventListener("beforeunload", this.leaveSession);
    },

    async leaveSession() {
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

      window.removeEventListener("beforeunload", this.leaveSession);
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
     *   1) Initialize a Session in OpenVidu Server	(POST /openvidu/api/sessions)
     *   2) Create a Connection in OpenVidu Server (POST /openvidu/api/sessions/<SESSION_ID>/connection)
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
                username: "OPENVIDUAPP",
                password: this.OPENVIDU_SERVER_SECRET,
              },
            }
          )
          .then((response) => response.data)
          .then((data) => resolve(data.id))
          .catch((error) => {
            if (error.response.status === 409) {
              resolve(sessionId);
            } else {
              console.warn(
                `No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}`
              );
              if (
                window.confirm(
                  `No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}\n\nClick OK to navigate and accept it. If no certificate warning is shown, then check that your OpenVidu Server is up and running at "${OPENVIDU_SERVER_URL}"`
                )
              ) {
                location.assign(`${this.OPENVIDU_SERVER_URL}/accept-certificate`);
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
                username: "OPENVIDUAPP",
                password: this.OPENVIDU_SERVER_SECRET,
              },
            }
          )
          .then((response) => response.data)
          .then((data) => resolve(data.token))
          .catch((error) => reject(error.response));
      });
    },
  },
};
</script>
<style scoped>
div {
  color: black;
}
.container {
  width: 100vw;
  height: 100vh;
}
</style>
