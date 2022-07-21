<template>
  <div>
    <h1>Hello mainPage</h1>
    <Carousel/>
  </div>
</template>

<script>
import Carousel from '../../components/Carousel.vue';

export default {
  name: 'MainPage',
  components: {
    Carousel,
  },
};
</script>
