<template>
  <b-container fluid class="pl-2 bg-secondary">
    <b-row>
      <b-col>
        <router-link to="/">
          <b-img fluid :src="require(`@/assets/logo.png`)" width="250px" />
        </router-link>
      </b-col>
      <b-col>
        <header-info></header-info>
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
import HeaderInfo from "@/components/HeaderInfo.vue";
export default {
  id: "myHeader",
  components: {
    HeaderInfo,
  },
};
</script>

<style scoped></style>
