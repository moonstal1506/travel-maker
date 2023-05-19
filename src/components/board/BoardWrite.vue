<template>
  <b-container class="bv-example-row mt-3">
    <div class="title">
      <h3><b-icon icon="journals"></b-icon> 글작성</h3>
    </div>
    <board-input-item type="register" />
  </b-container>
</template>

<script>
import BoardInputItem from "@/components/board/item/BoardInputItem";

export default {
  name: "BoardWrite",
  components: {
    BoardInputItem,
  },
};
</script>

<style></style>
