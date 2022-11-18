import Constant from "@/common/Constant.js";
import restApi from "@/util/http-common.js";

const SearchStore = {
  state: {
    blogs: [],
  },
  getters: {
    blogs: (state) => {
      return state.blogs;
    },
    
  },
  mutations: {
    [Constant.SET_BLOGS](state, payload) {
      state.blogs = payload;
    },
    
  },
  actions: {
    [Constant.GET_BLOGS](context) {
          restApi.get("/api/searchs").then(({ data }) => {
              console.log(data);
        context.commit(Constant.SET_BLOGS, data);
      });
    },

    
  },
  modules: {},
};

export default SearchStore;
