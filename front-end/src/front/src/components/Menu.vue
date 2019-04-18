<template>
  <b-card title="Menu">
    <div id="menu">
      <div class="container-fluid ">
        <div class="row">
          <b-list-group class="w-20">
            <b-list-group-item @click="$store.commit('changePage', 'mine')"
                               :to="'home'">
              Mine
            </b-list-group-item>
            <b-list-group-item @click="$store.commit('changePage', 'candidate')"
                               :to="'home'">
              Candidates
            </b-list-group-item>
            <b-list-group-item @click="$store.commit('changePage', 'in_work')"
                               :to="'home'">
              In work
            </b-list-group-item>
            <b-list-group-item :to="'task'">Task</b-list-group-item>
            <b-list-group-item @click="$store.commit('changePage', 'tasks')"
                               :to="'home'">
              Search
            </b-list-group-item>
            <b-list-group-item button>Users</b-list-group-item>
          </b-list-group>
          <main role="main" class="col-sm-9 ml-sm-auto col-md-10 pt-3">
          </main>
        </div>
      </div>
    </div>
  </b-card>
</template>

<script>
  export default {
    name: "Menu"
  }
</script>

<style scoped>

</style>
