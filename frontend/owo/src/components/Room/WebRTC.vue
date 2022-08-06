<template>
  <div class="webrtctag col-4 m0p0 mb-2 mx-1">
    <div class="m0p0">
      <button id="img" @click="get_out">
        <img style="width: 25px" src="@/assets/icon/get_out.png" alt="" />
      </button>
    </div>
    <!-- <p>개인 WebRTC 영상</p> -->
    <div v-if="streamManager" class="m0p0">
      <p class="myname">{{ clientData }}</p>
      <ov-video class="ov-video" :stream-manager="streamManager" />
    </div>
  </div>
</template>
<script>
import OvVideo from './OvVideo.vue';

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
  border: solid 1px black;
}

#img:hover {
  opacity: 1;
}

.webrtctag {
  width: 600px;
  height:100%;
}

.myname {
  display: inline;
  position:relative;
  top:0px;
  left:0px;
  background-color:white;
  font-size:24px;
  padding: 10px;
}
/* .ov-video {
  width: 100%;
  height: 100%;
} */
</style>
