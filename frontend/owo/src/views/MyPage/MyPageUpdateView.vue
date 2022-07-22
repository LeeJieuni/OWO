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
            <th class="th-2">
              <label for="goal-type">
                <select class="form-select form-select-sm" name="radio" id="goal_type">
                  <option value="유산소">유산소</option>
                  <option value="헬스">헬스</option>
                  <option value="스트레칭">스트레칭</option>
                  <option value="맨몸운동">맨몸운동</option>
                  <option value="요가">요가</option>
                  <option value="필라테스">필라테스</option>
                  <option value="기타">기타</option>
                </select></label>&nbsp;
              <input type="number" name="goal-hour" style="width: 50px;"> 시간&nbsp;
              <button @click="addGoal($value)" class="btn btn-outline-secondary">추가</button>
            </th>
          </form>
        <p v-for="(goal, goalIndex) in user.goals" :key="goalIndex">{{goal.goalType}}</p>
      </div>
    </div>
    <div class="row profile-form p-5">
      <form action="">
      <table>
        <tbody>

          <tr>
            <th class="th-1">닉네임</th><label for="nick">
              <th class="th-2"><input type="text" name="nick"></th></label>
          </tr>

          <tr>
            <th class="th-1">나이</th><label for="age">
              <th class="th-2"><input type="number" name="age">
              <span class="th-2-text"> 세</span></th></label>
          </tr>

          <tr>
            <th class="th-1">성별</th>
            <label for="gender">
              <th class="th-2">
                <select class="form-select form-select-sm" name="radio" id="gender">
                  <option value="female">여자</option>
                  <option value="male">남자</option>
                </select>
              </th>
            </label>
          </tr>

          <tr>
            <th class="th-1">키</th><label for="height">
              <th class="th-2">
                <input type="number" name="height"> cm
              </th>
            </label>
          </tr>

          <tr>
            <th class="th-1">몸무게</th><label for="secret">
              <th class="th-2">
                <input type="number" name="weight"> kg
              </th>
            </label>
          </tr>

          <tr>
            <th class="th-1">활동량</th>
            <th class="th-2">
              <label for="activity-num"> 주
                <input type="number" style="width: 50px;" name="activity-num">&nbsp;회&nbsp;</label>
              <label for="activity-hour">
                <input type="number" style="width: 50px;" name="activity-hour">
                &nbsp;시간 운동&nbsp;</label>
            </th>
          </tr>

          <tr>
            <th class="th-1">내 정보 공개하기</th>
            <th class="th-2"><label for="secret">
              <select class="form-select form-select-sm" name="radio" id="secret">
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
      goalType: '',
      goalHour: 0,
      user: {
        nick: '한나',
        slogan: '아자아자 화이팅!',
        goals: [],
      },
    };
  },
  setup() {},
  created() {},
  mounted() {},
  unmounted() {},
  methods: {
    addGoal() {
      this.user.goals.push({ goalType: this.goalType, goalHour: this.goalHour });
    },
  },
};
</script>

<style scoped>

  button {
    border-radius: 15px;
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

  table {
    margin: auto;
    border-spacing: 6px;
    border-collapse: separate;
    line-height: 32px;
    font-size: 16px;
    text-align: left;
  }
  input:not(.profile-input) {
    height: 30px;
    width: 150px;
    border:  solid rgb(165, 165, 165) 1px;
    border-radius: 15px;
    padding: 15px;
    margin: 3px;
  }

  select {
    height: 28px;
    font-size:16px;
    border:  solid rgb(165, 165, 165) 1px;
    border-radius: 15px;
  }

  .form-label {
    width: 150px;
  }

  .profile-input {
    margin-top: 20px;
    font-size: 10px;
    height: 15px;
    color: ;
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
