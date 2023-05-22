import Vue from "vue";
import VueRouter from "vue-router";
import AppMain from "@/views/AppMain";
import store from "@/store";
import AppHouse from "@/views/AppHouse";
import AppElectricCharger from "@/views/AppElectricCharger";

Vue.use(VueRouter);

// https://v3.router.vuejs.org/kr/guide/advanced/navigation-guards.html
const onlyAuthUser = async (to, from, next) => {
  const checkUserInfo = store.getters["memberStore/checkUserInfo"];
  const checkToken = store.getters["memberStore/checkToken"];
  let token = sessionStorage.getItem("access-token");
  console.log("로그인 처리 전", checkUserInfo, token);

  if (checkUserInfo != null && token) {
    console.log("토큰 유효성 체크하러 가자!!!!");
    await store.dispatch("memberStore/getUserInfo", token);
  }
  if (!checkToken || checkUserInfo === null) {
    alert("로그인이 필요한 페이지입니다..");
    // next({ name: "login" });
    router.push({ name: "login" });
  } else {
    console.log("로그인 했다!!!!!!!!!!!!!.");
    next();
  }
};

const routes = [
  {
    path: "/",
    name: "main",
    component: AppMain,
  },
  {
    path: "/house",
    name: "house",
    component: AppHouse,
  },
  {
    path: "/electric",
    name: "electric",
    component: AppElectricCharger,
  },
  {
    path: "/user",
    name: "user",
    component: () => import(/* webpackChunkName: "user" */ "@/views/AppUser"),
    children: [
      {
        path: "register",
        name: "register",
        component: () => import(/* webpackChunkName: "user" */ "@/components/user/UserRegister"),
      },
      {
        path: "login",
        name: "login",
        component: () => import(/* webpackChunkName: "user" */ "@/components/user/UserLogin"),
      },
      {
        path: "mypage",
        name: "mypage",
        beforeEnter: onlyAuthUser,
        component: () => import(/* webpackChunkName: "user" */ "@/components/user/UserMyPage"),
      },
      {
        path: "update",
        name: "update",
        beforeEnter: onlyAuthUser,
        component: () => import(/* webpackChunkName: "user" */ "@/components/user/UserUpdate"),
      },
    ],
  },
  {
    path: "/board",
    name: "board",
    component: () => import(/* webpackChunkName: "board" */ "@/views/AppBoard"),
    redirect: "/board/list",
    children: [
      {
        path: "list",
        name: "boardlist",
        component: () => import(/* webpackChunkName: "board" */ "@/components/board/BoardList"),
      },
      {
        path: "write",
        name: "boardwrite",
        beforeEnter: onlyAuthUser,
        component: () => import(/* webpackChunkName: "board" */ "@/components/board/BoardWrite"),
      },
      {
        path: "view/:articleno",
        name: "boardview",
        beforeEnter: onlyAuthUser,
        component: () => import(/* webpackChunkName: "board" */ "@/components/board/BoardView"),
      },
      {
        path: "modify",
        name: "boardmodify",
        beforeEnter: onlyAuthUser,
        component: () => import(/* webpackChunkName: "board" */ "@/components/board/BoardModify"),
      },
      {
        path: "delete/:articleno",
        name: "boarddelete",
        beforeEnter: onlyAuthUser,
        component: () => import(/* webpackChunkName: "board" */ "@/components/board/BoardDelete"),
      },
    ],
  },
  {
    path: "/qna",
    name: "qna",
    component: () => import(/* webpackChunkName: "qna" */ "@/views/AppQna"),
    redirect: "/qna/list",
    children: [
      {
        path: "list",
        name: "qnalist",
        component: () => import(/* webpackChunkName: "qna" */ "@/components/qna/QnaList"),
      },
      {
        path: "write",
        name: "qnawrite",
        beforeEnter: onlyAuthUser,
        component: () => import(/* webpackChunkName: "qna" */ "@/components/qna/QnaWrite"),
      },
      {
        path: "view/:articleno",
        name: "qnaview",
        beforeEnter: onlyAuthUser,
        component: () => import(/* webpackChunkName: "qna" */ "@/components/qna/QnaView"),
      },
      {
        path: "modify",
        name: "qnamodify",
        beforeEnter: onlyAuthUser,
        component: () => import(/* webpackChunkName: "qna" */ "@/components/qna/QnaModify"),
      },
      {
        path: "delete/:articleno",
        name: "qnadelete",
        beforeEnter: onlyAuthUser,
        component: () => import(/* webpackChunkName: "qna" */ "@/components/qna/QnaDelete"),
      },
    ],
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
