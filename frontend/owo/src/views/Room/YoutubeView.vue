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
          <div class="d-flex align-items-start justify-content-start mx-1 mb-5"
          style="background-color:aqua; width:911px; height:592px">
          <iframe width="100%" height="100%" src="https://www.youtube.com/embed/gTowV_F07uI" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>              </div>
        </div>
        <div style="width:30%; max-height:592px; overflow-y:auto;">
          <WebRTCYoutube></WebRTCYoutube>
          <WebRTCYoutube></WebRTCYoutube>
          <WebRTCYoutube></WebRTCYoutube>
          <WebRTCYoutube></WebRTCYoutube>
          <WebRTCYoutube></WebRTCYoutube>
          <WebRTCYoutube></WebRTCYoutube>
        </div>
      </div>
      <RoomButton></RoomButton>
    </div>
    <div>
      <br>
      <br>
      <button v-if="chatONOFF" @click="chatoff">채팅창 접어두기 >></button>
      <button v-if="!chatONOFF" @click="chaton">&lt;&lt; 채팅창 열기</button>
      <br>
      <br>
      <AChat v-if="chatONOFF"></AChat>
    </div>
  </div>
</div>
</template>
<script>
import Timer from '@/components/SetTimer.vue';
import RoomButton from '@/components/Room/RoomButton.vue';
import AChat from '@/components/Room/AChat.vue';
import WebRTCYoutube from '../../components/Room/WebRTCYoutube.vue';

export default {
  name: 'YoutubeView',
  components: {
    Timer,
    WebRTCYoutube,
    RoomButton,
    AChat,
  },
  data() {
    return {
      chatONOFF: true,
    };
  },
  setup() {},
  created() {},
  moundted() {},
  unmounted() {},
  methods: {
    chatoff() {
      this.chatONOFF = false;
    },
    chaton() {
      this.chatONOFF = true;
    },
  },
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
