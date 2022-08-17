 <template>
 <div class="body">
  <div class="tabs">
    <div class="tab-menu d-flex">
      <!--eslint-disable-->
        <div class="mytab" id="mytab1" style="color: #37beefef" v-on:click="((currentTab = 0), changeColor(0))"
        >{{ tabs[0] }}
        </div>
          <div class="mytab" id="mytab2" v-on:click="((currentTab = 1), changeColor(1))"
        >{{ tabs[1] }}
        </div>
          <div class="mytab" id="mytab3" v-on:click="((currentTab = 2), changeColor(2))"
        >{{ tabs[2] }}
        </div>
      <div style="margin-left: auto;">
        <div 
        style="margin-right: 5px; 
        font-family: 'Recipekorea';
        width: 100px;
        height: 50px;
        font-size: 22px;
        display: flex;
        justify-content: center;
        align-items: center;
        color: rgba(243, 62, 62, 0.78);
        "
        v-b-modal.modal-makeSession variant="outline-danger"
          v-if="isLogin"> 방 만들기
        </div>
      </div>
      <b-modal centered id="modal-makeSession" title="#오운완의 운동방 생성하기"
        hide-footer="true" hide-header="true">
        <div class="d-flex justify-content-center">
          <form class="makesessionForm">
            <div class="roomdata">
              <p class="modal-title">
                오운완 운동방 만들기
              </p>
              <!--기본 입력폼-->
              <b-input-group prepend="방 이름" class="roomdata_input">
                <b-form-input type="text" v-model="roomdatas.roomName"></b-form-input>
              </b-input-group>
              <b-input-group prepend="모드" class="roomdata_input">
                <b-form-select :options="Object.keys(mode)"
                v-model="roomdatas.mode"></b-form-select>
              </b-input-group>
              <!--모드 === 운동-->
              <div v-if="roomdatas.mode === '운동'">
                <b-input-group prepend="운동 종류" class="roomdata_input">
                  <b-form-select :options="Object.keys(workout)"
                  v-model="roomdatas.type"></b-form-select>
                </b-input-group>
                <b-input-group prepend="링크" class="roomdata_input">
                <b-form-input type="link" v-model="roomdatas.link"></b-form-input>
              </b-input-group>
                <b-input-group v-if="roomdatas.secret" prepend="비밀번호" class="roomdata_input">
                  <b-form-input type="password" v-model="roomdatas.password"></b-form-input>
                </b-input-group>
                &ensp; <label for="checkbox">
                  <input type="checkbox" v-model="roomdatas.secret"
                  style="padding:10px; margin-top: 10px;"> 비밀방 생성
                </label>
                <div class="btns d-flex row justify-content-center" style="margin-top: 20px;">
                  <b-button variant="primary"
                  @click="moveRoom(roomdatas)" style="width: 80%">운동하기</b-button>
                </div>
              </div>
              <!--모드 === 자유-->
              <div v-if="roomdatas.mode === '자유'">
                <b-input-group prepend="운동 종류" class="roomdata_input">
                  <b-form-select :options="Object.keys(workout)"
                  v-model="roomdatas.type"></b-form-select>
                </b-input-group>
                <b-input-group v-if="roomdatas.secret" prepend="비밀번호" class="roomdata_input">
                  <b-form-input type="password" v-model="roomdatas.password"></b-form-input>
                </b-input-group>
                &ensp; <label for="checkbox">
                  <input type="checkbox" v-model="roomdatas.secret"
                  style="padding:10px; margin-top: 10px"> 비밀방 생성
                </label>
                <div class="btns d-flex row justify-content-center" style="margin-top: 20px;">
                  <b-button variant="primary" @click="moveRoom(roomdatas)"
                  style="width: 80%">운동하기</b-button>
                </div>
              </div>
              <!--모드 === 경쟁-->
              <div v-if="roomdatas.mode === '경쟁'">
                <b-input-group prepend="운동 종류" class="roomdata_input">
                  <b-form-select :options="Object.keys(competition_option)"
                  v-model="roomdatas.type"></b-form-select>
                </b-input-group>
                <div class="btns d-flex row justify-content-center" style="margin-top: 20px;">
                  <b-button variant="primary" @click="moveRoom(roomdatas)"
                  style="width: 80%">운동하기</b-button>
                </div>
              </div>
            </div>
          </form>
        </div>
      </b-modal>
    </div>
  </div>
  <!--운동방 -->
    <div v-if="!noStreaming" class="tab-content wrap">
      <div v-show="currentTab == 0" class="scroll__wrap">
        <div v-for="(room, i) in roomList.streamingRoomList"
        :key="i" class="scroll--element">
         <div class='darkness' v-if="room.person === 6">
             <p>
              모든 인원이 꽉 찼습니다.
              </p>
          </div>
              <b-card class="rooms" footer-tag="footer">
                <div class="img_sport">
              <div v-if="room.type === 'GAME'">
                  <img src="@/assets/sport/game.png" alt="">
                </div>
                <div v-if="room.type === 'AEROBIC'">
                  <img src="@/assets/sport/running.png"
                  alt="">
                </div>
                <div v-if="room.type === 'HEALTH'" >
                  <img src="@/assets/sport/weight.png"
                  alt="">
                </div>
                <div v-if="room.type === 'STRETCHING'" >
                  <img src="@/assets/sport/stretching.png"
                  alt="">
                </div>
                <div v-if="room.type === 'HOME'">
                  <img src="@/assets/sport/home.png"
                  alt="">
                </div>
                <div v-if="room.type === 'BODYWEIGHT'">
                  <img src="@/assets/sport/push-up-bar.png"
                  alt="">
                </div>
                <div v-if="room.type === 'YOGA'">
                  <img src="@/assets/sport/yoga.png"
                  alt="">
                </div>
                <div v-if="room.type === 'PILATES'">
                  <img src="@/assets/sport/pilates.png"
                  alt="">
                </div>
            </div>
                <div class="d-flex">
                    <p align="left" class="workoutType">{{ workout_reverse[room.type] }}</p>
                </div>
            <div class="cardTitle d-flex align-items-center">
              <p style="font-size: 0.7em">{{ room.roomName }}</p>
            <span v-if="!room.secret"><img src="@/assets/icon/lock1.png"
            alt="" style="width:35px"></span>
            <p v-if="room.secret"><img src="@/assets/icon/lock2.png" alt="" style="width:35px"></p>
            </div>
            <b-button size="lg" class="rooms_btn" variant="primary"
              @click="enterroom({
              password: enterPassword,
              roomId: room.roomId,
              mode: room.mode,
              roomName: room.roomName,
              link: room.link,
              })">
              들어가기</b-button>
              <template #footer>
              <div class="d-flex justify-content-end">
                  <b-input-group prepend="PW" class="roomdata_input" v-show="room.secret===true"
                  style="width: 100%; padding: 0px;">
                    <b-form-input
                    type="password"
                    v-model="enterPassword"
                    >
                    </b-form-input>
                  </b-input-group>
                <p align="right" style="font-size: 25px;"
                class="workoutPerson"><img src="@/assets/icon/running.png"
                    alt="" style="width: 30px"> {{ room.person }} / 6</p>
              </div>
            </template>
          </b-card>
        </div>
      </div>
    </div>
    <div v-if="noStreaming" class="tab-content wrap">
      <div v-show="currentTab == 0" class="scroll__wrap">
      <div class="darkness">
        <h1 style="height:200px; line-height: 365px;">아직
        <strong style="color: #37beef;">운동방</strong>이 없습니다.</h1>
        <h1 style="margin-top: 20px;">방 만들기를 눌러 만들어 주세요!</h1>
      </div>
      </div>
    </div>
      <!--자유방  -->
    <div v-if="!noFree" class="tab-content wrap">
      <div v-show="currentTab == 1" class="scroll__wrap">
        <div v-for="(room, i) in roomList.freeRoomList" :key="i" class="scroll--element">
         <div class='darkness' v-if="room.person === 6">
            <p>
              모든 인원이 꽉 찼습니다.
              </p>
          </div>
          <b-card class="rooms" footer-tag="footer">
            <div class="img_sport">
              <div v-if="room.type === 'GAME'">
                  <img src="@/assets/sport/game.png" alt="">
                </div>
                <div v-if="room.type === 'AEROBIC'">
                  <img src="@/assets/sport/running.png"
                  alt="">
                </div>
                <div v-if="room.type === 'HEALTH'" >
                  <img src="@/assets/sport/weight.png"
                  alt="">
                </div>
                <div v-if="room.type === 'STRETCHING'" >
                  <img src="@/assets/sport/stretching.png"
                  alt="">
                </div>
                <div v-if="room.type === 'HOME'">
                  <img src="@/assets/sport/home.png"
                  alt="">
                </div>
                <div v-if="room.type === 'BODYWEIGHT'">
                  <img src="@/assets/sport/push-up-bar.png"
                  alt="">
                </div>
                <div v-if="room.type === 'YOGA'">
                  <img src="@/assets/sport/yoga.png"
                  alt="">
                </div>
                <div v-if="room.type === 'PILATES'">
                  <img src="@/assets/sport/pilates.png"
                  alt="">
                </div>
            </div>
                <div class="d-flex">
                    <p align="left" class="workoutType">{{ workout_reverse[room.type] }}</p>
                </div>
            <div class="cardTitle d-flex align-items-center">
              <p style="font-size: 0.7em">{{ room.roomName }}</p>
            <span v-if="!room.secret"><img src="@/assets/icon/lock1.png"
            alt="" style="width:35px"></span>
            <p v-if="room.secret"><img src="@/assets/icon/lock2.png" alt="" style="width:35px"></p>
            </div>
            <b-button size="lg" class="rooms_btn" variant="primary"
              @click="enterroom({
              password: enterPassword,
              roomId: room.roomId,
              mode: room.mode,
              roomName: room.roomName,
              })">
              들어가기</b-button>
                <template #footer>
              <div class="d-flex justify-content-end">
                  <b-input-group prepend="PW" class="roomdata_input" v-show="room.secret===true"
                  style="width: 100%; padding: 0px;">
                    <b-form-input
                    type="password"
                    v-model="enterPassword"
                    >
                    </b-form-input>
                  </b-input-group>
                <p align="right" style="font-size: 25px;"
                class="workoutPerson"><img src="@/assets/icon/running.png"
                    alt="" style="width: 30px"> {{ room.person }} / 6</p>
              </div>
            </template>
          </b-card>
        </div>
      </div>
    </div>
    <div v-if="noFree" class="tab-content wrap">
      <div v-show="currentTab == 1" class="scroll__wrap">
      <div class="darkness">
          <h1 style="height:200px; line-height: 365px;">아직
        <strong style="color: #37beef;">자유방</strong>이 없습니다.</h1>
        <h1 style="margin-top: 20px;">방 만들기를 눌러 만들어 주세요!</h1>
      </div>
      </div>
    </div>
      <!--경쟁방 -->
    <div v-if="!noGame" class="tab-content wrap">
      <div v-show="currentTab == 2" class="scroll__wrap">
        <div v-for="(room, i) in roomList.gameRoomList" :key="i" class="scroll--element">
         <div class='darkness' v-if="room.person === 6">
            <p>
              모든 인원이 꽉 찼습니다.
              </p>
          </div>
          <b-card class="rooms" footer-tag="footer">
            <div class="img_sport">
              <div v-if="room.type === 'GAME'">
                  <img src="@/assets/sport/game.png" alt="">
                </div>
                <div v-if="room.type === 'AEROBIC'">
                  <img src="@/assets/sport/running.png"
                  alt="">
                </div>
                <div v-if="room.type === 'HEALTH'" >
                  <img src="@/assets/sport/weight.png"
                  alt="">
                </div>
                <div v-if="room.type === 'STRETCHING'" >
                  <img src="@/assets/sport/stretching.png"
                  alt="">
                </div>
                <div v-if="room.type === 'HOME'">
                  <img src="@/assets/sport/home.png"
                  alt="">
                </div>
                <div v-if="room.type === 'BODYWEIGHT'">
                  <img src="@/assets/sport/push-up-bar.png"
                  alt="">
                </div>
                <div v-if="room.type === 'YOGA'">
                  <img src="@/assets/sport/yoga.png"
                  alt="">
                </div>
                <div v-if="room.type === 'PILATES'">
                  <img src="@/assets/sport/pilates.png"
                  alt="">
                </div>
            </div>
                <div class="d-flex">
                    <p align="left" class="workoutType">{{ workout_reverse[room.type] }}</p>
                    <p style="background-color: rgba(243, 62, 26, 0.445);">
                    {{ tier[Math.trunc(Number((room.manger_percentage / 20)) - 1)] }}</p>
                </div>
            <div class="cardTitle d-flex align-items-center">
              <p style="font-size: 0.7em">{{ room.roomName }}</p>
            </div>
            <b-button size="lg" class="rooms_btn" variant="primary"
              @click="enterCompetitionRoom({
              password: enterPassword,
              roomId: room.roomId,
              mode: room.mode,
              roomName: room.roomName,
              master: room.memberId,
              })">
              들어가기</b-button>
              <template #footer>
              <div class="d-flex justify-content-end">
                  <b-input-group prepend="PW" class="roomdata_input" v-show="room.secret===true"
                  style="width: 100%; padding: 0px;">
                    <b-form-input
                    type="password"
                    v-model="enterPassword"
                    >
                    </b-form-input>
                  </b-input-group>
                <p align="right" style="font-size: 25px;"
                class="workoutPerson"><img src="@/assets/icon/running.png"
                    alt="" style="width: 30px"> {{ room.person }} / 6</p>
              </div>
            </template>
          </b-card>
        </div>
      </div>
    </div>
    <div v-if="noGame" class="tab-content wrap">
      <div v-show="currentTab == 2" class="scroll__wrap">
       <div class="darkness">
        <h1 style="height:200px; line-height: 365px;">아직
        <strong style="color: #37beef;">경쟁방</strong>이 없습니다.</h1>
        <h1 style="margin-top: 20px;">방 만들기를 눌러 만들어 주세요!</h1>
      </div>
    </div>
  </div>
