<template>
  <b-container class="bv-example-row mt-3 text-center">
    <div class="title">
      <h3><b-icon icon="lightning-charge"></b-icon> 전기차 충전소 찾기</h3>
    </div>
    <b-row class="mt-3">
      <select-sido @select-sido="selectSido"></select-sido>
      <select-gugun
        :sidoCode="sidoCode"
        @select-gugun="selectGugun"
      ></select-gugun>
    </b-row>
    <b-row class="mt-3">
      <b-col cols="12">
        <the-kakao-map :chargers="chargerList"></the-kakao-map>
      </b-col>
    </b-row>
  </b-container>
</template>
<script>
// import { mapActions, mapMutations } from "vuex";
import { electricChargerStationList } from "@/api/electric.js";
import SelectSido from "@/components/item/SelectSido.vue";
import SelectGugun from "@/components/item/SelectGugun.vue";
import TheKakaoMap from "@/components/TheKakaoMap.vue";

export default {
  name: "AppElectricCharger",
  components: {
    SelectSido,
    SelectGugun,
    TheKakaoMap,
  },
  data() {
    return {
      sidoCode: null,
      chargerList: [],
    };
  },
  methods: {
    selectSido(sidoCode) {
      this.sidoCode = sidoCode;
    },
    selectGugun(gugunCode) {
      console.log("구군바꼈으니 충전소 찾으러 가자!!!", gugunCode);
      const SERVICE_KEY = process.env.VUE_APP_APT_DEAL_API_KEY;

      const params = {
        pageNo: 1,
        numOfRows: 30,
        zscode: gugunCode,
        serviceKey: decodeURIComponent(SERVICE_KEY),
      };
      // if (gugunCode) params.zscode = gugunCode;
      // else params.zcode = this.sidoCode;

      electricChargerStationList(
        params,
        ({ data }) => {
          this.chargerList = data.items[0].item;
        },
        (error) => {
          console.log(error);
        }
      );
    },
  },
};
</script>
<style scoped></style>
