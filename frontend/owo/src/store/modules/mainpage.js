import axios from 'axios';
// eslint-disable-next-line
export const mainpage = {
  namespaced: true,
  state: () => ({
    userImg: '',
    rankingList: null,
    myranking: '',
    achievement: null,
    lastingDay: null,
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
    SET_LASTING_DAY: (state, payload) => {
      state.lastingDay = payload;
    },
  },
  actions: {
    getRankingList({ commit, dispatch }) {
      axios({
        url: 'https://i7c202.p.ssafy.io:8282/api/rankingList',
        method: 'get',
      })
        .then((res) => {
          console.log('랭킹리스트');
          console.log(res);
          commit('SET_RANKING_LIST', res.data.data);
          dispatch('getLastingDay', res.data.data[0].member_id);
          dispatch('getAchievement', res.data.data[0].member_id);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    getMyRanking({ commit }) {
      let userInfo = sessionStorage.getItem('vuex');
      userInfo = JSON.parse(userInfo);
      // eslint-disable-next-line
      const userId = userInfo['accounts']['userInfo']['id'];
      // eslint-disable-next-line
      const accessToken = userInfo['accounts']['accessToken'];
      axios({
        url: `https://i7c202.p.ssafy.io:8282/api/user/ranking/${userId}`,
        method: 'get',
        headers: {
          'X-AUTH-TOKEN': accessToken,
        },
      })
        .then((res) => {
          commit('SET_MY_RANKING', res.data.data.ranking);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    getLastingDay({ commit }, KingID) { // KingID
      let userInfo = sessionStorage.getItem('vuex');
      userInfo = JSON.parse(userInfo);
      // eslint-disable-next-line
      const accessToken = userInfo['accounts']['accessToken'];
      axios({
        url: `https://i7c202.p.ssafy.io:8282/api/user/record/lastingDay/${KingID}`,
        // url: 'https://i7c202.p.ssafy.io:8282/api/user/record/lastingDay/2',
        method: 'get',
        headers: {
          'X-AUTH-TOKEN': accessToken,
        },
      })
        .then((res) => {
          console.log('getlastingDay');
          console.log(res);
          commit('SET_LASTING_DAY', res.data);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    getAchievement({ commit }, KingID) {
      let userInfo = sessionStorage.getItem('vuex');
      userInfo = JSON.parse(userInfo);
      // eslint-disable-next-line
      const accessToken = userInfo['accounts']['accessToken'];
      axios({
        url: `https://i7c202.p.ssafy.io:8282/api/user/record/goal/do/${KingID}`,
        method: 'get',
        headers: {
          'X-AUTH-TOKEN': accessToken,
        },
      })
        .then((res) => {
          if (res.data.data === {}) {
            commit('SET_ACHIEVEMENT', 0);
          }
          commit('SET_ACHIEVEMENT', res.data.data);
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
  getters: {
    rankingList: (state) => state.rankingList,
    myranking: (state) => state.myranking,
    achievement: (state) => state.achievement,
  },
};
