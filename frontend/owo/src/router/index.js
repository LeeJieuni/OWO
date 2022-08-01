import { createRouter, createWebHistory } from 'vue-router';
import mainPageView from '../views/MainPage/mainPageView.vue';
import MyPageView from '../views/MyPage/MyPageView.vue';
import MyPageMainView from '../views/MyPage/MyPageMainView.vue';
import MyPageAnalysView from '../views/MyPage/MyPageAnalysView.vue';
import MyPageUpdateView from '../views/MyPage/MyPageUpdateView.vue';
import MyPageScheduleView from '../views/MyPage/MyPageScheduleView.vue';
import MyPageSlideView from '../views/MyPage/MyPageSlideView.vue';
import CompetitionView from '../views/Room/ACompetition.vue';
import FriendChatView from '../views/Room/FriendChatView.vue';
import FriendNoChatView from '../views/Room/FriendNoChatView.vue';
import YoutubeChatView from '../views/Room/YoutubeChatView.vue';
import YoutubeNoChatView from '../views/Room/YoutubeNoChatView.vue';
import login from '../views/accounts/loginView.vue';
import register from '../components/MainPage/joinModal.vue';

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
        // name: 'MyPageScheduleView',
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
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
