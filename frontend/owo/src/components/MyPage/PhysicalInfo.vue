<template>
  <div class="col">
    <div class="row">
      <p class="my-physical-info-title">신체 정보 분석</p>
    </div>
    <div class="row bmi">
      <div class="my-physical-info-graph align-items-center">
        <p class="md-title">체질량지수</p>
        <div class="bar-info">
          <div class="progress ">
            <div class="progress-bar"
              role="progressbar" aria-label="Segment 1"
              style="width: 40%; background-color: #CEDFFF;" aria-valuenow="40"
              aria-valuemin="0" aria-valuemax="100" >저체중
            </div>
            <div class="progress-bar"
              role="progressbar" aria-label="Segment 2"
              style="width: 30%; background-color: #98BBFF;" aria-valuenow="30"
              aria-valuemin="0" aria-valuemax="100">정상
            </div>
            <div class="progress-bar"
              role="progressbar" aria-label="Segment 3"
              style="width: 20%; background-color: #4E8AFF;" aria-valuenow="20"
              aria-valuemin="0" aria-valuemax="100">과체중
            </div>
            <div class="progress-bar"
              role="progressbar" aria-label="Segment 4"
              style="width: 10% background-color: #274C95;" aria-valuenow="10"
              aria-valuemin="0" aria-valuemax="100">비만
            </div>
          </div>
        </div>
        <div class="bmi-solution pt-2 text-end">
          <p class="rg-font" v-if="user.bmi < 18.5">
            저체중입니다. 헬스로 근육을 찌워보시는 건 어떨까요?
          </p>
          <p class="rg-font" v-if="user.bmi >= 18.5 && user.bmi < 23">
            Good! {{ user.name }}님은 정상 체중이네요!
          </p>
          <p class="rg-font" v-if="user.bmi >= 23 && user.bmi < 25">
            과체중 솰라솔라
          </p>
          <p class="rg-font" v-if="user.bmi >= 25">
            비만 어쩌구
          </p>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  name: 'PhysicalInfo',
  components: {},
  data() {
    return {
      styleBarPercent: '"width: 30%"',
      user: {
        name: '한나',
        bmi: 20,
      },
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

.my-physical-info-title {
  text-align: left;
  padding-top: 50px;
  font-size: 22px;
  padding-bottom: 20px;
  padding-left: 50px;
}

.my-physical-info-graph {
  padding-left: 60px;
  padding-right: 60px;
  align-items: flex-start;
}

.bar {
  height: 30px;
  border-radius: 10;
}
.md-title {
  font-size: 18px;
  text-align: left;
}
.sm-title {
  text-align: left;
}

.rg-font {
  font-family: 'LeferiBaseType-RegularA';
}
</style>
