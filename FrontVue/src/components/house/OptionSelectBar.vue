<template>
    <div>
    <div class="option">
        <b-button variant="outline-info" active pill :pressed.sync="polygon">서울 지역구 보기</b-button>
    </div>
    <div class="option2">
        <b-button-group>
            <b-button variant="outline-light" active pill :pressed.sync="cafe">카페</b-button>
            <b-button class="ml-3" variant="outline-success" active pill :pressed.sync="bank">은행</b-button>
            <b-button class="ml-3" variant="outline-danger" active pill :pressed.sync="hospital">병원</b-button>
            <b-button class="ml-3" variant="outline-primary" active pill :pressed.sync="theater">극장</b-button>
        </b-button-group>
    </div>
    </div>
</template>

        <!-- <b-col class="text-center text-white">
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
        </b-form-checkbox> -->
          <!-- <b-col>State: {{ status }}</b-col> -->
<script>
import Constant from "@/common/Constant.js";
import { mapMutations,mapGetters} from "vuex"
    export default {
        data() {
            return {
                cafe: false,
                bank: false,
                hospital: false,
                theater: false,
                polygon: false,
            }
        },
        computed: {
            ...mapGetters(["status"])
        },
        methods: {
            ...mapMutations([Constant.SET_STATUS,Constant.SET_LEVEL,Constant.SET_LATLNG]),
        },
        watch: {
            polygon(value) {
                console.log(value+"polygon changed");
                if(value){
                    this.setLevel(9);
                    this.setLatLng({lat:37.5642,lng:127.0016});
                }
                this.setStatus(value);
            },
            status(value){
                this.polygon = value;
            }
        },
    }
</script>

<style scoped>

.option{
  position: absolute;
  top : 5px;
  right: 24%;
  z-index: 1;
  background: rgba(10,10,10,0.8);
  padding : 6px;
  border-radius: 5px;
}
.option2{
  position: absolute;
  top : 5px;
  right: 10px;
  z-index: 1;
  background: rgba(10,10,10,0.8);
  padding : 6px;
  border-radius: 5px;
}
</style>