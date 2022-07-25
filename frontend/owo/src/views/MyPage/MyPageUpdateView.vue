<template>
  <div class="container p-5">
    <div class="title-box row">
      <div class="profile col-5 d-flex justify-content-end">
        <label for="formFile" class="form-label">
          <img class="profile-img " src="https://picsum.photos/150" alt="">
          <input class="form-control form-control-sm profile-input"
          type="file" id="formFile">
        </label>
      </div>
      <div class="col-7 title p-3">
        <h3>{{ user.nick }}님의 정보 수정</h3><br>
          <p>이번주 운동 목표</p>
          <form action="">
            <label for="goal-type">
              <select v-model="inputGoalType"
                class="form-select form-select-sm goal-type-select"
                name="radio" id="goal_type">
                <option value="유산소">유산소</option>
                <option value="헬스">헬스</option>
                <option value="스트레칭">스트레칭</option>
                <option value="맨몸운동">맨몸운동</option>
                <option value="요가">요가</option>
                <option value="필라테스">필라테스</option>
                <option value="기타">기타</option>
              </select></label>&nbsp;
            <input
              type="number"
              name="goalHour"
              style="width: 70px;"
              v-model="inputGoalHour"
              min='1'> 시간
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <button @click="addGoal($event)" class="top-btns btn btn-outline-secondary">추가</button>
            <button @click="registGoal($event)" class="top-btns btn btn-outline-warning">등록</button>
          </form>
        <div class="tags row">
          <button
            v-for="(goal, goalI) in user.goals"
            :key="goalI"
            class="tag"
            @click="tagDelete">
            <p>{{goal.goalType}} {{goal.goalHour}}H</p>
          </button>
        </div>
      </div>
    </div>
    <div class="row profile-form p-5">
      <form action="">
      <table>
        <tbody>

          <tr>
            <th class="th-1">닉네임</th>
            <label for="nick">
              <th class="th-2"><input type="text" name="nick" v-model=user.nick></th>
            </label>
          </tr>

          <tr>
            <th class="th-1">나이</th><label for="age">
              <th class="th-2"><input type="number" name="age" v-model=user.age>
              <span class="th-2-text"> 세</span></th></label>
          </tr>

          <tr>
            <th class="th-1">성별</th>
            <label for="gender">
              <th class="th-2">
                <select class="form-select form-select-sm"
                  name="radio" id="gender" v-model=user.gender>
                  <option value="female">여자</option>
                  <option value="male">남자</option>
                </select>
              </th>
            </label>
          </tr>

          <tr>
            <th class="th-1">키</th><label for="height">
              <th class="th-2">
                <input type="number" name="height" v-model=user.height> cm
              </th>
            </label>
          </tr>

          <tr>
            <th class="th-1">몸무게</th><label for="secret">
              <th class="th-2">
                <input type="number" name="weight" v-model=user.weight> kg
              </th>
            </label>
          </tr>

          <tr>
            <th class="th-1">활동량</th>
            <th class="th-2">
              <label for="activity-num"> 주
                <input type="number" style="width: 55px;"
                  name="activity-num" v-model=user.activity.weektimes>&nbsp;회&nbsp;</label>
              <label for="activity-hour">
                <input type="number" style="width: 60px;"
                  name="activity-hour" v-model=user.activity.weekhours>
                &nbsp;시간 운동&nbsp;</label>
            </th>
          </tr>

          <tr>
            <th class="th-1">내 정보 공개하기</th>
            <th class="th-2"><label for="secret">
              <select class="form-select form-select-sm"
                name="radio" id="secret" v-model=user.secret>
                <option value="1">공개</option>
                <option value="2">비공개</option>
              </select></label>
            </th>
          </tr>

        </tbody>
      </table>
      <div class="row buttons">
        <button class="col btn btn-primary m-3" >변경</button>
        <button class="col btn btn-secondary m-3" >취소</button>
      </div>
    </form>
    </div>
  </div>
</template>
<script>
export default {
  name: 'MyPageUpdateView',
  components: {},
  data() {
    return {
      inputGoalType: '',
      inputGoalHour: 0,
      user: {
        nick: '한나',
        slogan: '아자아자 화이팅!',
        age: 27,
        gender: 'male',
        height: 156,
        weight: 44,
        activity: {
          weektimes: 5,
          weekhours: 20,
        },
        secret: 2,
        goals: [],
      },
    };
  },
  setup() {},
  created() {},
  mounted() {},
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
    }
  },
};
</script>

<style scoped>
.top-btns {
  width: 60px;
  height: 25px;
  font-size: 13px;
}
  .tags {
    padding: 10px;
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
  button {
    border-radius: 15px;
    height: 29px;
    padding-top: 3px;
  }
  .profile-img {
    border-radius: 50%;
  }

  .profile-form {
    margin: 0, auto;
  }

  .title {
    text-align: left;
  }

  .goal-type-select {
    width: 120px;
  }

  table {
    margin: auto;
    border-spacing: 6px;
    border-collapse: separate;
    line-height: 32px;
    text-align: left;
  }
  input:not(.profile-input) {
    height: 30px;
    width: 162px;
    border:  solid rgb(165, 165, 165) 1px;
    border-radius: 15px;
    padding: 10px;
    margin: 3px;
    font-size: 16px;
  }

  select {
    height: 30px;
    width: 162px;
    font-size:16px;
    border:  solid rgb(165, 165, 165) 1px;
    border-radius: 15px;
    padding-left: 10px;
    padding-top: 1px;
  }

  select:hover {
    cursor: pointer;
  }

  .form-label {
    width: 162px;
  }

  .profile-input {
    margin-top: 20px;
    font-size: 10px;
    height: 15px;
  }

  .buttons {
    width: 240px;
    margin: 20px auto;
  }

  .th-1 {
    text-align: right;
    padding-right: 30px;
  }

  .th-2 {
    /* border-left: solid black 1px; */
    text-align: left;
    font-family: 'LeferiBaseType-RegularA';
  }
</style>
