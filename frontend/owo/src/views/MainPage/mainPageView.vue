<template>
  <div>
    <Carousel></Carousel>
    <rankingList/>
    <roomTab></roomTab>
    <hr>
    <!--TEST -->
    <br><br><br><br>
    <button type="button" class="btn btn-primary"
      data-bs-toggle="modal" data-bs-target="#exampleModal">
        Launch demo modal
    </button>
    <div class="modal fade" id="exampleModal" tabindex="-1"
    aria-labelledby="exampleModalLabel" aria-hidden="true">
      <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="exampleModalLabel">Modal title</h5>
            <button type="button" class="btn-close"
            data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body">
            <form action="">
              <label for="profileImag">이미지
                <input
                  id="profileImg"
                  class="input-image"
                  accept="image/*"
                  type="file"
                  ref="profileImg"
                  @change="onInputImage()"
                >
              </label>
              <button class="send-btn" @click="onClickImgButton">이미지버튼</button>
            </form>
          </div>
          <!-- <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
            <button type="button" class="btn btn-primary">Save changes</button>
          </div> -->
        </div>
      </div>
    </div>
  </div>
</template>

<script>
// import { useStore } from 'vuex';
// import { reactive, computed } from 'vue';
import Carousel from '@/components/MainPage/Carousel.vue';
import rankingList from '@/components/MainPage/rankingList.vue';
import roomTab from '@/components/MainPage/roomTab.vue';

export default {
  components: {
    Carousel,
    rankingList,
    roomTab,
  },
  setup() {
    const onInputImage = () => {
      const input = document.querySelector('.input-image');
      console.log(input.files[0].type);
    };
    return {
      onInputImage,
    };
  },
};
</script>

<style scoped>
.start-btn {
    margin-right: 30px;
}

.btn-lg {
  height: 40px;
  text-align: center;
  line-height: 10px;
  font-size: 18px;
}
.owo {
  font-weight: 600;
  text-shadow: #7b7b7b 1px 1px;
}

.first-intro-text {
  font-size: 18px;
  line-height: 15px;
}
.btns {
  padding-top: 5px;
  padding-bottom: 20px;
}
</style>
