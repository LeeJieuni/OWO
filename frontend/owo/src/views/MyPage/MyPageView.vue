<template>
  <div class="mypageview">

    <h3>👧 5년째 헬린이 님의 운동 기록</h3>
    <br>
    <div class="mypageContainer"><br>
      <div class="title text-start">
          <span v-if="slogan === ''" class="slogan ps-5 fs-5" style="color: #828282;">
            목표 및 슬로건을 입력해 주세요!
            <span class="material-symbols-outlined">edit</span>
          </span>
          <span v-else class="slogan">{{ slogan }}
            <span class="material-symbols-outlined">edit</span>
          </span><br>
        <div class="my-nav text-end pb-1">
            <span class="ps-3"><router-link to="/mypage/main">⚒ 운동 분석</router-link></span>
            <span class="ps-3"><router-link to="/mypage/update">👧 내 정보 수정</router-link></span>
            <span class="ps-3"><router-link to="/mypage/schedule">📆 운동 일지</router-link></span>
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
      slogan: '',
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
    height: 100%px;
  }
/*
  .slogan {
    text-align: left;
    font-size: 20px;
    padding: 0px;
    margin: 0px;
    margin-right: auto;
  }

  .my-nav {

  }

  .my-nav li {
    text-decoration: none;
  }

  .my-nav ul {
    height: 100%;
    text-align: right;
    align-items:flex-end;
    justify-content: flex-end;
    padding-right: 20px;
    display: inline;
    font-size: 13px;
    color: #2E2E2E;
  }

  .material-symbols-outlined {
    font-size:20px;
  } */

</style>
