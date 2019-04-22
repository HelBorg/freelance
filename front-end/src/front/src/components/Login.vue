  <template>
    <div class="ml-auto mr-auto pt-20" style="width:40%">
      <form @submit.prevent="login">
        <h2 class="lead">Enter your credentials</h2>
        <b-input  v-model="email" type="email" id="text-password" class="mb-3 " placeholder="Input email"></b-input>
        <b-input  v-model="password" type="password" id="text-password"  placeholder="Input password" class="mb-3"></b-input>
        <div class="d-inline">
          <b-button variant="outline-secondary" type="submit" :to="'singup'">Not a member? Sing Up</b-button>
          <b-button  style="float:right" variant="primary" type="submit">Login</b-button>
        </div>
      </form>
    </div>
  </template>
<script>

  import * as types from '../store/mutation-types'

  export default {
    name: 'Login',
    data () {
      return {
        email: '',
        password: '',
        error: false
      }
    },
    methods: {
      login () {
        const self = this;
        this.$store.dispatch(types.LOGIN, {
          email: this.email,
          password: this.password
        }).then((response) => {
          if (response.status === 200) {
            response.json().then(json => {
              this.$store.commit(types.LOGIN_SUCCESS, {
                token: json.token,
                email: self.email
              });
              this.$router.push('/home/welcome')
            })
          } else if (response.status === 401) {
            this.log = 401;
            response.json().then(json => {
              this.error = json
            });
            this.$store.commit(types.LOGIN_WRONG_CREDENTIALS);
            this.alert("Wrong email or password");
            this.$router.push('/')
          } else {
            this.log = 'else: ' + response.status;
            response.json().then(json => {
              this.error = json
            });
            this.$router.push('/');
            this.$store.commit(types.LOGIN_ERROR)
          }
        }).catch(json => {
          this.error = 'Unable to connect server.'
        })
      }
    }
  }

</script>

<style scoped>

</style>
