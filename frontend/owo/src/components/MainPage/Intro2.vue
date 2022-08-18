<template>
  <!-- eslint-disable -->
  <div class="background">
    <div class="container">
      <div class="container-fluid">
        <div>
          <div>
            <div class="d-flex justify-content-between">
              <div style="z-index:5000;">
                <h1 class="h1-rem text-start">#오운완<span class="sub-title">오늘의 운동 완료</span></h1>
                <div class="content">
                  <p class="text">오운완의 <span style="font-size: 2vw; font-weight:800;">3대 운동</span>과 함께 경쟁해 보세요 !</p>
                  <p class="text">많은 운동 참여와 승리를 통해 <span style="font-size: 2vw; font-weight:800;">티어</span>를 높여볼까요?</p>
                </div>
              </div>
              <button style="border:none; margin-right:4vw;" @click="burpeeExercise()" class="btn text-center">
                <div style="font-size:2vw; color:white;" class="text-center">튜토리얼&ensp;>></div>
              </button>
            </div>
            <div class="d-flex justify-content-around align-items-center ">
              <div class="ms-5 mb-5" style="margin-top:15vh;">
                <button style="border:none;" class="btn" @click="burpeeExercise()">
                  <img style="width:15vw; height:auto;" src="@/assets/icon/burpee.png" alt="">
                  <div style="color:white; font-size:4vh;">버피테스트</div>
                </button>
              </div>
              <div class="ms-5 mb-5" style="margin-top:15vh;">
                <button style="border:none;" class="btn" @click="lungeExercise()">
                  <img style="width:15vw; height:auto;" src="@/assets/icon/lunge.png" alt="">
                  <div style="color:white; font-size:4vh;">런지</div>
                </button>
              </div>
              <div class="ms-5 mb-5" style="margin-top:15vh;">
                <button style="border:none;" class="btn" @click="squatExercise()">
                  <img style="width:15vw; height:auto;" src="@/assets/icon/squat.png" alt="">
                  <div style="color:white; font-size:4vh;">스쿼트</div>
                </button>
              </div>
            </div>
          </div>
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
  components: {
  },
  data() {
    return {
      burpee: true,
      lunge: false,
      squat: false,
    };
  },
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
  methods: {
    burpeeExercise() {
      this.$router.push('/tutorial/burpee');
    },
    lungeExercise() {
      this.$router.push('/tutorial/lunge');
    },
    squatExercise() {
      this.$router.push('/tutorial/squat');
    },
  },
};
</script>

<style scoped>
.background {
  position:relative;
  background-color: #642AFB;
  border-bottom: #DFDFDF 0.5px solid;
  height: 100vh;
  color: white;
  padding: 50px;
  padding-top: 10vh;
}
  .container {
    min-width: 100%;
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

.forCarousel {
  width:100%;
  height:100%;
  display: flex;
  justify-content: center;
  align-items: start;
}

img {
  width:500px;
  height:450px;
}

.carouselImg {
  width:79.3vw;
  height:100vh;
}

.carousel-inner {
  justify-content: center;
  align-items: center;
}

.mybackground {
  position:absolute;
  font-family: 'LeferiPoint-WhiteObliqueA';
  top:0%;
  left:60%;
  transform: translate(-50%, 0);
}

.myzindex {
  z-index:500;
}
</style>
