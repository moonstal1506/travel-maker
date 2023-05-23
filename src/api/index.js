import axios from "axios";

// local vue api axios instance
function apiInstance() {
  const instance = axios.create({
    baseURL: process.env.VUE_APP_API_BASE_URL,
    headers: {
      "Content-Type": "application/json;charset=utf-8",
    },
  });
  return instance;
}

function fileInstance() {
  const instance = axios.create({
    baseURL: process.env.VUE_APP_API_BASE_URL,
    headers: {
      "Content-Type": "multipart/form-data",
    },
  });
  return instance;
}

// house deal API axios instance
function houseInstance() {
  const instance = axios.create({
    baseURL: process.env.VUE_APP_HOUSE_DEAL_URL,
    headers: {
      "Content-Type": "application/json;charset=utf-8",
    },
  });
  return instance;
}

function electricInstance() {
  const instance = axios.create({
    baseURL: process.env.VUE_APP_ELECTRIC_CHARGER_STATION_URL,
    headers: {
      "Content-Type": "application/json;charset=utf-8",
    },
  });
  return instance;
}

export { apiInstance, houseInstance, electricInstance, fileInstance };
