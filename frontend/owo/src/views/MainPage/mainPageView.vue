<template>
  <div>
    <Carousel></Carousel>
    <rankingList/>
    <roomTab></roomTab>
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
import { createNamespacedHelpers } from 'vuex';
import swal from 'sweetalert2';

window.Swal = swal;

const { mapGetters } = createNamespacedHelpers('accounts');

export default {
  components: {
    Carousel,
    rankingList,
    roomTab,
  },
  computed: {
    ...mapGetters(['userInfo', 'isLogin']),
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
  methods: {
    setCookie() {
      this.$cookies.set('nosee', 'Y', '7d');
    },
  },
  created() {
    const check = this.$cookies.get('nosee');
    if (check !== null) { // 쿠키가 있는 경우
      if (this.userInfo.weight === null && this.isLogin === true && check !== 'Y') {
        this.$swal({
          title: '#오운완',
          input: 'checkbox',
          inputPlaceholder: '일주일 간 보지 않기',
          html:
            '추가 정보를 입력하시면, <br>더 많은 서비스를 이용하실 수 있습니다. <br> <a href="mypage/update"><strong>My Page</strong></a>로 이동하기. <hr>',
          icon: 'info',
          showCloseButton: true,
        }).then((res) => {
          if (res.value === 1) {
            this.setCookie();
          }
        });
      }
    }
    if (check === null) { // 쿠키가 없는 경우
      if (this.userInfo.weight !== null && this.isLogin === true) {
        this.$swal({
          title: '#오운완',
          input: 'checkbox',
          inputPlaceholder: '일주일 간 보지 않기',
          html:
            '추가 정보를 입력하시면, <br>더 많은 서비스를 이용하실 수 있습니다. <br> <a href="mypage/update"><strong>My Page</strong></a>로 이동하기. <hr>',
          icon: 'info',
          showCloseButton: true,
        }).then((res) => {
          if (res.value === 1) {
            this.setCookie();
          }
        });
      }
    }
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
