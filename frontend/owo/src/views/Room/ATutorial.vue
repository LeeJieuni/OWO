<template>
<div style="font-family: 'LeferiPoint-WhiteObliqueA';">
  <NavBar/>
  <div style="display:flex; position:relative;">
    <!-- eslint-disable-next-line -->
    <div class="d-flex" style="">
      <div class="d-flex row justify-content-start divbtn">
        <button style="border:none;" class="btn" @click="burpeeExercise()">
          <img style="width:5vw; height:auto;" src="@/assets/icon/burpee.png" alt="">
          <div style="color:#4e8aff; font-size:1vw;">버피테스트</div>
        </button>
        <button style="border:none;" class="btn" @click="lungeExercise()">
          <img style="width:5vw; height:auto;" src="@/assets/icon/lunge.png" alt="">
          <div style="color:#4e8aff; font-size:1vw;">런지</div>
        </button>
        <button style="border:none;" class="btn" @click="squatExercise()">
          <img style="width:5vw; height:auto;" src="@/assets/icon/squat.png" alt="">
          <div style="color:#4e8aff; font-size:1vw;">스쿼트</div>
        </button>
      </div>
      <div v-if="burpee" class="forCarousel">
        <div class="notebook" style="width:800px; height:450px; transform:translate(0, 7%);">
          <div id="carouselExampleCaptions" class="carousel slide" data-bs-ride="carousel">
            <div class="carousel-indicators">
              <!-- eslint-disable -->
              <button type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide-to="0" class="active" aria-current="true" aria-label="Slide 1"></button>
              <button type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide-to="1" aria-label="Slide 2"></button>
              <button type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide-to="2" aria-label="Slide 3"></button>
              <button type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide-to="3" aria-label="Slide 4"></button>
              <button type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide-to="4" aria-label="Slide 5"></button>
              <button type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide-to="5" aria-label="Slide 6"></button>
            </div>
            <div class="carousel-inner">
              <div class="carousel-item active">
                <img src="@/assets/icon/burpee1.png" alt="...">
                <div class="carousel-caption d-none d-md-block" style="color:white;">
                  <div style="font-size:26px;"><strong>#1.</strong>&ensp;머리부터 발끝까지 담기게 옆으로 서주세요.</div>
                </div>
              </div>
              <div class="carousel-item">
                <img src="@/assets/icon/burpee2.png" alt="...">
                <div class="carousel-caption d-none d-md-block" style="color:white;">
                  <div style="font-size:26px;"><strong>#2.</strong>&ensp;수그리는 자세를 취해주세요.</div>
                </div>
              </div>
              <div class="carousel-item">
                <img src="@/assets/icon/burpee3.png" alt="...">
                <div class="carousel-caption d-none d-md-block" style="color:white;">
                  <div style="font-size:26px;"><strong>#3.</strong>&ensp;엎드려 뻗쳐 자세를 취해주세요.</div>
                </div>
              </div>
              <div class="carousel-item">
                <img src="@/assets/icon/burpee2.png" alt="...">
                <div class="carousel-caption d-none d-md-block" style="color:white;">
                  <div style="font-size:26px;"><strong>#4.</strong>&ensp;다시 수그리는 자세를 취해주세요.</div>
                </div>
              </div>
              <div class="carousel-item">
                <img src="@/assets/icon/burpee1.png" alt="...">
                <div class="carousel-caption d-none d-md-block" style="color:white;">
                  <div style="font-size:26px;"><strong>#5.</strong>&ensp;다시 처음 자세로 돌아와 주세요.</div>
                </div>
              </div>
              <div class="carousel-item">
                <img src="@/assets/icon/burpee4.gif" alt="...">
                <div class="carousel-caption d-none d-md-block" style="color:white;">
                  <div style="font-size:26px;"><strong>#6.</strong>&ensp;영상입니다.</div>
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
        <div class="notebook" style="width:800px; height:450px; transform:translate(0, 7%);">
          <div id="carouselExampleCaptions" class="carousel slide" data-bs-ride="carousel">
            <div class="carousel-indicators">
              <!-- eslint-disable -->
              <button type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide-to="0" class="active" aria-current="true" aria-label="Slide 1"></button>
              <button type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide-to="1" aria-label="Slide 2"></button>
              <button type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide-to="2" aria-label="Slide 3"></button>
              <button type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide-to="3" aria-label="Slide 4"></button>
            </div>
            <div class="carousel-inner">
              <div class="carousel-item active">
                <img src="@/assets/icon/lunge1.png" alt="...">
                <div class="carousel-caption d-none d-md-block" style="color:white;">
                  <div style="font-size:26px;"><strong>#1.</strong>&ensp;다리를 앞뒤로 벌린 상태로 옆으로 서주세요.</div>
                </div>
              </div>
              <div class="carousel-item">
                <img src="@/assets/icon/lunge2.png" alt="...">
                <div class="carousel-caption d-none d-md-block" style="color:white;">
                  <div style="font-size:26px;"><strong>#2.</strong>&ensp;무릎 각도에 유의해주세요.</div>
                </div>
              </div>
              <div class="carousel-item">
                <img src="@/assets/icon/lunge1.png" alt="...">
                <div class="carousel-caption d-none d-md-block" style="color:white;">
                  <div style="font-size:26px;"><strong>#3.</strong>&ensp;다시 처음 자세로 움직여주세요.</div>
                </div>
              </div>
              <div class="carousel-item">
                <img src="@/assets/icon/lunge3.gif" alt="...">
                <div class="carousel-caption d-none d-md-block" style="color:white;">
                  <div style="font-size:26px;"><strong>#4.</strong>&ensp;영상입니다.</div>
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
      <div v-if="squat" class="forCarousel">
        <div class="notebook" style="width:800px; height:450px; transform:translate(0, 7%);">
          <div id="carouselExampleCaptions" class="carousel slide" data-bs-ride="carousel">
            <div class="carousel-indicators">
              <!-- eslint-disable -->
              <button type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide-to="0" class="active" aria-current="true" aria-label="Slide 1"></button>
              <button type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide-to="1" aria-label="Slide 2"></button>
              <button type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide-to="2" aria-label="Slide 3"></button>
              <button type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide-to="3" aria-label="Slide 4"></button>
            </div>
            <div class="carousel-inner">
              <div class="carousel-item active">
                <img src="@/assets/icon/squat1.png" alt="...">
                <div class="carousel-caption d-none d-md-block" style="color:white;">
                  <div style="font-size:26px;"><strong>#1.</strong>&ensp;다리를 어깨 너비보다 조금 벌리고 서주세요.</div>
                </div>
              </div>
              <div class="carousel-item">
                <img src="@/assets/icon/squat2.png" alt="...">
                <div class="carousel-caption d-none d-md-block" style="color:white;">
                  <div style="font-size:26px;"><strong>#2.</strong>&ensp;고관절을 접고 무릎을 굽히며 내려가 주세요.</div>
                </div>
              </div>
              <div class="carousel-item">
                <img src="@/assets/icon/squat1.png" alt="...">
                <div class="carousel-caption d-none d-md-block" style="color:white;">
                  <div style="font-size:26px;"><strong>#3.</strong>&ensp;다시 처음 자세로 돌아와 주세요.</div>
                </div>
              </div>
              <div class="carousel-item">
                <img src="@/assets/icon/squat3.gif" alt="...">
                <div class="carousel-caption d-none d-md-block" style="color:white;">
                  <div style="font-size:26px;"><strong>#4.</strong>&ensp;영상입니다.</div>
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
    </div>
   <router-link to="/roomtabs" class="h1-rem2" id="fixedbutton">
     <span class="h1-rem">운동하러</span> GO!</router-link>
  </div>
