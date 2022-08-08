import axios from 'axios';

// eslint-disable-next-line
export const mainpage = {
  state: () => ({
    userImg: '',
    rankingList: null,
    myranking: null,
    achievement: null,
  }),
  mutations: {
    SET_RANKING_LIST: (state, payload) => {
      state.rankingList = payload;
    },
    SET_MY_RANKING: (state, payload) => {
      state.myranking = payload;
    },
    SET_USER_IMG: (state, payload) => {
      state.userImg = payload;
    },
    SET_ACHIEVEMENT: (state, payload) => {
      state.achievement = payload;
    },
  },
  actions: {
    getRankingList({ state, commit }) {
      axios({
        url: `https://i7c202.p.ssafy.io:8282/api/ranking/api/${sessionStorage.getItem('userInfo.id')}`,
        method: '',
        headers: {
          'X-AUTH-TOKEN': state.accessToken,
          'REFRESH-TOKEN': state.refreshToken,
        },
      })
        .then((res) => {
          console.log(res);
          commit('SET_RANKIGN_LIST', res);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    getMyRanking({ state, commit }) {
      axios({
        url: 'https://i7c202.p.ssafy.io:8282/api/rankingList',
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
        });
    },
  },
  getters: {
    rankingList: (state) => state.rankingList,
    myranking: (state) => state.myranking,
  },
};
