<template>
  <b-container class="bv-example-row">
    <div class="title">
      <h3><b-icon icon="house-fill"></b-icon>나만의 여행 계획</h3>
    </div>
    <b-row class="mt-3">
      <b-col cols="12">
        <the-kakao-map :chargers="houses"></the-kakao-map>
      </b-col>
    </b-row>
    <b-row>
      <b-col>
        <house-search-bar></house-search-bar>
      </b-col>
    </b-row>
    <b-row>
      <b-col cols="6" align="left">
        <house-list type="plan" />
      </b-col>
      <b-col cols="6">
        <h3>여행 계획 작성</h3>
        <div class="mb-3">
          <b-form-input v-model="planTitle" placeholder="제목을 입력해주세요"></b-form-input>
        </div>
        <div class="mb-3">
          <b-form-input v-model="date" id="type-date" type="date"></b-form-input>
        </div>
        <div class="mb-3">
          <b-form-textarea
            v-model="content"
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
          <b-button variant="outline-success" @click="register">등록</b-button>
        </div>
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
import { registerPlan } from "@/api/plan";
import { mapState, mapActions } from "vuex";
import HouseSearchBar from "@/components/house/HouseSearchBar.vue";
import HouseList from "@/components/house/HouseList.vue";
import TheKakaoMap from "@/components/TheKakaoMap.vue";

const houseStore = "houseStore";
const memberStore = "memberStore";

export default {
  name: "PlanWrite",
  components: {
    HouseSearchBar,
    HouseList,
    TheKakaoMap,
  },
  data() {
    return {
      planTitle: "",
      date: "",
      content: "",
    };
  },
  computed: {
    ...mapState(houseStore, ["houses"]),
    ...mapState(houseStore, ["plans"]),
    ...mapState(memberStore, ["userInfo"]),
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
    register() {
      if (!this.userInfo) {
        this.$router.push({ name: "login" });
        alert("로그인이 필요한 페이지입니다.");
        return;
      }
      let err = false;
      let msg = "";
      if (!this.planTitle) {
        msg = "제목 입력해주세요";
        err = true;
      }
      if (!this.content) {
        msg = "내용 입력해주세요";
        err = true;
      }
      if (!this.date) {
        msg = "날짜 입력해주세요";
        err = true;
      }
      if (!this.plans) {
        msg = "여행지를 선택해주세요";
        err = true;
      }
      if (err) {
        alert(msg);
        return;
      }
      const params = {
        userid: this.userInfo.userid,
        planTitle: this.planTitle,
        date: this.date,
        content: this.content,
        plans: this.plans,
      };
      console.log(params);
      registerPlan(
        params,
        ({ data }) => {
          let msg = "등록 처리시 문제가 발생했습니다.";
          if (data === "success") {
            msg = "등록이 완료되었습니다.";
          }
          alert(msg);
          this.moveList();
        },
        (error) => {
          console.log(error);
        }
      );
    },
    colorChange(flag) {
      this.isColor = flag;
    },
    moveList() {
      this.$router.push({ name: "planlist" });
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
