<template>
  <div id="map"></div>
</template>

<script>
import Constant from "@/common/Constant.js";
import {mapGetters,mapActions,mapMutations} from "vuex";
export default {
  data() {
    return {
      map: null,
      markers: [],
    };
  },

  computed: {
    ...mapGetters(["deals","LatLng","Level","apts","apt"]),
  },
  methods: {
    ...mapActions(["getApt"]),
    ...mapMutations([Constant.SET_SIDEBAR]),
    initMap() {
      const container = document.getElementById("map");
      const options = {
        center: new kakao.maps.LatLng(this.LatLng.Lat, this.LatLng.Lng),
        level: this.Level,
      };
      this.map = new kakao.maps.Map(container, options);

      kakao.maps.event.addListener(this.map,'click',()=>{
        this.setSidebar(false);
      });
    },

    updateMarkers(){
        this.removeMarkers();
        // this.deals.forEach(deal=>{
        //   let marker = new kakao.maps.Marker({position: new kakao.maps.LatLng(deal.houseInfo.lat,deal.houseInfo.lng)});
        //   marker.setMap(this.map);
        //   this.markers.push(marker);
        // })
        let maxLength = Object.values(this.apts).reduce((max,val)=> max> val.length ? max : val.length);
        this.level = Math.floor(maxLength/5);
        Object.keys(this.apts).forEach((aptCode)=>{
          this.makeMarker(aptCode);
        })
        // this.apts.forEach(apt=>{
        //   let marker = new kakao.maps.Marker({position: new kakao.maps.LatLng(apt.houseInfo.lat,apt.houseInfo.lng)});
        //   marker.setMap(this.map);
        //   this.markers.push(marker);
        // })
    },

    makeMarker(aptCode){
      const {houseInfo} = this.apts[aptCode][0];
      let section = Math.floor(this.apts[aptCode].length/this.level);
      if(isNaN(section)) section= 1;
      let className = `level`+(section+1);
      let info = this.createToast(houseInfo);
      let marker = new kakao.maps.CustomOverlay({
        map : this.map,
        position: new kakao.maps.LatLng(houseInfo.lat,houseInfo.lng),
        content : this.createOverlay(className,aptCode,this.apts[aptCode].length,info),
        clickable: true
      })
      this.map.setLevel(5);
      this.markers.push(marker);
    },

    createOverlay(className,aptCode,data,info){
      let div = document.createElement("div");
      div.classList.add('circle');
      div.classList.add(className);
      div.setAttribute('aptCode',aptCode);
      let text = document.createTextNode(data);
      div.appendChild(text);
      div.addEventListener('click',()=>{
        this.getApt(aptCode);
      })
      div.addEventListener('mouseover',()=>{
        info.setMap(this.map);
      });
      div.addEventListener('mouseout',()=>{
        info.setMap(null);
      })
      return div;
    },

    createToast(houseInfo){
      let content = '<div class="wrap">' + 
            '    <div class="info">' + 
            '        <div class="title">' + 
            `            ${houseInfo.aptName}` + 
            '        </div>' + 
            '        <div class="body">' + 
            '            <div class="desc">' + 
            `                <div class="ellipsis">상세 주소 : ${houseInfo.dongName} ${houseInfo.jibun}</div>` + 
            `                <div class="ellipsis">건축 연도 : ${houseInfo.buildYear}</div><br/>` + 
            '                <div class="jibun ellipsis">마커 클릭 시 상세 정보를 볼 수 있습니다.</div>' + 
            '            </div>' + 
            '        </div>' + 
            '    </div>' +    
            '</div>';
      let infoWindow = new kakao.maps.CustomOverlay({
        content,
        position : new kakao.maps.LatLng(houseInfo.lat,houseInfo.lng),
        yAnchor: -0.2
      })
      return infoWindow;

    },
    removeMarkers(){
      this.setMarkers(null);
    },

    setMarkers(map){
      this.markers.forEach(marker=>marker.setMap(map));
    },

   
  },

  mounted() {
    if (!window.kakao || !window.kakao.maps) {
      const script = document.createElement("script");
      script.src = `//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=${process.env.VUE_APP_MAP_KEY}&libraries=services`;
      /* global kakao */
      script.addEventListener("load", () => {
        kakao.maps.load(this.initMap);
      });
      document.head.appendChild(script);
    } else {
      this.initMap();
    }
  },

  watch: {
    deals() {
      // console.log('watched'+this.apts);
      if(this.deals.length==0){
        this.removeMarkers();
      }
      else{
        this.updateMarkers();
      }
    },
    LatLng(){
      this.map.setCenter(new kakao.maps.LatLng(this.LatLng.Lat,this.LatLng.Lng));
    },
    Level(){
      this.map.setLevel(this.Level);
    },
    apt(){
      this.makeMarker(this.apt.aptCode);
    }
  },
};
</script>

<style>
#map {
  width: 100%;
  height: 750px;
}

.circle{
  width: 50px;
  height: 50px;
  line-height: 50px;
  border-radius: 50%;
  font-size: 15px;
  color: #fff;
  text-align: center;
}

.level1{
  background-color: #304ffe

}

.level2{
  background-color: #2e7d32
}

.level3{
  background-color: #ffd600
}

.level4{
  background-color: #ff9100
}

.level6{
  background-color: #d50000
}

.wrap {position: absolute;left: 0;bottom: 30px;width: 288px;height: 132px;margin-left: -144px;text-align: left;overflow: hidden;font-size: 12px;font-family: 'Malgun Gothic', dotum, '돋움', sans-serif;line-height: 1.5;}
    .wrap * {padding: 0;margin: 0;}
    .wrap .info {width: 286px;height: 120px;border-radius: 5px;border-bottom: 2px solid #ccc;border-right: 1px solid #ccc;overflow: hidden;background: #fff;}
    .wrap .info:nth-child(1) {border: 0;box-shadow: 0px 1px 2px #888;}
    .info .title {padding: 5px 0 0 10px;height: 30px;background: #eee;border-bottom: 1px solid #ddd;font-size: 18px;font-weight: bold;}
    .info .close {position: absolute;top: 10px;right: 10px;color: #888;width: 17px;height: 17px;background: url('https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/overlay_close.png');}
    .info .close:hover {cursor: pointer;}
    .info .body {position: relative;overflow: hidden;}
    .info .desc {position: relative;margin: 13px 0 0 13px;height: 75px;}
    .desc .ellipsis {overflow: hidden;text-overflow: ellipsis;white-space: nowrap; font-size:14px}
    .desc .jibun {font-size: 13px;color: red;margin-top: -2px; text-align: center;}
    .info .img {position: absolute;top: 6px;left: 5px;width: 73px;height: 71px;border: 1px solid #ddd;color: #888;overflow: hidden;}
    .info:after {content: '';position: absolute;margin-left: -12px;left: 50%;bottom: 0;width: 22px;height: 12px;background: url('https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/vertex_white.png')}
    .info .link {color: #5085BB;}
</style>
