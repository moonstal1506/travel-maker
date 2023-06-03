<template>
  <div>
    <div id="map"></div>
  </div>
</template>

<script>
import { mapState } from "vuex";
const tripStore = "tripStore";

export default {
  name: "KakaoMap",
  components: {},
  data() {
    return {
      map: null,
      positions: [],
      markers: [],
      customOverlay: null,
    };
  },
  props: {
    locations: [],
  },
  watch: {
    locations() {
      this.positions = [];
      this.trips.forEach((trip) => {
        let obj = {};
        obj.title = trip.title;
        obj.latlng = new kakao.maps.LatLng(trip.latitude, trip.longitude);

        this.positions.push(obj);
      });
      this.loadMaker();
    },
    trip(newTrip) {
      if (newTrip) {
        this.moveCenter(newTrip);
      }
    },
  },
  created() {},
  mounted() {
    // api 스크립트 소스 불러오기 및 지도 출력
    if (window.kakao && window.kakao.maps) {
      this.loadMap();
      this.loadMaker();
    } else {
      this.loadScript();
    }
  },
  computed: {
    ...mapState(tripStore, ["trips"]),
    ...mapState(tripStore, ["trip"]),
  },
  methods: {
    // api 불러오기
    loadScript() {
      const script = document.createElement("script");
      script.src =
        "//dapi.kakao.com/v2/maps/sdk.js?appkey=" +
        process.env.VUE_APP_KAKAO_MAP_API_KEY +
        "&autoload=false";
      /* global kakao */
      script.onload = () => window.kakao.maps.load(this.loadMap);

      document.head.appendChild(script);
    },
    // 맵 출력하기
    loadMap() {
      const container = document.getElementById("map");
      const options = {
        center: new window.kakao.maps.LatLng(33.450701, 126.570667),
        level: 5,
      };

      this.map = new window.kakao.maps.Map(container, options);
      //   this.loadMaker();
    },
    // 지정한 위치에 마커 불러오기
    loadMaker() {
      this.positions = [];
      this.trips.forEach((trip) => {
        let obj = {};
        obj.title = trip.title;
        obj.latlng = new kakao.maps.LatLng(trip.latitude, trip.longitude);
        this.positions.push(obj);
      });

      this.deleteMarker();
      this.markers = [];
      this.positions.forEach((position) => {
        const marker = new kakao.maps.Marker({
          map: this.map, // 마커를 표시할 지도
          position: position.latlng, // 마커를 표시할 위치
          title: position.title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
          //   image: markerImage, // 마커의 이미지
        });
        this.markers.push(marker);
        marker.addListener("click", () => {
          const trip = this.trips.find(
            (trip) => trip.title === marker.getTitle()
          );
          this.drawCustomOverlay(trip);
        });
      });
      console.log("마커수 ::: " + this.markers.length);

      // 4. 지도를 이동시켜주기
      // 배열.reduce( (누적값, 현재값, 인덱스, 요소)=>{ return 결과값}, 초기값);
      const bounds = this.positions.reduce(
        (bounds, position) => bounds.extend(position.latlng),
        new kakao.maps.LatLngBounds()
      );

      this.map.setBounds(bounds);
    },
    deleteMarker() {
      console.log("마커 싹 지우자!!!", this.markers.length);
      if (this.markers.length > 0) {
        this.markers.forEach((item) => {
          console.log(item);
          item.setMap(null);
        });
      }
    },
    moveCenter(trip) {
      this.map.setLevel(2);
      this.map.setCenter(new kakao.maps.LatLng(trip.latitude, trip.longitude));
      this.drawCustomOverlay(trip);
    },

    drawCustomOverlay(content) {
      console.log("gg", content);
      if (this.customOverlay != null) this.closeOverlay();

      const tmp = `<div class="wrap">
        <div class="info">
          <div class="title">
            ${content.title}
            <div class="close" @click="closeOverlay" title="닫기"></div>
          </div>
          <div class="body">
            <div class="img">
              <img src=${content.first_image} width="73" height="73"/>
            </div>
            <div class="desc">
              <div class="address1">${content.addr1}</div>
            </div>
          </div>
        </div>
      </div>`;

      const container = document.createElement("div");
      container.innerHTML = tmp;

      const closeButton = container.querySelector(".close");
      closeButton.addEventListener("click", this.closeOverlay); // closeOverlay 메소드를 클릭 이벤트에 연결

      this.customOverlay = new kakao.maps.CustomOverlay({
        content: container,
        position: new kakao.maps.LatLng(content.latitude, content.longitude),
        yAnchor: 1,
        zIndex: 3,
      });

      this.customOverlay.setMap(this.map);
    },
    closeOverlay() {
      // 오버레이를 닫는 기능 구현
      if (this.customOverlay) {
        this.customOverlay.setMap(null); // 오버레이를 지도에서 제거
        this.customOverlay = null; // customOverlay 변수 초기화
      }
    },
  },
};
</script>

<style>
#map {
  width: 100%;
  height: 700px;
}
.wrap {
  position: absolute;
  left: 0;
  bottom: 40px;
  width: 288px;
  height: 132px;
  margin-left: -144px;
  text-align: left;
  overflow: hidden;
  font-size: 12px;
  font-family: "Malgun Gothic", dotum, "돋움", sans-serif;
  line-height: 1.5;
}
.wrap * {
  padding: 0;
  margin: 0;
}
.wrap .info {
  width: 286px;
  height: 120px;
  border-radius: 5px;
  border-bottom: 2px solid #ccc;
  border-right: 1px solid #ccc;
  overflow: hidden;
  background: #fff;
}
.wrap .info:nth-child(1) {
  border: 0;
  box-shadow: 0px 1px 2px #888;
}
.info .title {
  padding: 5px 0 0 10px;
  height: 30px;
  background: #eee;
  border-bottom: 1px solid #ddd;
  font-size: 18px;
  font-weight: bold;
}
.info .close {
  position: absolute;
  top: 10px;
  right: 10px;
  color: #888;
  width: 17px;
  height: 17px;
  background: url("https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/overlay_close.png");
}
.info .close:hover {
  cursor: pointer;
}
.info .body {
  position: relative;
  overflow: hidden;
}
.info .desc {
  position: relative;
  margin: 13px 0 0 90px;
  height: 75px;
}
.desc .ellipsis {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
.desc .jibun {
  font-size: 11px;
  color: #888;
  margin-top: -2px;
}
.info .img {
  position: absolute;
  top: 6px;
  left: 5px;
  width: 73px;
  height: 71px;
  border: 1px solid #ddd;
  color: #888;
  overflow: hidden;
}
.info:after {
  content: "";
  position: absolute;
  margin-left: -12px;
  left: 50%;
  bottom: 0;
  width: 22px;
  height: 12px;
  background: url("https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/vertex_white.png");
}
.info .link {
  color: #5085bb;
}
</style>
