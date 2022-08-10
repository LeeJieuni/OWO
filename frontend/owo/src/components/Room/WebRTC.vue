<template>
  <div class="webrtctag col-4 m0p0 mb-2 mx-1">
    <!-- <div class="m0p0">
      <button id="img" @click="get_out">
        <img style="width: 25px" src="@/assets/icon/get_out.png" alt="" />
      </button>
    </div> -->
    <!-- <p>개인 WebRTC 영상</p> -->
    <div v-if="streamManager" class="position-relative m0p0">
      <div class="myreaction">{{newAllEMojiList[0]}}</div>
      <p class="myname">&ensp;{{ clientData }}&ensp;</p>
      <!-- <img class="mymic" v-if="!mic" src="@/assets/icon/micmute.png" alt=""> -->
      <!-- eslint-disable-next-line -->
      <ov-video class="ov-video" :stream-manager="streamManager" />
    </div>
  </div>
</template>
<script>
import { mapState } from 'vuex';
import OvVideo from './OvVideo.vue';

const emoji = 'emoji';
// const meetingroom = 'meetingroom';

export default {
  components: {
    OvVideo,
  },
  data() {
    return {
      user_isActive: false,
      user_get_out: false,
    };
  },
  setup() {},
  created() {},
  moundted() {},
  unmounted() {},
  methods: {
    get_out() {
      alert('추방하시겠습니까?');
      // alert 대신 confirm으로 별경한다.
      // const getout = confirm('추방하시겠습니까?')
      // comfirm 받은 것을 넘겨서 추방할 수 있도록 한다.
    },
    getConnectionData() {
      const { connection } = this.streamManager.stream;
      return JSON.parse(connection.data);
    },
  },

  computed: {
    ...mapState(emoji, ['allEmojiList']),
    // ...mapState(meetingroom, ['mic']),
    newAllEMojiList() {
      const newList = [];
      for (let i = this.allEmojiList.length - 1; i >= 0; i -= 1) {
        if (this.allEmojiList[i][0] === this.clientData) {
          newList.push(this.allEmojiList[i][1]);
          break;
        }
      }
      return newList;
    },
    clientData() {
      const { clientData } = this.getConnectionData();
      return clientData;
    },
  },
  props: {
    streamManager: Object,
  },
};
</script>
<style scoped>
#img {
  position: absolute;
  top: 5%;
  left: 88%;
  border: 0;
  outline: 0;
  opacity: 0;
  z-index:100;
}

.m0p0 {
  margin:0;
  padding:0;
  border-radius: 10px;
}

#img:hover {
  opacity: 1;
}

.webrtctag {
  width:520px;
  height:360px;
  border-radius: 20px;
  border: 3px solid #4e8aff;
  background-color:#eaf1ff;
}

.myname {
  position:absolute;
  top:-2px;
  left:-2px;
  background-color:#4e8aff;
  font-size:24px;
  border-radius: 20px 0px 10px 0px;
  z-index:600;
}

.myreaction {
  position:absolute;
  height:100%;
  top:0px;
  right:0px;
  font-size:150px;
  z-index:700;
}

.ov-video {
  width: 100.2%;
  border-radius: 20px;
  position:relative;
  top:-1px;
  left:-1px;
  z-index:500;
}

.mymic {
  position:absolute;
  bottom:20px;
  right:10px;
  font-size:15px;
  width:50px;
}
</style>
