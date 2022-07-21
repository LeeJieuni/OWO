<template>
<div>
  <div>
    <input type="radio" name="" id="html" value="HTML" v-model="favoriteLang">
    <label for="">HTML</label>
  </div>
  <div>
    <input type="radio" name="" id="css" value="CSS" v-model="favoriteLang">
    <label for="">CSS</label>
  </div>
  <div>
    <input type="radio" name="" id="js" value="JS" v-model="favoriteLang">
    <label for="">JS</label>
  </div>
  <div>선택한 지역: {{ favoriteLang }}</div>
</div>
</template>
<script>
export default {
  components: {},
  data() {
    return {
      favoriteLang: 'CSS'
    }
  },
  setup() {},
  created() {},
  mounted() {},
  unmounted() {},
  methods: {}
}
</script>
