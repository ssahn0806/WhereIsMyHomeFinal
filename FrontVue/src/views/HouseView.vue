<template>
  <b-container fluid class="main">
      <b-col class="mt-2" lg="12">
        <house-map>
        </house-map>
        <house-modal></house-modal>
        <house-embed></house-embed>
        <house-side-bar></house-side-bar>
      </b-col>
  </b-container>
</template>

<script>
import HouseMap from "@/components/house/HouseMap.vue";
import HouseModal from "@/components/house/HouseModal.vue";
import HouseSideBar from "@/components/house/HouseSideBar.vue";
import HouseEmbed from "@/components/house/HouseEmbed.vue";

export default {
  components: {
    HouseMap,
    HouseModal,
    HouseSideBar,
    HouseEmbed,
  },
};
</script>

<style scoped>
.main{
 width: 100%;
 height: 100%; 
 margin : 0;
 padding : 0;
}</style>
