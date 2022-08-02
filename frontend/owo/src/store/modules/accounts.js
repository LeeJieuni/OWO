import axios from 'axios';
// eslint-disable-next-line
export default {
  namespaced: true,
  state: () => ({
    isLogin: false,
    isLoginErr: false,
    accessToken: localStorage.getItem('accessToken') || '',
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
    SET_TOKEN: (state, token) => {
      state.accessToken = token;
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
    },
  },
  actions: {
    saveToken({ commit }, token) {
      commit('SET_TOKEN', token);
      localStorage.setItem('accessToken', token);
    },
    removeToken({ commit }) {
      commit('SET_TOKEN', '');
      localStorage.setItem('accessToken', '');
    },
    setUserInfo({ commit }, payload) {
      commit('SET_USER_INFO', payload);
    },
    login({ dispatch }, credentials) {
      axios({
        url: 'https://i7c202.p.ssafy.io:8282/auth/login',
        method: 'post',
        data: credentials,
      })
        .then((res) => {
          const token = res.data.accessToken;
          dispatch('saveToken', token);
          dispatch('setUserInfo', res.data);
          console.log(res);
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
  getters: {
  },
};
