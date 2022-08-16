<template>
  <div class="background">
    <div class="container">
      <div class="container-fluid">
          <h1 class="h1-rem text-start">오운완<span class="sub-title">오늘의 운동 완료</span></h1>
        <div class="content">
          <p class="text">사람들과 함께 운동하고 경쟁하며 즐기는 <span class="owo"># 오운완</span> !</p>
          <p class="text">여러분들도 재미있게 운동을 즐기실 수 있습니다!</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { useStore } from 'vuex';
import { reactive } from 'vue';

export default {
  name: 'Intro2Component',
  components: {},
  setup() {
    const store = useStore();
    const state = reactive({
    });
    const refresh = () => {
      store.dispatch('accounts/fetchPoint');
    };
    return {
      state,
      refresh,
    };
  },
};
</script>

<style scoped>
.background {
  background-color: #642afb;
  border-bottom: #DFDFDF 0.5px solid;
  height: 100vh;
  color: white;
  padding: 0 50px;
  padding-top: 10vh;
}
  .container {
    min-width: 100vw;
  }
    .container-fluid {
      /* padding-top: 30px; */
      height: 100vh;
      margin: 0;
      padding: 0;
    }

.h1-rem{
  font-family: 'Recipekorea';
  font-size: 4.5vh;
  letter-spacing: 0.8rem;
  /* color: white; */
}
.sub-title {
  font-family: 'NanumSquareRound';
  padding-left: 20px;
  font-size: 2.5vh;
  font-weight: 800;
  text-align: left;
}
.content {
  letter-spacing: 0.1rem;
  padding-top: 2vh;
  text-align: left;
  font-size: 2.5vh;
  font-weight: 100;
}
</style>
