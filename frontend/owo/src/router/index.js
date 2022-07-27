import { createRouter, createWebHistory } from 'vue-router';
import mainPageView from '../views/MainPage/mainPageView.vue';
import MyPageView from '../views/MyPage/MyPageView.vue';
import MyPageMainView from '../views/MyPage/MyPageMainView.vue';
import MyPageUpdateView from '../views/MyPage/MyPageUpdateView.vue';
import MyPageScheduleView from '../views/MyPage/MyPageScheduleView.vue';
import CompetitionView from '../views/Room/competition.vue';
import FriendChatView from '../views/Room/FriendChatView.vue';
import FriendNoChatView from '../views/Room/FriendNoChatView.vue';
import YoutubeChatView from '../views/Room/YoutubeChatView.vue';
import YoutubeNoChatView from '../views/Room/YoutubeNoChatView.vue';
import TestPage from '../views/Room/TestPage.vue';
import login from '../views/accounts/loginView.vue';
import register from '../views/accounts/registerView.vue';

const routes = [
  {
    path: '/',
    name: 'mainpage',
    component: mainPageView,
  },
  {
    path: '/login',
    name: 'login',
    component: login,
  },
  {
    path: '/join',
    name: 'join',
    component: register,
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
  {
    path: '/competition',
    name: 'competition',
    component: CompetitionView,
  },
  {
    path: '/friendchat',
    name: 'friendChat',
    component: FriendChatView,
  },
  {
    path: '/friendnochat',
    name: 'friendNoChat',
    component: FriendNoChatView,
  },
  {
    path: '/youtubechat',
    name: 'YoutubeChat',
    component: YoutubeChatView,
  },
  {
    path: '/youtubenochat',
    name: 'YoutubeNoChat',
    component: YoutubeNoChatView,
  },
  {
    path: '/testpage',
    name: 'TestPage',
    component: TestPage,
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
