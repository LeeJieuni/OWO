<template>
  <div>
    <Carousel></Carousel>
    <div class="intro_text">
      <h1>#오운완과 함께 시작해볼까요?</h1>
      <p>#오운완과 함께 하신 회원님들입니다!</p>
      <p>여러분들도 재미있게 운동을 즐기실 수 있습니다!</p>
      <div class="btns">
        <b-button pill variant="primary" class="btn-lg">Start with us!</b-button>
        <b-button pill class="btn-lg">What is 오운완?</b-button>
      </div>
    </div>
    <TodayKing/>
  </div>
</template>>

<script>
import Carousel from '@/components/MainPage/Carousel.vue';
import TodayKing from '@/components/MainPage/TodayKing.vue';

export default {
  components: {
    Carousel,
    TodayKing,
  },
};
</script>

<style>
.btn-lg {
    margin-right: 30px;
}
</style>
