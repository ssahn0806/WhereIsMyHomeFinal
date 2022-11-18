import Vue from "vue";
import Vuex from "vuex";
import Constant from "@/common/Constant";
import restApi from "@/util/http-common.js";


import NoticeStore from "@/store/modules/NoticeStore";
import memberStore from "@/store/modules/memberStore";

import HouseStore from "@/store/modules/HouseStore";
import UserStore from "@/store/modules/UserStore";
import MapStore from "@/store/modules/MapStore";

import createPersistedState from "vuex-persistedstate";
Vue.use(Vuex);

export default new Vuex.Store({
  modules: {
    NoticeStore,
    memberStore,
    HouseStore,
    UserStore,
    MapStore,
  },

  state: {
    // 로그인된 사용자 정보 객체
    //user: {},
    // 검색된 아파트 리스트
    apts: [],
  },
  getters: {
    // user(state) {
    //   return state.user;
    // },
    // apts(state) {
    //   return state.apts;
    // },
  },
  mutations: {
    [Constant.SET_APTS](state, payload) {
      state.apts = payload;
    },
  },
  actions: {
    [Constant.GET_APTS](context, payload) {
      restApi(`/api/houses/housedeals/dong/${payload}`).then(({ data }) => {
        context.commit(Constant.SET_APTS, data);
      });
    },
  },
  plugins: [
    createPersistedState({
      // 브라우저 종료시 제거하기 위해 localStorage가 아닌 sessionStorage로 변경. (default: localStorage)
      storage: sessionStorage,
    }),
  ],
});
