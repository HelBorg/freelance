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
            <SkillForm :skills="userSkills" :status="skillStatus"></SkillForm>
          </div>
          <CommentForm :comments="userComments" :status="commentsStatus"></CommentForm>

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
      this.loadUser(),
        this.getCurrentUserId()
        this.loadComments()
      this.checkUser()
    },
    components: {Menu, Navbar, SkillForm, Skill, CommentForm, Comment},
    data(){
      return{
        skillStatus:'',
        commentsStatus:'',
        currentUserId:'',
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
        let self = this
        if(self.currentUserId === self.userId)
          self.skillStatus = 'CURRENT'
          self.skillStatus = 'CURRENT'


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
                self.userSkills = data.skills;
              })
            }
            )
      },
      getCurrentUserId() {
        let self = this;
        fetch('/api/v1/me', {
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
                self.currentUserId = data.id;
              })
            }
          )
      },
    }
    }
</script>

<style scoped>

</style>
