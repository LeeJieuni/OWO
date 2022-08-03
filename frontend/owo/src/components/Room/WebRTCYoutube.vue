<template>
  <div class="card mx-2 my-2" style="width: 22rem; height:14rem;">
    <div class="ms-2 my-2" style="text-align:left;">
      <span class="p-1 text-white" style="background-color:#4e8aff;">{{ username }}</span>
    </div>
    <div class="ms-2 me-2 mb-2 d-flex justify-content-center align-items-center"
    style="width: 100%; height:100%;">
      <div>
        <button id="img" @click="get_out"><img style="width:25px;"
        src="@/assets/icon/get_out.png" alt="">
        </button>
      </div>
      <div>
          <p>개인 WebRTC 영상(Youtube)</p>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  components: {},
  data() {
    return {
      username: '이준호',
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
    },
  },
};
</script>
<style scoped>
#img {
  position : absolute;
  top:5%;
  left:88%;
  border: 0;
  outline: 0;
  background-color: #ffffff;
  opacity: 0;
}

#img:hover {
  opacity: 1;
}

</style>
