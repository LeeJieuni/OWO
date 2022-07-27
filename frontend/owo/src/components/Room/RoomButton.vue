<template>
    <div style="background-color:;">
        <div class="d-flex justify-content-start align-items-center">
            <button class="btn btn-outline-secondary m-2 " style="width:145px;"
            @click="mic_on_off" v-if="mic">
                <img class="menu_icon" src="@/assets/icon/mic_on.png" alt="mic_on">
                음소거 해제
            </button>
            <button class="btn btn-outline-secondary m-2 " style="width:145px;"
            @click="mic_on_off" v-if="!mic">
                <img class="menu_icon" src="@/assets/icon/mic_off.png" alt="mic_off">
                음소거
            </button>
            <button class="btn btn-outline-secondary m-2" style="width:145px;"
            @click="video_on_off" v-if="video">
                <img class="menu_icon" src="@/assets/icon/video_on.png" alt="video_on">
                비디오 시작
            </button>
            <button class="btn btn-outline-secondary m-2" style="width:145px;"
            @click="video_on_off" v-if="!video">
                <img class="menu_icon" src="@/assets/icon/video_off.png" alt="video_off">
                비디오 중지
            </button>
            <div class="m-2">
            <b-dropdown split @click="take_photo" variant="outline-secondary">
                <template #button-content >
                    <img class="menu_icon" src="@/assets/icon/photo.png" alt="photo">
                    사진촬영
                </template>
                <b-dropdown-item :value="timer" @click="set_timer_3">3초</b-dropdown-item>
                <b-dropdown-item :value="timer" @click="set_timer_5">5초</b-dropdown-item>
                <b-dropdown-item :value="timer" @click="set_timer_10">10초</b-dropdown-item>
            </b-dropdown>
            </div>
            <button class="btn btn-outline-secondary m-2">
                <img class="menu_icon" src="@/assets/icon/emoji.png" alt="mic_off">
            </button>
            <button class="btn btn-outline-secondary m-2">
                <img class="menu_icon" src="@/assets/icon/room_setting.png" alt="mic_off">
                설정
            </button>
            <button class="btn btn-outline-secondary m-2">
                <img class="menu_icon2" src="@/assets/icon/room_end.png" alt="mic_off">
                종료
            </button>
        </div>
        <div class="d-flex justify-content-center align-items-center text-white"
        v-if="is_take_photo" id="take_photo_background">
        </div>
        <div class="d-flex justify-content-center align-items-center text-white"
        v-if="is_take_photo" id="take_photo_WebRTC">
        </div>
        <div class="d-flex justify-content-center align-items-center text-white mt-4"
        v-if="is_take_photo" id="take_photo_timer">
        {{ timer }}
        </div>
    </div>
</template>
<script>
export default {
  components: {},
  data() {
    return {
      video: true,
      mic: true,
      timer: 3,
      temp_timer: 3,
      take_photo_timer: null,
      is_take_photo: false,
    };
  },
  setup() {},
  created() {},
  moundted() {},
  unmounted() {},
  methods: {
    mic_on_off() {
      this.mic = !this.mic;
    },
    video_on_off() {
      this.video = !this.video;
    },
    take_photo() {
      this.temp_timer = this.timer;
      this.is_take_photo = true;
      this.take_photo_timer = setInterval(() => {
        this.timer -= 1;
        console.log(this.timer);
        if (this.timer === 0) {
          console.log('사진찍는 모션');
          clearInterval(this.take_photo_timer);
          this.is_take_photo = false;
          this.timer = this.temp_timer;
        }
      }, 1000);
    },
    set_timer_3() {
      this.timer = 3;
      console.log(this.timer);
    },
    set_timer_5() {
      this.timer = 5;
      console.log(this.timer);
    },
    set_timer_10() {
      this.timer = 10;
      console.log(this.timer);
    },
  },
};
</script>
<style scoped>
.menu_icon {
    width:30px;
}

.menu_icon2 {
    width:25px;
}

.menu_icon3 {
    width:15px;
}

#take_photo_background {
    background-color: gray;
    position: fixed;
    overflow: hidden;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    z-index: 99;
    opacity: 0.5;
    font-size:300px;
}
#take_photo_WebRTC {
    border: 1px solid black;
    background-color: darkgray;
    position: fixed;
    top: 10%;
    left: 20%;
    width: 60%;
    height: 80%;
    z-index: 100;
}

#take_photo_timer {
    position:fixed;
    top: 0%;
    left: 0%;
    width: 100%;
    height: 100%;
    z-index: 101;
    font-size:300px;
}

</style>
