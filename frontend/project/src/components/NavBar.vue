<template>
  <div class="navContainer">
    <div class="container">
      <nav class="global-nav">
        <div class="global-nav-links">
          <a class="global-nav-item" href="#">Home</a>
          <a class="global-nav-item" href="#">Rooms</a>
          <a class="global-nav-item" href="#">MyPage</a>
          <a class="global-nav-item" href="#">Logout</a> <!--혹은 로그인 - 들어가야 회원가입까지 유도 가능-->
        </div>
      </nav>
      <nav class="local-nav">
        <div class="local-nav-links">
          <a href="#" class="product-name"><img src="../assets/logo2.png" style="width:80px;" alt=""></a>
          <a href="#">내 정보 보기</a>
          <a href="#">내 정보 수정하기</a>
          <a href="#">운동 일지</a>
        </div>
      </nav>
    </div>
    <!--오운완 내브바-->
    <div class="owo_nav">
      <nav class="navbar sticky-top">
        <div class="container-fluid">
          <!--logo-->
          <span class="navbar-brand mb-0 h1"><img id="logo2" src="../assets/logo2.png" alt=""></span>
          <!--dropdown1-practice-->
          <div class="sm-menu">
            <li class="menu1 nav-item dropdown nav-li">
              <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                <span>Practices</span>
              </a>
              <ul class="dropdown-menu">
                <li><router-link to="/">Home</router-link></li>
                <li><router-link to="/about">About</router-link></li>
                <li><router-link to="/databinding/string">String</router-link></li>
                <li><router-link to="/databinding/html">Html</router-link></li>
                <li><router-link to="/databinding/input">Input</router-link></li>
                <li><router-link to="/databinding/select">Select</router-link></li>
                <li><router-link to="/databinding/checkbox">Checkbox</router-link></li>
                <li><router-link to="/databinding/radio">Radio</router-link></li>
                <li><router-link to="/databinding/attribute">Attribute</router-link></li>
                <li><router-link to="/databinding/list">List</router-link></li>
                <li><router-link to="/databinding/class">Class</router-link></li>
                <li><router-link to="/databinding/style">Style</router-link></li>
                <li><router-link to="/event/click">Click</router-link></li>
                <li><router-link to="/event/change">Change</router-link></li>
                <li><router-link to="/event/key">Key</router-link></li>
              </ul>
            </li>

            <!--dropdows2-mypages-->
            <li class="menu1 mypages nav-item dropdown">
              <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                <span> MyPage</span>
              </a>
              <ul class="dropdown-menu">
                <li><router-link class="dropdown-item" to="/mypage">My Page</router-link></li>
                <li><a class="dropdown-item" href="#">Edit My Info</a></li>
                <li><a class="dropdown-item" href="#">Exercise Calendar</a></li>
                <li><hr class="dropdown-divider"></li>
                <li><a class="dropdown-item" href="#">LogOut</a></li>
              </ul>
            </li>

            <!--Logout-->
            <li class="menu1">LogOut</li>

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
      sampleData: ''
    }
  },
  setup() {},
  created() {},
  mounted() {},
  unmounted() {},
  methods: {}
}
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
  margin-bottom: 50px;
  padding-right: 30px;
  padding-left: 30px;
  font-family: 'Noto Sans KR', sans-serif;
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
  max-width: 980px;
  margin: 0 auto;
  height: 100%;
  padding: 0 22px;
  padding-left: max(22px,env(safe-area-inset-left));
  padding-right: max(22px,env(safe-area-inset-right));
 }
 .navbar-brand {
  margin-right: auto;
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
</style>
