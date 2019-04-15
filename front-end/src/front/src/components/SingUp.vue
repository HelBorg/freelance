<template>
  <div>
  <h3 class="lead">
    Create new user
  </h3>
  <div class="ml-auto mr-auto pt-20" style="width:40%">
    <form @submit.prevent="singup">
      <h2 class="lead">Name</h2>
      <b-input  v-model="usr_name" id="user_name" class="mb-3" ></b-input>
      <h2 class="lead">Email</h2>
      <b-input  v-model="usr_email" id="user_email" class="mb-3" ></b-input>
      <h2 class="lead">Password</h2>
      <b-input  v-model="usr_password" type="password" id="user_password" class="mb-3"></b-input>
      <h2 class="lead">Confirm password</h2>
      <b-input type="password" id="user_password_c" class="mb-3"></b-input>
      <div class="d-inline">
        <b-button variant="primary" style="float:right" type="submit">Sign up</b-button>
      </div>
    </form>
  </div>
  </div>
</template>
<script>

import axios from "axios";

export default {
  name:"singup",
  data(){
      return {
          usr_name:'',
          usr_email:'',
          usr_password:''
      }
  },
    methods: {
      singup() {
        axios.post('http://localhost:80/api/v1/person', {
          name:this.usr_name,
          email:this.usr_email,
          password:this.usr_password
        })
          .then(function (response) {
            console.log(response);
          })
          .catch(function (error) {
            alert("User already exists")
            console.log(error);
          });
    }
  }
}
</script>
