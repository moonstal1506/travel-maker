<template>
  <b-container class="bv-example-row mt-3 text-center">
    <div class="title">
      <h3><b-icon icon="trip-fill"></b-icon> 관광지 정보</h3>
    </div>
    <b-row class="mt-3">
      <b-col cols="12">
        <the-kakao-map :locations="trips"></the-kakao-map>
      </b-col>
    </b-row>
    <b-row>
      <b-col>
        <trip-search-bar></trip-search-bar>
      </b-col>
    </b-row>
    <b-row>
      <b-col cols="6" align="left">
        <trip-list />
      </b-col>
      <b-col cols="6">
        <trip-detail />
      </b-col>
    </b-row>
  </b-container>
</template>
<script>
import TripSearchBar from "@/components/trip/TripSearchBar.vue";
import TripList from "@/components/trip/TripList.vue";
import TripDetail from "@/components/trip/TripDetail.vue";
import TheKakaoMap from "@/components/TheKakaoMap.vue";
import { mapState } from "vuex";
const tripStore = "tripStore";

export default {
  name: "AppTrip",
  components: {
    TripSearchBar,
    TripList,
    TripDetail,
    TheKakaoMap,
  },
  data() {
    return {};
  },
  computed: {
    ...mapState(tripStore, ["trips"]),
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
