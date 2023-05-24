<template>
  <b-row
    class="m-2"
    @click="selectHouse"
    @mouseover="colorChange(true)"
    @mouseout="colorChange(false)"
    :class="{ 'mouse-over-bgcolor': isColor }"
  >
    <b-col cols="2" class="text-center align-self-center">
      <b-img thumbnail :src="house.first_image" alt="Image 1"></b-img>
    </b-col>
    <b-col cols="9" class="align-self-center">{{ house.title }} </b-col>
    <b-button cols="1" variant="light">+</b-button>
  </b-row>
</template>

<script>
import { mapActions } from "vuex";

const houseStore = "houseStore";
export default {
  name: "HouseListItem",
  data() {
    return {
      isColor: false,
    };
  },
  props: {
    house: Object,
  },
  methods: {
    ...mapActions(houseStore, ["detailHouse"]),
    ...mapActions(houseStore, ["addPlan"]),

    selectHouse() {
      this.detailHouse(this.house);
      this.addPlan(this.house);
      console.log("methods", this.house);
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
