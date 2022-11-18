import Vue from "vue";
import Vuex from "vuex";


import NoticeStore from "@/store/modules/NoticeStore";
import memberStore from "@/store/modules/memberStore";

import HouseStore from "@/store/modules/HouseStore";
import UserStore from "@/store/modules/UserStore";
import MapStore from "@/store/modules/MapStore";
import SearchStore from "@/store/modules/SearchStore";

import createPersistedState from "vuex-persistedstate";
Vue.use(Vuex);

export default new Vuex.Store({
  modules: {
    NoticeStore,
    memberStore,
    HouseStore,
    UserStore,
    MapStore,
    SearchStore,
  },

  plugins: [
    createPersistedState({
      // 브라우저 종료시 제거하기 위해 localStorage가 아닌 sessionStorage로 변경. (default: localStorage)
      storage: sessionStorage,
    }),
  ],
});
