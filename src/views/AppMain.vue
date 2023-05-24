<template>
  <div>
    <b-img class="main" alt="main" :src="require('@/assets/main.jpg')" />
    <b-container class="bv-example-row mt-3 text-center">
      <div class="title">
        <h2 class="post-title">🐱‍🐉여행지 추천</h2>
        <p class="post-subtitle">여행지 추천해드려요</p>
      </div>
      <hr class="my-4" />
      <div>
        <div class="row">
          <div class="col-md-4" v-for="(house, index) in houses" :key="index">
            <b-card
              :title="house.title"
              :img-src="house.first_image || require('@/assets/mango.jpg')"
              img-alt="Image"
              img-top
              tag="article"
              style="max-width: 20rem"
              class="mb-2"
            >
              <b-card-text>{{ house.addr1 }}</b-card-text>
              <!-- <b-button variant="success">Go somewhere</b-button> -->
            </b-card>
          </div>
        </div>
      </div>
    </b-container>
  </div>
</template>

<script>
import { mapActions, mapState } from "vuex";
const houseStore = "houseStore";

export default {
  name: "AppMain",
  props: {
    msg: String,
  },
  data() {
    return {};
  },
  created() {
    this.getCurrentPosition();
  },
  computed: {
    ...mapState(houseStore, ["houses"]),
  },
  methods: {
    ...mapActions(houseStore, ["getHouseList"]),
    getCurrentPosition() {
      navigator.geolocation.getCurrentPosition(this.getPositionSuccess, this.getPositionError);
    },
    getPositionSuccess(position) {
      const latitude = position.coords.latitude;
      const longitude = position.coords.longitude;
      let params = {
        latitude,
        longitude,
      };
      this.getHouseList(params);
    },
    getPositionError() {
      alert("위치를 찾을 수 없습니다.");
    },
  },
};
</script>

<style scoped>
div {
  text-align: center;
}

.main {
  width: 1150px;
  height: 400px;
  margin: 0px auto;
  margin-top: 50px;
}
</style>
