<template>
    <div>
        <b-sidebar id="sidebar-right" title="아파트 상세 정보" backdrop right shadow :visible="ToggleSidebar" @hidden="setSidebar(false)" width="350px">
            <div class="px-3 py-2">
                <b-overlay :show="loaded" rounded="sm" bg-color="dark" opacity="0.8">
                    <div id="roadview"></div>
                </b-overlay>
                <b-card 
                    bg-variant="dark"
                    text-variant="light"
                    :title="apt.aptName"
                    class="mt-1"
                >
                </b-card>
                <b-card bg-variant="dark" class="mt-2">
                    <b-card-text class="text-left text-white">상세 주소 : <b-badge variant="light" pill>{{apt.dongName}} {{apt.jibun}}</b-badge></b-card-text>
                    <b-card-text class="text-left text-white">건축 년도 : <b-badge variant="light" pill>{{apt.buildYear}}</b-badge></b-card-text>
                    <b-card-text class="text-left text-white">보유 평형 :
                        <b-badge pill :variant="color[index%color.length]" v-for="(area,index) in areas" :key="area">
                            {{area}}
                        </b-badge>
                    </b-card-text>
                </b-card>
                <div id="canvas" class="mt-2">
                    <canvas id="chart"></canvas>
                </div>
                <div class="mt-3">
                    <b-tabs fill v-model="tabIdx">
                        <b-tab title="거래내역">
                            <template>
                                <b-table 
                                    id="deal-table"
                                    striped 
                                    hover 
                                    :items="deals" 
                                    :fields="fields"
                                    :per-page="perPage"
                                    :current-page="currentPage">
                                    <template #cell(area)="data">
                                        {{Math.floor(data.item.area/3.3)}}
                                    </template>
                                </b-table>
                                <b-pagination
                                    class="pagination"
                                    v-model="currentPage"
                                    :total-rows="rows"
                                    :per-page="perPage"
                                    aria-controls="deal-table"
                                    pills
                                >
                                </b-pagination>
                            </template>
                        </b-tab>
                        <b-tab :title="`${this.ndong.dongName} 소식`">          
                            <template >
                                <div id="news">
                                    <b-card v-for="article in articles" :key="article.link">
                                        <b-card-sub-title v-html="article.title" @click="openEmbed(article.link)"></b-card-sub-title>
                                        <!-- <b-card v-html="article.description" @click="openwin(newa.link)"></b-card> -->
                                    </b-card>
                                </div>
                                <b-pagination
                                    class="pagination"
                                    v-model="currentIdx"
                                    :total-rows="this.news.length"
                                    :per-page="newsPerPage"
                                    aria-controls="news"
                                    pills
                                >
                                </b-pagination>
                            </template>
                        </b-tab>
                    </b-tabs>
                </div>
                
                <!-- <div id="itemList">
                <house-item v-for="deal in itemForList" :key="deal.aptCode" :info="deal" :service="geocoder"></house-item>
            </div>
            <b-pagination
                :total-rows="deals.length"
                v-model="currentPage"
                :per-page="perPage"
                align="center"
                aria-controls="itemList"
            /> -->

            </div>
        </b-sidebar>
    </div>
</template>

