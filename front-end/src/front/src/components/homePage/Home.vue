<template>
  <div id="container">
    <div>
      <Navbar/>
    </div>
    <div>
          <Menu/>
            <div class="hero">
              <h1 class="display-3">Freelance system</h1>
              <p class="lead">Dmitry and Elena corporated (c)</p>
            </div>
  </div>
  </div>
</template>

<script>
  import Navbar from "../Navbar";
  import Menu from "../Menu";
  import Tasks from "../Tasks.vue";

  export default {
    name: 'home',
    pageName: 'welcome',
    data() {
      return {}
    },
    components: {Tasks, Menu, Navbar},
    methods: {
      getPage() {
        this.pageName = this.$router.params.pageName
      }
    }
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
