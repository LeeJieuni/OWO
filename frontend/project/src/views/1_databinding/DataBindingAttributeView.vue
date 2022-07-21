<template>
  <div>
    <input class="form-control" type="text" v-model="userId" readonly />
    <input class="form-control" type="text" :value="userId" readonly />
    <br/>
    <img :src="imgUrl" alt="" style="width: 100px; height: auto;" />
    <br/>
    <div class="input-group">
      <input id="qwer" class="form-control" type="search" v-model="txt1">
      <button id="qwer" type="button" class="btn btn-primary" :disabled="txt1 === ''">조회</button>
    </div>
  </div>
</template>
<script>
export default {
  components: {},
  data() {
    return {
      userId: 'hanna',
      imgUrl: 'https://kr.vuejs.org/images/logo.png',
      txt1: ''
    }
  },
  setup() {},
  created() {},
  mounted() {},
  unmounted() {},
  methods: {}
}
</script>
