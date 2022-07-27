<template>
  <div>
    <div class="title">
      <h3>{{ user.nick }}님의 캘린더</h3>
    </div>
    <div class="calendar">
      <MyCalendar/>
    </div>
  </div>
</template>

<script>
import MyCalendar from '@/components/MyPage/MyCalendar.vue';

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
    dateClass(ymd, date) {
      const day = date.getDate();
      return day >= 10 && day <= 19 ? 'table-info' : '';
    },
  },
};
</script>

<style scoped>
  .title {
    padding: 50px;
  }
  .calendar {
    margin: 0 auto;
  }
</style>
