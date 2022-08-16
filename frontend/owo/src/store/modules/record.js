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
    dayExerciseList: '',
    achievementRate: '',
    recordPicture: '',
    dayPictures: '',
    monthStampHour: '',
    monthRecord: '',
  }),
  getters: {
    percentage: (state) => state.percentage,
    userId: (state) => state.userId,
    dayExerciseList: (state) => state.dayExerciseList,
    achievementRate: (state) => state.achievementRate,
    dayPictures: (state) => state.dayPictures,
    monthStampHour: (state) => state.monthStampHour,
    monthRecord: (state) => state.monthRecord,
  },
  mutations: {
    SET_PERCENTAGE: (state, payload) => {
      state.percentage = payload;
    },
    SET_SESSIONS: (state, payload) => {
      state.accessToken = payload.accessToken;
      state.refreshToken = payload.refreshToken;
      state.userId = payload.user.id;
    },
    SET_DAY_EXERCISE_LIST: (state, payload) => {
      state.dayExerciseList = payload;
    },
    SET_ACHIEVEMENT_RATE: (state, payload) => {
      state.achievementRate = payload;
    },
    SET_RECORD_PICTURE: (state, payload) => {
      state.recordPicture = payload;
    },
    SET_DAY_PICTURES: (state, payload) => {
      state.dayPictures = payload;
    },
    SET_MONTH_STAMP_HOUR: (state, payload) => {
      console.log('length', payload.length);
      for (let i = 0; i < payload.length; i += 1) {
        payload[i].day = Number(payload[i].datetime.substr(8));
      };
      state.monthStampHour = payload;
    },
    SET_MONTH_RECORD: (state, payload) => {
      state.monthRecord = payload;
    },
  },
  actions: {
    fetchSessions({ commit }) {
      let userInfo = sessionStorage.getItem('vuex');
      userInfo = JSON.parse(userInfo);
      const accessToken = userInfo['accounts']['accessToken'];
      const refreshToken = userInfo['accounts']['refreshToken'];
      const user = userInfo['accounts']['userInfo'];
      const payload = {
        accessToken: accessToken,
        refreshToken: refreshToken,
        user: user,
      };
      console.log('record test');
      console.log(payload);
      commit('SET_SESSIONS', payload);
    },
    fetchAchievementRate({ state, commit }) {
      console.log('fetchAchievementRate axios 전');
      axios({
        url: `https://i7c202.p.ssafy.io:8282/api/user/record/goal/do/${state.userId}`,
        method: 'get',
        headers: {
          'X-AUTH-TOKEN': state.accessToken,
          'REFRESH-TOKEN': state.refreshToken,
        },
      })
        .then((res) => {
          console.log('fetchAchievementRate 응답');
          console.log(res.data.message);
          console.log(res.data.data);
          commit('SET_ACHIEVEMENT_RATE', res.data.data);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    fetchPercentage({ state, commit }) {
      console.log(' fetchPercentage axios 전');
      // console.log(state.userId);
      axios({
        url: `https://i7c202.p.ssafy.io:8282/api/user/record/percentage/${state.userId}`,
        method: 'get',
        headers: {
          'X-AUTH-TOKEN': state.accessToken,
          'REFRESH-TOKEN': state.refreshToken,
        },
      })
        .then((res) => {
          console.log('fetchPercentage응답');
          console.log(res.data.data);
          commit('SET_PERCENTAGE', res.data.data);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    fetchDayExerciseList({ state, dispatch, commit }, date) {
      console.log(' fetchDayExerciseList axios 전');
      // console.log(state.userId);
      axios({
        url: `https://i7c202.p.ssafy.io:8282/api/user/record/day/${state.userId}/${date}`,
        method: 'get',
        headers: {
          'X-AUTH-TOKEN': state.accessToken,
          'REFRESH-TOKEN': state.refreshToken,
        },
        // data: date,
      })
        .then((res) => {
          // for (let i = 0; i < res.data.data.length; i += 1) {
          //   console.log(res.data.data[i].recordId);
          //   dispatch('fetchRecordPicture', res.data.data[i].recordId);
          // }
          commit('SET_DAY_EXERCISE_LIST', res.data.data);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    fetchDayPictures({ state, commit }, date) {
      console.log(' fetchDayPictures axios 전');
      // console.log(state.userId);
      axios({
        url: `https://i7c202.p.ssafy.io:8282/api/user/record/img/${state.userId}/${date}`,
        method: 'get',
        headers: {
          'X-AUTH-TOKEN': state.accessToken,
          'REFRESH-TOKEN': state.refreshToken,
        },
        // data: date,
      })
        .then((res) => {
          console.log(res.data.data);
          commit('SET_DAY_PICTURES', res.data.data);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    fetchRecordPicture({ state, commit }, recordId) {
      console.log(' fetchDayExerciseList axios 전');
      // console.log(state.userId);
      axios({
        url: `https://i7c202.p.ssafy.io:8282/api/user/record/img/${recordId}`,
        method: 'get',
        headers: {
          'X-AUTH-TOKEN': state.accessToken,
          'REFRESH-TOKEN': state.refreshToken,
        },
        // data: date,
      })
        .then((res) => {
          commit('SET_RECORD_PICTURE', res.data.data);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    fetchMonthStampHour({ state, commit }, date) {
      console.log(' fetchMonthStampHour axios 전');
      // console.log(state.userId);
      axios({
        url: `https://i7c202.p.ssafy.io:8282/api/user/record/placeNtime/${state.userId}/${date[0]}/${date[1]}`,
        method: 'get',
        headers: {
          'X-AUTH-TOKEN': state.accessToken,
          'REFRESH-TOKEN': state.refreshToken,
        },
      })
        .then((res) => {
          console.log('month stamp hour 응답', res.data.data);
          commit('SET_MONTH_STAMP_HOUR', res.data.data);
        })
        .catch((err) => {
          console.log(err);
          commit('SET_MONTH_STAMP_HOUR', '')
          // console.log(state.monthStampHour);
        });
    },
    fetchMonthRecord({ state, commit }, payload) {
      console.log(' fetchMonthRecord axios 전');
      console.log(payload[0], payload[1]);
      axios({
        url: `https://i7c202.p.ssafy.io:8282/api/user/record/month/${state.userId}/${payload[0]}/${payload[1]}`,
        method: 'get',
        headers: {
          'X-AUTH-TOKEN': state.accessToken,
          'REFRESH-TOKEN': state.refreshToken,
        },
      })
        .then((res) => {
          console.log('month record 응답');
          console.log(res.data.data);
          commit('SET_MONTH_RECORD', res.data.data);
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
  // setDayPictures({ commit }, payload) {
  //   commit('SET_DAY_PICTURES', payload);
  // },
  // loadDayRecord({ state, dispatch }, date) {
  //   axios({
  //     url: `https://i7c202.p.ssafy.io:8282/api/record/day/${sessionStorage.getItem('userInfo.id')}/${date}`,
  //     method: 'get',
  //     headers: {
  //       'X-AUTH-TOKEN': state.accessToken,
  //       'REFRESH-TOKEN': state.refreshToken,
  //     },
  //     // data: date,
  //   })
  //     .then((res) => {
  //       dispatch('setDayPictures', res.data.data);
  //     })
  //     .catch((err) => {
  //       console.log(err);
  //     });
  // },
  // loadDayPicture({ state, dispatch }, payload) {
  //   axios({
  //     url: 'https://i7c202.p.ssafy.io/api/record/img/:memberId/:date',
  //     method: 'put',
  //     headers: {
  //       'X-AUTH-TOKEN': state.accessToken,
  //       'REFRESH-TOKEN': state.refreshToken,
  //     },
  //     data: payload,
  //   })
  //     .then((res) => {
  //       dispatch('setDayPicture', res.data.data);
  //     })
  //     .catch((err) => {
  //       console.log(err.toJSON());
  //     });
  //   },
    // eslint-disable-next-line
    // sendRecord({}, { credentials, credentialsUser }) {
    //   console.log(credentialsUser);
    //   console.log('------');
    //   axios.post(`https://i7c202.p.ssafy.io:8282/api/record/finish/${credentialsUser.memberId}/${credentialsUser.meetingRoomId}`, credentials)
    //     .then((res) => {
    //       console.log('성공', res.data);
    //     })
    //     .catch((err) => {
    //       console.log('실패', err);
    //     });
    // },
};
