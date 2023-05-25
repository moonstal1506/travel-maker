<template>
  <div>
    <b-row class="mt-4 mb-4 text-center">
      <select-sido @select-sido="selectSido"></select-sido>
      <select-gugun :sidoCode="sidoCode" @select-gugun="selectGugun"></select-gugun>
      <select-content-type-id
        @select-content-type-id="selectContentTypeId"
      ></select-content-type-id>
    </b-row>
    <search-title @search="searchTitle"></search-title>
  </div>
</template>

<script>
import { mapActions, mapMutations } from "vuex";
import SelectSido from "@/components/item/SelectSido.vue";
import SelectGugun from "@/components/item/SelectGugun.vue";
import SelectContentTypeId from "@/components/item/SelectContentTypeId.vue";
import SearchTitle from "@/components/item/SearchTitle.vue";

const itemStore = "itemStore";
const houseStore = "houseStore";

export default {
  name: "HouseSearchBar",
  components: {
    SelectSido,
    SelectGugun,
    SelectContentTypeId,
    SearchTitle,
  },
  data() {
    return {
      sidoCode: null,
      contentTypeId: null,
      title: null,
    };
  },
  computed: {},
  created() {},
  methods: {
    ...mapActions(houseStore, ["getHouseList"]),
    ...mapActions(itemStore, ["getGugun"]),
    ...mapMutations(itemStore, ["CLEAR_DETAIL"]),
    selectSido(sidoCode) {
      // this.CLEAR_GUGUN_LIST();
      // this.getGugun(sidoCode);
      this.sidoCode = sidoCode;
    },
    selectGugun(gugunCode) {
      console.log("sido 코드는 : ", this.sidoCode);
      let params = {
        gugunCode,
        sidoCode: this.sidoCode,
      };
      if (gugunCode) this.getHouseList(params);
    },
    selectContentTypeId(contentTypeId) {
      console.log("contentTypeId : ", this.contentTypeId);
      let params = {
        gugunCode: this.gugunCode,
        sidoCode: this.sidoCode,
        contentTypeId,
      };
      if (contentTypeId) this.getHouseList(params);
    },
    searchTitle(title) {
      console.log("title : ", this.title);
      let params = {
        gugunCode: this.gugunCode,
        sidoCode: this.sidoCode,
        contentTypeId: this.contentTypeId,
        title,
      };
      this.getHouseList(params);
    },
  },
};
</script>

<style></style>
