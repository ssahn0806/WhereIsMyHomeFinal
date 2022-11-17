<template>
  <div class="home">
    <header-main></header-main>
    <header-nav></header-nav>
  </div>
</template>

<script>
// @ is an alias to /src
import HeaderMain from "@/components/HeaderMain.vue";
import HeaderNav from "@/components/HeaderNav.vue";
export default {
  name: "HomeView",
  components: {
    HeaderMain,
    HeaderNav,
  },
};
</script>
