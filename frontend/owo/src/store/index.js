import { createStore } from 'vuex';
import axios from 'axios';

export default createStore({
  state: {
    isLogin: false,
    isLoginError: false,
    userInfo: {
      username: '',
    },
  },
  mutations: {
  },
  actions: {
    login({ commit }, credentials) { // eslint-disable-line no-unused-vars
      axios.post('http://localhost:8282/api/auth/login', credentials)
        .then((res) => {
          console.log(res);
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
});
