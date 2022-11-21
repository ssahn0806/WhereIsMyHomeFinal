<template>
  <b-container fluid>
    <b-row>
      <b-dropdown id="search-drop" :text="selectedText">
        <b-dropdown-item @click="changeOption(1)">법정동명으로 검색</b-dropdown-item>
        <b-dropdown-item @click="changeOption(2)">아파트명으로 검색</b-dropdown-item>
      </b-dropdown>
      <template v-if="selectedOpt == 1">
        <b-col class="sm-3">
          <b-form-select v-model="sidoCode" :options="sidoOpts" @change="gugunLoad"></b-form-select>
        </b-col>
        <b-col class="sm-3">
          <b-form-select v-model="gugunCode" :options="gugunOpts" @change="dongLoad"></b-form-select>
        </b-col>

        <b-col class="sm-3" v-if="dongOpts.length >= 1">
          <b-form-select v-model="dongCode" :options="dongOpts"></b-form-select>

        </b-col>
        <b-col class="sm-3">
        <b-button @click="searchAptByCode">조회</b-button>
        </b-col>
        <b-col class="text-center">
          서울 구 표시
        </b-col>
          <b-form-checkbox
            id="checkbox-1"
            v-model="status"
            name="checkbox-1"
            value="checked"
            unchecked-value="not_checked"
            @change="showPoly"
          >
          </b-form-checkbox>
          <!-- <b-col>State: {{ status }}</b-col> -->
    </template>
      <template v-else-if="selectedOpt == 2">
        <b-col class="sm-3">
          <b-form-input v-model="searchName" placeholder="아파트명을 입력하세요"></b-form-input>             
        </b-col>
        <b-col class="sm-3">
          <b-button @click="searchAptByName">조회</b-button>
        </b-col>
      </template>


    </b-row>
  </b-container>
</template>

<script>
import restApi from "@/util/http-common";
import Constant from "@/common/Constant";
import { mapActions, mapMutations, mapGetters } from "vuex";
export default {
  name: "HouseSearchBar",

  computed: {
    ...mapGetters(["ndong", "status"]),
  },

  data() {
    return {
      selectedText : "검색 조건을 선택하세요",
      selectedOpt : 0,
      ischecked: false,
      sidoCode: null,
      gugunCode: null,
      dongCode: null,
      searchName : "",
      sidoOpts: [],
      gugunOpts: [],
      dongOpts: [],
    };
  },

  methods: {
    ...mapActions([Constant.GET_DEALS,Constant.GET_DEALS_NAME,Constant.GET_LATLNG]),
    ...mapMutations([Constant.SET_LEVEL,Constant.SET_DEALS,Constant.SET_MODAL,Constant.SET_NDONG,Constant.SET_STATUS,Constant.SET_LATLNG,Constant.SET_STATUS]),
    sidoList() {
      this.sidoCode = null;
      this.gugunCode = null;
      this.dongCode = null;
      this.sidoOpts = [];
      this.gugunOpts = [];
      this.dongOpts = [];
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
      this.dongOpts = [];
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

    searchAptByCode() {
      if(this.dongCode){
        this.getDeals(this.dongCode);
        this.getLatLng(this.dongCode);
        this.setLevel(4);
      } else if(this.gugunCode){
        this.getDeals(this.gugunCode.slice(0,5));
        this.getLatLng(this.gugunCode.slice(0,5));
        this.setLevel(9);
      } else {
        alert('조회할 동네를 선택하세요');
      }
    }
    ,
    searchAptByName(){
      if(this.searchName!=""){
        this.getDealsName(this.searchName);
      }
      else{
        alert('검색할 아파트 이름을 입력하세요');
      }
    },
    changeOption(OptNo){
      this.selectedOpt = OptNo;
      switch(this.selectedOpt){
        case 1:
          this.selectedText = "법정동명으로 검색";
          this.sidoList();
          break;
        case 2:
          this.searchName = "";
          this.selectedText = "아파트명으로 검색";
          break;
      }
      this.setDeals([]);
      this.setStatus("not_checked");
    },
    showPoly(status) {
      console.log("showpoly",status);
      if(status=="checked"){
        this.setLevel(9);
        this.setLatLng({lat:37.5642,lng:127.0016});
      }
      this.setStatus(status);
    },
  },

  created() {
    this.sidoList();
  },
};
</script>

<style scoped></style>
