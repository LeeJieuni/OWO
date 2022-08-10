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
      bmi: null,
      bmr: null,
      caloriePerDay: null,
    },
    image: '',
    record: {
      point: '', // 경쟁
      exp: '', // 자유, 영상 경험치
    },
    // compete: {
    //   workout1: '', // 운동1 최고기록
    //   workout2: '', // 운동2 최고기록
    //   workout3: '', // 운동3 최고기록
    // },
    goals: '',
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
    // SET_COMPETE: (state, payload) => {
    //   state.compete.workout1 = payload.workout1;
    //   state.compete.workout2 = payload.workout2;
    //   state.compete.workout3 = payload.workout3;
    // },
    SET_GOAL: (state, payload) => {
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
    login({ dispatch, commit }, credentials) { // 로그인
      axios.post('https://i7c202.p.ssafy.io:8282/api/auth/login', credentials) //
        .then((res) => {
          const response = res.data.data;
          const access = response.accessToken;
          const refresh = response.refreshToken;
          console.log(response);
          dispatch('saveAccessToken', access);
          dispatch('saveRefreshToken', refresh);
          dispatch('setUserInfo', response);
          dispatch('fetchPhysicalInfo');
          dispatch('fetchSlogan');
          dispatch('fetchPoint');
          dispatch('fetchCompete');
          dispatch('fetchGoal');
          dispatch('fetchProfileImg');
          router.push('/');
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
            } else if (err.response.data.message === '이메일 혹은 비밀번호가 맞지 않습니다.') {
              commit('SET_LOGIN_ERR', err.response.data.message);
              sessionStorage.removeItem('vuex');
              swal.fire(
                '#오운완',
                '이메일 혹은 비밀번호가 맞지 않습니다.',
                'warning',
              );
            } else {
              commit('SET_LOGIN_ERR', err.response.data.message); // 서버 error
              swal.fire(
                '#오운완',
                '로그인에 실패하였습니다. 다시 시도해 주세요!',
                'warning',
              );
            }
          }
        });
    },
    socialLogin({ commit }, token) {
      axios({
        url: 'https://i7c202.p.ssafy.io:8282/api/social',
        method: 'get',
        headers: {
          'X-AUTH-TOKEN': token,
        },
      })
        .then((res) => {
          console.log('소셜로그인 in vuex');
          commit('SET_USER_INFO', res.data);
          router.push('/');
          console.log(res.data);
        })
        .catch((err) => {
          console.log(err);
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
          swal.fire(
            '#오운완',
            '로그아웃 성공!',
            'success',
          );
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
            commit('SET_SIGNUP_MSG', err.response.data);
            swal.fire(
              '#오운완',
              '이미 회원가입 된 이메일입니다.',
              'warning',
            );
            router.push('/login');
          } else {
            commit('SET_SIGNUP_MSG', err.response.data);
            swal.fire(
              '#오운완',
              '회원가입에 실패했습니다. 다시 시도해 주세요.',
              'warning',
            );
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
    physicalInfo({ state, commit }) {
      axios({
        url: `https://i7c202.p.ssafy.io:8282/api/user/bmi/${state.userInfo.id}`,
        method: 'get',
        headers: {
          'X-AUTH-TOKEN': state.accessToken,
          'REFRESH-TOKEN': state.refreshToken,
        },
      })
        .then((res) => {
          console.log('운동분석 업데이트');
          console.log(res.data.data);
          commit('SET_PHYSICAL_INFO', res.data.data);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    updateUserInfo({ state, dispatch }, payload) {
      console.log(payload);
      console.log('액시오스하기전');
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
          console.log(err);
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
        },
        // responseType: 'blob',
      })
        .then((res) => {
          // eslint-disable-next-line
          // const url = window.URL.createObjectURL(new Blob([res.data], { type: res.headers['content-type'] }));
          console.log('이미지axios응답');
          console.log(res);
          console.log(res.data);
          console.log('응답 끝');
          const url = URL.createObjectURL(res.data);
          dispatch('setProfileImg', url);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    updateProfileImg({ state, dispatch }, payload) {
      console.log('이미지 업데이트 axios 하기 전');
      axios({
        url: `https://i7c202.p.ssafy.io:8282/api/user/img/${state.userInfo.id}`,
        method: 'post',
        headers: {
          'X-AUTH-TOKEN': state.accessToken,
          'REFRESH-TOKEN': state.refreshToken,
          // 'Content-Type': 'application/json',
        },
        data: payload,
      })
        .then((res) => {
          console.log('이미지 업데이트 res.data.data 보내기 전');
          console.log(res);
          alert(res.data.message);
          // console.log(payload);
          dispatch('setProfileImg', res.data);
          // dispatch('fetchProfileImg', res.data);  // 이미지 바꾸고 바로 페치하고싶은데, 이미지 바꾼 게 완료된 후 하고싶은데
          // router.push({ name: 'MyPageMainView' });
        })
        .catch((err) => {
          console.log(err);
        });
    },
    fetchPoint({ state, commit }) {
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
          // console.log(res.data.data);
          commit('SET_POINT', res.data.data);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    fetchCompete({ state, commit }) {
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
          commit('SET_COMPETE', res.data.data);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    fetchGoal({ state, commit }) {
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
          commit('SET_GOAL', res.data.data);
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
    record: (state) => state.record,
    workout: (state) => state.workout,
    goals: (state) => state.goals,
    profileImg: (state) => state.profileImg,
  },
};
