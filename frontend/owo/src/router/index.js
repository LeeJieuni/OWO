import { createRouter, createWebHistory } from 'vue-router';
import swal from 'sweetalert2';
import mainPageView from '../views/MainPage/mainPageView.vue';
import RoomTabView from '../views/MainPage/RoomTabView.vue';
import MyPageView from '../views/MyPage/MyPageView.vue';
import MyPageMainView from '../views/MyPage/MyPageMainView.vue';
import MyPageAnalysView from '../views/MyPage/MyPageAnalysView.vue';
import MyPageUpdateView from '../views/MyPage/MyPageUpdateView.vue';
import MyPageScheduleView from '../views/MyPage/MyPageScheduleView.vue';
import MyPageSlideView from '../views/MyPage/MyPageSlideView.vue';
import CompetitionView from '../views/Room/CompetitionView.vue';
import FriendView from '../views/Room/FriendView.vue';
import YoutubeView from '../views/Room/YoutubeView.vue';
import login from '../views/accounts/loginView.vue';
import register from '../views/accounts/registerView.vue';
import redirectView from '../views/accounts/RedirectView.vue';
import TutorialView from '../views/Room/ATutorial.vue';

const routes = [
  {
    path: '/',
    name: 'mainpage',
    component: mainPageView,
    meta: { unauthorized: true },
  },
  {
    path: '/rooms',
    name: 'roomTabView',
    component: RoomTabView,
    // meta: { unauthorized: true },
  },
  {
    path: '/login',
    name: 'login',
    component: login,
    meta: { unauthorized: true },
  },
  {
    path: '/oauth/redirect',
    name: 'redirectView',
    component: redirectView,
    meta: { unauthorized: true },
  },
  {
    path: '/register',
    name: 'register',
    component: register,
    meta: { unauthorized: true },
  },
  {
    path: '/mypage',
    name: 'MyPage',
    component: MyPageView,
    meta: { unauthorized: true },
    children: [
      {
        path: 'main',
        name: 'MyPageMainView',
        component: MyPageMainView,
      },
      {
        path: 'analys',
        // name: 'MyPageAnalysView',
        component: MyPageAnalysView,
      },
      {
        path: 'update',
        // name: 'MyPageUpdateView',
        component: MyPageUpdateView,
      },
      {
        path: 'schedule',
        name: 'MyPageScheduleView',
        component: MyPageScheduleView,
      },
    ],
  },
  {
    path: '/slide',
    name: 'MyPageSlideView',
    component: MyPageSlideView,
  },
  {
    path: '/room/competition/',
    name: 'competition1',
    component: CompetitionView,
  },
  {
    path: '/room/competition/:sessionId',
    name: 'competition2',
    component: CompetitionView,
  },
  {
    path: '/room/friend/:sessionId',
    name: 'friend',
    component: FriendView,
  },
  {
    path: '/room/youtube/:sessionId',
    name: 'Youtube',
    component: YoutubeView,
  },
  {
    path: '/tutorial',
    name: 'ATutorial',
    component: TutorialView,
    meta: { unauthorized: true },
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

router.beforeEach((to, from, next) => {
  const token = sessionStorage.getItem('accessToken');
  const refresh = sessionStorage.getItem('refreshToken');
  if (token === null && refresh !== null) { // accesstoken은 없는데 refresh값이 있을 경우, => 토큰 재발급함수 실행.
    console.log('로그인 세션이 만료 되었습니다 다시 로그인 해주세요!');
  }
  if (to.matched.some((record) => record.meta.unauthorized || token)) {
    return next();
  }
  if (to.matched.some((record) => !record.meta.unauthorized || !token)) {
    swal.fire(
      '#오운완',
      '로그인이 필요한 서비스입니다.',
      'warning',
    );
    return next('/login');
  }
  swal.fire(
    '#오운완',
    '잘못된 접근입니다.',
    'warning',
  );
  return next('/');
});
export default router;
