<template>
  <div class="container-fluid">
    <button v-b-modal.login-modal>login</button>
    <b-modal id="login-modal" title="#오운완" :hide-footer="true">
      <div class="login">
        <form @submit.prevent="" class="loginForm">
          <h2 style="text-align:center;">Login</h2>
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
          <div class="notUser" style="display: flex; margin-top: 20px;">
            오운완 회원이 아니신가요?
          </div>
          <div class="socialLogin" style="margin-top: 30px">
            <div class="socialbtn d-flex justify-content-around">
              <span>구글</span>
              <span>네이버</span>
              <span>카카오</span>
            </div>
          </div>
          <div class="buttons">
            <b-button type="submit" id="login_button" variant="outline-primary">
              Login
            </b-button>
          </div>
        </form>
      </div>
    </b-modal>
  </div>
</template>

<script>
// import joinModal from './joinModal.vue';

export default {
  data() {
    return {
    };
  },
  methods: {
  },
  components: {
  },
};
</script>

<style scope>
button {
  padding: 0;
  border: none;
  background: none;
}

.input_email {
  display: flex;
  margin-bottom: 20px;
}

.input_password {
  display: flex;
  margin-bottom: 20px;
}

.notUser {
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
