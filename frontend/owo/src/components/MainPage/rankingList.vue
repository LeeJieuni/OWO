<template>
  <div class="body container-fluid">
    <h1 class="title">오늘의 운동왕은 누구?</h1>
    <b-card-group deck class="d-flex justify-content-evenly">
      <b-card class="rounded-5 cards">
        <h4 class="card-title">어제 운동왕</h4>
        <div class="ranking1">
          <b-button block pill size='lg' variant="primary">
            1위 {{userrank.username}}님  {{userrank.workoutTime}}분</b-button>
        </div>
        <div class="ranking2">
          <b-button block pill size='lg' variant="outline-secondary">
            2위 {{userrank.username}}님 {{userrank.workoutTime}}분</b-button>
        </div>
        <div class="ranking3">
          <b-button block pill size='lg' variant="outline-danger">
            3위 {{userrank.username}}님 {{userrank.workoutTime}}분</b-button>
        </div>
        <hr/>
        <div class="myranking">
          <b-button block pill size='lg' variant="outline-danger">
            {{userrank.rate}}위 {{userrank.username}}님 {{userrank.workoutTime}}분</b-button>
        </div>
      </b-card>

      <b-card class="rounded-5 cards">
        <h4 class="card-title">운동왕의 record</h4>
        <h2>{{userrank.username}}</h2>
        <div class="d-flex justify-content-center" sytle="margin-bottom: 10px">
          <circle-progress
            class="progress-bar"
            :percent="80"
            :show-percent="true"
            :viewport="true"
            :size="100"/>
        </div>
        <h3 style="margin-top:10px">연속 3일동안 운동하고 있어요!</h3>
      </b-card>
      <b-card class="rounded-5 cards">
        <h4 class="card-title">나의 운동 기록</h4>
        <MySmCalendar/>
      <!-- <v-calendar ></v-calendar> -->
      <!-- <v-date-picker is-range v-model="date" is-expanded></v-date-picker> -->
      </b-card>
    </b-card-group>
  </div>
</template>

<script>
import { useStore } from 'vuex';
import { reactive, computed } from 'vue';
import 'vue3-circle-progress/dist/circle-progress.css';
import CircleProgress from 'vue3-circle-progress';
import MySmCalendar from '../MyPage/MySmCalendar.vue';

export default {
  components: {
    CircleProgress,
    MySmCalendar,
  },
  setup() {
    const store = useStore();
    const user = computed(() => store.getters['accounts/userInfo']);
    // const physical = computed(() => store.getters['accounts/physicalInfo']);
    const state = reactive({
    });
    return {
      value: 'test',
      userrank: {
        rate: 1,
        username: '백한나',
        workoutTime: 10,
      },
      date: new Date(),
      state,
      user,
    };
  },
};
</script>

<style scoped>
.body {
  background-color: #CEDFFF;
  height: 100%;
}
/* .b-card-group {
  display: flex;
  justify-content: center;
} */
.cards {
  max-width: 400px;
  max-height: 400px;
}
.title {
  padding: 30px 0 50px 0;
}
.ranking1 {
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
