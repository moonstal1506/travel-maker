<template>
  <b-container class="bv-example-row">
    <h3>여행 계획 작성</h3>
    <div class="mb-3">
      <b-form-input v-model="text" placeholder="제목을 입력해주세요"></b-form-input>
    </div>
    <div class="mb-3">
      <b-form-input id="type-date" type="date"></b-form-input>
    </div>
    <div class="mb-3">
      <b-form-textarea
        id="textarea-no-resize"
        placeholder="상세 계획 작성"
        rows="3"
        no-resize
      ></b-form-textarea>
    </div>
    <b-list-group v-if="plans && plans.length != 0">
      <b-list-group-item
        v-for="(plan, index) in plans"
        :key="index"
        :plan="plan"
        class="flex-column align-items-start"
      >
        <div class="d-flex w-100">
          <h5 class="mb-1">
            <b-row>
              <b-col cols="2" class="text-center align-self-center">
                <b-img thumbnail :src="plan.first_image" alt="Image 1"></b-img>
              </b-col>
              <b-col cols="10" class="text-left">{{ plan.title }} </b-col>
            </b-row>
          </h5>
          <b-button @click="remove(plan.contentId)" variant="light">x</b-button>
        </div>
        <div class="text-left">
          <p class="mb-1">
            {{ plan.addr1 }}
          </p>
        </div>
      </b-list-group-item>
    </b-list-group>
    <div class="mt-3 text-right">
      <b-button variant="outline-success" @click="write">등록</b-button>
    </div>
  </b-container>
</template>

<script>
import { mapState, mapActions } from "vuex";

const houseStore = "houseStore";

export default {
  name: "PlanWrite",
  computed: {
    ...mapState(houseStore, ["plans"]),
  },
  created() {
    this.reset();
  },
  methods: {
    ...mapActions(houseStore, ["resetPlans"]),
    ...mapActions(houseStore, ["setPlan"]),
    reset() {
      this.resetPlans();
    },
    remove(contentId) {
      this.setPlan(this.plans.filter((plan) => plan.contentId !== contentId));
    },
    colorChange(flag) {
      this.isColor = flag;
    },
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
