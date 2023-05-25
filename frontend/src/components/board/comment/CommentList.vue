<template>
  <b-container class="bv-example-row mt-3">
    <b-row>
      <b-col>
        <b-list-group>
          <b-list-group-item
            v-for="comment in comments"
            v-bind:key="comment.commentId"
            class="flex-column align-items-start"
          >
            <div class="d-flex w-100 justify-content-between">
              <h5 class="mb-1">📝{{ comment.userid }}</h5>
              <small class="text-right">{{ comment.regtime }}</small>
            </div>
            <div class="d-flex w-100 justify-content-between">
              <span>
                {{ comment.comment }}
              </span>
              <span class="x" @click="remove(comment.commentId)">x</span>
            </div>
          </b-list-group-item>
        </b-list-group>
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
import { listComment, deleteComment } from "@/api/board";
export default {
  name: "CommentList",
  data() {
    return {
      hotplaceno: 0,
      comments: [],
    };
  },
  created() {
    this.loadComments();
  },
  mounted() {
    this.$on("comment-added", this.loadComments);
  },
  methods: {
    loadComments() {
      this.articleno = this.$route.params.articleno;
      console.log("methods");
      listComment(
        this.articleno,
        ({ data }) => {
          this.comments = data;
          console.log(data);
        },
        (error) => {
          console.log(error);
        }
      );
    },

    remove(commentId) {
      console.log(commentId);
      let params = {
        articleno: this.articleno,
        commentId,
      };
      deleteComment(
        params,
        ({ data }) => {
          let msg = "삭제 처리시 문제가 발생했습니다.";
          if (data === "success") {
            msg = "삭제가 완료되었습니다.";
          }
          alert(msg);
          this.loadComments();
        },
        (error) => {
          console.log(error);
        }
      );
    },
  },
};
</script>

<style scope>
.x {
  cursor: pointer;
}
</style>
