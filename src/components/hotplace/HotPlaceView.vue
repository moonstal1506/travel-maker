<template>
  <b-container class="bv-example-row mt-3">
    <div class="title">
      <h3><b-icon icon="journals"></b-icon> 글보기</h3>
    </div>
    <b-row class="mb-1">
      <b-col class="text-left">
        <b-button variant="outline-primary" @click="moveList">목록</b-button>
      </b-col>
      <b-col class="text-right" v-if="userInfo.userid === article.userid">
        <b-button variant="outline-info" size="sm" @click="moveModifyArticle" class="mr-2"
          >글수정</b-button
        >
        <b-button variant="outline-danger" size="sm" @click="deleteArticle">글삭제</b-button>
      </b-col>
    </b-row>
    <b-row class="mb-1">
      <b-col>
        <b-card
          :header-html="`<h3>${article.hotplaceno}.
          ${article.subject} [${article.hit}]</h3><div><h6>${article.userid}</div><div>${article.regtime}</h6></div>`"
          class="mb-2"
          border-variant="dark"
          no-body
        >
          <img v-for="(file, index) in article.files" :key="index" :src="file.sfilename | urlTo" />
          <b-card-body class="text-left">
            <div v-html="message"></div>
          </b-card-body>
        </b-card>
      </b-col>
    </b-row>

    <!-- 댓글 -->
    <comment-write @comment-added="loadComments"></comment-write>
    <comment-list ref="CommentList"></comment-list>
  </b-container>
</template>

<script>
import CommentWrite from "@/components/hotplace/comment/CommentWrite";
import CommentList from "@/components/hotplace/comment/CommentList";
import { getArticle } from "@/api/hotplace";
import { mapState } from "vuex";

const memberStore = "memberStore";

export default {
  name: "HotPlaceDetail",
  components: {
    CommentWrite,
    CommentList,
  },
  filters: {
    urlTo(url) {
      return `http://localhost/hotplace/upload/image/${url}`;
    },
  },
  data() {
    return {
      article: {},
    };
  },
  computed: {
    ...mapState(memberStore, ["userInfo"]),
    message() {
      if (this.article.content) return this.article.content.split("\n").join("<br>");
      return "";
    },
  },
  created() {
    let param = this.$route.params.hotplaceno;
    getArticle(
      param,
      ({ data }) => {
        this.article = data;
      },
      (error) => {
        console.log(error);
      }
    );
  },
  methods: {
    loadComments() {
      this.$refs.CommentList.loadComments();
    },
    moveModifyArticle() {
      this.$router.replace({
        name: "hotplacemodify",
        params: { hotplaceno: this.article.hotplaceno },
      });
    },
    deleteArticle() {
      if (confirm("정말로 삭제?")) {
        this.$router.replace({
          name: "hotplacedelete",
          params: { hotplaceno: this.article.hotplaceno },
        });
      }
    },
    moveList() {
      this.$router.push({ name: "hotplacelist" });
    },
  },
};
</script>

<style></style>
