<template>
  <div class="container" style="height: 81.8vh">
    <div class="row justify-content-center">
      
      <div class="col-lg-8 col-md-10 col-sm-12">
        <form id="form-register" method="POST" action="">
          <input type="hidden" name="act" value="write" />
          <div class="mb-3">
            <label for="subject" class="form-label">아이디 </label>
           
            <input
              type="text"
              value="11"
              class="form-control"
              id="userid"
              name="userid"
              v-model.lazy="member.userid"
              disabled
            />
          </div>
          <div class="mb-3">
            <label for="subject" class="form-label">이름 </label>
            <input
              type="text"
              class="form-control"
              id="username"
              name="username"
              v-model.lazy="member.username"
              placeholder="이름"
            />
          </div>
          <div class="mb-3">
            <label for="subject" class="form-label">비밀번호 </label>
            <input
              type="text"
              class="form-control"
              id="userpwd"
              name="userpwd"
              v-model.lazy="member.userpwd"
              placeholder="비밀번호"
            />
          </div>
          <div class="mb-3">
            <label for="subject" class="form-label">이메일 </label>
            <input
              type="text"
              class="form-control"
              id="email"
              name="email"
              v-model.lazy="member.email"
              placeholder="이메일"
            />
          </div>

          <div class="col-auto text-center">
            <button
              type="button"
              id="btn-register"
              class="btn btn-outline-primary mb-3"
              @click="modify(member)"
            >
              회원 정보 수정
            </button>
            <button
              type="button"
              id="btn-list"
              class="btn btn-outline-danger mb-3"
              @click="changeForm"
            >
              목록으로이동
            </button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>
<script>

import Constant from "@/common/Constant.js";
import { mapState, mapActions } from "vuex";
const memberStore = "memberStore";
export default {
  data() {
    return {
      member: {},
    };
  },

  name: "UserModify",

  computed: {
    ...mapState(memberStore, ["isLogin", "isLoginError", "userInfo"]),
  },
  methods: {
    ...mapActions(memberStore, ["userConfirm", "getUserInfo", Constant.MODIFY_MEMBER]),
    
    modify(payload) {
      this.modifyMember(payload).then(() => {
        this.$router.push({ name: "mypage" });
      });
    },
    changeForm() {
      this.$router.push({ name: "main" });
    },
  },

  created () {
    this.member = {...this.userInfo};
  },
};
</script>

<style></style>
