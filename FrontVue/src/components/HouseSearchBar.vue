<template>
  <b-container class="sm-6">
    <b-row>
      <b-col class="sm-3">
        <b-form-select v-model="sidoCode" :options="sidoOpts" @change="gugunLoad"></b-form-select>
      </b-col>
      <b-col class="sm-3">
        <b-form-select v-model="gugunCode" :options="gugunOpts" @change="dongLoad"></b-form-select>
      </b-col>

      <b-col class="sm-3" v-if="dongOpts.length >= 2">
        <b-form-select v-model="dongCode" :options="dongOpts"></b-form-select>
      </b-col>

      <b-button @click="searchApts">조회</b-button>
    </b-row>
  </b-container>
</template>

<script>
import restApi from "@/util/http-common";
import Constant from "@/common/Constant";
import { mapActions } from "vuex";
export default {
  name: "HouseSearchBar",
  data() {
    return {
      sidoCode: null,
      gugunCode: null,
      dongCode: null,
      sidoOpts: [],
      gugunOpts: [],
      dongOpts: [],
    };
  },

  methods: {
    ...mapActions([Constant.GET_APTS]),
    sidoList() {
      this.sidoOpts.push({ value: null, text: "시도를 선택하세요." });
      this.gugunOpts.push({ value: null, text: "구/군을 선택하세요." });

      restApi.get("/api/houses/list").then(({ data }) => {
        data.forEach((sido) => {
          this.sidoOpts.push({ value: sido.sidoCode, text: sido.sidoName });
        });
      });
    },

    gugunLoad() {
      this.dongCode = null;
      this.gugunCode = null;
      this.gugunOpts = [];
      this.gugunOpts.push({ value: null, text: "구/군을 선택하세요." });
      if (this.sidoCode) {
        restApi.get(`/api/houses/gugun/${this.sidoCode}`).then(({ data }) => {
          data.forEach((gugun) => {
            this.gugunOpts.push({ value: gugun.gugunCode, text: gugun.gugunName });
          });
        });
      }
    },

    dongLoad() {
      this.dongCode = null;
      this.dongOpts = [];
      this.dongOpts.push({ value: null, text: "동을 선택하세요." });
      if (this.gugunCode) {
        restApi.get(`/api/houses/dong/${this.gugunCode}`).then(({ data }) => {
          data.forEach((dong) => {
            this.dongOpts.push({ value: dong.dongCode, text: dong.dongName });
          });
        });
      }
    },

    searchApts() {
      if (this.dongCode) {
        this.getApts(this.dongCode);
      } else {
        this.getApts(this.gugunCode.slice(0, 5));
      }
    },
  },

  created() {
    this.sidoList();
  },
};
</script>

<style scoped></style>
