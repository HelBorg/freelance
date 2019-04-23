<template>
  <div id="container">
    <div>
      <Navbar/>
    </div>
    <div>
      <b-col cols="4">
        <Menu/>
      </b-col>
      <b-col>
        <div>
          <div class="hero">
            <h1 class="display-3">Hello World</h1>
            <p class="lead">This is the homepage of our website</p>
          </div>
        </div>
      </b-col>
    </div>
  </div>
</template>

<script>
  import Navbar from "./Navbar";
  import Menu from "./Menu";
  import Table_for_tasks from "./Table_for_tasks";

  export default {
    name: 'home',
    components: {Menu, Navbar}
  }
</script>

<style scoped>
  .hero {
    height: 90vh;
    display: flex;
    align-items: center;
    justify-content: center;
    text-align: center;
  }

  .hero .lead {
    font-weight: 200;
    font-size: 1.5rem;
  }
</style>
