// eslint-disable-next-line
export const exercise = {
  namespaced: true,
  state: () => ({
    exerciseName: undefined,
    allSquatCountListSorted: new Set(),
    allLungeCountListSorted: new Set(),
    allBurpeeCountListSorted: new Set(),
    allSquatCountList: [],
    allLungeCountList: [],
    allburpeeCountList: [],
  }),

  mutations: {
    CHANGE_EXERCISE_NAME: (state, payload) => {
      state.exerciseName = payload;
    },
    // CHANGE_SQUAT_COUNT_LIST: (state, payload) => {
    CHANGE_SQUAT_COUNT_LIST: (state, payload) => {
      for (let i = 0; i < state.allSquatCountList.length; i += 1) {
        // eslint-disable-next-line
        if (state.allSquatCountList[i].connectionId == payload.connectionId) {
          state.allSquatCountList.splice(i, 1);
        }
      }
      state.allSquatCountList.push(payload);
    },
    CHANGE_SQUAT_COUNT_LIST_SORTED: (state) => {
      state.allSquatCountListSorted = new Set();
      async function myfunc() {
        for (let i = 0; i < state.allSquatCountList.length; i += 1) {
          // eslint-disable-next-line
          await state.allSquatCountListSorted.add(parseInt(state.allSquatCountList[i].allUserSquatCount));
        }
        // eslint-disable-next-line
        state.allSquatCountListSorted = Array.from(state.allSquatCountListSorted).sort((a, b) => { return b - a ; });
      }
      myfunc();
    },
    CHANGE_LUNGE_COUNT_LIST: (state, payload) => {
      for (let i = 0; i < state.allLungeCountList.length; i += 1) {
        // eslint-disable-next-line
        if (state.allLungeCountList[i].connectionId == payload.connectionId) {
          state.allLungeCountList.splice(i, 1);
        }
      }
      state.allLungeCountList.push(payload);
    },
    CHANGE_LUNGE_COUNT_LIST_SORTED: (state) => {
      state.allLungeCountListSorted = new Set();
      async function myfunc() {
        for (let i = 0; i < state.allLungeCountList.length; i += 1) {
          // eslint-disable-next-line
          await state.allLungeCountListSorted.add(parseInt(state.allLungeCountList[i].allUserLungeCount));
        }
        // eslint-disable-next-line
        state.allLungeCountListSorted = Array.from(state.allLungeCountListSorted).sort((a, b) => { return b - a ; });
      }
      myfunc();
    },
    CHANGE_BURPEE_COUNT_LIST: (state, payload) => {
      for (let i = 0; i < state.allburpeeCountList.length; i += 1) {
        // eslint-disable-next-line
        if (state.allburpeeCountList[i].connectionId == payload.connectionId) {
          state.allburpeeCountList.splice(i, 1);
        }
      }
      state.allburpeeCountList.push(payload);
    },
    CHANGE_BURPEE_COUNT_LIST_SORTED: (state) => {
      state.allBurpeeCountListSorted = new Set();
      async function myfunc() {
        for (let i = 0; i < state.allBurpeeCountList.length; i += 1) {
          // eslint-disable-next-line
          await state.allBurpeeCountListSorted.add(parseInt(state.allBurpeeCountList[i].allUserBurpeeCount));
        }
        // eslint-disable-next-line
        state.allBurpeeCountListSorted = Array.from(state.allBurpeeCountListSorted).sort((a, b) => { return b - a ; });
      }
      myfunc();
    },
    RESET_ALL_COUNT_LIST: (state) => {
      state.allSquatCountList = [];
      state.allSquatCountListSorted = new Set();
      state.allLungeCountList = [];
      state.allLungeCountListSorted = new Set();
      state.allBurpeeCountList = [];
      state.allBurpeeCountListSorted = new Set();
    },
  },
  actions: {
    changeExerciseName({ commit }, payload) {
      commit('CHANGE_EXERCISE_NAME', payload);
    },
    changeSquatCountList({ commit }, payload) {
      commit('CHANGE_SQUAT_COUNT_LIST', payload);
    },
    changeSquatCountListSorted({ commit }) {
      commit('CHANGE_SQUAT_COUNT_LIST_SORTED');
    },
    changeLungeCountList({ commit }, payload) {
      commit('CHANGE_LUNGE_COUNT_LIST', payload);
    },
    changeLungeCountListSorted({ commit }) {
      commit('CHANGE_LUNGE_COUNT_LIST_SORTED');
    },
    changeBurpeeCountList({ commit }, payload) {
      commit('CHANGE_BURPEE_COUNT_LIST', payload);
    },
    changeBurpeeCountListSorted({ commit }) {
      commit('CHANGE_BURPEE_COUNT_LIST_SORTED');
    },
    resetallCountList({ commit }) {
      commit('RESET_ALL_COUNT_LIST');
    },
  },
};
