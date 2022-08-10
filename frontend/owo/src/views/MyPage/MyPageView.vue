<template>
  <div class="mypageview">
    <b-modal id="image-upload" size="md" hide-footer hide-header centered>
      <div>
        <h3 class="modal-title text-center mt-4">프로필 이미지 업로드📷</h3>
        <div class="wrapper">
          <div class="container">
            <div class="row row-cols-3">
              <div class="img-wrapper">
                <img
                  class="profile-img"
                  :src="state.preloadImgUrl"
                  alt="프로필이미지"
                >
              </div>
              <form enctype="multipart/form-data" imgFormData>
              <label for="profileImag">
                <input class="input-image" accept="image/*" type="file"
                  ref="profileImg" @change.prevent="uploadProfileImg($event)" id="profileImg">
              </label>
              <div class="d-flex justify-content-center">
                <button on @click="updateProfileImg($event)"
                  class="btn btn-primary" form="imageUploadForm" >작성</button>
              </div>
              <!-- <button type="submit" class="btn btn-outline-primary"
                @click.prevent="updateProfileImg($event)" for="imageUploadForm">이미지 보내기</button> -->
            </form>
            </div>
          </div>
        </div>
        <div class="d-flex justify-content-center">
          <button on @click="updateProfileImg($event)"
            class="btn btn-primary" form="imageUploadForm">작성</button>
        </div>
      </div>
    </b-modal>

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
// import multer from

export default {
  name: 'MyPage',
  components: { MySidebar },
  setup() {
    const store = useStore();
    store.dispatch('record/fetchSessions');
    store.dispatch('record/fetchPercentage');
    const slogan = computed(() => store.getters['accounts/slogan']);
    const user = computed(() => store.getters['accounts/userInfo']);

    const state = reactive({
      sloganData: {
        id: user.value.id,
        slogan: slogan.value,
      },
      preloadImgUrl: '',
      // imgFormData: '',
    });

    // action
    const updateSlogan = function (sloganData) {
      // console.log(slogan);
      store.dispatch('accounts/updateSlogan', sloganData);
    };
    const fetchPercentage = function () {
      store.dispatch('record/fetchPercentage');
    };

    // Methods
    const updateProfileImg = (e) => {
      e.preventDefault();
      const img2 = document.querySelector('.input-image');
      // console.log('이미지 불러오기');
      // console.log(e.target.files[0]);
      // const img = e.target.files[0];
      const formData = new FormData();
      // const imgFile = {
      // }
      formData.append('file', img2.files[0]);
      formData.append('file', new Blob([JSON.stringify(img2)], { type: 'image/*' }));
      // console.log(state.imgFormData);
      store.dispatch('accounts/updateProfileImg', formData);
    };
    const uploadProfileImg = (e) => {
      // const img2 = document.querySelector('.input-image');
      // const modal = document.querySelector('.modal');
      e.preventDefault();
      // const multer = require('multer');
      // const path = require('path');
      console.log('이미지 불러오기');
      let img = e.target.files[0];
      // console.log(img);
      // console.log(img2.files[0]);
      console.log('파일사이즈 검사');
      if (img.size > (2 * 1024 * 1024)) {
        alert('파일 사이즈가 2mb를 넘습니다.');
        img = null;
      } else {
        console.log('처리 후');
        state.preloadImgUrl = URL.createObjectURL(img);
        this.imgFormData.append('file', img);
      }
      // const formData = new FormData();
      // const imgFile = {
      // }
      // formData.append('file', img);
      // store.dispatch('accounts/updateProfileImg', formData);
      // const formData = new FormData();
      // formData.append('name', img.name);
      // const imgFile = {

      // }
      // formData.append('file', img);
      // formData.append('user_id', user.value.id);
      // formData.append('date', new Date());
      // console.log(formData);
      // eslint-disable-next-line
      // formData.append('file', new Blob([JSON.stringify(state.data)], { type: 'application/json' }));
      // formData.append('file', new Blob([img], { type: 'application/octet-stream' });, img.name);
      // formData.append('file', new Blob([img], { type: `${img.type}` }), img.name);
      // for (let i; i < formData.length; i += 1) {
      // // console.log(formData[i]);
      // }
      // console.log(formData);
      // // eslint-disable-next-line

      // store.dispatch('accounts/updateProfileImg', formData);
    };

    return {
      slogan,
      state,
      updateSlogan,
      uploadProfileImg,
      updateProfileImg,
      fetchPercentage,
      user,
    };
  },
  created() {
  },
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
  .img-wrapper {
    position: relative;
    width: 150px;
    height: 150px;
  }
  .img-wrapper img {
    border-radius: 50%;
    /* margin: 0 auto; */
    position: absolute;
    top: 0;
    left: 0;
    transform: translate(50, 50);
    width: 100%;
    height: 100%;
    object-fit: cover;
    margin: auto;
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
    padding: 0;
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

  .container {
    height: 100%;
  }
  button:not(.btn) {
    border: none;
    background-color: white;
  }
</style>
