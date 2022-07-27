<template>
  <div class="container-fluid">
    <div class="container">
      <div class="d-flex">
        <div style="width:100%;">

          <div class="d-flex justify-content-between align-items-center" >
            <h3 class="m-0">방 이름</h3>
            <Timer></Timer>
          </div>

          <div class="d-flex">
            <div style="width:70%;">
              <div class="d-flex align-items-center justify-content-center mt-5"
              style="background-color:aqua;  height:400px">
              <iframe width="100%" height="100%" src="https://www.youtube.com/embed/gTowV_F07uI" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>              </div>
              <div class="mt-3 d-flex align-items-center justify-content-center"
              style="background-color:gray; height:50px;">
                도구 모음
              </div>
            </div>
            <div class="mt-5" style="width:30%; max-height:470px; overflow-y:auto;">
              <div class="card mt-0 m-2 ">
                <img src="" class="card-img-top" alt="...">
                <div class="card-body">
                  <h5 class="card-title">Card title</h5>
                  <p class="card-text">Some quick example text to build onontent.</p>
                  <a href="#" class="btn btn-primary">Go somewhere</a>
                </div>
              </div>
              <div class="card m-2">
                <img src="" class="card-img-top" alt="...">
                <div class="card-body">
                  <h5 class="card-title">Card title</h5>
                  <p class="card-text">Some quick example text to build onontent.</p>
                  <a href="#" class="btn btn-primary">Go somewhere</a>
                </div>
              </div>
              <div class="card m-2">
                <img src="" class="card-img-top" alt="...">
                <div class="card-body">
                  <h5 class="card-title">Card title</h5>
                  <p class="card-text">Some quick example text to build onontent.</p>
                  <a href="#" class="btn btn-primary">Go somewhere</a>
                </div>
              </div>
              <div class="card m-2">
                <img src="" class="card-img-top" alt="...">
                <div class="card-body">
                  <h5 class="card-title">Card title</h5>
                  <p class="card-text">Some quick example text to build onontent.</p>
                  <a href="#" class="btn btn-primary">Go somewhere</a>
                </div>
              </div>
              <div class="card m-2">
                <img src="" class="card-img-top" alt="...">
                <div class="card-body">
                  <h5 class="card-title">Card title</h5>
                  <p class="card-text">Some quick example text to build onontent.</p>
                  <a href="#" class="btn btn-primary">Go somewhere</a>
                </div>
              </div>
              <div class="card m-2">
                <img src="" class="card-img-top" alt="...">
                <div class="card-body">
                  <h5 class="card-title">Card title</h5>
                  <p class="card-text">Some quick example text to build onontent.</p>
                  <a href="#" class="btn btn-primary">Go somewhere</a>
                </div>
              </div>
            </div>
          </div>
        </div>

      </div>
    </div>
  </div>
</template>
<script>
import Timer from '@/components/SetTimer.vue';

export default {
  name: 'FriendNoChatView',
  components: {
    Timer,
  },
  data() {
    return {
    };
  },
  setup() {},
  created() {},
  moundted() {},
  unmounted() {},
  methods: {},
};
</script>
<style scoped>
div {
  color:black;
}
</style>
