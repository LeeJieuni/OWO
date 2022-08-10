import axios from 'axios';
// eslint-disable-next-line
export const mainpage = {
  namespaced: true,
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
        url: 'https://i7c202.p.ssafy.io:8282/api/rankingList',
        method: 'get',
        headers: {
          'X-AUTH-TOKEN': state.accessToken,
          'REFRESH-TOKEN': state.refreshToken,
        },
      })
        .then((res) => {
          console.log(res.data);
          commit('SET_RANKING_LIST', res.data.data);
          console.log(state.rankingList[0]);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    getMyRanking({ state, commit }) {
      let userInfo = sessionStorage.getItem('vuex');
      userInfo = JSON.parse(userInfo);
      // eslint-disable-next-line
      const userId = userInfo['accounts']['userInfo']['id'];
      axios({
        url: `https://i7c202.p.ssafy.io:8282/api/ranking/${userId}`,
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
