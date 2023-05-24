<template>
  <b-container v-if="house" class="bv-example-row">
    <b-row>
      <b-col
        ><h3>{{ house.title }}</h3></b-col
      >
    </b-row>
    <b-row class="mb-2 mt-1">
      <b-col><b-img :src="house.first_image" fluid-grow></b-img></b-col>
    </b-row>
    <b-row>
      <b-col>
        <b-alert show variant="primary">관광지명 : {{ house.title }} </b-alert>
      </b-col>
    </b-row>
    <b-row>
      <b-col>
        <b-alert show variant="info">주소 : {{ house.addr1 }} </b-alert>
      </b-col>
    </b-row>
    <b-row>
      <b-col>
        <b-alert show variant="warning">{{ house.overview }} </b-alert>
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
import { mapState } from "vuex";

const houseStore = "houseStore";

export default {
  name: "HouseDetail",
  computed: {
    ...mapState(houseStore, ["house"]),
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
