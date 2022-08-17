<template>
  <div class="main">
    <NavBar class="sticky-top"/>
    <div class="body">
      <rankingList2 class="rank"/>
      <div class="rankTitle">
        <h1 class="title" style="padding-left:10vh;">운동방 입장하기 🚀</h1>
      </div>
      <roomTab class="room"></roomTab>
    </div>
  </div>
</template>

<script>
import NavBar from '@/components/NavBar.vue';
import roomTab from '@/components/MainPage/roomTab.vue';
import rankingList2 from '@/components/MainPage/rankingList2.vue';
import { mapActions } from 'vuex';

const accounts = 'accounts';

export default {
  components: {
    roomTab,
    NavBar,
    rankingList2,
  },
  methods: {
    ...mapActions(accounts, ['getRoomList']),
  },
  created() {
    const mode = ['FREE', 'GAME', 'STREAMING'];
    for (let i = 0; i < mode.length; i += 1) {
      console.log('실행중이다 roomtabvue.view');
      this.getRoomList(mode[i]);
    }
    // this.getImage();
  },
};
</script>

<style scoped>
.body {
  padding: 0 50px;
}
.title {
  padding: 8vh 5vh 5vh;
  /* color: white; */
  font-family: 'LeferiPoint-BlackObliqueA';
  font-size: 4vh;
  text-align: left;
}
/* .rankTitle {
  padding-top: 50px;
  padding-bottom: 20px;
  padding-right: 100px;
  padding-left: 100px;
}
.rankTitleText {
  font-size: 40px;
  text-align: left;
  font-family: 'Recipekorea';
} */
</style>
