<template>
  <div id="main">
    <!-- ======= Intro Single ======= -->

    <section class="intro-single">
      <div class="container">
        <div class="row">
          <div class="col-md-12 col-lg-8">
            <div class="title-single-box">
              <h1 class="title-single">나의 여행 계획</h1>
              <!-- <span class="color-text-a">서울</span> -->
            </div>
          </div>
          <div class="col-md-12 col-lg-4">
            <nav aria-label="breadcrumb" class="breadcrumb-box d-flex justify-content-lg-end">
              <ol class="breadcrumb">
                <li class="breadcrumb-item"><a href="${root}/index.jsp">Home</a></li>
                <li class="breadcrumb-item">
                  <a href="recommendByLocation.html">나의 여행 계획</a>
                </li>
                <!-- <li class="breadcrumb-item active" aria-current="page">
                  304 Blaster Up
                </li> -->
              </ol>
            </nav>
          </div>
        </div>
      </div>
    </section>
    <!-- End Intro Single-->

    <div class="d-flex justify-content-center search-spot w-75">
      <form class="d-flex justify-content-center w-100" onsubmit="return false;" role="search">
        <select
          id="search-area"
          class="form-select me-2 w-100"
          v-on:change="getGugunList($event)"
          :value="sidoCode"
        >
          <option value="0" selected>검색 할 지역 선택</option>
          <option v-for="sido in sidoList" :key="sido.sidoCode" :value="sido.sidoCode">
            {{ sido.sidoName }}
          </option>
          <!-- <c:forEach items="${sido}" var="sido">
                        <tr v-for="book in books" :key="book.isbn"></tr>
                        <board-list-item v-for="article in articles" :key="article.articleNo" :article="article"></board-list-item>
                        <option value="${sido.sidoCode}">${sido.sidoName}</option>
                    </c:forEach> -->
        </select>
        <select
          id="search-gugun"
          class="form-select me-2 w-100"
          v-on:change="setGugun($event)"
          :value="gugunCode"
        >
          <option value="0" selected>검색 할 지역 선택</option>
          <option v-for="gugun in gugunList" :key="gugun.gugunCode" :value="gugun.gugunCode">
            {{ gugun.gugunName }}
          </option>
        </select>
        <select
          id="search-content-id"
          class="form-select me-2 w-100"
          v-on:change="setcontentTypeId($event)"
          :value="contentTypeId"
        >
          <option value="0" selected>관광지 유형</option>
          <option value="12">관광지</option>
          <option value="14">문화시설</option>
          <option value="15">축제공연행사</option>
          <option value="25">여행코스</option>
          <option value="28">레포츠</option>
          <option value="32">숙박</option>
          <option value="38">쇼핑</option>
          <option value="39">음식점</option>
        </select>
        <button
          id="btn-search"
          class="btn btn-outline-success w-50"
          type="button"
          style="width: 200px"
          @click="getAttrList()"
        >
          검색
        </button>
      </form>
    </div>

    <div class="d-flex align-items-top result-spot w-75">
      <!-- kakao map start -->
      <!-- <div id="map" class="mt-3 " style="width: 700px; height: 700px"></div>> -->
      <TheKakaoMap ref="kakaomap"></TheKakaoMap>
      <!-- kakao map end -->
      <div style="width: 5%"></div>

      <draggable
        v-if="attrList.length"
        class="dragArea list-group"
        :group="{ name: 'people', pull: 'clone', put: false }"
        :list="attrList"
        @change="log"
      >
        <tr
          v-for="trip in attrList"
          :key="trip.contentId"
          :value="trip.contentId"
          v-bind="attrList"
          class="list-group-item"
        >
          <td :value="trip" @click="moveCenter(trip)"><img :src="trip.imgPath" width="100px" /></td>
          <td :value="trip" @click="moveCenter(trip)">{{ trip.title }}</td>
          <!-- <td :value="trip" @click= "moveCenter(trip)">{{trip.address1}} {{trip.address2}} </td> -->
          <td :value="trip">
            <input
              type="button"
              @click="add(trip)"
              id="addButton"
              :name="trip.contentId"
              value="+"
            />
          </td>
        </tr>
      </draggable>

      <h3>내 일정</h3>

      <div>
        <button v-for="(trip, index) in myPlan" :key="trip" @click="showList(index)">
          day {{ index }}
        </button>
      </div>
      <div>
        <button @click="addPlan()">여행일 추가</button>
      </div>

      <draggable
        v-if="this.selectedMyPlan !== null && this.selectedMyPlan.length"
        class="dragArea list-group"
        :list="selectedMyPlan"
        group="people"
        @change="log"
        v-bind="selectedMyPlan"
      >
        <div
          class="list-group-item"
          v-for="(trip, index) in selectedMyPlan"
          :key="trip.contentId"
          v-bind="selectedMyPlan"
        >
          <td><img :src="trip.imgPath" width="100px" /></td>
          <td>
            {{ trip.title
            }}<input
              type="button"
              @click="pop(index)"
              id="addButton"
              :name="trip.contentId"
              value="-"
            />
          </td>
        </div>
      </draggable>
    </div>
  </div>
