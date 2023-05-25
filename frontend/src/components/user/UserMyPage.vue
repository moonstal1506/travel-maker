<template>
  <b-container class="mt-4" v-if="userInfo">
    <div class="title">
      <h3><b-icon icon="person-lines-fill"></b-icon> 마이페이지</h3>
    </div>
    <b-row>
      <b-col></b-col>
      <b-col cols="8">
        <b-jumbotron>
          <template #header>My Page</template>

          <template #lead> 내 정보 확인페이지입니다. </template>

          <hr class="my-4" />

          <b-container class="mt-4">
            <b-row>
              <b-col cols="2"></b-col>
              <b-col cols="2" align-self="end">아이디</b-col
              ><b-col cols="4" align-self="start">{{ userInfo.userid }}</b-col>
              <b-col cols="2"></b-col>
            </b-row>
            <b-row>
              <b-col cols="2"></b-col>
              <b-col cols="2" align-self="end">이름</b-col
              ><b-col cols="4" align-self="start">{{ userInfo.username }}</b-col>
              <b-col cols="2"></b-col>
            </b-row>
            <b-row>
              <b-col cols="2"></b-col>
              <b-col cols="2" align-self="end">이메일</b-col
              ><b-col cols="4" align-self="start">{{ userInfo.email }}</b-col>
              <b-col cols="2"></b-col>
            </b-row>
            <b-row>
              <b-col cols="2"></b-col>
              <b-col cols="2" align-self="end">가입일</b-col
              ><b-col cols="4" align-self="start">{{ userInfo.joindate }}</b-col>
              <b-col cols="2"></b-col>
            </b-row>
          </b-container>
          <hr class="my-4" />

          <b-button variant="outline-info" @click="goToUpdateUser">정보수정</b-button>
          <b-button variant="outline-danger" @click="deleteUser">회원탈퇴</b-button>
        </b-jumbotron>
      </b-col>
      <b-col></b-col>
    </b-row>
  </b-container>
</template>

<script>
import { mapState, mapActions } from "vuex";

const memberStore = "memberStore";

export default {
  name: "UserMyPage",
  components: {},
  computed: {
    ...mapState(memberStore, ["userInfo"]),
  },
  methods: {
    ...mapActions(memberStore, ["deleteMember", "userLogout", "goToUpdateUser"]),
    async deleteUser() {
      const confirmed = confirm("정말로 회원 탈퇴하시겠습니까?");
      if (confirmed) {
        await this.deleteMember(this.userInfo.userid)
          .then(() => {
            alert("회원 탈퇴가 완료되었습니다.");
            this.movePage();
          })
          .catch((error) => {
            console.log(error);
            alert("회원 탈퇴 중 오류가 발생했습니다.");
          });
      }
    },
    movePage() {
      this.$router.push({ name: "main" });
    },
    goToUpdateUser() {
      this.$router.push({ name: "update" });
    },
  },
};
</script>

<style></style>
