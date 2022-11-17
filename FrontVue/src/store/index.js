import Vue from "vue";
import Vuex from "vuex";
import NoticeStore from "@/store/modules/NoticeStore";
import HouseStore from "@/store/modules/HouseStore";
import UserStore from "@/store/modules/UserStore";
import MapStore from "@/store/modules/MapStore";
Vue.use(Vuex);

export default new Vuex.Store({
  modules: {
    NoticeStore,
    HouseStore,
    UserStore,
    MapStore
  }
});
