<template>
  <div class="container-wrap d-flex justify-content-center">
    <div>
      <div class="d-flex justify-content-between align-items-center"
      style="height:80px;">
        <h3 class="m-0">여기에 방 이름 들어갑니다.</h3>
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
      <button v-if="chatONOFF" @click="chatoff" class="chat">
        <img class="chatimg" src="@/assets/icon/commentoff.png" alt="">
      </button>
      <button v-if="!chatONOFF" @click="chaton" class="chat">
        <img class="chatimg" src="@/assets/icon/commenton.png" alt="">
      </button>
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
      chatONOFF: false,
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
.chat {
  position:fixed;
  bottom: 30px;
  right: 30px;
}

.chatimg {
  width:50px;
}

</style>
