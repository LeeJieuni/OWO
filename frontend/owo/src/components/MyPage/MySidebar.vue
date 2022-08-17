<template>
<div class="body">
  <div class="sidebarcontent d-flex justify-content-center">
    <div class="row sidebar-row pt-4 mb-3">
      <div class="profile-wrapper">
        <!-- {{ profileImg }} -->
        <div class="img-wrapper">
          <img
            class="profile-img"
            :src="profileImg"
            alt="프로필이미지"
          >
        </div>
        <img v-b-modal.image-upload
          class="change-profile-img" src="@/assets/icon/camera.png" alt="">
      </div>
        <p class="name" v-once>
          {{ user.nick }}
        </p>
      </div>
    </div>
    <div>
      <div class="row additional-info text-cetner mb-0">
        <p class="mb-1">현재 {{ lastingDay }}일간 연속 운동 중!</p>
        <p>이번 주 총 {{ thisWeekHours }}분 운동했습니다.</p>
      </div>
    </div>

    <div class="sidebar-row sidebar-menus text-center justify-content-center">
      <button class="row sidebar-menu">
        <router-link class="sidebar-menu" to="/mypage/main">Records</router-link>
      </button>
      <button class="row sidebar-menu">
        <router-link class="sidebar-menu" to="/mypage/schedule">Calendar</router-link>
      </button>
      <button class="row sidebar-menu">
        <router-link class="sidebar-menu" to="/mypage/analys">Physical Analys</router-link>
      </button>
      <button class="row sidebar-menu">
        <router-link class="sidebar-menu" to="/mypage/update">Update My Info</router-link>
      </button>
    </div>

    <div class="row sidebar-row goal-tab justify-content-center">
      <div>
        <p>이번주 운동 목표</p>
        <div class="addGoalRow">
        <span>
          <label for="goal-type">
            <select v-model="state.inputGoal.exercise"
              class="form-select form-select-sm goal-type-select"
              name="radio" id="goal_type">
              <option value="AEROBIC">유산소</option>
              <option value="HEALTH">헬스</option>
              <option value="STRETCHING">스트레칭</option>
              <option value="HOME">맨몸운동</option>
              <option value="YOGA">요가</option>
              <option value="PILATES">필라테스</option>
              <option value="ETC">기타</option>
            </select>
          </label>
          <input
            class="hourinput"
            type="number"
            name="goalHour"
            v-model="state.inputGoal.hour"
            min='1'> <span>시간</span><svg @click.prevent="addGoal(state.inputGoal.exercise, state.inputGoal.hour)" @keyup.enter="addGoal(state.inputGoal.exercise, state.inputGoal.hour)" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="24" height="24"><path fill="none" d="M0 0h24v24H0z"/><path d="M4 3h16a1 1 0 0 1 1 1v16a1 1 0 0 1-1 1H4a1 1 0 0 1-1-1V4a1 1 0 0 1 1-1zm7 8H7v2h4v4h2v-4h4v-2h-4V7h-2v4z"/></svg></span>
        </div>
      </div>
      <div class="tags row justify-content-center">
        <button
          v-for="(goal, i) in goals"
          :key="i"
          class="tag deleteGoal" @click.prevent="deleteGoal(goal.goalId)"
            @keyup.enter="deleteGoal(goal.goalId)">
          <p class="text-center">{{goal.exercise}} {{goal.hour}}H
            <!-- eslint-disable-next-line-->
            <!-- <span class="deleteGoal" @click.prevent="deleteGoal(goal.goalId)" @keyup.enter="deleteGoal(goal.goalId)">
              <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="16" height="16"> -->
              <!-- eslint-disable-next-line-->
              <!-- <path fill="none" d="M0 0h24v24H0z"/><path d="M12 10.586l4.95-4.95 1.414 1.414-4.95 4.95 4.95 4.95-1.414 1.414-4.95-4.95-4.95 4.95-1.414-1.414 4.95-4.95-4.95-4.95L7.05 5.636z" fill="rgba(156,0,0,1)"/></svg>
            </span> -->
          </p>
        </button>
      </div>
    </div>
  </div>
</template>
<script>
import { useStore } from 'vuex';
import { reactive, computed } from 'vue';
import swal from 'sweetalert2';

window.Swal = swal;

