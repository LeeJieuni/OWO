import router from '@/router';
import axios from 'axios';
import swal from 'sweetalert2';

window.Swal = swal;

// eslint-disable-next-line
export const accounts = {
  namespaced: true,
  state: () => ({
    LoginErr: '',
    isLoginErr: false,
    accessToken: null,
    refreshToken: null,
    signupInfo: {
      signupErr: false,
      message: '',
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
      id: '',
      email: '',
    },
    physicalInfo: {
      bmi: '',
      bmr: '',
      caloriePerDay: '',
    },
    slogandata: '',
    image: '',
    record: {
      point: '', // 경쟁
      exp: '', // 자유, 영상 경험치
    },
    compete: {
      workout1: '', // 운동1 최고기록
      workout2: '', // 운동2 최고기록
      workout3: '', // 운동3 최고기록
    },
    goals: [],
    profileImg: '',
  }),
  mutations: {
    SET_SIGNUP_MSG: (state, message) => {
      state.signupInfo.message = message;
      state.signupInfo.singupErr = true;
    },
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
    SET_USER_INFO: (state, payload) => { // 로그인 시 반환되는 유저정보 state에 할당
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
      state.image = payload;
    },
    SET_POINT: (state, payload) => {
      state.record.point = payload;
    },
    SET_COMPETE: (state, payload) => {
      state.compete.workout1 = payload.workout1;
      state.compete.workout2 = payload.workout2;
      state.compete.workout3 = payload.workout3;
    },
    SET_GOALS: (state, payload) => {
      state.goals = payload;
    },
  },
  actions: {
    saveAccessToken({ commit }, token) {
      sessionStorage.setItem('accessToken', token);
      commit('SET_ACCESS_TOKEN', token);
    },
    saveRefreshToken({ commit }, token) {
      sessionStorage.setItem('refreshToken', token); // 세션스토리지에 리프레시 토큰 저장
      commit('SET_REFRESH_TOKEN', token); // state 할당
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
      axios.post('https://i7c202.p.ssafy.io:8282/api/auth/login', credentials)
        .then((res) => {
          const response = res.data.data;
          const access = response.accessToken;
          const refresh = response.refreshToken;
          dispatch('saveAccessToken', access);
          dispatch('saveRefreshToken', refresh);
          dispatch('setUserInfo', response); // 사용자정보 조회
          dispatch('fetchPhysicalInfo'); // 운동분석 조회
          dispatch('fetchSlogan'); // 슬로건 조회
          dispatch('fetchPoint'); // 포인트 조회
          dispatch('fetchCompete'); // 경쟁모드 최고기록 조회
          dispatch('fetchGoals'); // 경쟁모드 최고기록 조회
          dispatch('fetchProfileImg'); // 경쟁모드 최고기록 조회
          router.push('/'); // main 페이지로 이동
        })
        .catch((err) => {
          if (err.response.status === 400) {
            if (err.response.data.message === '회원가입 이메일 인증이 필요합니다.') {
              commit('SET_LOGIN_ERR', err.response.data.message);
              sessionStorage.removeItem('vuex');
              swal.fire(
                '#오운완',
                '회원가입 이메일 인증이 필요합니다.',
                'warning',
              );
              // router.push('/emailVerify');
            } else if (err.response.data.message === '이메일 혹은 비밀번호가 맞지 않습니다.') {
              // alert(err.response.data.message);
              commit('SET_LOGIN_ERR', err.response.data.message);
              sessionStorage.removeItem('vuex');
              swal.fire(
                '#오운완',
                '이메일 혹은 비밀번호가 맞지 않습니다.',
                'warning',
              );
            } else {
              commit('SET_LOGIN_ERR', err.response.data.message); // 서버 error
            }
          }
        });
    },
    logout({ state, dispatch }) { // 로그아웃
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
          dispatch('removeToken');
          dispatch('setUserInfo', null);
          router.push('/');
        })
        .catch((err) => {
          console.log(err);
        });
    },
    register({ commit }, credentials) {
      axios({
        url: 'https://i7c202.p.ssafy.io:8282/api/auth/join',
        method: 'post',
        data: credentials,
      })
        .then((res) => {
          commit('SET_SIGNUP_MSG', res.data.message);
          swal.fire(
            '#오운완',
            '회원가입 성공! 이메일 인증 후 로그인 해주세요!',
            'success',
          );
          router.push('/login');
        })
        .catch((err) => {
          if (err.response.data === 'OVERLAP') {
            commit('SET_SIGNUP_MSG', '이미 회원가입 된 이메일입니다.');
            swal.fire(
              '#오운완',
              '이미 회원가입 된 이메일입니다.',
              'warning',
            );
            // alert(state.signupInfo.message);
            // router.push('/login');
          } else {
            commit('SET_SIGNUP_MSG', '회원가입에 실패했습니다. 다시 시도해 주세요.');
            swal.fire(
              '#오운완',
              '회원가입에 실패했습니다. 다시 시도해 주세요.',
              'warning',
            );
            // alert(state.signupInfo.message);
          }
        });
    },
    findPassword({ state }) { // 비밀번호 찾기
      axios({
        url: 'https://i7c202.p.ssafy.io:8282/api/authpassword',
        method: 'get',
        header: {
          'X-AUTH-TOKEN': state.accessToken,
          'REFRESH-TOKEN': state.refreshToken,
        },
        data: state.userInfo.email, // 사용자 이메일 데이터를 이용해서 password 찾기
      })
        .then((res) => {
          console.log(res);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    passwordChg({ state }, payload) { // 비밀번호 변경
      axios({
        url: '',
        method: '',
        header: {
          'X-AUTH-TOKEN': state.accessToken,
          'REFRESH-TOKEN': state.refreshToken,
        },
        data: payload,
      })
        .then((res) => {
          console.log(res);
        })
        .catch((err) => {
          console.log(err);
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
          'Content-Type': 'application/json',
        },
        data: payload,
      })
        .then((res) => {
          console.log('res');
          console.log(res.data.data);
          console.log(payload);
          dispatch('setSlogan', payload.slogan);
          // router.push({ name: 'MyPageMainView' });
        })
        .catch((err) => {
          console.log(err);
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
          dispatch('setUserInfo', res.data.data);
          dispatch('fetchPhysicalInfo');
          alert('정보가 수정되었습니다.');
          // router.push({ name: 'MyPageMainView' });
        })
        .catch((err) => {
          console.log(err.toJSON());
        });
    },
    setProfileImg({ commit }, payload) {
      commit('SET_PROFILE_IMG', payload);
    },
    fetchProfileImg({ state, dispatch }) {
      axios({
        url: `https://i7c202.p.ssafy.io:8282/api/user/${state.userInfo.id}`,
        method: 'get',
        headers: {
          'X-AUTH-TOKEN': state.accessToken,
          'REFRESH-TOKEN': state.refreshToken,
          // 'Content-Type': 'pultipart/form-data',
        },
      })
        .then((res) => {
          console.log('이미지axios응답');
          dispatch('setProfileImg', res.data.data);
          // commit('SET_PROFILE_IMG', res.data.data);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    updateProfileImg({ state, dispatch }, payload) {
      console.log('axios 하기 전');
      axios({
        url: `https://i7c202.p.ssafy.io:8282/api/user/img/${state.userInfo.id}`,
        method: 'put',
        headers: {
          'X-AUTH-TOKEN': state.accessToken,
          'REFRESH-TOKEN': state.refreshToken,
          // 'Content-Type': 'application/json',
        },
        data: payload,
      })
        .then((res) => {
          // console.log('res');
          console.log(res.data.data);
          // console.log(payload);
          dispatch('setProfileImg', res.data.data);
          // router.push({ name: 'MyPageMainView' });
        })
        .catch((err) => {
          console.log(err);
        });
    },
    setPoint({ commit }, payload) {
      commit('SET_POINT', payload);
    },
    fetchPoint({ state, dispatch }) {
      axios({
        url: `https://i7c202.p.ssafy.io:8282/api/user/point/${state.userInfo.id}`,
        method: 'get',
        headers: {
          'X-AUTH-TOKEN': state.accessToken,
          'REFRESH-TOKEN': state.refreshToken,
        },
      })
        .then((res) => {
          // console.log(res.data.message);
          console.log(res.data.data);
          dispatch('setPoint', res.data.data);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    setCompete({ commit }, payload) {
      commit('SET_POINT', payload);
    },
    fetchCompete({ state, dispatch }) {
      axios({
        url: `https://i7c202.p.ssafy.io:8282/api/user/compete/${state.userInfo.id}`,
        method: 'get',
        headers: {
          'X-AUTH-TOKEN': state.accessToken,
          'REFRESH-TOKEN': state.refreshToken,
        },
      })
        .then((res) => {
          // console.log(res.data.message);
          console.log(res.data.data);
          dispatch('setCompete', res.data.data);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    setGoals({ commit }, payload) {
      commit('SET_GOALS', payload);
    },
    fetchGoals({ state, dispatch }) {
      axios({
        url: `https://i7c202.p.ssafy.io:8282/api/user/goal/${state.userInfo.id}`,
        method: 'get',
        headers: {
          'X-AUTH-TOKEN': state.accessToken,
          'REFRESH-TOKEN': state.refreshToken,
        },
      })
        .then((res) => {
          console.log(res.data.data);
          dispatch('setGoals', res.data.data);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    addGoals({ state, dispatch }, payload) {
      axios({
        url: `https://i7c202.p.ssafy.io:8282/api/user/goal/${state.userInfo.id}`,
        method: 'post',
        headers: {
          'X-AUTH-TOKEN': state.accessToken,
          'REFRESH-TOKEN': state.refreshToken,
        },
        data: payload,
      })
        .then((res) => {
          dispatch('setGoals', res.data.data);
        })
        .catch((err) => {
          console.log(err.toJSON());
        });
    },
    updateGoals({ state, dispatch }, payload) {
      axios({
        url: `https://i7c202.p.ssafy.io:8282/api/user/goal/${state.userInfo.id}`,
        method: 'put',
        headers: {
          'X-AUTH-TOKEN': state.accessToken,
          'REFRESH-TOKEN': state.refreshToken,
        },
        data: payload,
      })
        .then((res) => {
          dispatch('setGoals', res.data.data);
        })
        .catch((err) => {
          console.log(err.toJSON());
        });
    },
  },
  getters: {
    isLogin: (state) => !!state.accessToken,

    userInfo: (state) => state.userInfo,
    physicalInfo: (state) => state.physicalInfo,
    slogan: (state) => state.slogan,
    record: (state) => state.record,
    workout: (state) => state.workout,
    goals: (state) => state.goals,
    profileImg: (state) => state.profileImg,
  },
};
