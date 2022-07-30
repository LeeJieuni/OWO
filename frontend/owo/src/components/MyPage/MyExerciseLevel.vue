<template>
  <div>
    <div class="row">
        <p class="my-level-title">나의 운동 레벨</p>
      </div>
      <div class="my-level-info align-items-center">
        <div class="my-level-icon align-self-center">
          <img
            class="my-level-icon-img"
            :src="require(`@/assets/icon/tier${tierNum}.png`)"
            alt="tear1">
        </div>
        <div class="bar-info">
          <div class="progress bar">
            <div
              class="progress-bar progress-bar-striped progress-bar-animated"
              role="progressbar"
              aria-label="Example with label"
              v-bind:style="{ width: tearPercent, backgroundColor: tierColor }"
              :aria-valuenow="{tearPercentage}"
              aria-valuemin="0"
              aria-valuemax="100">{{tearPercent}}
            </div>
          </div>
          <div class="tear-info d-flex text-center align-items-end">
            <div class="tear-name">{{ tearName }}&nbsp;&nbsp;</div>
            <div class="me-auto user-percent">전체 사용자 중 상위 {{rankPercent}}%</div>
            <div class="user-point">20 / 100 P</div>
          </div>
        </div>
      </div>
  </div>
</template>
<script>
export default {
  name: 'MyExerciseLevel',
  components: {},
  data() {
    return {
      tearPercent: '25%',
      tearPercentage: 25,
      tierColor: '#919191',
      tierNum: 2,
      rankPercent: 70,
      tearName: '실버',
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
  /* 나의 운동 레벨 */
.my-level {
  /* height: 150px; */
  border-bottom: solid #DFDFDF 1px;
}

.my-level-title {
  text-align: left;
  padding-top: 30px;
  font-size: 18px;
  padding-left: 40px;
  padding-bottom: 10px;
}

.my-level-info {
  width: 90%;
  display: flex;
  padding-left: 40px;
  height: 80px;

}

.user-percent {
  font-family: 'LeferiPoint-WhiteA';
  font-size: 12px;
}

.my-level-icon {
  width: 100px;
}
.my-level-icon-img {
  width: 50px;
}
.bar-info {
  width: 100%;
  padding-left: 15px;
}

.tear-bar {
  background-color: #89621d;
}

.bar {
  width: 100%;
  height: 18px;
}

.progress-bar {
  font-size: 14px;
  text-shadow: 2px 2px 2px #2E2E2E;
  width: 90%
}
.tear-info {
  padding-top: 5px;
  font-size: 12px;
}
.tear-name {
  padding-top: 0px;
  font-size: 16px;
}
</style>
