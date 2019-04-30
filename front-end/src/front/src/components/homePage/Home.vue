<template>
  <div id="container" class="bg">
    <div>
      <Navbar/>
    </div>
    <div>
          <Menu/>
    </div>
            <div class="hero">
              <h2 class="display-3" style="color:dimgrey">Freelance system of your company</h2>
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
    float:right;
    width: 80%;
    margin-right: 5%;
    margin-top:15%;
    text-align: center;
  }

  .hero .lead {
    font-weight: 200;
    font-size: 1.5rem;
  }

  .bg{
    background-image: url("https://images.unsplash.com/photo-1510915228340-29c85a43dcfe?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1650&q=80")
  }
</style>
