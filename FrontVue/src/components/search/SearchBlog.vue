<template>
  <div class="row justify-content-center">
    <div class="col-lg-8 col-md-10 col-sm-12"></div>
    <div class="col-lg-8 col-md-10 col-sm-12">
      <div class="row">
        <div class="col-md-8"></div>
      </div>

      <div>
        <b-carousel
          id="carousel-1"
          v-model="slide"
          :interval="4000"
          controls
          indicators
          background="#ababab"
          img-width="1024"
          img-height="480"
          style="text-shadow: 1px 1px 2px #333"
          @sliding-start="onSlideStart"
          @sliding-end="onSlideEnd"
        >
          <!-- <tr v-for="(newa, idx) in news.result" :key="newa.link"> -->
            <b-carousel-slide v-for="(newa, idx) in news" :key="newa.link" :img-src="'https://picsum.photos/1024/480/?image=' + no + idx">
              <h2 v-html="newa.title" @click="openwin(newa.link)"></h2>
              <h6 v-html="newa.description"></h6>
            </b-carousel-slide>
          <!-- </tr> -->
        </b-carousel>
      </div>
    </div>
    <div>
      <tbody>
        <b-row>
          <tr v-for="blog in blogs.result" :key="blog.link">
            <b-col class="pb-2"
              ><b-button variant="primary" @click="openwin(blog.link)">
                <p v-html="blog.title"></p> </b-button
            ></b-col>
          </tr>
        </b-row>
      </tbody>
    </div>
  </div>
</template>

<script>
//`@/assets/logo.png`
import Constant from "@/common/Constant.js";
// import user from '@/assets/user.json'
// import test from '@/assets/map.json'
//import a from 'C:/Users/Byung Hyun Jeon/Desktop/WhereIsMyHomeFinal/FrontVue/src/assets/map.json'
//import geo from "@/assets/a.geojson" assert { type: "json" };
import ab from "@/assets/ab.json";
import { mapActions, mapGetters } from "vuex";
export default {
  data() {
    return {
      no: 52,
      slide: 0,
      sliding: null,
      de: ab,
    };
  },

  computed: {
    ...mapGetters(["blogs", "news"]),
  },
  methods: {
    ...mapActions([Constant.GET_BLOGS, Constant.GET_NEWS]),

    getNaverBlogs() {
      console.log("naver blog 부르기 호출");
      this.getBlogs();
    },
    openwin(link) {
      window.open(link);
    },
    onSlideStart() {
      this.sliding = true;
    },
    onSlideEnd() {
      this.sliding = false;
    },
  },
  created() {
    console.log(this.de);

    let data = ab.features;
    console.log("---");
    console.log(data);
    console.log("---");
    // let coordinates = []; //좌표 저장 배열
    // let name = ''; //행정구 이름

    // let polygons = [];

    this.getBlogs();
    this.getNews();
  },
};
</script>

<style scoped></style>
