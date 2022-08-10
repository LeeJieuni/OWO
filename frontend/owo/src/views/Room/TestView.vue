<template>
  <a
    href="https://i7c202.p.ssafy.io:8282/oauth/authorization/google?redirect_uri=https://i7c202.p.ssafy.io:443/oauth/redirect"
    class="btn btn-success active"
    role="button"
    >Google Login</a
  >
  <a
    href="https://i7c202.p.ssafy.io:8282/oauth/authorization/naver?redirect_uri=https://i7c202.p.ssafy.io:443/oauth/redirect"
    class="btn btn-secondary active"
    role="button"
    >Naver Login</a
  >
  <a
    href="https://i7c202.p.ssafy.io:8282/oauth/authorization/kakao?redirect_uri=https://i7c202.p.ssafy.io:443/oauth/redirect"
    class="btn btn-warning active"
    role="button"
    >Kakao Login</a
  >
</template>

<script>
import axios from 'axios';

axios.defaults.headers.post['Content-Type'] = 'application/json';
export default {
  name: 'TestPage',
  data() {},
  methods: {},
};
</script>
