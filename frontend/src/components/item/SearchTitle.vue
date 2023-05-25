<template>
  <b-row class="mb-3"
    ><b-col cols="11">
      <b-form-input type="text" v-model="title" v-on:keyup.enter="search()"></b-form-input>
    </b-col>
    <b-col cols="1">
      <b-button variant="outline-success" @click="search">검색</b-button>
    </b-col>
  </b-row>
</template>

<script>
export default {
  name: "SearchTitle",
  data() {
    return {
      title: null,
    };
  },
  methods: {
    search() {
      this.$emit("search", this.title);
    },
  },
};
</script>

<style scoped></style>
