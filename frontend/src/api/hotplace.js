import { apiInstance, fileInstance } from "./index.js";

const api = apiInstance();
const file = fileInstance();

function listArticle(param, success, fail) {
  api.get(`/hotplace`, { params: param }).then(success).catch(fail);
}

function writeArticle(article, success, fail) {
  file.post(`/hotplace`, article).then(success).catch(fail);
}

function getArticle(articleno, success, fail) {
  api.get(`/hotplace/${articleno}`).then(success).catch(fail);
}

function modifyArticle(article, success, fail) {
  api.put(`/hotplace`, JSON.stringify(article)).then(success).catch(fail);
}

function deleteArticle(articleno, success, fail) {
  api.delete(`/hotplace/${articleno}`).then(success).catch(fail);
}

export { listArticle, writeArticle, getArticle, modifyArticle, deleteArticle };
