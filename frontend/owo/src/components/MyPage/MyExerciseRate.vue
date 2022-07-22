<template>
  <div>
    <div class="row">
        <p class="my-rate-title">나의 운동 비율</p>
      </div>
      <div class="my-rate-info align-items-center">
        <div class="bar-info">
          <div class="progress bar">
            <div class="progress bar">
              <div class="progress-bar"
                role="progressbar" aria-label="Segment one"
                style="width: 40%" aria-valuenow="40"
                aria-valuemin="0" aria-valuemax="100" >헬스 40%
              </div>
              <div class="progress-bar bg-light"
                role="progressbar" aria-label="Segment one"
                style="width: 30%" aria-valuenow="30"
                aria-valuemin="0" aria-valuemax="100">스트레칭 30%
              </div>
              <div class="progress-bar bg-secondary"
                role="progressbar" aria-label="Segment one"
                style="width: 20%" aria-valuenow="20"
                aria-valuemin="0" aria-valuemax="100">?? 20%
              </div>
              <div class="progress-bar bg-black"
                role="progressbar" aria-label="Segment one"
                style="width: 10%" aria-valuenow="10"
                aria-valuemin="0" aria-valuemax="100">요가 10%
              </div>
            </div>
          </div>
          <div class="tear-info d-flex text-center align-items-end">
            <div class="tear-name">브론즈&nbsp;&nbsp;</div>
            <div class="me-auto user-percent">ssss</div>
            <div class="user-point">20 / 100 P</div>
          </div>
        </div>
      </div>
  </div>
</template>
<script>
export default {
  name: 'MyExerciseRate',
  components: {},
  data() {
    return {
      records: [
        { exerciseName: '헬스', exerciseHours: 24 },
        { exerciseName: '스트레칭', exerciseHours: 15 },
        { exerciseName: '요가', exerciseHours: 10 },
        { exerciseName: '헬스', exerciseHours: 40 },
      ],
    };
  },
  setup() {},
  created() {},
  moundted() {},
  unmounted() {},
  methods: {},
  computed() {},
};
</script>

<style scoped>
  /* 나의 운동 레벨 */
.my-rate {
  height: 250px;
  border-bottom: solid #DFDFDF 1px;
}

.my-rate-title {
  text-align: left;
  padding-top: 50px;
  font-size: 22px;
  padding-left: 50px;
  padding-bottom: 5px;
}

.my-rate-info {
  width: 90%;
  display: flex;
  padding-left: 50px;
  height: 80px;

}

.my-rate-icon {
  width: 100px;
}
.my-rate-icon-img {
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
