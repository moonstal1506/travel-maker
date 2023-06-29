import { apiInstance } from "./index.js";

const api = apiInstance();

function sidoList(success, fail) {
  api.get(`/map/sido`).then(success).catch(fail);
}

function gugunList(params, success, fail) {
  api.get(`/map/gugun`, { params: params }).then(success).catch(fail);
}

function tripList(params, success, fail) {
  api.get(`/trip/data`, { params: params }).then(success).catch(fail);
}

function mainTripList(params, success, fail) {
  api.get(`/`, { params: params }).then(success).catch(fail);
}

export { sidoList, gugunList, tripList, mainTripList };
