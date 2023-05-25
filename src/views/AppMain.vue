<template>
  <div>
    <b-container class="bv-example-row mt-3 text-center">
      <div>
        <b-carousel
          id="carousel-1"
          v-model="slide"
          :interval="4000"
          controls
          indicators
          background="#ababab"
          img-width="1024"
          img-height="480"
          style="text-shadow: 1px 1px 2px #333, box-shadow: "
          @sliding-start="onSlideStart"
          @sliding-end="onSlideEnd"
        >
          <!-- Slides with custom text -->
          <b-carousel-slide img-src="https://i.ibb.co/xgxrVD7/pexels-caroline-cagnin-2007401.jpg">
            <h1>Travel Maker</h1>
          </b-carousel-slide>
          <!-- Text slides with image -->

          <b-carousel-slide
            caption="Make a Plan"
            text="멋진 여행 계획을 세우고, 다른 사람들과 공유해보세요!"
            img-src="https://i.ibb.co/XJjkrys/pexels-ben-mack-5707602.jpg"
          ></b-carousel-slide>

          <!-- Text slides with image -->
          <b-carousel-slide
            caption="Hot Place"
            text="당신만의 핫한 Place를 자랑해보세요!"
            img-src="https://i.ibb.co/1ZvJRQf/pexels-sangeet-rao-4660487.jpg"
          ></b-carousel-slide>

          <b-carousel-slide
            caption="Community"
            text="많은 사람들과 다양한 이야기를 나누어 보세요!"
            img-src="https://i.ibb.co/dLxvZ7Q/pexels-pixabay-207896-1.jpg"
          ></b-carousel-slide>

          <!-- Text slides with image -->
        </b-carousel>
      </div>

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
              style="
                width: 20rem;
                height: 25rem;
                border-radius: 30px;
                border-top-left-radius: 30px;
                border-top-right-radius: 30px;
                overflow: hidden;
                box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
              "
              class="mb-2"
            >
              <template #img-top>
                <b-img
                  :src="house.first_image || require('@/assets/mango.jpg')"
                  alt="Image"
                  class="card-img"
                  style="object-fit: cover; width: 100%; height: 66%"
                ></b-img>
              </template>
              <b-card-text class="card-content">{{ house.addr1 }}</b-card-text>
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
      navigator.geolocation.getCurrentPosition(
        this.getPositionSuccess,
        this.getPositionError
      );
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
