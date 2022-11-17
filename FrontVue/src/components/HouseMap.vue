<template>
  <div id="map"></div>
</template>

<script>
import { mapState } from "vuex";
export default {
  data() {
    return {
      map: null,
      markers: [],
    };
  },

  computed: {
    ...mapState(["apts"]),
  },
  methods: {
    initMap() {
      const container = document.getElementById("map");
      const options = {
        center: new kakao.maps.LatLng(37.498545, 127.062223),
        level: 7,
      };
      this.map = new kakao.maps.Map(container, options);
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
    apts() {
      console.log(this.apts);
    },
  },
};
</script>

<style scoped>
#map {
  width: 100%;
  height: 750px;
}
</style>
