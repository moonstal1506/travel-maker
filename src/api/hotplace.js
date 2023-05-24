import { apiInstance, fileInstance } from "./index.js";

const api = apiInstance();
const file = fileInstance();

function listArticle(param, success, fail) {
  api.get(`/hotplace`, { params: param }).then(success).catch(fail);
}

function writeArticle(article, success, fail) {
  file.post(`/hotplace`, article).then(success).catch(fail);
}

function getArticle(hotplaceno, success, fail) {
  api.get(`/hotplace/${hotplaceno}`).then(success).catch(fail);
}

function modifyArticle(article, success, fail) {
  api.put(`/hotplace`, JSON.stringify(article)).then(success).catch(fail);
}

function deleteArticle(hotplaceno, success, fail) {
  api.delete(`/hotplace/${hotplaceno}`).then(success).catch(fail);
}

function writeComment(comment, success, fail) {
  api
    .post(`/hotplace/${comment.hotplaceno}/comment`, JSON.stringify(comment))
    .then(success)
    .catch(fail);
}

function listComment(hotplaceno, success, fail) {
  api.get(`/hotplace/${hotplaceno}/comment`).then(success).catch(fail);
}

function deleteComment(params, success, fail) {
  api
    .delete(`/hotplace/${params.hotplaceno}/comment/${params.commentId}`)
    .then(success)
    .catch(fail);
}

function sidoList(success, fail) {
  api.get(`/map/sido`).then(success).catch(fail);
}

function gugunList(params, success, fail) {
  api.get(`/map/gugun`, { params: params }).then(success).catch(fail);
}

function houseList(params, success, fail) {
  api.get(`/trip/data`, { params: params }).then(success).catch(fail);
}

export {
  listArticle,
  writeArticle,
  getArticle,
  modifyArticle,
  deleteArticle,
  writeComment,
  listComment,
  deleteComment,
  sidoList,
  gugunList,
  houseList,
};
