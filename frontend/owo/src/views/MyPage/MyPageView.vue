<template>
  <div class="mypageview">
    <b-modal id="image-upload" size="lg" hide-footer hide-header>
      <div>
        <h1 class="modal-title text-center mt-4">프로필 이미지</h1>
        <div class="wrapper">
          <div class="md-title text-center">업로드📷</div>
          <div class="container">
            <div class="row row-cols-3">
              <form enctype="multipart/form-data" id="imageUploadForm">
              <label for="profileImag">이미지
                <input class="input-image" accept="image/*" type="file"
                  ref="profileImg" @change.prevent="updateProfileImg($event)" id="profileImg">
              </label>
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

export default {
  name: 'MyPage',
  components: { MySidebar },
  setup() {
    const store = useStore();
    const slogan = computed(() => store.getters['accounts/slogan']);
    const user = computed(() => store.getters['accounts/userInfo']);

    const state = reactive({
      sloganData: {
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
      // const img2 = document.querySelector('.input-image');
      // const modal = document.querySelector('.modal');
      e.preventDefault();
      console.log('이미지 불러오기');
      let img = e.target.files[0];
      // console.log(img);
      // console.log(img2.files[0]);
      console.log('파일사이즈 검사');
      if (img.size > (2 * 1024 * 1024)) {
        alert('파일 사이즈가 20mb를 넘습니다.');
        img = null;
      }
      console.log('처리 후');
      const formData = new FormData();
      // formData.append('name', img.name);
      formData.append('file', img);
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

      store.dispatch('accounts/updateProfileImg', formData);
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
</style>
