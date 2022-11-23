<template>
    <div>
        <b-modal id="dealsModal" :visible="ToggleModal" title="검색된 아파트 목록" @cancel="setModal(false)" @ok="setModal(false)" @hide="setModal(false)">
            <div id="itemList">
                <house-item v-for="deal in itemForList" :key="deal.aptCode" :info="deal" :service="geocoder"></house-item>
            </div>
            <b-pagination
                :total-rows="deals.length"
                v-model="currentPage"
                :per-page="perPage"
                align="center"
                aria-controls="itemList"
            />
        </b-modal>
    </div>
</template>

<script>
import Constant from "@/common/Constant.js";
import HouseItem from "@/components/house/HouseItem.vue";
import {mapGetters,mapMutations} from 'vuex';
    export default {
        data() {
            return {
                deals: [],
                geocoder : null,
                currentPage: 1,
                perPage: 5,
            }
        },
        computed :{
            ...mapGetters(["dealsname","ToggleModal"]),
            totalRows(){
                return this.deals.length;
            },
            itemForList(){
                return this.deals.slice((this.currentPage-1)*this.perPage,this.currentPage*this.perPage);
            }
        },
        methods: {
            ...mapMutations([Constant.SET_MODAL]),
            initGeocoder(){
                this.geocoder = new kakao.maps.services.Geocoder();
            }
        },
        components: {
            HouseItem,
        },
        
        mounted() {
            if (!window.kakao || !window.kakao.maps) {
            const script = document.createElement("script");
            script.src = `//dapi.kakao.com/v2/maps/sdk.js?appkey=${process.env.VUE_APP_MAP_KEY}&libraries=services,clusterer&autoload=false`;
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
            dealsname() {
                this.deals = [];
                Object.keys(this.dealsname).forEach((aptCode)=>{
                    this.deals.push(this.dealsname[aptCode]);
                })
            },
            
        },
    }
</script>

<style scoped>

</style>