import { createStore } from 'vuex';
import { Account } from '@/store/modules/accounts';

export default createStore({
  modules: {
    Account,
  },
});
