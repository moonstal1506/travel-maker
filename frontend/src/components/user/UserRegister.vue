<template>
  <b-container class="bv-example-row mt-3">
    <div class="title">
      <h3><b-icon icon="person-lines-fill"></b-icon> 회원가입</h3>
    </div>
    <b-row>
      <b-col></b-col>
      <b-col cols="8">
        <b-card class="text-center mt-3" style="max-width: 40rem" align="left">
          <b-form class="text-left">
            <b-alert show variant="danger" v-if="isRegisterError"
              >입력값을 확인하세요.</b-alert
            >
            <b-form-group label="아이디:" label-for="userid">
              <b-form-input
                id="userid"
                v-model="user.userid"
                required
                placeholder="아이디 입력"
                @keyup="confirm"
              ></b-form-input>
              <span v-if="isDuplicated" class="error"
                >불가능한 아이디입니다.</span
              >
              <span v-else class="success">사용가능한 아이디입니다.</span>
            </b-form-group>
            <b-form-group label="이름:" label-for="username">
              <b-form-input
                id="username"
                v-model="user.username"
                required
                placeholder="이름 입력"
              ></b-form-input>
            </b-form-group>
            <b-form-group label="비밀번호:" label-for="userpwd">
              <b-form-input
                type="password"
                id="userpwd"
                v-model="user.userpwd"
                required
                placeholder="비밀번호 입력"
                @keyup="confirmPwd"
              ></b-form-input>
            </b-form-group>
            <b-form-group label="비밀번호 확인:" label-for="userpwdCheck">
              <b-form-input
                type="password"
                id="userpwdCheck"
                v-model="user.userpwdCheck"
                required
                placeholder="비밀번호 확인"
                @keyup="confirmPwd"
              ></b-form-input>
              <span class="success" v-if="isEqual">비밀번호가 일치합니다.</span>
              <span class="error" v-else>비밀번호가 일치하지 않습니다.</span>
            </b-form-group>
            <b-form-group label="이메일:" label-for="email">
              <b-form-input
                id="email"
                v-model="user.email"
                required
                placeholder="이메일 입력"
              ></b-form-input>
            </b-form-group>
            <b-button
              type="button"
              variant="outline-success"
              class="m-1"
              @click="resisterUser"
              >회원가입</b-button
            >
          </b-form>
        </b-card>
      </b-col>
      <b-col></b-col>
    </b-row>
  </b-container>
</template>

<script>
import { mapState, mapActions } from "vuex";
import { register } from "@/api/member";

const memberStore = "memberStore";

export default {
  name: "UserRegister",
  data() {
    return {
      isEqual: false,
      isRegisterError: false,
      user: {
        userid: null,
        username: null,
        userpwd: null,
        userpwdCheck: null,
        email: null,
      },
    };
  },
  computed: {
    ...mapState(memberStore, ["isDuplicated"]),
  },
  methods: {
    ...mapActions(memberStore, ["confirmId"]),
    confirmPwd() {
      this.isEqual = this.user.userpwd === this.user.userpwdCheck;
      console.log(this.isEqual);
    },
    async confirm() {
      await this.confirmId(this.user.userid);
      console.log(this.isDuplicated);
    },
    async resisterUser() {
      if (
        this.isDuplicated ||
        !this.isEqual ||
        !this.user.username ||
        !this.user.email
      ) {
        this.isRegisterError = true;
      } else {
        register(
          this.user,
          ({ data }) => {
            let msg = "회원가입 처리시 문제가 발생했습니다.";
            if (data === "success") {
              msg = "회원가입이 완료되었습니다.";
            }
            alert(msg);
            this.movePage();
          },
          (error) => {
            console.log(error);
          }
        );
      }
    },
    movePage() {
      this.$router.push({ name: "main" });
    },
  },
};
</script>

<style scoped>
.error {
  color: red;
}

.success {
  color: rgb(128, 210, 105);
}
</style>
