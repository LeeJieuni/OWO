<template>
  <div class="body container-fluid">
    <h1 class="title">오늘의 운동왕은 누구?</h1>
    <b-card-group deck class="d-flex justify-content-evenly">
      <b-card class="rounded-5 cards">
        <h5 class="card-title">어제 운동왕</h5>
        <div class="ranking" v-for="(rank, index) in rankingList" :key="index">
          <b-button block pill size='lg' variant="primary">
          {{index + 1}}위 : {{rank.name}}</b-button>
        </div>
        <hr/>
        <div class="myranking">
          <b-button block pill size='lg' variant="outline-danger">
            나의 랭킹 : {{myranking.data.data.ranking}}위</b-button>
        </div>
      </b-card>
      <b-card class="rounded-5 cards">
        <h5 class="card-title">운동왕의 record</h5>
        <h2>test2</h2>
        <div class="d-flex justify-content-center" sytle="margin-bottom: 10px">
          <circle-progress
            class="progress-bar"
            :percent="80"
            :show-percent="true"
            :viewport="true"
            :size="100"/>
        </div>
        <h5>연속 3일동안 운동하고 있어요!</h5>
      </b-card>
      <b-card class="rounded-5 cards">
        <h5 class="card-title">나의 운동 기록</h5>
        <MySmCalendar/>
      <!-- <v-calendar ></v-calendar> -->
      <!-- <v-date-picker is-range v-model="date" is-expanded></v-date-picker> -->
      </b-card>
    </b-card-group>
  </div>
</template>

<script>
import 'vue3-circle-progress/dist/circle-progress.css';
import CircleProgress from 'vue3-circle-progress';
import { mapActions, mapState } from 'vuex';
// import MySmCalendar from '../MyPage/MySmCalendar.vue';

const mainpage = 'mainpage';

export default {
  components: {
    CircleProgress,
    // MySmCalendar,
  },
  computed: {
    ...mapState(mainpage, ['rankingList', 'myranking']),
  },
  methods: {
    ...mapActions(mainpage, ['getMyRanking', 'getRankingList']),
  },
  created() {
    this.getRankingList();
    this.getMyRanking();
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
.ranking2 {
  padding: 10px;
}
.ranking3 {
  padding: 10px;
}
.myranking {
  padding: 10px;
}
.progress-bar {
  font-size: 40px;
  font-weight: 200;
}
</style>
