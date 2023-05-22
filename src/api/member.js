import { apiInstance } from "./index.js";

const api = apiInstance();

async function confirmId(userid, success, fail) {
  await api.get(`/user/confirm/${userid}`).then(success).catch(fail);
}

function register(user, success, fail) {
  api.post(`/user/register`, JSON.stringify(user)).then(success).catch(fail);
}

async function login(user, success, fail) {
  await api.post(`/user/login`, JSON.stringify(user)).then(success).catch(fail);
}

async function findById(userid, success, fail) {
  api.defaults.headers["access-token"] = sessionStorage.getItem("access-token");
  await api.get(`/user/info/${userid}`).then(success).catch(fail);
}

async function tokenRegeneration(user, success, fail) {
  api.defaults.headers["refresh-token"] = sessionStorage.getItem("refresh-token"); //axios header에 refresh-token 셋팅
  await api.post(`/user/refresh`, user).then(success).catch(fail);
}

async function logout(userid, success, fail) {
  await api.get(`/user/logout/${userid}`).then(success).catch(fail);
}

async function deleteMember(userid, success, fail) {
  await api.delete(`/user/delete/${userid}`).then(success).catch(fail);
}

async function updateMember(userid, user, success, fail) {
  await api.put(`/user/update/${userid}`, JSON.stringify(user)).then(success).catch(fail);
}

export {
  confirmId,
  register,
  login,
  findById,
  tokenRegeneration,
  logout,
  deleteMember,
  updateMember,
};
