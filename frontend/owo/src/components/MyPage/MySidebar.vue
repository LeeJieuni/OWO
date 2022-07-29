<template>
  <div class="row">
    <label for="hi">
      <input v-if="slogan === ''" type="text" name="hi"
        class="slogan" placeholder="슬로건을 입력해 주세요!"
        v-model="slogan" @input="sloganInput">
        <input v-else type="text" name="hi"
        class="slogan" :placeholder=slogan
        v-model="slogan" @keyup.enter="changeSlogan()">
      </label>
    <div class="profile ">
      <img class="profile-img" src="https://picsum.photos/150" alt="">
      <p class="name">
        {{ name }} <span class="sm-name">님</span>
      </p>
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
export default {
  name: 'MySidebar',
  components: {},
  data() {
    return {
      name: '한나',
      inputGoalType: '',
      inputGoalHour: 0,
      goals: [],
    };
  },
  setup() {},
  created() {},
  moundted() {},
  unmounted() {},
  methods: {
    addGoal(event) {
      if (this.user.goals.length >= 3) {
        /* eslint-disable */
        alert('주간 목표는 3개까지 추가 가능합니다.');
        event.preventDefault();
        return;
      }
      else if (this.inputGoalType === '') {
        /* eslint-disable */
        alert('목표를 설정해 주세요!');
        event.preventDefault();
        return;
      }
      else if (this.inputGoalHour < 1) {
        /* eslint-disable */
        alert('시간을 설정해 주세요!');
        event.preventDefault();
        return;
      }
      event.preventDefault();
      this.user.goals.push({ goalType: this.inputGoalType, goalHour: this.inputGoalHour });
    },
    tagDelete(event) {
      /* eslint-disable */
      // alert('해당 주간 목표를 삭제하시겠습니까?');
      event.preventDefault();
      console.log(event)
    },
    registGoal() {
      // 솰라솰라
    },
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
