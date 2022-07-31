<template>
  <div class="mypageview">
    <div class="background-box">
      <div class="front-box row">
        <div class="mypageContainer row">
          <div class="col-3 sidebar m-0 p-0">
            <MySidebar/>
          </div>
          <div class="col-9 m-0 p-0">
            <div class="title text-center">
              <h4>{{slogan}}</h4>
            </div>
            <div>
              <router-view></router-view>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import MySidebar from '@/components/MyPage/MySidebar.vue';

export default {
  name: 'MyPage',
  components: { MySidebar },
  data() {
    return {
      slogan: '👧 8월 바프까지 화이팅!',
    };
  },
  setup() {},
  created() {},
  mounted() {},
  unmounted() {},
  methods: {
    changeSlogan() {

    },
    sloganInput(e) {
      this.slogan = e.target.value;
    },
  },
};
</script>

<style scoped>
  a {
    text-decoration: none;
  }

  .mypageview {
    text-align: center;
    color: black;
  }
  h1, h2, h3, h4, h5 {
    font-weight: 900;
  }
  .mypageContainer {
    font-weight: 600;
    /* text-shadow: #7b7b7b 1px 1px; */
    max-width: 900px;
    margin: 0 auto;
    background-color: white;
    text-align: center;
    border: solid #DFDFDF 1px;
    box-sizing: border-box;
    border-radius: 20px;
    height: 100%;
    box-shadow: rgb(105, 105, 105) 3px 3px 10px;
    opacity: 93%;
    /* padding: 35px; */
    margin-bottom: 100px;
  }
  .title {
    border-bottom: solid #DFDFDF 1px;
    font-weight: 800;
    margin-top: 10px;
    height: 100px;
    padding: 30px;
  }
  .background-box {
    width: 100vw;
    height: 500px;
    /* background: linear-gradient(lightCyan, skyBlue, deepSkyBlue); */
    background-image: url("https://i.pinimg.com/originals/07/7e/a6/077ea6de7db29b564b4335dfd2ba7c15.jpg");
    background-size: 100vh;
    position: relative;
  }
  .front-box {
    position: relative;
    padding-top: 100px;
    backdrop-filter: blur(6px);
  }
  .sidebar {
    border-right: solid 1px #DFDFDF;
  }
</style>
