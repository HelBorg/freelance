<template>
  <div>
    <b-modal ref="add_comment" id="add_comment" hide-footer>
      <template slot="modal-title">
        Add comment
        <b-form-textarea
          id="textarea"
          size="lg"
          rows="3"
          max-rows="8"
          v-model="newComment"
        ></b-form-textarea>
      </template>

      <div style="float:right">
        <b-button @click="$refs['add_comment'].hide()" class="mt-3">Cansel</b-button>
        <b-button v-on:click="saveComment" class="mt-3" variant="danger">Add</b-button>
      </div>
    </b-modal>

    <hr>

    <div>
      <div class="d-inline-block">
        <b-button @click="$refs['add_comment'].show();">Add new comment</b-button>
        <h5 class="lead">Comments</h5>
      </div>

      <div class="column">
        <Comment v-for="comment in comments" :load="load" :comment="comment" :status="status"></Comment>
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
