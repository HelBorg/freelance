<template>
  <div id="container">
    <div>
      <Navbar/>
    </div>
    <div>
      <Menu/>
      <b-card>
<!--      <div v-if="pageName==='welcome'">-->
<!--        <div class="hero">-->
<!--          <h1 class="display-3">Hello World</h1>-->
<!--          <p class="lead">This is the homepage of our website</p>-->
<!--        </div>-->
<!--      </div>-->
      <div>
        <Table_for_tasks/>
      </div>
      </b-card>
    </div>
  </div>
</template>

<script>
  import Navbar from "../Navbar";
  import Menu from "../Menu";
  import Table_for_tasks from "./Table_for_tasks";

  export default {
    name: 'home',
    pageName: 'tasks',
    data() {
      return {}
    },
    components: {Table_for_tasks, Menu, Navbar},
    methods: {
      getPage() {
        this.pageName = this.$route.params.pageName
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
