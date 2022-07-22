<template>
  <div>
    <div class="row">
        <p class="my-level-title">나의 운동 레벨</p>
      </div>
      <div class="my-level-info align-items-center">
        <div class="my-level-icon align-self-center">
          <img
            class="my-level-icon-img"
            src="@/assets/icon/exercise.png" alt="tear1">
        </div>
        <div class="bar-info">
          <div class="progress bar">
            <div
              class="progress-bar bg-warning progress-bar-striped progress-bar-animated"
              role="progressbar"
              aria-label="Example with label"
              style="width: 25%;"
              aria-valuenow="25"
              aria-valuemin="0"
              aria-valuemax="100">25%
            </div>
          </div>
          <div class="tear-info d-flex text-center align-items-end">
            <div class="tear-name">브론즈&nbsp;&nbsp;</div>
            <div class="me-auto user-percent">전체 사용자 중 상위 10%</div>
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
      a: '',
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
  font-size: 22px;
  padding-left: 50px;
  padding-bottom: 0px;
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
  height: 20px;
}

.progress-bar {
  font-size: 14px;
  text-shadow: 2px 2px 2px #2E2E2E;
  width: 90%
}
.tear-info {
  padding-top: 10px;
}
.tear-name {
  padding-top: 0px;
  font-size: 20px;
}
</style>
