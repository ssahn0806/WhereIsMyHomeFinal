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
