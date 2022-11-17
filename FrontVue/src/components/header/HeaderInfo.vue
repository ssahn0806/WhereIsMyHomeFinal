<template>
  <b-container class="mt-4">
    <b-row align-h="end">
      <template v-if="!user || !user.name">
        <router-link to="/user/regist"><h2>회원가입</h2></router-link>
        <h2>|</h2>
        <router-link to="/user/login"><h2>로그인</h2></router-link>
      </template>
      <template v-else>
        <h2>ㅇㅇㅇ님 안녕하세요</h2>
        <router-link to="/user/logout"><h2>로그아웃</h2></router-link>
      </template>
    </b-row>
  </b-container>
</template>

<script>
import { mapGetters } from "vuex";
export default {
  id: "userInfo",

  computed: {
    ...mapGetters(["user"]),
  },
};
</script>

<style scoped>
h2 {
  color: white;
}
</style>
