import { apiInstance } from "./index.js";

const api = apiInstance();

function registerPlan(plan, success, fail) {
  api.post(`/plan`, plan).then(success).catch(fail);
}

export { registerPlan };
