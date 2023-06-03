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
      drawingFlag: false, // 선이 그려지고 있는 상태를 가지고 있을 변수입니다
      moveLine: null, // 선이 그려지고 있을때 마우스 움직임에 따라 그려질 선 객체 입니다
      clickLine: null, // 마우스로 클릭한 좌표로 그려질 선 객체입니다
      distanceOverlay: null, // 선의 거리정보를 표시할 커스텀오버레이 입니다
      dots: {},
    };
  },
  computed: {
    ...mapState(tripStore, ["trips"]),
    ...mapState(tripStore, ["trip"]),
    ...mapState(tripStore, ["plans"]),
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
    plans(newPlans) {
      if (newPlans) {
        this.changePlan(newPlans);
      }
    },
  },
  mounted() {
    // api 스크립트 소스 불러오기 및 지도 출력
    if (window.kakao && window.kakao.maps) {
      this.loadMap();
      this.loadMaker();
      this.changePlan(this.plans);
    } else {
      this.loadScript();
    }
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
    // 마우스 드래그로 그려지고 있는 선의 총거리 정보를 표시하거
    // 마우스 오른쪽 클릭으로 선 그리가 종료됐을 때 선의 정보를 표시하는 커스텀 오버레이를 생성하고 지도에 표시하는 함수입니다
    showDistance(content, position) {
      if (this.distanceOverlay) {
        // 커스텀오버레이가 생성된 상태이면

        // 커스텀 오버레이의 위치와 표시할 내용을 설정합니다
        this.distanceOverlay.setPosition(position);
        this.distanceOverlay.setContent(content);
      } else {
        // 커스텀 오버레이가 생성되지 않은 상태이면

        // 커스텀 오버레이를 생성하고 지도에 표시합니다
        this.distanceOverlay = new kakao.maps.CustomOverlay({
          map: this.map, // 커스텀오버레이를 표시할 지도입니다
          content: content, // 커스텀오버레이에 표시할 내용입니다
          position: position, // 커스텀오버레이를 표시할 위치입니다.
          xAnchor: 0,
          yAnchor: 0,
          zIndex: 3,
        });
      }
    },

    // 클릭으로 그려진 선을 지도에서 제거하는 함수입니다
    deleteClickLine() {
      if (this.clickLine) {
        this.clickLine.setMap(null);
        this.clickLine = null;
      }
    },

    // 그려지고 있는 선의 총거리 정보와
    // 선 그리가 종료됐을 때 선의 정보를 표시하는 커스텀 오버레이를 삭제하는 함수입니다
    deleteDistnce() {
      if (this.distanceOverlay) {
        this.distanceOverlay.setMap(null);
        this.distanceOverlay = null;
      }
    },

    // 선이 그려지고 있는 상태일 때 지도를 클릭하면 호출하여
    // 클릭 지점에 대한 정보 (동그라미와 클릭 지점까지의 총거리)를 표출하는 함수입니다
    displayCircleDot(position, distance) {
      // 클릭 지점을 표시할 빨간 동그라미 커스텀오버레이를 생성합니다
      var circleOverlay = new kakao.maps.CustomOverlay({
        content: '<span class="dot"></span>',
        position: position,
        zIndex: 1,
      });

      // 지도에 표시합니다
      circleOverlay.setMap(this.map);

      if (distance > 0) {
        // 클릭한 지점까지의 그려진 선의 총 거리를 표시할 커스텀 오버레이를 생성합니다
        var distanceOverlay = new kakao.maps.CustomOverlay({
          content:
            '<div class="dotOverlay">거리 <span class="number">' +
            distance +
            "</span>m</div>",
          position: position,
          yAnchor: 1,
          zIndex: 2,
        });

        // 지도에 표시합니다
        distanceOverlay.setMap(this.map);
      }

      // 배열에 추가합니다
      this.dots.push({ circle: circleOverlay, distance: distanceOverlay });
    },

    // 클릭 지점에 대한 정보 (동그라미와 클릭 지점까지의 총거리)를 지도에서 모두 제거하는 함수입니다
    deleteCircleDot() {
      var i;

      for (i = 0; i < this.dots.length; i++) {
        if (this.dots[i].circle) {
          this.dots[i].circle.setMap(null);
        }

        if (this.dots[i].distance) {
          this.dots[i].distance.setMap(null);
        }
      }

      this.dots = [];
    },

    // 마우스 우클릭 하여 선 그리기가 종료됐을 때 호출하여
    // 그려진 선의 총거리 정보와 거리에 대한 도보, 자전거 시간을 계산하여
    // HTML Content를 만들어 리턴하는 함수입니다
    getTimeHTML(distance) {
      // 도보의 시속은 평균 4km/h 이고 도보의 분속은 67m/min입니다
      var walkkTime = (distance / 67) | 0;
      var walkHour = "",
        walkMin = "";

      // 계산한 도보 시간이 60분 보다 크면 시간으로 표시합니다
      if (walkkTime > 60) {
        walkHour =
          '<span class="number">' + Math.floor(walkkTime / 60) + "</span>시간 ";
      }
      walkMin = '<span class="number">' + (walkkTime % 60) + "</span>분";

      // 자전거의 평균 시속은 16km/h 이고 이것을 기준으로 자전거의 분속은 267m/min입니다
      // var bycicleTime = distance / 227 | 0;
      var bycicleTime = (distance / 600) | 0;
      var bycicleHour = "",
        bycicleMin = "";

      // 계산한 자전거 시간이 60분 보다 크면 시간으로 표출합니다
      if (bycicleTime > 60) {
        bycicleHour =
          '<span class="number">' +
          Math.floor(bycicleTime / 60) +
          "</span>시간 ";
      }
      bycicleMin = '<span class="number">' + (bycicleTime % 60) + "</span>분";

      // 거리와 도보 시간, 자전거 시간을 가지고 HTML Content를 만들어 리턴합니다
      var content = '<ul class="dotOverlay distanceInfo">';
      content += "    <li>";
      content +=
        '        <span class="label">총거리</span><span class="number">' +
        distance +
        "</span>m";
      content += "    </li>";
      content += "    <li>";
      content += '        <span class="label">도보</span>' + walkHour + walkMin;
      content += "    </li>";
      content += "    <li>";
      content +=
        '        <span class="label">자동차</span>' + bycicleHour + bycicleMin;
      content += "    </li>";
      content += "</ul>";

      return content;
    },

    changePlan(newPlans) {
      console.log(newPlans);
      // 지도 위에 선이 표시되고 있다면 지도에서 제거합니다
      this.deleteClickLine();

      // 지도 위에 커스텀오버레이가 표시되고 있다면 지도에서 제거합니다
      this.deleteDistnce();

      // 지도 위에 선을 그리기 위해 클릭한 지점과 해당 지점의 거리정보가 표시되고 있다면 지도에서 제거합니다
      this.deleteCircleDot();

      this.clickLine = null;

      console.log("newPlans:", newPlans);

      newPlans.forEach((element) => {
        console.log("lng:", element.latitude);
        var latlng = new kakao.maps.LatLng(element.latitude, element.longitude);
        var clickPosition = latlng;

        // 지도 클릭이벤트가 발생했는데 선을 그리고있는 상태가 아니면
        if (!this.clickLine) {
          // 클릭한 위치를 기준으로 선을 생성하고 지도위에 표시합니다
          this.clickLine = new kakao.maps.Polyline({
            map: this.map, // 선을 표시할 지도입니다
            path: [clickPosition], // 선을 구성하는 좌표 배열입니다 클릭한 위치를 넣어줍니다
            strokeWeight: 3, // 선의 두께입니다
            strokeColor: "#db4040", // 선의 색깔입니다
            strokeOpacity: 1, // 선의 불투명도입니다 0에서 1 사이값이며 0에 가까울수록 투명합니다
            strokeStyle: "solid", // 선의 스타일입니다
          });

          // 선이 그려지고 있을 때 마우스 움직임에 따라 선이 그려질 위치를 표시할 선을 생성합니다
          this.moveLine = new kakao.maps.Polyline({
            strokeWeight: 3, // 선의 두께입니다
            strokeColor: "#db4040", // 선의 색깔입니다
            strokeOpacity: 0.5, // 선의 불투명도입니다 0에서 1 사이값이며 0에 가까울수록 투명합니다
            strokeStyle: "solid", // 선의 스타일입니다
          });

          // 클릭한 지점에 대한 정보를 지도에 표시합니다
          this.displayCircleDot(clickPosition, 0);
        } else {
          // 선이 그려지고 있는 상태이면

          // 그려지고 있는 선의 좌표 배열을 얻어옵니다
          var path = this.clickLine.getPath();

          // 좌표 배열에 클릭한 위치를 추가합니다
          path.push(clickPosition);

          // 다시 선에 좌표 배열을 설정하여 클릭 위치까지 선을 그리도록 설정합니다
          this.clickLine.setPath(path);
          console.log("clickLine:", this.clickLine);
          var distance = Math.round(this.clickLine.getLength());
          this.displayCircleDot(clickPosition, distance);
          var content = this.getTimeHTML(distance);
          console.log("showDistance Called.............");
          this.showDistance(content, path[path.length - 1]);
        }
      });
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
.dot {
  overflow: hidden;
  float: left;
  width: 12px;
  height: 12px;
  background: url("https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/mini_circle.png");
}
.dotOverlay {
  position: relative;
  bottom: 10px;
  border-radius: 6px;
  border: 1px solid #ccc;
  border-bottom: 2px solid #ddd;
  float: left;
  font-size: 12px;
  padding: 5px;
  background: #fff;
}
.dotOverlay:nth-of-type(n) {
  border: 0;
  box-shadow: 0px 1px 2px #888;
}
.number {
  font-weight: bold;
  color: #ee6152;
}
.dotOverlay:after {
  content: "";
  position: absolute;
  margin-left: -6px;
  left: 50%;
  bottom: -8px;
  width: 11px;
  height: 8px;
  background: url("https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/vertex_white_small.png");
}
.distanceInfo {
  position: relative;
  top: 5px;
  left: 5px;
  list-style: none;
  margin: 0;
}
.distanceInfo .label {
  display: inline-block;
  width: 50px;
}
.distanceInfo:after {
  content: none;
}
</style>
