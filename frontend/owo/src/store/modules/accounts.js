// eslint-disable-next-line
export const Account = {
  namespaced: true,
  state: () => ({
    isLogin: true,
    userInfo: {
      age: '',
      height: '',
    },
  }),
  mutations: {
    change(state) {
      state.isLogin = false;
    },
  },
  actions: {
    test(commit) {
      commit('change');
    },
  },
  getters: {
    getLogin(state) {
      return state.isLogin;
    },
  },
};
