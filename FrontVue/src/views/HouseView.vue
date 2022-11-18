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
        <house-side-bar></house-side-bar>
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
import HouseSearchBar from "@/components/house/HouseSearchBar.vue";
import HouseMap from "@/components/house/HouseMap.vue";
import HouseSideBar from "@/components/house/HouseSideBar.vue";
export default {
  components: {
    HouseSearchBar,
    HouseMap,
    HouseSideBar,
  },
};
</script>

<sty,
    HouseSiderbarle scoped></style>
