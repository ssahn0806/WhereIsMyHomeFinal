import Vue from "vue";
import VueRouter from "vue-router";
import MainView from "@/views/MainView.vue";
import HouseView from "@/views/HouseView.vue";
import NoticeView from "@/views/NoticeView.vue";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "main",
    component: MainView,
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
        component: () => import("@/components/notice/NoticeRegist"),
      },
      {
        path: "detail/:articleNo",
        //props: true,
        component: () => import("@/components/notice/NoticeDetail"),
      },
      {
        path: "modify/:articleNo",
        component: () => import("@/components/notice/NoticeModify"),
      },
    ],
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
