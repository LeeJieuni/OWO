<template>
  <div>
    <div class="row">
        <p class="my-rate-title">나의 운동 비율</p>

      </div>
      <div class="my-rate-info align-items-center">
        <div class="bar-info">
          <div class="progress bar">
              <template v-for="(record, recordIndex) in records" :key="recordIndex">
                <div v-if="record.exerciseHours!==0"
                  class="progress-bar"
                  role="progressbar" aria-label="Segment one"
                  :style="{width: record.exerciseRate + '%',
                    backgroundColor: colors[recordIndex] }"
                  :aria-valuenow=record.exerciseRate
                  aria-valuemin="0" aria-valuemax="100" >
                  <span class="rate-name">
                    {{record.name}} <span class="rate-rate">{{record.exerciseRate}}%</span>
                  </span>
                </div>
              </template>
          </div>
          <p class="text-start mt-2 fs-6">{{ user.name }}님은 유산소를 많이 하셨군요!</p>
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
      user: {
        name: '한나',
      },
      sumOfExerciseHours: 0,
      records: [
        { name: '유산소', exerciseHours: 24, exerciseRate: null },
        { name: '헬스', exerciseHours: 20, exerciseRate: null },
        { name: '스트레칭', exerciseHours: 15, exerciseRate: null },
        { name: '맨몸운동', exerciseHours: 12, exerciseRate: null },
        { name: '요가', exerciseHours: 11, exerciseRate: null },
        { name: '필라테스', exerciseHours: 0, exerciseRate: null },
        { name: '기타', exerciseHours: 0, exerciseRate: null },
      ],
      colors: [
        '#6842FF', '#3C48E8', '#4E8AFF', '#3CA3E8', '#42E5FF', '#31E8CE', '#36FFAA',
      ],
    };
  },
  setup() {},
  created() {
    for (let i = 0; i < this.records.length; i += 1) {
      this.sumOfExerciseHours += this.records[i].exerciseHours;
    }
    for (let i = 0; i < this.records.length; i += 1) {
      this.records[i].exerciseRate = Math.round(
        (this.records[i].exerciseHours / this.sumOfExerciseHours) * 100,
      );
    }
  },
  moundted() {},
  unmounted() {},
  methods: {
  },
  computed() {

  },
};
</script>

<style scoped>
  /* 나의 운동 레벨 */

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

.bar {
  width: 100%;
  height: 20px;
}
.progress {
  height: 30px;
}
.progress-bar {
  font-size: 14px;
  text-shadow: 2px 2px 2px #2E2E2E;
  width: 90%;
}
.rate-info {
  padding-top: 10px;
}
.rate-name {
  font-size: 18px;
}
.rate-rate {
  font-size: 14px;
}
.sm-text {
  font-size: 10px;
}
.md-text {
  font-size: 12px;
}
</style>
