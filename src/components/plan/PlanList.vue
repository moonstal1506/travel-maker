<template>
  <div>
    <b-container class="bv-example-row mt-3 text-center">
      <div class="title">
        <h2 class="post-title">🗼나만의 여행 계획</h2>
        <p class="post-subtitle">나만의 여행 계획을 공유해보아요</p>
      </div>
      <hr class="my-4" />
      <b-row class="mb-3">
        <b-col class="text-left">
          <b-button variant="outline-success" @click="moveWrite()">계획세우기</b-button>
        </b-col>
      </b-row>
      <div>
        <div class="row">
          <div class="col-md-4" v-for="(plan, index) in plans" :key="index">
            <router-link
              :to="{
                name: 'planview',
                params: { planId: plan.planId },
              }"
            >
              <b-card
                :title="plan.planTitle"
                :img-src="plan.trips[0].first_image || require('@/assets/mango.jpg')"
                img-alt="Image"
                img-top
                tag="article"
                style="max-width: 20rem"
                class="mb-2"
              >
                <b-card-text>{{ plan.date }}</b-card-text>
              </b-card>
            </router-link>
          </div>
        </div>
      </div>
    </b-container>
  </div>
</template>

<script>
import { listPlan } from "@/api/plan";

export default {
  name: "PlanWrite",
  data() {
    return {
      plans: [],
      // title: "",
      // // date: "",이미지
      // content: "",
    };
  },
  created() {
    this.loadPlans();
  },
  methods: {
    loadPlans() {
      listPlan(
        null,
        ({ data }) => {
          this.plans = data;
          console.log(data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    moveWrite() {
      this.$router.push({ name: "planwrite" });
    },
  },
};
</script>

<style>
a {
  text-decoration: none;
  color: rgb(33, 33, 33);
}
</style>
