<template>
  <div>
    <PhysicalInfo/>
  </div>
</template>
<script>
import PhysicalInfo from '@/components/MyPage/PhysicalInfo.vue';

export default {
  name: 'MyPageAnalysView',
  components: { PhysicalInfo },
  data() {
    return {
    };
  },
  setup() {},
  created() {
  },
  moundted() {},
  unmounted() {},
  methods: {},
};
</script>

<style scoped>
.my-physical-info-title {
  text-align: left;
  padding-top: 50px;
  font-size: 22px;
  padding-bottom: 20px;
  padding-left: 50px;
}

.analy-div {
  padding-left: 60px;
  padding-right: 60px;
  align-items: flex-start;
}

.bar {
  height: 30px;
  border-radius: 10;
}
.md-title {
  font-size: 18px;
  text-align: left;
}
.sm-title {
  text-align: left;
}

.rg-font {
  font-family: 'LeferiBaseType-RegularA';
}
</style>
