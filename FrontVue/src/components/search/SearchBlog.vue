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
      </div>
      <div>
        <tbody>
          <tr
            v-for="blog in blogs.result"
            :key="blog.link"
          >
            <th scope="row">{{ blog.link }}</th>
            
          </tr>
          
        </tbody>
      </div>
      
    </div>
  </template>
  
  블로그데이터 불러오기 

  <script>
  import Constant from "@/common/Constant.js";
  import { mapActions, mapGetters } from "vuex";
  export default {
    computed: {
      ...mapGetters(["blogs"]),
    },
    methods: {
      ...mapActions([Constant.GET_BLOGS]),
  
    //   modify(articleNo) {
    //     console.log(articleNo);
    //     this.$router.push(`/notice/modify/${articleNo}`);
    //   },
    //   remove(payload) {
    //     console.log("articleno : " + payload);
    //     this.removeNotice(payload).then(() => {
    //       this.$router.push("/notice");
    //     });
    //   },
    //   changeForm() {
    //     this.$router.push("/notice");
    //     },

        getNaverBlogs() {
            console.log("naver blog 부르기 호출");
            this.getBlogs();
        },
        
    },
    created() {
        console.log("naver blog 자동 부르기 호출");
            this.getBlogs();
  },
  
    
  };
  </script>
  
  <style scoped></style>
  