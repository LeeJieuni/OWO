import { createStore } from 'vuex';
import { accounts } from '@/store/modules/accounts';
// import { member } from '@/store/modules/member';

export default createStore({
  modules: {
    accounts,
    // member
  },
});
