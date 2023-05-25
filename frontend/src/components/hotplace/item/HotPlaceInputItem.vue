<template>
  <b-row class="mb-1">
    <b-col style="text-align: left">
      <b-form @submit="onSubmit" @reset="onReset">
        <b-form-group
          id="subject-group"
          label="제목:"
          label-for="subject"
          description="제목을 입력하세요."
        >
          <b-form-input
            id="subject"
            v-model="article.subject"
            type="text"
            required
            placeholder="제목 입력..."
          ></b-form-input>
        </b-form-group>
        <b-form-group id="content-group" label="내용:" label-for="content">
          <quill-editor
            ref="myTextEditor"
            v-model="article.content"
            :options="editorOption"
            @blur="onEditorBlur"
            @focus="onEditorFocus"
            @ready="onEditorReady"
          ></quill-editor>
        </b-form-group>
        <b-row class="d-flex justify-content-center">
          <b-col cols="6">
            <house-search-bar></house-search-bar>
          </b-col>
        </b-row>
        <b-row class="d-flex justify-content-center">
          <b-col cols="6">
            <!-- house-list 컴포넌트에서 선택한 관광지 정보를 house-detail2로 전달 -->
            <house-list type="plan" @selected="updateSelectedHouse" />
          </b-col>
          <b-col cols="3">
            <house-detail2 :selectedHouse="selectedHouse" />
            <!-- 선택한 관광지 정보를 housedetail2 컴포넌트에 전달 -->
          </b-col>
        </b-row>

        <b-row class="d-flex justify-content-end mt-2">
          <b-col cols="auto">
            <b-button
              type="submit"
              variant="primary"
              class="m-1"
              v-if="this.type === 'register'"
            >
              글작성
            </b-button>
            <b-button
              type="submit"
              variant="outline-success"
              class="m-1"
              v-else
            >
              글수정
            </b-button>
            <b-button type="reset" variant="outline-success" class="m-1"
              >초기화</b-button
            >
          </b-col>
        </b-row>
      </b-form>
    </b-col>
  </b-row>
</template>

<script>
import HouseSearchBar from "@/components/house/HouseSearchBar.vue";
import HouseDetail2 from "@/components/house/HouseDetail2.vue";
import HouseList from "@/components/house/HouseList.vue";
import { writeArticle, modifyArticle, getArticle } from "@/api/hotplace"; // 백엔드 API 호출 관련 모듈 import
import hljs from "highlight.js";
import { quillEditor } from "vue-quill-editor";
import { mapState } from "vuex";
import debounce from "lodash/debounce";
import "highlight.js/styles/tomorrow.css";
import "quill/dist/quill.core.css";
import "quill/dist/quill.snow.css";

const memberStore = "memberStore";
const houseStore = "houseStore";
export default {
  name: "HotPlaceInputItem",
  title: "Theme: snow",
  components: {
    HouseSearchBar,
    HouseList,
    quillEditor,
    HouseDetail2,
  },
  data() {
    return {
      editorOption: {
        placeholder: "내용 입력",
        modules: {
          toolbar: [
            ["bold", "italic", "underline", "strike"],
            ["blockquote", "code-block"],
            [{ header: 1 }, { header: 2 }],
            [{ list: "ordered" }, { list: "bullet" }],
            [{ script: "sub" }, { script: "super" }],
            [{ indent: "-1" }, { indent: "+1" }],
            [{ direction: "rtl" }],
            [{ size: ["small", false, "large", "huge"] }],
            [{ header: [1, 2, 3, 4, 5, 6, false] }],
            [{ font: [] }],
            [{ color: [] }, { background: [] }],
            [{ align: [] }],
            ["video"],
          ],
          syntax: {
            highlight: (text) => hljs.highlightAuto(text).value,
          },
        },
      },
      content: "",
      article: {
        articleno: 0,
        subject: "",
        content: "",
      },
      selectedHouse: null, // 선택한 관광지 정보를 저장하는 변수
    };
  },
  computed: {
    ...mapState(houseStore, ["house"]),
    ...mapState(houseStore, ["houses"]),
    ...mapState(memberStore, ["userInfo"]),
  },
  props: {
    type: { type: String },
  },
  created() {
    if (this.type === "modify") {
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
    }
  },
  methods: {
    onSubmit(event) {
      event.preventDefault();

      let err = true;
      let msg = "";
      !this.article.subject &&
        ((msg = "제목 입력해주세요"),
        (err = false),
        this.$refs.subject.focus());
      err &&
        !this.article.content &&
        ((msg = "내용 입력해주세요"),
        (err = false),
        this.$refs.content.focus());

      if (!err) alert(msg);
      else
        this.type === "register" ? this.registArticle() : this.modifyArticle();
    },
    onReset(event) {
      event.preventDefault();
      this.article.articleno = 0;
      this.article.subject = "";
      this.article.content = "";
      this.moveList();
    },
    registArticle() {
      // let param = {
      //   subject: this.article.subject,
      //   content: this.article.content,
      //   fileup: this.article.fileup,
      // };
      const formData = new FormData();
      formData.append("userid", this.userInfo.userid);
      formData.append("subject", this.article.subject);
      formData.append("content", this.article.content);
      formData.append("contentId", this.house.contentId);
      writeArticle(
        formData,
        ({ data }) => {
          let msg = "등록 처리시 문제가 발생했습니다.";
          if (data === "success") {
            msg = "등록이 완료되었습니다.";
          }
          alert(msg);
          this.moveList();
        },
        (error) => {
          console.log(error);
        }
      );
    },
    modifyArticle() {
      let param = {
        articleno: this.article.articleno,
        subject: this.article.subject,
        content: this.article.content,
        contentId: this.house.contentId,
      };
      modifyArticle(
        param,
        ({ data }) => {
          let msg = "수정 처리시 문제가 발생했습니다.";
          if (data === "success") {
            msg = "수정이 완료되었습니다.";
          }
          alert(msg);
          this.moveList();
        },
        (error) => {
          console.log(error);
        }
      );
    },
    moveList() {
      this.$router.push({ name: "hotplacelist" });
    },
    onEditorChange: debounce(function (value) {
      this.content = value.html;
    }, 466),
    onEditorBlur(editor) {
      console.log("editor blur!", editor);
    },
    onEditorFocus(editor) {
      console.log("editor focus!", editor);
    },
    onEditorReady(editor) {
      console.log("editor ready!", editor);
    },
    updateSelectedHouse(house) {
      this.selectedHouse = house; // 선택한 관광지 정보를 업데이트
    },
  },
};
</script>
