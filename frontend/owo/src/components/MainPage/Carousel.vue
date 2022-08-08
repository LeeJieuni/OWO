<template>
<div class="container-fluid">
  <div class="title">
    <h3 class="lg-title" style="text-align: left; margin-left: 5px;">
      실제<span class="owo-text">#오운완</span>을 사용하시는 회원님입니다.
    </h3>
  </div>
  <div class="carousel">
    <Carousel :autoplay="2000" :items-to-show="3" :wrap-around="true">
      <Slide v-for="slide in 10" :key="slide">
        <div class="carousel__item">{{ slide }}</div>
      </Slide>
      <template #addons>
      </template>
    </Carousel>
  </div>
    <div class="content">
        <h1><span class="owo"># 오운완</span>과 함께 시작해볼까요?</h1>
        <p class="first-intro-text"><span class="owo"># 오운완</span>과 함께 하신 회원님들입니다!</p>
        <p class="first-intro-text">여러분들도 재미있게 운동을 즐기실 수 있습니다!</p>
        <!-- <div class="btns"> -->
        <router-link v-if="!isLogin" to="/login">
          <b-button pill variant="outline-secondary" class="btn-lg start-btn">
            Start with us!
          </b-button>
        </router-link>
        <a v-else to="">
          <b-button pill variant="outline-secondary" class="btn-lg start-btn">
            Start with us!
          </b-button>
        </a>
        <!-- </div> -->
      </div>
</div>
</template>

<script>
import { defineComponent, reactive, computed } from 'vue';
import { Carousel, Slide } from 'vue3-carousel';
import { useStore } from 'vuex';

import 'vue3-carousel/dist/carousel.css';

export default defineComponent({
  name: 'AutoPlay',
  components: {
    Carousel,
    Slide,
  },
  setup() {
    const store = useStore();
    const isLogin = computed(() => store.getters['accounts/isLogin']);
    const state = reactive({
      selected: '',
      options: [
        { text: '없음', value: '1' },
        { text: '약간', value: '2' },
        { text: '중간', value: '3' },
        { text: '많음', value: '4' },
        { text: '매우 많음', value: '5' },
      ],
    });
    return {
      state,
      isLogin,
    };
  },
});
</script>

<style scope>
.owo-text {
  /* color: #4E8AFF; */
  font-size: 32px;
}
.owo {
  /* color: #4E8AFF; */
  font-weight: 600;
  text-shadow: #7b7b7b 1px 1px;
}
.container-fluid {
  padding-top: 30px;
  height: 100%;
}
.carousel {
  /* padding-top: 10px; */
  padding-bottom: 10px;
}
.carousel__item {
  min-height: 380px;
  width: 100%;
  background-color: var(--vc-clr-primary);
  color:  var(--vc-clr-white);
  font-size: 20px;
  border-radius: 8px;
  display: flex;
  justify-content: center;
  align-items: center;
}
.lg-title {
  font-weight: 800;
  font-size: 28px;
}
.start-btn {
  border-radius: 30%;
  margin-bottom: 30px;
}

.carousel__slide {
  padding: 10px;
}

.carousel__prev,
.carousel__next {
  box-sizing: content-box;
  /* border: 5px solid white; */
}
.first-intro-text {
  font-size: 18px;
  line-height: 15px;
}
</style>
