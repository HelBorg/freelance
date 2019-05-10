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
          <b-form-input v-model="find_name" placeholder="Type to Search"></b-form-input>
          <b-input-group-append>
            <b-button :disabled="!find_name" @click="find_name = ''">Clear</b-button>
          </b-input-group-append>
        </b-input-group>
      </b-form-group>


      <b-form-group id="input-group-2"
                    label="Date of creation:"
                    label-for="input-2"
                    label-class="font-weight-bold pt-0"
                    class="input-group">
        <p>
          <b-form aria-label="From:">
            From:
            <datepicker v-model="date_from"
                        monday-first=true>
            </datepicker>
          </b-form>
        </p>
        <p>
          <b-form aria-label="To:">
            To:
            <datepicker v-model="date_to"
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
          <b-container fluid v-for="(skillF,index) in skillsF">
            <b-row>
              <b-col>
                <b-form-select
                  id="input-{{index}}"
                  v-model="skillsF[index].name">
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
                <b-form-input id="skill-range-{{skillF.name}}" v-model="skillF.value" type="range" min="0"
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
            <b-form-input v-model="findUser" placeholder="Type user name"></b-form-input>
            <b-input-group-append>
              <b-button :disabled="!findUser" @click="findUserByName">Find</b-button>
            </b-input-group-append>
          </b-input-group>

          <b-form-select :select-size="4">
            <option v-for="user in filterUsers(getUsers.users)"
                    @click="selectedUser=user">
              {{user.name}}
            </option>
          </b-form-select>
          <a>Selected: {{selectedUser.name}}</a>
        </b-form-group>
      </p>
    </b-form-group>

    <b-button type="submit" variant="primary" @click="onSubmit">Submit</b-button>
    <b-button type="reset" variant="danger" @click="onReset">Reset</b-button>
  </b-card>

</template>

<script>
  import axios from 'axios';
  import Datepicker from 'vuejs-datepicker';

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
        find_name: '',
        date_from: '',
        date_to: '',
        skillsF: [
          {name: '', value: 0}
        ],
        findUser: '',
        filteredUsers: [],
        selectedUser: {name: '', id: null}
      }
    },
    methods: {
      retrieveSkills() {
        axios.get('http://localhost:80/api/v1/skill', {
          headers: {
            Authorization: 'Bearer ' + localStorage.getItem('JWT')
          }
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
          headers: {
            Authorization: 'Bearer ' + localStorage.getItem('JWT')
          }
        }).then(response => {
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
        this.skillsF.push({
          name: '',
          value: 0
        });
        this.show = Object(false);
        this.$nextTick(() => {
          this.show = Object(true);
        })
      },

      deleteSkill(index) {
        this.skillsF.splice(index, 1);
      },

      filterUsers: function (users) {
        this.filteredUsers = [];
        if (this.findUser.length < 1) {
          return users;
        }
        for (let user in users) {
          if (users[user].name.includes(this.findUser)) {
            this.filteredUsers.push(users[user]);
          }
        }
        if (this.filteredUsers.length < 1) {
          return users;
        } else {
          return this.filteredUsers;
        }
      },

      select(user) {
        this.selectedUser.name = user.name;
        this.selectedUser.id = user.id;
      },

      onSubmit(evt) {
        evt.preventDefault();
        alert(JSON.stringify(this.form));
        this.$emit('submit', this.find_name, this.date_to, this.date_from);
      },

      onReset(evt) {
        evt.preventDefault();
        // Reset our form values
        this.refreshFilter();
      },
      refreshFilter() {
        this.find_name = '';
        this.date_from = '';
        this.date_to = '';
        this.skillsF = [
          {name: '', value: 0}
        ];
        this.selectedUser = {};
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
