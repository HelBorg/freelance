<template>
  <div class="container">
    <div class="col-md-6 mx-auto text-center">
      <div class="header-title">
        <h1 class="wv-heading--title">
          Welcome, login please!
        </h1>
      </div>
    </div>
    <div class="row">
      <div class="col-md-4 mx-auto">
        <div class="myform form ">
            <div class="form-group">
              <b-input v-model="email" type="email" name="email"  class="form-control my-input" id="email" placeholder="Email"></b-input>
            </div>
            <div class="form-group">
              <b-input v-model="password" type="password" min="0" name="password" id="password"  class="form-control my-input" placeholder="Password"></b-input>
            </div>
            <div class="text-center ">
              <b-button @click="login" type="submit" variant="primary" class=" btn btn-block send-button tx-tfm">Log In</b-button>
              <b-button variant="outline-secondary" type="submit" class="mt-2" :to="'singup'">Not a member? Sing Up</b-button>
            </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>

  import * as types from '../store/mutation-types'

  export default {
    name: 'Login',
    data() {
      return {
        email: '',
        password: '',
        error: false
      }
    },
    methods: {
      login() {
        const self = this;
        self.$store.dispatch(types.LOGIN, {
          email: self.email,
          password: self.password
        }).then((response) => {
          if (response.status === 200) {
            response.json().then(json => {
              self.$store.commit(types.LOGIN_SUCCESS, {
                token: json.token,
                email: self.email
              });
              self.$nextTick(() => {
                self.$router.replace('/home')
              });
            })
          } else if (response.status === 401) {
            self.log = 401;
            response.json().then(json => {
              self.error = json
            });
            self.$store.commit(types.LOGIN_WRONG_CREDENTIALS);
            self.alert("Wrong email or password");
            self.$router.push('/')
          } else {
            self.log = 'else: ' + response.status;
            response.json().then(json => {
              this.error = json
            });
            self.$router.push('/');
            self.$store.commit(types.LOGIN_ERROR)
          }
        }).catch(json => {
          self.error = 'Unable to connect server.'
        })
      }
    }
  }

</script>

<style scoped>

</style>
