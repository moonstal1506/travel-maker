import { apiInstance } from "./index.js";

const api = apiInstance();

function registerPlan(plan, success, fail) {
  api.post(`/plan`, plan).then(success).catch(fail);
}
function listPlan(param, success, fail) {
  api.get(`/plan`, { params: param }).then(success).catch(fail);
}
function getPlan(planId, success, fail) {
  api.get(`/plan/${planId}`).then(success).catch(fail);
}
function deletePlan(planId, success, fail) {
  api.delete(`/plan/${planId}`).then(success).catch(fail);
}
export { registerPlan, listPlan, getPlan, deletePlan };
