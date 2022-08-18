<template>
  <div class="footer d-flex justify-content-between align-items-center">
    <div class="contents">
        <div class="d-flex row">
          <h7 style="text-align: left;">#오운완 Created by SSAFY7 - Team SoS</h7>
          <p style="text-align: left; margin: 0px;">
          FrontEnd :&nbsp; &nbsp; 이준호  &nbsp; 백한나  &nbsp; 나장엽</p>
          <p style="text-align: left; margin: 0px;">
          BackEnd :&nbsp; &nbsp; &nbsp; 이승희  &nbsp; 이지은  &nbsp; 안다슬</p>
        </div>
    </div>
    <div class="contents">
      <a style="text-decoration: none; color: white;" href="https://accidental-postage-c1a.notion.site/bdcd991182e142f8b410f5b9a4aae238">팀 소개</a>
    </div>
    <div class="contents" style="text-align: right;">
      <p style="text-align:center; text-decoration: none; color: rgb(29, 29, 29);">
      </p>
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
.footer {
  font-family: 'Recipekorea';
  background-color: rgb(29, 29, 29);
  color: white;
  padding: 20px;
}
.bold {
  font-size: 14px;
  font-weight: 600;
}
.contents {
  width: 33%;
  font-weight: 300;
  font-size: 10px;
  margin-top: 20px;
  margin-bottom: 20px;
  align-items: center;
}
</style>
