<template>
    <div>
        <p>test page</p>
        <button @click="increment">Counter: {{ count }}</button>
    </div>
</template>
<script>
export default {
  components: {},
  data() {
    return {
      count: 0,
    };
  },
  setup() {},
  created() {},
  moundted() {},
  unmounted() {},
  methods: {
    increment() {
      this.count += 1;
    },
  },
};
</script>
