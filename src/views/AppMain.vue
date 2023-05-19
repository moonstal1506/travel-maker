<template>
  <div>
    <b-img class="main" alt="main" :src="require('@/assets/main.jpg')" />
    <b-container class="bv-example-row mt-3 text-center">
      <div class="title">
        <h2 class="post-title">🐱‍🐉여행지 추천</h2>
        <p class="post-subtitle">여행지 추천해드려요</p>
      </div>
      <hr class="my-4" />
      <div>
        <div class="row">
          <div class="col-md-3" v-for="(card, index) in cards" :key="index">
            <b-card
              :title="card.title"
              :img-src="card.imgSrc"
              img-alt="Image"
              img-top
              tag="article"
              style="max-width: 20rem"
              class="mb-2"
            >
              <b-card-text>{{ card.content }}</b-card-text>
              <b-button :href="card.link" variant="success"
                >Go somewhere</b-button
              >
            </b-card>
          </div>
        </div>
      </div>
    </b-container>
  </div>
</template>

<script>
export default {
  data() {
    return {
      cards: [
        {
          title: "Card 1",
          imgSrc: "https://picsum.photos/300/300/?image=25",
          content: "Some quick example text for Card 1.",
          link: "#",
        },
        {
          title: "Card 2",
          imgSrc: "https://picsum.photos/300/300/?image=50",
          content: "Some quick example text for Card 2.",
          link: "#",
        },
        {
          title: "Card 3",
          imgSrc: "https://picsum.photos/300/300/?image=75",
          content: "Some quick example text for Card 3.",
          link: "#",
        },
        {
          title: "Card 4",
          imgSrc: "https://picsum.photos/300/300/?image=75",
          content: "Some quick example text for Card 3.",
          link: "#",
        },
        {
          title: "Card 1",
          imgSrc: "https://picsum.photos/300/300/?image=25",
          content: "Some quick example text for Card 1.",
          link: "#",
        },
        {
          title: "Card 2",
          imgSrc: "https://picsum.photos/300/300/?image=50",
          content: "Some quick example text for Card 2.",
          link: "#",
        },
        {
          title: "Card 3",
          imgSrc: "https://picsum.photos/300/300/?image=75",
          content: "Some quick example text for Card 3.",
          link: "#",
        },
        {
          title: "Card 4",
          imgSrc: "https://picsum.photos/300/300/?image=75",
          content: "Some quick example text for Card 3.",
          link: "#",
        },
        // Add more cards here
      ],
    };
  },
  name: "AppMain",
  props: {
    msg: String,
  },
};
</script>

<style scoped>
.underline-steelblue {
  display: inline-block;
  background: linear-gradient(
    180deg,
    rgba(255, 255, 255, 0) 70%,
    rgba(72, 190, 233, 0.3) 30%
  );
}

div {
  text-align: center;
}

.main {
  width: 1150px;
  height: 400px;
  margin: 0px auto;
  margin-top: 50px;
}
</style>
