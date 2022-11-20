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
    ...mapGetters(["deals","LatLng","Level","apts"]),
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
      //지도 생성
      this.map = new kakao.maps.Map(container, options);
      
      var polygonPath = [
        new kakao.maps.LatLng(37.485570527805734, 127.041345846097684),
        new kakao.maps.LatLng(37.48479724859385, 127.03396794290876),
        new kakao.maps.LatLng(37.485099256250635, 127.033777478523902),
        new kakao.maps.LatLng(37.48954007672873, 127.031623257520422),
        new kakao.maps.LatLng(37.492413584680669, 127.040888375285618),
        new kakao.maps.LatLng(37.494305112972256, 127.046692264025225),
        new kakao.maps.LatLng(37.489869250626548, 127.049043599534428),
        new kakao.maps.LatLng(37.489453203594884, 127.049261726321987),
        new kakao.maps.LatLng(37.489191206011675, 127.049399041786714),
        new kakao.maps.LatLng(37.48838037967024, 127.049823039172196),
        new kakao.maps.LatLng(37.487716969477404, 127.048379960577932),
        new kakao.maps.LatLng(37.487677229213425, 127.048008459737375),
        new kakao.maps.LatLng(37.487147898183736, 127.046747534871614),
        new kakao.maps.LatLng(37.486534992158688, 127.045393853867196),
        new kakao.maps.LatLng(37.48648448414103, 127.045282303064624),
        new kakao.maps.LatLng(37.486356667940882, 127.045374464330109),
        new kakao.maps.LatLng(37.486229283697035, 127.045035110613384),
        new kakao.maps.LatLng(37.485970400366497, 127.044225096387265),
        new kakao.maps.LatLng(37.485954430544524,127.044174292667293 ),
        new kakao.maps.LatLng(37.485897831890583,127.043814089922734 ),
        new kakao.maps.LatLng( 37.485570527805734, 127.041345846097684)
      ];

      // 지도에 표시할 다각형을 생성합니다
      var polygon = new kakao.maps.Polygon({
          path:polygonPath, // 그려질 다각형의 좌표 배열입니다
          strokeWeight: 3, // 선의 두께입니다
          strokeColor: '#39DE2A', // 선의 색깔입니다
          strokeOpacity: 0.8, // 선의 불투명도 입니다 1에서 0 사이의 값이며 0에 가까울수록 투명합니다
          strokeStyle: 'longdash', // 선의 스타일입니다
          fillColor: '#A2FF99', // 채우기 색깔입니다
          fillOpacity: 0.7 // 채우기 불투명도 입니다
      });

      // 지도에 다각형을 표시합니다
      polygon.setMap(this.map);


        
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
      let className = `level`+(section+1);
      let marker = new kakao.maps.CustomOverlay({
        map : this.map,
        position: new kakao.maps.LatLng(houseInfo.lat,houseInfo.lng),
        content : this.createOverlay(className,aptCode,this.apts[aptCode].length),
        clickable: true
      })
      this.map.setLevel(5);
      this.markers.push(marker);
    },

    createOverlay(className,aptCode,data){
      let div = document.createElement("div");
      div.classList.add('circle');
      div.classList.add(className);
      div.setAttribute('aptCode',aptCode);
      let text = document.createTextNode(data);
      div.appendChild(text);

      div.addEventListener('click',()=>{
        this.getApt(aptCode);
      });

      return div;
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
      script.src = `//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=${process.env.VUE_APP_MAP_KEY}`;
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
      this.updateMarkers();
    },
    LatLng(){
      this.map.setCenter(new kakao.maps.LatLng(this.LatLng.Lat,this.LatLng.Lng));
    },
    Level(){
      this.map.setLevel(this.Level);
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
</style>
