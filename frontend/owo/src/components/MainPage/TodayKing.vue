<template>
  <div class="todayKing">
    <h1 class="lg-title text-center my-5">오늘의 운동왕은 누구~?</h1>
    <div class="wrapper d-flex justify-content-evenly">

      <div class="my-exercise-analy">
        <h5 class="sm-title">나의 운동 분석</h5>
        <div>

        </div>
      </div>

      <div class="king-record">
        <h5 class="sm-title">"{{king}}"의 Record</h5>

      </div>

      <div class="week-rank">
        <h5 class="sm-title">{{month}}월 {{weekNum}}째주 운동왕</h5>
      </div>

    </div>
  </div>
</template>

<script>
export default {
  components: {},
  data() {
    return {
      king: '운동왕',
      month: 7,
      weekNum: '둘',
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
.todayKing {
  margin: 50px auto;
  text-align: left;
  height: 100vh;
}
.sm-title {
  font-weight: 900;
}
.lg-title {
  font-weight: 900;
}
</style>
