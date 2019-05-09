<template>
  <div>
    <h5 class="mt-2 mb-4 lead">
      <strong>Comments</strong>
    </h5>
    <div>
      <div class="d-inline" v-if="status !== 'CURRENT' || status !== 'IN_DESIGN'">
        <b-form-textarea
          id="textarea"
          size="sm"
          rows="1"
          max-rows="8"
          v-model="newComment"
          placeholder="New comment"
        ></b-form-textarea>
        <b-button class="mt-2" variant="success" @click="saveComment">Add new comment</b-button>
      </div>

      <div class="column">
        <Comment v-for="comment in comments" :comment="comment" :status="status"></Comment>
      </div>
    </div>
  </div>
</template>

<script>
  import Comment from "./Comment.vue"

  export default {
    props: {
      comments: Object,
      status: Object,
    },
    components: {Comment},
    data() {
      return {
        newComment: ''
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
            done:false,
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
