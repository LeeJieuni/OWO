<template>
  <div>
    <Carousel></Carousel> <br>
    <div class="intro_text p-5">
      <h1><span class="owo"># 오운완</span>과 함께 시작해볼까요?</h1><br><br>
      <p class="first-intro-text"><span class="owo"># 오운완</span>과 함께 하신 회원님들입니다!</p>
      <p class="first-intro-text">여러분들도 재미있게 운동을 즐기실 수 있습니다!</p> <br>
      <div class="btns">
        <b-button pill variant="primary" class="btn-lg">Start with us!</b-button>
        <b-button pill class="btn-lg">What is <span class="owo"># 오운완</span>?</b-button>
      </div>
    </div>
    <SecondIntro/>
    <TodayKing/>
    <transition name="bounce" mode="out-in">
      <MyModal></MyModal>
    </transition>
  </div>
</template>>

<script>
import Carousel from '@/components/MainPage/Carousel.vue';
import SecondIntro from '@/components/MainPage/SecondIntro.vue';
import TodayKing from '@/components/MainPage/TodayKing.vue';
import MyModal from '@/components/MainPage/MyModal.vue';

export default {
  components: {
    Carousel,
    TodayKing,
    SecondIntro,
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

.first-intro-text {
  font-size: 18px;
}
.btns {
  padding-top: 50px;
  padding-bottom: 50px;
}
</style>
