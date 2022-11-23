<template>
  <div id="map_wrap">
    <div id="map"></div>
    <house-search-bar></house-search-bar>
    <option-select-bar></option-select-bar>
  </div>
</template>

<script>
import Constant from "@/common/Constant.js";
import OptionSelectBar from "@/components/house/OptionSelectBar.vue";
import HouseSearchBar from "@/components/house/HouseSearchBar.vue";
import { mapGetters, mapActions, mapMutations } from "vuex";
//import geo from "@/assets/ab.json";
import geo from "@/assets/seoul_gu.json";
export default {
  data() {
    return {
      map: null,
      markers: [],
      polygons: [],
      guName: [],
      guCode: [],
      cafeMarkers: [],
      theaterMarkers: [],
      hospitalMarkers: [],
      bankMarkers: [],
      openedOverlay: new Set(),
    };
  },

  computed: {
    ...mapGetters([
      "deals",
      "LatLng",
      "Level",
      "apts",
      "status",
      "apt",
      "ToggleSidebar",
      "cafes",
      "theaters",
      "hospitals",
      "banks",
    ]),
  },

  components: {
    OptionSelectBar,
    HouseSearchBar,
  },
  methods: {
    ...mapActions(["getApt", Constant.GET_DEALS, Constant.GET_DEALS_NAME]),
    ...mapMutations([
      Constant.SET_SIDEBAR,
      Constant.SET_STATUS,
      Constant.SET_MODAL,
      Constant.SET_LATLNG,
      Constant.SET_DEALS,
      Constant.SET_NDONG,
      Constant.SET_LEVEL,
      Constant.SET_URL,
      Constant.SET_EMBED,
    ]),

    createPolygons() {
      let data = geo.features;
      let coordinates = []; //좌표 저장 배열

      //var infowindow = new kakao.maps.InfoWindow({ removable: true });
      const displayArea = (coordinates, name, code) => {
        let customOverlay = new kakao.maps.CustomOverlay({});
        let path = [];
        let points = [];
        //let areaResult = pollution.filter((item) => item[0] === name); //없어도 됨

        coordinates[0].forEach((coordinate) => {
          let point = {};
          ///console.log(coordinate);
          point.x = coordinate[1];
          point.y = coordinate[0];
          // console.log(point.x, point.y);
          points.push(point);
          path.push(new kakao.maps.LatLng(coordinate[1], coordinate[0]));
        });
        let polygon = new kakao.maps.Polygon({
          // map: this.map,
          path: path, // 그려질 다각형의 좌표 배열입니다
          strokeWeight: 2, // 선의 두께입니다
          strokeColor: "#004c80", // 선의 색깔입니다
          strokeOpacity: 0.8, // 선의 불투명도 입니다 1에서 0 사이의 값이며 0에 가까울수록 투명합니다
          strokeStyle: "solid", // 선의 스타일입니다
          fillColor: "#fff", // 채우기 색깔입니다
          fillOpacity: 0.7, // 채우기 불투명도 입니다
        });

        this.polygons.push(polygon);
        //   polygon.setMap(this.map);

        kakao.maps.event.addListener(polygon, "mouseover", () => {
          polygon.setOptions({
            fillColor: "#09f",
          });
          // console.log("mouseover :" + name);
          // customOverlay.setContent('<div class="area">' + name + '</div>');
          // customOverlay.setPosition(mouseEvent.latLng);
          // customOverlay.setMap(this.map);

          // const content = '<div style="padding:2px;"><p><b>' + name + '</div>';
          // console.log("위에들어옴");
          // console.log("status :" + this.status);
          // infowindow.setContent(content);
          // infowindow.setPosition(mouseEvent.latLng);
          // infowindow.setMap(this.map);
          customOverlay.setContent('<div class="name">' + name + "</div>");
          // customOverlay.setZIndex(3);
          // console.log(centroid(points));
          customOverlay.setPosition(centroid(points));
          customOverlay.setMap(this.map);
        });

        // // 다각형에 mousemove 이벤트를 등록하고 이벤트가 발생하면 커스텀 오버레이의 위치를 변경합니다
        kakao.maps.event.addListener(polygon, "mousemove", function (mouseEvent) {
          customOverlay.setPosition(mouseEvent.latLng);
        });

        // 다각형에 mouseout 이벤트를 등록하고 이벤트가 발생하면 폴리곤의 채움색을 원래색으로 변경합니다
        // 커스텀 오버레이를 지도에서 제거합니다
        kakao.maps.event.addListener(polygon, "mouseout", function () {
          polygon.setOptions({ fillColor: "#fff" });
          customOverlay.setMap(null);
        });

        // 다각형에 click 이벤트를 등록하고 이벤트가 발생하면 다각형의 이름과 면적을 인포윈도우에 표시합니다
        // kakao.maps.event.addListener(polygon, "click", function (mouseEvent) {

        // });

        function centroid(points) {
          var i, j, len, p1, p2, f, area, x, y;

          area = x = y = 0;

          for (i = 0, len = points.length, j = len - 1; i < len; j = i++) {
            p1 = points[i];
            p2 = points[j];

            f = p1.y * p2.x - p2.y * p1.x;
            x += (p1.x + p2.x) * f;
            y += (p1.y + p2.y) * f;
            area += f * 3;
          }
          return new kakao.maps.LatLng(x / area, y / area);
        }
        kakao.maps.event.addListener(polygon, "click", () => {
          //     var content = '<div class="info">' +
          //               '   <div class="title">' + name + '</div>' +
          //               '   <div class="size">총 면적 : 약 ' + Math.floor(polygon.getArea()) + ' m<sup>2</sup></div>' +
          //               '</div>';

          //   infowindow.setContent(content);
          //  // infowindow.setPosition(mouseEvent.latLng);
          //   infowindow.setMap(this.map);

          polygon.setOptions({ fillColor: "#fff" });
          // 현재 지도 레벨에서 2레벨 확대한 레벨
          console.log("밑에 들어옴");
          var level = this.map.getLevel() - 2;

          // 지도를 클릭된 폴리곤의 중앙 위치를 기준으로 확대합니다
          this.map.setLevel(level, {
            anchor: centroid(points),
            animate: {
              duration: 500, //확대 애니메이션 시간
            },
          });
          console.log("codecode", code);
          this.setStatus(parseInt(code));
          customOverlay.setMap(null);
          setTimeout(() => {
            this.getDeals(code.slice(0, 5));
          }, 500);

          //this.removePolygons(this.polygons);
          //deletePolygon(polygons);                    //폴리곤 제거
        });
      };

      data.forEach((val) => {
        coordinates = val.geometry.coordinates;
        var name = val.properties.name;
        var code = val.properties.code;
        this.guName.push(name);
        this.guCode.push(code);
        displayArea(coordinates, name, code);
      });
    },
    initMap() {
      const container = document.getElementById("map");
      const options = {
        center: new kakao.maps.LatLng(this.LatLng.Lat, this.LatLng.Lng),
        level: 7,
      };
      //지도 생성
      this.map = new kakao.maps.Map(container, options);
      this.createPolygons();
      this.setStatus(0);
      kakao.maps.event.addListener(this.map, "click", () => {
        this.setSidebar(false);
      });
      kakao.maps.event.addListener(this.map, "zoom_changed", () => {
        this.setLevel(this.map.getLevel());
      });
      kakao.maps.event.addListener(this.map, "center_changed", () => {
        let center = this.map.getCenter();
        this.setLatLng({ lat: center.getLat(), lng: center.getLng() });
      });
    },

    updateMarkers() {
      this.removeMarkers();
      // this.deals.forEach(deal=>{
      //   let marker = new kakao.maps.Marker({position: new kakao.maps.LatLng(deal.houseInfo.lat,deal.houseInfo.lng)});
      //   marker.setMap(this.map);
      //   this.markers.push(marker);
      // })
      let maxLength = Object.values(this.apts).reduce((max, val) =>
        max > val.length ? max : val.length
      );
      this.level = Math.floor(maxLength / 5);
      Object.keys(this.apts).forEach((aptCode) => {
        this.makeMarker(aptCode);
      });
      // this.apts.forEach(apt=>{
      //   let marker = new kakao.maps.Marker({position: new kakao.maps.LatLng(apt.houseInfo.lat,apt.houseInfo.lng)});
      //   marker.setMap(this.map);
      //   this.markers.push(marker);
      // })
    },

    makeMarker(aptCode) {
      const { houseInfo } = this.apts[aptCode][0];
      let section = Math.floor(this.apts[aptCode].length / this.level);
      if (isNaN(section)) {
        section = 1;
      }
      let className = `level` + (section + 1);
      let info = this.createToast(houseInfo);
      let marker = new kakao.maps.CustomOverlay({
        map: this.map,
        position: new kakao.maps.LatLng(houseInfo.lat, houseInfo.lng),
        content: this.createOverlay(className, aptCode, this.apts[aptCode].length, info),
        clickable: true,
      });
      this.map.setLevel(6);
      this.markers.push(marker);
    },

    createOverlay(className, aptCode, data, info) {
      let div = document.createElement("div");
      div.classList.add("circle");
      div.classList.add(className);
      div.setAttribute("aptCode", aptCode);
      let text = document.createTextNode(data);
      div.appendChild(text);

      div.addEventListener("click", () => {
        this.getApt(aptCode);
        info.setMap(null);
      });
      div.addEventListener("mouseover", () => {
        info.setMap(this.map);
      });
      div.addEventListener("mouseout", () => {
        info.setMap(null);
      });
      return div;
    },

    createToast(houseInfo) {
      let content =
        '<div class="wrap">' +
        '    <div class="info">' +
        '        <div class="title">' +
        `            ${houseInfo.aptName}` +
        "        </div>" +
        '        <div class="body">' +
        '            <div class="desc">' +
        `                <div class="ellipsis">상세 주소 : ${houseInfo.dongName} ${houseInfo.jibun}</div>` +
        `                <div class="ellipsis">건축 연도 : ${houseInfo.buildYear}</div><br/>` +
        '                <div class="jibun ellipsis">마커 클릭 시 상세 정보를 볼 수 있습니다.</div>' +
        "            </div>" +
        "        </div>" +
        "    </div>" +
        "</div>";
      let infoWindow = new kakao.maps.CustomOverlay({
        content,
        position: new kakao.maps.LatLng(houseInfo.lat, houseInfo.lng),
        yAnchor: -0.2,
      });
      return infoWindow;
    },
    removeMarkers() {
      this.hideMarkers();
      this.markers = [];
    },
    hideMarkers() {
      this.setMarkers(null);
    },
    showMarkers() {
      this.setMarkers(this.map);
    },

    setMarkers(map) {
      this.markers.forEach((marker) => marker.setMap(map));
    },

    removePolygons() {
      this.polygons.forEach((polygon) => polygon.setMap(null));
    },

    setPolygons(map) {
      console.log("setpolygons");
      this.polygons.forEach((polygon) => polygon.setMap(map));
    },

    makeInfoMarkers(type, data) {
      let markers = data.map((info) => {
        const markerPosition = new kakao.maps.LatLng(info.y || info.lat, info.x || info.lng);
        const imageSrc = require(`@/assets/${type}_marker.png`);
        const imageSize = new kakao.maps.Size(34, 39);
        const imageOptions = { offset: new kakao.maps.Point(27, 69) };
        const markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imageOptions);

        console.log(info);
        let overlay = new kakao.maps.CustomOverlay({
          position: markerPosition,
          yAnchor: 1.0,
          clickable: true,
        });

        let div = document.createElement("div");
        div.classList.add("customoverlay");
        let child =
          '<a target="_blank">' +
          `    <span class="title">${info.place_name || info.name}</span>` +
          `    <span class="text">주소 : ${info.road_address_name || info.address}</span>` +
          "</a>";
        div.innerHTML = child;
        let close = document.createElement("div");
        close.classList.add("closeimg");
        close.setAttribute("title", "닫기");
        close.addEventListener("click", () => {
          overlay.setMap(null);
          this.openedOverlay.delete(overlay);
        });
        let open = document.createElement("div");
        open.classList.add("openimg");
        open.addEventListener("click", () => {
          this.setUrl(
            info.place_url || `https://map.kakao.com/link/roadview/${info.lat},${info.lng}`
          );
          this.setEmbed(true);
        });
        div.appendChild(close);
        div.appendChild(open);
        overlay.setContent(div);
        let marker = new kakao.maps.Marker({
          map: this.map,
          position: markerPosition,
          image: markerImage,
        });

        kakao.maps.event.addListener(marker, "click", () => {
          overlay.setMap(this.map);
          this.openedOverlay.add(overlay);
        });

        return marker;
      });

      this[`${type}Markers`] = markers;
    },
    removeInfoMarkers(type) {
      let targetMarkers = this[`${type}Markers`];
      targetMarkers.forEach((marker) => {
        marker.setMap(null);
      });
      targetMarkers = [];
      if (this.openedOverlay.size > 0) {
        this.openedOverlay.forEach((overlay) => {
          overlay.setMap(null);
        });
        this.openedOverlay = new Set();
      }
    },
  },

  mounted() {
    if (!window.kakao || !window.kakao.maps) {
      const script = document.createElement("script");
      script.src = `//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=${process.env.VUE_APP_MAP_KEY}&libraries=services,clusterer`;
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
      if (this.deals.length == 0) {
        this.removeMarkers();
      } else {
        this.updateMarkers();
      }
    },
    LatLng() {
      this.map.setCenter(new kakao.maps.LatLng(this.LatLng.Lat, this.LatLng.Lng));
    },
    Level() {
      this.map.setLevel(this.Level);
    },

    apt() {
      if (this.deals.length == 0) this.removeMarkers();
      else this.hideMarkers();
      this.makeMarker(this.apt.aptCode);
      this.setLatLng({ lat: this.apt.lat, lng: this.apt.lng });
      this.setNdong({ dongName: this.apt.dongName, dongCode: this.apt.dongCode });
    },

    status() {
      console.log("status changed", this.status);
      if (this.status == 1) {
        this.setLatLng({ lat: 37.5642, lng: 127.0016 });
        this.setLevel(9);
        this.setPolygons(this.map);
        this.removeMarkers();
      } else {
        this.removePolygons();
      }
    },
    ToggleSidebar(flag) {
      if (!flag) {
        this.showMarkers(this.map);
      }
    },
    hospitals() {
      console.log(this.hospitals);
      if (this.hospitals.length > 0) {
        this.makeInfoMarkers("hospital", this.hospitals);
      } else {
        this.removeInfoMarkers("hospital");
      }
    },
    cafes() {
      if (this.cafes.length > 0) {
        this.makeInfoMarkers("cafe", this.cafes);
      } else {
        this.removeInfoMarkers("cafe");
      }
    },
    banks() {
      if (this.banks.length > 0) {
        this.makeInfoMarkers("bank", this.banks);
      } else {
        this.removeInfoMarkers("bank");
      }
    },
    theaters() {
      if (this.theaters.length > 0) {
        this.makeInfoMarkers("theater", this.theaters);
      } else {
        this.removeInfoMarkers("theater");
      }
    },
  },
};
</script>

