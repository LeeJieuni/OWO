<template>
  <div>
    <div class="title">
      <!-- <p class="title-p">{{ userInfo.nick }}'s Calendar </p> -->
    </div>
    <div class="calendar">
      <MyCalendar/>
    </div>
  </div>
</template>

<script>
import MyCalendar from '@/components/MyPage/MyCalendar.vue';
import { createNamespacedHelpers } from 'vuex';

const { mapGetters } = createNamespacedHelpers('accounts');

export default {
  components: { MyCalendar },
  data() {
    return {
      user: {
        nick: '한나',
      },
      value: '',
    };
  },
  setup() {},
  created() {},
  mounted() {},
  unmounted() {},
  methods: {
    // dateClass(ymd, date) {
    //   const day = date.getDate();
    //   return day >= 10 && day <= 19 ? 'table-info' : '';
    // },
  },
  computed: {
    ...mapGetters(['userInfo']),
  },
};
</script>

<style scoped>
  p {
    font-weight: 700;
    font-size: 24px;
  }
  .title {
    padding-top: 40px;
    padding-bottom: 20px;
  }
  .calendar {
    margin: 0 auto;
  }
  .title-p {
    font-family: 'Recipekorea';
  }
</style>
