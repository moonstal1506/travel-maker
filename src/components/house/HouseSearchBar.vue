<template>
  <b-row class="mt-4 mb-4 text-center">
    <!-- <b-col class="sm-3">
      <b-form-input
        v-model.trim="dongCode"
        placeholder="동코드 입력...(예 : 11110)"
        @keypress.enter="sendKeyword"
      ></b-form-input>
    </b-col>
    <b-col class="sm-3" align="left">
      <b-button variant="outline-primary" @click="sendKeyword">검색</b-button>
    </b-col> -->
    <!-- <b-col class="sm-3">
      <b-form-select v-model="sidoCode" :options="sidos" @change="gugunList"></b-form-select>
    </b-col>
    <b-col class="sm-3">
      <b-form-select v-model="gugunCode" :options="guguns" @change="searchApt"></b-form-select>
    </b-col> -->
    <select-sido @select-sido="selectSido"></select-sido>
    <select-gugun :sidoCode="sidoCode" @select-gugun="selectGugun"></select-gugun>
  </b-row>
</template>

<script>
import { mapActions, mapMutations } from "vuex";
import SelectSido from "@/components/item/SelectSido.vue";
import SelectGugun from "@/components/item/SelectGugun.vue";

const itemStore = "itemStore";
const houseStore = "houseStore";

export default {
  name: "HouseSearchBar",
  components: {
    SelectSido,
    SelectGugun,
  },
  data() {
    return {
      sidoCode: null,
    };
  },
  computed: {},
  created() {},
  methods: {
    ...mapActions(houseStore, ["getHouseList"]),
    ...mapActions(itemStore, ["getGugun"]),
    ...mapMutations(itemStore, ["CLEAR_GUGUN_LIST"]),
    selectSido(sidoCode) {
      // this.CLEAR_GUGUN_LIST();
      // this.getGugun(sidoCode);
      this.sidoCode = sidoCode;
    },
    selectGugun(gugunCode) {
      console.log("구군바꼈으니 아파트 찾으러 가자!!!");
      if (gugunCode) this.getHouseList(gugunCode);
    },
  },
};
</script>

<style></style>
