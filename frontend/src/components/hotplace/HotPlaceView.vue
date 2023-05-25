<template>
  <b-container class="bv-example-row mt-3">
    <div class="title">
      <h3><b-icon icon="journals"></b-icon> 글보기</h3>
    </div>
    <div class="title">
      <h2 class="post-title">🗽핫플 자랑하기</h2>
      <p class="post-subtitle">나만의 핫플을 자랑해보아요</p>
    </div>
    <hr class="my-4" />
    <h2>{{ article.subject }}</h2>
    <p>
      조회수 {{ article.hit }} | 작성자 {{ article.userid }} | 날짜
      {{ article.regtime }}
    </p>
    <hr class="my-4" />
    <b-img
      :src="article.first_image || require('@/assets/mango.jpg')"
      alt="trip"
      width="600"
      height="600"
    />
    <b-row class="mb-1">
      <b-col>
        <br /><br />
        <h2>{{ article.title }}</h2>
        <h3>{{ article.addr1 }}</h3>
        <hr class="my-4" />
        <h5 v-html="article.content"></h5>
      </b-col>
    </b-row>
    <b-row class="mb-1">
      <b-col class="text-left">
        <b-button variant="outline-primary" @click="moveList">목록</b-button>
      </b-col>
      <b-col class="text-right" v-if="userInfo.userid === article.userid">
        <b-button
          variant="outline-info"
          size="sm"
          @click="moveModifyArticle"
          class="mr-2"
          >글수정</b-button
        >
        <b-button variant="outline-danger" size="sm" @click="deleteArticle"
          >글삭제</b-button
        >
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
import { getArticle } from "@/api/hotplace";
import { mapState } from "vuex";

const memberStore = "memberStore";

export default {
  name: "HotPlaceDetail",
  components: {},
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
      if (this.article.content)
        return this.article.content.split("\n").join("<br>");
      return "";
    },
  },
  created() {
    let param = this.$route.params.articleno;
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
    moveModifyArticle() {
      this.$router.replace({
        name: "hotplacemodify",
        params: { articleno: this.article.articleno },
      });
    },
    deleteArticle() {
      if (confirm("정말로 삭제?")) {
        this.$router.replace({
          name: "hotplacedelete",
          params: { articleno: this.article.articleno },
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
