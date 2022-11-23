import Vue from "vue";
import VueRouter from "vue-router";
import MainView from "@/views/MainView.vue";
import HouseView from "@/views/HouseView.vue";
import NoticeView from "@/views/NoticeView.vue";

import store from "@/store";

Vue.use(VueRouter);

//user auth
const onlyAuthUser = async (to, from, next) => {
  const checkUserInfo = store.getters["memberStore/checkUserInfo"];
  const checkToken = store.getters["memberStore/checkToken"];
  let token = sessionStorage.getItem("access-token");
  console.log("로그인 처리 전", checkUserInfo, token);

  if (checkUserInfo != null && token) {
    console.log("토큰 유효성 체크하러 가자!!!!");
    await store.dispatch("memberStore/getUserInfo", token);
  }
  if (!checkToken || checkUserInfo === null) {
    alert("로그인이 필요한 페이지입니다..");
    // next({ name: "login" });
    router.push({ name: "login" });
  } else {
    console.log("로그인 성공");
    next();
  }
};

const routes = [
  {
    path: "/",
    name: "main",
    component: MainView,
    redirect: "/search",
  },
  {
    path: "/house",
    name: "house",
    component: HouseView,
  },

  {
    path: "/notice",
    name: "notice",
    component: NoticeView,
    redirect: "/notice/list",
    children: [
      {
        path: "list",
        component: () => import("@/components/notice/NoticeList"),
      },
      {
        path: "registForm",
        beforeEnter: onlyAuthUser,
        component: () => import("@/components/notice/NoticeRegist"),
      },
      {
        path: "detail/:articleNo",
        //props: true,
        beforeEnter: onlyAuthUser,
        component: () => import("@/components/notice/NoticeDetail"),
      },
      {
        path: "modify/:articleNo",
        component: () => import("@/components/notice/NoticeModify"),
      },
    ],
  },
  {
    path: "/user",
    name: "user",
    component: () => import("@/views/UserView"),
    children: [
      {
        path: "regist",
        name: "regist",
        component: () => import("@/components/user/UserRegister"),
      },
      {
        path: "login",
        name: "login",
        component: () => import("@/components/user/UserLogin"),
      },
      {
        path: "mypage",
        name: "mypage",
        beforeEnter: onlyAuthUser,
        component: () => import("@/components/user/UserMyPage"),
      },
      {
        path: "modify",
        name: "modify",
        component: () => import("@/components/user/UserModify"),
      },
    ],
  },

  {
    path: "/search",
    name: "search",
    component: () => import("@/components/search/SearchBlog"),
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
