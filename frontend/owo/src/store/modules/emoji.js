// eslint-disable-next-line
export const emoji = {
  namespaced: true,
  state: () => ({
    allEmojiList: [],
  }),
  mutations: {
    CHANGE_EMOJI_LIST: (state, payload) => {
      state.allEmojiList = payload;
    },
    REMOVE_EMOJI_LIST: (state) => {
      state.allEmojiList = [];
    },
  },
  actions: {
    changeEmojiList({ commit }, payload) {
      commit('CHANGE_EMOJI_LIST', payload);
      setTimeout(() => {
        commit('REMOVE_EMOJI_LIST');
      }, 5000);
    },
    removeEmojiList({ commit }) {
      commit('REMOVE_EMOJI_LIST');
    },
  },
};
