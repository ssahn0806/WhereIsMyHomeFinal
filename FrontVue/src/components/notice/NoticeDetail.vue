<template>
  <div class="row justify-content-center">
    <div class="col-lg-10 col-md-10 col-sm-12">
        <h2 class="mt-3 my-3 py-3 shadow-sm bg-dark text-center" style="border-radius:10px">
          <mark class="bg-dark text-light">공지사항 상세보기</mark>
      </h2>
    </div>
    <div class="col-lg-10 col-md-10 col-sm-12 shadow-sm">
      <div class="row my-2 justify-content-flex-start align-contents-space-between">
        <h2 class="text-secondary px-5 mr-5">{{ notice.articleNo }}. {{ notice.subject }}</h2>
        <div class="clearfix ml-6 align-contents-baseline">
          <p>
            <span class="fw-bold">{{ notice.userId }} / </span>
            <span class="text-secondary fw-light">
              {{ notice.registerTime }} / 조회 : {{ notice.hit }}
            </span>
          </p>
        </div>
      </div>
      <div class="row justify-content-center shadow-lg">
        <div class="text-secondary" style="height:200px">{{ notice.content }}</div>
      </div>
      <div class="row justify-content-end">
          <button
            type="button"
            id="btn-list"
            class="btn btn-outline-success mb-3 mr-3"
            @click="modify(notice.articleNo)"
            v-if="userInfo && userInfo.userid == admin"
          >
            수정
          </button>
          <button
            type="button"
            id="btn-list"
            class="btn btn-outline-danger mb-3 mr-3"
            @click="remove(notice.articleNo)"
            v-if="userInfo && userInfo.userid == admin"
          >
            삭제
          </button>
          <button
            type="button"
            id="btn-list"
            class="btn btn-outline-primary mb-3 mr-3"
            @click="changeForm"
          >
            공지사항 목록
          </button>
      </div>
    </div>
  </div>
</template>

<script>
import Constant from "@/common/Constant.js";
import { mapState, mapActions, mapGetters } from "vuex";
const memberStore = "memberStore";
export default {
  data() {
    return {
      admin: "admin",
    };
  },
  computed: {
    ...mapGetters(["notice"]),
    ...mapState(memberStore, ["userInfo"]),
  },
  methods: {
    ...mapActions([Constant.GET_NOTICE, Constant.REMOVE_NOTICE]),
    modify(articleNo) {
      console.log(articleNo);
      this.$router.push(`/notice/modify/${articleNo}`);
    },
    remove(payload) {
      console.log("articleno : " + payload);
      this.removeNotice(payload).then(() => {
        this.$router.push("/notice");
      });
    },
    changeForm() {
      this.$router.push("/notice");
    },
  },

  created() {
    this.getNotice(this.$route.params.articleNo);
    console.log(this.userInfo);
  },
  watch: {
    $route() {
      this.getNotice(this.$route.params.articleNo);
    },
  },
};
</script>

<style scoped></style>
