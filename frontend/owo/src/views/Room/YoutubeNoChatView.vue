<template>
      <div class="d-flex">
        <div style="width:100%;">
          <div class="d-flex justify-content-between align-items-center" >
            <h3 class="m-0">방 이름</h3>
            <Timer></Timer>
          </div>
          <div class="d-flex">
            <div style="width:70%;">
              <div class="d-flex align-items-center justify-content-center mt-5"
              style="background-color:aqua;  height:400px">
              <iframe width="100%" height="100%" src="https://www.youtube.com/embed/gTowV_F07uI" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>              </div>
              <RoomButton></RoomButton>
            </div>
            <div class="mt-5" style="width:30%; max-height:470px; overflow-y:auto;">
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
div {
  color:black;
}
</style>
