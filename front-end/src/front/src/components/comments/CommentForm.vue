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

      <Comment  v-on:update="updateHandler"
                v-if="renderComments"
                v-for="comment in comments"
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
  import * as types from '../../store/mutation-types'


  export default {
    created(){
      this.loadComments()
    },
    props: {
      status: Object,
      authorId: Object,
      currentUserId: Object
    },
    components: {Comment},
    data() {
      return {
        renderComments:true,
        comments:[],
        newComment: '',
        done: '',
        taskId: ''
      }
    },
    methods: {
      reloadComments() {
        let self = this
        self.renderComments= false;
        self.$nextTick(() => {
          self.renderComments = true;
        });
      },
      saveComment() {
        let self = this

        fetch('/api/v1/review', {
          method: 'POST',
          headers:types.HEADER,
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
              self.loadComments()
              self.reloadComments()
              self.newComment = ''
            }
          )
      },
      loadComments(){
      let self = this
      if (self.$router.currentRoute.name === 'Task') {
        fetch('/api/v1/review/' + self.$route.params.id, {
          method: 'GET',
          headers: types.HEADER
        })
          .then(
            function (response) {
              if (response.status !== 200) {
                console.log('Looks like there was a problem. Status Code: ' +
                  response.status);
                return;
              }
              response.json().then(function (data) {
                self.comments = data;
              })
            }
          )
      }
        if (self.$router.currentRoute.name === 'User') {
          fetch('/api/v1/review/user/' + self.$route.params.id, {
            method: 'GET',
            headers: types.HEADER
          })
            .then(
              function (response) {
                if (response.status !== 200) {
                  console.log('Looks like there was a problem. Status Code: ' +
                    response.status);
                  return;
                }
                response.json().then(function (data) {
                  self.comments = data;
                })
              }
            )
        }
      },
      updateHandler(){
        let self = this
        self.$emit('update');
      }

    }
  }
</script>

<style scoped>

</style>
