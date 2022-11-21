import Constant from "@/common/Constant.js";
import restApi from "@/util/http-common.js";

const HouseStore = {
  state: {
    ndong: {},
    // 검색된 거래내역 리스트
    deals: [],
    apts: {},
    // 상세 보기를 눌렀을 때 아파트 정보
    apt: {},
    status: "not_checked",
  },
  getters: {
    status(state) {
      return state.status;
    },
    ndong(state) {
      return state.ndong;
    },
    apt(state) {
      return state.apt;
    },
    apts(state) {
      return state.apts;
    },
    deals(state) {
      return state.deals;
    },
  },
  mutations: {
    [Constant.SET_STATUS](state, payload) {
      state.status = payload;
    },
    [Constant.SET_NDONG](state, payload) {
      state.ndong = payload;
    },
    [Constant.SET_DEALS](state, payload) {
      state.deals = payload;
    },
    [Constant.SET_APTS](state, payload) {
      //중복 제거하기
      // state.apts = payload.reduce((acc, current) => {
      //     const x = acc.find(item => item.houseInfo.aptCode === current.houseInfo.aptCode);
      //     if (!x) {
      //         return acc.concat([current]);
      //     } else {
      //         return acc;
      //     }
      // }, []);
      //그룹별로 묶기
      state.apts = payload.reduce((acc, curr) => {
        const { houseInfo } = curr;
        if (acc[houseInfo.aptCode]) acc[houseInfo.aptCode].push(curr);
        else acc[houseInfo.aptCode] = [curr];
        return acc;
      }, {});
    },

    [Constant.SET_APT](state, payload) {
      state.apt = payload;
    },
  },
  actions: {
    [Constant.GET_DEALS](context, payload) {
      return restApi(`/api/houses/housedeals/dong/${payload}`).then(({ data }) => {
        context.commit(Constant.SET_DEALS, data);
        context.commit(Constant.SET_APTS, data);
      });
    },
    [Constant.GET_APT](context, payload) {
      return restApi(`/api/houses/houseinfos/name/${payload}`).then(({ data }) => {
        context.commit(Constant.SET_APT, data);
      });
    },
  },
};

export default HouseStore;
