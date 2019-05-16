<template>
  <div>
    <Navbar></Navbar>
    <div>
      <Menu></Menu>
      <!-- Modal -->
      <div id="profile" style="float:right; width:80%">
        <div>
          <div>
            <div class="modal-header">
              <h4 class="modal-title">User profile</h4>

            </div>
            <div class="modal-body">
              <div style="text-align: center;">
                <img style="border-radius:50%"
                     src="https://www.ondernemers-peelland.nl/wp-content/themes/ondernemers-peelland/images/profile_image.jpg"
                     width="160" height="160" class="circle">
                <h3>{{userName}}</h3>
                <p>
                  Email: {{userEmail}}
                </p>
                <p>
                  Rating:
                  <b-badge class="label" variant="success">{{userRating}}</b-badge>
                </p>
                <p>
                  Tasks done:
                  <b-badge class="label" variant="success">{{userTasksNumber}}</b-badge>
                </p>
              </div>
              <SkillForm style="text-align: center;"
                         :skills="userSkills"
                         :current="current">
              </SkillForm>

              <CommentForm :comments="userComments"></CommentForm>

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
  import * as types from '../store/mutation-types'


  export default {

    beforeMount() {
      this.getCurrentUserId()
      this.loadUser()
    },
    components: {Menu, Navbar, SkillForm, Skill, CommentForm, Comment},
    data() {
      return {
        skillStatus: '',
        commentsStatus: '',
        current: '',
        edit: false,
        userId: '',
        userName: '',
        userEmail: '',
        userTasksNumber:'',
        userRating: '',
        userSkills: [],
        userComments: []
      }
    },
    methods: {
      loadUser: function () {
        let self = this;
        fetch('/api/v1/person/' + self.$route.params.id, {
          method: 'GET',
          headers: types.HEADER
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
                self.userTasksNumber = data.tasksDone;

                if (data.skills !== null) {
                  self.userSkills = data.skills;
                } else {
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
          headers: types.HEADER
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
              })
            }
          )
      }
    }
  }
</script>

<style scoped>

</style>
