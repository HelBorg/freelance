<template>
  <b-badge variant="warning" class="ml-2 mr-2">
    {{skill.skillName.name}} - {{skill.level}}

    <span @click="deleteSkill"
     v-if="curs == $route.params.id || status === 'IN_DESIGN'"
     class="ml-1"
     style="cursor: pointer;color:red">x
    </span>

  </b-badge>
</template>

<script>
  export default {

    props: {
      route: Object,
      skill: Object,
      status: Object,
      curs: Object
    },
    methods: {
      deleteSkill(){
        let self = this
        if(self.$router.currentRoute.name === 'Task') {
          return self.deleteTaskSkill()
        }if(self.$router.currentRoute.name === 'User'){
          return self.deleteUserSkill()
        }
      },
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
      },
      deleteUserSkill() {
        let self = this;
        fetch('/api/v1/user-skill/' + self.skill.id, {
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
