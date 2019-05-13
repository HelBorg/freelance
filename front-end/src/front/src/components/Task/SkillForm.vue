<template>
  <div class="mt-4">
  <h5 class="lead">
   <strong>Skills:</strong>
      <Skill v-for="each in skills" :skill="each" :status="status" :curs="current"></Skill>
  </h5>

  <div v-if="current == $route.params.id || status === 'IN_DESIGN'" >
    <b-form-select v-model="skill_name" style="width:20%">
      <option :value="null">Select skill name</option>
      <option value="java">Java</option>
      <option value="sql">SQL</option>
      <option value="spring">Spring</option>
      <option value="javascript">Javascript</option>
    </b-form-select>

    <b-form-select v-model="skill_level" style="width:20%">
      <option :value="null">Select skill level</option>
      <option value="bad">Bad</option>
      <option value="semi-good">semi-good</option>
      <option value="good">good</option>
      <option value="semi-profi">semi-profi</option>
      <option value="profi">profi</option>
    </b-form-select>

    <b-button @click="addSkill" variant="success" class="p-1"> Add skill</b-button>
  </div>
  </div>
</template>

<script>
  //TODO: не отображаются скилы у юзера
  import Skill from "./Skill.vue"

    export default {
    beforeMount(){
      this.getCurrentUserId()
      console.log(this.$router.currentRoute.name + ' ---- current route')
    },
    data(){
      return{
        skill_name:null,
        skill_level:null,
      }
    },
      props: {
        current: Object,
        skills: Object,
        status: Object
      },
      components: {
        Skill
      },
      methods: {
      addSkill(){
        if(this.$router.currentRoute.name === 'Task') {
          this.addTaskSkill()
        }if(this.$router.currentRoute.name === 'User'){
          this.addUserSkill()
        }
      },
        addTaskSkill() {
          let self = this;
          self.skills.push({
            skillName: {
              name: self.skill_name
            },
            level: self.skill_level
          })
          fetch('/api/v1/task-skill/' + self.$route.params.id, {
            method: 'POST',
            headers: {
              'Content-Type': 'application/json',
              'Authorization': 'Bearer ' + localStorage.getItem('JWT')
            },
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
              }
            )
        },
      addUserSkill() {
        let self = this;
        self.skills.push({
          skillName: {
            name: self.skill_name
          },
          level: self.skill_level
        })
        fetch('/api/v1/user-skill/' + self.$route.params.id, {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + localStorage.getItem('JWT')
          },
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
            }
          )
      },
        getCurrentUserId: function ()
        {
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
                  self.current = data.id;
                })
              }
            )
        }

      }

    }


</script>

<style scoped>

</style>
