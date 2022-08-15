<template>
  <div class="body container-fluid">
    <h1 class="title">오늘의 운동왕은 누구?</h1>
    <b-card-group deck class="d-flex justify-content-evenly"
      v-if="isLogin === true">
      <b-card class="rounded-5 cards">
        <h5 class="card-title">어제 운동왕</h5>
        <div class="ranking d-flex justify-content-center"
          v-for="(rank, index) in rankingList" :key="index">
          <button :style="{backgroundColor: backColors[index]}"
            class="d-flex justify-content-between button">
            <span>{{index+1}}위</span><span>{{ rank.name }}</span>
            <span>{{ rank.score }}분</span>
          </button>
        </div>
        <hr />
        <div class="myranking">
          <button class="button" block pill size='lg'
          variant="outline-danger" v-if="isLogin && getMyRanking()">
            나의 랭킹 : {{ myranking }}위</button>
          <button class="button" block pill size='lg'
          variant="outline-danger" v-if="
          !isLogin">
            로그인 해주세요</button>
        </div>
      </b-card>
      <!--운동왕의 레코드  -->
      <b-card class="rounded-5 cards">
        <h5 class="card-title">{{ rankingList[0].name }}님의 record</h5>
        <div class="d-flex justify-content-center align-items-center"
          style="margin-bottom: 10px">
          <circle-progress class="progress-bar" :percent="achievement"
          :show-percent="true" :viewport="true" :size="160" />
        </div>
        <h5 class="card-bottom">연속 {{ lastingDay }}일 동안 운동하고 있어요!</h5>
      </b-card>
      <b-card class="rounded-5 cards">
        <h5 class="card-title">나의 운동 기록</h5>
        <MySmCalendar/>
      </b-card>
    </b-card-group>
    <!--로그인 전  화면 부분-->
    <!-- <a v-if="isLogin === false"><router-link to="/login">로그인 후 이용해주세요.</router-link></a> -->
    <b-card-group deck class="d-flex justify-content-evenly" v-if="isLogin === false">
        <b-card class="rounded-5 cards">
          <h5 class="card-title"> 저번 주 운동왕</h5>
          <div v-for="(ranking, i) in rankingList" :key="i" class="ranking">
            <button class="button">{{ i + 1 }}위 {{ ranking.name }}</button>
          </div>
          <hr />
          <div class="myranking">
            <router-link to="/login">
              <button class="button" block pill size='lg'
                variant="outline-danger">내 랭킹을 확인해 볼까요?</button>
            </router-link>
          </div>
        </b-card>
        <b-card class="rounded-5 cards">
          <h5 class="card-title">Records</h5>
          <div class="d-flex row justify-content-center" sytle="margin-bottom: 10px">
            <p>목표 대비 달성률</p>
            <circle-progress class="progress-bar" :percent="achievement"
            :show-percent="true" :viewport="true" :size="130"/>
          </div>
          <p class="card-bottom">나의 운동 기록을 확인해 볼까요?</p>
        </b-card>
        <b-card class="rounded-5 cards">
          <h5 class="card-title">나의 운동 기록</h5>
          <MySmCalendar/>
        </b-card>
      </b-card-group>
  </div>
</template>

<script>
import 'vue3-circle-progress/dist/circle-progress.css';
import CircleProgress from 'vue3-circle-progress';
import { mapActions, mapGetters, mapState } from 'vuex';
import MySmCalendar from '../MyPage/MySmCalendar.vue';

const mainpage = 'mainpage';
const accounts = 'accounts';

export default {
  beforeCreate() {
  },
  created() {
    console.log('days');
    console.log(this.lastingDay);
  },
  data() {
    return {
      backColors: ['#274C95', '#4E8AFF', '#98BBFF'],
    };
  },
  methods: {
    ...mapActions(mainpage, ['getMyRanking', 'getRankingList']),
  },
  components: {
    CircleProgress,
    MySmCalendar,
  },
  computed: {
    ...mapState(mainpage, ['rankingList', 'myranking']),
    ...mapGetters(accounts, ['isLogin', 'userInfo', 'lastingDay']),
    ...mapGetters(mainpage, ['achievement']),
  },
};
</script>

<style scoped>
.body {
  background-color: #642afb;
  height: 100vh;
}
p {
  font-size: 1.5vh;
}
/* .b-card-group {
  display: flex;
  justify-content: center;
} */
.card-title {
  font-size: 1vw;
  font-weight: 700;
  padding-top: 2vh;
  margin-bottom: 2vh;
}
.card-bottom {
  margin-top: 10px;
}

.cards {
  max-width: 25vw;
  height: 25vw;
  max-height: 50vh
}

.title {
  padding: 3vh 0 10vh 0;
  color: white;
  font-family: 'LeferiPoint-BlackObliqueA';
  font-size: 4vh;
}

.ranking {
  padding: 1vh;
}

.myranking {
  padding: 1vh;
}

.progress-bar {
  font-size: 40px;
  font-weight: 200;
}

.button {
  font-size: 1vw;
  font-weight: 400;
  width: 20vw;
  height: 5vh;
  text-align: center;
  border: none;
  border-radius: 5px;
  padding-left: 1.5vw;
  padding-right: 1.5vw;
  background-color: #246dfe;
  color: white;
}
.button span{
  line-height: 5vh;
}
</style>
