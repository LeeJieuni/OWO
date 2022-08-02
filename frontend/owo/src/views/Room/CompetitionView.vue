<template>
  <div class="container-wrap d-flex justify-content-center">
      <div>
        <div class="d-flex justify-content-between align-items-center"
        style="height:80px;">
          <h3 class="m-0">팔굽혀펴기</h3>
          <Timer/>
        </div>
        <div class="container d-flex align-items-start justify-content-between">
          <div class="row">
            <WebRTC v-if="true" class="col-lg-4 mb-5"></WebRTC>
            <WebRTC v-if="true" class="col-4 mb-5"></WebRTC>
            <WebRTC v-if="true" class="col-4 mb-5"></WebRTC>
            <WebRTC v-if="true" class="col-4 mb-5"></WebRTC>
            <WebRTC v-if="true" class="col-4 mb-5"></WebRTC>
            <WebRTC v-if="true" class="col-4 mb-5"></WebRTC>
            <RoomButton></RoomButton>
          </div>
        </div>
      </div>
    </div>
</template>
<script>
import Timer from '@/components/SetTimer.vue';
import WebRTC from '@/components/Room/WebRTC.vue';
import RoomButton from '@/components/Room/RoomButton.vue';

export default {
  name: 'CompetitionView',
  components: {
    Timer,
    WebRTC,
    RoomButton,
  },
  data() {
    return {
    };
  },
  setup() {},
  created() {},
  moundted() {},
  unmounted() {},
  methods: {},
};
</script>
<style scoped>
div {
  color:black;
}
.container {
  width:100vw;
  height:100vh;
}

</style>
