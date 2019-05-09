<template>
  <div class="mt-4">
  <h5 class="lead">
   <strong>Skills:</strong>
      <Skill v-for="each in skills" :skill="each" :status="status"></Skill>
  </h5>

  <div v-if="status === 'IN_DESIGN' || status === 'CURRENT'" >
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

    <b-button @click="addTaskSkill" variant="success" class="p-1"> Add skill</b-button>
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
        route: Object,
        cusr: Object,
        skills: Object,
        status: Object
      },
      components: {
        Skill
      },
      methods: {
        addTaskSkill() {
          let self = this;
          self.skills.push({
            skillName: {
              name: self.skill_name
            },
            level: self.skill_level
          })
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
              level: self.skill_level
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
