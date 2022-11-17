<template>
  <div class="row justify-content-center">
    <div class="col-lg-8 col-md-10 col-sm-12">
      <h2 class="my-3 py-3 shadow-sm bg-light text-center">
        <mark class="sky">QnA 보기</mark>
      </h2>
    </div>
    <div class="col-lg-8 col-md-10 col-sm-12">
      <div class="row my-2">
        <h2 class="text-secondary px-5">{{ notice.articleNo }}. {{ notice.subject }}</h2>
      </div>
      <div class="row">
        <div class="col-md-8">
          <div class="clearfix align-content-center">
            <img
              class="avatar me-2 float-md-start bg-light p-2"
              src="https://raw.githubusercontent.com/twbs/icons/main/icons/person-fill.svg"
            />
            <p>
              <span class="fw-bold">{{ notice.userId }}</span> <br />
              <span class="text-secondary fw-light">
                {{ notice.registerTime }} 조회 : {{ notice.hit }}
              </span>
            </p>
          </div>
        </div>
        <div class="divider mb-3"></div>
        <div class="text-secondary">{{ notice.content }}</div>
        <div class="divider mt-3 mb-3"></div>
        <div class="d-flex justify-content-end">
          <button
            type="button"
            id="btn-list"
            class="btn btn-outline-primary mb-3"
            @click="modify(notice.articleNo)"
          >
            수정
          </button>
          <button
            type="button"
            id="btn-list"
            class="btn btn-outline-primary mb-3"
            @click="remove(notice.articleNo)"
          >
            삭제
          </button>
          <button
            type="button"
            id="btn-list"
            class="btn btn-outline-primary mb-3"
            @click="changeForm"
          >
            QnA 목록
          </button>
          <!--
          <c:if test="${article.userId eq userinfo.userId}">
            <button type="button" id="btn-mv-modify" class="btn btn-outline-success mb-3 ms-1">
              글수정
            </button>
            <button type="button" id="btn-delete" class="btn btn-outline-danger mb-3 ms-1">
              글삭제
            </button>
          </c:if>
          -->
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Constant from "@/common/Constant.js";
import { mapActions, mapGetters } from "vuex";
export default {
  //props: ["articleNo"],
  computed: {
    ...mapGetters(["notice"]),
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
  },
  watch: {
    $route() {
      this.getNotice(this.$route.params.articleNo);
    },
  },
};
</script>

<style scoped></style>
