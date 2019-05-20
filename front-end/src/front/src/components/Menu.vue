<template>
  <div id="menu" style="float:left; width:15%">
      <b-list-group variant="light">
        <b-list-group-item style="border-radius:0" variant="outline-primary"  @click="my">
          My tasks
          <b-button @click="createNewTask" size="sm" variant="success" style="float:right">+</b-button>
        </b-list-group-item>
        <b-list-group-item  variant="outline-primary"  @click="candidates">
          Candidates
        </b-list-group-item>
        <b-list-group-item  variant="outline-primary"  @click="inWork">
          In work
        </b-list-group-item>
        <b-list-group-item  variant="outline-primary"  @click="search">
          Search
        </b-list-group-item>
        <b-list-group-item variant="outline-primary" @click="users">Users</b-list-group-item>
      </b-list-group>
      <main role="main" class="col-sm-9 ml-sm-auto col-md-10 pt-3">
      </main>
  </div>
</template>

<script>
  import router from "../router";
  import * as types from '../store/mutation-types'

  export default {
    name: "Menu",
    data() {
      return {
        id:'',
      }
    },
    methods: {
      createNewTask() {
        fetch('/api/v1/task', {
          method: 'POST',
          headers:types.HEADER
        })
          .then(
            function (response) {
              if (response.status !== 200) {
                console.log('Looks like there was a problem. Status Code: ' +
                  response.status);
                return;
              }
              response.json().then(function (data) {
                console.log(data)
                router.push('/task/' + data.id);
                window.location.reload()
              })
            }
          )
      },
      users() {
        router.push('/users');
      },
      my() {
        router.push('/tasks/my');
      },
      candidates() {
        router.push('/tasks/candidates');
      },
      search() {
        router.push('/tasks/search');
      },
      inWork() {
        router.push('/tasks/in_work');
      }
    }
  }
</script>

<style scoped>
</style>
