<template>
  <div class="container">
    <div class="d-flex">
      <div style="width:100%;">
        <div class="d-flex justify-content-between align-items-center" >
          <h3 class="m-0">팔굽혀펴기</h3>
          <Timer/>
        </div>
        <div class="container d-flex justify-content-between mt-5">
          <div class="container">
            <div class="row">
              <WebRTC v-if="false" class="col-lg-4 mb-5"></WebRTC>
              <WebRTC class="col-4 mb-5"></WebRTC>
              <WebRTC class="col-4 mb-5"></WebRTC>
              <WebRTC class="col-4 mb-5"></WebRTC>
              <WebRTC class="col-4 mb-5"></WebRTC>
              <WebRTC class="col-4 mb-5"></WebRTC>
              <RoomButton></RoomButton>
            </div>
          </div>
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
  width:1600px;
}
</style>
