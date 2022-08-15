<template>
  <div class="webrtctag col-4 m0p0 my-2 mx-2">
    <!-- <div class="m0p0">
      <button id="img" @click="get_out">
        <img style="width: 25px" src="@/assets/icon/get_out.png" alt="" />
      </button>
    </div> -->
    <div v-if="streamManager" class="position-relative m0p0">
      <!-- eslint-disable-next-line -->
      <ov-video class="ov-video" :stream-manager="streamManager" />
      <!-- eslint-disable-next-line -->
      <div class="myreaction" v-if="myReaction.connectionId == this.myconnectionId">{{myReaction.userEmoji}}</div>
      <p class="myname">&ensp;{{ clientData }}&ensp;</p>
      <!-- eslint-disable-next-line -->
      <div v-if="exerciseName === 1">
        <div class="myexercise">&ensp;{{ mySquat.userSquatCount }} 회&ensp;</div>
        <img v-if="mySquatRanking === 1" src="@/assets/icon/1st.png" class="mySquatRanking" alt="">
        <img v-if="mySquatRanking === 2" src="@/assets/icon/2nd.png" class="mySquatRanking" alt="">
        <img v-if="mySquatRanking === 3" src="@/assets/icon/3rd.png" class="mySquatRanking" alt="">
      </div>
      <!-- eslint-disable-next-line -->
      <div v-if="exerciseName === 2">
        <div class="myexercise">&ensp;{{ myLunge.userLungeCount }} 회&ensp;</div>
        <img v-if="myLungeRanking == 1" src="@/assets/icon/1st.png" class="myLungeRanking" alt="">
        <img v-if="myLungeRanking == 2" src="@/assets/icon/2nd.png" class="myLungeRanking" alt="">
        <img v-if="myLungeRanking == 3" src="@/assets/icon/3rd.png" class="myLungeRanking" alt="">
      </div>
      <!-- eslint-disable-next-line -->
      <div v-if="exerciseName === 3">   
        <div class="myexercise">&ensp;{{ myBurpee.userBurpeeCount }} 회&ensp;</div>
        <img v-if="myBurpeeRanking == 1" src="@/assets/icon/1st.png" class="myBurpeeRanking" alt="">
        <img v-if="myBurpeeRanking == 2" src="@/assets/icon/2nd.png" class="myBurpeeRanking" alt="">
        <img v-if="myBurpeeRanking == 3" src="@/assets/icon/3rd.png" class="myBurpeeRanking" alt="">
      </div>
    </div>
  </div>
</template>
<script>
import { mapState } from 'vuex';
import OvVideo from './OvVideo.vue';

const emoji = 'emoji';
const accounts = 'accounts';
const exercise = 'exercise';

