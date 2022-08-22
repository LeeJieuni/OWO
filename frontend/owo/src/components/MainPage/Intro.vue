<template>
  <div class="m-0 p-0 background">
    <div class="container">
      <div class="container-fluid">
        <div class="bg-video">
          <video class="bg-video__content" autoplay muted loop>
            <source src="@/assets/video/yogaVideo.mp4" type="video/mp4" />
          </video>
        </div>
        <div class="content">
          <h1 class="h1-rem">오운완과 함께 시작해볼까요?</h1>
          <!-- <p class="first-intro-text fs-4">오운완과 함께 하신 회원님들입니다!</p> -->
          <p class="text">집 밖으로 나가기는 귀찮지만 자기관리는 놓치고 싶지 않다면?</p>
          <p class="text">사람들과 함께 <span class="owo">운동</span>하고
            <span class="owo">경쟁</span>하며 즐기는 <span class="owo"> #오운완</span>을 통해</p>
          <p class="text">여러분들도 재미있게 운동을 즐기실 수 있습니다</p>
          <!-- <div class="btns"> -->
          <div class="btns">
            <router-link v-if="!isLogin" to="/login">
              <button class="start-btn btn-12">
                <span>Login</span><span>Start With Us!</span>
              </button>
            </router-link>
            <router-link v-if="isLogin" to="/roomtabs">
              <button class="start-btn btn-12">
                <span>Let's WorkOut!</span><span>Start With Us!</span>
              </button>
            </router-link>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { useStore } from 'vuex';
import { reactive, computed } from 'vue';

export default {
  name: 'IntroComponent',
  components: {},
  setup() {
    const store = useStore();
    const isLogin = computed(() => store.getters['accounts/isLogin']);
    const state = reactive({
    });
    const refresh = () => {
      store.dispatch('accounts/fetchPoint');
    };
    return {
      state,
      refresh,
      isLogin,
    };
  },
};
</script>

<style scoped>
.background {
  background-color: white;
  border-bottom: #DFDFDF 0.5px solid;
  height: 97vh;
  margin:0;
  padding:0;
}
  .container {
    min-width: 100%;
    margin:0;
    padding: 0;
  }
    .container-fluid {
      /* padding-top: 30px; */
      height: 100vh;
      margin: 0;
      padding: 0;
    }

.bg-video {
  /* position: absolute; */
  /* top: 0;
  left: 0; */
  max-width: 100%;
  height: 50%;
  z-index: -1;
  opacity: 0.65;
}
.bg-video__content {
  height: 100%;
  width: 100%;
  object-fit: cover;
}

.h1-rem{
  font-family: 'Recipekorea';
  /* color: white; */
  margin-top: 5vh;
}
.owo-text {
  /* color: #4E8AFF; */
  font-size: 32px;
}
.owo {
  /* color: #4E8AFF; */
  font-weight: 600;
  text-shadow: #7b7b7b 1px 1px;
}
.carousel {
  /* padding-top: 10px; */
  padding-bottom: 10px;
}
.carousel__item {
  /* min-height: 300px; */
  height: 37vh;
  width: 99%;
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

.carousel__prev,
.carousel__next {
  box-sizing: content-box;
  /* border: 5px solid white; */
}
.content {
  padding: 1.2em;
  height: 40vh;
}
.h1-rem {
  font-size: 4vh;
  padding: 2vh;
}
.text {
  font-size: 2vh;
  font-weight: 700;
  line-height: 1.7vh;
  padding: 0.3em;
}

.start-btn {
  /* font-family: 'yg-jalnan'; */
  margin-top: 1vh;
  width: 200px;
  height: 40px;
  color: #fff;
  border-radius: 30px;
  padding: 10px 25px;
  /* font-family: 'Lato', sans-serif; */
  font-weight: 400;
  font-size: 20px;
  background: transparent;
  cursor: pointer;
  transition: all 0.3s ease;
  position: relative;
  display: inline-block;
  box-shadow:inset
    2px 2px 2px 0px rgba(255,255,255,.5),
    7px 7px 20px 0px rgba(0,0,0,.1),
    4px 4px 5px 0px rgba(0,0,0,.1);
  outline: none;
}

.btn-12{
  position: relative;
  right: 20px;
  bottom: 20px;
  border:none;
  box-shadow: none;
  width: 200px;
  height: 50px;
  line-height: 45px;
  -webkit-perspective: 230px;
  perspective: 230px;
}
.btn-12 span {
  background: rgb(0,172,238);
  background: linear-gradient(0deg, rgb(173, 188, 241)0%, rgb(55, 68, 217) 100%);
  display: block;
  position: absolute;
  width: 200px;
  height: 50px;
  box-shadow:inset 2px 2px 2px 0px rgba(255,255,255,.5),
    7px 7px 20px 0px rgba(0,0,0,.1),
    4px 4px 5px 0px rgba(0,0,0,.1);
  border-radius: 20px;
  margin:0;
  text-align: center;
  -webkit-box-sizing: border-box;
  -moz-box-sizing: border-box;
  box-sizing: border-box;
  -webkit-transition: all .3s;
  transition: all .3s;
}
.btn-12 span:nth-child(1) {
  box-shadow:
    -7px -7px 20px 0px #fff9,
    -4px -4px 5px 0px #fff9,
    7px 7px 20px 0px #0002,
    4px 4px 5px 0px #0001;
  -webkit-transform: rotateX(90deg);
  -moz-transform: rotateX(90deg);
  transform: rotateX(90deg);
  -webkit-transform-origin: 50% 50% -20px;
  -moz-transform-origin: 50% 50% -20px;
  transform-origin: 50% 50% -20px;
}
.btn-12 span:nth-child(2) {
  -webkit-transform: rotateX(0deg);
  -moz-transform: rotateX(0deg);
  transform: rotateX(0deg);
  -webkit-transform-origin: 50% 50% -20px;
  -moz-transform-origin: 50% 50% -20px;
  transform-origin: 50% 50% -20px;
}
.btn-12:hover span:nth-child(1) {
  box-shadow:inset 2px 2px 2px 0px rgba(255,255,255,.5),
    7px 7px 20px 0px rgba(0,0,0,.1),
    4px 4px 5px 0px rgba(0,0,0,.1);
  -webkit-transform: rotateX(0deg);
  -moz-transform: rotateX(0deg);
  transform: rotateX(0deg);
}
.btn-12:hover span:nth-child(2) {
  box-shadow:inset 2px 2px 2px 0px rgba(255,255,255,.5),
    7px 7px 20px 0px rgba(0,0,0,.1),
    4px 4px 5px 0px rgba(0,0,0,.1);
  color: transparent;
  -webkit-transform: rotateX(-90deg);
  -moz-transform: rotateX(-90deg);
  transform: rotateX(-90deg);
}
.btn-12:active {
  top:-16px;
}
a, a:link {
  cursor: url('@/assets/cursor/cursor-hover.cur'), auto;
}
a, a:link, a:hover, a:focus, a:active{
  cursor: url('@/assets/cursor/cursor-hover.cur'), auto;
}
a:active {
  cursor: url('@/assets/cursor/cursor-hover.cur'), auto;
}
button:hover {
  cursor: url('@/assets/cursor/cursor-hover.cur'), auto;
}
html, body {
  cursor: url('@/assets/cursor/cursor.cur');
}
</style>
