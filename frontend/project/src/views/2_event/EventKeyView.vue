<template>
  <div>
    <!-- <input type="search" v-model="searchText" @keyup="checkEnter($event)"> -->
    <input type="search" v-model="searchText" @keyup.ctrl.="keyupEnter($event)">
    <button v-on:click="doSearch">조회</button>
  </div>
</template>
<script>
export default {
  components: {},
  data() {
    return {
      searchText: ''
    }
  },
  setup() {},
  created() {},
  mounted() {},
  unmounted() {},
  methods: {
    doSearch() {
      console.log(this.searchText)
    },
    checkEnter(event) {
      if (event.keyCode === 13) {
        this.doSearch()
        this.searchText = ''
      }
    },
    keyupEnter(event) {
      this.doSearch()
      this.searchText = ''
    }
  }
}
</script>
