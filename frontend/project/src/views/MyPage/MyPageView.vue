<template>
  <div class="mypageview">

    <h3>👧 5년째 헬린이 님의 운동 기록</h3>
    <br>
    <div class="mypageContainer">
      <div class="title">
        <div class="slogan-box">
          <p class="slogan">6월 바프까지 화이팅!</p>
        </div>
        <div class="my-nav">
          <ul>
            <li><router-link to="/mypage">운동 분석</router-link></li>
            <li><router-link to="/mypage/update">내 정보 수정</router-link></li>
            <li><router-link to="/mypage/schedule">운동 일지</router-link></li>
          </ul>
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
      sampleData: ''
    }
  },
  setup() {},
  created() {},
  mounted() {},
  unmounted() {},
  methods: {}
}
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
    max-width: 860px;
    margin: 0 auto;

    background-color: white;
    text-align: center;
    border: solid #DFDFDF 1px;
    box-sizing: border-box;
    border-radius: 20px;
    height: 1000px;
    /* padding: 35px; */
  }
  .title {
    border-bottom: solid #DFDFDF 1px;
    height: 100px;
    display: flex;
    align-items: center;
    justify-content: space-between;

  }
  .slogan-box {
    width: 60%;
    height: 100%;
    padding: 30px;
    margin: 0px;
    border-right: solid #DFDFDF 1px;

  }
  .slogan {
    text-align: left;
    font-size: 18px;
    padding: 0px;
    margin: 0px;
    margin-right: auto;
    /* background-color: aqua; */

  }
  .my-nav {
    /* background-color: aqua; */
    display: flex;
    text-align: right;
  }
  .my-nav li {
    display: inline;
    padding: 0 20px;
    font-size: 13px;
  }

</style>
