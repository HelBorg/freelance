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
      status: Object
    },
    components: {Comment},
    data() {
      return {
        newComment: ''
      }
    },
    methods: {
      saveComment() {
        fetch('/api/v1/review', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + localStorage.getItem('JWT')
          },
          body: JSON.stringify({
            description: this.newComment,
            taskId: {
              taskId: this.$route.params.id
            }
          })
        })
          .then(
            function (response) {
              if (response.status !== 200) {
                console.log('Looks like there was a problem. Status Code: ' +
                  response.status);
                this.newComment = '';
                return;
              }
              alert("Success!");
              response.json().then(function (data) {
                console.log(data)
                this.newComment = '';
              })
            }
          )

      },
      loadComments() {
        let self = this;
        fetch('/api/v1/review/' + this.$route.params.id, {
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
                console.log(data)
                self.comments = data
                console.log(self.comments)
              })
            }
          )
      },
      updateComment(comment) {
        this.comments.push(comment);
      },
    }
  }
</script>

<style scoped>

</style>