</div>
</template>

<script>
import { mapGetters, mapActions, mapState } from 'vuex';
// eslint--disable-next-line
const accounts = 'accounts';

export default {
  name: 'myTabs',
  components: {
  },
  data() {
    return {
      enterPassword: '',
      tabs: ['운동방', '자유방', '경쟁방'],
      currentTab: 0,
      mode: {
        자유: 'FREE',
        경쟁: 'GAME',
        운동: 'STREAMING',
      },
      workout: {
        유산소: 'AEROBIC',
        헬스: 'HEALTH',
        스트레칭: 'STRETCHING',
        홈트레이닝: 'HOME',
        맨몸운동: 'BODYWEIGHT',
        요가: 'YOGA',
        필라테스: 'PILATES',
        게임: 'GAME',
        기타: 'ETC',
      },
      workout_reverse: {
        AEROBIC: '유산소',
        HEALTH: '헬스',
        STRETCHING: '스트레칭',
        HOME: '홈트레이닝',
        BODYWEIGHT: '맨몸운동',
        YOGA: '요가',
        PILATES: '필라테스',
        GAME: '게임',
        ETC: '기타',
      },
      roomdatas: {
        secret: false,
        password: '',
        mode: '',
        roomName: '',
        type: '',
        link: '--',
      },
      competition_option: {
        게임: 'GAME',
      },
      tier: {
        0: '다이아', // 0 ~ 19
        1: '플래티넘', // 20 ~ 39
        2: '골드', // 40 ~ 59
        3: '실버', // 60 ~ 79
        4: '브론즈', // 80 ~ 100
      },
    };
  },
  computed: {
    ...mapState(accounts, ['masterTier']),
    ...mapGetters(accounts, ['roomList', 'userInfo', 'isLogin', 'noGame', 'noFree', 'noStreaming']),
  },
  methods: {
    ...mapActions(accounts, ['getRoomList', 'enterroom', 'makeRoom', 'enterCompetitionRoom']),
    moveRoom(payload) {
      this.makeRoom(payload);
      document.body.removeAttribute('class');
      document.body.removeAttribute('style');
      document.getElementsByClassName('modal-backdrop')[0].remove();
    },
    changeColor(k) {
      const el = document.getElementsByClassName('mytab');
      el[0].style.color = 'black';
      el[1].style.color = 'black';
      el[2].style.color = 'black';
      el[k].style.color = '#37beef';
    },
  },
  created() {
  },
};
</script>

