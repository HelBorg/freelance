<template>
  <div id="menu" style="float: left;width:29%">
    <div class="container-fluid ">
      <b-list-group >
        <b-list-group-item :to="'home/mine'">
          Mine
        </b-list-group-item>
        <b-list-group-item :to="'home/candidates'">
          Candidates
        </b-list-group-item>
        <b-list-group-item :to="'home/in_work'">
          In work
        </b-list-group-item>
        <b-list-group-item>
          <router-link @click="createNewTask" to="/task">Create New</router-link>
        </b-list-group-item>
        <b-list-group-item :to="'home/search'">
          Search
        </b-list-group-item>
        <b-list-group-item :to="'home'">Users</b-list-group-item>
      </b-list-group>
      <main role="main" class="col-sm-9 ml-sm-auto col-md-10 pt-3">
      </main>
    </div>
  </div>
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
