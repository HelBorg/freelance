<template>
  <div>
    <b-card class="mt-2">
      <img src="https://www.ondernemers-peelland.nl/wp-content/themes/ondernemers-peelland/images/profile_image.jpg"
           width="40" height="40"
           style=" border-radius:50%;">
      <span style="cursor:pointer" @click="goToUserPage(comment.user.id)">{{comment.user.name}}</span>
      <span class="text-muted small">commented {{comment.createdTime.substring(0, 10)}}</span>

      <b-button
        v-if="(comment.user.id !== authorId &&
                  currentUserId === authorId) && (
                  status === 'PUBLISHED' ||
                  status === 'ASSIGNED')"
        @click="assignUser(comment.user.id)" style="float:right" variant="outline-success" class="ml-3 p-1">
        Assign this user
      </b-button>

      <b-card-body class="p-2">{{comment.description}}</b-card-body>

      <b-input-group v-if="$router.currentRoute.name !== 'User'">
        <b-form-input v-model="replyText" size="sm"></b-form-input>
        <b-input-group-append>

          <b-button @click="saveComment" variant="info" size="sm">Reply</b-button>
        </b-input-group-append>
      </b-input-group>

      <SubComments  v-on:add="addSubHandler"
                    v-if="renderSubComments"
                    v-for="subComment in subComments"
                   :sub-comment="subComment"
                   :status="status"
                   :current-user-id="currentUserId"
                   :author-id="authorId"
                   :parent-id="comment.id"
      ></SubComments>

    </b-card>

  </div>
</template>
<script>
  import router from "../../router";
  import SubComments from "./SubCommetns.vue"
  import * as types from '../../store/mutation-types'


  export default {
    created() {
      this.loadSubComments()
    },
    props: {
      comment: Object,
      status: Object,
      authorId: Object,
      currentUserId: Object
    },
    components: {SubComments},
    data() {
      return {
        renderSubComments:true,
        replyText: '',
        subComments: []
      }
    },
    methods: {
      reloadSubComments() {
        let self = this
        self.renderSubComments= false;
        self.$nextTick(() => {
          self.renderSubComments = true;
        });
      },
      saveComment() {
        let self = this
        fetch('/api/v1/review', {
          method: 'POST',
          headers:types.HEADER,
          body: JSON.stringify({
            description: self.replyText,
            parentId: self.comment.id,
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
              self.loadSubComments()
              self.reloadSubComments()
            }
          )
      },
      goToUserPage(id) {
        router.replace('/user/' + id)
      },
      assignUser(userId) {
        let self = this;
        fetch('/api/v1/task/' + self.$route.params.id + '/assigned/' + userId, {
          method: 'PUT',
          headers: types.HEADER
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
      loadSubComments() {
        let self = this;
        fetch('/api/v1/review/' + self.comment.id + '/subcomments', {
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
                self.subComments = data;
                console.log(self.subComments)
              })
            }
          )
      },
      addSubHandler(){
        let self = this;
        self.loadSubComments()
        self.reloadSubComments()
      }
    }
  }
</script>

<style scoped>
</style>
