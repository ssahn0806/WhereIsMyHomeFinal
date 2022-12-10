<template>
  <div>
    <div class="option">
      <b-button variant="outline-warning" active pill @click="moveMap()" v-if="userInfo!=null && userInfo.favorloc!=null">관심 지역 보기</b-button>
      <b-button class="ml-1 mr-1" variant="outline-info" active pill :pressed.sync="polygon" 
        >서울 지역구 보기</b-button
      >
    </div>
    <div class="option2">
      <b-button-group>
        <b-button
          variant="outline-warning"
          active
          pill
          :pressed.sync="mark"
          >아파트</b-button
        >
        <b-button
          class="ml-3"
          variant="outline-light"
          active
          pill
          :pressed.sync="cafe"
          @click="getRegionCode('cafe')"
          >카페</b-button
        >
        <b-button
          class="ml-3"
          variant="outline-success"
          active
          pill
          :pressed.sync="bank"
          @click="getRegionCode('bank')"
          >은행</b-button
        >
        <b-button
          class="ml-3"
          variant="outline-danger"
          active
          pill
          :pressed.sync="hospital"
          @click="getRegionCode('hospital')"
          >병원</b-button
        >
        <b-button
          class="ml-3"
          variant="outline-primary"
          active
          pill
          :pressed.sync="theater"
          @click="getRegionCode('theater')"
          >극장</b-button
        >
      </b-button-group>
    </div>
  </div>
</template>

<script>
import Constant from "@/common/Constant.js";
import { mapState, mapMutations, mapGetters, mapActions } from "vuex";
const memberStore = "memberStore";
export default {
  data() {
    return {
      mark: false,
      cafe: false,
      bank: false,
      hospital: false,
      theater: false,
      polygon: false,
      geocoder: null,
      service: null,
      dongcode: "",
      points : []
    };
  },
  computed: {
    ...mapGetters(["status", "LatLng","apts","deals"]),
    ...mapState(memberStore, ["userInfo"]),
  },
  methods: {
    ...mapMutations([
      Constant.SET_STATUS,
      Constant.SET_LEVEL,
      Constant.SET_LATLNG,
      Constant.SET_BANKS,
      Constant.SET_HOSPITALS,
      Constant.SET_CAFES,
      Constant.SET_THEATERS,
      Constant.SET_OPENFAV,
      Constant.SET_DEALS,
      Constant.SET_MARKER
    ]),
    moveMap() {
      if(this.status){
        this.setStatus(false);
      }
      this.setOpenFav();
    },

    ...mapActions([Constant.GET_THEATERS, Constant.GET_CAFES]),
    initGeocoder() {
      this.geocoder = new kakao.maps.services.Geocoder();
      this.service = new kakao.maps.services.Places();
    },
    getRegionCode(type) {
      if (this[type]) {
        this.geocoder.coord2RegionCode(this.LatLng.Lng, this.LatLng.Lat, (res, status) => {
          if (status == kakao.maps.services.Status.OK) {
            let data = res[0];
            if (data.region_type === "B") {
              this.dongcode = data.code;
              switch (type) {
                case "bank": //BK9
                  this.getData(type, "BK9");
                  break;
                case "cafe": //Q12
                  this.getCafes(this.dongcode);
                  break;
                case "hospital": //HP8
                  this.getData(type, "HP8");
                  break;
                case "theater": //N03
                  this.getTheaters(this.dongcode);
                  break;
              }
            }
          }
        });
      } else {
        switch (type) {
          case "bank": //BK9
            this.setBanks([]);
            break;
          case "cafe": //Q12
            this.setCafes([]);
            break;
          case "hospital": //HP8
            this.setHospitals([]);
            break;
          case "theater": //N03
            this.setTheaters([]);
            break;
        }
      }
    },
    getData(type, code) {
      this.service.categorySearch(
        code,
        (res, status) => {
          if (status == kakao.maps.services.Status.OK) {
            if (type == "bank") {
              this.setBanks(res);
            } else {
              this.setHospitals(res);
            }
          }
        },
        {
          x: this.LatLng.Lng,
          y: this.LatLng.Lat,
        }
      );
    },
  },
  mounted() {
    if (!window.kakao || !window.kakao.maps) {
      const script = document.createElement("script");
      script.src = `//dapi.kakao.com/v2/maps/sdk.js?appkey=${process.env.VUE_APP_MAP_KEY}&libraries=services&autoload=false`;
      /* global kakao */
      script.addEventListener("load", () => {
        kakao.maps.load(this.initGeocoder);
      });
      document.head.appendChild(script);
    } else {
      this.initGeocoder();
    }
  },
  watch: {
    mark(value){
        if(!value){
            this.setMarker(false);
        }
        else{
            this.setMarker(true);
        }
    },
    apts(value){
        if(Object.keys(value)?.length){
            this.mark = true;
        }
        else{
            this.mark = false;
            this.cafe = false;
            this.theater =false;
            this.bank = false;
            this.hospital = false;
        }
    },
    polygon(value) {
      if (value) {
        this.mark = false;

        this.setLevel(9);
        this.setLatLng({ lat: 37.5642, lng: 127.0016 });
        this.setStatus(1);
      } else {
        if (this.status == 0 || this.status == 1) {
          this.setStatus(0);
        }
      }
    },
    status(value) {
      if (value == 1) {
        this.polygon = true;
        if (this.bank) {
          this.bank = false;
          this.setBanks([]);
        }
        if (this.theater) {
          this.theater = false;
          this.setTheaters([]);
        }
        if (this.hospital) {
          this.hospital = false;
          this.setHospitals([]);
        }
        if (this.cafe) {
          this.cafe = false;
          this.setCafes([]);
        }
      } else {
        this.polygon = false;
      }
    },
  },
};
</script>

<style scoped>
.option {
  position: absolute;
  top: 5px;
  right: 25%;
  z-index: 1;
  background: rgba(10, 10, 10, 0.8);
  padding: 6px;
  border-radius: 5px;
}
.option2 {
  position: absolute;
  top: 5px;
  right: 2px;
  z-index: 1;
  background: rgba(10, 10, 10, 0.8);
  padding: 6px;
  border-radius: 5px;
}
</style>