export default {
  name: 'MyPageUpdateView',
  setup() {
    const store = useStore(); // 필수
    // State

    // Getters
    const user = computed(() => store.getters['accounts/userInfo']);
    const goals = computed(() => store.getters['accounts/goals']);
    const profileImg = computed(() => store.getters['accounts/profileImg']);
    const thisWeekHours = computed(() => store.getters['accounts/thisWeekHours']);
    const lastingDay = computed(() => store.getters['accounts/lastingDay']);
    // for (let i = 0; i < this.goals.length; i += 1) {
    //   console.log(this.goals[i].value.exercise);
    // }
    // console.log(this.goals);
    // State (vue3의 data 선언 방식. state라는 이름으로 접근)
    const state = reactive({
      input: {
        image: '',
      },
      inputGoal: {
        exercise: '',
        hour: 0,
      },
      profileSrc: '',
    });

    const addGoal = (type, hour) => {
      // console.log('goals.value');
      // console.log(goals.value[0].exercise);
      // console.log(type, hour);
      for (let i = 0; i < goals.value.length; i += 1) {
        if (goals.value[i].exercise === type) {
          swal.fire('이미 추가한 운동입니다.');
          return;
        }
      }
      if (goals.value.length >= 3) {
        /* eslint-disable */
        swal.fire('주간 목표는 3개까지 추가 가능합니다.');
        // e.preventDefault();
      }
      else if (type === '') {
        /* eslint-disable */
        swal.fire('목표를 설정해 주세요!');
        // e.preventDefault();
      }
      else if (hour < 1) {
        /* eslint-disable */
        swal.fire('시간을 설정해 주세요!');
        // e.preventDefault();
      } else {
        store.dispatch('accounts/addGoal', state.inputGoal);
        store.dispatch('accounts/fetchGoal');
        // swal.fire('운동 목표가 추가되었습니다.');
      }
    };
    // 새로고침
    const refreshAll = () => {
      this.$router.go();
    };
    const deleteGoal = (goalId) => {
      // e.preventDefault();
      console.log(goalId);
      console.log('삭제 시도 요청');
      store.dispatch('accounts/deleteGoal', goalId);
      // swal.fire('운동 목표가 삭제되었습니다.');
      store.dispatch('accounts/fetchGoal');
    };
    // const tagModal = () => {
    //   /* eslint-disable */
    //   // alert('해당 주간 목표를 삭제하시겠습니까?');
    //   // Swal.fire({
    //   //   icon: 'question',
    //   //   title: '주간 목표',
    //   //   text: '변경하시겠어요?',
    //   //   footer: '<a href="">Why do I have this issue?</a>'
    //   // })
    //   e.preventDefault();
    //   console.log(e)
    // };
    // console.log('이미지시작')
    // console.log(typeof(profileImg));
    // console.log('이미지끝')

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
      deleteGoal,
      refreshAll,
      thisWeekHours,
      lastingDay,
    };
  },
};
</script>

<style scoped>
  .body {
    padding-bottom: 10px;
  }
  .profile-wrapper {
    text-align: center;
    white-space: nowrap;
    position: relative;
  }
  /* .img-wrapper:hover + img {display: block;} */
  .img-wrapper {
    position: relative;
    width: 150px;
    height: 150px;
    display: inline-block;
    margin-bottom: 16px;
    margin-left: auto;
    margin-right: auto;
    text-align: center;
  }
  .img-wrapper img {
    /* border: solid #DFDFDF 1px; */
    box-shadow:#DFDFDF 1px 1px 3px;
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
  .change-profile-img {
    width: 30px;
    height: 30px;
    position: absolute;
    cursor: pointer;
    /* top: 0; */
    /* left: 0; */
    right: 35px;
    bottom: 35px;
    border: white solid 3px;
    border-radius: 50px;
    /* display: none; */
  }

  .sidebar-row {
    padding: 0;
    margin: 0;
    border-top: #DFDFDF 1px solid;
  }
  .name {
    font-weight: 900;
    font-size: 25px;
    margin-top: 10px;
  }
   .tags {
    padding: 0 10px;
  }
  .tag {
    width: 100%;
    height: 30px;
    border: solid #DFDFDF 1px;
    display:inline-block;
    border-radius: 10px;
    padding: 4px;
    margin: 2px;
    font-size: 14px;
    background-color: #ffffff;
    padding-left: 10px;
    text-align: center;
  }
  .tag:hover {
    background-color: #ffc5c5;
    /* color: white; */
    transition: 0.9s;
    cursor: pointer;
    color: black;

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
    /* background-color: white; */
  }
  .sidebar-menu > a {
    font-weight: 700;
    text-decoration: none;
    color: black;
    background-color: white;
    /* display: table-cell;
    vertical-align: middle;
    text-align: center; */
  }
  .sidebar-menu:hover {
    background-color: #DFDFDF;
    color: white;
    transition: 0.3s;
    width: 100%;
  }
  .sidebar-menu > a:hover {
    color: white;
    transition: 0.1s;
    font-weight: 900;
    font-size: 15px;
    text-shadow: #373737 1px 1px 2px;
  }
  .sidebar-menu > a:active {
    transition: 0.1s;
    font-weight: 900;
    font-size: 16px;
  }
  label {
    padding: 0;
  }
  .goal-type-select {
    width: 100px;
    border-radius: 5px;
  }
  .hourinput {
    width: 40px;
    /* height: 20px; */
    padding: 2px;
  }
  select {
    /* border: none; */
    border:  solid #DFDFDF 1px;
    cursor: pointer;
  }
  input {
    height: 30px;
    cursor: pointer;
    border:  solid #DFDFDF 1px;
    /* border: none; */
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
  button {
    border: none;
  }
  .deleteGoal:hover {
    opacity: 50%;
  }
  .close_icon {
    height: 10px;
    opacity: 60%;
  }
  .goal-tab {
    padding-bottom: 20px;
    padding-top : 30px;
  }
  .addGoalRow {
    padding: 0;
    margin: 0;
  }

  .additional-info {
    font-size: 12px;
    font-weight: 500;
  }
</style>
