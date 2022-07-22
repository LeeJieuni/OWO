<template>
  <div class="mypageview">

    <h3>👧 5년째 헬린이 님의 운동 기록</h3>
    <br>
    <div class="mypageContainer">
      <div class="title">
        <div class="slogan-box">
          <span class="slogan">{{ slogan }}</span> &nbsp; &nbsp; &nbsp;<span><img class="pencil" alt="pencil" src="https://img.icons8.com/ios-glyphs/30/000000/pencil--v1.png"/></span>
        </div>
        <div class="my-nav">
          <div class="my-nav-bottom d-flex align-items-end">
            <ul>
              <li class="ps-3"><router-link to="/mypage/main">⚒ 운동 분석</router-link></li>
              <li class="ps-3"><router-link to="/mypage/update">👧 내 정보 수정</router-link></li>
              <li class="ps-3"><router-link to="/mypage/schedule">📆 운동 일지</router-link></li>
            </ul>
          </div>
        </div>
      </div>
      <div>
        <router-view></router-view>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  name: 'MyPage',
  components: {},
  data() {
    return {
      slogan: '6월 바디프로필까지 화이팅~이다아',
    };
  },
  setup() {},
  created() {},
  mounted() {},
  unmounted() {},
  methods: {},
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
    font-family: 'LeferiBaseType-BoldA';
  }
  .mypageContainer {
    max-width: 800px;
    margin: 0 auto;

    background-color: white;
    text-align: center;
    border: solid #DFDFDF 1px;
    box-sizing: border-box;
    border-radius: 20px;
    height: 1200px;
    /* padding: 35px; */
  }
  .title {
    border-bottom: solid #DFDFDF 1px;
    height: 80px;
    display: flex;
    align-items: center;
    justify-content: space-between;

  }
  .slogan-box {
    width: 60%;
    height: 100%;
    padding: 30px;
    margin: 0px;
    text-align: left;
    /* border-right: solid #DFDFDF 1px; */

  }
  .slogan {
    text-align: left;
    font-size: 20px;
    padding: 0px;
    margin: 0px;
    margin-right: auto;
    /* background-color: aqua; */
  }

  .pencil {
    width: 20px;
  }

  .my-nav {
    width: 40%;
    height: 100%;
    display: flex;
    text-align: right;
    align-items:flex-end;
    justify-content: flex-end;
    padding-right: 20px;
  }

  .my-nav li {
    display: inline;
    font-size: 13px;
    color: #2E2E2E;
  }

  .my-nav ul {
    padding-left: 0px;
    margin-bottom: 5px;
  }

</style>
