<template>
  <div class="navContainer">
    <!--오운완 내브바-->
    <div class="owo_nav">
      <nav class="navbar sticky-top">
        <div class="container-fluid d-flex justify-content-between">
          <!--logo-->
          <div class="navbar-brand mb-0 h1">
            <router-link to="/">
              <img id="logo2" src="../assets/logo/logo2.png" alt="">
            </router-link>
          </div>
          <div v-if="!isLogin" class="d-flex justify-content-center">
            <router-link to="/login" style="text-align:center;">
              <li class="menu1">Login</li>
            </router-link>
            <router-link to="/register">
              <li class="menu1">Signup</li>
            </router-link>
          </div>
          <div v-else class="d-flex">
            <div class="d-flex align-items-center">
              <div class="navbar-brand mb-0 h1">
                <span>안녕하세요
                  <router-link to="/mypage/main"><span
                    v-once class="nickToMypage">{{ userInfo.nick }}</span>
                  </router-link> 님,
                반갑습니다!</span>
              </div>
            </div>
            <div class="dropdown">
              <div class="img-wrapper">
                <img class="profile-img" :src="profileImg" alt="" style="cursor:pointer;">
              </div>
              <div class="dropdown-content">
                <li><router-link to="/tutorial/burpee">튜토리얼</router-link></li>
                <li>
                  <router-link to="/roomtabs">운동하기</router-link>
                </li>
                <li>
                  <router-link
                  to="/mypage/main" @click="fetchMypage"
                    @keyup.enter="fetchMypage">마이페이지
                  </router-link>
                </li>
                <li>
                  <button class="logout-btn" @click="logout()">로그아웃</button>
                </li>
              </div>
            </div>
          </div>
        </div>
      </nav>
    </div>
  </div>
</template>
<script>
import { createNamespacedHelpers } from 'vuex';
// import axios from 'axios';

const { mapActions, mapGetters } = createNamespacedHelpers('accounts');

export default {
  name: 'NavBar',
  data() {
    return {
      user: this.userInfo,
    };
  },
  methods: {
    ...mapActions({
      logout: 'logout',
    }),
    // ...mapActions('record', ['fetchPercentage', 'fetchAchievementRate']),
    ...mapActions('accounts', ['fetchMypage', 'fetchUserInfo']),
    // mypageFetch() {
    //   this.fetchMypage();
    // },
  },
  computed: {
    ...mapGetters(['isLogin', 'userInfo', 'profileImg']),
  },
  watch: {
  },
};
</script>
<style>

</style>
<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Black+Han+Sans&family=Do+Hyeon&family=Jua&family=Nanum+Brush+Script&family=Noto+Sans+KR:wght@100;300;400;500;700;900&family=Stylish&display=swap');

/* .navbar {
  color: black;
} */
a {
  text-decoration: none;
}

li {
  list-style: none;
}
.navbar {
  box-shadow: #DFDFDF 5px 0 10px;
  height: 69px;
}

.navContainer {
  margin-bottom: 0px;
  padding-right: 0px;
  padding-left: 0px;
  font-family: 'Noto Sans KR', sans-serif;
  background-color: rgb(255, 255, 255);
  /* navbar color */
}

.owonav {
  margin-bottom: 50px;
  padding-right: 30px;
  padding-left: 10px;
  font-family: 'Noto Sans KR', sans-serif;
  position: relative;
}

.global-nav {
  height: 44px;
  position: relative;
}

.local-nav {
  height: 52px;
  border-bottom: 1px solid #ddd;
}

.container-fluid {
  display: flex;
  align-items: center;
  max-width: 100%;
  margin: 0 auto;
  height: 100%;
  padding: 0 22px;
  padding-left: max(22px, env(safe-area-inset-left));
  padding-right: max(22px, env(safe-area-inset-right));
}

.navbar-brand {
  font-size: 1rem;
}

.global-nav-links,
.local-nav-links {
  display: flex;
  align-items: center;
  max-width: 980px;
  margin: 0 auto;
  height: 100%;
  padding: 0 22px;
  padding-left: max(22px, env(safe-area-inset-left));
  padding-right: max(22px, env(safe-area-inset-right));
}

.sm-menu {
  display: flex;
  align-items: center;
}

.menu1 {
  margin-left: 1em;
  margin-right: 1em;
  font-size: 1rem;
  text-align: center;
}

.navli {
  margin-left: 2em;
}

.global-nav-links {
  justify-content: space-between;
}

.local-nav-links .product-name {
  margin-right: auto;
  font-size: 1rem;
}

.local-nav-links a {
  /* font-size: 0.5rem; */
}

.local-nav-links a:not(.product-name) {
  margin-left: 2em;
}

#logo2 {
  width: 90px;
}

.dropdown-menu {
  min-width: 5rem;
  font-size: 0.8rem;
  line-height: 1.4rem;
  padding: 10px;
}

/* .nickToMypage {
  font-size: 20px;
  color: rgb(73, 73, 73);
  text-decoration: underline;
} */
.dropdown {
  position: relative;
  display: inline-block;
}

.dropdown-content {
  display: none;
  position: absolute;
  background-color: #F9F9F9;
  min-width: 100px;
  padding: 8px;
  box-shadow: 0px 8px 16px 0px rgba(0, 0, 0, 0.2);
  border-radius: 10%;
  right: -25px;
}

.dropdown:hover .dropdown-content {
  display: block;
}

.dropdown-content li {
  padding: 5px;
}
.logout-btn {
  border: none;
  background-color: #F9F9F9
}
.img-wrapper {
  width: 50px;
  height: 50px;
  overflow:hidden;
  margin:0 auto;
}
.profile-img {
  width:100%;
  height:100%;
  object-fit:cover;
  border-radius: 50%;
  border: #DFDFDF 0.2px solid;
}
</style>
