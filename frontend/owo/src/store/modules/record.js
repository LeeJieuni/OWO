/* eslint-disable */
// import router from '@/router';
import axios from 'axios';

// eslint-disable-next-line
export const record = {
  namespaced: true,
  state: () => ({
    accessToken: null,
    refreshToken: null,
    userId: '',
    percentage: '',
  }),
  getters: {
    percentage: (state) => !!state.percentage,
  },
  mutations: {
    SET_PERCENTAGE: (state, payload) => {
      state.percentage = payload;
    },
    SET_SESSIONS: (state, payload) => {
      state.accessToken = payload.accessToken;
      state.refreshToken = payload.refreshToken;
      state.userId = payload.userId;
    },
  },
  actions: {
    fetchSessions({ commit }) {
      let userInfo = sessionStorage.getItem('vuex');
      userInfo = JSON.parse(userInfo);
      const accessToken = userInfo['accounts']['accessToken'];
      const refreshToken = userInfo['accounts']['refreshToken'];
      const userId = userInfo['accounts']['userInfo.id'];
      const payload = {
        accessToken: accessToken,
        refreshToken: refreshToken,
        userId: userId,
      };
      console.log(payload);
      commit('SET_SESSIONS', payload);
    },
    fetchPercentage({ state, commit }, date) {
      console.log('axios 전');
      axios({
        url: `https://i7c202.p.ssafy.io:8282/api/record/percentage/${state.userId}`,
        method: 'get',
        headers: {
          'X-AUTH-TOKEN': state.accessToken,
          'REFRESH-TOKEN': state.refreshToken,
        },
      })
        .then((res) => {
          commit('SET_PERCENTAGE', res.data.data);
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
  setDayPictures({ commit }, payload) {
    commit('SET_DAY_PICTURES', payload);
  },
  loadDayRecord({ state, dispatch }, date) {
    axios({
      url: `https://i7c202.p.ssafy.io:8282/api/record/day/${sessionStorage.getItem('userInfo.id')}/${date}`,
      method: 'get',
      headers: {
        'X-AUTH-TOKEN': state.accessToken,
        'REFRESH-TOKEN': state.refreshToken,
      },
      // data: date,
    })
      .then((res) => {
        dispatch('setDayPictures', res.data.data);
      })
      .catch((err) => {
        console.log(err);
      });
  },
  loadDayPicture({ state, dispatch }, payload) {
    axios({
      url: 'https://i7c202.p.ssafy.io/api/record/img/:memberId/:date',
      method: 'put',
      headers: {
        'X-AUTH-TOKEN': state.accessToken,
        'REFRESH-TOKEN': state.refreshToken,
      },
      data: payload,
    })
      .then((res) => {
        dispatch('setDayPicture', res.data.data);
      })
      .catch((err) => {
        console.log(err.toJSON());
      });
    // eslint-disable-next-line
    sendRecord({}, { credentials, credentialsUser }) {
      console.log(credentialsUser);
      console.log('------');
      axios.post(`https://i7c202.p.ssafy.io:8282/api/record/finish/${credentialsUser.memberId}/${credentialsUser.meetingRoomId}`, credentials)
        .then((res) => {
          console.log('성공', res.data);
        })
        .catch((err) => {
          console.log('실패', err);
        });
    },
  },
};
