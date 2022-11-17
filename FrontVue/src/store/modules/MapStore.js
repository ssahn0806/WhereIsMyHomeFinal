import Constant from "@/common/Constant.js";
import restApi from "@/util/http-common.js";

const MapStore = {
    state: {
        Lat: 37.498545,
        Lng: 127.062223
    },
    getters: {
        LatLng(state) {
            return {
                Lat : state.Lat,
                Lng : state.Lng
            }
        }
    },

    mutations: {
        [Constant.SET_LATLNG](state, payload) {
            console.log(payload);
            state.Lat = payload.lat;
            state.Lng = payload.lng;
        },
    },

    actions: {
        [Constant.GET_LATLNG](context,payload) {
            restApi.get(`/api/houses/coordsMap/${payload}`).then(({ data }) => {
                context.commit(Constant.SET_LATLNG, data);
            });
        },
    }
}

export default MapStore