<template>
    <div class="row justify-content-center">
      <div class="col-lg-8 col-md-10 col-sm-12">
        <h2 class="my-3 py-3 shadow-sm bg-light text-center">
          <mark class="sky">QnA 보기</mark>
        </h2>
      </div>
      <div class="col-lg-8 col-md-10 col-sm-12">
        <div class="row">
          <div class="col-md-8">
            <button
              type="button"
              id="btn-list"
              class="btn btn-outline-primary mb-3"
              @click="getNaverBlogs">블로그 가져오기</button>
            </div>
        </div>
        <div>
          <b-row>
  <b-col lg="4" class="pb-2"><b-button size="sm">Small Button</b-button></b-col>
  <b-col lg="4" class="pb-2"><b-button>Default Button</b-button></b-col>
  <b-col lg="4" class="pb-2"><b-button size="lg">Large Button</b-button></b-col>
</b-row>
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
    {{blog.title}}</b-button></b-col>
            
          </tr>
        </b-row>
        </tbody>

        <tbody>
          <b-row>
          <tr
            v-for="newa in news.result"
            :key="newa.link"
          >
          
  <b-col  class="pb-2"><b-button variant="primary" @click="openwin(newa.link)">
    {{newa.title}}</b-button></b-col>
            
          </tr>
        </b-row>
        </tbody>
      </div>
      
    </div>
  </template>
  

  <script>
  import Constant from "@/common/Constant.js";
  import { mapActions, mapGetters } from "vuex";
  export default {
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
        
    },
    created() {
        console.log("naver blog 자동 부르기 호출");
      this.getBlogs();
      this.getNews();
  },
  
    
  };
  </script>
  
  <style scoped></style>
  