import { createApp } from 'vue';
import BootstrapVue3 from 'bootstrap-vue-3';
import VCalendar from 'v-calendar';
import VueSweetalert2 from 'vue-sweetalert2';
import App from './App.vue';
import router from './router';
import store from './store';
import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap-vue-3/dist/bootstrap-vue-3.css';
import 'v-calendar/dist/style.css';
import 'sweetalert2/dist/sweetalert2.min.css';

const app = createApp(App);
app.use(BootstrapVue3);
app.use(store);
app.use(router);
app.use(VueSweetalert2);
app.use(VCalendar, {});
app.mount('#app');
