import { createApp } from 'vue';
import BootstrapVue3 from 'bootstrap-vue-3';
import VCalendar from 'v-calendar';
import App from './App.vue';
import router from './router';
import store from './store';
import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap-vue-3/dist/bootstrap-vue-3.css';
import 'v-calendar/dist/style.css';

const app = createApp(App);
app.use(BootstrapVue3);
app.use(store);
app.use(router);
app.use(VCalendar, {});
app.mount('#app');
