<template>
  <div>
    <NavBar/>
    <div class="main-container">
      <router-view/>
    </div>
    <!-- <TeleportExample/> -->
  </div>
</template>

<script>
// import TeleportExample from '@/components/MyPage/teleport/TeleportExample.vue';
import NavBar from './components/NavBar.vue';

export default {
  components: {
    NavBar,
    // TeleportExample,
  },
};
</script>

<style>
#app {
  text-align: center;
   /* display: flex; */
  font-family: 'NanumSquareRound', 'Noto Sans KR', sans-serif;
  color:#2E2E2E;
  background-color: #ffffff;
  font-size : 14px;
  height: 100%;
  letter-spacing : -0.05em;
  padding-bottom: 100px;
  line-height: 1.6;
  padding: 0;
  margin: 0;
}
* {
  margin: 0;
  padding: 0;
}
/* font import */

/* 나눔스퀘어 라운드
font-family: 'NanumSquareRoundLight';
font-family: 'NanumSquareRound';
font-family: 'NanumSquareRoundBold';
font-family: 'NanumSquareRoundExtraBold';
*/

@font-face {
    font-family: 'NanumSquareRound';
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_two@1.0/NanumSquareRound.woff') format('woff');
    font-weight: normal;
    font-style: normal;
}

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

/* 동화또박 */
@font-face {
  font-family: 'Fairytale_ddobak';
  src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/naverfont_03@1.0/Fairytale_ddobak.woff') format('woff');
  font-weight: normal;
  font-style: normal;
}

/* 숫자용 google RIGHTEOUS */
@import url('https://fonts.googleapis.com/css2?family=Righteous&display=swap');

/* 캘린더 두꺼운글자용 BLACK Han Sans */
@import url('https://fonts.googleapis.com/css2?family=Black+Han+Sans&display=swap');
/* font-family: 'Black Han Sans', sans-serif; */

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
  Basic3: DFDFDF
  BackGround1: F6F7F9
  Background2: 383838 투명도 60% === background-color: #38383899;
  ddd
*/
</style>