export default {
  components: {
    OvVideo,
  },
  data() {
    return {
      myconnectionId: '',
      user_isActive: false,
      user_get_out: false,
    };
  },
  setup() {},
  created() {},
  moundted() {},
  unmounted() {},
  methods: {
    get_out() {
      alert('추방하시겠습니까?');
    },
    getConnectionData() {
      const { connection } = this.streamManager.stream;
      this.myconnectionId = connection.connectionId;
      return JSON.parse(connection.data);
    },
  },

  computed: {
    ...mapState(emoji, ['allEmojiList']),
    ...mapState(exercise, [
      'exerciseName',
      'allSquatCountList',
      'allSquatCountListSorted',
      'allLungeCountList',
      'allLungeCountListSorted',
      'allburpeeCountList',
      'allburpeeCountListSorted',
    ]),
    ...mapState(accounts, ['userInfo']),
    myReaction() {
      let myEmojiNow = '';
      for (let i = 0; i < this.allEmojiList.length; i += 1) {
        // eslint-disable-next-line
        if (this.allEmojiList[i].connectionId == this.myconnectionId) {
          myEmojiNow = {
            connectionId: this.myconnectionId,
            userEmoji: this.allEmojiList[i].userEmoji,
          };
          break;
        }
      }
      return myEmojiNow;
    },
    mySquat() {
      let mySquatNow = { userSquatCount: 0 };
      for (let i = 0; i < this.allSquatCountList.length; i += 1) {
        // eslint-disable-next-line
        if (this.allSquatCountList[i].connectionId == this.myconnectionId) {
          mySquatNow = {
            connectionId: this.myconnectionId,
            userSquatCount: this.allSquatCountList[i].allUserSquatCount,
          };
          break;
        }
      }
      return mySquatNow;
    },
    myLunge() {
      let myLungeNow = { userLungeCount: 0 };
      for (let i = 0; i < this.allLungeCountList.length; i += 1) {
        // eslint-disable-next-line
        if (this.allLungeCountList[i].connectionId == this.myconnectionId) {
          myLungeNow = {
            connectionId: this.myconnectionId,
            userLungeCount: this.allLungeCountList[i].allUserLungeCount,
          };
          break;
        }
      }
      return myLungeNow;
    },
    myBurpee() {
      let myBurpeeNow = { userBurpeeCount: 0 };
      for (let i = 0; i < this.allburpeeCountList.length; i += 1) {
        // eslint-disable-next-line
        if (this.allburpeeCountList[i].connectionId == this.myconnectionId) {
          myBurpeeNow = {
            connectionId: this.myconnectionId,
            userBurpeeCount: this.allburpeeCountList[i].allUserBurpeeCount,
          };
          break;
        }
      }
      return myBurpeeNow;
    },
    mySquatRanking() {
      // eslint-disable-next-line
      if (parseInt(this.mySquat.userSquatCount) === this.allSquatCountListSorted[0]) {
        return 1;
      // eslint-disable-next-line
      } else if (parseInt(this.mySquat.userSquatCount) === this.allSquatCountListSorted[1]) {
        return 2;
      // eslint-disable-next-line
      } else if (parseInt(this.mySquat.userSquatCount) === this.allSquatCountListSorted[2]) {
        return 3;
      } else {
        return 99;
      }
    },
    myLungeRanking() {
      // eslint-disable-next-line
      if (parseInt(this.myLunge.userLungeCount) === this.allLungeCountListSorted[0]) {
        return 1;
      // eslint-disable-next-line
      } else if (parseInt(this.myLunge.userLungeCount) === this.allLungeCountListSorted[1]) {
        return 2;
      // eslint-disable-next-line
      } else if (parseInt(this.myLunge.userLungeCount) === this.allSquatCountListSorted[2]) {
        return 3;
      } else {
        return 99;
      }
    },
    myBurpeeRanking() {
      // eslint-disable-next-line
      if (parseInt(this.myBurpee.userBurpeeCount) === this.allBurpeeCountListSorted[0]) {
        return 1;
      // eslint-disable-next-line
      } else if (parseInt(this.myBurpee.userBurpeeCount) === this.allBurpeeCountListSorted[1]) {
        return 2;
      // eslint-disable-next-line
      } else if (parseInt(this.myBurpee.userBurpeeCount) === this.allSquatCountListSorted[2]) {
        return 3;
      } else {
        return 99;
      }
    },
    clientData() {
      const { clientData } = this.getConnectionData();
      return clientData;
    },
  },
  props: {
    streamManager: Object,
  },
};
</script>
<style scoped>
#img {
  position: absolute;
  top: 5%;
  left: 88%;
  border: 0;
  outline: 0;
  opacity: 0;
  /* z-index:100; */
}

.m0p0 {
  margin:0;
  padding:0;
  border-radius: 10px;
}

#img:hover {
  opacity: 1;
}

.webrtctag {
  width:520px;
  height:360px;
  border-radius: 20px;
  border: 3px solid #4e8aff;
  background-color:white;
  transition-property: width, height, background-color, border;
  transition-duration: 2s;
}

.myname {
  position:absolute;
  top:-1px;
  left:-1px;
  background-color:#4e8aff;
  font-size:24px;
  border-radius: 15px 0px 10px 0px;
  /* z-index:600; */
}
.myexercise {
  position:absolute;
  top:-1px;
  right:-1px;
  font-size:24px;
  text-align:right;
  background-color:#4e8aff;
  font-size:24px;
  border-radius: 0px 15px 0px 10px;
  /* z-index:600; */
}

.myreaction {
  position:absolute;
  height:100%;
  top:0px;
  right:0px;
  font-size:150px;
  /* z-index:700; */
}

.ov-video {
  width: 100.2%;
  border-radius: 20px;
  position:relative;
  top:-1px;
  left:-1px;
  /* z-index:500; */
}

.mymic {
  position:absolute;
  bottom:20px;
  right:10px;
  font-size:15px;
  width:50px;
}

.mySquatRanking {
  position:absolute;
  top: 35px;
  left: -18px;
  width:150px;
}
.myLungeRanking {
  position:absolute;
  top: 35px;
  left: -20px;
}
.myBurpeeRanking {
  position:absolute;
  top: 35px;
  left: -20px;
}
</style>
