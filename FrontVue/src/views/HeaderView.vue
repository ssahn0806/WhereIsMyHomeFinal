<template>
  <div>
    <header-main></header-main>
  </div>
</template>

<script>
// @ is an alias to /src
import HeaderMain from "@/components/header/HeaderMain.vue";
//import HeaderNav from "@/components/header/HeaderNav.vue";
export default {
  name: "HomeView",
  components: {
    HeaderMain,
    //HeaderNav,
  },
};
</script>
