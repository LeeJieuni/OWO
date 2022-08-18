<template>
  <div class="body container-fluid">
    <h1 class="title">오늘의 운동왕 ✨</h1>
    <b-card-group deck class="d-flex justify-content-evenly"
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
        <router-link to="/mypage/main">
          <p class="card-title">나의 RECORD</p>
            <div class="d-flex justify-content-center align-items-center"
              style="margin-bottom: 10px">
              <circle-progress class="progress-bar" :percent="(lastingDay / 7)*100"
              :show-percent="true" :viewport="true" :size="180" />
            </div>
          <h5 class="card-bottom">연속 {{ lastingDay }}일 동안 운동하고 있어요!</h5>
        </router-link>
      </b-card>

      <!--캘린더-->
      <b-card class="rounded-5 cards">
        <router-link to="/mypage/schedule">
            <p class="card-title" style="text-decoration-line: none;">나의 운동 기록</p>
        </router-link>
        <MySmCalendar/>
      </b-card>
    </b-card-group>
    <!--로그인 전  화면 부분-->
    <!-- <a v-if="isLogin === false"><router-link to="/login">로그인 후 이용해주세요.</router-link></a> -->
    <b-card-group deck class="d-flex justify-content-evenly" v-if="isLogin === false">
        <b-card class="rounded-5 cards">
          <h5 class="card-title"> 저번 주 운동왕</h5>
          <div class="ranking d-flex justify-content-center"
          v-for="(rank, index) in rankingList" :key="index">
          <button class="ranks d-flex justify-content-between button">
            <span><img :src="require(`@/assets/icon/medal${ index + 1 }.png`)"
              alt="" style="width: 25px;"></span><span>{{ rank.name }}</span>
            <span>{{ rank.score }}분</span>
          </button>
        </div>
          <hr class="hr" />
          <div class="myranking text">
            <router-link to="/login">
              <p>내 랭킹을 확인해 볼까요?</p>
            </router-link>
          </div>
        </b-card>

        <!-- 본인의 레코드  -->
        <b-card class="rounded-5 cards">
          <h5 class="card-title">오운완의 RECORD</h5>
          <div class="d-flex row justify-content-center" sytle="margin-bottom: 10px">
            <p>목표 대비 달성률</p>
            <circle-progress class="progress-bar" :percent="percents"
            :show-percent="true" :viewport="true" :size="130"/>
          </div>
          <div class="text">
            <router-link to="/login">
              <p>내 운동 목표 달성률을 확인해 볼까요?</p>
            </router-link>
          </div>
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
      percents: this.lastingDay,
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
    ...mapGetters(accounts, ['isLogin', 'userInfo', 'achievementRate', 'lastingDay']),
  },
};
</script>

<style scoped>
.body {
  background-color: #642afb;
  /* height: 700px; */
  color: black;
}
p {
  font-size: 13px;
}
.card-title {
  font-family: 'yg-jalnan';
  color: #585858;
  /* color: #585858; */
  font-size: 24px;
  font-weight: 700;
  font-size: 1vw;
  font-weight: 700;
  padding-top: 15px;
  margin-bottom: 3vh;
}
.card-bottom {
  margin-top: 10px;
}

.cards {
  position: relative;
  max-width: 20vw;
  min-height: 20vw;
  height: 20vw;
  max-height: 50vh;
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
.hr {
  margin-left: 70px;
  margin-right: 70px;
}
.button {
  font-size: 1vw;
  font-weight: 400;
  width: 250px;
  height: 50px;
  text-align: center;
  border: solid 1px #9f9f9f;
  border-radius: 5px;
  padding: 10px 30px 10px 30px;
  background-color: white;
  box-shadow: #DFDFDF 2px 2px 2px;
  /* color: white; */
}
.button span{
  line-height: 5vh;
}
router-link:hover {
  text-decoration: none;
}
router-link:hover {
  cursor: pointer;
}
</style>
