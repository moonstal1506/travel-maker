<template>
  <div class="navbar-container">
    <b-navbar toggleable="lg" type="dark" variant="success" fixed="top" class="custom-navbar">
      <b-navbar-brand href="#">
        <router-link :to="{ name: 'main' }" class="link">
          <h2>TravelMaker</h2>
        </router-link>
      </b-navbar-brand>

      <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

      <b-collapse id="nav-collapse" is-nav>
        <b-navbar-nav>
          <b-nav-item href="#">
            <router-link :to="{ name: 'main' }" class="link"> 메인 </router-link>
            <router-link :to="{ name: 'board' }" class="m-2 link"> 게시판 </router-link>
            <router-link :to="{ name: 'qna' }" class="m-2 link"> Qna </router-link>
            <router-link :to="{ name: 'electric' }" class="m-2 link"> 전기 </router-link>
            <router-link :to="{ name: 'house' }" class="m-2 link"> 여행지 </router-link>
            <router-link :to="{ name: 'plan' }" class="m-2 link"> 여행계획 </router-link>
            <router-link :to="{ name: 'hotplace' }" class="m-2 link">
              나만의 핫플레이스
            </router-link>
          </b-nav-item>
        </b-navbar-nav>

        <!-- after login -->
        <b-navbar-nav class="ml-auto" v-if="userInfo">
          <b-nav-item class="align-self-center">
            {{ userInfo.username }}({{ userInfo.userid }})님 환영합니다.
          </b-nav-item>
          <b-nav-item class="align-self-center">
            <router-link :to="{ name: 'mypage' }" class="link align-self-center"
              >내정보보기</router-link
            >
          </b-nav-item>
          <b-nav-item class="align-self-center link" @click.prevent="onClickLogout"
            >로그아웃</b-nav-item
          >
        </b-navbar-nav>
        <!-- before login -->
        <b-navbar-nav class="ml-auto" v-else>
          <b-nav-item-dropdown right>
            <template #button-content>
              <b-icon icon="people" font-scale="2"></b-icon>
            </template>
            <b-dropdown-item href="#">
              <router-link :to="{ name: 'register' }" class="link drop">
                <b-icon icon="person-circle"></b-icon> 회원가입
              </router-link>
            </b-dropdown-item>
            <b-dropdown-item href="#">
              <router-link :to="{ name: 'login' }" class="link drop">
                <b-icon icon="key"></b-icon> 로그인
              </router-link>
            </b-dropdown-item>
          </b-nav-item-dropdown>
        </b-navbar-nav>
      </b-collapse>
    </b-navbar>
  </div>
</template>

<script>
import { mapState, mapGetters, mapActions } from "vuex";

const memberStore = "memberStore";

export default {
  name: "TheHeaderNavbar",
  data() {
    return {};
  },
  computed: {
    ...mapState(memberStore, ["isLogin", "userInfo"]),
    ...mapGetters(["checkUserInfo"]),
  },
  methods: {
    ...mapActions(memberStore, ["userLogout"]),
    // ...mapMutations(memberStore, ["SET_IS_LOGIN", "SET_USER_INFO"]),
    onClickLogout() {
      // this.SET_IS_LOGIN(false);
      // this.SET_USER_INFO(null);
      // sessionStorage.removeItem("access-token");
      // if (this.$route.path != "/") this.$router.push({ name: "main" });
      console.log(this.userInfo.userid);
      //vuex actions에서 userLogout 실행(Backend에 저장 된 리프레시 토큰 없애기
      //+ satate에 isLogin, userInfo 정보 변경)
      // this.$store.dispatch("userLogout", this.userInfo.userid);
      this.userLogout(this.userInfo.userid);
      sessionStorage.removeItem("access-token"); //저장된 토큰 없애기
      sessionStorage.removeItem("refresh-token"); //저장된 토큰 없애기
      if (this.$route.path != "/") this.$router.push({ name: "main" });
    },
  },
};
</script>

<style scoped>
#logo {
  width: 120px;
}

.ml-auto .drop {
  text-decoration: none;
  color: #9dc8c8;
}
.link {
  color: rgb(245, 255, 240);
}

.navbar-container {
  margin-bottom: 100px;
}

.custom-navbar {
  background-color: rgba(209, 182, 225, 0.8) !important;
  box-shadow: 0 16px 24px rgba(0, 0, 0, 0.1);
  margin-bottom: 10px;
}

.link {
  color: rgb(245, 255, 240);
  text-decoration: none; /* 밑줄 제거 */
  transition: transform 0.3s;
}

.link:hover {
  transform: scale(1.15); /* 마우스를 갖다 대었을 때 크기를 1.15배로 확대 */
}

nav a:hover {
  transform: scale(1.05);
}
</style>
