<template>
  <div>
    <div class="register">
      <div class="container">
      <form @submit.prevent="register(credentials)" class="registerForm">
      <h2 class="mb-3">회원가입</h2>
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
          @change="isSame()"
        /></label>
      </div>
      <div class="input">
        <label for="confirm"><span>비밀번호 확인</span><input
          v-model="confirm"
          class="form-control"
          type="password"
          placeholder="password123"
          @change="isSame()"
          required
        /></label>
      </div>
      <small v-if="passwordErr">비밀번호가 일치하지 않습니다.</small>
      <div class="alternative-option mt-4">
       오운완 회원이신가요?
       <b-button Style="font-size: 15px" variant="link" @click="moveToLogin">로그인</b-button>
      </div>
      <div class="socialregister" style="padding: 10px">
        <div class="socialbtn d-flex justify-content-around">
          <span>구글</span>
          <span>네이버</span>
          <span>카카오</span>
        </div>
      </div>
      <b-button type="submit"
      class="mt-3 btn-pers" id="register_button" variant="outline-primary">
        회원가입
      </b-button>
    </form>
    </div>
  </div>
  </div>
</template>

<script>

import { createNamespacedHelpers } from 'vuex';

const { mapActions } = createNamespacedHelpers('accounts');

export default {
  data() {
    return {
      passwordErr: false,
      credentials: {
        email: '',
        password: '',
      },
      confirm: '',
    };
  },
  methods: {
    ...mapActions({
      register: 'register',
    }),
    moveToLogin() {
      this.$router.push('/login');
    },
    isSame() {
      const password1 = this.credentials.password;
      const password2 = this.confirm;
      if (password1 !== password2) {
        this.passwordErr = true;
      } else {
        this.passwordErr = false;
      }
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
.registerForm {
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
