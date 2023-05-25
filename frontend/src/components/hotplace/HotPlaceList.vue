<template>
  <b-container class="bv-example-row mt-3">
    <div class="title">
      <h3><b-icon icon="journals"></b-icon> 핫플 자랑하기</h3>
    </div>
    <!-- 검색 영역 -->
    <b-row class="mb-3">
      <b-col> </b-col>
      <b-col>
        <b-form-select v-model="key">
          <option v-for="(k, index) in fields" :key="index" :value="k.key">
            {{ k.label }}
          </option>
        </b-form-select>
      </b-col>
      <b-col>
        <b-form-input type="text" v-model="word"></b-form-input>
      </b-col>
      <b-col class="text-left">
        <b-button class="mr-4" variant="outline-success" @click="search()"
          >검색</b-button
        >
        <b-button variant="outline-success" @click="moveWrite()"
          >글쓰기</b-button
        >
      </b-col>
    </b-row>
    <!-- 글 목록 -->
    <b-row>
      <b-col>
        <b-container>
          <b-row>
            <b-col
              v-for="(article, index) in articles"
              :key="index"
              class="grid-item"
            >
              <router-link
                :to="{
                  name: 'hotplaceview',
                  params: { articleno: article.articleno },
                }"
              >
                <b-card>
                  <b-card-img
                    :src="article.first_image"
                    :alt="article.subject"
                    top
                  ></b-card-img>
                  <b-card-title>
                    {{ article.subject }}
                  </b-card-title>
                  <b-card-text class="text-muted">
                    작성자: {{ article.userid }}<br />
                    조회수: {{ article.hit }}
                  </b-card-text>
                </b-card>
              </router-link>
            </b-col>
          </b-row>
        </b-container>
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
import { listArticle } from "@/api/hotplace";

export default {
  name: "HotPlaceList",
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
      this.$router.push({ name: "hotplacewrite" });
    },
    viewArticle(article) {
      this.$router.push({
        name: "hotplaceview",
        params: { articleno: article.articleno },
      });
    },
    search() {
      this.pageNo = "1"; // 검색 후 첫 페이지로 이동
      this.loadArticles();
    },
    getArticleImage(article) {
      // 글의 이미지 경로를 반환하는 메서드
      return article.image;
    },
  },
};
</script>

<style scoped>
.tdClass {
  width: 50px;
  text-align: center;
}
.tdSubject {
  width: 300px;
  text-align: left;
}

a {
  text-decoration: none;
  color: rgb(33, 33, 33);
}

.grid-item {
  flex-basis: 33.33%;
  padding: 10px;
}
</style>
