<template>
    <div>
        <div class="card me-3 d-flex justify-content-center align-items-center"
        style="width: 25rem; height:18rem;">
          <img onclick="get_out()" id="img" src="@/assets/icon/more.png" alt="">
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
      user1_isActive: false,
      user2_isActive: true,
      user3_isActive: true,
      user4_isActive: true,
      user5_isActive: true,
      user6_isActive: true,
    };
  },
  setup() {},
  created() {},
  moundted() {},
  unmounted() {},
  methods: {
    get_out() {
      alert('hello');
    },
  },
};
</script>
<style scoped>
#img {
  width: 25px;
  position : absolute;
  top:5%;
  left:90%;
}
</style>
