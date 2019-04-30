<template>
  <div>
    <Navbar></Navbar>
    <div>
      <Menu></Menu>
  <!-- Modal -->
  <div  id="profile" style="float:right; width:80%">
    <div>
      <div>
        <div class="modal-header">
          <h4 class="modal-title">User profile</h4>
        </div>
        <div class="modal-body">
          <div style="text-align: center;">
            <img src="https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcRbezqZpEuwGSvitKy3wrwnth5kysKdRqBW54cAszm_wiutku3R" width="140" height="140" class="circle">
            <h3>{{userName}}</h3>
            <p>
              Email: {{userEmail}}
            </p>
            <p>
              Rating:<b-badge class="label" variant="success">{{userRating}}</b-badge>
            </p>

          </div>
          <hr>
          <SkillForm :skills="userSkills" ></SkillForm>
        </div>
      </div>
    </div>
  </div>
    </div>
  </div>

</template>

<script>
  import Navbar from "./Navbar.vue";
  import Menu from "./Menu.vue";
  import SkillForm from "./Task/SkillForm.vue";

  export default {

    created(){
      this.loadUser()
    },
    components: {Menu, Navbar, SkillForm},
    data(){
      return{
        userId:'',
        userName:'',
        userEmail:'',
        userRating:'',
        userSkills:[]
      }
    },
    methods: {
      loadUser: function () {
        let self = this;
        fetch('/api/v1/person/' + self.$route.params.id, {
          method: 'GET',
          headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + localStorage.getItem('JWT')
          }
        })
          .then(
            function (response) {
              if (response.status !== 200) {
                console.log('Looks like there was a problem. Status Code: ' +
                  response.status);
                return;
              }
              response.json().then(function (data) {
                self.userId = data.id;
                self.userName = data.name;
                self.userRating = data.rating;
                self.userEmail = data.email;
                self.userSkills = data.skills;
              })
            }
          )
      }
    }
    }
</script>

<style scoped>

</style>
