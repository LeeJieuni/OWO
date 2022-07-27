<template>
    <div>
        <div class="card me-3 d-flex justify-content-center align-items-center"
        style="width: 25rem; height:18rem;">
        <div id="img_hover">
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
#img_hover {
  display: inline-block;
}

#img {
  display: none;
  position : absolute;
  top:5%;
  left:88%;
  border: 0;
  outline: 0;
  background-color: #ffffff;
  opacity: 0;
}

#img_hover:hover #img {
  display: block;
}

</style>
