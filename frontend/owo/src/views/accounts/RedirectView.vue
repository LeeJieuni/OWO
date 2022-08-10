<template>
  <div>accounts.redirectView이다</div>
</template>

<script>
export default {
  name: 'OauthRedrect',
  created() {
    const { accessToken } = this.$route.query;
    console.log(accessToken);
  },
};
</script>

<style>
</style>
