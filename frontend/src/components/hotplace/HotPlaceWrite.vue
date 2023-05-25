<template>
  <b-container class="bv-example-row mt-3">
    <div class="title">
      <h3><b-icon icon="journals"></b-icon> 글작성</h3>
    </div>
    <hot-place-input-item type="register" />
  </b-container>
</template>

<script>
import HotPlaceInputItem from "@/components/hotplace/item/HotPlaceInputItem";

export default {
  name: "HotPlaceWrite",
  components: {
    HotPlaceInputItem,
  },
};
</script>

<style></style>
