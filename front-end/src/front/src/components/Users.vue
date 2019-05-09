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
        <b-table id="users"
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
                    :disabled="getUsers.currentPage===0">
            First
          </b-button>
          <b-button variant="light"
                    @click="changePage(getUsers.currentPage - 1)"
                    :disabled="getUsers.currentPage===0">
            Prev
          </b-button>
          <b-button variant="light"
                    v-for="index in getUsers.pagesCount"
                    @click="changePage(index - 1)"
          >
            {{index}}
          </b-button>
          <b-button :disabled="(getUsers.pagesCount-1)===getUsers.currentPage"
                    @click="changePage(getUsers.currentPage + 1)"
                    variant="light">
            Next
          </b-button>
          <b-button :disabled="getUsers.currentPage===(getUsers.pagesCount-1)"
                    @click="changePage(getUsers.pagesCount - 1)"
                    variant="light">
            Last
          </b-button>
        </div>
        {{getUsers.users}}
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
        show: true,
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
                sort: this.getUsers.sort
              }
          }
        ).then(response => {
            console.log(response.data);
            if (response) {
              this.getUsers.hasNextPage = response.data.hasNextPage;
              this.getUsers.hasPreviousPage = response.data.hasPreviousPage;
              this.getUsers.pagesCount = response.data.pagesCount;
              this.getUsers.sort = response.data.sort;
              this.getUsers.find = response.data.find;
              this.getUsers.users = []; //remove default msg from users
              for (let p = 0; t < this.getUsers.pageSize; t++) {
                this.getUsers.users.push({
                  id: response.data.items[p].id,
                  name: response.data.items[p].name,
                  email: response.data.items[p].email,
                  rating: response.data.items[p].rating,
                  skills: []
                });
                for (let sk in response.data.items[p].skills) {
                  this.getUsers.users[p].skills.push(response.data.items[p].skills[sk].skillName.name)
                }
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
