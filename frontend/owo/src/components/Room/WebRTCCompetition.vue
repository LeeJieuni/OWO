<template>
  <div>
    <div class="card mx-1" style="width: 25rem; height:17rem;">
      <div class="d-flex justify-content-between">
        <div class="ms-2 my-2" style="text-align:left;">
            <span class="p-1 text-white" style="border-radius: 5px; background-color:#4e8aff;">
            {{ username }}</span>
        </div>
        <div class="me-2 my-2" style="text-align:left;">
            <span class="p-1 text-white" style="background-color:#4e8aff; border-radius: 5px;">
              {{ usercount }} 회</span>
        </div>
      </div>
      <div class="d-flex justify-content-center align-items-center" style="width:100%; height:100%">
        <div>
          <button id="img" @click="get_out"><img style="width:25px;"
          src="@/assets/icon/get_out.png" alt="">
          </button>
        </div>
        <div>
            <p>개인 WebRTC 영상</p>
        </div>
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
      usercount: 3,
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
