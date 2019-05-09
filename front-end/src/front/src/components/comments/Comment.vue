  <template>
        <b-card style="border-radius:0" class="mt-2">
            <img src="https://placekitten.com/g/30/30" style=" border-radius:50%;">
            <span style="cursor:pointer" @click="goToUserPage(comment.user.id)">{{comment.user.name}}</span>
            <span class="text-muted small">commented {{comment.createdTime.substring(0, 10)}}</span>
          <b-button v-if="status === 'PUBLISH'" @click="assignUser(comment.user.id)" style="float:right" variant="success" class="ml-4 p-1">Assign this user</b-button>
          <b-card-body class="p-2">{{comment.description}}</b-card-body>
        </b-card>
  </template>
<script>
  import router from "../../router";

  export default {

      props: {
        comment: Object,
        status: Object
      },
      data(){
        return{

        }
      },
      methods:{
        goToUserPage(id){
          router.replace('/user/' + id)
        },
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
                  console.log(self.comment.user.id)
                  window.location.reload()
                }
              )
        }
      }
    }
</script>

<style scoped>
  .thumbnail {
    padding:0px;
  }
  .panel {
    position:relative;
  }
  .panel>.panel-heading:after,.panel>.panel-heading:before{
    position:absolute;
    top:11px;left:-16px;
    right:100%;
    width:0;
    height:0;
    display:block;
    content:" ";
    border-color:transparent;
    border-style:solid solid outset;
    pointer-events:none;
  }
  .panel>.panel-heading:after{
    border-width:7px;
    border-right-color:#f7f7f7;
    margin-top:1px;
    margin-left:2px;
  }
  .panel>.panel-heading:before{
    border-right-color:#ddd;
    border-width:8px;
  }
</style>
