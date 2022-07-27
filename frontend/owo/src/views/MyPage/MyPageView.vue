<template>
  <div class="mypageview">

    <h3>👧 5년째 헬린이 님의 운동 기록</h3>
    <br>
    <div class="mypageContainer"><br>
      <div class="title text-start">
        <label for="hi">
          <input v-if="slogan === ''" type="text" name="hi"
            class="slogan" placeholder="목표 및 슬로건을 입력해 주세요!"
            v-model="slogan" @input="sloganInput">
            <input v-else type="text" name="hi"
            class="slogan" :placeholder=slogan
            v-model="slogan" @keyup.enter="changeSlogan()">
            <span class="material-symbols-outlined">edit</span>
        </label>
        <div class="my-nav row text-center">
          <p class="col col-auto ms-auto">
            <router-link to="/mypage/main">⚒ 운동 분석</router-link>
          </p>
          <p class="col col-auto">
            <router-link to="/mypage/update">👧 내 정보 수정</router-link>
          </p>
          <p class="col col-auto me-4">
            <router-link to="/mypage/schedule">📆 운동 일지</router-link>
          </p>
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
    font-family: 'LeferiBaseType-BoldA';
  }
  .mypageContainer {
    font-weight: 600;
    /* text-shadow: #7b7b7b 1px 1px; */
    max-width: 980px;
    margin: 0 auto;
    background-color: white;
    text-align: center;
    border: solid #DFDFDF 1px;
    box-sizing: border-box;
    border-radius: 20px;
    height: 1500px;
    box-shadow: rgb(105, 105, 105) 3px 3px 10px;
    /* padding: 35px; */
  }
  .title {
    border-bottom: solid #DFDFDF 1px;
    font-weight: 800;
    margin-top: 10px;
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
    color: #6f6f6f;
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
  .my-nav > p:hover {
    color: white;
    background-color: rgb(229, 241, 255);
    border-radius: 10px;
    transition: 0.3s;
  }
</style>
