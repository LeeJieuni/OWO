// eslint-disable-next-line
export const exercise = {
  namespaced: true,
  state: () => ({
    exerciseName: undefined,
    allSquatCountList: [],
    allLungeCountList: [],
    allBurpeeCountList: [],
    allScoreList: [],
    allSquatCountListSorted: new Set(),
    allLungeCountListSorted: new Set(),
    allBurpeeCountListSorted: new Set(),
    allScoreListSorted: new Set(),
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
      async function myfunc2() {
        for (let i = 0; i < state.allLungeCountList.length; i += 1) {
          // eslint-disable-next-line
          await state.allLungeCountListSorted.add(parseInt(state.allLungeCountList[i].allUserLungeCount));
        }
        // eslint-disable-next-line
        state.allLungeCountListSorted = Array.from(state.allLungeCountListSorted).sort((a, b) => { return b - a ; });
      }
      myfunc2();
    },
    CHANGE_BURPEE_COUNT_LIST: (state, payload) => {
      for (let i = 0; i < state.allBurpeeCountList.length; i += 1) {
        // eslint-disable-next-line
        if (state.allBurpeeCountList[i].connectionId == payload.connectionId) {
          state.allBurpeeCountList.splice(i, 1);
        }
      }
      state.allBurpeeCountList.push(payload);
    },
    CHANGE_BURPEE_COUNT_LIST_SORTED: (state) => {
      state.allBurpeeCountListSorted = new Set();
      async function myfunc3() {
        for (let i = 0; i < state.allBurpeeCountList.length; i += 1) {
          // eslint-disable-next-line
          await state.allBurpeeCountListSorted.add(parseInt(state.allBurpeeCountList[i].allUserBurpeeCount));
        }
        // eslint-disable-next-line
        state.allBurpeeCountListSorted = Array.from(state.allBurpeeCountListSorted).sort((a, b) => { return b - a ; });
      }
      myfunc3();
    },
    RESET_ALL_COUNT_LIST: (state) => {
      state.allSquatCountList = [];
      state.allSquatCountListSorted = new Set();
      state.allLungeCountList = [];
      state.allLungeCountListSorted = new Set();
      state.allBurpeeCountList = [];
      state.allBurpeeCountListSorted = new Set();
    },
    CHANGE_SCORE_LIST: (state, payload) => {
      state.allScoreList.push(payload);
    },
    CHANGE_SCORE_LIST_SORTED: (state) => {
      state.allScoreListSorted = new Set();
      async function myfunc4() {
        for (let i = 0; i < state.allScoreList.length; i += 1) {
          // eslint-disable-next-line
          await state.allScoreListSorted.add(parseInt(state.allScoreList[i].userScore));
        }
        // eslint-disable-next-line
        state.allScoreListSorted = Array.from(state.allScoreListSorted).sort((a, b) => { return b - a ; });
      }
      myfunc4();
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
    resetAllCountList({ commit }) {
      commit('RESET_ALL_COUNT_LIST');
    },
    changeScoreList({ commit }, payload) {
      commit('CHANGE_SCORE_LIST', payload);
    },
    changeScoreListSorted({ commit }) {
      commit('CHANGE_SCORE_LIST_SORTED');
    },
  },
};
