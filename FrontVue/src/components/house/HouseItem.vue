<template>
    <div>
        <b-card bg-variant="light" text-variant="black" :header="houseInfo.aptName" class="text-center" @click="showDetail">
            <b-row>
                <h5>매물 건수 : {{info.length}}      </h5>
                <h5 class="ml-3">  건축 연도 : {{houseInfo.buildYear}}</h5>
            </b-row>
            <b-row>
                <h5>보유 평형 :
                    <b-badge pill :variant="color[index%color.length]" v-for="(area,index) in areas" :key="area">
                        {{area}}
                    </b-badge>
                </h5>
            </b-row>
            <b-row>
                <h5>도로명 주소 : {{this.address}}</h5>
            </b-row>
        </b-card>
    </div>
</template>

<script>
import {mapActions,mapMutations} from 'vuex';
    export default {
        data() {
            return {
                houseInfo:{},
                areas : [],
                color : ["primary","success","warning","info","danger","light"],
                address : "",
            }
        },
        props : ["info","service"],
        methods: {
            ...mapActions(["getApt"]),
            ...mapMutations(["setLatLng","setSidebar","setModal","setLevel","setApts"]),
            getAddress() {
                /* global kakao */
                this.service.coord2Address(this.houseInfo.lng,this.houseInfo.lat,(res,status)=>{
                    if(status==kakao.maps.services.Status.OK){
                        this.address = res[0].address.address_name;
                    }
                })
            },
            showDetail(){
                this.setApts(this.info);
                this.setLatLng({lat:this.houseInfo.lat,lng:this.houseInfo.lng});
                this.setSidebar(true);
                this.setModal(false);
                this.getApt(this.houseInfo.aptCode);
                this.setLevel(4);
            },
            setData(){
                this.houseInfo = this.info[0]['houseInfo'];
                this.areas = Object.keys(this.info.reduce((acc, curr) => {
                    const { area } = curr;
                    if (acc[Math.floor(area/3.31)]) acc[Math.floor(area/3.31)].push(curr);
                    else acc[Math.floor(area/3.31)] = [curr];
                    return acc;
                }, {})).sort();
                this.getAddress();
            }
        },
        
        created () {
            this.setData();
        },

        watch: {
            info() {
                this.setData();
            },
        }
    }
</script>

<style scoped>
</style>