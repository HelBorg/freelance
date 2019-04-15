<template>
  <div id="menu" class="w-20">
    <div class="container-fluid ">
      <div class="row">
        <b-list-group>
          <b-list-group-item :to="''">Create new</b-list-group-item>
          <b-list-group-item button>Mine</b-list-group-item>
          <b-list-group-item button>Candidate
            <b-badge variant="primary" pill>4</b-badge></b-list-group-item>
          <b-list-group-item button>In work
            <b-badge variant="primary" pill>7</b-badge></b-list-group-item>
          <b-list-group-item button>Common pull</b-list-group-item>
          <b-list-group-item href="#" active>Users</b-list-group-item>

        </b-list-group>
        <main role="main" class="col-sm-9 ml-sm-auto col-md-10 pt-3">
        </main>
      </div>
    </div>
  </div>
</template>

<script>

    export default {
      name: "Menu"
    }


</script>

<style scoped>

</style>
