<template>
  <b-container class="mt-4" v-if="userInfo">
    <b-row class="justify-content-center" >
      <b-col cols="8">
        <b-alert variant="secondary" show><h3>나의 정보</h3></b-alert>
      </b-col>
    </b-row>
    <b-row>
      <b-col></b-col>
      <b-col cols="8">
        <b-jumbotron>
          <template #header>My Page</template>

          <template #lead> 내 정보 확인페이지입니다. </template>

          <hr class="my-4" />

          <b-container class="mt-4">
            <b-row>
              <b-col cols="2"></b-col>
              <b-col cols="2" align-self="end">아이디</b-col
              ><b-col cols="4" align-self="start">{{ userInfo.userid }}</b-col>
              <b-col cols="2"></b-col>
            </b-row>
            <b-row>
              <b-col cols="2"></b-col>
              <b-col cols="2" align-self="end">이름</b-col
              ><b-col cols="4" align-self="start">{{ userInfo.username }}</b-col>
              <b-col cols="2"></b-col>
            </b-row>
            <b-row>
              <b-col cols="2"></b-col>
              <b-col cols="2" align-self="end">이메일</b-col
              ><b-col cols="4" align-self="start">{{ userInfo.email }}</b-col>
              <b-col cols="2"></b-col>
            </b-row>
            <b-row>
              <b-col cols="2"></b-col>
              <b-col cols="2" align-self="end">관심지역 </b-col>
                <b-col cols="4" align-self="start" v-if="userInfo.favorloc">{{ userInfo.favorloc }}</b-col>
                <b-col cols="4" align-self="start" v-else>없음</b-col>
              
            </b-row>
          </b-container>
          <hr class="my-4" />

          <b-button variant="primary" @click="modify()" class="mr-1">정보수정</b-button>
          <b-button variant="danger" @click="remove(userInfo.userid)">회원탈퇴</b-button>
        </b-jumbotron>
      </b-col>
      <b-col></b-col>
    </b-row>
  </b-container>
</template>

<script>
import { mapState,mapActions , mapGetters,mapMutations} from "vuex";
import Constant from "@/common/Constant.js";
const memberStore = "memberStore";

export default {
  name: "UserMyPage",
  components: {},
  computed: {
    ...mapState(memberStore, ["userInfo"]),
    ...mapGetters(["checkUserInfo"]),
  },
  methods: {
    ...mapActions(memberStore, [Constant.DELETE_MEMBER]),
    ...mapMutations(memberStore, [Constant.SET_USERINFO]),
    remove(payload) {
      console.log("remove : " + payload);
       this.deleteMember(payload).then(() => {
         console.log("remove");
         console.log("userinfo : " + this.userInfo);
         
         this.$router.push({ name: "login" });
       });
     },
    modify() {
      console.log("modify");
      this.$router.push({ name: "modify" });
     }
  },
};
</script>

<style></style>
