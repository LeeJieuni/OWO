<template>
  <div>
    <div class="slide-box">
      <div class="section section-project">
        <h2 class="section__title">Project Carousel</h2><small class="section__subtitle">
          A simple Synchronised carousel by <a href="https://codepen.io/ariona">@ariona_rian</a><br/>using Slick Carousel</small>
        <div class="project-carousel">
          <div class="project-strip" ref="project-strip">
            <div class="project"><img src="http://unsplash.it/578/360/?image=26&amp;blur" alt=""/></div>
            <div class="project"><img src="http://unsplash.it/578/360/?image=39&amp;blur" alt=""/></div>
            <div class="project"><img src="http://unsplash.it/578/360/?image=52&amp;blur" alt=""/></div>
            <div class="project"><img src="http://unsplash.it/578/360/?image=65&amp;blur" alt=""/></div>
            <div class="project"><img src="http://unsplash.it/578/360/?image=78&amp;blur" alt=""/></div>
            <div class="project"><img src="http://unsplash.it/578/360/?image=91&amp;blur" alt=""/></div>
            <div class="project"><img src="http://unsplash.it/578/360/?image=104&amp;blur" alt=""/></div>
            <div class="project"><img src="http://unsplash.it/578/360/?image=117&amp;blur" alt=""/></div>
            <div class="project"><img src="http://unsplash.it/578/360/?image=130&amp;blur" alt=""/></div>
          </div>
          <div class="project-screen">
            <div class="project-detail" ref="project-detail">
              <div class="project"><img src="http://unsplash.it/578/361/?image=26" alt=""/></div>
              <div class="project"><img src="http://unsplash.it/578/361/?image=39" alt=""/></div>
              <div class="project"><img src="http://unsplash.it/578/361/?image=52" alt=""/></div>
              <div class="project"><img src="http://unsplash.it/578/361/?image=65" alt=""/></div>
              <div class="project"><img src="http://unsplash.it/578/361/?image=78" alt=""/></div>
              <div class="project"><img src="http://unsplash.it/578/361/?image=91" alt=""/></div>
              <div class="project"><img src="http://unsplash.it/578/361/?image=104" alt=""/></div>
              <div class="project"><img src="http://unsplash.it/578/361/?image=117" alt=""/></div>
              <div class="project"><img src="http://unsplash.it/578/361/?image=130" alt=""/></div>
            </div>
            <div class="screen-frame"></div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'MyPageSlideView',
  components: {},
  data() {
    return {
      a: '',
    };
  },
  setup() {},
  created() {},
  moundted() {},
  unmounted() {},
  methods: {},
};
</script>
<style scoped>
  .slide-box {
    width: 100%;
    /* height: 100%; */
   background-color: aqua;
  }
  @import 'https://fonts.googleapis.com/css?family=Josefin+Slab';
body {
  font-family: "Josefin Slab";
  height: 100vh;
  background-color: #222;
}

.section-project {
  padding: 50px 0;
  position: relative;
  background-color: #222;
}

.section__title, .section__subtitle {
  text-align: center;
  color: white;
  display: block;
}

.section__title {
  font-size: 3em;
  line-height: 1;
  margin: 0 0 20px;
}

.section__subtitle {
  font-size: 1.2em;
  line-height: 1.5;
  opacity: 0.5;
}
.section__subtitle a {
  color: inherit;
}

.project-carousel {
  position: relative;
  padding: 50px 0;
}

.project-strip {
  margin: 150px 0 150px;
  filter: blur(10px);
}
.project-strip .project {
  cursor: pointer;
}
.project-strip img {
  max-width: 100%;
  width: 100%;
  height: auto;
}

.project-screen {
  width: 768px;
  height: 451px;
  position: absolute;
  left: 0;
  right: 0;
  top: 0;
  bottom: 0;
  margin: auto;
}
.project-screen .project-detail {
  position: absolute;
  left: 0;
  right: 0;
  width: 578px;
  height: 361px;
  margin: 32px auto 0;
  z-index: 1;
  background-color: #333;
}
.project-screen .screen-frame {
  content: " ";
  width: 768px;
  height: 451px;
  position: absolute;
  background-image: url("https://dl.dropboxusercontent.com/u/26808427/cdn/experiments/training/macbook-frame.png");
  background-repeat: no-repeat;
}
.project-screen .project {
  margin-right: -100%;
  float: left;
  cursor: move;
}
</style>
