import Constant from "@/common/Constant.js";
import restApi from "@/util/http-common.js";

const SearchStore = {
  state: {
    blogs: [],
    news: [],
  },
  getters: {
    blogs: (state) => {
      return state.blogs;
    },
    news: (state) => {
      return state.news;
    },
  },
  mutations: {
    [Constant.SET_BLOGS](state, payload) {
      state.blogs = payload;
    },
    [Constant.SET_NEWS](state, payload) {
      state.news = payload;
    },
  },
  actions: {
    [Constant.GET_BLOGS](context) {
      return restApi.get("/api/searchs/blogs").then(({ data }) => {
<<<<<<< HEAD
=======
        console.log(data);
>>>>>>> f72cd9b629cd12f2e78fb3956e9614a4350086b7
        context.commit(Constant.SET_BLOGS, data);
      });
    },
    [Constant.GET_NEWS](context, payload) {
      if (!payload) {
        return restApi.get("/api/searchs/news").then(({ data }) => {
<<<<<<< HEAD
        console.log(data.result);
        context.commit(Constant.SET_NEWS, data.result);
=======
          console.log(data.result);

          context.commit(Constant.SET_NEWS, data.result);
>>>>>>> f72cd9b629cd12f2e78fb3956e9614a4350086b7
        });
      }
      if (payload) {
        return restApi.get(`/api/searchs/news/${payload}`).then(({ data }) => {
          // console.log(data.result);
          context.commit(Constant.SET_NEWS, data.result);
        });
      }
    },
  },
  modules: {},
};

export default SearchStore;
