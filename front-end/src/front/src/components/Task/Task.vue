<template>
  <div>
    <Navbar></Navbar>
    <div class="bg">
      <Menu></Menu>
      <div id="main" class="lead mr-5" style="float:right; width:80%">
        <!--Top buttons task name and status area-->
        <div id="header">
          <b-badge value="name" variant="danger" class="mt-4">{{status}} </b-badge>
          <b-badge variant="light" @click="goToUserPage(assignedUser.id)" v-if="status === 'ASSIGNED'
              || status === 'IN_WORK' || status === 'DONE'" style="cursor: pointer" >
            performer - {{assignedName}}</b-badge>


          <div id="headerButtons" style="float:right" class="mt-2">
            <!--top buttons-->

            <b-button v-if="status === 'IN_WORK' & author_id === userId" @click="$refs['task_done'].show();" variant="primary">Done</b-button>

            <b-modal ref="task_done" id="task_done" hide-footer>
              <template slot="modal-header">Please, write review about performer</template>
              <b-form-select class="mt-2" v-model="settedRate">
                <option :value="null">Work rate</option>
                <option value=1>bad</option>
                <option value=2>semi-good</option>
                <option value=3>good</option>
                <option value=4>semi-profi</option>
                <option value=5>profi</option>
              </b-form-select>
              <b-form-textarea
                id="area_text"
                size="sm"
                rows="1"
                max-rows="8"
                class="mt-2"
                v-model="userReview"
              >
              </b-form-textarea>

              <div style="float:right">
                <b-button @click="$refs['task_done'].hide();" class="mt-3">Cansel</b-button>
                <b-button class="mt-3" v-on:click="taskDone" variant="success">Done
                </b-button>
              </div>
            </b-modal>

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
              <b-button @click="$refs['commit_delete'].hide();" class="mt-3">Cansel</b-button>
              <b-button class="mt-3" v-on:click="acceptDeleteHandler" variant="danger">Delete
              </b-button>
            </div>
          </b-modal>

          <!--Task Name area-->
          <div id="TaskName">
            <h3 v-if="status === 'IN_DESIGN'" class="mt-4 mb-1 lead"><strong>Name</strong></h3>
            <h3 v-if="status !== 'IN_DESIGN'" class="mt-4 mb-1 lead"><strong>{{name}}</strong></h3>
            <b-form-input v-if="status === 'IN_DESIGN'" v-model="name"></b-form-input>
          </div>
          <!-->
        </div> <!--end of header-->

      <hr/>

        <!--Description area-->
        <div id="taskDescription">
          <h5 class="mb-2 mt-1 lead"><strong>Task description</strong></h5>
          <p v-if="status !== 'IN_DESIGN'" class="mt-2 mb-1 text-justify">{{description}}</p>
          <b-form-textarea v-if="status === 'IN_DESIGN'" id="textarea" rows="3" max-rows="6" v-model="description" ></b-form-textarea>
        </div>
        <!-->


          <!--Skills component -->
          <SkillForm :skills="loaded_skills" :status="status" ></SkillForm>
          <!-->

        <!--TaskDateRange area-->
        <div id="taskDateRange">
         <!-- <p class="mb-1 mt-1">Created date</p>
            <b-form-input disabled="true" v-model=created_time type="date" style="width:20%"></b-form-input>-->

            <h5 class="mb-1 mt-4 lead"><strong>Due date</strong></h5>
            <b-form-input v-if="status === 'IN_DESIGN'" v-model=deadline type="date" style="width:20%"></b-form-input>
            <b-form-input v-if="status !== 'IN_DESIGN'" disabled="true" v-model=deadline type="date" style="width:20%"></b-form-input>

        </div>
        <!-->
        <!--Comments component-->
        <CommentForm v-if="status !== 'IN_DESIGN'"
                     :comments="comments"
                     :status="status"
                     :author-id="author_id"
                     :current-user-id="userId"
        ></CommentForm>
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
      this.loadTask()
      this.loadComments()
      this.getCurrentUserId()
    },
    components: {Menu, Navbar, CommentForm, SkillForm, Comment, Skill},
    data() {
      return {
        header:{
          'Content-Type': 'application/json',
          'Authorization': 'Bearer ' + localStorage.getItem('JWT')
        },
        userReview:'',
        settedRate:null,
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
        assignedUser:[],
        assignedName:''

        //-------task---------
      }
    },
    methods: {
      taskDone(){
        let self =this
        fetch('/api/v1/person/'+ self.assignedUser.id + '/' + self.settedRate, {
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
              fetch('/api/v1/review', {
                method: 'POST',
                headers: self.header,
                body: JSON.stringify({
                  description: self.userReview,
                  done: true,
                  taskId: {
                    taskId: self.$route.params.id,
                  }
                })
              })
                .then(
                  function (response) {
                    if (response.status !== 200) {
                      console.log('Looks like there was a problem. Status Code: ' +
                        response.status);
                    }

                  }
                )
              self.$refs['task_done'].hide();
              self.updateStatus()
            }
          )

      },
      goToUserPage(id){
        router.replace('/user/' + id)
      },
      revertStatus(){
        let self = this;
        fetch('/api/v1/task/delete/assigned/'+ self.$route.params.id, {
          method: 'POST',
          headers: self.header,
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
            headers: self.header,
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
          headers: this.header,
        })
          .then(
            function (response) {
              if (response.status !== 200) {
                console.log('Looks like there was a problem. Status Code: ' +
                  response.status);
                return;
              }
              response.json().then(function () {
                router.replace('/home');
                router.push('/home');

              })
            }
          )
      },
      saveTask() {
        let self = this;
        fetch('/api/v1/task', {
          method: 'POST',
          headers: self.header,
          body: JSON.stringify({
            id: self.$route.params.id,
            name: self.name,
            status: self.status,
            description: self.description,
            deadline: self.deadline,

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
          headers: self.header
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
                self.assignedUser = data.assignedUser
                self.assignedName = data.assignedUser.name;

              })
            }
          )
      },
      loadComments() {
        let self = this;
        fetch('/api/v1/review/' + self.$route.params.id, {
          method: 'GET',
          headers: self.header
        })
          .then(
            function (response) {
              if (response.status !== 200) {
                console.log('Looks like there was a problem. Status Code: ' +
                  response.status);
                return;
              }
              response.json().then(function (data) {
                self.comments = data;
              })
            }
          )
      },
      getCurrentUserId() {
        let self = this;
        fetch('/api/v1/me', {
          method: 'GET',
          headers: self.header
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


