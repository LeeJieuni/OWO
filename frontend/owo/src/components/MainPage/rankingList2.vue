<template>
  <div class="body container-fluid">
    <h1 class="title">오늘의 운동왕 ✨</h1>
    <b-card-group deck class="d-flex justify-content-evenly mt-5"
      v-if="isLogin === true">

      <!--운동왕 rank-->
      <b-card class="rounded-5 cards">
        <h1 class="card-title">어제 운동왕</h1>
        <div class="ranking d-flex justify-content-center"
          v-for="(rank, index) in rankingList" :key="index">
          <button class="ranks d-flex justify-content-between button">
            <span><img :src="require(`@/assets/icon/medal${ index + 1 }.png`)"
              alt="" style="width: 25px;"></span><span>{{ rank.name }}</span>
            <span>{{ rank.score }}분</span>
          </button>
        </div>
        <hr class="hr" />
        <div class="ranking d-flex justify-content-center">
          <button class="button ranks" block pill size='lg'
          variant="outline-danger" v-if="isLogin && getMyRanking()">
            나의 랭킹 : {{ myranking }}위</button>
        </div>
      </b-card>

      <!-- 본인의 레코드  -->
      <b-card class="rounded-5 cards">
        <router-link to="/mypage/main">
          <p class="card-title">{{ rankingList[0].name }}님의 record</p>
          <div class="d-flex justify-content-center align-items-center"
            style="margin-bottom: 10px">
            <circle-progress class="progress-bar" :percent="(lastingDay / 7)*100"
            :show-percent="true" :viewport="true" :size="240" />
          </div>
        </router-link>
          <p class="card-bottom m-5 fs-5" style="text-decoration: none;">
            연속 {{ lastingDay }}일 동안 운동하고 있어요!</p>
      </b-card>

      <!--캘린더-->
        <b-card class="rounded-5 cards">
          <router-link to="/mypage/schedule">
            <p class="card-title" style="text-decoration-line: none;">나의 운동 기록</p>
          </router-link>
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
  /* background-color: #642afb; */
  height: 800px;
  color: black;
}
.card-title {
  font-family: 'yg-jalnan';
  color: #585858;
  font-size: 24px;
  font-weight: 700;
  padding-top: 20px;
  margin-bottom: 40px;
  text-decoration: none;
}
.card-bottom {
  margin-top: 10px;
}
.cards {
  position: relative;
  max-width: 500px;
  min-height: 500px;
  /* max-height: 50vh */
  box-shadow: #DFDFDF 0 0 10px;
  padding-left: 20px;
  padding-right: 20px;
}
.cards:hover {
  box-shadow: #642afb 0 0 3px;
}
.cards:hover .card-title {
  color: black;
}
.text {
  bottom: 2vh;
  left: 10%;
  position: absolute;
}

.title {
  padding: 8vh 0 5vh 5vh;
  /* color: white; */
  font-family: 'LeferiPoint-BlackObliqueA';
  font-size: 4vh;
  text-align: left;
}
.ranks span {
  font-size: 18px;
  font-weight: 700;
  color: #2b2b2b;
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
  height: 50px;
  text-align: center;
  border: solid 1px #9f9f9f;
  border-radius: 5px;
  padding: 10px 30px 10px 30px;
  background-color: white;
  box-shadow: #DFDFDF 2px 2px 2px;
  /* color: white; */
}
.hr {
  margin-left: 70px;
  margin-right: 70px;
}
router-link:hover {
  text-decoration: none;
}
router-link:hover {
  cursor: pointer;
}
</style>
