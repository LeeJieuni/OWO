<template>
  <div>
    <div class="container-wrap d-flex justify-content-center">
      <div>
        <div class="d-flex justify-content-between align-items-center" style="height: 80px">
          <h3 class="m-0">방 이름</h3>
          <button class="btn btn-outline-secondary m-2" style="width: 145px" @click="startVideo">
            동영상 시작
          </button>
          <button class="btn btn-outline-secondary m-2" style="width: 145px" @click="pauseVideo">
            동영상 멈춤
          </button>
          <button class="btn btn-outline-secondary m-2" style="width: 145px" @click="stopVideo">
            동영상 종료
          </button>
          <button class="btn btn-outline-secondary m-2" style="width: 145px" @click="upVolume">
            음량 크게(+1)
          </button>
          <button class="btn btn-outline-secondary m-2" style="width: 145px" @click="downVolume">
            음량 작게(-1)
          </button>
          <Timer />
        </div>
        <div class="d-flex">
          <div
            class="d-flex align-items-start justify-content-start mx-1 mb-5"
            style="background-color: aqua; width: 911px; height: 592px"
          >
            <YouTube
              src="https://www.youtube.com/watch?v=sqgxcCjD04s"
              @ready="onReady"
              :vars="controls"
              @state-change="onChange"
              width="911"
              height="592"
              ref="youtube"
            />
            <!-- <YouTube
              src="https://www.youtube.com/watch?v=sqgxcCjD04s"
              @ready="onReady"
              ref="youtube"
            /> -->
          </div>
          <!-- <div>
          <div class="d-flex align-items-start justify-content-start mx-1 mb-5"
          style="background-color:aqua; width:911px; height:592px">
          <iframe width="100%" height="100%" :src="youtubeURL"
          title="YouTube video player" frameborder="0"
          allow="accelerometer; autoplay; clipboard-write; encrypted-media;
          gyroscope; picture-in-picture"
          allowfullscreen></iframe>
          </div>
        </div> -->
          <div style="width: 30%; max-height: 592px; overflow-y: auto">
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
    </div>
    <div>
      <br />
      <br />
      <button v-if="chatONOFF" @click="chatoff" class="chat">
        <img class="chatimg" src="@/assets/icon/commentoff.png" alt="" />
      </button>
      <button v-if="!chatONOFF" @click="chaton" class="chat">
        <img class="chatimg" src="@/assets/icon/commenton.png" alt="" />
      </button>
      <br />
      <br />
      <!-- <AChat v-if="chatONOFF"></AChat> -->
    </div>
  </div>
</template>
<script>
import Timer from '@/components/SetTimer.vue';
import RoomButton from '@/components/Room/RoomButton.vue';
// import AChat from '@/components/Room/AChat.vue';
import YouTube from 'vue3-youtube';
import WebRTCYoutube from '../../components/Room/WebRTCYoutube.vue';

export default {
  name: 'YoutubeView',
  components: {
    YouTube,
    Timer,
    WebRTCYoutube,
    RoomButton,
    // AChat,
  },
  data() {
    return {
      chatONOFF: false,
      youtubeURL: 'https://www.youtube.com/watch?v=sqgxcCjD04s',
      player: null,
      done: false,
      start: false,
      controls: {
        controls: 0,
      },
      volume: 0,
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

    startVideo() {
      this.$refs.youtube.playVideo();
      this.$refs.youtube.controls = 0;
      this.volume = this.$refs.youtube.getVolume();
    },
    pauseVideo() {
      this.$refs.youtube.pauseVideo();
    },
    stopVideo() {
      this.$refs.youtube.stopVideo();
    },
    onChange() {
      if (this.$refs.youtube.getPlayerState() === -1) {
        if (!this.start) {
          this.start = true;
        } else {
          alert('동영상 끝! -> 방 종료 로직 넣으면 될 듯 ㅎㅎ');
          this.start = false;
        }
      }
    },
    upVolume() {
      console.log(`==============${this.volume}`);
      if (this.volume === 100) {
        alert('볼륨 최대 수치');
      } else {
        this.volume += 1;
        this.$refs.youtube.setVolume(this.volume);
      }
      console.log(this.$refs.youtube.getVolume());
    },
    downVolume() {
      console.log(`==============${this.volume}`);
      if (this.volume === 0) {
        alert('볼륨 최소 수치');
      } else {
        this.volume -= 1;
        this.$refs.youtube.setVolume(this.volume);
      }
      console.log(this.$refs.youtube.getVolume());
    },
  },
};
</script>
<style scoped>
.container {
  width: 100vw;
}
div {
  color: black;
}

.chat {
  position: fixed;
  bottom: 30px;
  right: 30px;
}

.chatimg {
  width: 50px;
}
</style>
