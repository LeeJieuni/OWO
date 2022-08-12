<template>
  <div>
    <div class="row">
      <p class="my-weekly-goal-title">주간 목표 달성률</p>
    </div>
    <div class="my-weekly-goal-info container-fluid">
      <div class="d-flex justify-content-center">
        <div v-for="(name, i) in state.achieveName" :key="i" class="goal">
          <p class="goal-name">{{ name }}</p>
          <div class="progress-box">
            <circle-progress
            class="progress-bar" :percent="state.achieveRate[i]" :show-percent="true"
            :viewport="true" :size="130"/>
          </div>
          <p style="font-size: 12px; padding-top: 5px; color: #4E8AFF"
            v-if="state.achieveRate[i]==100">목표 달성 완료!</p>
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
    const achievementRate = computed(() => store.getters['record/achievementRate']);
    console.log('ㅇㅇ');
    console.log(achievementRate.value);
    const state = reactive({
      achieveName: [],
      achieveRate: [],
    });
    return {
      state,
      achievementRate,
    };
  },
  data() {
    return {
      // value: '90%',
      goals: [
        { name: '유산소', rate: 50 },
        { name: '스트레칭', rate: 80 },
        { name: '', rate: 0 },
      ],
    };
  },
  created() {
    // console.log('퍼센트 출력');
    const achieve = Object.keys(this.achievementRate);
    // }
    // console.log(achieve);
    // console.log(achieve.length);
    for (let i = 0; i < achieve.length; i += 1) {
      this.state.achieveName.push(achieve[i]);
      let realRate;
      if (this.achievementRate[achieve[i]] > 100) {
        realRate = 100;
      } else {
        realRate = this.achievementRate[achieve[i]];
      }
      this.state.achieveRate.push(realRate);
    }
    console.log(this.state.achieves);
  },
  moundted() {},
  unmounted() {},
  methods: {},
};
</script>

<style scoped>
.my-weekly-goal-title {
  text-align: left;
  padding-top: 30px;
  font-size: 18px;
  padding-left: 40px;
  padding-bottom: 10px;
  margin-bottom: 0;
}
.my-weekly-goal-info {
  padding: 0;
  margin: 0;
}
.progress-bar {
  padding: 20px;
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
