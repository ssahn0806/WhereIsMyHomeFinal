<template>
  <div class="container" style="height: 81.8vh">
    <div class="row justify-content-center">
      <div class="col-lg-8 col-md-10 col-sm-12">
        <h2 class="my-3 py-3 shadow-sm bg-light text-center">
          <mark class="sky">QnA 쓰기</mark>
        </h2>
      </div>
      <div class="col-lg-8 col-md-10 col-sm-12">
        <form id="form-register" method="POST" action="">
          <input type="hidden" name="act" value="write" />
          <div class="mb-3">
            <label for="subject" class="form-label">아이디 : </label>
            <input
              type="text"
              class="form-control"
              id="userId"
              name="userId"
              placeholder="admin"
              value="admin"
              readonly
            />
          </div>
          <div class="mb-3">
            <label for="subject" class="form-label">제목 : </label>
            <input
              type="text"
              class="form-control"
              id="subject"
              name="subject"
              v-model.lazy="notice.subject"
              placeholder="제목"
            />
          </div>
          <div class="mb-3">
            <label for="content" class="form-label">내용 : </label>
            <textarea
              class="form-control"
              id="content"
              name="content"
              rows="7"
              v-model.lazy="notice.content"
            >
            </textarea>
          </div>
          <div class="col-auto text-center">
            <button
              type="button"
              id="btn-register"
              class="btn btn-outline-primary mb-3"
              @click="regist(notice)"
            >
              QnA 작성
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
import { mapActions } from "vuex";
export default {
  data() {
    return {
      notice: {
        userId: "admin",
      },
    };
  },

  methods: {
    ...mapActions([Constant.REGIST_NOTICE]),
    regist(payload) {
      console.log(payload);
      this.registNotice(payload).then(() => {
        this.$router.push("/notice/list");
      });
    },
    changeForm() {
      this.$router.push("/notice/list");
    },
  },
};
</script>

<style scoped></style>
