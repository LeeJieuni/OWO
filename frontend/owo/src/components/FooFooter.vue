<template>
  <div class="footer d-flex justify-content-between align-items-center">
    <div class="contents">
      <div class="d-flex row text-start">
        <span><img class="logo" src="@/assets/logo/logo2.png" alt=""></span>
        <p><span class="bold">Front</span> &nbsp; &nbsp; 이준호  &nbsp; 백한나  &nbsp; 나장엽</p>
        <p><span class="bold">Back</span> &nbsp; &nbsp; &nbsp; 이승희  &nbsp; 이지은  &nbsp; 안다슬</p>
        <span># 오운완 Created by SSAFY7 - Team SoS</span>
      </div>
    </div>
    <div class="contents">
      <a style="text-decoration: none;" href="https://accidental-postage-c1a.notion.site/bdcd991182e142f8b410f5b9a4aae238">팀 소개</a><br>
      <a style="text-decoration: none;" href="https://youtu.be/gIxsl6bg5dI">프로젝트 소개 영상</a>
    </div>
    <div class="contents" style="text-align: right;">
    </div>
  </div>
</template>

<script>
export default {
  name: 'FooFooter',
  components: {},
  data() {
    return {
      a: '',
    };
  },
  setup() {},
  created() {},
  moundted() {},
  unmounted() {},
  methods: {},
};
</script>

<style scoped>
p {
  line-height: 5px;
}
.footer {
  font-family: 'NanumSquareRound';
  font-weight: 700;
  background-color: #white;
  color: #2E2E2E;
  padding: 15px;
}
.logo{
  width: 120px;
  margin-bottom: 10px;
}
.bold {
  font-weight: bold;
}
.contents {
  margin: 20px 20px;
  width: 33%;
  font-weight: 300;
  font-size: 12px;
  align-items: center;
  color: #2E2E2E;
}
</style>
