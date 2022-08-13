 <template>
  <div class="tabs">
    <div class="tab-menu d-flex">
      <b-button lg="4" size="lg" style="margin-right: 5px;"
      v-b-modal.modal-makeSession variant="outline-primary"
      v-if="isLogin"> 방 만들기
      </b-button>
      <b-button-group class="mx-1" v-for="(tab, index) in tabs" :key="index"
        v-bind:class="{ active: currentTab === index }">
        <b-button lg="4" size="lg" v-on:click="currentTab = index"
        variant="outline-primary">{{ tab }}
        </b-button>
      </b-button-group>

      <b-modal id="modal-makeSession"
      title="#오운완의 운동방 생성하기"
      hide-footer="true"
      >
      <div class="d-flex justify-content-center">
        <form class="makesessionForm">
             <div class="roomdata">
               <b-input-group prepend="방 이름" class="roomdata_input">
                 <b-form-input type="text" v-model="roomdatas.roomName"></b-form-input>
               </b-input-group>
               <b-input-group prepend="모드" class="roomdata_input">
                 <b-form-select :options="Object.keys(mode)"
                 v-model="roomdatas.mode"></b-form-select>
                 <div>{{roomdatas.mode}}</div>
               </b-input-group>
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
                <b-button variant="primary"
                @click="moveRoom(roomdatas)"
                style="width: 80%">운동하기</b-button>
              </div>
             </div>
           </form>
      </div>
    </b-modal>
    </div>
    <div v-if="!noStreaming" class="tab-content wrap">
      <div v-show="currentTab == 0" class="scroll__wrap">
        <div v-for="(room, i) in roomList.streamingRoomList" :key="i" class="scroll--element">
          <b-card img-src="https://placekitten.com/280/200" img-top class="rooms">
            <h3>{{ room.roomName }}</h3>
            <p v-if="!room.secret"><img src="@/assets/icon/lock1.png" alt="" style="width:20px"></p>
            <p v-if="room.secret"><img src="@/assets/icon/lock2.png" alt="" style="width:20px"></p>
            <p>인원수 : {{ room.person }} / 6</p>
            <p>운동 : {{ workout_reverse[room.type] }}</p>
            <b-button class="rooms_btn" variant="primary"
            @click="enterroom({mode: room.mode, roomId: room.roomId})">들어가기</b-button>
          </b-card>
        </div>
      </div>
    </div>
    <div v-if="noStreaming" class="tab-content wrap">
      <div v-show="currentTab == 0" class="scroll__wrap">
        <h1>아직 운동중인 방이 없습니다!</h1>
      </div>
    </div>
    <div v-if="!noFree" class="tab-content wrap">
      <div v-show="currentTab == 1" class="scroll__wrap">
            <p>{{noFree}}</p>
        <div v-for="(room, i) in roomList.freeRoomList" :key="i" class="scroll--element">
          <b-card img-src="https://placekitten.com/280/200" img-top class="rooms">
            <h3>{{ room.roomName }}</h3>
            <p v-if="!room.secret"><img src="@/assets/icon/lock1.png" alt="" style="width:20px"></p>
            <p v-if="room.secret"><img src="@/assets/icon/lock2.png" alt="" style="width:20px"></p>
            <!-- <p>비번 : {{room.password}}</p> -->
            <!-- <p>방장 :{{room.memberId}}</p> -->
            <p>인원수 : {{ room.person }} / 6</p>
            <p>운동 : {{ workout_reverse[room.type] }}</p>
            <b-button class="rooms_btn" variant="primary"
            @click="enterroom({mode: room.mode, roomId: room.roomId})">들어가기</b-button>
          </b-card>
        </div>
      </div>
    </div>
    <div v-if="noFree" class="tab-content wrap">
      <div v-show="currentTab == 1" class="scroll__wrap">
        <h1>아직 운동중인 방이 없습니다!</h1>
      </div>
    </div>
    <div v-if="!noGame" class="tab-content wrap">
      <div v-show="currentTab == 2" class="scroll__wrap">
      <p>{{noStreaming}}</p>
        <div v-for="(room, i) in roomList.gameRoomList" :key="i" class="scroll--element">
          <b-card img-src="https://placekitten.com/280/200" img-top class="rooms">
            <h3>{{ room.roomName }}</h3>
            <p v-if="!room.secret"><img src="@/assets/icon/lock1.png" alt="" style="width:20px"></p>
            <p v-if="room.secret"><img src="@/assets/icon/lock2.png" alt="" style="width:20px"></p>
            <!-- <p>비번 : {{room.password}}</p> -->
            <p>인원수 : {{ room.person }} / 6</p>
            <p>운동 : {{ workout_reverse[room.type] }}</p>
            <!-- <p>방장 :{{room.memberId}}</p> -->
            <b-button class="rooms_btn" variant="primary"
            @click="enterroom({mode: room.mode, roomId: room.roomId})">들어가기</b-button>
          </b-card>
        </div>
      </div>
    </div>
    <div v-if="noGame" class="tab-content wrap">
      <div v-show="currentTab == 2" class="scroll__wrap">
        <h1>아직 운동중인 방이 없습니다!</h1>
      </div>
    </div>
  </div>
</template>

<script>
import { mapGetters, mapActions } from 'vuex';
// eslint--disable-next-line
const accounts = 'accounts';

export default {
  name: 'myTabs',
  components: {
  },
  data() {
    return {
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
        password: 'string',
        mode: '',
        roomName: '',
        type: '',
        link: '~~~',
      },
    };
  },
  computed: {
    ...mapGetters(accounts, ['roomList', 'userInfo', 'isLogin', 'noGame', 'noFree', 'noStreaming']),
  },
  methods: {
    ...mapActions(accounts, ['getRoomList', 'enterroom', 'makeRoom']),
    moveRoom(payload) {
      this.makeRoom(payload);
      document.body.removeAttribute('class');
      document.body.removeAttribute('style');
      document.getElementsByClassName('modal-backdrop')[0].remove();
    },
  },
};
</script>

<style scope>
.tab-menu {
  margin-top: 50px;
}

/* 기본 구조 */
.wrap {
  max-width: 100%;
  margin: 50px auto;
  background: #f8f8f8;
}

/* 가로 스크롤 적용 */
.scroll__wrap {
  overflow: auto;
  white-space: nowrap;
  font-size: 15px;
  margin-top: -30px;
}

.scroll--element {
  display: inline-block;
  line-height: 15px;
  width: 33%;
  height: 100%;
  padding-left: 10px;
}

.scroll--element+scroll--element {
  margin-left: 10px;
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

.rooms_btn {
  margin-top: 20px;
  width: 45%;
}
.roomdata {
  display: justify-content-center;
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
  margin-left: 50px;
}
</style>
