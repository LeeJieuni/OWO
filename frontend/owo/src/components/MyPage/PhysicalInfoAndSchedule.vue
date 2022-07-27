<template>
  <div>
    <div class="container p-0 m-0">
      <div class="row row1 p-0 m-0">
        <div class="col col-6 my-physical-info">
          <PhysicalInfo/>
        </div>
        <div class="col col-6 my-calendar">
          <p class="my-calendar-title">운동 일지</p>
          <MyCalendar/>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import PhysicalInfo from './PhysicalInfo.vue';
import MyCalendar from './MyCalendar.vue';

export default {
  name: 'PhysicalInfoAndSchedule',
  components: { PhysicalInfo, MyCalendar },
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
