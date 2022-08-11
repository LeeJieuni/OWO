<template>
  <div class="container-box p-5">
    <div class="row profile-form p-1">
      <form>
      <table>
        <tbody>
          <tr>
            <th class="th-1">닉네임</th>
              <th class="th-2">
              <label for="nick">
                <input class="form-input text-center"
                  type="text"
                  v-model.number="user.nick"
                  maxlength="9"
                >
              </label>
            </th>
          </tr>

          <tr>
            <th class="th-1">나이</th>
            <th class="th-2">
              <label for="age">
                <input class="form-input text-center"
                  type="number" name="age"
                  v-model.number="user.age">
                <span class="th-2-text"> 세</span>
              </label>
            </th>
          </tr>

          <tr>
            <th class="th-1">성별</th>
            <th class="th-2">
              <label for="gender">
                <select class="form-select form-select-sm form-input text-center"
                  name="radio" id="gender"
                  v-model="user.gender">
                  <option value="FEMALE">여자</option>
                  <option value="MALE">남자</option>
                </select>
              </label>
            </th>
          </tr>

          <tr>
            <th class="th-1">키</th>
            <th class="th-2">
              <label for="height">
                <input class="form-input text-center text-center"
                  type="number" name="height"
                  v-model.number="user.height"> cm
              </label>
            </th>
          </tr>

          <tr>
            <th class="th-1">몸무게</th>
            <th class="th-2">
              <label for="weight">
                <input class="form-input text-center"
                  type="number" name="weight"
                  v-model.number="user.weight"> kg
              </label>
            </th>
          </tr>

          <tr>
            <th class="th-1">활동량</th>
            <th class="th-2">
              <label for="activity-num">
                <select class="form-select form-select-sm form-input text-center"
                  name="radio" id="activity"
                  v-model.number="user.activityLevel">
                  <option value="1">주로 앉아서 보냄</option>
                  <option value="2">약간 활동적</option>
                  <option value="3">중간 활동적</option>
                  <option value="4">꽤나 활동적</option>
                  <option value="5">매우 활동적</option>
                </select>
              </label>
            </th>
          </tr>
        </tbody>
      </table>
      <div class="row buttons">
        <button class="col btn btn-primary m-3" @click.prevent="update(user)">변경</button>
        <button class="col btn btn-secondary m-3" >취소</button>
      </div>
      <!-- {{ state.options[0] }} -->
    </form>
    </div>
  </div>
</template>
<script>
import { useStore } from 'vuex';
import { reactive, computed } from 'vue';

export default {
  name: 'MyPageUpdateView',
  setup() {
    const store = useStore();
    const user = computed(() => store.getters['accounts/userInfo']);
    const state = reactive({
      selected: '',
      options: [
        { text: '없음', value: '1' },
        { text: '약간', value: '2' },
        { text: '중간', value: '3' },
        { text: '많음', value: '4' },
        { text: '매우 많음', value: '5' },
      ],
    });
    console.log(user);
    const update = function (userInfo) {
      // console.log(userInfo);
      store.dispatch('accounts/updateUserInfo', userInfo);
    };
    return {
      state,
      user,
      update,
    };
  },
  components: {},
  created() {
    // console.log(this.user);
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
    padding: 0 10px;
  }
  .tag {
    width: 105px;
    height: 26px;
    border: solid #828282 0px;
    display:inline-block;
    border-radius: 10px;
    /* padding: 4px; */
    margin: 2px;
    font-size: 12px;
    font-weight: 700;
    background-color:#828282;
    /* padding-left: 10px; */
    line-height: 18px;
    color: white;
    letter-spacing: -1.5;
  }
  .tag:hover {
    background-color: #DE7474;
    color: white;
    transition: 0.2s;
    cursor: pointer;
  }
  .tag p {
    text-align: center;
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
  .form-input {
    height: 30px;
    width: 200px;
    border:  solid rgb(165, 165, 165) 1px;
    border-radius: 10px;
    /* padding: 10px; */
    margin: 3px;
    font-size: 16px;
    /* text-indent: 10px; */
    align-items: center;
  }
  .form-input::-webkit-input-placeholder { text-align: center; }
  .form-input::-moz-placeholder { text-align: center; }
  .form-input:-ms-input-placeholder { text-align: center; }
  .form-input:-moz-placeholder { text-align: center; }
  .form-input::placeholder { text-align: center; }
  select {
    height: 30px;
    width: 250px;
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
    align-items: center;
    align-self: center;
  }
</style>
