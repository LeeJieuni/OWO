<template>
  <!-- eslint-disable-next-line -->
  <div class="d-flex justify-content-center" style="width: 100%; height:100vh; background-color:#ffdddd;">
    <div style="width:1600px;" class="viewsetting">
      <!-- eslint-disable-next-line -->
      <div class="d-flex justify-content-center align-items-center" style="width: 100%; height: 80px;">
      <!-- eslint-disable-next-line -->
        <h3 class="game-name m-0" style="font-size:3rem; font-family: 'LeferiPoint-WhiteObliqueA';">{{ roomName }}</h3>
      </div>
      <!-- eslint-disable-next-line -->
      <div class="d-flex align-items-start justify-content-start" style="position:relative; background-color: transparent; width: 1050px; height: 750px">
        <YouTube class="my-2 mx-2 col-8"
          :src="this.youtubeURL"
          @ready="onReady"
          :vars="this.controls"
          @state-change="onChange"
          width="1050"
          height="750"
          ref="youtube"
          disablekb=1
          fs="0"
        />
        <!-- eslint-disable-next-line -->
        <div class="my-2 mx-2 shadow3"></div>
        <div v-if="!isStarted2" class="my-2 mx-2 shadow"></div>
        <div v-if="!isStarted2" class="my-2 mx-2 shadow2">
          오른쪽 위 START 버튼으로<br>
          시작해주세요 .<br>
        </div>
        <!-- eslint-disable-next-line -->
        <div class="my-2 mx-2 col-4" style="overflow-y: auto; width:560px; max-height:750px;">
          <WebRTC :stream-manager="mainStreamManager"/>
          <WebRTC :stream-manager="sub"
            v-for="sub in subscribers"
            :key="sub.stream.connection.connectionId"
          />
          <!-- <WebRTC :stream-manager="sub"
            v-for="sub in subscribers"
            :key="sub.stream.connection.connectionId"
            @click="updateMainVideoStreamManager(sub)"
          /> -->
          <div v-show="this.subscribers.length <= 0" class="webrtcetc col-4 m0p0 my-2 mx-2"></div>
          <div v-show="this.subscribers.length <= 1" class="webrtcetc col-4 m0p0 my-2 mx-2"></div>
          <div v-show="this.subscribers.length <= 2" class="webrtcetc col-4 m0p0 my-2 mx-2"></div>
          <div v-show="this.subscribers.length <= 3" class="webrtcetc col-4 m0p0 my-2 mx-2"></div>
          <div v-show="this.subscribers.length <= 4" class="webrtcetc col-4 m0p0 my-2 mx-2"></div>
        </div>
      </div>
      <!-- 세션 -->
      <!-- 운동 종료 모달 -->
      <div>
        <div class="d-flex justify-content-center align-items-center">
          <!-- eslint-disable-next-line -->
          <b-modal v-model="modalShow" id="after-exercise-modal" size="xl" no-close-on-esc no-close-on-backdrop hide-footer hide-header>
            <form @submit.prevent="sendRecord(credentials, credentialsUser)">
              <div class="checkboxposition">
                <h1 class="title text-center mt-4">🏃‍♂️운동 일지</h1>
                <label for="my-checkbox m-0" class="secretcheckbox d-flex align-items-center">
                  <input v-model="credentials.secret" type="checkbox">
                  <div v-show="credentials.secret">
                    <!-- eslint-disable-next-line -->
                    &ensp;&ensp;<span style="color:#de7474;">비공개</span>&ensp;<img class="menu_icon1" src="@/assets/icon/lock.png" alt="">
                  </div>
                  <div  v-show="!credentials.secret">
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
                  <div class="md-title2 text-center">📷오늘의 운동 사진 1장을 골라주세요!(필수)</div>
                  <br>
                    <!-- eslint-disable-next-line -->
                </div>
                <div class="row d-flex align-items-start justify-content-center">
                  <!-- eslint-disable-next-line -->
                  <button @click.prevent="pickmyImg(`${mypicture}`, i)" v-bind:id="i" v-for="(mypicture, i) in mypictures" :key="i" class="pickimg col-4 m0p0 mx-1 my-1" style="padding:0px; margin:0px; width:330px;">
                    <img :src="mypicture" alt="img" style="width:328px;">
                  </button>
                </div>
                <!-- 메모 남기기 버튼 -->
                <div>
                  <div class="md-title">
                    <div class="text-center">태그를 선택해주세요!</div>
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
                              <input id="myTagInput" v-model="newTagContent" type="text" style="text-align: center; width:100px; color:white; border: none; border-bottom:1px solid white; background: transparent;" placeholder="#나만의태그">
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
                    <label for="exerciseMemo" style="width:100%"
                    class="d-flex justify-content-center">
                      <!-- eslint-disable-next-line -->
                      <textarea v-model="credentials.recordMemo" id="exerciseMemo" rows="4" style="width:95%" @keyup="fn_checkByte(this)"></textarea>
                    </label>
                    <div class="btyeposition">(<span id="nowByte">0</span>/200bytes)</div>
                  </div>
                </div>
                <div class="d-flex justify-content-center">
                  <!-- eslint-disable-next-line -->
                  <b-button type="submit" class="mybutton btn btn-success m-2 p-2">&ensp;기록 저장하기&ensp;</b-button>
                  <!-- eslint-disable-next-line -->
                  <button @click="tempLeaveSession()" class="mybutton btn btn-warning m-2 p-2">저장하지 않기</button>
                  <!-- eslint-disable-next-line -->
                  <b-button id="modalclose" class="mybutton btn btn-danger m-2 p-2" @click="get_out()">&ensp;취소하기&ensp;</b-button>
                </div>
              </div>
              <br>
              <div class="d-flex justify-content-center">
                <!-- eslint-disable-next-line -->
                <div style="width: 95%; text-align: right; color:red;">종료 / 새로 고침 시 기록이 저장되지 않아요!</div>
              </div>
            </form>
          </b-modal>
        </div>
      </div>
      <!-- 채팅 -->
      <div v-if="this.session">
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
      <!-- 버튼 -->
      <div>
        <button @click="open_emoji" class="open_emoji">
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
        <button class="mybtn3" @click="take_photo">
          <img class="menu_icon2" src="@/assets/icon/photo.png" alt="photo">
          <div style="color:#4e8aff; font-size:12px;">
            사진
          </div>
        </button>
        <button class="mybtn9" @click="volumeUp">
          <img class="menu_icon2" src="@/assets/icon/soundup.png" alt="photo">
          <div style="color:#4e8aff; font-size:12px;">볼륨
          </div>
          <div style="color:#4e8aff; font-size:12px; line-height:0.8;">
            Up
          </div>
        </button>
        <button class="mybtn10" @click="volumeDown">
          <img class="menu_icon2" src="@/assets/icon/sounddown.png" alt="photo">
          <div style="color:#4e8aff; font-size:12px;">볼륨
          </div>
          <div style="color:#4e8aff; font-size:12px; line-height:0.8;">
            Down
          </div>
        </button>
        <!-- <b-button v-b-modal.after-exercise-modal class="mybtn6"> -->
        <button @click="roomOut()" class="mybtn6">
          <img class="menu_icon2" src="@/assets/icon/roomout.png" alt="leaveSession">
        </button>
        <!-- eslint-disable-next-line -->
        <div v-if="(!this.subscribers.length && !this.isStartedGame)" class="mybtn11">2명 이상 모여야 시작 가능!!</div>
        <!-- eslint-disable-next-line -->
        <div v-if="!(this.credentialsUser.memberId === this.masterId) & !this.isStarted & (this.subscribers.length >= 1) && !this.isStartedGame" class="mybtn11">방장 >> 오른쪽 위 START 버튼!</div>
        <!-- eslint-disable-next-line -->
        <setTimer1 ref="setTimer1"></setTimer1>
        <!-- eslint-disable-next-line -->
        <button v-if="(this.credentialsUser.memberId === this.masterId) & !this.isStarted & (this.subscribers.length >= 1) && !this.isStartedGame" class="mybtn7" @click="startTimer">
        <!-- <button v-if="!isExercising" class="mybtn5" @click="startround1"> -->
          <img class="menu_icon4" src="@/assets/icon/start.png" alt="Start">
        </button>
        <!-- eslint-disable-next-line -->
        <b-dropdown size="lg" class="mybtn4" style="border:none; margin:0; padding:0;" variant="link" toggle-class="text-decoration-none" no-caret>
        <!-- eslint-disable-next-line -->
        <!-- <b-dropdown no-caret class="dropright mybtn4 text-decoration-none" style="background-color:none;" type="button"> -->
          <template #button-content>
            <img class="menu_icon2" src="@/assets/icon/setTimer.png" alt="photo">
            <div style="color:#4e8aff; font-size:12px;">타이머</div>
          </template>
          <!-- eslint-disable-next-line -->
          <button @click="set_timer_3" class="dropdown-item text-center" type="button" target="_self">3초</button>
          <!-- eslint-disable-next-line -->
          <button @click="set_timer_5" class="dropdown-item text-center" type="button" target="_self">5초</button>
          <!-- eslint-disable-next-line -->
          <button @click="set_timer_10" class="dropdown-item text-center" type="button" target="_self">10초</button>
        </b-dropdown>
      </div>
      <!-- 이모티콘 -->
      <div class="emoji_position" v-if="Emoji_ONOFF">
        <div class="row">
          <!-- apple, google, twitter, facebook -->
          <Picker :data="emojiIndex" set="twitter" @select="showEmoji" />
        </div>
      </div>
      <div v-show="isSetting" class="myBackGroundSetting">
        <setTimer2 ref="setTimer2"/>
      </div>
      <!-- 사진 양식 -->
      <div class="d-flex justify-content-center align-items-center"
      v-if="is_take_photo" id="take_photo_background"></div>
      <div class="d-flex justify-content-center align-items-center text-white"
      v-if="is_take_photo">
        <!-- eslint-disable-next-line -->
        <WebRTCPhoto id="take_photo_WebRTC" :stream-manager="mainStreamManager"/>
        <img v-if="this.photoDisplay" id="take_photo_WebRTC_photo" :src="this.mypictures[0]" alt="">
        <div id="take_photo_WebRTC_warning">&ensp;사진은 최신 3장까지 저장됩니다!&ensp;</div>
      </div>
      <!-- eslint-disable-next-line -->
      <div class="d-flex justify-content-center align-items-center text-white mt-4" v-if="is_take_photo" id="take_photo_timer">
        {{ temp_timer_2 }}
      </div>
    </div>
  </div>
