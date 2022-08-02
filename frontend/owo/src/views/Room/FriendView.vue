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
    <div>
      <br>
      <br>
      <button v-if="chatONOFF" @click="chatoff" >채팅창 접어두기 >></button>
      <button v-if="!chatONOFF" @click="chaton">&lt;&lt; 채팅창 열기</button>
      <br>
      <br>
      <AChat v-if="chatONOFF"></AChat>
    </div>
  </div>
</template>
<script>
import Timer from '@/components/SetTimer.vue';
import WebRTC from '@/components/Room/WebRTC.vue';
import RoomButton from '@/components/Room/RoomButton.vue';
import AChat from '@/components/Room/AChat.vue';

export default {
  name: 'FriendView',
  components: {
    Timer,
    WebRTC,
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
div {
  color:black;
}
.container {
  width:100vw;
  height:100vh;
}

</style>
