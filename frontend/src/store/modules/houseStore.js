import { sidoList, gugunList, houseList } from "@/api/house.js";

const houseStore = {
  namespaced: true,
  state: {
    sidos: [{ value: null, text: "선택하세요" }],
    guguns: [{ value: null, text: "선택하세요" }],
    houses: [],
    house: null,
    plans: [],
  },
  getters: {},
  mutations: {
    CLEAR_SIDO_LIST(state) {
      state.sidos = [{ value: null, text: "선택하세요" }];
    },
    CLEAR_GUGUN_LIST(state) {
      state.guguns = [{ value: null, text: "선택하세요" }];
    },
    CLEAR_APT_LIST(state) {
      state.houses = [];
      state.house = null;
    },
    CLEAR_DETAIL(state) {
      state.house = null;
    },
    SET_SIDO_LIST(state, sidos) {
      sidos.forEach((sido) => {
        state.sidos.push({ value: sido.sidoCode, text: sido.sidoName });
      });
    },
    SET_GUGUN_LIST(state, guguns) {
      guguns.forEach((gugun) => {
        state.guguns.push({ value: gugun.gugunCode, text: gugun.gugunName });
      });
    },
    SET_HOUSE_LIST(state, houses) {
      state.houses = houses;
    },
    SET_DETAIL_HOUSE(state, house) {
      state.house = house;
    },
    ADD_PLAN_LIST(state, plan) {
      state.plans.push({
        contentId: plan.contentId,
        first_image: plan.first_image,
        title: plan.title,
        addr1: plan.addr1,
      });
    },
    RESET_PLAN_LIST(state) {
      state.plans = [];
    },
    SET_PLAN_LIST(state, plans) {
      state.plans = plans;
    },
  },
  actions: {
    getSido: ({ commit }) => {
      sidoList(
        ({ data }) => {
          commit("SET_SIDO_LIST", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    getGugun: ({ commit }, sidoCode) => {
      const params = { sido: sidoCode };
      gugunList(
        params,
        ({ data }) => {
          commit("SET_GUGUN_LIST", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    getHouseList: ({ commit }, data) => {
      const params = {
        gugunCode: data.gugunCode,
        sidoCode: data.sidoCode,
        contentTypeId: data.contentTypeId,
        title: data.title,
        latitude: data.latitude,
        longitude: data.longitude,
      };
      houseList(
        params,
        ({ data }) => {
          commit("SET_HOUSE_LIST", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    detailHouse: ({ commit }, house) => {
      commit("SET_DETAIL_HOUSE", house);
    },
    addPlan: ({ commit }, plan) => {
      commit("ADD_PLAN_LIST", plan);
    },
    setPlan: ({ commit }, plan) => {
      commit("SET_PLAN_LIST", plan);
    },
    resetPlans: ({ commit }) => {
      commit("RESET_PLAN_LIST");
    },
  },
};

export default houseStore;