<script>
import Constant from "@/common/Constant";
import Chart from 'chart.js/auto';
import {mapGetters,mapMutations,mapActions} from 'vuex';
export default {

       name: "HouseSideBar",
       data() {
        return {
            roadview: null,
            roadviewClient: null,
            loaded: false,
            color : ["primary","success","warning","info","danger","light"],
            chart: null,
            deals: [],
            areas : [],
            fields: [
                {
                    key: 'floor',
                    label: '층',
                    sortable: true
                },
                {
                    key: 'area',
                    label: '평수',
                    sortable: true
                },
                {
                    key: 'dealYear',
                    label: '년도',
                    sortable: true,
                },
                {
                    key: 'dealMonth',
                    label: '월',
                    sortable: true,
                },
                {
                    key: 'dealAmount',
                    label: '금액',
                    sortable: true
                }
            ],
            perPage : 10,
            currentPage: 1,
            currentIdx: 1,
            newsPerPage : 3,
            tabIdx: 0,
        }
       },
       methods: {
            ...mapMutations([Constant.SET_SIDEBAR,Constant.SET_EMBED,Constant.SET_URL]),
            ...mapActions([Constant.GET_NEWS]),
            initRoadView(){
                let roadviewContainer = document.getElementById('roadview');
                this.roadview = new kakao.maps.Roadview(roadviewContainer);
                this.roadviewClient = new kakao.maps.RoadviewClient();  
            },

            updateRoadView(){
                if(!this.ToggleSidebar){
                    this.currentPage = 1;
                    this.setSidebar(true);
                }
                this.loaded = true;
                let position = new kakao.maps.LatLng(this.apt.lat,this.apt.lng);
                this.roadviewClient.getNearestPanoId(position, 150, (panoId)=> {
                    this.roadview.setPanoId(panoId, position); //panoId와 중심좌표를 통해 로드뷰 실행
                    setTimeout(() => {
                        this.loaded = false;
                    }, 500);
                });
                
            },

            drawChart(){
                if(this.chart)
                    this.chart.destroy();
                let canvas = document.getElementById("chart");
                this.chart = new Chart(canvas,{
                    type : "line",
                    data : {
                        labels : this.deals.map(deal=>deal.dealYear+"."+deal.dealMonth),
                        datasets : [{
                            label : '실거래 변동 추이(만원)',
                            data : this.deals.map(deal=>parseInt(deal.dealAmount.trim().replace(/,/g,''))),
                            fill : false,
                            borderColor : '#e63946',
                            tension : 0.05
                        }]
                    },
                    options:{
                        maintainAspectRatio: false,
                    }
                })
            },

            sortData(){
                this.deals = this.apts[this.apt.aptCode].sort((a,b)=> {
                    if(a.dealYear>b.dealYear) return 1;
                    if(a.dealYear<b.dealYear) return -1;

                    if(a.dealMonth>b.dealMonth) return 1;
                    if(a.dealMonth<b.dealMonth) return -1;

                    if(a.dealDay>b.dealDay) return 1;
                    if(a.dealDay<b.dealDay) return -1;
                    return 0;
                });
                
                this.areas = Object.keys(this.deals.reduce((acc, curr) => {
                    const { area } = curr;
                    if (acc[Math.floor(area/3.31)]) acc[Math.floor(area/3.31)].push(curr);
                    else acc[Math.floor(area/3.31)] = [curr];
                    return acc;
                }, {})).sort();
            },

            printAreas(){
                let str = ''
                this.areas.forEach(area=>{
                    str += area+','
                })
                str = str.slice(0,-1);
                return str;
           },
           getLocalNews(dongName) {
               this.getNews(dongName);
           },
           openEmbed(link) {
                // window.open(link);
                this.setUrl(link);
                this.setEmbed(true);
           },
        },
        
    computed: {  
            ...mapGetters(["ToggleSidebar","apt","apts","news","ndong"]),
            rows(){
                return this.deals.length;
            },
            articles(){
                return this.news.slice((this.currentIdx-1)*this.newsPerPage,this.currentIdx*this.newsPerPage);
            }
        },

        mounted() {
            if (!window.kakao || !window.kakao.maps) {
            const script = document.createElement("script");
            script.src = `//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=${process.env.VUE_APP_MAP_KEY}&libraries=services,clusterer`;
            /* global kakao */
            script.addEventListener("load", () => {
                kakao.maps.load(this.initRoadView);
            });
            document.head.appendChild(script);
            } else {
                this.initRoadView();
            }
        },

       watch: {
        apt() {
            this.updateRoadView();
            this.sortData();
            this.drawChart();
            this.getLocalNews(this.apt.dongName);
            this.tabIdx = 0;
            this.currentIdx = 1;
        }
       },
    }
</script>

<style scoped>
#roadview{
    width: 100%;
    height: 200px;
}

#canvas{
    background-color: rgba(0.9,0.9,0.9,0.1);
    height: 250px;
    /* width: 100%; */
}

.pagination{
    margin-top: 3px;
    display: flex;
    justify-content: center;
}

</style>