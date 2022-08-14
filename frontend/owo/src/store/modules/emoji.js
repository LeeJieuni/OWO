// eslint-disable-next-line
export const emoji = {
  namespaced: true,
  state: () => ({
    allEmojiList: [],
  }),
  mutations: {
    CHANGE_EMOJI_LIST: (state, payload) => {
      // state.allEmojiList = payload;
      for (let i = 0; i < state.allEmojiList.length; i += 1) {
        // eslint-disable-next-line
        if (state.allEmojiList[i].connectionId == payload.connectionId) {
          state.allEmojiList.splice(i, 1);
        }
      }
      state.allEmojiList.push(payload);
    },
    REMOVE_EMOJI_LIST: (state, payload) => {
      for (let i = 0; i < state.allEmojiList.length; i += 1) {
        // eslint-disable-next-line
        if (state.allEmojiList[i].connectionId == payload.connectionId) {
          state.allEmojiList.splice(i, 1);
        }
      }
    },
  },
  actions: {
    changeEmojiList({ commit }, payload) {
      console.log(payload);
      commit('CHANGE_EMOJI_LIST', payload);
      setTimeout(() => {
        commit('REMOVE_EMOJI_LIST', payload);
      }, 4000);
    },
    removeEmojiList({ commit }, payload) {
      commit('REMOVE_EMOJI_LIST', payload);
    },
  },
};
