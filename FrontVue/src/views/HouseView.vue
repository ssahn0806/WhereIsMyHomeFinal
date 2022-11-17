<template>
  <b-container fluid>
    <b-row class="ml-4 mt-4">
      <b-col lg="8">
        <house-search-bar></house-search-bar>
      </b-col>
    </b-row>
    <b-row>
      <b-col class="mt-4" lg="12">
        <house-map></house-map>
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
import HouseSearchBar from "@/components/house/HouseSearchBar.vue";
import HouseMap from "@/components/house/HouseMap.vue";
export default {
  components: {
    HouseSearchBar,
    HouseMap,
  },
};
</script>

<style scoped></style>
