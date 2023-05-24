<template>
  <div>
    <hr />
    <b-row class="mb-1">
      <b-col cols="10">
        <b-form-input
          type="text"
          placeholder="댓글을 입력하세요"
          v-model="comment"
        ></b-form-input>
      </b-col>
      <b-col cols="2">
        <b-button variant="outline-success" @click="registComment"
          >댓글작성</b-button
        >
      </b-col>
    </b-row>
  </div>
</template>

<script>
import { writeComment } from "@/api/board";

import { mapState } from "vuex";
const memberStore = "memberStore";

export default {
  name: "CommentWrite",
  props: {
    articleno: Number,
  },
  data() {
    return {
      comment: "",
    };
  },
  computed: {
    ...mapState(memberStore, ["userInfo"]),
  },
  methods: {
    registComment() {
      if (!this.comment) {
        alert("댓글을 작성해주세요.");
        return;
      }
      let param = {
        articleno: this.$route.params.articleno,
        userid: this.userInfo.userid,
        comment: this.comment,
      };
      console.log(param);
      writeComment(
        param,
        ({ data }) => {
          let msg = "등록 처리시 문제가 발생했습니다.";
          if (data === "success") {
            msg = "등록이 완료되었습니다.";
            this.comment = "";
            this.$emit("comment-added");
            console.log("comment - added");
          }
          alert(msg);
        },
        (error) => {
          console.log(error);
        }
      );
    },
  },
};
</script>

<style></style>
