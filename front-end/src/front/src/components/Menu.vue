<template>
  <div id="menu" style="float:left; width:15%">
      <b-list-group variant="secondary">
        <b-list-group-item style="border-radius:0" variant="secondary" :to="'tasks/mine'">
          Mine
        </b-list-group-item>
        <b-list-group-item  variant="secondary" :to="'tasks/candidates'">
          Candidates
        </b-list-group-item>
        <b-list-group-item  variant="secondary" :to="'tasks/in_work'">
          In work
        </b-list-group-item>
        <b-list-group-item  variant="secondary" @click="createNewTask">
          Create New
        </b-list-group-item>
        <b-list-group-item  variant="secondary" :to="'tasks/search'">
          Search
        </b-list-group-item>
        <b-list-group-item  variant="secondary" :to="'user'">Users</b-list-group-item>
      </b-list-group>
      <main role="main" class="col-sm-9 ml-sm-auto col-md-10 pt-3">
      </main>
  </div>
</template>

<script>
  import router from "../router";
  export default {
    name: "Menu",
    data() {
      return {
        id:'',
      }
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
                console.log(data)
                router.push('/task/' + data.id);
                window.location.reload()
              })
            }
      )
      }
    }
  }
</script>

<style scoped>
  .b-list-item{
    color:whitesmoke;
  }
  .bg{
    background-image: url("https://images.unsplash.com/photo-1510915228340-29c85a43dcfe?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1650&q=80")
  }
</style>
