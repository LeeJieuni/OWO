<template>
  <div class="row">
    <div class="profile">
        <img class="profile-img" src="https://picsum.photos/150" alt="">
        <p class="name" v-once>
          {{ user.nick }} <span class="sm-name">님</span>
        </p>
    </div>
    <div>
      <button type="button" class="btn btn-primary"
        data-bs-toggle="modal" data-bs-target="#exampleModal">
          ImgBtn
      </button>
    </div>

    <div class="sidebar-menus text-center justify-content-center;">
      <button class="row sidebar-menu">
        <router-link to="/mypage/main">⚒ 운동 기록</router-link>
      </button>
      <button class="row sidebar-menu">
        <router-link to="/mypage/schedule">📆 운동 일지</router-link>
      </button>
      <button class="row sidebar-menu">
        <router-link to="/mypage/analys">⚒ 운동 분석</router-link>
      </button>
      <button class="row sidebar-menu">
        <router-link to="/mypage/update">👧 내 정보 수정</router-link>
      </button>
    </div>
  </div>
</template>
<script>
import { useStore } from 'vuex';
import { reactive, computed } from 'vue';

export default {
  name: 'MyPageUpdateView',
  setup() {
    const store = useStore(); // 필수
    // Getters
    const user = computed(() => store.getters['accounts/userInfo']);

    // State (vue3의 data 선언 방식. state라는 이름으로 접근)
    const state = reactive({
      selected: '',
      options: [
        { text: '없음', value: '1' },
        { text: '약간', value: '2' },
        { text: '중간', value: '3' },
        { text: '많음', value: '4' },
        { text: '매우 많음', value: '5' },
      ],
      input: {
        image: '',
      },
      testText: '할수있다',
    });

    // Methods
    // const onInputImage = () => {
    //   const input = document.querySelector('.input-image');
    //   console.log(input.files);
    // };

    return {
      state,
      user,
      // onInputImage,
    };
  },
  components: {},
  created() {
  },
};
</script>

<style scoped>
  .profile-img {
    border-radius: 50%;
    margin: 0 auto;
  }
  .name {
    font-weight: 900;
    font-size: 25px;
    margin-top: 10px;
  }
  .sm-name {
    font-size: 22px;
  }
   .tags {
    padding: 0 10px;
  }
  .tag {
    width: 105px;
    height: 26px;
    border: solid #828282 1px;
    display:inline-block;
    border-radius: 20px;
    padding: 4px;
    margin: 2px;
    font-size: 12px;
    background-color:aliceblue;
    padding-left: 10px;
  }
  .tag:hover {
    background-color: #DE7474;
    color: white;
    transition: 0.2s;
    cursor: pointer;

  }
  .tag p {
    text-align: left;
  }
  .title {
    text-align: center;
    font-size: 18px;
    margin: 0;
  }
  .sidebar-menu {
    border: solid black 1px;
    margin: 5px;
    width: 200px;
    height: 40px;
    padding: 10px;
    border-radius: 5px;
    background-color: #2E2E2E;
    box-shadow: 3px 3px 5px gray;
  }
  .sidebar-menu > a {
    color: white;
    font-weight: 700;
    text-decoration: none;
  }
  .sidebar-menu:hover {
    background-color: #F6F7F9;
    color: black;
  }
  .sidbar-menu > a:hover {
    color: black;
  }
  .goal-type-select {
    width: 120px;
  }
  input {
    height: 30px;
    border:  solid rgb(70, 70, 70) 1px;
    border-radius: 5px;
    padding: 10px;
    margin: 3px;
    font-size: 16px;
  }
  .slogan {
    font-size: 18px;
    border: 0px;
    width: 200px;
    padding: 2px;
    font-weight: 900;
  }
  .slogan::placeholder {
    color: #6f6f6f;
    font-size: 12px;
  }
  .slogan:hover {
    font-weight: 900;
    cursor: pointer;
    transition: 0.4s;
    border: solid #6f6f6f 1px;
  }
</style>
