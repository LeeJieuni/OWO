import { createStore } from 'vuex';
import axios from 'axios';
import router from '@/router';

export default createStore({
  state: {
    accessToken: '',
    isLogin: false,
    isLoginError: false,
    loginErrMsg: '',
    loginInfo: {
      status: '',
      message: '',
      accessToken: '',
      refreshToken: '',
    },
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
  },
  getters: {

  },
  mutations: {
    SET_TOKEN(state, token) {
      state.loginInfo.accessToken = token;
    },
    SET_USER_INFO(state, payload) {
      state.userInfo.activityHour = payload.activityHour;
      state.userInfo.activityLevel = payload.activityLevel;
      state.userInfo.activityNum = payload.activityNum;
      state.userInfo.age = payload.age;
      state.userInfo.gender = payload.gender;
      state.userInfo.height = payload.height;
      state.userInfo.nick = payload.nick;
      state.userInfo.weight = payload.weight;
    },
    loginSuccess(state) {
      state.isLogin = true;
      state.isLoginError = false;
    },
    loginFail(state, payload) {
      state.loginErrMsg = payload;
      state.isLogin = false;
      state.isLoginError = true;
    },
    logout(state) {
      state.isLogin = false;
      state.isLoginError = false;
      state.userInfo = null;
    },
  },
  actions: {
    login({ commit }, credentials) { // eslint-disable-line no-unused-vars
      axios.post('https://i7c202.p.ssafy.io:8282/auth/login', credentials)
        .then((res) => {
          const data = res.data.data; // eslint-disable-line 
          const requestToken = data.accessToken;
          this.dispatch('saveToken', requestToken);
          commit('loginSuccess');
          commit('SET_USER_INFO', data);
          console.log(data);
          router.push('/');
        })
        .catch((error) => {
          commit('loginFail', error);
        });
    },
    saveToken({ commit }, token) {
      commit('SET_TOKEN', token);
      localStorage.setItem('accessToken', token);
    },
    removeToken({ commit }) {
      commit('SET_TOKEN', '');
      localStorage.setItem('token', '');
    },
  },
});
