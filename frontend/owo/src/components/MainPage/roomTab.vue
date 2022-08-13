 <template>
  <div class="tabs">
    <div class="tab-menu d-flex">
      <b-button-group class="mx-1" v-for="(tab, index) in tabs" :key="index"
        v-bind:class="{ active: currentTab === index }">
        <b-button lg="4" size="lg" v-on:click="currentTab = index"
        variant="outline-primary">{{ tab }}
        </b-button>
      </b-button-group>

      <b-button lg="4" size="lg" v-b-modal.modal-makeSession variant="outline-primary"
      v-if="isLogin"> 방 만들기
      </b-button>
      <b-modal id="modal-makeSession"
      title="#오운완의 운동방 생성하기"
      >
      <div class="d-flex justify-content-center">
        <form class="makesessionForm">
             <div class="roomdata">
               <b-input-group prepend="방 이름" class="roomdata_input">
                 <b-form-input type="text" v-model="roomdatas.roomName"></b-form-input>
               </b-input-group>
               <b-input-group prepend="모드" class="roomdata_input">
                 <b-form-select :options="mode" v-model="roomdatas.mode"></b-form-select>
                 <div>{{roomdatas.mode}}</div>
               </b-input-group>
               <b-input-group prepend="운동 종류" class="roomdata_input">
                  <b-form-select :options="Object.keys(workout)"
                  v-model="workoutType"></b-form-select>
               </b-input-group>
                 <b-input-group v-if="roomdatas.secret" prepend="비밀번호" class="roomdata_input">
                   <b-form-input type="password" v-model="roomdatas.password"></b-form-input>
                 </b-input-group>
                &ensp; <label for="checkbox">
                     <input type="checkbox" v-model="roomdatas.secret" style="padding:10px;"> 비밀방 생성
                   </label>
              <div class="btns d-flex row justify-content-center">
                <b-button variant="primary" @click="(makeRoom(roomdatas), closeModal())"
                style="width: 80%">운동하기</b-button>
              </div>
             </div>
           </form>
      </div>
    </b-modal>
    </div>
    <div class="tab-content wrap">
      <div v-show="currentTab == 0" class="scroll__wrap">
        <div v-for="(room, i) in roomList.streamingRoomList" :key="i" class="scroll--element">
          <b-card img-src="https://placekitten.com/280/200" img-top class="rooms">
            <h3>{{ room.roomName }}</h3>
            <p>인원수 : {{ room.person }}</p>
            <p>비밀여부 : {{ room.secret }}</p>
            <!-- <p>비번 : {{room.password}}</p> -->
            <p>운동 : {{ room.type }}</p>
            <!-- <p>방장 :{{room.memberId}}</p> -->
            <b-button class="rooms_btn" variant="primary"
            @click="enterstreamingroom(room.roomId)">들어가기</b-button>
          </b-card>
        </div>
      </div>
    </div>
    <div class="tab-content wrap">
      <div v-show="currentTab == 1" class="scroll__wrap">
        <div v-for="(room, i) in roomList.freeRoomList" :key="i" class="scroll--element">
          <b-card img-src="https://placekitten.com/280/200" img-top class="rooms">
            <h3>{{ room.roomName }}</h3>
            <p>인원수 : {{ room.person }}</p>
            <p>비밀여부 : {{ room.secret }}</p>
            <!-- <p>비번 : {{room.password}}</p> -->
            <p>운동 : {{ room.type }}</p>
            <!-- <p>방장 :{{room.memberId}}</p> -->
            <b-button class="rooms_btn" variant="primary"
            @click="enterfreeroom(room.roomId)">들어가기</b-button>
          </b-card>
          <p>{{userInfo.id}}</p>
        </div>
      </div>
    </div>
    <div class="tab-content wrap">
      <div v-show="currentTab == 2" class="scroll__wrap">
        <div v-for="(room, i) in roomList.gameRoomList" :key="i" class="scroll--element">
          <b-card img-src="https://placekitten.com/280/200" img-top class="rooms">
            <h3>{{ room.roomName }}</h3>
            <p>인원수 : {{ room.person }}</p>
            <p>비밀여부 : {{ room.secret }}</p>
            <!-- <p>비번 : {{room.password}}</p> -->
            <p>운동 : {{ room.type }}</p>
            <!-- <p>방장 :{{room.memberId}}</p> -->
            <b-button class="rooms_btn" variant="primary"
            @click="entercompetitionroom(room.roomId)">들어가기</b-button>
          </b-card>
          <p>{{room.roomId}}</p>
        </div>
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
      workoutType: '',
      currentTab: 3,
      mode: ['FREE', 'GAME', 'STREAMING'],
      tabs: ['운동방', '자유방', '경쟁방'],
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
    ...mapGetters(accounts, ['roomList', 'userInfo', 'isLogin']),
  },
  methods: {
    ...mapActions(accounts, ['getRoomList', 'entercompetitionroom', 'enterfreeroom', 'enterstreamingroom', 'makeRoom']),
    closeModal(event) {
      event.preventDefault();
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
