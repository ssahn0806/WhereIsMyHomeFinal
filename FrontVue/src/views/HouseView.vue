<template>
  <b-container fluid>
    <b-row class="ml-4 mt-2">
      <b-col lg="8">
        <house-search-bar></house-search-bar>
      </b-col>
    </b-row>
    <b-row>
      <b-col class="mt-4" lg="12">
        <house-map></house-map>
        <house-modal></house-modal>
        <house-side-bar></house-side-bar>
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
import HouseSearchBar from "@/components/house/HouseSearchBar.vue";
import HouseMap from "@/components/house/HouseMap.vue";
import HouseModal from "@/components/house/HouseModal.vue";
import HouseSideBar from "@/components/house/HouseSideBar.vue";

export default {
  components: {
    HouseSearchBar,
    HouseMap,
    HouseModal,
    HouseSideBar,
  },
};
</script>

<sty,
    HouseSiderbarle scoped></style>
