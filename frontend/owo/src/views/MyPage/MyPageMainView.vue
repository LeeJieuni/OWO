<template>
  <div class="wrap">
    <!--나의 운동 레벨-->
    <div class="my-level">
      <MyExerciseRecord/>
    </div>

    <!--운동 비율-->

    <div class="my-exercise-rate">
      <div class="row">
        <p class="my-rate-title">나의 운동 레벨</p>
      </div>
      <div class="my-rate-info align-items-center">
        <div class="bar-info">
          <div class="row d-flex justify-content-center mt-100">
            <div class="col-md-10">
              <div class="d-flex justify-content-center">
                <div v-for="(goal, i) in goals" :key="i">
                  <p class="goal-name">{{ goal }}</p>
                  <div class="progress-box" >
                    <div class="progress blue">
                      <span class="progress-left"><span class="progress-bar"></span></span>
                      <span class="progress-right"><span class="progress-bar"></span></span>
                      <div class="progress-value">90%</div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

  </div>
</template>
<script>
import MyExerciseRecord from '@/components/MyPage/MyExerciseRecord.vue';

export default {
  name: 'MyPageMainView',
  components: { MyExerciseRecord },
  data() {
    return {
      goals: [
        '유산소', '스트레칭',
      ],
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
.my-level {
  height: 250px;
  border-bottom: solid #DFDFDF 1px;
}

.my-level-title {
  text-align: left;
  padding-top: 50px;
  font-size: 24px;
  padding-left: 50px;
  padding-bottom: 10px;
}

.my-level-info {
  width: 90%;
  display: flex;
  padding-left: 50px;
  height: 80px;

}

.my-level-icon {
  width: 100px;
}
.my-level-icon-img {
  width: 60px;
}
.bar-info {
  width: 100%;
  padding-left: 20px;
}

.tear-name {
  padding-top: 10px;
  margin-bottom: 0px;
}

.bar {
  width: 100%;
  height: 25px;
}

.progress-bar {
  font-size: 15px;
  width: 90%
}
.tear-info {
  padding-top: 10px;
}
.tear-name {
  padding-top: 0px;
  font-size: 20px;
}

/* 운동 비율 */

.my-exercise-rate {
  height: 400px;
  border-bottom: solid #DFDFDF 1px;
}

.my-rate-title {
  text-align: left;
  padding-top: 50px;
  font-size: 24px;
  padding-left: 50px;
  padding-bottom: 10px;
}

.my-rate-info {
  width: 90%;
  display: flex;
  padding-left: 50px;
  height: 50px;

}

.mt-100{
  margin-top: 200px;
}
.progress {
  width: 130px;
  height: 130px !important;

  line-height: 80px;
  background: none;
  margin: 20px;
  box-shadow: none;
  position: relative;
}
.progress:after {
  content: "";
  width: 100%;
  height: 100%;
  border-radius: 50%;
  border: 12px solid #fff;
  position: absolute;
  top: 0;
  left: 0;
}
.progress>span {
  width: 50%;
  height: 100%;
  overflow: hidden;
  position: absolute;
  top: 0;
  z-index: 1;
}
.progress .progress-left {
  left: 0;
}
.progress .progress-bar {
  width: 100%;
  height: 100%;
  background: none;
  border-width: 12px;
  border-style: solid;
  position: absolute;
  top: 0;
}
.progress .progress-left .progress-bar {
  left: 100%;
  border-top-right-radius: 80px;
  border-bottom-right-radius: 80px;
  border-left: 0;
  -webkit-transform-origin: center left;
  transform-origin: center left;
}
.progress .progress-right {
  right: 0;
}
.progress .progress-right .progress-bar {
  left: -100%;
  border-top-left-radius: 80px;
  border-bottom-left-radius: 80px;
  border-right: 0;
  -webkit-transform-origin: center right;
  transform-origin: center right;
  animation: loading-1 1.8s linear forwards;
}
.progress .progress-value {
  width: 90%;
  height: 90%;
  border-radius: 50%;
  background: rgb(255, 255, 255);
  font-size: 24px;
  color: rgb(52, 52, 52);
  line-height: 135px;
  text-align: center;
  position: absolute;
  top: 5%;
  left: 5%;
}
.progress.blue .progress-bar {
  border-color: #4E8AFF;
}
.progress.blue .progress-left .progress-bar {
  animation: loading-2 1.5s linear forwards 1.8s;
}
.progress.yellow .progress-bar {
  border-color: #98BBFF;
}
.progress.yellow .progress-right .progress-bar {
  animation: loading-3 1.8s linear forwards;
}
.progress.yellow .progress-left .progress-bar {
  animation: none;
}
@keyframes loading-1 {
  0% {
    -webkit-transform: rotate(0deg);
    transform: rotate(0deg);
  }
  100% {
    -webkit-transform: rotate(180deg);
    transform: rotate(180deg);
  }
}
@keyframes loading-2 {
  0% {
    -webkit-transform: rotate(0deg);
    transform: rotate(0deg);
  }
  100% {
    -webkit-transform: rotate(144deg);
    transform: rotate(144deg);
  }
}
@keyframes loading-3 {
  0% {
    -webkit-transform: rotate(0deg);
    transform: rotate(0deg);
  }
  100% {
    -webkit-transform: rotate(135deg);
    transform: rotate(135deg);
  }
}

.progress-box {
  border: solid black 1px;
  width: 180px;
  height: 180px;
  border-radius: 20%;
  display: inline-block;
  margin-left: 10px;
  margin-right: 10px;
}

.goal-name {
  font-size: 20px;
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
