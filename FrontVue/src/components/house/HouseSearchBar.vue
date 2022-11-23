<template>
  <div class="con">
    <b-row class="box">
      <b-dropdown id="search-drop" :text="selectedText" variant="warning">
        <b-dropdown-item @click="changeOption(1)">법정동</b-dropdown-item>
        <b-dropdown-item @click="changeOption(2)">아파트</b-dropdown-item>
      </b-dropdown>
      <template v-if="selectedOpt == 1">
        <b-col lg="3">
          <b-form-select v-model="sidoCode" :options="sidoOpts" @change="gugunLoad"></b-form-select>
        </b-col>
        <b-col lg="3">
          <b-form-select
            v-model="gugunCode"
            :options="gugunOpts"
            @change="dongLoad"
          ></b-form-select>
        </b-col>
        <b-col lg="3">
          <b-form-select
            v-model="dongCode"
            :options="dongOpts"
            :disabled="dongOpts.length == 1"
          ></b-form-select>
        </b-col>
        <b-col>
          <b-button class="ml-1" @click="searchAptByCode" variant="warning">조회</b-button>
          <b-button
            class="ml-1"
            variant="warning"
            v-if="userInfo != null && userInfo.favorloc != null"
            :disabled="dongCode == null"
            @click="registFavDong('변경')"
            >관심지역 변경</b-button
          >
          <b-button
            class="ml-1"
            variant="warning"
            :disabled="dongCode == null"
            v-else-if="userInfo != null && userInfo.favorloc == null"
            @click="registFavDong('등록')"
            >관심지역 등록</b-button
          >
        </b-col>
      </template>
      <template v-else-if="selectedOpt == 2">
        <b-col lg="9">
          <b-form-input v-model="searchName" placeholder="아파트명을 입력하세요..."></b-form-input>
        </b-col>
        <b-col class="ml-8">
          <b-button @click="searchAptByName" variant="warning">거래매물 조회</b-button>
        </b-col>
      </template>
    </b-row>
  </div>
</template>

<script>
import restApi from "@/util/http-common";
import Constant from "@/common/Constant";
import { mapState, mapActions, mapMutations, mapGetters } from "vuex";
const memberStore = "memberStore";
export default {
  name: "HouseSearchBar",

  computed: {
    ...mapGetters(["ndong", "status", "openfav"]),
    ...mapState(memberStore, ["userInfo"]),
  },

  data() {
    return {
      selectedText: "검색조건",
      selectedOpt: 0,
      ischecked: false,
      sidoCode: null,
      gugunCode: null,
      dongCode: null,
      searchName: "",
      sidoOpts: [],
      gugunOpts: [],
      dongOpts: [],
    };
  },
  watch: {
    // userInfo(data) {
    //   if (data != null) {
    //     this.myInfo = data;
    //   } else {
    //     this.myInfo = {};
    //   }
    // },
    openfav() {
      //여기서  fav로
      this.selectedText = "법정동";
      this.selectedOpt = 1;
      this.sidoList();

      this.sidoCode = this.userInfo.favorloc.slice(0, 2).padEnd(10, "0");
      this.gugunLoad();
      this.gugunCode = this.userInfo.favorloc.slice(0, 5).padEnd(10, "0");
      this.dongLoad();
      this.dongCode = this.userInfo.favorloc;
      this.searchAptByCode();
    },
    status(value) {
      if (value == 1) {
        this.selectedText = "법정동";
        this.selectedOpt = 1;
        this.sidoList();
        this.sidoCode = "1100000000";
        this.gugunLoad();
      } else if (value != 0) {
        console.log(value);
        this.gugunCode = value + "00000";
        this.dongLoad();
        //동코드 주기
        //this.searchaptByCode()
      }
    },
  },
  methods: {
    ...mapActions([Constant.GET_DEALS, Constant.GET_DEALS_NAME, Constant.GET_LATLNG]),
    ...mapActions(memberStore,[Constant.UPDATE_FAV]),
    ...mapMutations([
      Constant.SET_LEVEL,
      Constant.SET_DEALS,
      Constant.SET_MODAL,
      Constant.SET_NDONG,
      Constant.SET_LATLNG,
      Constant.SET_STATUS,
      Constant.SET_APTS,
      Constant.SET_CAFES,
      Constant.SET_BANKS,
      Constant.SET_HOSPITALS,
      Constant.SET_THEATERS
    ]),
    sidoList() {
      this.sidoCode = null;
      this.gugunCode = null;
      this.dongCode = null;
      this.sidoOpts = [];
      this.gugunOpts = [];
      this.dongOpts = [];
      this.sidoOpts.push({ value: null, text: "시/도를 선택" });
      this.gugunOpts.push({ value: null, text: "구/군을 선택" });
      this.dongOpts.push({ value: null, text: "동을 선택" });
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
      this.gugunOpts.push({ value: null, text: "구/군을 선택" });
      this.dongOpts.push({ value: null, text: "동을 선택" });
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
      this.dongOpts.push({ value: null, text: "동을 선택" });
      if (this.gugunCode) {
        restApi.get(`/api/houses/dong/${this.gugunCode}`).then(({ data }) => {
          data.forEach((dong) => {
            this.dongOpts.push({ value: dong.dongCode, text: dong.dongName });
          });
        });
      }
    },

    searchAptByCode() {
      if (this.dongCode) {
        this.getDeals(this.dongCode);
        this.getLatLng(this.dongCode);
        this.setLevel(4);
      } else if (this.gugunCode) {
        this.getDeals(this.gugunCode.slice(0, 5));
        this.getLatLng(this.gugunCode.slice(0, 5));
        this.setLevel(9);
      } else {
        alert("조회할 법정동을 선택하세요");
      }
    },
    searchAptByName() {
      if (this.searchName != "") {
        this.getDealsName(this.searchName);
      } else {
        alert("검색할 아파트 이름을 입력하세요");
      }
    },
    changeOption(OptNo) {
      this.selectedOpt = OptNo;
      switch (this.selectedOpt) {
        case 1:
          this.selectedText = "법정동";
          this.sidoList();
          break;
        case 2:
          this.searchName = "";
          this.selectedText = "아파트";
          break;
      }
      this.setStatus(0);
      this.setDeals([]);
      this.setApts([]);
      this.setCafes([]);
      this.setBanks([]);
      this.setHospitals([]);
      this.setTheaters([]);
    },
    registFavDong(flag){
      console.log(this.userInfo,this.dongCode);
      this.updateFav({dongCode:this.dongCode,member:this.userInfo}).then(()=>{
          alert(`관심지역 ${flag}에 성공하였습니다.`);
          console.log(this.userInfo);
          // this.userInfo.favorloc = this.dongcode;
      });
    }
  },

  created() {
    this.sidoList();
  },
};
</script>

<style scoped>
.con {
  position: absolute;
  top: 5px;
  left: 10px;
  z-index: 1;
  background: rgba(0, 0, 0, 0.7);
  border-radius: 5px;
  width: 65%;
}
.box {
  padding: 6px;
  padding-left: 23px;
}
</style>
