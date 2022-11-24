import Constant from "@/common/Constant.js";
import restApi from "@/util/http-common.js";

const NoticeStore = {
  state: {
    notices: [],
    notice: {},
  },
  getters: {
    notices: (state) => {
      return state.notices;
    },
    notice: (state) => {
      return state.notice;
    },
  },
  mutations: {
    [Constant.SET_NOTICES](state, payload) {
      state.notices = payload;
    },
    [Constant.SET_NOTICE](state, payload) {
      state.notice = payload;
    },
  },
  actions: {
    [Constant.GET_NOTICES](context) {
      restApi.get("/api/boards").then(({ data }) => {
        context.commit(Constant.SET_NOTICES, data);
      });
    },

    [Constant.GET_NOTICE](context, payload) {
      restApi.get(`/api/boards/${payload}`).then(({ data }) => {
        context.commit(Constant.SET_NOTICE, data);
      });
    },
    [Constant.REGIST_NOTICE](context, payload) {
      return restApi.post("/api/boards", payload);
    },

    [Constant.MODIFY_NOTICE](context, payload) {
      return restApi.put(`/api/boards/${payload.articleNo}`, payload).then(({ data }) => {
        context.commit(Constant.SET_NOTICE, data);
      });
    },
    [Constant.REMOVE_NOTICE](context, payload) {
      return restApi.delete(`/api/boards/${payload}`);
    },
  },
  modules: {},
};

export default NoticeStore;
