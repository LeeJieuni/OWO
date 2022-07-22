<template>
  <div>
    <div class="row">
      <p class="my-level-title">신체 정보 분석</p>
    </div>
  </div>
</template>
<script>
export default {
  name: 'PhysicalInfoAnalys',
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
  /* 나의 운동 레벨 */
.my-level {
  height: 250px;
  border-bottom: solid #DFDFDF 1px;
}

.my-level-title {
  text-align: left;
  padding-top: 30px;
  font-size: 22px;
  padding-left: 50px;
  padding-bottom: 10px;
}

</style>
