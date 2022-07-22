<template>
  <div class="mypageview">

    <h3>👧 5년째 헬린이 님의 운동 기록</h3>
    <br>
    <div class="mypageContainer"><br>
      <div class="title text-start">
        <label for="hi">
          <input v-if="slogan === ''" type="text" name="hi"
            class="slogan" placeholder="목표 및 슬로건을 입력해 주세요!"
            v-model="slogan">
            <input v-else type="text" name="hi"
            class="slogan" :placeholder=slogan
            v-model="slogan" @keyup.enter="changeSlogan()">
            <span class="material-symbols-outlined">edit</span>
        </label>
        <div class="my-nav text-end pb-1">
            <span class="ps-3"><router-link to="/mypage/main">⚒ 운동 분석</router-link></span>
            <span class="ps-3"><router-link to="/mypage/update">👧 내 정보 수정</router-link></span>
            <span class="ps-3"><router-link to="/mypage/schedule">📆 운동 일지</router-link>
            &nbsp;&nbsp;&nbsp;&nbsp;
          </span>
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
  methods: {
    changeSlogan() {

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
    font-family: 'LeferiBaseType-BoldA';
  }
  .mypageContainer {
    font-weight: 600;
    /* text-shadow: #7b7b7b 1px 1px; */
    max-width: 800px;
    margin: 0 auto;
    background-color: white;
    text-align: center;
    border: solid #DFDFDF 1px;
    box-sizing: border-box;
    border-radius: 20px;
    height: 1200px;
    box-shadow: rgb(105, 105, 105) 3px 3px 10px;
    /* padding: 35px; */
  }
  .title {
    border-bottom: solid #DFDFDF 1px;
    font-weight: 800;
  }

  .slogan {
    font-size: 18px;
    margin-left: 40px;
    margin-right: 3px;
    border: 0px;
    width: 330px;
    padding: 2px;
  }
  .slogan::placeholder {
    color: black;
  }
  .slogan:hover {
    font-weight: 900;
    cursor: pointer;
    transition: 0.4s;
    border: solid #6f6f6f 1px;
  }

  .material-symbols-outlined {
    font-size:17px;
  }
  .material-symbols-outlined:hover {
    font-size: 19px;
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
