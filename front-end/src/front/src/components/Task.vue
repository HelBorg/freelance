<template>
  <div class="bg-aqua">
    <div>
      <div id="taskName">
        <h2>Creating FREELANCE system
          <b-badge variant="danger">in design</b-badge>
          <b-button-group style="float:right">
            <b-button v-b-modal.edit>Edit</b-button>
            <b-button>Publish</b-button>
            <b-button v-b-modal.commit_delete>Delete</b-button>
          </b-button-group>
        </h2>
      </div>
    </div>
    <b-modal id="commit_delete" title="Delete task">
      <p class="my-4">You really want to delete task?</p>
    </b-modal>
    <b-modal
      id="edit"
      ref="modal"
      size="lg"
      title="Edit task"
      @ok="handleOk"
      @shown="clearName">
      <form @submit.stop.prevent="handleSubmit">
        <div>
          <p class="mb-2">Enter task name:</p>
          <b-form-input v-model="name" placeholder="Enter task name" class="mb-2"/>
          <p class="mb-2">Enter description</p>
          <b-form-textarea
            class="mb-2"
            id="task_description"
            placeholder="Enter description..."
            rows="3"
            max-rows="20"></b-form-textarea>
          <p class="mb-2">Choose task deadline</p>
          <b-form-input type="date" class="mb-2"></b-form-input>
          <p class="mb-2">Add skills</p>
          <b-row>
            <b-col lg="6">
              <b-input-group>
                <b-form-select v-model="skill" :options="skills" v-cloneya-select></b-form-select>
                <b-form-select v-model="level" :options="skill_levels" v-cloneya-select></b-form-select>
                <button type="button" class="btn btn-success" tabindex="-1"><i>+</i></button>
                <button type="button" class="btn btn-danger" tabindex="-1"><i>-</i></button>
              </b-input-group>
            </b-col>
          </b-row>
        </div>
      </form>
    </b-modal>
    <hr/>
    <h5 class="mb-2">
      Description
    </h5>
    <div id="taskDesc"  style="padding:10px">
        <p>This guide provides a sampling of how Spring Boot helps you accelerate and facilitate application development. As you read more Spring Getting Started guides, you will see more use cases for Spring Boot. It is meant to give you a quick taste of Spring Boot. If you want to create your own Spring Boot-based project, visit Spring Initializr, fill in your project details, pick your options, and you can download either a Maven build file, or a bundled up project as a zip file.</p>
    </div>
    <hr/>

    <div id="skills">
      <h5>
        Nessesary skills level
      </h5>
      <div class="row mb-1">
        <div class="col-sm-2">Java</div>
        <div class="col-sm-10 pt-1">
          <b-progress :value="75" variant="success"></b-progress>
        </div>
      </div>
      <div class="row mb-1">
        <div class="col-sm-2">Js</div>
        <div class="col-sm-10 pt-1">
          <b-progress :value="20" variant="danger"></b-progress>
        </div>
      </div>
      <div class="row mb-1">
        <div class="col-sm-2">SQL</div>
        <div class="col-sm-10 pt-1">
          <b-progress :value="50" variant="warning"></b-progress>
        </div>
      </div>

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
    <hr/>
    <h5>
      Task comments
    </h5>
    <div id="table">
      <div>
        <b-table :items="records" :fields="column" striped hover :current-page="currentPage" :per-page="perPage">
          <template slot="HEAD_selected" slot-scope="comments">
            <input type="checkbox" @click.stop v-model="selectAll" @change="toggleSelectAll" />
          </template>
          <template slot="selected" slot-scope="comments">
            <input type="checkbox" v-model="comments.item.selected" @change="selectRow(comments.item)" />
          </template>
        </b-table>
        <b-row>
          <b-col md="6" class="my-1">
            <b-pagination :total-rows="totalRows" :per-page="perPage" v-model="currentPage" class="my-0"></b-pagination>
          </b-col>
          <b-col md="6" class="my-1">
            <b-form-group horizontal label="Per page" class="mb-0">
              <b-form-select :options="pageOptions" v-model="perPage"></b-form-select>
            </b-form-group>
          </b-col>
        </b-row>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    name: "Task",
    data() {
      return {
        exampleData: ["Vue", "Cloneya", "VueCloneya Example", "More than min"],
        skill: 'Choose skill',
        skills: [
          {value: 'Java', text: 'Java'},
          {value: 'JavaScript', text: 'Javascript'},
          {value: 'sql', text: 'SQL'},
        ],
        level: 'Choose level',
        skill_levels: [
          {value: 'bad', text: 'Bad'},
          {value: 'semi-good', text: 'Semi-good'},
          {value: 'good', text: 'Good'},
          {value: 'semi-profi', text: 'Semi-profi'},
          {value: 'profi', text: 'Profi'}
        ],
        name: '',
        names: [],

        selectAll: false,
        records: [  {
          "name": "Dmitry",
          "msg": "I will do it",
          "date": "8/15/2017"
        },
          {
            "name": "Elena",
            "msg": "I will do it",
            "date": "8/9/2017"
          },
          {
            "name": "Yuri",
            "msg": "I will do it",
            "date": "3/4/2018"
          },
          {
            "name": "Roma",
            "msg": "I will do it",
            "date": "10/16/2017"
          },
          {
            "name": "Petya",
            "msg": "I will do it",
            "date": "5/23/2018"
          }],
        perPage: 5,
        currentPage: 1,
        pageOptions: [5, 10, 15],
        column: [
          {
            key: "selected",
            sortable: false,
            label: "",
            class: "options-column"
          },
          {
            key: "name",
            sortable: true,
            label: "User name"
          },
          {
            key: "msg",
            sortable: true,
            label: "Message",
            class: "text-left options-column"
          },
          {
            key: "date",
            sortable: true,
            label: "comment add Date",
            class: "text-left options-column"
          },
          {
            sortable: false,
            label: "Options",
            class: "options-column"
          }
        ]
      }
    },
    computed: {
      hasRecords() {
        return this.records.length > 0;
      },
      totalRows() {
        return this.records.length;
      }
    },
    methods: {
      selectRow(comments) {
        if (comments.selected) {
          comments._rowVariant = "info";
        } else {
          comments._rowVariant = "default";
          if (this.selectAll) {
            this.selectAll = false;
          }
        }
      },
      toggleSelectAll() {
        if (this.selectAll) {
          for (var i = 0; i < this.records.length; i++) {
            var updatingItem = this.records[i];
            updatingItem.selected = true;
            updatingItem._rowVariant = "info";
            this.$set(this.records, i, updatingItem);
          }
        } else {
          for (var i = 0; i < this.records.length; i++) {
            var updatingItem = this.records[i];
            updatingItem.selected = false;
            updatingItem._rowVariant = "default";
            this.$set(this.records, i, updatingItem);
          }
        }
      },
      alert(msg) {
        console.log(msg);
      },
      clearName() {
        this.name = ''
      },
      handleOk(bvModalEvt) {
        // Prevent modal from closing
        bvModalEvt.preventDefault()
        if (!this.name) {
          alert('Please enter your name')
        } else {
          this.handleSubmit()
        }
      },
      handleSubmit() {
        this.names.push(this.name)
        this.clearName()
        this.$nextTick(() => {
          // Wrapped in $nextTick to ensure DOM is rendered before closing
          this.$refs.modal.hide()
        })
      }
    }
  }
</script>


