<template>
  <div>
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
import { useStore } from 'vuex';
import { reactive, computed } from 'vue';

export default {
  name: 'MyImgModal',
  components: {
    // TeleportExample,
    // MyImgModal,
  },
  setup() {
    const store = useStore(); // 필수
    // Getters
    const user = computed(() => store.getters['accounts/userInfo']);

    // State (vue3의 data 선언 방식. state라는 이름으로 접근)
    const state = reactive({
      selected: '',
      options: [
        { text: '없음', value: '1' },
        { text: '약간', value: '2' },
        { text: '중간', value: '3' },
        { text: '많음', value: '4' },
        { text: '매우 많음', value: '5' },
      ],
      input: {
        image: '',
      },
      testText: '할수있다',
    });
    // Methods
    const onInputImage = () => {
      const inputImg = document.querySelector('.input-image');
      console.log(inputImg.files[0]);
      // console.log(input.e.target);
    };
    return {
      state,
      user,
      onInputImage,
    };
  },
};
</script>
