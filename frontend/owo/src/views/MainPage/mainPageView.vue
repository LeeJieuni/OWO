<template>
  <div>
    <Carousel></Carousel>
    <rankingList/>
    <roomTab></roomTab>
  </div>
</template>>

<script>
import Carousel from '@/components/MainPage/Carousel.vue';
import rankingList from '@/components/MainPage/rankingList.vue';
import roomTab from '@/components/MainPage/roomTab.vue';

export default {
  components: {
    Carousel,
    rankingList,
    roomTab,
  },
};
</script>

<style scoped>
.start-btn {
    margin-right: 30px;
}

.btn-lg {
  height: 40px;
  text-align: center;
  line-height: 10px;
  font-size: 18px;
}
.owo {
  font-weight: 600;
  text-shadow: #7b7b7b 1px 1px;
}

.first-intro-text {
  font-size: 18px;
  line-height: 15px;
}
.btns {
  padding-top: 5px;
  padding-bottom: 20px;
}
</style>
