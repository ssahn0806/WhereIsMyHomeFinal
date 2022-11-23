<template>
  <b-navbar toggleable="lg" type="dark" variant="secondary">
    <b-navbar-brand href="#">바로가기</b-navbar-brand>

    <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

    <b-collapse id="nav-collapse" is-nav>
      <b-navbar-nav>
        <b-nav-item @click="move(`/search`)">Main</b-nav-item>
        <b-nav-item class="pl-15" @click="move(`/notice`)">공지사항</b-nav-item>
        <b-nav-item class="pl-15" @click="move(`/house`)">부동산 조회</b-nav-item>
      </b-navbar-nav>

      <!-- Right aligned nav items -->
      <b-navbar-nav class="ml-auto">
        <b-nav-item-dropdown right v-if="userInfo == null">
          <template #button-content>
            <em style="color: black">User</em>
          </template>
          <b-dropdown-item @click="move(`/user/login`)">로그인</b-dropdown-item>
          <b-dropdown-item @click="move(`/user/regist`)">회원가입</b-dropdown-item>
        </b-nav-item-dropdown>
        <b-nav-item-dropdown right v-else>
          <template #button-content>
            <h5 style="color: black">{{ userInfo.userid }}님</h5>
          </template>
          <b-dropdown-item @click="move(`/user/mypage`)">내정보 보기</b-dropdown-item>
          <b-dropdown-item @click.prevent="onClickLogout">로그 아웃</b-dropdown-item>
        </b-nav-item-dropdown>
      </b-navbar-nav>
    </b-collapse>
  </b-navbar>
</template>

<script>
import { mapState, mapGetters, mapActions } from "vuex";

const memberStore = "memberStore";

export default {
  //name: "TheHeaderNavbar",
  name: "HeaderInfo2",

  data() {
    return {};
  },
  computed: {
    ...mapState(memberStore, ["isLogin", "userInfo"]),
    ...mapGetters(["checkUserInfo"]),
  },
  methods: {
    ...mapActions(memberStore, ["userLogout"]),
    onClickLogout() {
      console.log(this.userInfo.userid);
      this.userLogout(this.userInfo.userid);
      sessionStorage.removeItem("access-token"); //저장된 토큰 없애기
      sessionStorage.removeItem("refresh-token"); //저장된 토큰 없애기
      if (this.$route.path != "/") this.$router.push({ name: "main" });
    },
    move(payload) {
      //this.$router.push("/notice/list");
      console.log("payload : " + payload);
      this.$router.push(`${payload}`);
    },
  },
};
</script>

<style scoped>
h2 {
  color: white;
}
/* .navbar{
    padding:"rem 1rem";
  } */
nav.navbar {
  position: relative;
  display: flex;
  flex-wrap: wrap;
  align-items: center;
  justify-content: space-between;
  height: 100%;
}

.navbar-dark .navbar-nav .nav-link {
  color: rgb(217, 198, 219);
  font-size: 2rem;
}
.navbar-dark {
  color: black;
}
</style>