<style scoped>
.mytab {
  width: 100px;
  height: 50px;
  display: flex;
  align-items: center;
  justify-content: left;
  font-size: 22px;
  font-family: 'Recipekorea';
}
.body {
  min-height: 500px;
  height: 500px;
  margin: 0 50px;
  margin: 0;
  /* background-color: aqua; */
}
.img_sport img {
  width: 150px;
  height: 150px;
}
.workoutPerson {
  align-items: right;
}
.workoutType {
  text-align: left;
  background-color: rgba(244, 165, 96, 0.492);
  color: gray;
  border-radius: 5px;
  padding: 5px;
}
.cardTitle {
  font-size: 3em;
}
.tab-menu {
  margin-top: 25px;
}
/* 기본 구조 */
.wrap {
  max-width: 100%;
  margin: 50px auto;
  /* background: #f8f8f8; */
}

/* .tab-content {
  height: 300px;
} */
/* 가로 스크롤 적용 */
.scroll__wrap {
  overflow: auto;
  white-space: nowrap;
  font-size: 15px;
  margin-top: -30px;
  height: 400px;
  /* background-color: #f4f3f3; */
  border-radius: 10px;
  border: solid #dfdfdf 1px;
}

.scroll--element {
  display: inline-block;
  line-height: 15px;
  width: 25%;
  height: 100%;
  padding-left: 10px;

}

