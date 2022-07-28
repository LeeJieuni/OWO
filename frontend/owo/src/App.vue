<template>
  <div>
    <NavBar/>
    <div class="main-container">
      <router-view/>
    </div>
  </div>
</template>

<script>
import NavBar from '@/components/NavBar.vue';

export default {
  components: {
    NavBar,
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
