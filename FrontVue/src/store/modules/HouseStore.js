import Constant from "@/common/Constant.js";
import restApi from "@/util/http-common.js";

const HouseStore = {
    state: {
        // 검색된 거래내역 리스트
        deals: [],
        apts: {},
    },
    getters: {
        apts(state) {
            return state.apts;
        },
        deals(state) {
          return state.deals;
        },
    },
    mutations: {
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
        }
      },
      actions: {
        [Constant.GET_DEALS](context, payload) {
            return restApi(`/api/houses/housedeals/dong/${payload}`).then(({ data }) => {
                context.commit(Constant.SET_DEALS, data);
                context.commit(Constant.SET_APTS, data);
            });
        },
        [Constant.GET_APT](context, payload) {
            return restApi(`/api/houseinfos/name/${payload}`);
        },
    },
}

export default HouseStore;