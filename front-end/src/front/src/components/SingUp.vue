<template>
  <div class="container">
    <div class="col-md-6 mx-auto text-center">
      <div class="header-title">
<!--
        <b-alert ref="success_alert" variant="success">User successfully created! Now you will redirect to Login page</b-alert>
-->
        <h1 class="wv-heading--title">
          Welcome to <strong>Web-free</strong>, get your free account!
        </h1>
      </div>
    </div>
    <div class="row">
      <div class="col-md-4 mx-auto">
        <div class="myform form ">
            <div class="form-group">
              <b-input v-model="usr_name" type="text" name="name"  class="form-control my-input" id="name" placeholder="Name"></b-input>
            </div>
            <div class="form-group">
              <b-input v-model="usr_email" type="email" name="email"  class="form-control my-input" id="email" placeholder="Email"></b-input>
            </div>
            <div class="form-group">
              <b-input v-model="usr_password" type="password" min="0" name="password" id="password"  class="form-control my-input" placeholder="Password"></b-input>
            </div>
            <div class="form-group">
              <b-input v-model="confirm_password" type="password" min="0" name="password" id="password"  class="form-control my-input" placeholder="Confirm password"></b-input>
            </div>
            <div class="text-center ">
              <b-button @click="singup" type="submit" variant="primary" class=" btn btn-block send-button tx-tfm">Create Your  Account</b-button>
            </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>

import axios from "axios";
import router from "../router";
import * as types from '../store/mutation-types'


export default {
  name:"singup",
  data(){
      return {

          usr_name:'',
          usr_email:'',
          usr_password:'',
          confirm_password:''

      }
  },
    methods: {
      singup() {
        let self = this
        if(self.usr_password === self.confirm_password) {
          axios.post('http://localhost:80/api/v1/person', {
            name: self.usr_name,
            email: self.usr_email,
            password: self.usr_password
          })
            .then(function (response) {
              alert("User successfully created");
              router.push('/login');
            })
            .catch(function (error) {
              alert("User already exists");
              console.log(error);
            });
        }else{
          alert('Passwords not matches')
        }

    }
  }
}
</script>
<style>
  .send-button{
    background: #54C7C3;
    width:100%;
    font-weight: 600;
    color:#fff;
    padding: 8px 25px;
  }
  input[type=number]::-webkit-inner-spin-button,
  input[type=number]::-webkit-outer-spin-button {
    -webkit-appearance: none;
    margin: 0;
  }
  .g-button{
    color: #fff !important;
    border: 1px solid #EA4335;
    background: #ea4335 !important;
    width:100%;
    font-weight: 600;
    color:#fff;
    padding: 8px 25px;
  }
  .my-input{
    box-shadow: inset 0 1px 2px rgba(0, 0, 0, 0.1);
    cursor: text;
    padding: 8px 10px;
    transition: border .1s linear;
  }
  .header-title{
    margin: 5rem 0;
  }
  h1{
    font-size: 31px;
    line-height: 40px;
    font-weight: 600;
    color:#4c5357;
  }
  h2{
    color: #5e8396;
    font-size: 21px;
    line-height: 32px;
    font-weight: 400;
  }
  .login-or {
    position: relative;
    color: #aaa;
    margin-top: 10px;
    margin-bottom: 10px;
    padding-top: 10px;
    padding-bottom: 10px;
  }
  .span-or {
    display: block;
    position: absolute;
    left: 50%;
    top: -2px;
    margin-left: -25px;
    background-color: #fff;
    width: 50px;
    text-align: center;
  }
  .hr-or {
    height: 1px;
    margin-top: 0px !important;
    margin-bottom: 0px !important;
  }
  @media screen and (max-width:480px){
    h1{
      font-size: 26px;
    }
    h2{
      font-size: 20px;
    }
  }
</style>
