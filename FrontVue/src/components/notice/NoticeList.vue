<template>
  <div class="container" style="height: 40.8vh">
    <div>

      <table class="table w-100 mt-5">
        <caption>
          <div style="display:flex;justify-content:flex-end;">
            <input
              class="btn btn-success"
              type="button"
              value="등록"
              @click="changeForm"
              v-if="userInfo && userInfo.userid == admin"
            />
          </div>
        </caption>
        <thead class="thead-dark bg-success text-white">
          <tr>
            <td class="w-10" style="font-size:20px;font-weight:600;">번호</td>
            <td class="w-50" style="font-size:20px;font-weight:600;">제목</td>
            <td class="w-10" style="font-size:20px;font-weight:600;">조회수</td>
            <td class="w-10" style="font-size:20px;font-weight:600;">등록시간</td>
            <td></td>
          </tr>
        </thead>
        <tbody id="news">
          <tr
            v-for="notice in articles"
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

      <b-pagination
            class="pagination"
            v-model="currentIdx"
            :total-rows="this.notices.length"
            :per-page="newsPerPage"
            aria-controls="news"
          >
        </b-pagination>
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
      perPage: 5,
      currentPage: 1,
      currentIdx: 1,
      newsPerPage: 5,
      tabIdx: 0,
    };
  },
  computed: {
    ...mapGetters(["notices"]),
    ...mapState(memberStore, ["userInfo"]),

    rows() {
      return this.notices.length;
    },
    articles() {
      return this.notices.slice(
        (this.currentIdx - 1) * this.newsPerPage,
        this.currentIdx * this.newsPerPage
      );
    },
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

<style scoped>
.pagination{
    margin-top: 15px;
    display: flex;
    justify-content: center;
}
</style>
