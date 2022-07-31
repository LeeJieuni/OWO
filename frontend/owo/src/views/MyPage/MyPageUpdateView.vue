<template>
  <div class="container-box p-5">
    <h3>{{ user.nick }}님의 정보 수정</h3><br>
    <div class="title-box row">
      <div class="profile d-flex justify-content-center">
        <label for="formFile" class="form-label" >
          <img class="profile-img " :src=user.profileImg alt="">
          <input class="form-control form-control-sm profile-input"
            type="file" id="formFile"
            src=profileImg
            v-on:change="profileImg($event)"
            >
        </label>
      </div>
    </div>

    <div class="row profile-form p-1">
      <form action="">
      <table>
        <tbody>

          <tr>
            <th class="th-1">슬로건</th>
              <th class="th-2">
              <label for="slogan">
                <input type="text" name="slogan" v-model=user.slogan>
              </label>
            </th>
          </tr>

          <tr>
            <th class="th-1">닉네임</th>
              <th class="th-2">
              <label for="nick">
                <input type="text" name="nick" v-model=user.nick>
              </label>
            </th>
          </tr>

          <tr>
            <th class="th-1">나이</th>
            <th class="th-2">
              <label for="age">
                <input type="number" name="age" v-model=user.age>
                <span class="th-2-text"> 세</span>
              </label>
            </th>
          </tr>

          <tr>
            <th class="th-1">성별</th>
            <th class="th-2">
              <label for="gender">
                <select class="form-select form-select-sm"
                  name="radio" id="gender" v-model=user.gender>
                  <option value="female">여자</option>
                  <option value="male">남자</option>
                </select>
              </label>
            </th>
          </tr>

          <tr>
            <th class="th-1">키</th>
            <th class="th-2">
              <label for="height">
                <input type="number" name="height" v-model=user.height> cm
              </label>
            </th>
          </tr>

          <tr>
            <th class="th-1">몸무게</th>
            <th class="th-2">
              <label for="secret">
                <input type="number" name="weight" v-model=user.weight> kg
              </label>
            </th>
          </tr>

          <tr>
            <th class="th-1">활동량</th>
            <th class="th-2">
              <b-form-group>
                <b-form-radio-group
                  id="radio-group-1"
                  v-model="selected"
                  :options="options"
                  :aria-describedby="ariaDescribedby"
                  name="radio-options"
                  v-b-tooltip.hover title="
                    1) 주로 앉아서 보냄<br>
                    2) 주 1-3일<br>
                    3) 주 3-5일<br>
                    4) 주 6-7일<br>
                    5) 하루 2회 등 격렬한 운동"
                ></b-form-radio-group>
              </b-form-group>

              <!-- <label for="activity-num">
                <select class="form-select form-select-sm"
                  name="radio" id="activity" v-model=user.activity>
                  <option value="1">주로 앉아서 보냄</option>
                  <option value="2">약간 활동적</option>
                  <option value="3">중간 활동적</option>
                  <option value="4">꽤나 활동적</option>
                  <option value="5">매우 활동적</option>
                </select>
              </label> -->
            </th>
          </tr>

          <tr>
            <th class="th-1">주간 목표</th>
            <th class="th-2">
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
                <button @click="addGoal($event)"
                  class="top-btns btn btn-outline-secondary ms-2">추가</button>
                <!-- <button @click="registGoal($event)"
                  class="top-btns btn btn-outline-warning">등록</button> -->
              </form>
              <tr>
                <div class="tags row">
                  <button
                    v-for="(goal, goalI) in user.goals"
                    :key="goalI"
                    class="tag"
                    @click="tagDelete">
                    <p>{{goal.goalType}} {{goal.goalHour}}H</p>
                  </button>
                </div>
              </tr>
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
        slogan: '8월 바프까지 화이팅!',
        nick: '한나',
        age: 27,
        gender: 'female',
        height: 156,
        weight: 44,
        activity: 4,
        secret: 2,
        goals: [],
        profileImg: 'https://picsum.photos/150',
      },
      selected: '',
      options: [
        { text: '없음', value: '1' },
        { text: '약간', value: '2' },
        { text: '중간', value: '3' },
        { text: '많음', value: '4' },
        { text: '매우 많음', value: '5' },
      ],
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
    // registGoal() {
    //   // 솰라솰라
    // },
    profileImg(e) {
      let files = e.target.files || e.dataTransfer.files;
      if (!files.length)
        return;
      this.createImage(files[0]);
    }
  },
};
</script>

<style scoped>
.container-box {
  margin: 0 auto;
}
.profile {
  max-width: 150px;
  display: flex;
}
.title-box {
  margin: 0 auto;
  max-width: 550px;
  justify-content: center;

}
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
    line-height: 18px; 
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
    margin: 0 auto;
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
    width: 300px;
    border:  solid rgb(165, 165, 165) 1px;
    border-radius: 10px;
    padding: 10px;
    margin: 3px;
    font-size: 16px;
  }

  select {
    height: 30px;
    width: 300px;
    font-size:16px;
    border:  solid rgb(165, 165, 165) 1px;
    border-radius: 10px;
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
    width: 70%;
  }
</style>
