<template>
  <div>
    <div class="row mb-3">
      <div class="col my-weekly-goal-title">
        <p class=" p-0 m-0">나의 운동 비율</p>
      </div>
      <div class="col refresh">
        <!--eslint-disable-next-line-->
        <svg @click="refresh()" class="refresh-icon" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="24" height="24"><path fill="none" d="M0 0h24v24H0z"/><path d="M5.463 4.433A9.961 9.961 0 0 1 12 2c5.523 0 10 4.477 10 10 0 2.136-.67 4.116-1.81 5.74L17 12h3A8 8 0 0 0 6.46 6.228l-.997-1.795zm13.074 15.134A9.961 9.961 0 0 1 12 22C6.477 22 2 17.523 2 12c0-2.136.67-4.116 1.81-5.74L7 12H4a8 8 0 0 0 13.54 5.772l.997 1.795z" fill="rgba(130,130,130,1)"/></svg>
      </div>
    </div>
    <div class="my-weekly-goal-info container-fluid">
      <div class="d-flex justify-content-center">
        <div v-for="(achieveName, i) in achievementRate.achieveNames" :key="i" class="goal">
          <p class="goal-name">{{ achieveName }}</p>
          <div class="progress-box">
            <circle-progress
            class="progress-bar"
            :percent="(achievementRate.achieveRates[i] > 100)?
              100 : achievementRate.achieveRates[i]"
            :show-percent="true"
            :viewport="true" :size="130"/>
          </div>
          <p style="font-size: 12px; padding-top: 5px; color: #4E8AFF"
            v-if="achievementRate.achieveRates[i]>=100">목표 달성 완료!</p>
        </div>
        <div v-if="achievementRate.achieveNames.length==0" class="pt-5">
          <router-link class="no-record" to="/">목표를 추가하고 운동을 시작해볼까요?</router-link>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import 'vue3-circle-progress/dist/circle-progress.css';
import { useStore } from 'vuex';
import { reactive, computed } from 'vue';
import CircleProgress from 'vue3-circle-progress';

export default {
  name: 'MyWeeklyGoal',
  components: { CircleProgress },
  setup() {
    const store = useStore();
    // const user = computed(() => store.getters['accounts/userInfo']);
    // const physical = computed(() => store.getters['accounts/physicalInfo']);
    const achievementRate = computed(() => store.getters['accounts/achievementRate']);
    const refresh = function () {
      store.dispatch('accounts/fetchAchievementRate');
    };
    const state = reactive({
      // achieveName: [],
      // achieveRate: [],
    });
    // onUpdated() {
    // };
    return {
      state,
      achievementRate,
      refresh,
    };
  },
  created() {
  },
  moundted() {},
  unmounted() {},
  methods: {},
};
</script>

<style scoped>
.no-record {
  text-decoration: none;
  font-weight: 900;
  /* font-size: 20px; */
}
.no-record:hover {
  color: orange;
}
.my-weekly-goal-title {
  text-align: left;
  padding-top: 30px;
  font-size: 18px;
  padding-left: 40px;
  padding-bottom: 10px;
  margin-bottom: 0;
}
.refresh {
  margin-top: 30px;
  margin-right: 80px;
  padding: 0;
  /* padding-left: 40px; */
  /* padding-bottom: 10px; */
  font-size: 18px;
  text-align: end;
  /* border: solid black 1px; */
}
.refresh-icon:hover {
  cursor: pointer;
}
.my-weekly-goal-info {
  padding: 0;
  margin: 0;
}
.progress-bar {
  padding: 20px;
}
.current-counter {
  font-size: 30px;
}
.progress-box {
  border: solid black 1px;
  width: 130px;
  height: 130px;
  border-radius: 20%;
  /* display: inline-block; */
}
.goal {
  padding: 0 20px;
}
.goal-name {
  font-size: 15px;
}
.no-goal-name {
  font-size: 15px;
  color: gray;
}
.no-goal-text {
  line-height: 130px;
}
.no-goal {
  /* text-decoration: none; */
  color: gray;
}
</style>
