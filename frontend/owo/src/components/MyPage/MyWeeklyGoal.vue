<template>
  <div>
    <div class="row">
      <p class="my-weekly-goal-title">주간 목표 달성률</p>
    </div>
    <div class="my-weekly-goal-info container-fluid">
      <div class="d-flex justify-content-center">
        <template v-for="(goal, i) in goals" :key="i">
          <div v-if="goal.name!==''" class="goal">
            <p class="goal-name">{{ goal.name }}</p>
            <div class="progress-box">
              <circle-progress
              class="progress-bar" :percent="30" :show-percent="true"
              :viewport="true" :size="130"/>
            </div>
          </div>
          <div v-else class="goal">
            <p class="no-goal-name">목표가 없습니다.</p>
            <div class="progress-box">
              <router-link to="/mypage/update" class="no-goal">
                <p class="no-goal-text">목표를 추가해 보세요!</p>
              </router-link>
            </div>
          </div>
        </template>
      </div>
    </div>
  </div>
</template>
<script>
import 'vue3-circle-progress/dist/circle-progress.css';
import CircleProgress from 'vue3-circle-progress';

export default {
  name: 'MyWeeklyGoal',
  components: { CircleProgress },
  data() {
    return {
      value: '90%',
      goals: [
        { name: '유산소' },
        { name: '스트레칭' },
        { name: '' },
      ],
    };
  },
  setup() {},
  created() {},
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
  padding-bottom: 20px;
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