</template>
<script>
import html2canvas from 'html2canvas';
import WebRTC from '@/components/Room/WebRTC.vue';
import WebRTCPhoto from '@/components/Room/WebRTCPhoto.vue';
import setTimer1 from '@/components/Room/setTimer1.vue';
import setTimer2 from '@/components/Room/setTimer2.vue';
import axios from 'axios';
import { OpenVidu } from 'openvidu-browser';
import emojidata from 'emoji-mart-vue-fast/data/all.json';
import 'emoji-mart-vue-fast/css/emoji-mart.css';
import { Picker, EmojiIndex } from 'emoji-mart-vue-fast/src';
import { mapState, mapActions, mapMutations } from 'vuex';
import swal from 'sweetalert2';
import YouTube from 'vue3-youtube';

window.Swal = swal;

axios.defaults.headers.post['Content-Type'] = 'application/json';

const openvidu = 'openvidu';
const accounts = 'accounts';
const meetingroom = 'meetingroom';
const emojiIndex = new EmojiIndex(emojidata);
const emoji = 'emoji';
const today = new Date();
const year = today.getFullYear();
const month = today.getMonth() + 1;
const day = today.getDate();
// eslint-disable-next-line
const format = year + '-' + (('00' + month.toString()).slice(-2)) + '-' +
((`00${day.toString()}`).slice(-2));

