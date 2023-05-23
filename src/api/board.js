import { apiInstance, fileInstance } from "./index.js";

const api = apiInstance();
const file = fileInstance();

function listArticle(param, success, fail) {
  api.get(`/board`, { params: param }).then(success).catch(fail);
}

function writeArticle(article, success, fail) {
  file.post(`/board`, article).then(success).catch(fail);
}

function getArticle(articleno, success, fail) {
  api.get(`/board/${articleno}`).then(success).catch(fail);
}

function modifyArticle(article, success, fail) {
  api.put(`/board`, JSON.stringify(article)).then(success).catch(fail);
}

function deleteArticle(articleno, success, fail) {
  api.delete(`/board/${articleno}`).then(success).catch(fail);
}

function writeComment(comment, success, fail) {
  api
    .post(`/board/${comment.articleno}/comment`, JSON.stringify(comment))
    .then(success)
    .catch(fail);
}

function listComment(articleno, success, fail) {
  api.get(`/board/${articleno}/comment`).then(success).catch(fail);
}

function deleteComment(params, success, fail) {
  api.delete(`/board/${params.articleno}/comment/${params.commentId}`).then(success).catch(fail);
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
};
