<template>
  <div>
    <div class="container p-0 m-0">
      <div class="row row1 p-0 m-0">
        <div class="col col-6 my-physical-info">
          <div class="row">
            <p class="my-physical-info-title">신체 정보 분석</p>
          </div>
          <div class="row bmi">
            <p class="sm-title">체질량지수</p>
            <div class="my-physical-info-graph align-items-center">
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
              <div class="bmi-solution pt-2">
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
        <div class="col col-6 my-calendar">
          <p class="my-calendar-title">운동 일지</p>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  name: 'PhysicalInfoAnalys',
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

.my-physical-info {
  height: 100%;
  border-right: solid #DFDFDF 1px;
}

.my-physical-info-title, .my-calendar-title {
  text-align: left;
  padding-top: 50px;
  font-size: 22px;
  padding-bottom: 10px;
}

.my-physical-info, .my-calendar {
  padding-left: 50px;
  padding-right: 50px;
  align-items: flex-start;
}

.bar {
  height: 30px;
  border-radius: 10;
}

.sm-title {
  text-align: left;
}

.rg-font {
  font-family: 'LeferiBaseType-RegularA';
}
</style>
