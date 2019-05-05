<template>
  <div id="tasksList" v-if="this.show">
    <div>
      <Navbar/>
    </div>
    <div>
      <div>
        <Menu/>
      </div>
      <div>
        <b-table id="tasks"
                 title="Users"
                 :items="getUsers.users"
                 :fields="fields"
                 small
                 hover
                 striped
                 @row-clicked="goToUser">
          <template slot="skills" slot-scope="row">
            <div v-for="skill in row.item.skills">
              {{skill}}
            </div>
          </template>
        </b-table>

        <!--          Pagination-->
        <div v-if="getUsers.pagesCount>1">
          <b-button variant="light"
                    @click="changePage(0)"
                    :disabled="getUsers.currentPage==0">
            First
          </b-button>
          <b-button variant="light"
                    @click="changePage(getUsers.currentPage - 1)"
                    :disabled="getUsers.currentPage==0">
            Prev
          </b-button>
          <b-button variant="light"
                    v-for="index in getUsers.pagesCount"
                    @click="changePage(index - 1)"
          >
            {{index}}
          </b-button>
          <b-button variant="light"
                    @click="changePage(getUsers.currentPage + 1)"
                    :disabled="getUsers.currentPage==(getUsers.pagesCount-1)">
            Next
          </b-button>
          <b-button variant="light"
                    @click="changePage(getUsers.pagesCount - 1)"
                    :disabled="getUsers.currentPage==(getUsers.pagesCount-1)">
            Last
          </b-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import axios from 'axios';

  export default {
    name: "Tasks",
    data() {
      return {
        getUsers: {
          users: [{name: 'Sorry, there is no tasks in here yet'}],
          hasPreviousPage: null,
          hasNextPage: null,
          pagesCount: null,
          sort: null,
          currentPage: 0,
          pageSize: 10
        },
        fields: {
          name: {
            key: 'name',
            label: 'Name',
            thClass: null,
            tdClass: null
          }
        }
      }
    },
    methods: {
      retrieveUsers() {
        axios.get('http://localhost:80/api/v1/person', {
            params:
              {
                size: this.getUsers.pageSize,
                page: this.getUsers.currentPage,
                pageName: this.page.get,
                id: this.page.user_id,
                sort: this.getUsers.sort
              }
          }
        )
          .then(response => {
            console.log(response.data);
            if (response) {
              this.getUsers.hasNextPage = response.data.hasNextPage;
              this.getUsers.hasPreviousPage = response.data.hasPreviousPage;
              this.getUsers.pagesCount = response.data.pagesCount;
              this.getUsers.sort = response.data.sort;
              this.getUsers.find = response.data.find;
              this.getUsers.users = []; //remove default msg from users
              for (let t = 0; t < this.getUsers.pageSize; t++) {
                this.getUsers.users.push({
                  id: response.data.items[t].id,
                  name: response.data.items[t].name
                });
              }
            }
          })
          .catch(e => {
            this.errors.push(e)
          });
      },
      refreshList() {
        this.retrieveUsers();
        this.show = false;
        this.$nextTick(() => {
          this.show = true
        });
      },
      changePage(changeTo) {
        console.log(this.getUsers.currentPage);
        this.getUsers.currentPage = changeTo;
        this.refreshList();
        this.show = false;
        this.$nextTick(() => {
          this.show = true
        })
      },
      changePerPage(perPage) {
        this.getUsers.pageSize = perPage;
        this.refreshList();
      },
      goToUser(record) {
        this.$router.push({name: 'User', params: {id: record.id}});
      },
    },
    mounted() {
      this.refreshList();
    }
  }
</script>

<style>

</style>
