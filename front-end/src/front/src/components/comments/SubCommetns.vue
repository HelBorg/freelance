<template>
  <b-card style="border:0; width:70%; float:right" class="mt-2">
    <img src="https://www.ondernemers-peelland.nl/wp-content/themes/ondernemers-peelland/images/profile_image.jpg"
         width="40" height="40"
         style=" border-radius:50%;">
    <span style="cursor:pointer" @click="goToUserPage(subComment.user.id)">{{subComment.user.name}}</span>
    <span class="text-muted small">answered {{subComment.createdTime.substring(0, 10)}}</span>

    <b-button
      v-if="(subComment.user.id !== authorId &&
                  currentUserId === authorId) && (
                  status === 'PUBLISHED' ||
                  status === 'ASSIGNED')"
      @click="assignUser(subComment.user.id)"
      style="float:right" size="sm" variant="outline-success" class="ml-4 p-1">
      Assign this user
    </b-button>

    <b-card-body class="p-2">{{subComment.description}}</b-card-body>

    <b-input-group>
      <b-form-input size="sm" v-model="replyText"></b-form-input>
      <b-input-group-append>
        <b-button @click="saveComment" variant="info" size="sm">Reply</b-button>
      </b-input-group-append>
    </b-input-group>

  </b-card>
</template>

<script>

  import router from "../../router";
  import * as types from '../../store/mutation-types'


  export default {

    props: {
      parentId: Object,
      subComment: Object,
      status: Object,
      authorId: Object,
      currentUserId: Object
    },
    components: {Comment},
    data() {
      return {
        replyText: '',
        newComment: ''
      }
    },
    methods: {

      saveComment() {
        let self = this
        fetch('/api/v1/review', {
          method: 'POST',
          headers: types.HEADER,
          body: JSON.stringify({
            description: self.replyText,
            done: false,
            parentId: self.parentId,
            taskId: {
              taskId: self.$route.params.id
            }
          })
        })
          .then(
            function (response) {
              if (response.status !== 200) {
                console.log('Looks like there was a problem. Status Code: ' +
                  response.status);
                return;
              }
              self.$emit('add');
              }
          )
      },
      assignUser(userId) {
        let self = this;
        fetch('/api/v1/task/' + self.$route.params.id + '/assigned/' + userId, {
          method: 'PUT',
          headers: types.HEADER,
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
          )
      },
      goToUserPage(id) {
        router.replace('/user/' + id)
      }
    }
  }
</script>

<style scoped>

</style>
