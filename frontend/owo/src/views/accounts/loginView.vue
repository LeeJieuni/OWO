<template>
    <div class="login">
      <div class="container">
        <router-link to="/">
          <img src="../../assets/logo/logo2.png" alt=""
          style="width: 500px; margin-right: 20px;">
        </router-link>
        <form @submit.prevent="login(credentials)" class="loginForm">
          <h1 class="title">Login</h1>
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
          <div class="alternative-option mt-2">
            오운완 회원이 아니신가요?
            <b-button style="font-size: 15px" variant="link" @click="moveToRegister">회원가입</b-button>
          </div>
              <b-button type="submit" class="mt-3 btn-pers"
              id="login_button" variant="outline-primary"
              style="width: 50%; border-radius:50px">로그인</b-button>

          <div class="socialLogin" style="padding: 20px; padding-top: 10px;">
            <hr class="hr">
            <div class="socialbtn">
              <!--eslint--disable-next-line-->
              <a href="https://i7c202.p.ssafy.io:8282/oauth2/authorization/google?redirect_uri=https://i7c202.p.ssafy.io:443/oauth/redirect">
                <!--eslint--disable-next-line--><span style="display: none;">s</span>
              <img src="@/assets/socialLogo/btn_google_signin_light_normal_web@2x.png"
              style="width:191px; height: 46px; margin-bottom: 5px;" alt="">
              <!--eslint--disable-next-line-->
              </a>
              <a href="https://i7c202.p.ssafy.io:8282/oauth2/authorization/naver?redirect_uri=https://i7c202.p.ssafy.io:443/oauth/redirect">
                <!--eslint--disable-next-line--><span style="display: none;">s</span>
              <img src="@/assets/socialLogo/btnG_official.png"
              style="width:191px; height: 46px; margin-bottom: 5px;"
              alt="">
              <!--eslint--disable-next-line-->
              </a>
              <a href="https://i7c202.p.ssafy.io:8282/oauth2/authorization/kakao?redirect_uri=https://i7c202.p.ssafy.io:443/oauth/redirect">
                <!--eslint--disable-next-line--><span style="display: none;">s</span>
              <img src="@/assets/socialLogo/kakao_login_medium_narrow_eng.png"
              style="width:191px; height: 46px; margin-bottom: 5px;"
              alt="">
              <!--eslint--disable-next-line-->
              </a>
            </div>
          </div>
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
  height: 600px;
  padding: 20px;
  border: solid 1px rgb(177, 177, 181);
  border-radius: 15px;
  box-shadow: 2px 2px 2px 2px rgb(154, 152, 152);
}

label {
  flex: 1;
  text-align: left;
  margin-bottom: 10px;
}
.title {
  font-weight: bold;
  padding: 20px;
  font-family: 'LeferiPoint-BlackObliqueA';
}
.hr {
  margin: 25px 35px;
}
</style>
