<template>
  <div id="map"></div>
</template>

<script>
import {mapGetters} from "vuex";
export default {
  data() {
    return {
      map: null,
      markers: [],
      set: new Set()
    };
  },

  computed: {
    ...mapGetters(["deals","LatLng","apts"]),
  },
  methods: {
    initMap() {
      const container = document.getElementById("map");
      const options = {
        center: new kakao.maps.LatLng(this.LatLng.Lat, this.LatLng.Lng),
        level: 7,
      };
      this.map = new kakao.maps.Map(container, options);
    },

    updateMarkers(){
        this.removeMarkers();
        // this.deals.forEach(deal=>{
        //   let marker = new kakao.maps.Marker({position: new kakao.maps.LatLng(deal.houseInfo.lat,deal.houseInfo.lng)});
        //   marker.setMap(this.map);
        //   this.markers.push(marker);
        // })
        console.log(this.apts);
        Object.keys(this.apts).forEach((aptCode)=>{
          const {houseInfo} = this.apts[aptCode][0];
          let marker = new kakao.maps.CustomOverlay({
            map : this.map,
            position: new kakao.maps.LatLng(houseInfo.lat,houseInfo.lng),
            content: `<div class="circle">${this.apts[aptCode].length}</div>`
          })
          this.markers.push(marker);
        })
        // this.apts.forEach(apt=>{
        //   let marker = new kakao.maps.Marker({position: new kakao.maps.LatLng(apt.houseInfo.lat,apt.houseInfo.lng)});
        //   marker.setMap(this.map);
        //   this.markers.push(marker);
        // })
    },

    removeMarkers(){
      this.setMarkers(null);
    },

    setMarkers(map){
      this.markers.forEach(marker=>marker.setMap(map));
    }
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
  background-color: #55947a
}
</style>
