<template>
  <div>
    <Navbar></Navbar>
    <div>
      <Menu></Menu>
      <div id="main" class="lead mr-2" style="float:right; width:80%">
        <!--Top buttons task name and status area-->
        <div id="header">
          <b-badge value="name" variant="danger">{{status}} </b-badge>
            <router-link v-if="status === 'ASSIGNED'">Performer - {{assignedUser.name}}</router-link>

          <div id="headerButtons" style="float:right">
            <!--top buttons-->
            <b-button v-if="status === 'IN_WORK' && author_id === userId" @click="updateStatus" variant="primary">Done</b-button>

            <b-button v-if="status === 'ASSIGNED' && assignedUser.id === userId" @click="updateStatus" variant="primary">Apply</b-button>
            <b-button v-if="status === 'ASSIGNED' && assignedUser.id === userId" @click="revertStatus" variant="primary">Decline</b-button>


            <b-button v-if="status === 'IN_DESIGN'" @click="saveTask" variant="primary">Save</b-button>

            <b-button v-if="status === 'IN_DESIGN' && author_id === userId" @click="updateStatus" variant="success"
                      v-b-tooltip.hover title="Make task visible to other users"
            >Publish</b-button>

            <b-button v-if="status === 'IN_DESIGN' || status === 'PUBLISH' && author_id === userId"
                      @click="$refs['commit_delete'].show();" variant="danger">Delete</b-button>
            <!-->
          </div>

          <b-modal ref="commit_delete" id="commit_delete" hide-footer>
            <template slot="modal-title">You really want to delete task?</template>
            <div style="float:right">
              <b-button @click="$refs['commit_delete'].hide();" class="mt-3">Close</b-button>
              <b-button class="mt-3" v-on:click="acceptDeleteHandler" variant="danger">Delete
              </b-button>
            </div>
          </b-modal>

          <!--Task Name area-->
          <div id="TaskName">
            <p class="mt-4 mb-1"><strong>Name</strong></p>
            <p v-if="status !== 'IN_DESIGN'" class="mt-4 mb-1">{{name}}</p>
            <b-form-input v-if="status === 'IN_DESIGN'" v-model="name"></b-form-input>
          </div>
          <!-->
        </div> <!--end of header-->

        <hr/>

        <!--Description area-->
        <div id="taskDescription">
          <h5 class="mb-2 lead"><strong>Description</strong></h5>
          <p v-if="status !== 'IN_DESIGN'" class="mt-4 mb-1">{{description}}</p>
          <b-form-textarea v-if="status === 'IN_DESIGN'" id="textarea" rows="3" max-rows="6" v-model="description" ></b-form-textarea>
        </div>
        <!-->

          <hr/>

          <!--Skills component -->
          <SkillForm :skills="loaded_skills" :status="status" ></SkillForm>
          <!-->
          <hr/>

        <!--TaskDateRange area-->
        <div id="taskDateRange">
          <h5 class="lead"><strong>Task date range</strong></h5>
            <p class="mb-1">Created date</p>
            <b-form-input disabled="true" v-model=created_time type="date"></b-form-input>
            <p class="mb-1">Deadline</p>
            <b-form-input v-if="status === 'IN_DESIGN'" v-model=deadline type="date"></b-form-input>
            <b-form-input v-if="status !== 'IN_DESIGN'" disabled="true" v-model=deadline type="date"></b-form-input>

        </div>
        <!-->

        <!--Comments component-->
        <CommentForm v-if="status !== 'IN_DESIGN'" :comments="comments" :status="status"></CommentForm>
        <!-->
      </div>
    </div>
  </div>
</template>
<script>

  import Navbar from "../Navbar";
  import Menu from "../Menu";
  import SkillForm from "./SkillForm.vue"
  import CommentForm from "../comments/CommentForm.vue"
  import Comment from "../comments/Comment.vue"
  import Skill from "../../components/Task/Skill.vue"
  import router from "../../router";



  export default {
    beforeMount() {
      this.loadTask(),
        this.getCurrentUserId()
    },
    components: {Menu, Navbar, CommentForm, SkillForm, Comment, Skill},
    data() {
      return {
        userId:'',
        comments: [],
        newComment: '',
        //-------task---------
        loaded_skills: [],
        taskId: '',
        name: '',
        status: '',
        author_id:'',
        description: '',
        created_time: '',
        deadline: '',
        assignedUser:Object
        //-------task---------
      }
    },
    methods: {
      goToUserPage(id){
        router.replace('/user/' + id)
      },
      revertStatus(){
        let self = this;
        fetch('/api/v1/task/delete/assigned/'+ self.$route.params.id, {
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
              window.location.reload()
            }
          )
      },
      updateStatus(){
          let self = this;
          fetch('/api/v1/task/update/status/'+ self.$route.params.id + '/' + self.status, {
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
                window.location.reload()
              }
            )
      },
      deleteTask() {
        fetch('/api/v1/task/' + this.$route.params.id, {
          method: 'DELETE',
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
              alert("Success!");
              response.json().then(function (data) {
                self.router.push('tasks/mine')
              })
            }
          )
      },
      saveTask() {
        let self = this;
        fetch('/api/v1/task', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + localStorage.getItem('JWT')
          },
          body: JSON.stringify({
            taskId: self.$route.params.id,
            name: self.name,
            status: self.status,
            description: self.description,
            deadline: self.deadline
          })
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
                console.log(data),
                  window.location.reload()
              })
            }
          )

      },
      loadTask() {
        let self = this;
        fetch('/api/v1/task/' + self.$route.params.id, {
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
                self.taskId = data.id;
                self.name = data.name;
                self.status = data.status;
                self.description = data.description;
                self.created_time = data.createdTime.substring(0, 10);
                self.deadline = data.deadline.substring(0, 10);
                self.author_id = data.author.id;
                self.loaded_skills = data.skills;
                self.comments = data.reviews;
                self.assignedUser = data.assignedUser
                console.log(data.assignedUser.id)
                console.log(data.assignedUser.name)



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
                self.userId = data.id;
              })
            }
          )
      },
      acceptDeleteHandler(){
        this.$refs['commit_delete'].hide();
        this.deleteTask();
      }
    }
  }
</script>
<style>
</style>


