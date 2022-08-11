<template>
  <div>accounts.redirectView이다</div>
</template>

<script>
import { mapActions } from 'vuex';

export default {
  name: 'OauthRedrect',
  created() {
    const { accessToken } = this.$route.query;
    this.socialLogin(accessToken);
  },
  methods: {
    ...mapActions('accounts', ['socialLogin']),
  },
};
</script>

<style>
</style>
