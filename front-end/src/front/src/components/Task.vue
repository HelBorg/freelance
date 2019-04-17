<template>
  <div id="main" style="float: right">
    <div>
      <div id="taskName">
        <b-badge variant="danger">{{task_data.status}}</b-badge> <!--status-->
        <div style="float:right">
          <b-button v-b-modal.edit>Edit</b-button> <!--top buttons-->
          <b-button>Publish</b-button>
          <b-button v-b-modal.commit_delete>Delete</b-button>
        </div>
        <div>
          <b-input class="ml-2 mt-3 w-75">{{task_data.name}}</b-input>
        </div>
      </div>
    </div>
    <b-modal id="commit_delete" title="Delete task">
      <p class="my-4">You really want to delete task?</p>
    </b-modal>
    <hr/>
    <h5 class="mb-2">
      Description
    </h5>
    <div id="taskDesc" style="padding:10px">
      <b-form-textarea
        id="textarea"
        rows="3"
        max-rows="6"
      >
        Description
      </b-form-textarea>
      <hr/>
      <div id="skills">
        <h5>
          Nessesary skills level
        </h5>

        <b-form inline>
          <label class="mr-sm-2" >Skill</label>
          <b-form-select
            class="mb-2 mr-sm-2 mb-sm-0"
            :value="null"
            :options="{ 1: 'Java', 2: 'SQL', 3: 'Javascript' }"
            id="select-skill"
          >
            <option slot="first" :value="null">Choose...</option>
          </b-form-select>

          <label class="mr-sm-2">Level</label>
          <b-form-select
            class="mb-2 mr-sm-2 mb-sm-0"
            :value="null"
            :options="{ 1: 'bad', 25: 'semi-good', 50: 'good', 75: 'semi-prof', 100: 'proffesional' }"
            id="select-level"
          >
            <option slot="first" :value="null">Choose...</option>
          </b-form-select>
          <b-button variant="primary"  v-on:click='addNewSkillLine()'>Add new skill</b-button>
        </b-form>
        <div id="skill_line" class="row mb-1">

        </div>
      <hr/>
      <h5>
        Task date range
      </h5>
      <b-container id="datetime">
        <div>
          <b-row class="my-1">
            <b-col sm="3">
              <label>Start date:</label>
            </b-col>
            <b-col sm="9">
              <b-form-input type="date"></b-form-input>
            </b-col>
          </b-row>
        </div>
        <div>
          <b-row class="my-1">
            <b-col sm="3">
              <label>End date:</label>
            </b-col>
            <b-col sm="9">
              <b-form-input type="date"></b-form-input>
            </b-col>
          </b-row>
        </div>
      </b-container>
    </div>
  </div>
  </div>
</template>

<script>
  import axios from "../../axios/axios";

  export default {
    created(){
      this.createNewTask()
    },
    name: "Task",
    data() {
      return {
        task_data: {
          id: 0,
          name: '',
          status: ''
        },
        skill_l: 'Java<b-progress :value=25></b-progress><b-button variant="primary" class="sm-10 mt-2">Delete</b-button> ',
        ch_skill: '',
        skill: 'Choose skill',
        skills: [
          {value: 'Java', text: 'Java'},
          {value: 'JavaScript', text: 'Javascript'},
          {value: 'sql', text: 'SQL'},
        ],
        selectAll: false
      }
    },
    methods:{
    createNewTask: function () {
      axios.post('http://localhost:80/api/v1/task', {
        headers: {
          'Content-Type': 'application/json',
          'Authorization': 'Bearer ' + localStorage.getItem("JWT")
        }

      }) .then(function (response) {
        this.task_data.id = response.data.id;
        this.task_data.name = response.data.name;
        this.task_data.status = response.data.status;


        console.log(task_data);
      });
    },
      addNewSkillLine: function(){
        let element = $('#skill_line').append(this.skill_l);
        /* compile the new content so that vue can read it */
        this.compile(element.get(0));
      },
      test: function(){
        alert('Test');
      }
    }
  }
</script>


