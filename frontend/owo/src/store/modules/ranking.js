import axios from 'axios';
import { mapGetters } from 'vuex';

// eslint-disable-next-line
export const ranking = {
  namespaced: true,
  state: {
    rankingList: null,
    myranking: null,
  },
  mutations: {
    SET_RANKING_LIST: (state, payload) => {
      state.rankingList = payload;
    },
    SET_MY_RANKING: (state, payload) => {
      state.myranking = payload;
    } 
  },
  actions: {
    getRankingList({ commit }) {
      axios({
        url: `https://i7c202.p.ssafy.io:8282/api/ranking/${sessionStorage.getItem('userInfo.id')}`,
        method: '',
        headers: {
          'X-AUTH-TOKEN': state.accessToken,
          'REFRESH-TOKEN': state.refreshToken,
        },
      })
      .then((res) => {
        console.log(res);
        commit('SET_RANKIGN_LIST', res)
      })
      .catch((err) => {
        console.log(err);
      })
    },
    getMyRanking({ commit }) {
      axios({
        url: 'https://i7c202.p.ssafy.io:8282/rankingList',
        method: 'get',
        headers: {
          'X-AUTH-TOKEN': state.accessToken,
          'REFRESH-TOKEN': state.refreshToken,
        },
      })
      .then((res) => {
        console.log(res);
        commit('SET_MY_RANKING', res);
      })
      .catch((err) => {
        console.log(err);
      })
    }
  },
  getters: {
    rankingList: (state) => state.rankingList,
    myranking: (state) => state.myranking,
  },
};