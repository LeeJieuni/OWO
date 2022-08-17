<template>
<div style="font-family: 'LeferiPoint-WhiteObliqueA';">
  <NavBar/>
  <div style="display:flex; justify-content:center; position:relative;">
    <div style="position:fixed; top:50%; left:50%; transform:translate(-50%, -47%);">
      <div class="d-flex justify-content-around">
        <button style="border:none;" class="btn" @click="burpeeExercise()">
          <img style="width:100px; height:auto;" src="@/assets/icon/burpee.png" alt="">
          <div style="color:#4e8aff;">버피테스트</div>
        </button>
        <button style="border:none;" class="btn" @click="lungeExercise()">
          <img style="width:100px; height:auto;" src="@/assets/icon/lunge.png" alt="">
          <div style="color:#4e8aff;">런지</div>
        </button>
        <button style="border:none;" class="btn" @click="squatExercise()">
          <img style="width:100px; height:auto;" src="@/assets/icon/squat.png" alt="">
          <div style="color:#4e8aff;">스쿼트</div>
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
                  <div style="font-size:50px; font-weight:bold;">버피테스트 #1</div>
                  <div style="font-size:28px;">머리부터 발끝까지 잡히게 자세를 취해주세요.</div>
                </div>
              </div>
              <div class="carousel-item">
                <img src="@/assets/icon/burpee2.png" alt="...">
                <div class="carousel-caption d-none d-md-block" style="color:white;">
                  <div style="font-size:50px; font-weight:bold;">버피테스트 #2</div>
                  <div style="font-size:28px;">수구리는 자세를 취해주세요.</div>
                </div>
              </div>
              <div class="carousel-item">
                <img src="@/assets/icon/burpee3.png" alt="...">
                <div class="carousel-caption d-none d-md-block" style="color:white;">
                  <div style="font-size:50px; font-weight:bold;">버피테스트 #3</div>
                  <div style="font-size:28px;">엎드려벋쳐 자세를 취해주세요.</div>
                </div>
              </div>
              <div class="carousel-item">
                <img src="@/assets/icon/burpee2.png" alt="...">
                <div class="carousel-caption d-none d-md-block" style="color:white;">
                  <div style="font-size:50px; font-weight:bold;">버피테스트 #4</div>
                  <div style="font-size:28px;">다시 수구리는 자세를 취해주세요.</div>
                </div>
              </div>
              <div class="carousel-item">
                <img src="@/assets/icon/burpee1.png" alt="...">
                <div class="carousel-caption d-none d-md-block" style="color:white;">
                  <div style="font-size:50px; font-weight:bold;">버피테스트 #5</div>
                  <div style="font-size:28px;">다시 처음자세로 움직여주세요.</div>
                </div>
              </div>
              <div class="carousel-item">
                <img src="@/assets/icon/burpee4.gif" alt="...">
                <div class="carousel-caption d-none d-md-block" style="color:white;">
                  <div style="font-size:50px; font-weight:bold;">버피테스트 #6</div>
                  <div style="font-size:28px;">영상입니다.</div>
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
        <div class="notebook" style="width:800px; height:450px; transform:translate(0, 8%);">
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
                  <div style="font-size:50px; font-weight:bold;">런지 #1</div>
                  <div style="font-size:28px;">머리부터 발끝까지 잡히게 발 벌려 자세를 취해주세요.</div>
                </div>
              </div>
              <div class="carousel-item">
                <img src="@/assets/icon/lunge2.png" alt="...">
                <div class="carousel-caption d-none d-md-block" style="color:white;">
                  <div style="font-size:50px; font-weight:bold;">런지 #2</div>
                  <div style="font-size:28px;">무릎 각도에 유의해주세요.</div>
                </div>
              </div>
              <div class="carousel-item">
                <img src="@/assets/icon/lunge1.png" alt="...">
                <div class="carousel-caption d-none d-md-block" style="color:white;">
                  <div style="font-size:50px; font-weight:bold;">런지 #3</div>
                  <div style="font-size:28px;">다시 처음 자세로 움직여주세요.</div>
                </div>
              </div>
              <div class="carousel-item">
                <img src="@/assets/icon/lunge3.gif" alt="...">
                <div class="carousel-caption d-none d-md-block" style="color:white;">
                  <div style="font-size:50px; font-weight:bold;">런지 #4</div>
                  <div style="font-size:28px;">영상입니다.</div>
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
        <div class="notebook" style="width:800px; height:450px; transform:translate(0, 8%);">
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
                  <div style="font-size:50px; font-weight:bold;">스쿼트 #1</div>
                  <div style="font-size:28px;">머리부터 발끝까지 잡히게 자세를 취해주세요.</div>
                </div>
              </div>
              <div class="carousel-item">
                <img src="@/assets/icon/squat2.png" alt="...">
                <div class="carousel-caption d-none d-md-block" style="color:white;">
                  <div style="font-size:50px; font-weight:bold;">스쿼트 #2</div>
                  <div style="font-size:28px;">천천히 내려가주세요.</div>
                </div>
              </div>
              <div class="carousel-item">
                <img src="@/assets/icon/squat1.png" alt="...">
                <div class="carousel-caption d-none d-md-block" style="color:white;">
                  <div style="font-size:50px; font-weight:bold;">스쿼트 #3</div>
                  <div style="font-size:28px;">다시 처음 자세로 움직여주세요.</div>
                </div>
              </div>
              <div class="carousel-item">
                <img src="@/assets/icon/squat3.gif" alt="...">
                <div class="carousel-caption d-none d-md-block" style="color:white;">
                  <div style="font-size:50px; font-weight:bold;">스쿼트 #4</div>
                  <div style="font-size:28px;">영상입니다.</div>
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
  </div>
</div>
</template>
<script>
import NavBar from '@/components/NavBar.vue';

export default {
  name: 'ATutorial',
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
  background-size:1000px 700px;
  background-position: 0% 100%;
  background-color:white;
  width:1000px;
  height:700px;
  display: flex;
  justify-content: center;
  align-items: start;
}

img {
  width:800px;
  height:530px;
}

.carousel-inner {
  justify-content: center;
  align-items: center;
}

</style>
