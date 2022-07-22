<template>
  <div>
    <div class="title">
      <h3>{{ user.nick }}님의 캘린더</h3>
    </div>
  </div>
</template>
<script>
export default {
  name: 'MyPageScheduleView',
  components: {},
  data() {
    return {
      user: {
        nick: '한나',
      },
    };
  },
  setup() {},
  created() {},
  mounted() {},
  unmounted() {},
  methods: {},
};
</script>

<style scoped>
  .title {
    padding: 50px;
  }
</style>
