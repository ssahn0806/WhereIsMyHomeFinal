<template>
  <b-container fluid class="pl-3 bg-success">
    <b-row>
      <b-col class="col-3">
        <router-link to="/">
          <b-img fluid :src="require(`@/assets/logo.png`)" width="200px" />
        </router-link>
      </b-col>
      <b-col class="col-9">
        <header-info></header-info>
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
import HeaderInfo from "@/components/header/HeaderInfo.vue";
export default {
  id: "myHeader",
  components: {
    HeaderInfo,
  },
};
</script>

<style scoped></style>
