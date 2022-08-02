<template>
    <div class="d-flex justify-content-center">
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
            <div>
            <Emoji v-bind:style="{position: 'absolute', top: 50 +'px', left: 0 + 'px'}"
            v-if="Emoji_ONOFF"/>
            <button id="emoji_btn" @click="open_emoji" class="btn btn-outline-secondary m-2">이모티콘
                <img class="menu_icon" src="@/assets/icon/emoji.png" alt="emoji">
            </button>
            </div>
            <RoomSetting></RoomSetting>
            <AfterExerciseModal/>
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
    <!-- <div>
      ex: {{ ex }}
    </div>
    <div>
      ey: {{ ey }}
    </div>
    <div>
      이모지 포지션 잡는 중
    </div> -->
</template>
<script>
import AfterExerciseModal from './AfterExerciseModal.vue';
import Emoji from '../MyEmoji.vue';
import RoomSetting from './RoomSetting.vue';

let emojiX;
let emojiY;

window.onload = function () {
  emojiX = document.getElementById('emoji_btn').getClientRects()[0].x;
  emojiY = document.getElementById('emoji_btn').getClientRects()[0].bottom;
  // console.log(emojiX);
  // console.log(emojiY);
  // console.log('top :', document.getElementById('emoji_btn').getClientRects()[0].top);
  // console.log('bottom :', document.getElementById('emoji_btn').getClientRects()[0].bottom);
  // console.log('left :', document.getElementById('emoji_btn').getClientRects()[0].left);
  // console.log('right :', document.getElementById('emoji_btn').getClientRects()[0].right);
  // console.log('height :', document.getElementById('emoji_btn').getClientRects()[0].height);
  // console.log('width :', document.getElementById('emoji_btn').getClientRects()[0].width);
  // console.log('x :', document.getElementById('emoji_btn').getClientRects()[0].x);
  // console.log('y :', document.getElementById('emoji_btn').getClientRects()[0].y);
};

export default {
  components: {
    Emoji,
    AfterExerciseModal,
    RoomSetting,
  },
  data() {
    return {
      // input: '',
      // search: '',
      Emoji_ONOFF: null,
      isMaster: true,
      video: true,
      mic: true,
      timer: 3,
      temp_timer: 3,
      take_photo_timer: null,
      ey: null,
      ex: null,
      is_take_photo: false,
      // emoji_h: document.getElementById('emoji_btn').top,
    };
  },
  setup() {},
  created() {},
  moundted() {
  },
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
    open_emoji() {
      this.Emoji_ONOFF = !this.Emoji_ONOFF;
      this.ex = emojiX;
      this.ey = emojiY;
    },
  },
};
</script>
<style scoped>
.Emoji {
  position:fixed;
  top:0px;
  left:0px;
  z-index: 103;
}
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
