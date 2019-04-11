<template>
  <div id="container">
    <div>
      <Navbar/>
    </div>
    <div>
      <Menu/>
    </div>
    <div>
      <p>Welcome!</p>
      <router-view>

      </router-view>
    </div>
  </div>
</template>


<script>
  import Navbar from "./Navbar";
  import Menu from "./Menu";
  export default {
    name: 'home',
    components: {Menu, Navbar}
  }
</script>
