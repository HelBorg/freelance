<template>
  <div>
  <Navbar></Navbar>
  <div>
    <b-card>
  <Menu></Menu>
<div id="main" class="lead" style="float:right; width:70%">
  <div>
    <div id="taskName">
      <b-badge value="name" variant="danger">{{status}}</b-badge> <!--status-->
      <div style="float:right">
        <b-button @click="saveTask" variant="primary">Save</b-button> <!--top buttons-->
        <b-button variant="success">Publish</b-button>
        <b-button v-b-modal.commit_delete variant="danger">Delete</b-button>
      </div>
      <div>
        <p class="mt-4 mb-1">Name</p>
        <b-form-input v-model="name"></b-form-input>
      </div>
    </div>
  </div>
  <b-modal id="commit_delete" title="Delete task">
    <p class="my-4">You really want to delete task?</p>
  </b-modal>
  <hr/>
  <h5 class="mb-2 lead">
    Description
  </h5>
  <div id="taskDesc">
    <b-form-textarea
      id="textarea"
      rows="3"
      max-rows="6"
      v-model="description"
    >
    </b-form-textarea>
    <hr/>
    <div id="skills">
      <h5 class="lead">
        Nessesary skills
      </h5>

      <b-form-select v-model="skill_name" class="mb-3">
        <option :value="null">Please select skill name</option>
        <option value="java">Java</option>
        <option value="sql">SQL</option>
        <option value="sql">Javascript</option>
      </b-form-select>
      <b-form-select v-model="skill_level" class="mb-3">
        <option :value="null">Please select skill level</option>
        <option value="bad">Bad</option>
        <option value="semi-good">semi-good</option>
        <option value="good">good</option>
        <option value="semi-profi">semi-profi</option>
        <option value="profi">profi</option>
      </b-form-select>
      <b-button @click="addRow" variant="success"> Add skill </b-button>
      <b-button @click="deleteRow" variant="danger"> Delete Selected </b-button>

      <b-table selectable
               select-mode="single"
               selectedVariant="danger"
               hover
               :items="skills"
               @row-selected="rowSelected"
      ></b-table>
      {{skills}}
    </div>
  </div>
  <hr/>
  <h5 class="lead">
    Task date range
  </h5>
  <p class="mb-1">Start date</p>
  <b-form-input v-model=created_time type="date"></b-form-input>
  <p class="mb-1">End date</p>
  <b-form-input type="date"></b-form-input>
</div>
    </b-card>
  </div>
  </div>
  </template>
<script>

  import Navbar from "./Navbar";
  import Menu from "./Menu";
  export default {
    beforeMount(){
      this.loadTask()
    },

    name: "Task",
    components:{Menu, Navbar},
    data() {
      return {
        skills:[],
        skill_level:null,
        skill_name:null,
        //-------task---------
        id:'',
        name:'',
        status:'',
        description: '',
        created_time:''
        //-------task---------
      }
    },

    methods: {
      addRow(){
        if(this.skill_name!=null && this.skill_level !=null){
          this.skills.push({
            name:this.skill_name,
            level:this.skill_level
          })
        }
      },
      deleteRow(){
        let index = this.skills.indexOf(this.selected[0])
        this.skills.splice(index, 1);
      },
      rowSelected(skills) {
        this.selected = skills
      },
      saveTask(){
        let self = this;
        fetch('/api/v1/task', {
          method: 'POST',
          headers: {
            'Content-Type' : 'application/json',
            'Authorization': 'Bearer ' + localStorage.getItem('JWT')
          },
          body:JSON.stringify({
            id: localStorage.getItem("loadedTask"),
            name: self.name,
            status:self.status,
            description: self.description,
            skills: self.skills,
          })

        })
          .then(
            function(response) {
              console.log(self.skills)
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
      loadTask(){
        let self = this;
        fetch('/api/v1/task/' + localStorage.getItem('loadedTask'), {
          method: 'GET',
          headers: {
            'Content-Type' : 'application/json',
            'Authorization': 'Bearer ' + localStorage.getItem('JWT')
          }
        })
          .then(
            function(response) {
              if (response.status !== 200) {
                console.log('Looks like there was a problem. Status Code: ' +
                  response.status);
                return;
              }
              response.json().then(function (data) {
                self.id = data.task.id;
                self.name = data.task.name;
                self.status = data.task.status;
                self.description = data.task.description;
                self.created_time = data.task.createdTime.substring(0,10);
                self.skills = data.task.skills;
              })
            }
          )
      },
      addNewSkillLine: function () {
        let element = $('#skill_line').append(this.skill_l);
        /* compile the new content so that vue can read it */
        this.compile(element.get(0));
      },
      test: function () {
        alert('Test');
      }
    }
  }
</script>


