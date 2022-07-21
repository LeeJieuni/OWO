<template>
<div>
  <NavBar/>
  <div class="main-container">
    <router-view/>
  </div>
</div>
</template>

<script>
import NavBar from '@/components/NavBar.vue'
export default {
  components: {
    NavBar
  }
}
</script>

<style>
#app {
  text-align: center;
  /* display: flex; */
  font-family: 'LeferiBaseType-BoldA', 'Noto Sans KR', sans-serif;
  color:#4E8AFF;
  background-color: #F6F7F9;
  font-size : 14px;
  height: 100%;

}

.main-container {
  padding-right: 15px;
  padding-left: 15px;
  margin: 0 auto;
  letter-spacing : -0.05em;
  }

/* font import */

/* 레페리포인트 WHITE OBLIQUE */
@font-face {
    font-family: 'LeferiPoint-WhiteObliqueA';
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2201-2@1.0/LeferiPoint-WhiteObliqueA.woff') format('woff');
    font-weight: normal;
    font-style: normal;
}

/* 레페리포인트 BLACK OBLIQUE */
@font-face {
    font-family: 'LeferiPoint-BlackObliqueA';
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2201-2@1.0/LeferiPoint-BlackObliqueA.woff') format('woff');
    font-weight: normal;
    font-style: normal;
}

/* 레페리베이스 BLACK */
@font-face {
    font-family: 'LeferiPoint-BlackA';
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2201-2@1.0/LeferiPoint-BlackA.woff') format('woff');
    font-weight: normal;
    font-style: normal;
}

/* 레페리베이스 BOLD */
@font-face {
    font-family: 'LeferiBaseType-BoldA';
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2201-2@1.0/LeferiBaseType-BoldA.woff') format('woff');
    font-weight: normal;
    font-style: normal;
}

/* 레페리베이스 REAULAR */
@font-face {
    font-family: 'LeferiBaseType-RegularA';
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2201-2@1.0/LeferiBaseType-RegularA.woff') format('woff');
    font-weight: normal;
    font-style: normal;
}

/* Color Palette */
/*
  Main: 4E8AFF
  Sub1: 274C95
  Sub2: 98BBFF
  Sub3: CEDFFF
  Extra1: DE7474
  Extra2: C5A180
  Extra3: CCB9A8
  Basic1: 2E2E2E
  Basic2: 828282
  Basic3: v
  BackGround1: F6F7F9
  Background2: 383838 투명도 60% === background-color: #38383899;
  ddd
*/
</style>
