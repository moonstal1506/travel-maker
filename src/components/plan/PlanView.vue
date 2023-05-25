<template>
  <b-container class="bv-example-row mt-3 text-center">
    <div class="title">
      <h2 class="post-title">🗼나만의 여행 계획</h2>
      <p class="post-subtitle">나만의 여행 계획을 공유해보아요</p>
    </div>
    <hr class="my-4" />
    <h2>{{ plan.planTitle }}</h2>
    <p>{{ plan.date }}</p>
    <hr class="my-4" />

    <div class="history">
      <div class="circle"></div>
      <div
        class="item"
        :class="['logo', index % 2 !== 0 ? 'item_right' : '']"
        v-for="(trip, index) in plan.trips"
        :key="index"
      >
        <b-img
          :src="trip.first_image || require('@/assets/mango.jpg')"
          alt="trip"
          width="50"
          height="50"
        />
        <div class="contents">
          <div class="year">{{ trip.title }}</div>
          <div class="title">{{ trip.addr1 }}</div>
        </div>
      </div>
      <div class="circle"></div>
    </div>
    <br />
    <p>{{ plan.content }}</p>

    <b-row class="mb-1">
      <b-col class="text-left">
        <b-button variant="outline-primary" @click="moveList">목록</b-button>
      </b-col>
      <b-col class="text-right" v-if="userInfo ? userInfo.userid === plan.userid : false">
        <b-button variant="outline-info" size="sm" @click="moveModifyArticle" class="mr-2"
          >글수정</b-button
        >
        <b-button variant="outline-danger" size="sm" @click="remove">글삭제</b-button>
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
// import CommentWrite from "@/components/board/comment/CommentWrite";
// import CommentList from "@/components/board/comment/CommentList";
import { getPlan, deletePlan } from "@/api/plan";
import { mapState } from "vuex";
const memberStore = "memberStore";

export default {
  name: "PlanDetail",
  components: {
    // CommentWrite,
    // CommentList,
  },
  data() {
    return {
      plan: {},
    };
  },
  computed: {
    ...mapState(memberStore, ["userInfo"]),
    message() {
      if (this.plan.content) return this.plan.content.split("\n").join("<br>");
      return "";
    },
  },
  created() {
    let param = this.$route.params.planId;
    getPlan(
      param,
      ({ data }) => {
        this.plan = data;
      },
      (error) => {
        console.log(error);
      }
    );
  },
  methods: {
    // loadComments() {
    //   this.$refs.CommentList.loadComments();
    // },
    // moveModifyPlan() {
    //   this.$router.replace({
    //     name: "planmodify",
    //     params: { planId: this.plan.planId },
    //   });
    // },
    remove() {
      let param = this.$route.params.planId;
      if (!confirm("정말로 삭제하시겠습니까?")) {
        return;
      }
      deletePlan(
        param,
        ({ data }) => {
          let msg = "삭제 처리시 문제가 발생했습니다.";
          if (data === "success") {
            msg = "삭제가 완료되었습니다.";
          }
          alert(msg);
          this.$router.push({ name: "planlist" });
        },
        (error) => {
          console.log(error);
        }
      );
    },
    moveList() {
      this.$router.push({ name: "planlist" });
    },
  },
};
</script>

<style scoped>
a {
  text-decoration: none;
  color: rgb(33, 33, 33);
}
.circle {
  width: 20px;
  height: 20px;
  background-color: #6baba3;
  border-radius: 20px;
  border: 1px solid #6baba3;
  margin: auto;
}

.header {
  background-color: #6baba3;
  text-align: center;
  padding: 30px;
}

.header .title {
  text-transform: uppercase;
  border-bottom: 1px solid #c4dddb;
  font-weight: 500;
  font-size: 50px;
  color: #222c3f;
  margin-bottom: 0px;
}

.header .subtitle {
  margin-top: 5px;
  font-weight: 400;
  color: #222c3f;
}

.history {
  background-color: #fdfdfd;
  padding: 10px;
  position: relative;
}

.history:before {
  content: " ";
  position: absolute;
  left: 50%;
  top: 0;
  bottom: 0;
  background-color: #6baba3;
  width: 4px;
  margin-left: -2px;
  margin-top: 10px;
  margin-bottom: 10px;
}

.item {
  width: 50%;
  padding: 10px;
  text-align: right;
  position: relative;
  box-sizing: border-box;
  overflow: hidden;
}

.item_right {
  text-align: left;
  margin-left: auto;
  margin-top: -50px;
  margin-bottom: -50px;
}

.item .logo {
  vertical-align: top;
  margin: 15px;
  padding: 5px;
  background-color: white;
  border-radius: 10px;
  text-align: center;
  float: right;
}

.item .logo img {
  vertical-align: top;
}

.item .logo:after {
  content: " ";
  background-color: #6baba3;
  height: 4px;
  width: 25px;
  position: absolute;
  top: 54px;
  right: 0;
}

.item_right .logo:after {
  left: 0;
}

.item_right .logo {
  float: left;
}

.item .contents {
  overflow: hidden;
}

.item .contents .year {
  font-size: 20px;
  color: #e25744;
  font-weight: bolder;
  margin: -10px 0;
  margin-bottom: 5px;
  padding-top: 10px;
}

.item .contents .title {
  font-size: 15px;
  color: #6baba3;
  font-weight: bold;
  margin: 0;
}

.item .contents .description {
  margin: 0;
  max-width: 300px;
  display: inline-block;
  color: rgb(133, 133, 133);
}

@media (max-width: 720px) {
  .history:before {
    display: none;
  }

  .item {
    width: auto;
    text-align: center;
    margin: 0;
  }

  .item .logo {
    float: none;
    display: inline-block;
  }

  .item .logo:after {
    display: none;
  }
}
</style>
