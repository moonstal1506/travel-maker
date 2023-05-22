<template>
  <b-container class="bv-example-row mt-3">
    <div class="title">
      <h3><b-icon icon="person-lines-fill"></b-icon> 회원정보 수정</h3>
    </div>
    <b-row>
      <b-col></b-col>
      <b-col cols="8">
        <b-card class="text-center mt-3" style="max-width: 40rem" align="left">
          <b-form class="text-left">
            <b-alert show variant="danger" v-if="isUpdateError">입력값을 확인하세요.</b-alert>
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
                placeholder="비밀번호 입력 (변경하지 않을 경우 비워둔 채로)"
              ></b-form-input>
            </b-form-group>
            <b-form-group label="비밀번호 확인:" label-for="userpwdCheck">
              <b-form-input
                type="password"
                id="userpwdCheck"
                v-model="user.userpwdCheck"
                placeholder="비밀번호 확인 (변경하지 않을 경우 비워둔 채로)"
                @input="confirmPwd"
              ></b-form-input>
              <span class="success" v-if="isEqual && user.userpwdCheck !== ''"
                >비밀번호가 일치합니다.</span
              >
              <span class="error" v-else-if="user.userpwdCheck !== ''"
                >비밀번호가 일치하지 않습니다.</span
              >
            </b-form-group>
            <b-form-group label="이메일:" label-for="email">
              <b-form-input
                id="email"
                v-model="user.email"
                required
                placeholder="이메일 입력"
              ></b-form-input>
            </b-form-group>
            <b-button type="button" variant="outline-success" class="m-1" @click="updateUser"
              >회원정보 수정</b-button
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
import { updateMember } from "@/api/member";

const memberStore = "memberStore";

export default {
  name: "UserUpdate",
  data() {
    return {
      isEqual: false,
      isUpdateError: false,
      // user: {
      //   userid: null,
      //   username: null,
      //   userpwd: null,
      //   userpwdCheck: null,
      //   email: null,
      // },
    };
  },
  created() {
    this.user = {
      ...this.userInfo,
    };
    console.log(this.user);
  },
  computed: {
    ...mapState(memberStore, ["userInfo"]),
    ...mapState(memberStore, ["isDuplicated"]),
  },
  methods: {
    ...mapActions(memberStore, ["confirmId"]),
    confirmPwd() {
      this.isEqual = this.user.userpwd === this.user.userpwdCheck;
    },
    async updateUser() {
      if (
        (!this.user.userpwd && this.user.userpwdCheck) ||
        (this.user.userpwd && !this.user.userpwdCheck) ||
        (this.user.userpwd && this.user.userpwdCheck && !this.isEqual) ||
        !this.user.username ||
        !this.user.email
      ) {
        this.isUpdateError = true;
      } else {
        updateMember(
          sessionStorage.getItem("userid"),
          this.user,
          () => {
            alert("회원정보가 수정되었습니다.");
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
