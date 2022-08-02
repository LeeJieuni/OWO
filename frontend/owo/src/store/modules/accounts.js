import router from '@/router';
import axios from 'axios';
// eslint-disable-next-line
export const accounts = {
  namespaced: true,
  state: () => ({
    accessToken: null,
    refreshToken: null,
    userInfo: {
      nick: '',
      gender: '',
      age: '',
      height: '',
      weight: '',
      activityNum: '',
      activityHour: '',
      activityLevel: '',
    },
  }),
  mutations: {
    SET_ACCESS_TOKEN: (state, token) => {
      state.accessToken = token;
    },
    SET_REFRESH_TOKEN: (state, token) => {
      state.refreshToken = token;
    },
    SET_USER_INFO: (state, payload) => {
      state.userInfo.nick = payload.nick;
      state.userInfo.gender = payload.gender;
      state.userInfo.age = payload.age;
      state.userInfo.height = payload.height;
      state.userInfo.weight = payload.weight;
      state.userInfo.activityNum = payload.activityNum;
      state.userInfo.activityHour = payload.activityHour;
      state.userInfo.activityLevel = payload.activityLevel;
      console.log(state.userInfo);
    },
  },
  actions: {
    saveAccessToken({ commit }, token) {
      localStorage.setItem('accessToken', token);
      commit('SET_ACCESS_TOKEN', token);
    },
    saveRefreshToken({ commit }, token) {
      localStorage.setItem('refreshToken', token);
      commit('SET_REFRESH_TOKEN', token);
    },
    removeToken({ commit }) {
      commit('SET_ACCESS_TOKEN', null);
      commit('SET_REFRESH_TOKEN', null);
      localStorage.setItem('accessToken', null);
      localStorage.setItem('refreshToken', null);
    },
    setUserInfo({ commit }, payload) {
      commit('SET_USER_INFO', payload);
    },
    login({ dispatch }, credentials) {
      axios.post('https://i7c202.p.ssafy.io:8282/auth/login', credentials)
        .then((res) => {
          const response = res.data.data;
          const access = response.accessToken;
          const refresh = response.refreshToken;
          dispatch('saveAccessToken', access);
          dispatch('saveRefreshToken', refresh);
          dispatch('setUserInfo', response);
          router.push('/');
        })
        .catch((err) => {
          console.log(err);
        });
    },
    logout({ state, dispatch }) {
      // eslint-disable-next-line
      axios({
        url: 'https://i7c202.p.ssafy.io:8282/api/logout',
        method: 'get',
        headers: {
          'X-AUTH-TOKEN': state.accessToken,
          'REFRESH-TOKEN': state.refreshToken,
        },
      })
        .then(() => {
          alert('로그아웃');
          dispatch('removeToken');
          dispatch('setUserInfo', null);
          router.push('/');
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
  getters: {
    isLogin: (state) => !!state.accessToken,
    userInfo: (state) => state.userInfo,
  },
};