</template>

<script>
import http from "@/api/http";
import TheKakaoMap from "@/components/TheKakaoMap";
import store from "@/store";
import draggable from "vuedraggable";
export default {
  name: "TripPlan",
  components: {
    TheKakaoMap,
    draggable,
  },
  data() {
    return {
      days: 0,
      sidoCode: "",
      gugunCode: "",
      contentTypeId: "",
      gugunList: [],
      attrList: [],
      sidoList: [],
      myPlan: [[]],
      selectedMyPlan: [],
    };
  },
  created() {
    this.getSidoList();
  },
  methods: {
    log: function (evt) {
      window.console.log(evt);
      console.log(this.myPlan);
    },
    add: function (trip) {
      console.log("added......");
      console.log(trip);
      this.selectedMyPlan.push(trip);
    },
    pop: function (index) {
      console.log("deleted......");
      this.selectedMyPlan.splice(index, 1);
    },
    addPlan: function () {
      this.days++;
      this.myPlan.push([]);
    },
    showList: function (index) {
      this.selectedMyPlan = this.myPlan[index];
    },
    getAttrList() {
      console.log(this.sidoCode);
      console.log(this.gugunCode);
      console.log(this.contentTypeId);
      console.log(this.myPlan);
      http
        .get(
          `trip/attrs?sidoCode=${this.sidoCode}&gugunCode=${this.gugunCode}&contentTypeId=${this.contentTypeId}`
        )
        .then((response) => {
          //200 ok 됐을때  응답 데이타를 받았을 때
          console.log(response);
          if (response.data.attrList) {
            this.attrList = response.data.attrList;
          } else {
            this.attrList = [];
            alert("조회한 데이타 정보가 없습니다.");
          }
          console.log("attrList.........", this.attrList);
        })
        .catch((err) => {
          console.log(err);
          alert(err.response.data);
        });
    },
    getGugunList(e) {
      this.sidoCode = e.target.value;

      console.log(this.sidoCode);
      http
        .get(`trip/attr/${this.sidoCode}`)
        .then((response) => {
          //200 ok 됐을때  응답 데이타를 받았을 때
          console.log(response);
          if (response.data.gugunList) {
            this.gugunList = response.data.gugunList;
          } else {
            this.gugunList = [];
            alert("조회한 데이타 정보가 없습니다.");
          }
          console.log("gugunList.........", this.gugunList);
        })
        .catch((err) => {
          console.log(err);
          alert(err.response.data);
        });
    },
    setGugun(e) {
      this.gugunCode = e.target.value;
    },
    setcontentTypeId(e) {
      this.contentTypeId = e.target.value;
    },
    getSidoList() {
      http
        .get(`trip`)
        .then((response) => {
          //200 ok 됐을때  응답 데이타를 받았을 때
          console.log(response);
          if (response.data.sidoList) {
            this.sidoList = response.data.sidoList;
          } else {
            this.sidoList = [];
            alert("조회한 데이타 정보가 없습니다.");
          }
          console.log("sidoList.........", this.sidoList);
        })
        .catch((err) => {
          console.log(err);
          alert(err.response.data);
        });
    },
    increaseLikeCount(e) {
      const curTrip = e.target.value;
      console.log(curTrip);
      const contentId = curTrip.id;
      this.checkAuthor();
      const checkUserInfo = store.getters["memberStore/checkUserInfo"];
      console.log(checkUserInfo);

      http.post(`trip/hotplace`, {
        contentId,
        uid: checkUserInfo.id,
      });
    },
    async moveCenter(e) {
      const curTrip = e;
      console.log(curTrip);
      // console.log(selectedcontentId)
      // const curTrip = this.attrList.filter(contentId => selectedcontentId == contentId);
      await this.$nextTick();
      console.log(this.$refs);
      this.$refs.kakaomap.moveCenter(curTrip);
    },
    async checkAuthor() {
      const checkUserInfo = store.getters["memberStore/checkUserInfo"];
      const checkToken = store.getters["memberStore/checkToken"];
      let token = sessionStorage.getItem("access-token");
      console.log("로그인 처리 전", checkUserInfo, token);

      if (checkUserInfo != null && token) {
        console.log("유저 검증 완료");
        await store.dispatch("memberStore/getUserInfo", token);
      }
      if (!checkToken || checkUserInfo === null) {
        alert("로그인이 필요합니다.");
        // next({ name: "login" });
      } else {
        console.log("좋아요 권한 승인 완료");
      }
    },
  },
};
</script>
