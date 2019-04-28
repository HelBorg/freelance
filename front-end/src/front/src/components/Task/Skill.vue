<template>
  <b-card  style="border:0">
    <b-card-header class="p-1">
      {{skill.skillName.name}}
      <b-button v-if="status === 'IN_DESIGN'" class="p-1 ml-3" variant="danger" @click="deleteTaskSkill">Delete</b-button>
    </b-card-header>
    <b-progress :max="100" height="2rem">
      <b-progress-bar  :value="skill.level" >
        <strong>{{skill.level}}%</strong>
      </b-progress-bar>
    </b-progress>
  </b-card>
</template>

<script>

    export default {
      props: {
        skill: Object,
        status: Object
      },
      methods:{
        deleteTaskSkill() {
          let self = this;
          fetch('/api/v1/task-skill/' + self.skill.id, {
            method: 'DELETE',
            headers: {
              'Content-Type': 'application/json',
              'Authorization': 'Bearer ' + localStorage.getItem('JWT')
            },
          })
            .then(
              function (response) {
                if (response.status !== 200) {
                  console.log('Looks like there was a problem. Status Code: ' +
                    response.status);
                  return;
                }
                window.location.reload()
              }
            );
        }
      }
    }
</script>

<style scoped>

</style>
