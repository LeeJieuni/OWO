<template>
  <div class="body container-fluid">
    <h1 class="title">오늘의 운동왕은 누구?</h1>
    <b-card-group deck class="d-flex justify-content-evenly">
      <b-card class="rounded-5 cards">
        <h5 class="card-title">어제 운동왕</h5>
        <div class="ranking">
          <b-button block pill size='lg' variant="outline-primary"
          class="d-flex justify-content-between button" >
          <span>1위 : </span><span>{{rankingList[0].name}}</span>
          <span>{{rankingList[0].score}}분</span></b-button>
        </div>
        <div class="ranking">
          <b-button block pill size='lg' variant="outline-primary"
          class="d-flex justify-content-between button">
          <span>2위 : </span><span>{{rankingList[1].name}}</span>
          <span>{{rankingList[1].score}}분</span></b-button>
        </div>
        <div class="ranking">
          <b-button block pill size='lg' variant="outline-primary"
          class="d-flex justify-content-between button">
          <span>3위 : </span><span>{{rankingList[2].name}}</span>
          <span>{{rankingList[2].score}}분</span></b-button>
        </div>
        <hr/>
        <div class="myranking">
          <b-button class="button" block pill size='lg'
          variant="outline-danger" v-if="isLogin && getMyRanking()">
            나의 랭킹 : {{myranking}}위</b-button>
            <b-button class="button" block pill size='lg' variant="outline-danger" v-if="
            !isLogin">
             로그인 해주세요</b-button>
        </div>
      </b-card>
      <b-card class="rounded-5 cards">
        <h5 class="card-title">{{rankingList[0].name}}님의 record</h5>
        <div class="d-flex justify-content-center" sytle="margin-bottom: 10px">
          <circle-progress
            class="progress-bar"
            :percent="80"
            :show-percent="true"
            :viewport="true"
            :size="220"/>
        </div>
        <h5 class="card-bottom">연속 {{lastingDay.data}}일 동안 운동하고 있어요!</h5>
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
// import MySmCalendar from '../MyPage/MySmCalendar.vue';

const mainpage = 'mainpage';
const accounts = 'accounts';

export default {
  components: {
    CircleProgress,
    MySmCalendar,
  },
  computed: {
    ...mapState(mainpage, ['rankingList', 'myranking', 'lastingDay']),
    ...mapGetters(accounts, ['isLogin']),
  },
  methods: {
    ...mapActions(mainpage, ['getMyRanking', 'getRankingList']),
  },
  created() {
    this.getRankingList();
  },
};
</script>

<style scoped>
.body {
  background-color: #CEDFFF;
  height: 700px;
}
/* .b-card-group {
  display: flex;
  justify-content: center;
} */
.card-title {
  font-size: 24px;
  font-weight: 700;
  margin-bottom: 30px;
}
.card-bottom {
  margin-top: 35px;
}
.cards {
  max-width: 400px;
  max-height: 400px;
}
.title {
  padding: 30px 0 50px 0;
}
.ranking {
  padding: 10px;
}
.myranking {
  padding: 10px;
}
.progress-bar {
  font-size: 40px;
  font-weight: 200;
}
.button {
  font-size: 20px;
  font-weight: bold;
  width: 100%;
}
</style>
