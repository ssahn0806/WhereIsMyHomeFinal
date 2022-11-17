import Vue from "vue";
import Vuex from "vuex";
import Constant from "@/common/Constant.js";
import restApi from "@/util/http-common.js";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    // 로그인된 사용자 정보 객체
    user: {},
    // 검색된 아파트 리스트
    apts: [],
  },
  getters: {
    user(state) {
      return state.user;
    },
    apts(state) {
      return state.apts;
    },
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
  modules: {},
});
