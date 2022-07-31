<template>
    <div class="container-fluid">
      <button v-b-modal.join-modal>signup</button>
      <b-modal id="join-modal" title="#오운완" :hide-footer="true">
        <div class="join">
          <form @submit.prevent="" class="joinForm">
            <h2 style="text-align:center;">회원가입</h2>
            <div class="input_email">
              <label for="email"><span style="font-size: 20px;">아이디</span>
              <input class="form-control" type="email" name="email"
                  placeholder="email@adress.com" required /></label>
            </div>
            <div class="input_password">
              <label for="password"><span style="font-size: 20px;">비밀번호</span>
                <input class="form-control" type="password"
                name="password" placeholder="password123" required /></label>
            </div>
            <div class="alreadyUser" style="margin-top: 20px;">
              오운완 회원이신가요?
              <b-button Style="font-size: 20px" variant="link" >로그인</b-button>
            </div>
            <div class="socialJoin" style="margin-top: 15px">
              <h3 style="text-align: center;">소셜 계정으로 회원가입하기</h3>
              <div class="socialbtn d-flex justify-content-around">
                <span>구글</span>
                <span>네이버</span>
                <span>카카오</span>
              </div>
            </div>
            <div class="buttons" style="margin-bottom:30px;">
              <b-button type="submit" id="joinButtons" variant="outline-primary">
                회원가입
              </b-button>
            </div>
          </form>
        </div>
      </b-modal>
    </div>
</template>

<script>
export default {
  data() {
    return {
    };
  },
  methods: {
  },
};
</script>

<style scope>
button {
  padding: 0;
  border: none;
  background: none;
}
.socialbtn {
  margin-top: 20px;
}
.input_email {
  display: flex;
  margin-bottom: 20px;
}

.input_password {
  display: flex;
  margin-bottom: 20px;
}

.alreadyUser {
  text-align: center;
  font-size: 20px;
}

.buttons {
  margin-top: 50px;
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>
