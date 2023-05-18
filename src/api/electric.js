import { electricInstance } from "./index.js";

const electric = electricInstance();

function electricChargerStationList(params, success, fail) {
  electric.get(``, { params: params }).then(success).catch(fail);
}

export { electricChargerStationList };
