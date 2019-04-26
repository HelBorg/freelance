<template>
  <b-card title="Menu">
        <b-list-group class="w-20">
          <b-list-group-item :to="'tasks/mine'">
            Mine
          </b-list-group-item>
          <b-list-group-item :to="'tasks/candidates'">
            Candidates
          </b-list-group-item>
          <b-list-group-item :to="'tasks/in_work'">
            In work
          </b-list-group-item>
          <b-list-group-item>
            <router-link @click="createNewTask" to="/task">Create New</router-link>
          </b-list-group-item>
          <b-list-group-item :to="'tasks/search'">
            Search
          </b-list-group-item>
          <b-list-group-item :to="'users'">Users</b-list-group-item>
        </b-list-group>
  </b-card>
</template>

<script>

  export default {
    name: "Menu",
    data() {
      return {}
    },
    methods: {
      createNewTask() {
        fetch('/api/v1/task/new', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + localStorage.getItem('JWT')
          }
        })
          .then(
            function (response) {
              if (response.status !== 200) {
                console.log('Looks like there was a problem. Status Code: ' +
                  response.status);
                return;
              }
              response.json().then(function (data) {
                console.log(data);
                localStorage.setItem('loadedTask', data.id)
              })
            }
          )
      }
    }
  }

</script>

<style scoped>

</style>
