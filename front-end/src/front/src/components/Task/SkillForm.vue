<template>
  <div class="mt-4">
    <h5 v-if=skills class="lead">
      <strong>Skills:</strong>
      <Skill
        v-on:remove="loadSkills"
        v-if="renderSkills"
        v-for="each in skills"
        :skill="each"
        :status="status"
        :curs="current"></Skill>
    </h5>

    <div v-if="($router.currentRoute.name === 'User' && current == $route.params.id)|| status === 'IN_DESIGN'">
      <b-form-select v-model="skill_name" style="width:20%">
        <option :value="null">Select skill name</option>
        <option value="java">Java</option>
        <option value="sql">SQL</option>
        <option value="spring">Spring</option>
        <option value="javascript">Javascript</option>
      </b-form-select>

      <b-form-select v-model="skill_level" style="width:20%">
        <option :value="null">Select skill level</option>
        <option value="1">Bad</option>
        <option value="2">semi-good</option>
        <option value="3">good</option>
        <option value="4">semi-profi</option>
        <option value="5">profi</option>
      </b-form-select>

      <b-button @click="addSkill" variant="success" class="p-1"> Add skill</b-button>
    </div>
  </div>
</template>

<script>
  import Skill from "./Skill.vue"
  import * as types from '../../store/mutation-types'


  export default {
    created() {
      this.loadSkills()
      this.getCurrentUserId()
      console.log(this.$router.currentRoute.name + ' ---- current route')
    },
    data() {
      return {
        renderSkills:true,
        skill_name: null,
        skill_level: null,
        skills: []
      }
    },
    props: {
      current: Object,
      status: Object
    },
    components: {
      Skill
    },
    methods: {
      reloadSkills() {
        let self = this
        self.renderSkills= false;
        self.$nextTick(() => {
          self.renderSkills = true;
        });
      },
      addSkill() {
        let self = this
        if (self.$router.currentRoute.name === 'Task') {
          return self.addTaskSkill()
        }
        if (self.$router.currentRoute.name === 'User') {
          return self.addUserSkill()
        }
      },
      addTaskSkill() {
        let self = this;
        fetch('/api/v1/task-skill/' + self.$route.params.id, {
          method: 'POST',
          headers: types.HEADER,
          body: JSON.stringify({
            skillName: {
              name: self.skill_name
            },
            level: self.skill_level
          })

        })
          .then(
            function (response) {
              console.log(self.skills)
              if (response.status !== 200) {
                console.log('Looks like there was a problem. Status Code: ' +
                  response.status);
              }
              self.loadSkills()
              self.reloadSkills()
            }
          )
      },
      addUserSkill() {
        let self = this;
        fetch('/api/v1/user-skill/' + self.$route.params.id, {
          method: 'POST',
          headers: types.HEADER,
          body: JSON.stringify({
            skillName: {
              name: self.skill_name
            },
            level: self.skill_level
          })

        })
          .then(
            function (response) {
              console.log(self.skills)
              if (response.status !== 200) {
                console.log('Looks like there was a problem. Status Code: ' +
                  response.status);
              }
              self.loadSkills()
              self.reloadSkills()
            }
          )
      },
      getCurrentUserId: function () {
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
      },
      loadSkills(){
        let self = this.skills;
        if (self.$router.currentRoute.name === 'Task') {
          fetch('/api/v1/task-skill/' + self.$route.params.id, {
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
                  self.skills = data;
                })
              }
            )
        }
        if (self.$router.currentRoute.name === 'User') {
          fetch('/api/v1/user-skill/' + self.$route.params.id, {
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
                  self.skills = data;
                })
              }
            )
        }
      }

    }

  }


</script>

<style scoped>

</style>
