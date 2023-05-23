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
              <h5 class="mb-1">✍{{ comment.userid }}</h5>
              <small class="text-muted">{{ comment.regtime }}</small>
            </div>
            <p class="text-left">
              {{ comment.comment }}
            </p>
          </b-list-group-item>
        </b-list-group>
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
import { listComment } from "@/api/board";
export default {
  name: "CommentList",
  data() {
    return {
      comments: [],
    };
  },
  created() {
    this.loadComments();
  },
  methods: {
    loadComments() {
      let articleno = this.$route.params.articleno;
      console.log("methods");
      listComment(
        articleno,
        ({ data }) => {
          this.comments = data;
          console.log(data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
  },
};
</script>

<style scope></style>
