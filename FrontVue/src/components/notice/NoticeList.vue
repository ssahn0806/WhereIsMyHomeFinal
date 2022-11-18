<template>
  <div class="container" style="height: 40.8vh">
    <div>
      <table class="table w-100 mt-5">
        <thead class="thead-dark bg-secondary text-white">
          <tr>
            <td class="w-10">번호</td>
            <td class="w-50">제목</td>
            <td class="w-10">조회수</td>
            <td class="w-10">등록시간</td>
            <td></td>
          </tr>
        </thead>
        <tbody>
          <tr
            v-for="notice in notices"
            :key="notice.articleNo"
            @click="pickNotice(notice.articleNo)"
          >
            <th scope="row">{{ notice.articleNo }}</th>
            <td>
              {{ notice.subject }}
            </td>
            <td>{{ notice.hit }}</td>
            <td>{{ notice.registerTime }}</td>
          </tr>
        </tbody>
      </table>
      <!--
      <c:if test="${userlevel eq 'admin'}">
        </c:if>
      -->
      <div class="mt-5">
        <input class="btn btn-primary" type="button" value="등록" @click="changeForm" />
      </div>
    </div>
  </div>
</template>

<script>
import Constant from "@/common/Constant.js";
import { mapActions, mapGetters } from "vuex";
export default {
  computed: {
    // notices() {
    //   return this.$store.state.notices;
    // },
    ...mapGetters(["notices"]),
  },

  methods: {
    ...mapActions([Constant.GET_NOTICES]),

    pickNotice(articleNo) {
      console.log(articleNo);
      this.$router.push(`/notice/detail/${articleNo}`);
    },

    changeForm() {
      this.$router.push("/notice/registForm");
    },
  },
  created() {
    this.getNotices();
  },

  watch: {
    $route(to) {
      if (to.path == "/notice" || to.path == "/notice/list") {
        this.getNotices();
      }
    },
  },
};
</script>

<style scoped></style>
