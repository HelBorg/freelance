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
            <img style="border-radius:50%" src="https://www.ondernemers-peelland.nl/wp-content/themes/ondernemers-peelland/images/profile_image.jpg" width="160" height="160" class="circle">
            <h3>{{userName}}</h3>
            <p>
              Email: {{userEmail}}
            </p>
            <p>
              Rating:<b-badge class="label" variant="success">{{userRating}}</b-badge>
            </p>
          </div>
          <SkillForm style="text-align: center;" :skills="userSkills" :status="skillStatus" :current="current" ></SkillForm>

          <CommentForm  :comments="userComments"></CommentForm>

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
  import Skill from "./Task/Skill.vue";
  import CommentForm from "./comments/CommentForm.vue"
  import Comment from "./comments/Comment.vue"


  export default {

    beforeMount(){
      this.getCurrentUserId()
      this.checkUser()
      this.loadUser(),
      this.loadComments()
    },
    components: {Menu, Navbar, SkillForm, Skill, CommentForm, Comment},
    data(){
      return{
        skillStatus:'',
        commentsStatus:'',
        current:'',
        edit:false,
        userId:'',
        userName:'',
        userEmail:'',
        userRating:'',
        userSkills:[],
        userComments:[]
      }
    },
    methods: {
      checkUser(){
        let self = this;
        if(self.current === self.$route.params.id)
          self.skillStatus = 'CURRENT'
          self.commentsStatus = 'CURRENT'
      },
      loadComments() {
        let self = this;
        fetch('/api/v1/review/user/' + self.$route.params.id, {
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
                self.userComments = data;
              })
            }
          )
      },
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

                if(data.skills !== null){
                  self.userSkills = data.skills;
                }else {
                  self.userSkills.length = 0
                }

              })
            }
            )
      },
      getCurrentUserId() {
        let self = this;
        fetch('/api/v1', {
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
                self.current = data.id;
                console.log(data)
                console.log(data.id)
                console.log(self.current)


              })
            }
          )
      }
    }
  }
</script>

<style scoped>

</style>
