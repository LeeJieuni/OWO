<template>
<div class="body">
  <div class="sidebarcontent d-flex justify-content-center">
    <div class="row sidebar-row pt-4">
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
    <div class="row sidebar-row">
      <p>이번주 운동 목표</p>
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
          min='1'> <span>시간</span><button
            @click="addGoal(state.inputGoal.exercise, state.inputGoal.hour)"
            class="top-btns btn btn-outline-warning">등록</button>
      </span>
    </div>
    <div class="tags row sidebar-row justify-content-center">
      <button
        v-for="(goal, i) in goals"
        :key="i"
        class="tag"
        @click="tagModal">
        <p>{{goal.exercise}} {{goal.hour}}H
          <!-- eslint-disable-next-line-->
          <span class="deleteGoal" @click.prevent="deleteGoal(goal.goalId)" @keyup.enter="deleteGoal(goal.goalId)">
            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="16" height="16">
            <!-- eslint-disable-next-line-->
            <path fill="none" d="M0 0h24v24H0z"/><path d="M12 10.586l4.95-4.95 1.414 1.414-4.95 4.95 4.95 4.95-1.414 1.414-4.95-4.95-4.95 4.95-1.414-1.414 4.95-4.95-4.95-4.95L7.05 5.636z" fill="rgba(156,0,0,1)"/></svg>
          </span>
        </p>
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
// import Swal from 'sweetalert2';

export default {
  name: 'MyPageUpdateView',
  setup() {
    const store = useStore(); // 필수
    // Getters
    const user = computed(() => store.getters['accounts/userInfo']);
    const goals = computed(() => store.getters['accounts/goals']);
    const profileImg = computed(() => store.getters['accounts/profileImg']);
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
      profileSrc: 'https://src.hidoc.co.kr/image/lib/2021/4/28/1619598179113_0.jpg',
    });

    const addGoal = (type, hour) => {
      // console.log('goals.value');
      // console.log(goals.value[0].exercise);
      // console.log(type, hour);
      for (let i = 0; i < goals.value.length; i += 1) {
        if (goals.value[i].exercise === type) {
          alert('이미 추가한 운동입니다.');
          return;
        }
      }
      if (goals.value.length >= 3) {
        /* eslint-disable */
        alert('주간 목표는 3개까지 추가 가능합니다.');
        // e.preventDefault();
      }
      else if (type === '') {
        /* eslint-disable */
        alert('목표를 설정해 주세요!');
        // e.preventDefault();
      }
      else if (hour < 1) {
        /* eslint-disable */
        alert('시간을 설정해 주세요!');
        // e.preventDefault();
      } else {
        confirm('목표를 추가하시겠습니까?');
        store.dispatch('accounts/addGoal', state.inputGoal);
        store.dispatch('accounts/fetchGoal');
      }
    };
    // 새로고침
    const refreshAll = () => {
      this.$router.go();
    };
    const deleteGoal = (goalId) => {
      // e.preventDefault();
      console.log(goalId);
      alert('운동 목표를 삭제할까요?');
      console.log('삭제 시도 요청');
      store.dispatch('accounts/deleteGoal', goalId);
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
    };
  },
};
</script>

<style scoped>
.profile-wrapper {
  text-align: center;
  white-space: nowrap;
  position: relative;
}
/* .profile-wrapper::before .sidebarcontent::after {
  border-radius: 50%;
  border: 1pxsolidrgba(32,33,36,.13);
  box-sizing: border-box;
  content: "";
  height: 100%;
  left: 0;
  position: absolute;
  top: 0;
  width: 100%;
} */
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
   .tags {
    padding: 0 10px;
  }
  .tag {
    width: 150px;
    height: 26px;
    border: solid #828282 1px;
    display:inline-block;
    border-radius: 10px;
    padding: 4px;
    margin: 2px;
    font-size: 12px;
    background-color: #F6F7F9;
    padding-left: 10px;
    text-align: center;
  }
  .tag:hover {
    background-color: #CEDFFF;
    /* color: white; */
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
    /* background-color: white; */
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
  input {
    height: 30px;
    border:  solid rgb(197, 197, 197) 1px;
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
</style>
