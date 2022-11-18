import Constant from "@/common/Constant.js";
import restApi from "@/util/http-common.js";

const MapStore = {
    state: {
        Lat: 37.498545,
        Lng: 127.062223,
        Level: 7,
        ToggleSidebar: false,
    },
    getters: {
        LatLng(state) {
            return {
                Lat : state.Lat,
                Lng : state.Lng
            }
        },
        Level(state) {
            return state.Level;
        },
        ToggleSidebar(state) {
            return state.ToggleSidebar;
        }
    },

    mutations: {
        [Constant.SET_LATLNG](state, payload) {
            state.Lat = payload.lat;
            state.Lng = payload.lng;
        },
        [Constant.SET_LEVEL](state, payload) {
            state.Level = payload;
        },
        [Constant.SET_SIDEBAR](state, payload) {
            state.ToggleSidebar = payload;
        }
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