</div>
</template>
<script>
import NavBar from '@/components/NavBar.vue';

export default {
  name: 'ATutorialBurpee',
  components: {
    NavBar,
  },
  data() {
    return {
      burpee: true,
      lunge: false,
      squat: false,
    };
  },
  setup() {},
  created() {},
  moundted() {},
  unmounted() {},
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
.forCarousel {
  background-image:url('@/assets/icon/notebook.png');
  background-repeat: no-repeat;
  background-size:50.2vw 59.7vh;
  background-position: 0% 100%;
  background-color:white;
  width:50vw;
  height:60vh;
  position: fixed;
  top: 55%;
  left: 50%;
  transform: translate(-50%, -50%);
  display: flex;
  justify-content: center;
  align-items: start;
}

img {
  width:40vw;
  height:45vh;
}

.carousel-inner {
  justify-content: center;
  align-items: center;
}
#fixedbutton {
  position: fixed;
  bottom: 19px;
  right: 20px;
}
#fixedbutton:hover {
  position: fixed;
  bottom: 23px;
  color: #4E8AFF;
}
.h1-rem {
  font-size: 2vh;
  /* padding: 2vh; */
  font-family: 'Recipekorea';
  color: rgb(29, 29, 29);
}
.h1-rem2 {
  font-size: 3vh;
  padding: 2vh;
  font-family: 'Recipekorea';
  color: rgb(29, 29, 29);
  text-decoration: none;
  /* text-shadow: 3px 3px #4E8AFF; */
}

/* 마우스 커서 */
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
.divbtn {
  position: fixed;
  top:30vh;
  left:0vw;
}

</style>
