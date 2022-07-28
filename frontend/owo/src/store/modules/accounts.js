import router from '@/router';
import axios from 'axios';

export default {
  state: {
  },
  mutations: {
  },
  actions: {
    login({ commit, dispatch }, credentials) {
      axios({
        url: '/localhost:8282/api/auth/login',
        method: 'post',
        data: credentials,
      })
        .then((res) => {
          const { token } = res.data;
          dispatch('saveToken', token);
          dispatch('fetchCurrentUser');
          router.push({ name: 'mainpage' });
        })
        .catch((err) => {
          console.log(err);
          alert('로그인실패');
        });
    },
    logout({ getters, dispatch }) {
      axios({
        url: 'localhost:8282/api/auth/logout',
        method: 'post',
      });
    },
  },

};
