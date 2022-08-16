<template>
  <div>
    <div class="register">
      <div class="container">
      <img src="../../assets/logo/logo2.png" alt="" style="width: 500px; margin-right: 20px;">
      <form @submit.prevent="register(credentials)"
      class="registerForm">
      <h1 style="font-weight: bold; margin-bottom: 20px;
        font-family: 'LeferiPoint-BlackObliqueA';">SignUp</h1>
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
      <div class="mt-2">
       오운완 회원이신가요?
       <b-button Style="font-size: 15px" variant="link" @click="moveToLogin">로그인</b-button>
      </div>
      <!-- <div class="socialregister" style="padding: 10px">
        <div class="socialbtn d-flex justify-content-around">
          <span>구글</span>
          <span>네이버</span>
          <span>카카오</span>
        </div>
      </div> -->
      <b-button type="submit"
      class="mt-3 btn-pers" id="register_button" variant="outline-primary"
      style="width: 50%; border-radius:50px">
        회원가입
      </b-button>
      <div class="socialLogin" style="padding: 20px; padding-top:10px;">
            <hr>
            <div class="socialbtn">
              <!--eslint--disable-next-line-->
              <a href="https://i7c202.p.ssafy.io:8282/oauth2/authorization/google?redirect_uri=https://i7c202.p.ssafy.io:443/oauth/redirect">
                <!--eslint--disable-next-line--><span style="display: none;">s</span>
              <img src="@/assets/socialLogo/btn_google_signin_light_focus_web.png"
              style="width:191px; height: 46px; margin-bottom: 5px;" alt="">
              <!--eslint--disable-next-line-->
              </a>
              <a href="https://i7c202.p.ssafy.io:8282/oauth2/authorization/naver?redirect_uri=https://i7c202.p.ssafy.io:443/oauth/redirect">
                <!--eslint--disable-next-line--><span style="display: none;">s</span>
              <img src="@/assets/socialLogo/btnG_completed.png"
              style="width:191px; height: 46px; margin-bottom: 5px;"
              alt="">
              <!--eslint--disable-next-line-->
              </a>
              <a href="https://i7c202.p.ssafy.io:8282/oauth2/authorization/kakao?redirect_uri=https://i7c202.p.ssafy.io:443/oauth/redirect">
                <!--eslint--disable-next-line--><span style="display: none;">s</span>
              <img src="@/assets/socialLogo/kakao_login_medium_narrow.png"
              style="width:191px; height: 46px; margin-bottom: 5px;"
              alt="">
              <!--eslint--disable-next-line-->
              </a>
            </div>
          </div>
    </form>
    </div>
  </div>
  </div>
</template>

<script>

import { createNamespacedHelpers } from 'vuex';

const { mapActions, mapGetters } = createNamespacedHelpers('accounts');

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
  computed: {
    ...mapGetters(['signupInfo']),
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
  width: 350px;
  height: 660px;
  padding: 50px;
  border: solid 1px rgb(177, 177, 181);
  border-radius: 15px;
  box-shadow: 2px 2px 2px 2px rgb(154, 152, 152);
}
label {
  flex: 1;
  text-align: left;
  margin-bottom: 10px;
}
</style>
