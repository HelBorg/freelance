<template>
  <div id="menu" style="float: left">
    header="Menu">
        <b-list-group>
          <b-list-group-item @click="createNewTask"><router-link to="/task">Create New</router-link></b-list-group-item>
            </b-list-group-item>
            </b-list-group-item>
            <b-list-group-item button>Mine</b-list-group-item>
          <b-list-group-item button>Candidate
            <b-badge variant="primary" pill>4</b-badge></b-list-group-item>
          <b-list-group-item button>In work
          <b-list-group-item button>Common pull</b-list-group-item>
          <b-list-group-item href="#" active>Users</b-list-group-item>

          </b-list-group>
          <main role="main" class="col-sm-9 ml-sm-auto col-md-10 pt-3">
          </main>
  </b-card>
</template>

<script>

  export default {
      name: "Menu",
      data () {
        return {
        }
      },
      methods:{
        createNewTask() {
          fetch('/api/v1/task/new', {
            method: 'POST',
            headers: {
              'Content-Type' : 'application/json',
              'Authorization': 'Bearer ' + localStorage.getItem('JWT')
            }
          })
            .then(
              function(response) {
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
