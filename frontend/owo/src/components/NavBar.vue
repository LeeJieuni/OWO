<template>
  <div class="navContainer">

    <!--오운완 내브바-->
    <div class="owo_nav">
      <nav class="navbar sticky-top">
        <div class="container-fluid d-flex justify-content-between">
          <!--logo-->
          <div class="navbar-brand mb-0 h1">
            <router-link to="/" >
              <img id="logo2" src="../assets/logo/logo2.png" alt="">
            </router-link>
          </div>
          <div class="d-flex align-items-center">
            <div class="navbar-brand mb-0 h1">
              <router-link to="/mypage/main"><span
              class="welcome">안녕하세요 오운완회원님, 반갑습니다!</span></router-link>
            </div>
            <div v-if="!isLoggedIn()" class="d-flex">
            <!--dropdows1-rooms-->
              <li class="menu1 mypages nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#"
                role="button" data-bs-toggle="dropdown" aria-expanded="false">
                  <span> Rooms</span>
                </a>
                <ul class="dropdown-menu">
                  <li><router-link class="dropdown-item"
                  to="/competition">Competition</router-link></li>
                  <li><router-link class="dropdown-item"
                  to="/friendchat">FriendChat</router-link></li>
                  <li><router-link class="dropdown-item"
                  to="/friendnochat">FriendNoChat</router-link></li>
                  <li><router-link class="dropdown-item"
                  to="/youtubechat">YoutubeChat</router-link></li>
                  <li><router-link class="dropdown-item"
                  to="/youtubenochat">YoutubeNoChat</router-link></li>
                </ul>
              </li>
              <router-link to="/login"><li class="menu1">login</li></router-link>
            </div>
            <div v-if="isLoggedIn()" class="d-flex">
              <div class="dropdown">
                <img class="profile-img " src="https://picsum.photos/150" alt="">
                <div class="dropdown-content">
                  <router-link to="/mypage/main"><li>마이페이지</li></router-link>
                  <router-link to=""><li>환경설정</li></router-link>
                  <router-link to=""><li>로그아웃</li></router-link>
                </div>
              </div>
            </div>
          </div>
        </div>
      </nav>
    </div>
  </div>
</template>
<script>
export default {
  name: 'NavBar',
  components: {},
  data() {
    return {
      sampleData: '',
    };
  },
  setup() {},
  created() {},
  mounted() {},
  unmounted() {},
  methods: {
    isLoggedIn() {
      return true;
    },
  },
};
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Black+Han+Sans&family=Do+Hyeon&family=Jua&family=Nanum+Brush+Script&family=Noto+Sans+KR:wght@100;300;400;500;700;900&family=Stylish&display=swap');

/* .navbar {
  color: black;
} */
a {
  text-decoration: none;
}

li{
   list-style:none;
   }

.navContainer {
  margin-bottom: 0px;
  padding-right: 0px;
  padding-left: 0px;
  font-family: 'Noto Sans KR', sans-serif;
  background-color: rgb(255, 255, 255);/* navbar color */
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
  padding-left: max(22px,env(safe-area-inset-left));
  padding-right: max(22px,env(safe-area-inset-right));
 }
 .navbar-brand {
  font-size: 1.2rem;
  font-weight: 900;
 }

.global-nav-links,
.local-nav-links {
  display: flex;
  align-items: center;
  max-width: 980px;
  margin: 0 auto;
  height: 100%;
  padding: 0 22px;
  padding-left: max(22px,env(safe-area-inset-left));
  padding-right: max(22px,env(safe-area-inset-right));
}

.sm-menu {
  display:flex;
  align-items: center;
}
.menu1 {
  margin-left: 2em;
  font-size: 0.8rem;
}

.navli {
  margin-left: 2em;
}
.global-nav-links {
  justify-content: space-between;
}

.local-nav-links .product-name {
  margin-right: auto;
  font-size: 1.2rem;
  font-weight: 900;
}

.local-nav-links a {
  font-size: 0.8rem;
}

.local-nav-links a:not(.product-name) {
  margin-left: 2em;
}

#logo2 {
  width: 90px;
}

.dropdown-menu {
  min-width:5rem;
  font-size: 14px;
  line-height: 1.4rem;
  padding: 10px;
}
.welcome {
  font-size: 15px;
  font-weight: lighter;
  color: black;
}
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
box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
border-radius: 10%;
right: -25px;
}
.dropdown:hover .dropdown-content { display: block; }
.profile-img {
   border-radius: 50%;
   height: 50px;
}
.dropdown-content li {
  padding: 5px;
}
</style>
