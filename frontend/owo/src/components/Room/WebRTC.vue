<template>
  <div class="col col-4">
    <div class="me-2 mb-5" style="width: 30rem; height: 20rem">
      <div>
        <button id="img" @click="get_out">
          <img style="width: 25px" src="@/assets/icon/get_out.png" alt="" />
        </button>
      </div>
      <!-- <p>개인 WebRTC 영상</p> -->
      <div v-if="streamManager">
        <ov-video style="width:100%; height:100%;" :stream-manager="streamManager" />
        <div>
          <p>{{ clientData }}</p>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import OvVideo from "./OvVideo.vue";

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
      alert("추방하시겠습니까?");
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

#img:hover {
  opacity: 1;
}
</style>
