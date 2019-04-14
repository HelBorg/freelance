<template>
  <b-card
    header="Menu">
          <b-list-group class="w-20">
            <b-list-group-item :to="'task'">Task</b-list-group-item>
            <b-list-group-item :to="'home'">Search</b-list-group-item>
            <b-list-group-item button>In work
              <b-badge variant="primary" pill>4</b-badge>
            </b-list-group-item>
            <b-list-group-item button>Candidates
              <b-badge variant="primary" pill>7</b-badge>
            </b-list-group-item>
            <b-list-group-item button>Mine</b-list-group-item>
          </b-list-group>
          <main role="main" class="col-sm-9 ml-sm-auto col-md-10 pt-3">
          </main>
  </b-card>
</template>

<script>
  export default {
    name: "Menu"
  }
</script>

<style scoped>

</style>
