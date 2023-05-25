<template>
  <div id="app">
    <the-header-navbar></the-header-navbar>
    <router-view></router-view>
    <br /><br />
    <the-footer></the-footer>
  </div>
</template>

<script>
import TheHeaderNavbar from "@/components/TheHeaderNavbar";
import TheFooter from "@/components/TheFooter";

export default {
  name: "App",
  components: {
    TheHeaderNavbar,
    TheFooter,
  },
};
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #42576d;
}

.title {
  margin-top: 50px;
  margin-bottom: 50px;
}
</style>
