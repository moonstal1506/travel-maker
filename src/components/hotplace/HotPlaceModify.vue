<template>
  <b-container class="bv-example-row mt-3">
    <div class="title">
      <h3><b-icon icon="journals"></b-icon> 글수정</h3>
    </div>
    <hot-place-input-item type="modify" />
  </b-container>
</template>

<script>
import HotPlaceInputItem from "@/components/hotplace/item/HotPlaceInputItem";

export default {
  name: "HotPlaceModify",
  components: {
    HotPlaceInputItem,
  },
};
</script>

<style></style>
