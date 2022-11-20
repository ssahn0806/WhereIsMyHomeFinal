<template>
    <div class="row justify-content-center">
      <div class="col-lg-8 col-md-10 col-sm-12">
       
      </div>
      <div class="col-lg-8 col-md-10 col-sm-12">
        <div class="row">
          <div class="col-md-8">
            
            </div>
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
      style="text-shadow: 1px 1px 2px #333;"
      @sliding-start="onSlideStart"
      @sliding-end="onSlideEnd"
    >
      <tr v-for="newa in news.result" :key="newa.link">
        <b-carousel-slide 
        
        text="Nulla vitae elit libero, a pharetra augue mollis interdum."
        img-src="https://picsum.photos/1024/480/?image=52"
      >
      <h2 v-html="newa.title" @click="openwin(newa.link)"></h2>
      <h6 v-html="newa.description"></h6>
      </b-carousel-slide>
      </tr>
      
    </b-carousel>

  </div>


      </div>
      <div>
        <tbody>
          <b-row>
          <tr
            v-for="blog in blogs.result"
            :key="blog.link"
          >
          
  <b-col  class="pb-2"><b-button variant="primary" @click="openwin(blog.link)">
    <p v-html="blog.title"></p>
    </b-button></b-col>
            
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
  import { mapActions, mapGetters } from "vuex";
  export default {
    data() {
      return {
        slide: 0,
        sliding: null
      }
    },



  
    computed: {
      ...mapGetters(["blogs","news"]),
    },
    methods: {
      ...mapActions([Constant.GET_BLOGS,Constant.GET_NEWS]),

        getNaverBlogs() {
            console.log("naver blog 부르기 호출");
            this.getBlogs();
      },
      openwin(link) {
        window.open(link);
      },
      onSlideStart() {
        this.sliding = true
      },
      onSlideEnd() {
        this.sliding = false
      }
    },
  created() {
      this.getBlogs();
      this.getNews();
  },
  
    
  };
  </script>
  
  <style scoped></style>
  