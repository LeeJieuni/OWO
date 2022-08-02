<template>
  <div>
    <div class="login">
      <div class="container">
      <form @submit.prevent="login(credentials)" class="loginForm">
      <h2 class="mb-3">Login</h2>
      <div class="input">
        <label for="email">아이디<input
          v-model="credentials.email"
          class="form-control"
          type="email"
          name="email"
          placeholder="email@adress.com"
          required
        /></label>
      </div>
      <div class="input">
        <label for="password"><span>비밀번호</span><input
          v-model="credentials.password"
          class="form-control"
          type="password"
          name="password"
          placeholder="password123"
          required
        /></label>
      </div>
      <div class="alternative-option mt-4">
       오운완 회원이 아니신가요?
       <b-button Style="font-size: 15px" variant="link" @click="moveToRegister">회원가입</b-button>
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
    <!-- <p>{{ userInfo }}</p> -->
  </div>
  </div>
</template>

<script>
import { createNamespacedHelpers } from 'vuex';

const { mapActions } = createNamespacedHelpers('accounts');

export default {
  data() {
    return {
      credentials: {
        email: '',
        password: '',
      },
    };
  },
  methods: {
    ...mapActions({
      login: 'login',
    }),
    moveToRegister() {
      this.$router.push('/join');
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
.loginForm {
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
