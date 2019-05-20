<template>
  <div id="tasksList">
    <div>
      <Navbar/>
    </div>
    <div>

      <Menu></Menu>

      <div style="width: 80%; float: right" v-if="show">
        <p>
          <b-table id="user"
                   :items="user"
                   :fields="fields"
                   small
                   hover
                   striped
                   @row-clicked="goToUser">
            <template slot="place_in_rating" slot-scope="row">
              {{row.item.placeInRating}}
            </template>
            <template slot="rating" slot-scope="row">
              <div>
                {{ratingName(row.item.rating)}}({{row.item.rating}})
              </div>
            </template>

            <template slot="skills" slot-scope="row">
              <div v-for="skill in row.item.skills">
                {{skill.skillName.name}}:{{skill.level}}
              </div>
            </template>
          </b-table>
          Осталось до следующего уровня: {{remainsToGet(user[0].rating)}}
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
            <template slot="place_in_rating" slot-scope="row">
              {{filter.currentPage*filter.pageSize + row.index + 1}}
            </template>
            <template slot="skills" slot-scope="row">
              <div v-for="skill in row.item.skills">
                {{skill.skillName.name}}:{{skill.level}}
              </div>
            </template>
          </b-table>
        </p>


        <MyPagination :currentPage="filter.currentPage"
                      :pagesCount="getUsers.pagesCount"
                      @changePage="changePage"></MyPagination>
      </div>
    </div>
  </div>
</template>

<script>
  import axios from 'axios';
  import Navbar from "./Navbar";
  import Menu from "./Menu";
  import * as types from '../store/mutation-types'


  export default {
    name: "Users",
    components: {Navbar, Menu},
    beforeMount() {
      this.getUserId();
    },
    data() {
      return {
        show: true,
        errors: [],
        getUsers: {},
        user: [{
          id: null
        }],
        filter: {
          sort: null,
          currentPage: 0,
          pageSize: 10
        },
        findUser: '',
        fields: {
          place_in_rating: {
            name: 'place',
            label: 'Place in rating'
          },
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
              sort: this.filter.sort,
              findName: this.findUser
            },
            headers: types.HEADER
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
      ratingName: function (rate) {
        if (rate < 50) {
          return "Новичок"
        }
        if (rate < 200) {
          return "Прошаренный"
        }
        if (rate < 500) {
          return "Профи"
        }
        return "Гуру"
      },
      remainsToGet: function (rate) {
        if (rate < 50) {
          return 50 - rate
        }
        if (rate < 200) {
          return 200 - rate
        }
        if (rate < 500) {
          return 500 - rate
        }
        return 0
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
        axios.get('http://localhost:80/api/v1', {
            headers: types.HEADER
          }
        ).then(response => {
          console.log(response.data);
          if (response) {
            this.user[0].id = response.data.id;
          }
          this.getUserInfo();
        }).catch(e => {
          this.errors.push(e);
          console.log(e);
        });
      },
      getUserInfo() {
        axios.get('http://localhost:80/api/v1/person/' + this.user[0].id, {
          headers: types.HEADER
        }).then(response => {
          console.log(response.data);
          this.user = [];
          if (response) {
            this.user.push(response.data);
          }
          this.refreshList();
        }).catch(e => {
          this.errors.push(e);
          console.error(e);
        });
      }
    },

  }
</script>
