<template>
  <div class="footer" style="height: 200px;">
    <div class="d-flex justify-content-between">
      <div>
        <div class="d-flex row">
          <h3 style="text-align: left;">#오운완 팀 소개</h3>
          <h4 style="text-align: left;">SSAFY 7기 : 이준호 &nbsp; 이승희 &nbsp;
          이지은 &nbsp; 안다슬 &nbsp; 백한나 &nbsp;나장엽</h4>
        </div>
      </div>
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
  height: 200px;
  color: white;
  padding: 20px;
}
.bold {
  font-size: 14px;
  font-weight: 600;
}
</style>
