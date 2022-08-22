<template>
  <div>0811 RedirectView.vue이다.
    {{ accessToken }}
  </div>
</template>

<script>
import { mapActions } from 'vuex';

export default {
  name: 'OauthRedrect',
  created() {
    const { accessToken } = this.$route.query;
    console.log(accessToken);
    this.socialLogin(accessToken);
  },
  methods: {
    ...mapActions('accounts', ['socialLogin']),
  },
};
</script>
