import router from '@/router';
import axios from 'axios';
// eslint-disable-next-line
export const accounts = {
  namespaced: true,
  state: () => ({
    LoginErr: '',
    isLoginErr: false,
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
      id: '',
      email: '',
    },
    physicalInfo: {
      bmi: null,
      bmr: null,
      caloriePerDay: null,
    },
    slogan: '',
    image: '',
  }),
  mutations: {
    SET_LOGIN_ERR: (state, message) => {
      state.LoginErr = message;
      state.isLoginErr = true;
    },
    SET_ACCESS_TOKEN: (state, token) => {
      state.accessToken = token;
    },
    SET_REFRESH_TOKEN: (state, token) => {
      state.refreshToken = token;
    },
    SET_USER_INFO: (state, payload) => {
      state.userInfo.id = payload.id;
      state.userInfo.email = payload.email;
      state.userInfo.nick = payload.nick;
      state.userInfo.gender = payload.gender;
      state.userInfo.age = payload.age;
      state.userInfo.height = payload.height;
      state.userInfo.weight = payload.weight;
      state.userInfo.activityNum = payload.activityNum;
      state.userInfo.activityHour = payload.activityHour;
      state.userInfo.activityLevel = payload.activityLevel;
      console.log(state.userInfo);
      if (state.userInfo.nick === '' || !state.userInfo.nick) {
        const { email } = state.userInfo;
        const atSignIndex = email.indexOf('@');
        const emailNick = email.substr(0, atSignIndex);
        state.userInfo.nick = emailNick;
      }
    },
    SET_PHYSICAL_INFO: (state, payload) => {
      state.physicalInfo.bmi = payload.bmi;
      state.physicalInfo.bmr = payload.bmr;
      state.physicalInfo.caloriePerDay = payload.caloriePerDay;
    },
    SET_SLOGAN: (state, payload) => {
      state.slogan = payload.slogan;
    },
    SET_PROFILE_IMG: (state, payload) => {
      state.image = payload.image;
    },
  },
  actions: {
    saveAccessToken({ commit }, token) {
      sessionStorage.setItem('accessToken', token);
      commit('SET_ACCESS_TOKEN', token);
    },
    saveRefreshToken({ commit }, token) {
      sessionStorage.setItem('refreshToken', token);
      commit('SET_REFRESH_TOKEN', token);
    },
    removeToken({ commit }) {
      commit('SET_ACCESS_TOKEN', null);
      commit('SET_REFRESH_TOKEN', null);
      sessionStorage.removeItem('accessToken'); // accessToken 제거
      sessionStorage.removeItem('refreshToken'); // refreshToken 제거
      sessionStorage.removeItem('vuex'); // 사용자 정보 제거
    },
    setUserInfo({ commit }, payload) {
      commit('SET_USER_INFO', payload);
    },
    login({ dispatch, commit }, credentials) {
      axios.post('https://i7c202.p.ssafy.io:8282/auth/login', credentials)
        .then((res) => {
          const response = res.data.data;
          const access = response.accessToken;
          const refresh = response.refreshToken;
          dispatch('saveAccessToken', access);
          dispatch('saveRefreshToken', refresh);
          dispatch('setUserInfo', response);
          router.push('/'); // main 페이지로 이동
          dispatch('fetchPhysicalInfo');
          dispatch('fetchSlogan');
        })
        .catch((err) => {
          if (err.response.status === 400) {
            if (err.response.data.message === '회원가입 이메일 인증이 필요합니다.') {
              commit('SET_LOGIN_ERR', err.response.data.message);
              sessionStorage.removeItem('vuex');
              // router.push('/emailVerify');
            } else if (err.response.data.message === '이메일 혹은 비밀번호가 맞지 않습니다.') {
              // alert(err.response.data.message);
              commit('SET_LOGIN_ERR', err.response.data.message);
              sessionStorage.removeItem('vuex');
            } else {
              commit('SET_LOGIN_ERR', err.response.data.message); // 서버 error
            }
          }
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
    register({ dispatch, state }, payload) {
      axios.post('https://i7c202.p.ssafy.io:8282/auth/join', payload)
        .then((res) => {
          const response = res.data.data;
          console.log(response);
          dispatch('setUserInfo', response);
          router.push('/');
          console.log(state.userInfo);
        })
        .catch((err) => {
          console.log(err.response.data);
          if (err.response.data === 'OVERLAP') {
            alert('회원가입한 이력이 있습니다.');
          }
          console.log(payload);
        });
    },
    fetchPhysicalInfo({ state, commit }) {
      axios({
        url: `https://i7c202.p.ssafy.io:8282/api/user/bmi/${state.userInfo.id}`,
        method: 'get',
        headers: {
          'X-AUTH-TOKEN': state.accessToken,
          'REFRESH-TOKEN': state.refreshToken,
        },
      })
        .then((res) => {
          commit('SET_PHYSICAL_INFO', res.data.data);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    setSlogan({ commit }, payload) {
      commit('SET_SLOGAN', payload);
    },
    fetchSlogan({ state, commit }) {
      axios({
        url: `https://i7c202.p.ssafy.io:8282/api/user/slogan/${state.userInfo.id}`,
        method: 'get',
        headers: {
          'X-AUTH-TOKEN': state.accessToken,
          'REFRESH-TOKEN': state.refreshToken,
        },
      })
        .then((res) => {
          // console.log(res);
          commit('SET_SLOGAN', res.data.data);
          // dispatch('setSlogan', res.data.data);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    updateSlogan({ state, dispatch }, payload) {
      console.log('axios 하기 전');
      axios({
        url: 'https://i7c202.p.ssafy.io:8282/api/user',
        method: 'put',
        headers: {
          'X-AUTH-TOKEN': state.accessToken,
          'REFRESH-TOKEN': state.refreshToken,
        },
        data: payload,
      })
        .then((res) => {
          console.log('res');
          console.log(res.data.data);
          dispatch('setSlogan', res.data.data);
          // router.push({ name: 'MyPageMainView' });
        })
        .catch((err) => {
          console.log(err.toJSON());
        });
    },
    updateUserInfo({ state, dispatch }, payload) {
      axios({
        url: 'https://i7c202.p.ssafy.io:8282/api/user',
        method: 'put',
        headers: {
          'X-AUTH-TOKEN': state.accessToken,
          'REFRESH-TOKEN': state.refreshToken,
        },
        data: payload,
      })
        .then((res) => {
          // console.log(res.data.data);
          dispatch('setUserInfo', res.data.data);
          // router.push({ name: 'MyPageMainView' });
        })
        .catch((err) => {
          console.log(err.toJSON());
        });
    },
    setProfileImg({ commit }, payload) {
      commit('SET_PROFILE_IMG', payload);
    },
    fetchProfileImg({ state, commit }) {
      axios({
        url: `https://i7c202.p.ssafy.io:8282/api/user/bmi/${state.userInfo.id}`,
        method: 'get',
        headers: {
          'X-AUTH-TOKEN': state.accessToken,
          'REFRESH-TOKEN': state.refreshToken,
        },
      })
        .then((res) => {
          commit('SET_PHYSICAL_INFO', res.data.data);
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
  getters: {
    isLogin: (state) => !!state.accessToken,
    userInfo: (state) => state.userInfo,
    physicalInfo: (state) => state.physicalInfo,
    slogan: (state) => state.slogan,
  },
};
