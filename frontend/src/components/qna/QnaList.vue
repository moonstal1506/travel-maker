<template>
  <b-container class="bv-example-row mt-3">
    <b-row>
      <b-col>
        <b-alert show><h3>글목록</h3></b-alert>
      </b-col>
    </b-row>
    <b-row class="mb-1">
      <b-col class="col-5"> </b-col>
      <b-col>
        <select v-model="key">
          <option v-for="(k, index) in fields" :key="index" :value="k.key">
            {{ k.label }}
          </option>
        </select>
      </b-col>
      <b-col>
        <input type="text" v-model="word" />
      </b-col>
      <b-col>
        <button class="btn btn-warning" @click="search()">검색</button>
      </b-col>
      <b-col class="text-right">
        <b-button class="btn btn-warning" @click="moveWrite()">글쓰기</b-button>
      </b-col>
    </b-row>
    <b-row>
      <b-col>
        <b-table
          striped
          hover
          :items="articles"
          :fields="fields"
          @row-clicked="viewArticle"
        >
          <template #cell(subject)="data">
            <router-link
              :to="{
                name: 'qnaview',
                params: { articleno: data.item.articleno },
              }"
            >
              {{ data.item.subject }}
            </router-link>
          </template>
        </b-table>
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
import { listArticle } from "@/api/qna";

export default {
  name: "QnaList",
  data() {
    return {
      articles: [],
      pageNo: "1",
      key: "",
      word: "",
      fields: [
        { key: "articleno", label: "글번호", tdClass: "tdClass" },
        { key: "subject", label: "제목", tdClass: "tdSubject" },
        { key: "userid", label: "작성자", tdClass: "tdClass" },
        { key: "regtime", label: "작성일", tdClass: "tdClass" },
        { key: "hit", label: "조회수", tdClass: "tdClass" },
      ],
    };
  },
  created() {
    this.loadArticles();
  },
  methods: {
    loadArticles() {
      let param = {
        pg: this.pageNo,
        spp: 20,
        key: this.key,
        word: this.word,
      };
      listArticle(
        param,
        ({ data }) => {
          this.articles = data;
        },
        (error) => {
          console.log(error);
        }
      );
    },
    moveWrite() {
      this.$router.push({ name: "qnawrite" });
    },
    viewArticle(article) {
      this.$router.push({
        name: "qnaview",
        params: { articleno: article.articleno },
      });
    },
    search() {
      this.pageNo = "1"; // 검색 후 첫 페이지로 이동
      this.loadArticles();
    },
  },
};
</script>

<style scope>
.tdClass {
  width: 50px;
  text-align: center;
}
.tdSubject {
  width: 300px;
  text-align: left;
}
</style>
