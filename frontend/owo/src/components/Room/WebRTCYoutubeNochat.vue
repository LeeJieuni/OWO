<template>
    <div class="mx-1">
        <div class="card ms-2 me-2 mb-2 d-flex justify-content-center align-items-center"
        style="width: 22rem; height:14rem;">
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
</template>
<script>
export default {
  components: {},
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
