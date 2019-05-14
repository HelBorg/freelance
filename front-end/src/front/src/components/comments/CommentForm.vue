<template>
  <div>
    <h5 v-if=comments class="mt-2 mb-4 lead">
      <strong>Comments</strong>
    </h5>
    <div>
      <div class="d-inline" v-if="status !== 'IN_DESIGN' && $router.currentRoute.name !== 'User'">
        <b-form-textarea
          id="textarea"
          size="sm"
          rows="1"
          max-rows="8"
          v-model="newComment"
          placeholder="New comment"
        ></b-form-textarea>
        <b-button size="sm" class="mt-2" variant="success" @click="saveComment">Add new comment</b-button>
      </div>

      <Comment v-for="comment in comments"
               :comment="comment"
               :status="status"
               :author-id="authorId"
               :current-user-id="currentUserId"
      ></Comment>
    </div>
  </div>
</template>

<script>
  import Comment from "./Comment.vue"

  export default {

    props: {
      comments: Object,
      status: Object,
      authorId: Object,
      currentUserId: Object
    },
    components: {Comment},
    data() {
      return {
        newComment: '',
        done: '',
        taskId: ''
      }
    },
    methods: {
      saveComment() {
        let self = this

        fetch('/api/v1/review', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + localStorage.getItem('JWT')
          },
          body: JSON.stringify({
            description: self.newComment,
            parentId: null,
            done: false,
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
              window.location.reload()
            }
          )
      }
    }
  }
</script>

<style scoped>

</style>
