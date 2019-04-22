<template>
  <div>
    <b-col>
      <div v-if="tasks.length > 0">
        <b-table id="tasks"
                 title="Tasks"
                 :items="tasks"
                 :fields="fields"
                 :per-page="perPage"
                 :current-page="currentPage"
                 small
                 @row-clicked="goToTask(item.id)">
      </b-table>
      </div>
      <div v-else>
        <h4>List is empty!</h4>
      </div>
      <b-pagination
        v-model="currentPage"
        :total-rows="rows"
        :per-page="perPage"
        aria-controls="tasks"
        first-text="First"
        prev-text="Prev"
        next-text="Next"
        last-text="Last"
        size="sm"
      ></b-pagination>
    </b-col>

    <!--    Filter-->
    <b-col>
      <div v-if="pageConf.filter"
           title="Filter">
        <b-form @submit="onSubmit" @reset="onReset" v-if="show">

          <b-form-group id="input-group-1" label-cols-sm="3" label="Filter" class="mb-0">
            <b-input-group>
              <b-form-input v-model="editFilter.name" placeholder="Type to Search"></b-form-input>
              <b-input-group-append>
                <b-button :disabled="!editFilter.name" @click="editFilter.name = ''">Clear</b-button>
              </b-input-group-append>
            </b-input-group>
          </b-form-group>

          <b-form-group id="input-group-2" label="Date:" label-for="input-2">
            <b-form>
              <b-row>
<!--                <datepicker v-model="editFilter.date_from"></datepicker>-->
              </b-row>
            </b-form>
            <b-form>
              <b-row>
<!--                <datepicker v-model="editFilter.date_to"></datepicker>-->
              </b-row>
            </b-form>
          </b-form-group>


          <b-form-group id="input-group-3" label="Skills:" label-for="input-3">
            <b-container fluid v-for="(skill,index) in editFilter.skillsF">
              <b-row>
                <b-col>
                  <b-form-select
                    id="input-3"
                    v-model="selected"
                    :options="editFilter.skills">
                  </b-form-select>
                </b-col>
                <b-col cols="3"> Value: {{skill.value}}
                </b-col>
                <b-col cols='1'>
                  <b-button @click="deleteSkill(index)" variant="primary"> - </b-button>
                </b-col>
              </b-row>
              <b-row>
                <b-col>
                  <b-form-input id="skill-range-{{skill.name}}" v-model="skill.value" type="range" min="0"
                                max="10"></b-form-input>
                </b-col>
                <b-col cols="1"></b-col>
              </b-row>
              <b-row>
                <h1>
                </h1>
              </b-row>
            </b-container>
          </b-form-group>

          <b-form-group id="input-group-4" label="Author:" label-for="input-4">
            <b-form-select multiple v-model="selected" :options="options" :select-size="4"></b-form-select>
            <div class="mt-3">Selected: <strong>{{ selected }}</strong></div>
          </b-form-group>

          <b-button type="submit" variant="primary">Submit</b-button>
          <b-button type="addSkill" variant="success" v-on:click="addSkill">Add Skill</b-button>
          <b-button type="reset" variant="danger">Reset</b-button>
        </b-form>
      </div>
    </b-col>
  </div>
</template>

<script>
  import axios from 'axios'
  // import DatePick from 'vue-date-pick';
  // import 'vue-date-pick/dist/vueDatePick.css';

  export default {
    name: "Table_for_tasks",
    // components: {DatePick},
    newSkill: {
      name: '',
      value: ''
    },
    pageName: '',
    pageConf: {
      filter: true,
      status: true,
      author: true
    },
    data() {
      return {
        perPage: 10,
        currentPage: 1,
        tasks: [{id: '1', name:'3ui'}],
        skills: [],

        fields: {
          name: {
            label: 'Name',
            sortable: true
          },
          status: {
            label: 'Status'
          },
          date_from: {
            label: 'Date of creating',
            sortable: true
          },
          date_to: {
            label: 'To',
            sortable: true
          },
          task_rate: {
            label: 'Rate',
            sortable: true
          },
          author: {
            label: 'Author',
            sortable: true
          },
          skills: {
            label: 'Skills'
          },
          assigned: {
            label: 'Assigned'
          }
        },

        // Filter
        editFilter: {
          name: '',
          date_from: '',
          date_to: '',
          skillsF:
            [
              {name: '', value: '0'}
            ],
          skills:
            ['author 1', 'author 2', 'author 3', 'author 4']
        },
        show: true,
        selected:
          ['author 1', 'author 2'], // Array reference
        status: 'not_accepted'
      }
    },
    computed: {
      rows() {
        return this.items.length
      }
    },
    methods: {
      getTasks() {
        axios.get('http://localhost:8080/api/{$store.state.page}')
          .then((response) => {
            console.log(response.data);
            this.tasks = response.data;
          }).catch(function (error) {
          alert("Ошибка. Обновление события!");
          console.log(error);
        });
      },

      getSkills() {
        axios.get('http://localhost:8080/api/skill')
          .then((response) => {
            console.log(response.data);
            this.skills = response.data;
          }).catch(function (error) {
          alert("Ошибка. Обновление события!");
          console.log(error);
        });
      },
      getPage() {
        this.pageName = this.$route.params.pageName;

      },
      goToTask(id) {
        this.$router.push('task/:{id}')
      },
      refreshList() {
        this.getTasks()
      },

      // Filter
      onSubmit(evt) {
        evt.preventDefault()
        alert(JSON.stringify(this.form))
        axios.get('http://localhost:8080/api/skill')
          .then((response) => {
            console.log(response.data);
            this.skills = response.data;
          }).catch(function (error) {
          alert("Ошибка. Обновление события!");
          console.log(error);
        });
      },
      onReset(evt) {
        evt.preventDefault()
        // Reset our form values
        this.filter.name = ''
        // Trick to reset/clear native browser form validation state
        this.show = false
        this.$nextTick(() => {
          this.show = true
        })
      },
      addSkill() {
        this.editFilter.skillsF.push(this.newSkill)
      },
      deleteSkill(index) {
        this.editFilter.skillsF.splice(index, 1);
      }
    },
    mounted() {
      this.getTasks();
    },
    created() {
      this.getPage();
    }
  };
</script>

<style scoped>

</style>
