<template>
  <b-container class="bv-example-row mt-3 text-center">
    <div class="title">
      <h3><b-icon icon="house-fill"></b-icon> 관광지 정보</h3>
    </div>
    <b-row class="mt-3">
      <b-col cols="12">
        <the-kakao-map :chargers="houses"></the-kakao-map>
      </b-col>
    </b-row>
    <b-row>
      <b-col>
        <house-search-bar></house-search-bar>
      </b-col>
    </b-row>
    <b-row>
      <b-col cols="6" align="left">
        <house-list />
      </b-col>
      <b-col cols="6">
        <house-detail />
      </b-col>
    </b-row>
  </b-container>
</template>
<script>
import HouseSearchBar from "@/components/house/HouseSearchBar.vue";
import HouseList from "@/components/house/HouseList.vue";
import HouseDetail from "@/components/house/HouseDetail.vue";
import TheKakaoMap from "@/components/TheKakaoMap.vue";
import { mapState } from "vuex";
const houseStore = "houseStore";

export default {
  name: "AppHouse",
  components: {
    HouseSearchBar,
    HouseList,
    HouseDetail,
    TheKakaoMap,
  },
  data() {
    return {};
  },
  computed: {
    ...mapState(houseStore, ["houses"]),
    // houses() {
    //   return this.$store.state.houses;
    // },
  },
};
</script>
<style scoped>
.underline-orange {
  display: inline-block;
  background: linear-gradient(
    180deg,
    rgba(255, 255, 255, 0) 70%,
    rgba(231, 149, 27, 0.3) 30%
  );
}
</style>
