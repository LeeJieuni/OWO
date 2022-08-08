<template>
<div>
  <div class="d-flex justify-content-center">
    <div class="row sidebar-row d-flex justify-content-center pt-4">
      <div class="img-wrapper">
        <img
          class="profile-img"
          :src="state.profileSrc"
          alt="프로필이미지"
        >
      </div>
        <p class="name" v-once>
          {{ user.nick }} <span class="sm-name">님</span>
        </p>
      </div>
    </div>
    <div class="row sidebar-row">
      <button
        v-b-modal.image-upload class="btn btn-outline-secondary">
        이미지 변경
      </button>
    </div>
    <div class="row sidebar-row">
      <p>이번주 운동 목표</p>
      <form action="">
        <label for="goal-type">
          <select v-model="state.inputGoalType"
            class="form-select form-select-sm goal-type-select"
            name="radio" id="goal_type">
            <option value="CARDIOVASCULAR">유산소</option>
            <option value="HEALTH">헬스</option>
            <option value="STRETCHING">스트레칭</option>
            <option value="CALISTHENICS">맨몸운동</option>
            <option value="YOGA">요가</option>
            <option value="PILATES">필라테스</option>
            <option value="ETC">기타</option>
          </select>
        </label>
        <input
          type="number"
          name="goalHour"
          style="width: 70px;"
          v-model="state.inputGoalHour"
          min='1'> 시간 <br>
        <button @click="addGoal($event)" class="top-btns btn btn-outline-secondary">추가</button>
        <button @click="registGoal($event)"
          class="top-btns btn btn-outline-warning">등록</button>
      </form>
    </div>
    <div class="tags row sidebar-row">
      <button
        v-for="(goal, i) in goals"
        :key="i"
        class="tag"
        @click="tagDelete">
        <p>{{goal.exercise}} {{goal.hour}}H</p>
      </button>
    </div>

    <div class="sidebar-row sidebar-menus text-center justify-content-center">
      <button class="row sidebar-menu">
        <router-link class="sidebar-menu" to="/mypage/main">운동 기록</router-link>
      </button>
      <button class="row sidebar-menu">
        <router-link class="sidebar-menu" to="/mypage/schedule">운동 일지</router-link>
      </button>
      <button class="row sidebar-menu">
        <router-link class="sidebar-menu" to="/mypage/analys">운동 분석</router-link>
      </button>
      <button class="row sidebar-menu">
        <router-link class="sidebar-menu" to="/mypage/update">내 정보 수정</router-link>
      </button>
    </div>
  </div>
</template>
<script>
import { useStore } from 'vuex';
import { reactive, computed } from 'vue';

export default {
  name: 'MyPageUpdateView',
  data() {
    return {
      // profileSrc: require('https://src.hidoc.co.kr/image/lib/2021/4/28/1619598179113_0.jpg'),
    };
  },
  setup() {
    const store = useStore(); // 필수
    // Getters
    const user = computed(() => store.getters['accounts/userInfo']);
    const goals = computed(() => store.getters['accounts/goals']);
    const profileImg = computed(() => store.getters['accounts/profileImg']);
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
      inputGoalHour: 0,
      inputGoalType: '',
      profileSrc: 'https://src.hidoc.co.kr/image/lib/2021/4/28/1619598179113_0.jpg',
    });

    const addGoal = () => {
      if (this.goals.length >= 3) {
        /* eslint-disable */
        alert('주간 목표는 3개까지 추가 가능합니다.');
        e.preventDefault();
      }
      else if (this.inputGoalType === '') {
        /* eslint-disable */
        alert('목표를 설정해 주세요!');
        e.preventDefault();
      }
      else if (this.inputGoalHour < 1) {
        /* eslint-disable */
        alert('시간을 설정해 주세요!');
        e.preventDefault();
      }
      e.preventDefault();
      store.dispatch('accounts/updateUserInfo', userInfo);
    };
    const tagDelete = () => {
      /* eslint-disable */
      // alert('해당 주간 목표를 삭제하시겠습니까?');
      e.preventDefault();
      console.log(e)
    };
    console.log('이미지시작')
    console.log(profileImg);
    console.log('이미지끝')
    // Methods
    // const onInputImage = () => {
    //   const input = document.querySelector('.input-image');
    //   console.log(input.files);
    // };

    return {
      state,
      user,
      addGoal,
      goals,
      profileImg,
    };
  },
  components: {},
  created() {
    console.log(this.user.id);
  },
};
</script>

<style scoped>
  .img-wrapper {
    position: relative;
    width: 150px;
    height: 150px;
  }
  .img-wrapper img {
    border-radius: 50%;
    /* margin: 0 auto; */
    position: absolute;
    top: 0;
    left: 0;
    transform: translate(50, 50);
    width: 100%;
    height: 100%;
    object-fit: cover;
    margin: auto;
  }

  .sidebar-row {
    padding: 0;
    margin: 0;
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
    border-radius: 10px;
    padding: 4px;
    margin: 2px;
    font-size: 12px;
    background-color:aliceblue;
    padding-left: 10px;
  }
  .tag:hover {
    background-color: #DE7474;
    color: white;
    transition: 0.9s;
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
    /* border: solid black 1px; */
    /* border-radius: 5px; */
    /* margin: 5px; */
    padding: 0;
    margin: 0;
    width: 100%;
    height: 40px;

    /* box-shadow: 3px 3px 5px gray; */
    /* display: table; */
    display: flex;
    justify-content: center;
    align-items: center;
  }
  .sidebar-menu > a {
    font-weight: 700;
    text-decoration: none;
    color: black;
    /* display: table-cell;
    vertical-align: middle;
    text-align: center; */
  }
  .sidebar-menu:hover {
    background-color: #2E2E2E;
    color: white;
    transition: 0.3s;
    width: 100%;
  }
  .sidebar-menu > a:hover {
    color: white;
    transition: 0.1s;
    font-weight: 900;
    font-size: 16px;
  }
  .sidebar-menu > a:active {
    transition: 0.1s;
    font-weight: 900;
    font-size: 16px;
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
