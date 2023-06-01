<template>
  <b-container v-if="trips && trips.length != 0" class="bv-example-row mt-3">
    <trip-list-item v-for="(trip, index) in trips" :key="index" :trip="trip" />
  </b-container>
  <b-container v-else class="bv-example-row mt-3">
    <b-row>
      <b-col><b-alert show>관광지가 없습니다.</b-alert></b-col>
    </b-row>
  </b-container>
</template>

<script>
import TripListItem from "@/components/trip/TripListItem";
import { mapState } from "vuex";
const tripStore = "tripStore";

export default {
  name: "TripList",
  components: {
    TripListItem,
  },
  data() {
    return {};
  },
  computed: {
    ...mapState(tripStore, ["trips"]),
  },
};
</script>

<style></style>
