<template>
  <b-container fluid>
    <b-row>
      <b-col class="sm-3">
        <b-form-select v-model="sidoCode" :options="sidoOpts" @change="gugunLoad"></b-form-select>
      </b-col>
      <b-col class="sm-3">
        <b-form-select v-model="gugunCode" :options="gugunOpts" @change="dongLoad"></b-form-select>
      </b-col>

      <b-col class="sm-3" v-if="dongOpts.length >= 2">
        <b-form-select v-model="sdong" :options="dongOpts"></b-form-select>
      </b-col>

      <!-- <b-col class="sm-3" v-if="!gugunCode">
        <b-form-group
          class="pt-2"
          horizontal
        >
          <b-form-checkbox v-model="ischecked" name="checkbox"></b-form-checkbox>
          <b-form-input v-model="searchValue" placeholder="아파트명을 입력하세요"></b-form-input> 
        
        </b-form-group>
      </b-col> -->
      <b-col class="sm-3">
       <b-button @click="searchApts">조회</b-button>
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
import restApi from "@/util/http-common";
import Constant from "@/common/Constant";
import { mapActions, mapMutations ,mapGetters} from "vuex";
export default {
  name: "HouseSearchBar",

  computed: {
    ...mapGetters(["ndong"]),
  },

  data() {
    return {
      sdong: {},
      ischecked: false,
      sidoCode: null,
      gugunCode: null,
      dongCode: null,
      sidoOpts: [],
      gugunOpts: [],
      dongOpts: [],
    };
  },

  methods: {
    ...mapActions([Constant.GET_DEALS,Constant.GET_LATLNG]),
    ...mapMutations([Constant.SET_LEVEL,Constant.SET_NDONG]),
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
      this.sdong = {};
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
      this.sDong = {};
      this.dongCode = null;
      this.dongOpts = [];
      this.dongOpts.push({ value: null, text: "동을 선택하세요." });
      if (this.gugunCode) {
        restApi.get(`/api/houses/dong/${this.gugunCode}`).then(({ data }) => {
          data.forEach((dong) => {
            this.dongOpts.push({ value: dong, text: dong.dongName });
          });
        });
      }
    },

    searchApts() {
      
      if (this.sdong.dongCode) {
        
        this.setNdong(this.sdong);
        console.log("ndong :"  +this.ndong.dongName +", " + this.ndong.dongCode);
        console.log(this.sdong.dongName);
        this.getDeals(this.sdong.dongCode);
        this.getLatLng(this.sdong.dongCode);
        this.setLevel(4);
      } else {
        this.getDeals(this.gugunCode.slice(0,5));
        this.getLatLng(this.gugunCode.slice(0,5));
        this.setLevel(11);
      }
    },
  },

  created() {
    this.sidoList();
  },
};
</script>

<style scoped></style>
