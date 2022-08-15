<template>
  <NavBar/>
    <div v-if="!isLogin">
      <Carousel></Carousel>
      <rankingList/>
      <roomTab></roomTab>
    </div>
    <div v-if="isLogin">
      <Carousel></Carousel>
      <rankingList/>
      <roomTab></roomTab>
    </div>
</template>

<script>
// import { useStore } from 'vuex';
// import { reactive, computed } from 'vue';
import Carousel from '@/components/MainPage/Carousel.vue';
import rankingList from '@/components/MainPage/rankingList.vue';
import roomTab from '@/components/MainPage/roomTab.vue';
import swal from 'sweetalert2';
import { mapGetters, mapActions } from 'vuex';
import NavBar from '@/components/NavBar.vue';

window.Swal = swal;

const accounts = 'accounts';

export default {
  components: {
    Carousel,
    rankingList,
    roomTab,
    NavBar,
  },
  computed: {
    ...mapGetters(accounts, ['userInfo', 'isLogin']),
  },
  methods: {
    setCookie() {
      this.$cookies.set('nosee', 'Y', '7d');
    },
    ...mapActions(accounts, ['getRoomList']),
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
    const mode = ['FREE', 'GAME', 'STREAMING'];
    for (let i = 0; i < mode.length; i += 1) {
      console.log('실행중이다 getroomList');
      this.getRoomList(mode[i]);
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
