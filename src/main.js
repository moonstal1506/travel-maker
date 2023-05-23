import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";

import "@/api/lib/fontAwesomeIcon.js"; // fontAwesomeIcon.js 불러옴
import "@/api/lib/vueBootstrap.js";
import "bootstrap/dist/css/bootstrap.css"; // 부트스트랩 CSS 파일 추가
import "bootstrap"; // 부트스트랩 JavaScript 파일 추가

Vue.config.productionTip = false;

new Vue({
  router,
  store,
  render: (h) => h(App),
}).$mount("#app");