export default {
  name: 'CompetitionView',
  metaInfo: {
  },
  components: {
    WebRTC,
    WebRTCPhoto,
    Picker,
    setTimer1,
    setTimer2,
    YouTube,
  },
  data() {
    return {
      isStartedGame: false,
      youtubeURL: this.link,
      player: null,
      done: false,
      statusStart: false,
      controls: {
        controls: 0,
      },
      volume: 20,
      isSetting: false,
      limitMininalTime: 15, // 최소한의 기록이 남는 시간(초)
      firstPictureTime: 10, // 첫 사진이 촬영되는 시간(초)
      modalShow: false,
      // 타이머 셋팅
      timer: 3,
      temp_timer: 3,
      temp_timer_2: 3,
      take_photo_timer: null,
      is_take_photo: false,
      startTime: undefined,
      endTime: undefined,
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
      photoDisplay: false,
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
      restTime: undefined,
      credentials: {
        fileOriName: '',
        fileUrl: '',
        recordDatetime: format,
        recordMemo: null,
        recordTime: 0,
        secret: false,
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
      isStarted: false,
      isStarted2: false,
      isExercising: false,
      RoundStartTimer: null,
      // tm 영역
      webcam: undefined,
      URL: undefined,
      model: undefined,
      status: 'ready',
      check: false,
      check2: false,
      count: 0,
      gameType: 1, // 1:squat, 2:lunge, 3:burpee
      ctx: undefined,
      // 각 운동의 카운트를 memberId와 함께 session.on으로 보내주고 데이터 받아서 저장한다.
      squatCount: 0,
      LungeCount: 0,
      burpeeCount: 0,
      // 운동이 끝나면 count는 서버에 보내고, counts에 따라 임의의 score를 저장한다.
      squatScore: undefined,
      burpeeScore: undefined,
      Lungescore: undefined,
      // score의 합산으로 다시 정렬해서 순위별 등수를 매겨로 포인트를 부여한다.
      myscore: undefined,
      // mypoints를 서버에 보낸다.
      mypoints: undefined,
    };
  },
  setup() {
  },
  created() {
    this.sessionId = this.$route.params.sessionId;
    this.joinSession(this.sessionId);
    this.credentialsUser.memberId = this.userInfo.id;
    this.credentialsUser.meetingRoomId = this.mySessionId;
    document.body.style.backgroundColor = '#ffdddd';
  },
  moundted() {
  },
  unmounted() {
    this.leaveSession();
    document.body.style.backgroundColor = 'white';
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
    ...mapState(accounts, ['accessToken', 'userInfo', 'refreshToken', 'roomName', 'masterId', 'link']),
    ...mapState(openvidu, ['OPENVIDU_SERVER_URL', 'OPENVIDU_SERVER_SECRET']),
    ...mapState(meetingroom, ['mySessionId', 'camera', 'mic']),
  },
  methods: {
    get_out() {
      this.modalShow = !this.modalShow;
    },
    onChange() {
      if (this.$refs.youtube.getPlayerState() === -1) {
        if (!this.statusStart) {
          this.statusStart = true;
        } else {
          this.statusStart = false;
        }
      }
    },
    volumeUp() {
      console.log(`==============${this.volume}`);
      if (this.volume === 100) {
        swal.fire({
          icon: 'error',
          title: '볼륨이 최대에요',
          text: '5씩 조절돼요',
        });
      } else {
        this.volume += 5;
        this.$refs.youtube.setVolume(this.volume);
      }
    },
    volumeDown() {
      console.log(`==============${this.volume}`);
      if (this.volume === 0) {
        swal.fire({
          icon: 'error',
          title: '볼륨이 최소에요',
          text: '5씩 조절돼요',
        });
      } else {
        this.volume -= 5;
        this.$refs.youtube.setVolume(this.volume);
      }
    },
    tempLeaveSession() {
      this.leaveSession();
      document.body.removeAttribute('data-bs-overflow');
      document.body.removeAttribute('data-bs-padding-right');
      document.body.removeAttribute('class');
      document.body.removeAttribute('style');
      document.getElementsByClassName('modal-backdrop')[0].remove();
      document.getElementsByClassName('modal-backdrop')[0].remove();
      document.getElementsByClassName('modal-backdrop')[0].remove();
    },
    pickmyImg(Img, i) {
      this.credentials.fileOriName = `${this.userInfo.id}_${this.sessionId}_${format}.webp`;
      this.credentials.fileUrl = Img.replace('data:image/webp;base64,', '');
      const el1 = document.getElementById('0');
      const el2 = document.getElementById('1');
      const el3 = document.getElementById('2');
      if (el1) { el1.style.opacity = '0.3'; }
      if (el2) { el2.style.opacity = '0.3'; }
      if (el3) { el3.style.opacity = '0.3'; }
      document.getElementById(`${i}`).style.opacity = '1';
    },
    roomOut() {
      if (!this.startTime) {
        this.leaveSession();
      }
      const now = new Date();
      this.endTimeSet(now);
      // eslint-disable-next-line
      this.credentials.recordTime = Math.abs((this.startTime.getTime() - this.endTime.getTime()) / (1000 * 60));
      console.log(this.credentials.recordTime);
      if (this.credentials.recordTime * 60 < this.limitMininalTime) {
        swal.fire({
          title: '퇴장하실건가요?',
          text: `${this.limitMininalTime}초 미만 운동 시 기록이 저장되지 않습니다.`,
          icon: 'warning',
          showCancelButton: true,
          cancelButtonColor: '#3085d6',
          cancelButtonText: '더 운동할께요!',
          confirmButtonColor: '#d33',
          confirmButtonText: '그래도 나갈래요.',
        }).then((result) => {
          if (result.isConfirmed) {
            this.leaveSession();
          }
        });
      } else {
        this.modalShow = !this.modalShow;
      }
    },
    fn_checkByte() {
      const maxByte = 200; // 최대 200바이트
      const textVal = document.getElementById('exerciseMemo').value; // 입력한 문자
      const textLen = textVal.length; // 입력한 문자수
      let totalByte = 0;

      for (let i = 0; i < textLen; i += 1) {
        const eachChar = textVal.charAt(i);
        const uniChar = escape(eachChar); // 유니코드 형식으로 변환
        if (uniChar.length > 4) {
          totalByte += 2; // 한글 : 2Byte
        } else {
          totalByte += 1; // 영문,숫자,특수문자 : 1Byte
        }
      }
      if (totalByte > maxByte) {
        swal.fire({
          icon: 'warning',
          title: '알림',
          text: '최대 200bytes까지 입력가능해요!',
        });
        document.getElementById('exerciseMemo').value = document.getElementById('exerciseMemo').value.substr(0, 200);
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
        if (newTagContent.replace(/ /gi, '').replace(/#/gi, '') !== '') {
          if (this.myTags.indexOf(newTagContent.replace(/ /gi, '').replace(/#/gi, '')) < 0) {
            this.myTags.push(newTagContent.replace(/ /gi, '').replace(/#/gi, ''));
            this.newTagContent = '';
            this.myTagList(this.myTags[this.myTags.length - 1]);
          }
          this.newTagContent = '';
        }
        this.newTagContent = '';
      }
    },
    deletemyTagList(newTagContent) {
      this.myTags.splice(this.myTags.indexOf(newTagContent.replace(/ /gi, '').replace(/#/gi, '')), 1);
      if (this.credentials.tagList.indexOf(newTagContent.replace(/ /gi, '').replace(/#/gi, '')) >= 0) {
        this.credentials.tagList.splice(this.credentials.tagList.indexOf(newTagContent.replace(/ /gi, '').replace(/#/gi, '')), 1);
      }
    },
    myTagList(tag) {
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
    async sendRecord(credentials, credentialsUser) {
      this.credentialsUser.memberId = encodeURI(this.credentialsUser.memberId);
      this.credentialsUser.meetingRoomId = encodeURI(this.credentialsUser.meetingRoomId);
      await axios({
        url: `https://i7c202.p.ssafy.io:8282/api/user/record/finish/${credentialsUser.memberId}/${credentialsUser.meetingRoomId}`,
        method: 'post',
        headers: {
          'X-AUTH-TOKEN': this.accessToken,
        },
        data: {
          fileOriName: credentials.fileOriName, //
          fileEncoding: credentials.fileUrl, //
          recordDatetime: credentials.recordDatetime, //
          recordMemo: credentials.recordMemo, //
          recordTime: credentials.recordTime, // time만 양식에 맞춰서 반영하면 될듯?
          secret: credentials.secret,
          tagList: credentials.tagList,
        },
      })
        .then(() => {
          this.tempLeaveSession();
          document.getElementsByClassName('modal-backdrop')[0].remove();
          document.getElementsByClassName('modal-open')[0].removeAttribute('style');
          document.getElementsByClassName('modal-open')[0].classList.remove('modal-open');
        });
    },
    ...mapActions(emoji, ['changeEmojiList', 'removeEmojiList']),
    ...mapMutations(meetingroom, ['SET_SESSION_ID']),
    ...mapMutations(accounts, ['SET_YOUTUBE_LINK']),
    ...mapActions(meetingroom, [
      'enterMeetingRoom',
      'leaveMeetingRoom',
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

      // On every asynchronous exception...
      // this.session.on('exception', ({ exception }) => {
      //   console.warn(exception);
      // });

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
            this.connectionId = this.mainStreamManager.stream.session.connection.connectionId;
            this.publisher = publisher;
            // --- Publish your stream ---
            this.session.publish(this.publisher);
          });
      });
      // 사용자 정의 함수 영역
      this.session.on('signal:my-emoji', (event) => {
        const sendEmojiData = event.data.split(',');
        const obj = {
          connectionId: sendEmojiData[0],
          userEmoji: sendEmojiData[1],
        };
        this.changeEmojiList(obj);
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

      this.session.on('signal:startTimer', (event) => {
        this.start();
        setTimeout(() => {
          this.SET_YOUTUBE_LINK(event.data);
          this.youtubeURL = event.data;
          this.$refs.setTimer1.startTimer();
          const now = new Date();
          this.startTimeSet(now);
          this.startVideo();
        }, 4000);
        /* eslint-disable new-cap */
        setTimeout(() => {
          const el = document.getElementsByClassName('ov-video')[0];
          html2canvas(el).then((canvas) => {
            this.mypictures.unshift(canvas.toDataURL('image/webp', 1.0));
          });
        }, this.firstPictureTime * 1000);
      });
    },
    startVideo() {
      this.$refs.youtube.playVideo();
      this.$refs.youtube.controls = 0;
      this.$refs.youtube.setVolume(0);
    },
    start() {
      this.isStartedGame = true;
      this.isSetting = true;
      this.isStarted = true;
      // eslint-disable-next-line
      const audio = new Audio(require('@/assets/music/321.mp3'));
      audio.play();
      this.$refs.setTimer2.pauseTimer();
      setTimeout(() => {
        this.isStarted2 = true;
        this.isSetting = false;
      }, 4000);
      setTimeout(() => {
        // eslint-disable-next-line
        document.getElementsByClassName('webrtcetc')[0].remove();
        document.getElementsByClassName('webrtcetc')[0].remove();
        document.getElementsByClassName('webrtcetc')[0].remove();
        document.getElementsByClassName('webrtcetc')[0].remove();
        document.getElementsByClassName('webrtcetc')[0].remove();
      }, 2000);
    },
    startTimeSet(now) {
      this.startTime = now;
    },
    endTimeSet(now) {
      this.endTime = now;
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

    startTimer() {
      this.session
        .signal({
          data: this.link,
          to: [],
          type: 'startTimer',
        }).then(() => {
          this.isStarted = true;
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
      this.SET_SESSION_ID('');
      this.$router.push('/');
    },

    // updateMainVideoStreamManager(stream) {
    //   if (this.mainStreamManager === stream) return;
    //   this.mainStreamManager = stream;
    // },

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
        if (this.timer === 0) {
          const el = document.querySelector('#take_photo_WebRTC');
          el.style.opacity = '0';
          this.temp_timer_2 = '';
          // eslint-disable-next-line
          const audio2 = new Audio(require('@/assets/music/takePhoto.mp3'));
          audio2.play();
          html2canvas(el).then((canvas) => {
            this.mypictures.unshift(canvas.toDataURL('image/png', 1.0));
          }, 850);
        }
        if (this.timer === -1) {
          this.temp_timer_2 = '';
        }
        if (this.timer === -1) {
          this.photoDisplay = true;
          setTimeout(() => {
            this.photoDisplay = false;
          }, 2000);
          if (this.mypictures.length >= 3) { this.mypictures.pop(); }
          clearInterval(this.take_photo_timer);
          setTimeout(() => {
            this.is_take_photo = false;
            this.timer = this.temp_timer;
          }, 2000);
        }
      }, 1000);
      this.temp_timer_2 = this.temp_timer;
    },
    set_timer_3() { this.timer = 3; },
    set_timer_5() { this.timer = 5; },
    set_timer_10() { this.timer = 10; },
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
  top:38%;
  left:10px;
  /* z-index: 500; */
}

.mybtn5 {
  background-color:transparent;
  border:none;
  position:fixed;
  top: 20px;
  right: 120px;
  /* z-index: 500; */
}

.mybtn6 {
  background-color:transparent;
  border:none;
  position:fixed;
  top: 47px;
  right: 30px;
  /* z-index: 500; */
}

.mybtn7 {
  background-color:transparent;
  border:none;
  position:fixed;
  top: 21px;
  right: 120px;
  /* z-index: 500; */
}

.mybtn11 {
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

.mybtn9 {
  background-color:transparent;
  border:none;
  position:fixed;
  top: 48%;
  left: 30px;
  /* z-index: 500; */
}
.mybtn10 {
  background-color:transparent;
  border:none;
  position:fixed;
  top: 58%;
  left: 30px;
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

.pickimg:active {
  width:500px;
}

.shadow {
  font-size:70px;
  opacity:1;
  background-color: white;
  position: absolute;
  top:0px;
  left:0px;
  width: 1050px;
  height: 750px;
  display:flex;
  justify-content: center;
  align-items: center;
  font-family: 'LeferiPoint-WhiteObliqueA';
  color:#828282;
}

.shadow2 {
  font-size:45px;
  opacity:0.9;
  background-color: #4e8aff;
  position: absolute;
  top:50%;
  left:50%;
  transform: translate(-50%, -50%);
  border-radius: 50px;;
  width: 650px;
  height: 300px;
  display:flex;
  justify-content: center;
  align-items: center;
  font-family: 'LeferiPoint-WhiteObliqueA';
  color:white;
}

.shadow3 {
  font-size:70px;
  opacity:0;
  background-color: white;
  position: absolute;
  top:0px;
  left:0px;
  width: 1050px;
  height: 750px;
  display:flex;
  justify-content: center;
  align-items: center;
  font-family: 'LeferiPoint-WhiteObliqueA';
  color:#828282;
}

</style>
