<template>
  <div class="mypageview">
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
            <form enctype="multipart/form-data">
              <label for="profileImag">이미지
                <input class="input-image" accept="image/*" type="file"
                  ref="profileImg" @change.prevent="updateProfileImg($event)" id="profileImg">
              </label>
              <button class="send-btn" @click.prevent="updateProfileImg($event)">이미지버튼</button>
            </form>
          </div>
          <!-- <div class="modal-footer">
            <button type="button" class="btn btn-secondary"
              data-bs-dismiss="modal">Close</button>
            <button type="button" class="btn btn-primary">Save changes</button>
          </div> -->
        </div>
      </div>
    </div>
    <!-- <div class="background-box"> -->
      <div class="front-box row">
        <div class="mypageContainer row">
          <div class="col-3 sidebar m-0 p-0">
            <MySidebar/>
          </div>
          <div class="col-9 m-0 p-0">
            <div class="title text-center">
              <h4>{{slogan}}</h4>
              <form action="">
                <label for="slogan">
                  <input type="text" id="slogan" v-model="slogan">
                </label>
                <button @click.prevent="updateSlogan(slogan)">변경</button>
              </form>
            </div>
            <div>
              <router-view></router-view>
            </div>
          </div>
        </div>
      </div>
    <!-- </div> -->
  </div>
</template>

<script>
import MySidebar from '@/components/MyPage/MySidebar.vue';
import { useStore } from 'vuex';
import { computed, reactive } from 'vue';

export default {
  name: 'MyPage',
  components: { MySidebar },
  setup() {
    const store = useStore();
    const slogan = computed(() => store.getters['accounts/slogan']);
    const user = computed(() => store.getters['accounts/userInfo']);
    // console.log('dd');
    // console.log(slogan.value);
    const state = reactive({
      imagePath: '',
      // sloganData: 'ss',
      sloganData: {
        id: user.value.id,
        slogan: slogan.value,
      },
      data: {
        id: user.value.id,
        slogan: slogan.value,
      },
    });
    // action
    const updateSlogan = function (sloganData) {
      // console.log(slogan);
      store.dispatch('accounts/updateSlogan', sloganData);
    };
    // Methods
    const updateProfileImg = (e) => {
      const img2 = document.querySelector('.input-image');
      const modal = document.querySelector('.modal');
      // e.preventDefault();
      console.log('이미지 불러오기');
      const img = e.target.files[0];
      console.log(img);
      console.log(img2.files[0]);
      console.log('파일사이즈 검사');
      if (img.size > (2 * 1024 * 1024)) {
        alert('파일 사이즈가 20mb를 넘습니다.');
        this.$refs.profileImg.value = null;
        img[0].select();
        document.selection.clear();
      }
      modal.style.display = 'none';
      img2.files[0] = null;
      console.log('처리 후');
      console.log(img);
      console.log(img2);
      if (img.size > (2 * 1024 * 1024)) {
        alert('파일 사이즈가 20mb를 넘습니다.');
        this.$refs.profileImg.value = null;
      }
      // console.log('처리 후');
      // console.log(this.$refs.profileImg);
      // const formData = new FormData();
      // formData.append('file', img);
      // eslint-disable-next-line
      // formData.append('request', new Blob([JSON.stringify(state.data)], { type: 'application/json' }));
      // // store.dispatch('profileUpdate', formData);
      // console.log('폼데이타');
      // console.log(formData);
      // // eslint-disable-next-line
      // for (const pair of formData.entries()) {
      //   console.log(pair[0], pair[1]);
      // }
      // store.dispatch('accounts/updateProfileImg', formData);
    };
    return {
      slogan,
      state,
      updateSlogan,
      updateProfileImg,
      user,
    };
  },
  created() {},
  mounted() {},
  unmounted() {},
  // methods: {
  // sloganput(e) {
  //   this.slogan = e.target.value;
  // },
  // },
};
</script>

<style scoped>
  a {
    text-decoration: none;
  }

  .mypageview {
    text-align: center;
    color: black;
  }
  h1, h2, h3, h4, h5 {
    font-weight: 900;
  }
  .mypageContainer {
    font-weight: 600;
    /* text-shadow: #7b7b7b 1px 1px; */
    max-width: 900px;
    margin: 0 auto;
    background-color: white;
    text-align: center;
    border: solid #DFDFDF 1px;
    box-sizing: border-box;
    border-radius: 20px;
    height: 100%;
    box-shadow: rgb(105, 105, 105) 3px 3px 10px;
    opacity: 93%;
    /* padding: 35px; */
    margin-bottom: 100px;
  }
  .title {
    border-bottom: solid #DFDFDF 1px;
    font-weight: 800;
    margin-top: 10px;
    height: 100px;
    padding: 30px;
  }
/*
  .background-box {
    width: 100vw;
    height: 500px;
    background-image: url("https://i.pinimg.com/originals/07/7e/a6/077ea6de7db29b564b4335dfd2ba7c15.jpg");
    background-size: 100vh;
    position: relative;
} */
  .front-box {
    position: relative;
    padding-top: 100px;
    backdrop-filter: blur(6px);
  }
  .sidebar {
    border-right: solid 1px #DFDFDF;
  }
</style>
