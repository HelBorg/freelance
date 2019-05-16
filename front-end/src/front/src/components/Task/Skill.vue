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

  import * as types from '../../store/mutation-types'

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
          headers: types.HEADER,
        })
          .then(
            function (response) {
              if (response.status !== 200) {
                console.log('Looks like there was a problem. Status Code: ' +
                  response.status);
                return;
              }
              self.$emit('remove');
            }
          );
      },
      deleteUserSkill() {
        let self = this;
        fetch('/api/v1/user-skill/' + self.skill.id, {
          method: 'DELETE',
          headers: types.HEADER,
        })
          .then(
            function (response) {
              if (response.status !== 200) {
                console.log('Looks like there was a problem. Status Code: ' +
                  response.status);
                return;
              }
              self.$emit('remove');
            }
          );
      }
    }
  }
</script>

<style scoped>

</style>
