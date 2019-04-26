  <template>
        <b-card>
          <b-card-header class="p-2">
            <img src="https://placekitten.com/g/30/30" class="d-inline-block align-top" style=" border-radius:50%;">
            <b-link href="#">@{{comment.user.name}}</b-link>
          <b-button v-if="status === 'PUBLISH'" @click="assignUser(comment.user.id)" class="ml-4 p-1">Assign this user</b-button>
          </b-card-header>
          <b-card-body class="p-2">{{comment.description}}</b-card-body>
          <b-card-footer class="p-2">date: {{comment.createdTime.substring(0, 10)}} </b-card-footer>
        </b-card>
  </template>
<script>
    export default {
      props: {
        comment: Object,
        status: Object
      },
      methods:{
        assignUser(userId){
            let self = this;
            fetch('/api/v1/task/update/assigned/'+ self.$route.params.id + '/' + userId, {
              method: 'POST',
              headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + localStorage.getItem('JWT')
              },
            })
              .then(
                function (response) {
                  if (response.status !== 200) {
                    console.log('Looks like there was a problem. Status Code: ' +
                      response.status);
                    return;
                  }
                  alert("Success!");
                  response.json().then(function (data) {
                    self.status = data;
                    console.log(data)
                  })
                }
              )
        }
      }
    }
</script>

<style scoped>

</style>
