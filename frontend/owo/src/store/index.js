// import Vue from "vue";
import Vuex from 'vuex';
import createPersistedState from 'vuex-persistedstate';

// import { createStore } from "vuex";
import { accounts } from '@/store/modules/accounts';
import { openvidu } from '@/store/modules/openvidu';
import { meetingroom } from '@/store/modules/meetingroom';
import { room } from '@/store/modules/room';
import { record } from '@/store/modules/record';
import { emoji } from '@/store/modules/emoji';
import { mainpage } from '@/store/modules/mainpage';

const store = new Vuex.Store({
  modules: {
    accounts,
    openvidu,
    meetingroom,
    room,
    record,
    emoji,
    mainpage,
  },
  plugins: [
    createPersistedState({
      // 브라우저 종료시 제거하기 위해 localStorage가 아닌 sessionStorage로 변경. (default: localStorage)
      // storage: sessionStorage,
      storage: sessionStorage,
    }),
  ],
});

export default store;
