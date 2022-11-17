const UserStore = {
    state: {
        // 로그인된 사용자 정보 객체
        user: {},
    
    },

    getters: {
        user(state) {
            return state.user;
          },
        
    }
}

export default UserStore;