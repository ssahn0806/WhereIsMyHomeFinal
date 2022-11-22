import Constant from "@/common/Constant.js";

const ModalStore = {
    state: {
        openEmbed: false,
        url : "",
    },

    getters: {
        openEmbed(state) {
            return state.openEmbed;
        },
        url(state) {
            return state.url;
        }
    },

    mutations: {
        [Constant.SET_EMBED](state, payload) {
            state.openEmbed = payload;
        },
        [Constant.SET_URL](state, payload) {
            state.url = payload;
        }
    }
}

export default ModalStore;