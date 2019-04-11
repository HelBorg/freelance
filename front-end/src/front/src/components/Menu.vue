<template>
  <div id="menu">
    <div class="container-fluid ">
      <div class="row">
        <b-list-group class="w-20">
          <b-list-group-item :to="'task'">Task</b-list-group-item>
          <b-list-group-item button>Search</b-list-group-item>
          <b-list-group-item button>In work
            <b-badge variant="primary" pill>4</b-badge></b-list-group-item>
          <b-list-group-item button>Candidates
            <b-badge variant="primary" pill>7</b-badge></b-list-group-item>
          <b-list-group-item button>Mine</b-list-group-item>
          <b-list-group-item href="#" active>Messages</b-list-group-item>
          <b-list-group-item button>Inbox
            <b-badge variant="primary" pill>7</b-badge></b-list-group-item>
          <b-list-group-item button>Outbox</b-list-group-item>
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
