<template>
    <div class="login">
      <div class="container">
        <img src="../../assets/logo/logo2.png" alt="" style="width: 500px">
        <form @submit.prevent="login(credentials)" class="loginForm">
          <h1 style="font-weight: bold; margin-bottom: 20px;">Login</h1>
          <div class="input">
            <label for="email">아이디<input v-model="credentials.email"
            class="form-control" type="email"
                placeholder="email@adress.com" required /></label>
          </div>
          <div class="input">
            <label for="password"><span>비밀번호</span>
              <input v-model="credentials.password" class="form-control"
              type="password" placeholder="password123"
                required /></label>
          </div>
          <!-- <div class="findpassword"> -->
            <!-- <b-button variant="link" style="text-align-right" @click="">비밀번호 찾기</b-button> -->
          <!-- </div> -->
          <!-- <small v-if="isLoginErr">{{ LoginErr }}</small> -->
          <div class="alternative-option mt-0">
            오운완 회원이 아니신가요?
            <b-button Style="font-size: 15px" variant="link" @click="moveToRegister">회원가입</b-button>
          </div>
          <div class="socialLogin" style="padding: 10px">
            <div class="socialbtn d-flex justify-content-around">
              <b-button pill variant="secondary"
              href="https://i7c202.p.ssafy.io:8282/oauth2/authorization/google?redirect_uri=https://i7c202.p.ssafy.io:443/oauth/redirect"
              role="button">Google</b-button>
              <b-button pill variant="success"
              href="https://i7c202.p.ssafy.io:8282/oauth2/authorization/naver?redirect_uri=https://i7c202.p.ssafy.io:443/oauth/redirect"
              role="button">Naver</b-button>
              <b-button pill variant="warning"
              href="https://i7c202.p.ssafy.io:8282/oauth2/authorization/kakao?redirect_uri=https://i7c202.p.ssafy.io:443/oauth/redirect"
              role="button">Kakao</b-button>
            </div>
          </div>
            <b-button type="submit" class="mt-3 btn-pers"
            id="login_button" variant="outline-primary"
            style="width: 90%; border-radius:50px">로그인</b-button>
        </form>
      </div>
    </div>
</template>

<script>
import { createNamespacedHelpers } from 'vuex';

const { mapActions, mapState } = createNamespacedHelpers('accounts');

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
      this.$router.push('/register');
    },
    findPassword() {
      this.$router.push('/findPassword');
    },
  },
  computed: {
    ...mapState({
      isLoginErr: 'isLoginErr',
      LoginErr: 'LoginErr',
    }),
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
  width: 350px;
  height: 500px;
  padding: 50px;
  border: solid 1px rgb(177, 177, 181);
  border-radius: 10%;
  box-shadow: 2px 2px 2px 2px rgb(154, 152, 152);
}

label {
  flex: 1;
  text-align: left;
  margin-bottom: 10px;
}
</style>
