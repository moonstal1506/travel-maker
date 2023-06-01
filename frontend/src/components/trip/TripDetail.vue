<template>
  <b-container v-if="trip" class="bv-example-row">
    <b-row>
      <b-col
        ><h3>{{ trip.title }}</h3></b-col
      >
    </b-row>
    <b-row class="mb-2 mt-1">
      <b-col><b-img :src="trip.first_image" fluid-grow></b-img></b-col>
    </b-row>
    <b-row>
      <b-col>
        <b-alert show variant="primary">관광지명 : {{ trip.title }} </b-alert>
      </b-col>
    </b-row>
    <b-row>
      <b-col>
        <b-alert show variant="info">주소 : {{ trip.addr1 }} </b-alert>
      </b-col>
    </b-row>
    <b-row>
      <b-col>
        <b-alert show variant="warning">{{ trip.overview }} </b-alert>
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
import { mapState } from "vuex";

const tripStore = "tripStore";

export default {
  name: "TripDetail",
  computed: {
    ...mapState(tripStore, ["trip"]),
  },
  filters: {
    price(value) {
      if (!value) return value;
      return value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    },
  },
};
</script>

<style></style>
