<template>
  <div>
    <Carousel></Carousel>
    <div class="intro_text">
      <h1><span class="owo"># 오운완</span>과 함께 시작해볼까요?</h1>
      <p><span class="owo"># 오운완</span>과 함께 하신 회원님들입니다!</p>
      <p>여러분들도 재미있게 운동을 즐기실 수 있습니다!</p>
      <div class="btns">
        <b-button pill variant="primary" class="btn-lg">Start with us!</b-button>
        <b-button pill class="btn-lg">What is <span class="owo"># 오운완</span>?</b-button>
      </div>
    </div>
    <TodayKing/>
    <transition name="bounce" mode="out-in">
      <MyModal></MyModal>
    </transition>
  </div>
</template>>

<script>
import Carousel from '@/components/MainPage/Carousel.vue';
import TodayKing from '@/components/MainPage/TodayKing.vue';
import MyModal from '@/components/MainPage/MyModal.vue';

export default {
  components: {
    Carousel,
    TodayKing,
    MyModal,
  },
};
</script>

<style scoped>
.btn-lg {
    margin-right: 30px;
}
.owo {
  font-weight: 600;
  text-shadow: #7b7b7b 1px 1px;
}
</style>
