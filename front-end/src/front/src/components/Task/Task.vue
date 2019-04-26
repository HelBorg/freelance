<template>
  <div>
    <Navbar></Navbar>
    <div>
      <Menu></Menu>
      <div id="main" class="lead" style="float:right; width:70%">
        <!--Top buttons task name and status area-->
        <div id="header">
          <a >
          <b-badge value="name" variant="danger">{{status}} <a v-if="status === 'ASSIGNED'" href="../homePage/Home.vue"> - {{assignedUser.name}}</a></b-badge>
          </a>
          <div id="headerButtons" style="float:right">
            <!--top buttons-->
            <b-button v-if="status === 'IN_DESIGN'" @click="saveTask" variant="primary">Save</b-button>

            <b-button @click="updateStatus" variant="success"
                      v-b-tooltip.hover title="STATUS ORDER: IN_DESIGN, PUBLISH, ASSIGNED, IN_WORK, DONE"
            >Update Status</b-button>

            <b-button v-if="status === 'IN_DESIGN' || status === 'PUBLISH'"
                      @click="$refs['commit_delete'].show();" variant="danger">Delete</b-button>
            <!-->
          </div>

          <b-modal ref="commit_delete" id="commit_delete" hide-footer>
            <template slot="modal-title">You really want to delete task?</template>

            <div style="float:right">
              <b-button @click="$refs['commit_delete'].hide();" class="mt-3">Close</b-button>
              <b-button class="mt-3" v-on:click="deleteTask" variant="danger">Delete
              </b-button>
            </div>
          </b-modal>

          <!--Task Name area-->
          <div id="TaskName">
            <p class="mt-4 mb-1">Name</p>
            <p v-if="status !== 'IN_DESIGN'" class="mt-4 mb-1">{{name}}</p>
            <b-form-input v-if="status === 'IN_DESIGN'" v-model="name"></b-form-input>
          </div>
          <!-->
        </div> <!--end of header-->

        <hr/>

        <!--Description area-->
        <div id="taskDescription">
          <h5 class="mb-2 lead">Description</h5>
          <p v-if="status !== 'IN_DESIGN'" class="mt-4 mb-1">{{description}}</p>
          <b-form-textarea v-if="status === 'IN_DESIGN'" id="textarea" rows="3" max-rows="6" v-model="description" ></b-form-textarea>
        </div>
        <!-->

          <hr/>

          <!--Skills component -->
          <Skills :status="status" :skills="loaded_skills"></Skills>
          <!-->
          <hr/>

        <!--TaskDateRange area-->
        <div id="taskDateRange">
          <h5 class="lead">Task date range</h5>
            <p class="mb-1">Created date</p>
            <b-form-input disabled="true" v-model=created_time type="date"></b-form-input>
            <p class="mb-1">Deadline</p>
            <b-form-input v-model=deadline type="date"></b-form-input>
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
  import Skills from "./Skills.vue"
  import CommentForm from "../comments/CommentForm.vue"
  import Comment from "../comments/Comment.vue"


  export default {
    created() {
      this.loadTask()
    },
    components: {Menu, Navbar, CommentForm, Skills, Comment},
    data() {
      return {
        comments: [],
        newComment: '',
        //-------task---------
        loaded_skills: [],
        id: '',
        name: '',
        status: '',
        description: '',
        created_time: '',
        deadline: '',
        assignedUser:[]
        //-------task---------
      }
    },
    methods: {
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
                alert("Success!");
                response.json().then(function (data) {
                  self.status = data;
                  console.log(data)
                })
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
                console.log(data)
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
            id: self.$route.params.id,
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
                console.log(data)
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
                self.id = data.id;
                self.name = data.name;
                self.status = data.status;
                self.description = data.description;
                self.created_time = data.createdTime.substring(0, 10);
                self.deadline = data.deadline;
                self.loaded_skills = data.skills
                self.comments = data.reviews
                self.assignedUser = data.assignedUser
              })
            }
          )
      }
    }
  }
</script>


