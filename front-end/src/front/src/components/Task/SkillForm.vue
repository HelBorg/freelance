<template>
  <div>
  <h5 class="lead">
    <strong>Nessesary skills</strong>
  </h5>
  <div v-if="status === 'IN_DESIGN'" >
    <b-form-select v-model="skill_name" class="mb-3">
      <option :value="null">Please select skill name</option>
      <option value="java">Java</option>
      <option value="sql">SQL</option>
      <option value="spring">Spring</option>
      <option value="javascript">Javascript</option>
    </b-form-select>

    <b-form-select v-model="skill_level" class="mb-3">
      <option :value="null">Please select skill level</option>
      <option value="1">Bad</option>
      <option value="25">semi-good</option>
      <option value="50">good</option>
      <option value="75">semi-profi</option>
      <option value="99">profi</option>
    </b-form-select>

    <b-button @click="addTaskSkill" variant="success"> Add skill</b-button>

  </div>

    <div class="column">
      <Skill v-for="each in skills" :skill="each" :status="status"></Skill>
    </div>
  </div>
</template>

<script>
  import Skill from "./Skill.vue"
    export default {
    data(){
      return{
        skill_name:null,
        skill_level:null
      }
    },

      props: {
        skills: Object,
        status: Object
      },
      components: {
        Skill
      },
      methods: {
        addTaskSkill() {
          let self = this;
          if(!self.checkForArray(self.skill_name, self.skills)){
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
              level: self.skill_level,
            })

          })
            .then(
              function (response) {
                console.log(self.skills)
                if (response.status !== 200) {
                  console.log('Looks like there was a problem. Status Code: ' +
                    response.status);
                  return;
                }
                window.location.reload()
              }
            )
          }else alert("Skill already exists")
        },

        checkForArray: function(element, array) {
          var length = array.length;
          for(var i = 0; i < length; i++) {
            if(array[i] === element) return true;
          }
          return false;
        }

      }

      }


</script>

<style scoped>

</style>
