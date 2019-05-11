<template>
  <div id="tasksList" v-if="show">
    <div>
      <Navbar/>
    </div>
    <div>

      <Menu></Menu>

      <div style="width: 80%; float: right">
        <p>
          <b-table id="user"
                   :items="user"
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
        </p>
        <p>
          <b-input-group>
            <b-form-input v-model="findUser" placeholder="Type user name"></b-form-input>
            <b-input-group-append>
              <b-button :disabled="!findUser" @click="refreshList">Find</b-button>
            </b-input-group-append>
          </b-input-group>
        </p>

        <p>
          <b-table id="users"
                   title="Users"
                   :items="getUsers.items"
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
        </p>


        <div>
          <MyPagination :currentPage="filter.currentPage"
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
        },
        user: {},
        filter: {
          sort: null,
          currentPage: 0,
          pageSize: 10
        },
        findUser: '',
        fields: {
          name: {
            key: 'name',
            label: 'Name'
          },
          rating: {
            key: 'rating',
            label: 'Rating'
          },
          email: {
            key: 'email',
            label: 'Email'
          },
          skills: {
            key: 'skills',
            label: 'Skills'
          }
        }
      }
    },
    methods: {
      retrieveUsers() {
        axios.get('http://localhost:80/api/v1/person', {
            params: {
              size: this.filter.pageSize,
              page: this.filter.currentPage,
              sort: this.filter.sort
            },
            headers: {
              Authorization: 'Bearer ' + localStorage.getItem('JWT')
            }
          }
        ).then(response => {
          console.log(response.data);
          if (response) {
            this.getUsers = response.data;
          }
        })
          .catch(e => {
            this.errors.push(e);
            console.error(e);
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
        this.filter.currentPage = changeTo;
        this.refreshList();
        this.show = false;
        this.$nextTick(() => {
          this.show = true
        })
      },
      changePerPage(perPage) {
        this.filter.pageSize = perPage;
        this.refreshList();
      },
      goToUser(record) {
        this.$router.push({name: 'User', params: {id: record.id}});
      },
      getUserId() {
        console.log(this.getUsers);
        this.getUsers.user = [];
        axios.get('http://localhost:80/api/v1/me', {
            headers: {
              Authorization: 'Bearer ' + localStorage.getItem('JWT')
            }
          }
        ).then(response => {
          console.log(response.data);
          if (response) {
            this.user.id = response.data.id
          }
        }).catch(e => {
          this.errors.push(e);
          console.log(e);
        });
        this.getUserInfo();
      },
      getUserInfo() {
        axios.get('http:/localhost:80/api/v1/person/' + this.user.id, {
          headers: {
            Authorization: 'Bearer ' + localStorage.getItem('JWT')
          }
        }).then(response => {
          console.log(response.data);
          if (response) {
            this.user = response.data;
          }
        }).catch(e => {
          this.errors.push(e);
          console.error(e);
        });
      }
    },
    mounted() {
      this.getUserId();
      this.refreshList();
    }
  }
</script>
