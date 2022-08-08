<template>
  <div></div>
</template>

<script>
export default {
  name: 'OauthRedrect',
  created() {
    const { accessToken } = this.$route.query;
    console.log('accessToken', accessToken);

    // this.$router.replace('/');
  },
  methods: {},
};
</script>

<style>
</style>