<style>
#map {
  width: 100%;
  height: 82vh;
  position: relative;
  overflow: hidden;
}

#map_wrap {
  position: relative;
  margin: 0;
  padding: 0;
}
.circle {
  width: 50px;
  height: 50px;
  line-height: 50px;
  border-radius: 50%;
  font-size: 15px;
  color: #fff;
  text-align: center;
}

.level1 {
  background-color: #304ffe;
}

.level2 {
  background-color: #2e7d32;
}

.level3 {
  background-color: #ffd600;
}

.level4 {
  background-color: #ff9100;
}

.level6 {
  background-color: #d50000;
}

.wrap {
  position: absolute;
  left: 0;
  bottom: 30px;
  width: 288px;
  height: 132px;
  margin-left: -144px;
  text-align: left;
  overflow: hidden;
  font-size: 12px;
  font-family: "Malgun Gothic", dotum, "돋움", sans-serif;
  line-height: 1.5;
}
.wrap * {
  padding: 0;
  margin: 0;
}
.wrap .info {
  width: 286px;
  height: 120px;
  border-radius: 5px;
  border-bottom: 2px solid #ccc;
  border-right: 1px solid #ccc;
  overflow: hidden;
  background: #fff;
}
.wrap .info:nth-child(1) {
  border: 0;
  box-shadow: 0px 1px 2px #888;
}
.info .title {
  padding: 5px 0 0 10px;
  height: 30px;
  background: #eee;
  border-bottom: 1px solid #ddd;
  font-size: 18px;
  font-weight: bold;
}
.info .close {
  position: absolute;
  top: 10px;
  right: 10px;
  color: #888;
  width: 17px;
  height: 17px;
  background: url("https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/overlay_close.png");
}
.info .close:hover {
  cursor: pointer;
}
.info .body {
  position: relative;
  overflow: hidden;
}
.info .desc {
  position: relative;
  margin: 13px 0 0 13px;
  height: 75px;
}
.desc .ellipsis {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  font-size: 14px;
}
.desc .jibun {
  font-size: 13px;
  color: red;
  margin-top: -2px;
  text-align: center;
}
.info .img {
  position: absolute;
  top: 6px;
  left: 5px;
  width: 73px;
  height: 71px;
  border: 1px solid #ddd;
  color: #888;
  overflow: hidden;
}
.info:after {
  content: "";
  position: absolute;
  margin-left: -12px;
  left: 50%;
  bottom: 0;
  width: 22px;
  height: 12px;
  background: url("https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/vertex_white.png");
}
.info .link {
  color: #5085bb;
}
.name {
  position: absolute;
  background: #fff;
  border: 1px solid #888;
  border-radius: 3px;
  font-size: 12px;
  top: -5px;
  left: 15px;
  padding: 2px;
}
.customoverlay {
  position: relative;
  bottom: 80px;
  left: -10px;
  border-radius: 6px;
  border: 1px solid #ccc;
  border-bottom: 2px solid #ddd;
  float: left;
}
.customoverlay:nth-of-type(n) {
  border: 0;
  box-shadow: 0px 1px 2px #888;
}
.customoverlay a {
  display: block;
  text-decoration: none;
  color: #000;
  text-align: center;
  border-radius: 6px;
  font-size: 12px;
  font-weight: bold;
  overflow: hidden;
  background: #d95050;
}
.customoverlay .title {
  display: block;
  text-align: center;
  background: #fff;
  margin-right: 35px;
  padding: 10px 15px;
  font-size: 16px;
  font-weight: bold;
}
.customoverlay .text {
  display: block;
  text-align: center;
  background: #fff;
  margin-right: 35px;
  padding: 10px 15px;
  font-size: 13px;
}
.customoverlay:after {
  content: "";
  position: absolute;
  margin-left: -12px;
  left: 50%;
  bottom: -12px;
  width: 22px;
  height: 12px;
  background: url("https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/vertex_white.png");
}
.closeimg {
  position: absolute;
  top: 10px;
  right: 10px;
  color: #888;
  width: 17px;
  height: 17px;
  background: url("https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/overlay_close.png")
    no-repeat;
}
.close:hover {
  cursor: pointer;
}
.openimg {
  position: absolute;
  bottom: 10px;
  right: 10px;
  color: #888;
  width: 17px;
  height: 17px;
  background: url("https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/arrow_white.png")
    no-repeat center;
}
</style>
