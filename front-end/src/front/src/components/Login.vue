  <template>
    <div class="login-wrapper border border-light m-auto">
      <form class="form-signin" @submit.prevent="login">
        <h2 class="form-signin-heading">Please sign in</h2>
        <label for="inputEmail" class="sr-only">Email address</label>
        <input v-model="email" type="email" id="inputEmail" class="form-control" placeholder="Email address" required autofocus>
        <label for="inputPassword" class="sr-only">Password</label>
        <input v-model="password" type="password" id="inputPassword" class="form-control" placeholder="Password" required>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
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
        const self = this
        this.$store.dispatch(types.LOGIN, {
          email: this.email,
          password: this.password
        }).then((response) => {
          if (response.status === 200) {
            response.json().then(json => {
              this.$store.commit(types.LOGIN_SUCCESS, {
                token: json.token,
                email: self.email
              })
              this.$router.push('/home')
            })
          } else if (response.status === 401) {
            this.log = 401
            response.json().then(json => {
              this.error = json
            })
            this.$store.commit(types.LOGIN_WRONG_CREDENTIALS)
            this.$router.push('/')
          } else {
            this.log = 'else: ' + response.status
            response.json().then(json => {
              this.error = json
            })
            this.$router.push('/')
            this.$store.commit(types.LOGIN_ERROR)
          }
        }).catch(json => {
          this.error = 'Unable to connect server.'
        })
      }
    }
    /*  login () {
        this.$http.post('/singin', { email: this.email, password: this.password })
          .then(request => this.loginSuccessful(request))
          .catch(() => this.loginFailed())
      },
      loginSuccessful (req) {
        if (!req.data.token) {
          this.loginFailed()
          return
        }else
        localStorage.token = req.data.token
        this.error = false

        this.$router.replace(this.$route.query.redirect || '/home')
      },

      loginFailed () {
        this.error = 'Login failed!'
        delete localStorage.token
      }
    }*/
  }

</script>

<style scoped>

</style>
