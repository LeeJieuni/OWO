<template>
  <div>
    <b-modal
    id="login-modal"
    title="로그인"
    :hide-footer="true"
    >
      <form action="" @submit.stop.prevent="">
        <b-form-group
          label="아이디"
          label-for="user-id-input"
          invalid-feedback="ID is required"
        >
        <b-form-input id="user_id_input" v-model="user.username"></b-form-input>
        </b-form-group>
        <b-form-group
          label="비밀번호"
          label-for="user-pw-input"
          invalid-feedback="PassWord is required"
        >
        <b-form-input id="user_pw_input" v-model="user.password"></b-form-input>
        </b-form-group>
        <input type="button" value="로그인" @click="ok()">
        </form>
    </b-modal>

  </div>
</template>

<script>
export default {
  data() {
    return {
      user: {
        username: null,
        password: null,
      },
    };
  },
  methods: {
    print() {
      console.log(this.user.username);
      console.log(this.user.password);
    },
  },
};
</script>
