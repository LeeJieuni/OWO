<template>
  <div>
    <Carousel></Carousel> <br>
    <div class="intro_text">
      <h1><span class="owo"># 오운완</span>과 함께 시작해볼까요?</h1><br><br>
      <p class="first-intro-text"><span class="owo"># 오운완</span>과 함께 하신 회원님들입니다!</p>
      <p class="first-intro-text">여러분들도 재미있게 운동을 즐기실 수 있습니다!</p> <br>
      <div class="btns">
        <b-button pill variant="primary" class="btn-lg start-btn">Start with us!</b-button>
        <b-button pill class="btn-lg">What is <span class="owo"># 오운완</span>?</b-button>
      </div>
    </div>
    <SecondIntro/>
    <TodayKing/>
    <transition name="bounce" mode="out-in">
      <MyModal></MyModal>
    </transition>
    <FooFooter/>
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
.start-btn {
    margin-right: 30px;
}

.btn-lg {
  height: 40px;
  text-align: center;
  line-height: 10px;
  font-size: 18px;
}
.owo {
  font-weight: 600;
  text-shadow: #7b7b7b 1px 1px;
}

.first-intro-text {
  font-size: 18px;
  line-height: 15px;
}
.btns {
  padding-top: 5px;
  padding-bottom: 20px;
}
</style>
