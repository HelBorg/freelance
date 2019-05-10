<template>
  <div id="tasksList" v-if="show">
    <div>
      <Navbar/>
    </div>
    <div>

      <Menu></Menu>

      <div style="width: 75%; float: right">
        <b-table id="user"
                 :items="getUsers.user"
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


        <div>
          <MyPagination :currentPage="getUsers.currentPage"
                        :pagesCount="getUsers.pagesCount"
                        @changePage="changePage"/>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import axios from 'axios';
  import Navbar from "./Navbar";
  import Menu from "./Menu";

  export default {
    name: "Users",
    components: {Navbar, Menu},
    data() {
      return {
        show: true,
        errors: [],
        getUsers: {
          users: [{name: 'Sorry, there is no tasks in here yet'}],
          user: [],
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
            params: {
              size: this.getUsers.pageSize,
              page: this.getUsers.currentPage,
              sort: this.getUsers.sort
            },
            headers: {
              Authorization: 'Bearer ' + localStorage.getItem('JWT')
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
            for (let p = 0; p < this.getUsers.pageSize; p++) {
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
            this.errors.push(e);
            console.error(e);
          });
      },
      refreshList() {
        this.getUserInfo();
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
      getUserInfo() {
        axios.get('http://localhost:80/api/v1/me', {
            headers: {
              Authorization: 'Bearer ' + localStorage.getItem('JWT')
            }
          }
        ).then(response => {
          console.log(response.data);
          if (response) {
            this.getUsers.user.push({
              id: response.data.id,
              name: response.data.name
            });
          }
        }).catch(e => {
          this.errors.push(e);
          console.error(e);
        });
      }
    },
    mounted() {
      this.refreshList();
    }
  }
</script>
