<template>
  <b-row
    class="m-2"
    @click="selectTrip"
    @mouseover="colorChange(true)"
    @mouseout="colorChange(false)"
    :class="{ 'mouse-over-bgcolor': isColor }"
  >
    <b-col cols="2" class="text-center align-self-center">
      <b-img thumbnail :src="trip.first_image" alt="Image 1"></b-img>
    </b-col>
    <b-col cols="9" class="align-self-center">{{ trip.title }} </b-col>
    <b-button cols="1" variant="light">+</b-button>
  </b-row>
</template>

<script>
import { mapActions } from "vuex";

const tripStore = "tripStore";
export default {
  name: "TripListItem",
  data() {
    return {
      isColor: false,
    };
  },
  props: {
    trip: Object,
  },
  methods: {
    ...mapActions(tripStore, ["detailTrip"]),
    ...mapActions(tripStore, ["addPlan"]),

    selectTrip() {
      this.detailTrip(this.trip);
      this.addPlan(this.trip);
      console.log("methods", this.trip);
    },
    colorChange(flag) {
      this.isColor = flag;
    },
  },
};
</script>

<style scoped>
.apt {
  width: 50px;
}
.mouse-over-bgcolor {
  background-color: rgba(236, 236, 236, 0.964);
}
</style>
