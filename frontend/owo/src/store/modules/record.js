// import router from '@/router';
import axios from 'axios';

// eslint-disable-next-line
export const record = {
  namespaced: true,
  state: () => ({
  }),
  getters: {
  },
  mutations: {
  },
  actions: {
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
