<template>
  <div>
    <div class="join">
      <div class="container">
      <form @submit.prevent="join" class="joinForm">
      <h2 class="mb-3">회원가입</h2>
      <div class="input">
        <label for="email">아이디<input
          class="form-control"
          type="text"
          name="email"
          placeholder="email@adress.com"
        /></label>
      </div>
      <div class="input">
        <label for="password"><span>비밀번호</span><input
          class="form-control"
          type="password"
          name="password"
          placeholder="password123"
        /></label>
      </div>
      <div class="input">
        <label for="password"><span>비밀번호 확인</span><input
          class="form-control"
          type="password"
          name="password"
          placeholder="password123"
        /></label>
      </div>
      <hr>
      <div class="alternative-option mt-4">
        이미 오운완 회원이신가요?
       <b-button Style="font-size: 15px" variant="link" @click="moveToLogin">로그인</b-button>
      </div>
      <div class="socialLogin" style="padding: 10px">
        <div class="socialbtn d-flex justify-content-around">
          <span>구글</span>
          <span>네이버</span>
          <span>카카오</span>
        </div>
      </div>
      <b-button type="submit"
      class="mt-3 btn-pers" id="login_button" variant="outline-primary">
        Login
      </b-button>
    </form>
    </div>
  </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      email: '',
      password: '',
    };
  },
  methods: {
    moveToLogin() {
      this.$router.push('/login');
    },
  },
};
</script>

<style>
.container {
  display: flex;
  height: 100vh;
  justify-content: center;
  align-items: center;
}
.joinForm {
  padding: 50px;
  border: solid 1px rgb(177, 177, 181);
  border-radius: 10%;
}
label {
  flex: 1;
  text-align: left;
  margin-bottom: 10px;
}
</style>
