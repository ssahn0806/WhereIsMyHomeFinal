<template>
    <div>
        <b-sidebar id="sidebar-right" title="아파트 상세 정보" backdrop right shadow :visible="ToggleSidebar" @hidden="setSidebar(false)" width="350px">
            <div class="px-3 py-2">
                <b-overlay :show="loaded" rounded="sm" bg-color="dark" opacity="0.8">
                    <div id="roadview"></div>
                </b-overlay>
                <br/>
                <b-card 
                    bg-variant="dark"
                    text-variant="light"
                    :title="apt.aptName"
                >
                    <b-card-body>
                        <b-card-text class="text-left">상세 주소 : <b-badge variant="light">{{apt.dongName}} {{apt.jibun}}</b-badge></b-card-text>
                        <b-card-text class="text-left">건축 년도 : <b-badge variant="secondary">{{apt.buildYear}}</b-badge></b-card-text>
                        <b-card-text class="text-left">보유 평형 :
                            <b-badge pill :variant="color[index%color.length]" v-for="(area,index) in areas" :key="area">
                                {{area}}
                            </b-badge>
                        </b-card-text>
                    </b-card-body>
                </b-card>
                <br/>
                <div id="canvas">
                    <canvas id="chart"></canvas>
                </div>
                <div>
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
                        id="pagination"
                        v-model="currentPage"
                        :total-rows="rows"
                        :per-page="perPage"
                        aria-controls="deal-table"
                        pills
                    >
                    </b-pagination>
                </div>
                
      
                <div>지역 뉴스
                    <b-button v-b-toggle.collapse-1 variant="primary" @click="getLocalNews()">∇</b-button>
                    <b-collapse id="collapse-1" class="mt-2">
                        <b-card>
                        <p class="card-text">{{this.ndong.dongNmae}} 지역 뉴스</p>
                        <tr v-for="newa in news.result" :key="newa.link">
                       
                            <b-card v-html="newa.title" @click="openwin(newa.link)"></b-card>
                            <b-card v-html="newa.description" @click="openwin(newa.link)"></b-card>

                       
                        </tr>
                        </b-card>
                    </b-collapse>
                </div>
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
            currentPage: 1
        }
       },
       methods: {
            ...mapMutations([Constant.SET_SIDEBAR]),
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
           getLocalNews() {
               this.getNews(this.ndong.dongName);
           },
           openwin(link) {
          window.open(link);
          },
        },
        
    computed: {
        
            ...mapGetters(["ToggleSidebar","apt","apts","news","ndong"]),
            rows(){
                return this.deals.length;
            }
        },

        mounted() {
            if (!window.kakao || !window.kakao.maps) {
            const script = document.createElement("script");
            script.src = `//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=${process.env.VUE_APP_MAP_KEY}&libraries=services`;
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

#pagination{
    display: flex;
    justify-content: center;
}

</style>