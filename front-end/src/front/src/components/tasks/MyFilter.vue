<template>
  <b-card bg-variant="light">
    <b-form-group @submit="onSubmit"
                  @reset="onReset"
                  class="mb-0">
      <b-form-group id="input-group-1"
                    label="Name"
                    label-class="font-weight-bold pt-0"
                    class="input-group">
        <b-input-group>
          <b-form-input v-model="filter.find_name" placeholder="Type to Search"></b-form-input>
          <b-input-group-append>
            <b-button size="sm" :disabled="!filter.find_name" @click="filter.find_name = ''">Clear</b-button>
          </b-input-group-append>
        </b-input-group>
      </b-form-group>


      <b-form-group id="input-group-2"
                    label="Date of creation:   "
                    label-for="input-2"
                    label-class="font-weight-bold pt-0"
                    class="input-group">
        <p>
          <b-form aria-label="From:">
            From:
            <datepicker v-model="filter.date_from"
                        monday-first=true>
            </datepicker>
          </b-form>
        </p>
        <p>
          <b-form aria-label="To:">
            To:
            <datepicker v-model="filter.date_to"
                        style="margin-bottom: 10px"
                        monday-first=true>
            </datepicker>
          </b-form>
        </p>
      </b-form-group>

      <b-form-group id="input-group-5"
                    label="   Due date:   "
                    label-for="input-5"
                    label-class="font-weight-bold pt-0"
                    class="input-group">
        <p>
          <b-form aria-label="From:">
            From:
            <datepicker v-model="filter.due_from"
                        monday-first=true>
            </datepicker>
          </b-form>
        </p>
        <p>
          <b-form aria-label="To:">
            To:
            <datepicker v-model="filter.due_to"
                        style="margin-bottom: 10px"
                        monday-first=true>
            </datepicker>
          </b-form>
        </p>
      </b-form-group>

      <p>
        <b-form-group id="input-group-3"
                      label="Skills:"
                      label-for="input-3"
                      style="margin-bottom: 20px"
                      label-class="font-weight-bold pt-0"
                      class="input-group">
          <b-container fluid v-for="(skillF,index) in filter.skillsF">
            <b-row>
              <b-col>
                <b-form-select
                  id="input-{{index}}"
                  v-model="skillF.name">
                  <option v-for="skill in getSkills.skills">
                    {{skill.name}}
                  </option>
                </b-form-select>
              </b-col>
              <b-col cols="3"> Value: {{skillF.value}}
              </b-col>
              <b-col cols='1'>
                <b-button @click="deleteSkill(index)" variant="primary"> -</b-button>
              </b-col>
            </b-row>
            <b-row>
              <b-col>
                <b-form-input id="skill-range-{{skillF.name}}" v-model="skillF.value" type="range" min="1"
                              max="5"></b-form-input>
              </b-col>
              <b-col cols="1"></b-col>
            </b-row>
            <b-row>
              <h1>
              </h1>
            </b-row>
          </b-container>
        </b-form-group>
        <b-button type="addSkill"
                  variant="success"
                  @click="addSkill">
          Add Skill
        </b-button>
      </p>


      <p>
        <b-form-group id="input-group-4"
                      label="Author:"
                      label-for="input-4"
                      label-class="font-weight-bold pt-0"
                      margin-top="50px">
          <b-input-group>
            <b-form-input class="mb-2" v-model="findUser" placeholder="Type user name"></b-form-input>
            <b-input-group-append>
              <b-button size="sm" :disabled="!findUser" @click="retrieveUsers">Find</b-button>
            </b-input-group-append>
          </b-input-group>

          <b-form-select :select-size="4">
            <option v-for="user in getUsers.users"
                    @click="filter.selectedUser=user">
              {{user.name}}
            </option>
          </b-form-select>
          <a>Selected: {{filter.selectedUser.name}}</a>
        </b-form-group>
      </p>
    </b-form-group>

    <b-button size="sm" type="submit" variant="primary" @click="onSubmit">Submit</b-button>
    <b-button size="sm" type="reset" variant="danger" @click="onReset">Reset</b-button>
  </b-card>

</template>

<script>
  import axios from 'axios';
  import Datepicker from 'vuejs-datepicker';
  import * as types from '../../store/mutation-types'


  export default {
    name: "MyFilter",
    props: {
      show: Object
    },
    components: {Datepicker},
    data() {
      return {
        getSkills: {skills: [{name: 'Nothing in here yet'}]},
        getUsers: {users: [{name: 'There is nobody here yet'}]},
        errors: [],
        filter: {
          find_name: '',
          date_from: '',
          date_to: '',
          due_from: '',
          due_to: '',
          selectedUser: {name: ''},
          skillsF: [
            {name: '', value: 0}
          ]
        },
        findUser: '',
      }
    },
    methods: {
      retrieveSkills() {
        axios.get('http://localhost:80/api/v1/skill', {
          headers: types.HEADER
        }).then(response => {
          if (response) {
            this.getSkills.skills = response.data;
          }
        })
          .catch(e => {
            this.errors.push(e);
            console.log(e);
          });
      },
      retrieveUsers() {
        axios.get('http://localhost:80/api/v1/person', {
          params: {
            findName: this.findUser
          },
          headers: types.HEADER
        }).then(response => {
          console.log(response.data);
          if (response) {
            this.getUsers.users = response.data.items;
          }
        }).catch(e => {
          this.errors.push(e);
          console.log(e);
        });
      },
      // Filter
      addSkill() {
        this.filter.skillsF.push({
          name: '',
          value: 0
        });
        this.show = Object(false);
        this.$nextTick(() => {
          this.show = Object(true);
        })
      },
      deleteSkill(index) {
        this.filter.skillsF.splice(index, 1);
      },
      onSubmit(evt) {
        evt.preventDefault();
        this.$emit('filter', this.filter);
      },
      onReset(evt) {
        evt.preventDefault();
        // Reset our form values
        this.refreshFilter();
        this.$emit('filter', this.filter);
      },
      refreshFilter() {
        this.filter.find_name = '';
        this.filter.date_from = '';
        this.filter.date_to = '';
        this.filter.due_from = '';
        this.filter.due_to = '';
        this.filter.skillsF = [
          {name: '', value: 0}
        ];
        this.filter.selectedUser = {name: ''};
        // Trick to reset/clear native browser form validation state
        this.show = Object(false);
        this.$nextTick(() => {
          this.show = Object(true);
        })
      }
    },
    mounted() {
      this.refreshFilter();
      this.retrieveSkills();
      this.retrieveUsers();
    }

  }

</script>

<style scoped>
  .input-group {
    label-cols-sm: 3;
    label-align-sm: right;
    label-class: font-weight-bold pt-0
  }
</style>
