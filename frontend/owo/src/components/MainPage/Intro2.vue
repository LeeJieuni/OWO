<template>
  <!-- eslint-disable -->
  <div class="background">
    <div class="mybackground">
      <div v-show="burpee" class="forCarousel">
        <div>
          <div id="carouselExampleCaptions" class="carousel slide" data-bs-ride="carousel">
            <div class="carousel-indicators">
              <button type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide-to="0" class="active" aria-current="true" aria-label="Slide 1"></button>
              <button type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide-to="1" aria-label="Slide 2"></button>
              <button type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide-to="2" aria-label="Slide 3"></button>
              <button type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide-to="3" aria-label="Slide 4"></button>
              <button type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide-to="4" aria-label="Slide 5"></button>
              <button type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide-to="5" aria-label="Slide 6"></button>
            </div>
            <div class="carousel-inner">
              <div class="carousel-item active">
                <img class="carouselImg" src="@/assets/icon/burpee1.png" alt="...">
                <div class="carousel-caption d-none d-md-block" style="color:white;">
                  <div style="font-size:2.3vw;"><strong>#1.</strong> 머리부터 발끝까지 영상에 담기게 옆으로 서주세요.</div>
                </div>
              </div>
              <div class="carousel-item">
                <img class="carouselImg" src="@/assets/icon/burpee2.png" alt="...">
                <div class="carousel-caption d-none d-md-block" style="color:white;">
                  <div style="font-size:2.3vw;"><strong>#2.</strong> 수그리는 자세를 취해주세요.</div>
                </div>
              </div>
              <div class="carousel-item">
                <img class="carouselImg" src="@/assets/icon/burpee3.png" alt="...">
                <div class="carousel-caption d-none d-md-block" style="color:white;">
                  <div style="font-size:2.3vw;"><strong>#3.</strong> 엎드려 뻗쳐 자세를 취해주세요.</div>
                </div>
              </div>
              <div class="carousel-item">
                <img class="carouselImg" src="@/assets/icon/burpee2.png" alt="...">
                <div class="carousel-caption d-none d-md-block" style="color:white;">
                  <div style="font-size:2.3vw;"><strong>#4.</strong> 다시 수그리는 자세를 취해주세요.</div>
                </div>
              </div>
              <div class="carousel-item">
                <img class="carouselImg" src="@/assets/icon/burpee1.png" alt="...">
                <div class="carousel-caption d-none d-md-block" style="color:white;">
                  <div style="font-size:2.3vw;"><strong>#5.</strong> 다시 처음 자세로 돌아와 주세요.</div>
                </div>
              </div>
              <div class="carousel-item">
                <img class="carouselImg" src="@/assets/icon/burpee4.gif" alt="...">
                <div class="carousel-caption d-none d-md-block" style="color:white;">
                  <div style="font-size:2.3vw;"><strong>#6.</strong> 영상입니다.</div>
                </div>
              </div>
            </div>
            <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide="prev">
              <span class="carousel-control-prev-icon" aria-hidden="true"></span>
              <span class="visually-hidden">Previous</span>
            </button>
            <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide="next">
              <span class="carousel-control-next-icon" aria-hidden="true"></span>
              <span class="visually-hidden">Next</span>
            </button>
          </div>
        </div>
      </div>
      <div v-if="lunge" class="forCarousel">
        <div>
          <div id="carouselExampleCaptions1" class="carousel slide" data-bs-ride="carousel">
            <div class="carousel-indicators">
              <!-- eslint-disable -->
              <button type="button" data-bs-target="#carouselExampleCaptions1" data-bs-slide-to="0" class="active" aria-current="true" aria-label="Slide 1"></button>
              <button type="button" data-bs-target="#carouselExampleCaptions1" data-bs-slide-to="1" aria-label="Slide 2"></button>
              <button type="button" data-bs-target="#carouselExampleCaptions1" data-bs-slide-to="2" aria-label="Slide 3"></button>
              <button type="button" data-bs-target="#carouselExampleCaptions1" data-bs-slide-to="3" aria-label="Slide 4"></button>
            </div>
            <div class="carousel-inner">
              <div class="carousel-item active">
                <img class="carouselImg" src="@/assets/icon/lunge1.png" alt="...">
                <div class="carousel-caption d-none d-md-block" style="color:white;">
                  <div style="font-size:2.3vw;"><strong>#1.</strong>  다리를 앞뒤로 벌려 옆모습이 보이도록 서주세요.</div>
                </div>
              </div>
              <div class="carousel-item">
                <img class="carouselImg" src="@/assets/icon/lunge2.png" alt="...">
                <div class="carousel-caption d-none d-md-block" style="color:white;">
                  <div style="font-size:2.3vw;"><strong>#2.</strong> 무릎 각도에 유의해주세요.</div>
                </div>
              </div>
              <div class="carousel-item">
                <img class="carouselImg" src="@/assets/icon/lunge1.png" alt="...">
                <div class="carousel-caption d-none d-md-block" style="color:white;">
                  <div style="font-size:2.3vw;"><strong>#3.</strong> 다시 처음 자세로 움직여주세요.</div>
                </div>
              </div>
              <div class="carousel-item">
                <img class="carouselImg" src="@/assets/icon/lunge3.gif" alt="...">
                <div class="carousel-caption d-none d-md-block" style="color:white;">
                  <div style="font-size:2.3vw;"><strong>#4.</strong> 영상입니다.</div>
                </div>
              </div>
            </div>
            <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleCaptions1" data-bs-slide="prev">
              <span class="carousel-control-prev-icon" aria-hidden="true"></span>
              <span class="visually-hidden">Previous</span>
            </button>
            <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleCaptions1" data-bs-slide="next">
              <span class="carousel-control-next-icon" aria-hidden="true"></span>
              <span class="visually-hidden">Next</span>
            </button>
          </div>
        </div>
      </div>
      <div v-show="squat" class="forCarousel">
        <div >
          <div id="carouselExampleCaptions2" class="carousel slide" data-bs-ride="carousel">
            <div class="carousel-indicators">
              <!-- eslint-disable -->
              <button type="button" data-bs-target="#carouselExampleCaptions2" data-bs-slide-to="0" class="active" aria-current="true" aria-label="Slide 1"></button>
              <button type="button" data-bs-target="#carouselExampleCaptions2" data-bs-slide-to="1" aria-label="Slide 2"></button>
              <button type="button" data-bs-target="#carouselExampleCaptions2" data-bs-slide-to="2" aria-label="Slide 3"></button>
              <button type="button" data-bs-target="#carouselExampleCaptions2" data-bs-slide-to="3" aria-label="Slide 4"></button>
            </div>
            <div class="carousel-inner">
              <div class="carousel-item active">
                <img class="carouselImg" src="@/assets/icon/squat1.png" alt="...">
                <div class="carousel-caption d-none d-md-block" style="color:white;">
                  <div style="font-size:2.3vw;"><strong>#1.</strong> 다리를 어깨 너비보다 조금 벌리고 서주세요.</div>
                </div>
              </div>
              <div class="carousel-item">
                <img class="carouselImg" src="@/assets/icon/squat2.png" alt="...">
                <div class="carousel-caption d-none d-md-block" style="color:white;">
                  <div style="font-size:2.3vw;"><strong>#2.</strong> 고관절을 접고 무릎을 굽히며 천천히 내려가 주세요.</div>
                </div>
              </div>
              <div class="carousel-item">
                <img class="carouselImg" src="@/assets/icon/squat1.png" alt="...">
                <div class="carousel-caption d-none d-md-block" style="color:white;">
                  <div style="font-size:2.3vw;"><strong>#3.</strong> 다시 처음 자세로 돌아와 주세요.</div>
                </div>
              </div>
              <div class="carousel-item">
                <img class="carouselImg" src="@/assets/icon/squat3.gif" alt="...">
                <div class="carousel-caption d-none d-md-block" style="color:white;">
                  <div style="font-size:2.3vw;"><strong>#4.</strong> 영상입니다.</div>
                </div>
              </div>
            </div>
            <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleCaptions2" data-bs-slide="prev">
              <span class="carousel-control-prev-icon" aria-hidden="true"></span>
              <span class="visually-hidden">Previous</span>
            </button>
            <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleCaptions2" data-bs-slide="next">
              <span class="carousel-control-next-icon" aria-hidden="true"></span>
              <span class="visually-hidden">Next</span>
            </button>
          </div>
        </div>
      </div>
    </div>
    <div class="container">
      <div class="container-fluid">
        <div>
          <div class="d-flex row justify-content-start">
            <div style="z-index:5000;">
              <h1 class="h1-rem text-start">#오운완<span class="sub-title">오늘의 운동 완료</span></h1>
              <div class="content">
                <p class="text">오운완의 3대 운동과 함께 경쟁에 도전해 보세요 !</p>
                <p class="text">많은 운동 참여와 승리를 통해 레벨과 티어를 높여볼까요?</p>
              </div>
            </div>
            <div class="d-flex justify-content-start ms-5 mt-4 mb-5">
              <button style="border:none;" class="btn" @click="burpeeExercise()">
                <img style="width:6vw; height:auto;" src="@/assets/icon/burpee.png" alt="">
                <div style="color:white; font-size:2vh;">버피테스트</div>
              </button>
            </div>
            <div class="d-flex justify-content-start ms-5 mb-5">
              <button style="border:none;" class="btn" @click="lungeExercise()">
                <img style="width:6vw; height:auto;" src="@/assets/icon/lunge.png" alt="">
                <div style="color:white; font-size:2vh;">런지</div>
              </button>
            </div>
            <div class="d-flex justify-content-start ms-5">
              <button style="border:none;" class="btn" @click="squatExercise()">
                <img style="width:6vw; height:auto;" src="@/assets/icon/squat.png" alt="">
                <div style="color:white; font-size:2vh;">스쿼트</div>
              </button>
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
      this.burpee = true;
      this.lunge = false;
      this.squat = false;
    },
    lungeExercise() {
      this.burpee = false;
      this.lunge = true;
      this.squat = false;
    },
    squatExercise() {
      this.burpee = false;
      this.lunge = false;
      this.squat = true;
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
