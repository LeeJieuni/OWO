<template>
  <NavBar class="sticky-top"/>
  <div class="mypageview">
    <!-- <div class="spinner-div" v-if="isLoading">
      <q-spinner-cube
      color="primary"
      size="5em"
      />
    </div> -->
    <b-modal id="image-upload" size="lg" hide-footer hide-header centered>
      <div>
        <h3 class="modal-title text-center mt-4">프로필 이미지 업로드📷</h3>
        <p class="text-center">프로필 이미지는 png 파일, 2mb 이하만 가능합니다.</p>
        <div class="wrapper">
          <div class="container">
            <div class="row">
              <div class="img-wrapper col-4">
                <img
                  class="profile-img"
                  :src="!!state.preloadImgUrl ? state.preloadImgUrl : profileImg"
                  alt="프로필이미지"
                >
              </div>
              <div class="col-8">
                <form enctype="multipart/form-data" imgFormData>
                  <label for="profileImag">
                    <input class="input-image form-control" accept="image/*" type="file"
                      ref="profileImg" @change.prevent="uploadProfileImg($event)" id="profileImg">
                  </label>
                </form>
              </div>
            </div>
          </div>
        </div>
      </div>
    </b-modal>

    <b-modal id="slogan-update" size="md" hide-footer hide-header centered>
      <div>
        <h3 class="modal-title text-center mt-4">슬로건 변경 📝</h3>
        <div class="wrapper">
          <div class="container">
            <div class="col col-3">
              <img class="slogan-img" src="@/assets/icon/slogan.png" alt="">
            </div>
            <div class="col col-9">
              <div class="d-flex flex-column ms-3">
                <div class="row">
                  <p class="p-tag">나만의 운동 목표, 슬로건을 작성해 주세요!</p><br>
                </div>
                <div class="row">
                  <form id="sloganUpdate">
                    <label for="slogan">
                      <input class="slogan-input" type="text"
                        id="slogan" v-model="state.sloganData.slogan">
                      <!-- {{ state.sloganData.slogan }} -->
                    </label>
                    <!-- <button @click.prevent="updateSlogan($event)">변경</button> -->
                  </form>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="d-flex justify-content-center">
          <button
            class="btn btn-outline-secondary"
            form="sloganUpdate"
            @click.prevent="updateSlogan()">변경
          </button>
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
            <!--eslint-disable-next-line-->
            <h4>{{slogan}} <svg class="sloganUpdate" v-b-modal.slogan-update xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="24" height="24"><path fill="none" d="M0 0h24v24H0z"/><path d="M7.243 18H3v-4.243L14.435 2.322a1 1 0 0 1 1.414 0l2.829 2.829a1 1 0 0 1 0 1.414L7.243 18zM3 20h18v2H3v-2z" fill="rgba(114,114,114,1)"/></svg></h4>
          </div>
          <div>
            <router-view></router-view>
          </div>
        </div>
      </div>
    </div>
    <!-- </div> -->
    <router-link to="/roomtabs" class="h1-rem2" id="fixedbutton">
            <span class="h1-rem">운동하러</span> GO!</router-link>
  </div>
</template>

<script>
import MySidebar from '@/components/MyPage/MySidebar.vue';
import { useStore } from 'vuex';
import { computed, reactive } from 'vue';
import swal from 'sweetalert2';
import NavBar from '@/components/NavBar.vue';

window.Swal = swal;

export default {
  name: 'MyPage',
  components: {
    MySidebar,
    NavBar,
  },
  setup() {
    const store = useStore();
    store.dispatch('record/fetchSessions');
    // store.dispatch('record/fetchPercentage');
    const slogan = computed(() => store.getters['accounts/slogan']);
    const user = computed(() => store.getters['accounts/userInfo']);
    const profileImg = computed(() => store.getters['accounts/profileImg']);
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
    const updateSlogan = function () {
      // e.preventDefault();
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
        swal.fire({
          title: '파일 사이즈가 너무 큽니다.',
          icon: 'warning',
          text: '파일 사이즈는 2mb 이하만 가능합니다.',
        });
        // this.$swal({
        // title: '파일 사이즈는 2mb 이하만 가능합니다.',
        // input: 'checkbox',
        // inputPlaceholder: '일주일 간 보지 않기',
        //   html:
        //     'ㄴㄴ',
        //   icon: 'info',
        //   showCloseButton: true,
        // }).then((res) => {
        //   if (res.value === 1) {
        //     this.setCookie();
        //   }
        // });
        img = null;
      } else if (img.type.indexOf('image/png') < 0) {
        swal.fire({
          title: '파일 타입이 다릅니다.',
          icon: 'warning',
          text: '.png 파일만 업로드 가능합니다.',
        });
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
        }, 500);
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
      profileImg,
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
  .mypageview {
    color: #2E2E2E;
  }
  .mypageview::-webkit-scrollbar {
    display: none; /* Chrome, Safari, Opera*/
  }
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
    /* backdrop-filter: blur(6px); */
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
  .sloganUpdate {
    cursor: pointer;
  }
  .slogan-img {
    max-width: 100px;
  }
  .p-tag {
    font-weight: 800;
    /* font-weight: 500; */
  }
  .slogan-input {
    border: #DFDFDF 1px solid;
    color:#5c5c5c;
    border-radius: 15px;
    padding-left: 10px;
    font-weight: 500;
    width: 300px;
    height: 30px;
  }
  .slogan-input:visited {
    border: #3f3f3f 1px solid;
    color: black;
    font-weight: 600;
  }
  #fixedbutton {
  position: fixed;
  bottom: 19px;
  right: 20px;
}
#fixedbutton:hover {
  position: fixed;
  bottom: 23px;
  color: #4E8AFF;
}
.h1-rem {
  font-size: 2vh;
  /* padding: 2vh; */
  font-family: 'Recipekorea';
  color: rgb(29, 29, 29);
}
.h1-rem2 {
  font-size: 3vh;
  padding: 2vh;
  font-family: 'Recipekorea';
  color: rgb(29, 29, 29);
  text-decoration: none;
  /* text-shadow: 3px 3px #4E8AFF; */
}

a, a:link {
  cursor: url('@/assets/cursor/cursor-hover.cur'), auto;
}
a, a:link, a:hover, a:focus, a:active{
  cursor: url('@/assets/cursor/cursor-hover.cur'), auto;
}
a:active {
  cursor: url('@/assets/cursor/cursor-hover.cur'), auto;
}
button:hover {
  cursor: url('@/assets/cursor/cursor-hover.cur'), auto;
}
html, body {
  cursor: url('@/assets/cursor/cursor.cur');
}
</style>
