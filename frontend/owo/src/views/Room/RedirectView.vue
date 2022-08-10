<template>
  <div>redirect View</div>
</template>

<script>
export default {
  name: 'OauthRedrect',
  created() {
    const { accessToken } = this.$route.query;
    sessionStorage.setItem('accessToken', accessToken);
    console.log('세션저장완료');
    // router.push('/');
    // this.$router.replace('/');
  },
  methods: {},
};
</script>
