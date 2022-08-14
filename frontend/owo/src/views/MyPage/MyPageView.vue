<template>
  <div class="mypageview">
    <!-- <div class="spinner-div" v-if="isLoading">
      <q-spinner-cube
      color="primary"
      size="5em"
      />
    </div> -->
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
            </form>
            </div>
          </div>
        </div>
      </div>
    </b-modal>

    <!-- <div class="background-box"> -->
    <div class="front-box row">
      <div class="mypageContainer row">
        <div class="col-3 sidebar m-0 p-0">
          <MySidebar/>
        </div>
        <div class="col-9 m-0 p-0 right">
          <div class="title text-center">
            <h4>{{slogan}}</h4>
            <form action="">
              <label for="slogan">
                <input type="text" id="slogan" v-model="state.sloganData.slogan">
                <!-- {{ state.sloganData.slogan }} -->
              </label>
              <button @click.prevent="updateSlogan($event)">변경</button>
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
    // store.dispatch('record/fetchPercentage');
    const slogan = computed(() => store.getters['accounts/slogan']);
    const user = computed(() => store.getters['accounts/userInfo']);
    store.dispatch('accounts/fetchMypage');
    const state = reactive({
      sloganData: {
        id: user.value.id,
        slogan: slogan.value,
      },
      preloadImgUrl: '',
      imgData: '',
    });

    // Methods
    const updateSlogan = function (e) {
      e.preventDefault();
      console.log('보낸다');
      console.log(this.state.sloganData);
      store.dispatch('accounts/updateSlogan', this.state.sloganData);
    };
    const updateProfileImg = (img) => {
      // e.preventDefault();
      // console.log(e.target);
      // console.log(img);
      const formData = new FormData();
      formData.append('file', img);
      console.log('폼데이터 값 출력', formData.get('file'));
      store.dispatch('accounts/updateProfileImg', formData);
    };
    const uploadProfileImg = (e) => {
      e.preventDefault();
      console.log('이미지 불러오기');
      let img = e.target.files[0];
      console.log('파일사이즈 검사');
      if (img.size > (2 * 1024 * 1024)) {
        alert('파일 사이즈가 2mb를 넘습니다.');
        img = null;
      } else if (img.type.indexOf('image/png') < 0) {
        alert('이미지(png) 파일만 업로드 가능합니다.');
        img = null;
      } else {
        console.log('처리 후');
        state.preloadImgUrl = URL.createObjectURL(img);
        state.imgData = img;
        setTimeout(() => {
          // eslint-disable-next-line
          const imgConfirm = confirm('업로드 하시겠습니까?');
          if (imgConfirm) {
            updateProfileImg(img);
          } else {
            alert('다시 선택해주세요.');
            img = null;
          }
        }, 1000);
      }
      // const preview = URL.createObjectURL(file);
      // console.log(file);
      // console.log(file.type);
      // // this.previewUrl = preview;
      // console.log(preview);
      // // console.log(file);
      // let validation = true;
      // let message = '';

      // 파일 용량 제한
      // if (file.size > (2 * 1024 * 1024)) {
      //   message = `${message}, 파일 용량은 2MB 이하만 가능합니다.`;
      //   validation = false;
      // }
      // if (file.type.indexOf('image/png') < 0) {
      //   message = `${message}, 이미지 파일만 업로드 가능합니다.`;
      //   validation = false;
      // }

      // if (validation) {
      //   state.imgFile = file;
      //   // this.preview = URL.createObjectURL(file);
      //   // console.log(this.preview);
      // } else {
      //   state.imgFile = '';
      //   alert(message);
      // }
    };
    // const uploadProfileImg = (event) => {
    //   event.preventDefault();
    //   console.log('이미지 불러오기');
    //   const img = event.target;
    //   if (img.files && img.files[0]) {
    //     const reader = new FileReader();
    //     reader.onload = (e) => {
    //       this.preloadImg = e.target.target;
    //     };
    //     reader.readAsDataURL(img.files[0]);
    //   }
    //   // console.log('파일사이즈 검사');
    //   // if (img.size > (2 * 1024 * 1024)) {
    //   //   alert('파일 사이즈가 2mb를 넘습니다.');
    //   //   img = null;
    //   //   this.preloadImgUrl = null;
    //   // } else {
    //   //   updateProfileImg(img);
    //   //   this.preloadImgUrl = img;
    //   // }
    // };

    return {
      slogan,
      state,
      updateSlogan,
      updateProfileImg,
      uploadProfileImg,
      // fetchPercentage,
      // fetchGoal,
      // fetchAchievementRate,
      // fetchProfileImg
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
    background-color: #F6F7F9;
    overflow-x: hidden;
    height: 94vh;
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
    margin-bottom: 70px;
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
  .right {
    border-radius: 20px;
  }
</style>
