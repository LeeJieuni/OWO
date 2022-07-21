import { createRouter, createWebHistory } from 'vue-router';
import HomeView from '../views/HomeView.vue';
import MyPageView from '../views/MyPage/MyPageView.vue';
import MyPageMainView from '../views/MyPage/MyPageMainView.vue';
import MyPageUpdateView from '../views/MyPage/MyPageUpdateView.vue';
import MyPageScheduleView from '../views/MyPage/MyPageScheduleView.vue';

const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView,
  },
  {
    path: '/about',
    name: 'about',
    component: () => import(/* webpackChunkName: "about" */ '../views/AboutView.vue'),
  },
  {
    path: '/mypage',
    name: 'MyPage',
    component: MyPageView,
    children: [
      {
        path: 'main',
        // name: 'MyPageMainView',
        component: MyPageMainView,
      },
      {
        path: 'update',
        // name: 'MyPageUpdateView',
        component: MyPageUpdateView,
      },
      {
        path: 'schedule',
        // name: 'MyPageScheduleView',
        component: MyPageScheduleView,
      },
    ],
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
