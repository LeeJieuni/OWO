<template>
  <carousel :autoplay="2000" :wrap-around="true">
      <Slide v-for="image in images" :key="image">
          <img :src="require(`@/assets/${image}`)" alt="">
      </Slide>
    <template #addons>
      <pagination />
    </template>
  </carousel>
</template>

<script>
import 'vue3-carousel/dist/carousel.css';
import {
  Carousel, Slide, Pagination,
} from 'vue3-carousel';

export default {
  name: 'AutoPlay',
  data() {
    return {
      images: [
        '19329.jpg',
        'logo1.png',
        'logo2.png',
      ],
    };
  },
  components: {
    Carousel,
    Slide,
    Pagination,
  },
};
</script>

<style>
img {
  width: 80%;
  height: 50%;
}
</style>
