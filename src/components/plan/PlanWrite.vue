<template>
  <b-container class="bv-example-row">
    <h3>여행 계획 작성</h3>
    <div class="mb-3">
      <b-form-input v-model="text" placeholder="제목을 입력해주세요"></b-form-input>
    </div>
    <b-list-group>
      <b-list-group-item class="d-flex justify-content-between align-items-center">
        제주도
        <b-badge variant="primary" pill>x</b-badge>
      </b-list-group-item>

      <b-list-group-item class="d-flex justify-content-between align-items-center">
        일본
        <b-badge variant="primary" pill>x</b-badge>
      </b-list-group-item>

      <b-list-group-item class="d-flex justify-content-between align-items-center">
        한국
        <b-badge variant="primary" pill>x</b-badge>
      </b-list-group-item>
    </b-list-group>
    <div class="mt-3 text-right">
      <b-button variant="outline-success" @click="write">등록</b-button>
    </div>
  </b-container>
</template>

<script>
import { mapState } from "vuex";

const houseStore = "houseStore";

export default {
  name: "PlanWrite",
  computed: {
    ...mapState(houseStore, ["house"]),
    // house() {
    //   return this.$store.state.house;
    // },
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
