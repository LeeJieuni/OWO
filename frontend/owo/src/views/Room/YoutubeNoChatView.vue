<template>
<div>
  <div class="container-wrap d-flex justify-content-center">
    <div>
      <div class="d-flex justify-content-between align-items-center"
      style="height:80px;">
        <h3 class="m-0">방 이름</h3>
        <Timer/>
      </div>
      <div class="d-flex">
        <div>
          <div class="d-flex align-items-start justify-content-start mb-5 m-2"
          style="background-color:aqua; width:930px; height:585px">
          <iframe width="100%" height="100%" src="https://www.youtube.com/embed/gTowV_F07uI" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>              </div>
          <RoomButton></RoomButton>
        </div>
        <div style="width:30%; max-height:600px; overflow-y:auto;">
          <WebRTCYoutubeNochat></WebRTCYoutubeNochat>
          <WebRTCYoutubeNochat></WebRTCYoutubeNochat>
          <WebRTCYoutubeNochat></WebRTCYoutubeNochat>
          <WebRTCYoutubeNochat></WebRTCYoutubeNochat>
          <WebRTCYoutubeNochat></WebRTCYoutubeNochat>
          <WebRTCYoutubeNochat></WebRTCYoutubeNochat>
        </div>
      </div>
    </div>
  </div>
</div>
</template>
<script>
import Timer from '@/components/SetTimer.vue';
import RoomButton from '@/components/Room/RoomButton.vue';
import WebRTCYoutubeNochat from '../../components/Room/WebRTCYoutubeNochat.vue';

export default {
  name: 'FriendNoChatView',
  components: {
    Timer,
    WebRTCYoutubeNochat,
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
.container {
  width:100vw;
}
div {
  color:black;
}
</style>
