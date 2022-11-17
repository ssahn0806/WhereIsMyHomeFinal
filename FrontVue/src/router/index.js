import Vue from "vue";
import VueRouter from "vue-router";
import MainView from "../views/MainView.vue";
import HouseView from "@/views/HouseView.vue";
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
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
