<template>
  <div id="navbar">
    <b-navbar toggleable="lg" type="dark" variant="dark" class="margin-down-10">
      <!-- Надо найти картинку-->
      <b-navbar-brand href="#">
        <img src="https://cdn3.iconfinder.com/data/icons/web-and-graphic-design-vol-2/64/Freelance-128.png" class="d-inline-block align-top" alt="Kitten">
      </b-navbar-brand>

    <b-navbar-brand href="#">Freelance</b-navbar-brand>

    <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

    <b-collapse id="nav-collapse" is-nav>

      <!-- Right aligned nav items -->
      <b-navbar-nav class="ml-auto">
          <!-- Using 'button-content' slot -->
          <b-nav-item @click="logout">Logout</b-nav-item>
      </b-navbar-nav>
    </b-collapse>
  </b-navbar>
  </div>
</template>

<script>
  import * as types from '../store/mutation-types'
  export default {
    beforeMount(){
      this.getusername()
    },
        name: "Navbar",
        data(){
          return{
            username:''
          }
        },
        methods: {
          getusername(){
            let self = this;
            fetch('/api/v1/me', {
              method: 'GET',
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
                    self.username = data.name
                  })
                }
              )
          },
        logout () {
          this.$store.dispatch(types.LOGOUT)
        }
    }
  }
</script>

<style scoped>
 .margin-down-10{
   margin-bottom: 10px;
 }
</style>
