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
      styleBarPercent: '"width: 30%"',
      user: {
        name: '한나',
        bmi: 20,
        gender: 'femail',
        weight: 44,
        height: 156,
        age: 27,
      },
      meta: 0,
      eat: 0,
    };
  },
  setup() {},
  created() {
    if (this.user.gender === 'femail') {
      this.meta = (10 * this.user.weight) + (6.25 * this.user.height) - (5 * this.user.age) - 161;
    } else {
      this.meta = (10 * this.user.weight) + (6.25 * this.user.height) - (5 * this.user.age) + 5;
    }
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
