<template>
  <div class="row justify-content-center">
    <div style="width:54%">
      <div class="mt-2">
        <b-carousel
          id="carousel-1"
          v-model="slide"
          :interval="3000"
          controls
          indicators
          background="#ababab"
          img-width="1024"
          img-height="480"
          style="text-shadow: 1px 1px 2px #333"
          @sliding-start="onSlideStart"
          @sliding-end="onSlideEnd"
        >
          <b-carousel-slide
            v-for="(newa, idx) in news"
            :key="newa.link"
            :img-src="'https://picsum.photos/1024/480/?image=' + no + idx"
          >
            <h2 v-html="newa.title" @click="openEmbed(newa.link)"></h2>
            <h6 v-html="newa.description"></h6>
          </b-carousel-slide>
        </b-carousel>
      </div>
    </div>
    <div class="row justify-content-center mt-3">
      <tr v-for="blog in blogs.result" :key="blog.link" class="ml-4 mr-4">
        <b-col
          ><b-card bg-variant="success" @click="openEmbed(blog.link)" img-height="180" img-top img-src="https://mblogthumb-phinf.pstatic.net/MjAyMDA5MjRfMTQ0/MDAxNjAwOTI4MDIxMTQ4.LGGIcSU4FrU6c_-MgA5xerYp1KXlF290JkqaG9uTPskg.MHNU4ycUh0ivOCxgEDo6YWorwOtonE7zwtmJMr1I3RAg.PNG.thdnjs4484/%EB%B8%94%EB%A1%9C%EA%B7%B81.png?type=w800">
            <b-card-text v-html="blog.title" style="color:white;font-weight:800;font-size:20px;"></b-card-text> 
            </b-card
        ></b-col>
      </tr>
    </div>
    <!-- <b-card
      title="Card Title"
      img-src="https://picsum.photos/600/300/?image=25"
      img-alt="Image"
      img-top
      tag="article"
      style="max-width: 20rem"
      class="mb-2"
    >
      <b-card-text>
        Some quick example text to build on the card title and make up the bulk of the card's
        content.
      </b-card-text>

      <b-button href="#" variant="primary">Go somewhere</b-button>
    </b-card> -->
    <house-embed></house-embed>
  </div>
</template>

<script>
//`@/assets/logo.png`
import Constant from "@/common/Constant.js";
import { mapActions, mapGetters, mapMutations } from "vuex";
import HouseEmbed from "@/components/house/HouseEmbed.vue";
export default {
  components: {
    HouseEmbed,
  },
  data() {
    return {
      no: 52,
      slide: 0,
      sliding: null,
    };
  },

  computed: {
    ...mapGetters(["blogs", "news"]),
  },
  methods: {
    ...mapActions([Constant.GET_BLOGS, Constant.GET_NEWS]),
    ...mapMutations([Constant.SET_URL,Constant.SET_EMBED]),
    getNaverBlogs() {
      this.getBlogs();
    },
    openEmbed(link) {
      this.setUrl(link);
      this.setEmbed(true);
    },
    onSlideStart() {
      this.sliding = true;
    },
    onSlideEnd() {
      this.sliding = false;
    },
  },
  created() {
    this.getBlogs();
    this.getNews();
  },
};
</script>

<style scoped></style>
