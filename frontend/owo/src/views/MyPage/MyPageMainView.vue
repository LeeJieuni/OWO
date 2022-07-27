<template>
  <div class="wrap">
    <!--나의 운동 레벨-->
    <div class="my-level">
      <MyExerciseLevel/>
    </div>

    <!-- 나의 운동 비율 -->
    <div class="my-exercise-rate">
      <MyExerciseRate/>
    </div>

    <!--주간 목표 달성량-->
    <div class="weekly-goal-achivement-rate">
      <MyWeeklyGoal/>
    </div>

    <!-- 신체 정보 분석 & 운동일지 -->
    <div class="physical-info-analys">
      <physical-info-and-schedule/>
    </div>

  </div>
</template>
<script>
import MyExerciseLevel from '@/components/MyPage/MyExerciseLevel.vue';
import MyExerciseRate from '@/components/MyPage/MyExerciseRate.vue';
import MyWeeklyGoal from '@/components/MyPage/MyWeeklyGoal.vue';
import PhysicalInfoAndSchedule from '@/components/MyPage/PhysicalInfoAndSchedule.vue';

export default {
  name: 'MyPageMainView',
  components: {
    MyExerciseLevel, MyWeeklyGoal, MyExerciseRate, PhysicalInfoAndSchedule,
  },
  data() {
    return {
      sample: '',
    };
  },
  setup() {},
  created() {},
  mounted() {},
  unmounted() {},
  methods: {},
};
</script>

<style scoped>

/* 나의 운동 레벨 */

/* 운동 비율 */

.my-level {
  height: 220px;
  border-bottom: solid #DFDFDF 1px;
}

.my-exercise-rate {
  height: 220px;
  border-bottom: solid #DFDFDF 1px;
}
.weekly-goal-achivement-rate {
  height: 350px;
  border-bottom: solid #DFDFDF 1px;
}

.physical-info-analys {
  height: 400px;
  padding: 0px;
  margin: 0px;
}

/* Color Palette */
/*
  Main: 4E8AFF
  Sub1: 274C95
  Sub2: 98BBFF
  Sub3: CEDFFF
  Extra1: DE7474
  Extra2: C5A180
  Extra3: CCB9A8
  Basic1: 2E2E2E
  Basic2: 828282
  Basic3: DFDFDF
  BackGround1: F6F7F9
  Background2: 383838 투명도 60% === background-color: #38383899;
  ddd
*/
</style>
