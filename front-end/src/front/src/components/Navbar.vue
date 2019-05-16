<template>
  <b-navbar toggleable="lg" type="dark" variant="secondary">
    <!-- Надо найти картинку-->

    <b-navbar-brand :to="'/home'">Web-free</b-navbar-brand>

    <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

    <b-collapse id="nav-collapse" is-nav>

      <!-- Right aligned nav items -->
      <b-navbar-nav class="ml-auto">
          <!-- Using 'button-content' slot -->
          <b-nav-item @click="goToUserPage" >Welcome, {{username}} </b-nav-item>
          <b-nav-item @click="logout">Logout</b-nav-item>
      </b-navbar-nav>
    </b-collapse>
  </b-navbar>
</template>

<script>
  import router from "../router";
  import * as types from '../store/mutation-types'


  export default {
    created(){
      this.getusername()
    },
        data(){
          return{
            username:'',
            id:''
          }
        },
        methods: {
          getusername: function ()
          {
            let self = this;
            fetch('/api/v1', {
              method: 'GET',
              headers:{
                'Content-Type': 'application/json',
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
                    self.id = data.id;
                    self.username = data.name
                  })
                }
              )
          },
        logout () {
          this.$store.dispatch(types.LOGOUT)
        },
          goToUserPage(){
            router.replace('/user/' + this.id)
          }
    }
  }
</script>

<style scoped>
 .margin-down-10{
   margin-bottom: 10px;
 }
</style>