.scroll--element+scroll--element {
  margin-left: 10px;
}
.rooms {
  background-color: white;
}
.rooms p {
  width: max-content;
  padding: 5px;
  margin: 5px;
  text-align: left;
  font-size: 20px;
  font-weight: bold;
  border-radius: 10%;
}
.card-footer {
  /* background-color:#246dfe; */
  background-color: var(--bs-card-cap-bg);
}
.rooms_btn {
  margin-top: 20px;
  width: 45%;
}
.makeSessionForm {
  width: 50%;
}

.roomdata_input {
  padding: 10px;
}

.form-check {
  padding: 10px;
}

input {
  /* margin-left: 50px; */
}
.darkness {
  font-family: 'Recipekorea';
  line-height: 390px;
  font-size: 25px;
  border: none;
  /* padding-left: 1.5vw;
  padding-right: 1.5vw; */
  background-color: #246dfe;
  color: white;
  text-decoration: none;
  text-align: center;
  display: block;
  position: relative;
  background: rgba(0, 0, 0, 0.7);
  width: 100%;
  height: 100%;
  margin-bottom: -405px;
  z-index: 1;
  border-radius: 10px;
}
.modal-title {
  font-family: 'Recipekorea';
  font-size: 20px;
  font-weight: 900;
  padding: 20px;
}
</style>
