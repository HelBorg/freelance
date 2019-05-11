<template>
  <div id="tasksList">
    <div>
      <Navbar/>
    </div>
    <div>
      <Menu/>
      <div style="float:right; width: 80%">
        <b-row>
          <b-col>
            <div>
              <b-dropdown id="dropdown-1"
                          class="m-md-2"
                          text="Number of tasks">
                <b-dropdown-item @click="changePerPage(5)">5</b-dropdown-item>
                <b-dropdown-item @click="changePerPage(10)">10</b-dropdown-item>
                <b-dropdown-item @click="changePerPage(50)">50</b-dropdown-item>
                <b-dropdown-item @click="changePerPage(100)">100</b-dropdown-item>
              </b-dropdown>
              <b-dropdown id="dropdown-2"
                          class="m-md-2"
                          text="Sort by">
                <b-dropdown-item @click="changeSort(createdTime)">Date of creation</b-dropdown-item>
                <b-dropdown-item @click="changeSort(deadline)">Deadline</b-dropdown-item>
                <b-dropdown-item @click="changeSort(taskId)">None</b-dropdown-item>
              </b-dropdown>
              <b-dropdown id="dropdown-3"
                          class="m-md-2"
                          text="Direction">
                <b-dropdown-item @click="changeSortDir()">From old to new</b-dropdown-item>
                <b-dropdown-item @click="changeSortDir()">From new to old</b-dropdown-item>
              </b-dropdown>
            </div>
            <div v-if="this.show">
              <b-table id="tasks"
                       title="Tasks"
                       :items="getTasks.items"
                       :fields="fields"
                       small
                       hover
                       striped
                       @row-clicked="goToTask">
                <template slot="createdTime" slot-scope="row">
                  <div>
                    {{dateConstructor(row.item.createdTime)}}
                  </div>
                </template>
                <template slot="deadline" slot-scope="row">
                  <div>
                    {{dateConstructor(row.item.deadline)}}
                  </div>
                </template>
                <template slot="author" slot-scope="row">
                  <div>
                    {{row.item.author.name}}
                  </div>
                </template>
                <template slot="skills" slot-scope="row">
                  <div v-for="skill in row.item.skills">
                    {{skill.name}}
                  </div>
                </template>
                <template slot="assignedUser" slot-scope="row">
                  <div v-if="row.item.assignedUser">
                    {{row.item.assignedUser.name}}
                  </div>
                </template>
              </b-table>
            </div>
            <div>
              <MyPagination :currentPage="page.currentPage"
                            :pagesCount="getTasks.pagesCount"
                            @changePage="changePage"/>
            </div>
          </b-col>

          <b-col cols="4">
            <div>
              <MyFilter :show="this.page.showFilter"
                        @filter="handleFilter"/>
            </div>
          </b-col>
        </b-row>
      </div>
    </div>
  </div>
</template>
<script>
  import moment from 'moment';
  import axios from 'axios';
  import Menu from "../Menu";
  import Navbar from "../Navbar";
  import MyFilter from "./MyFilter";
  import MyPagination from "./MyPagination";

  export default {
    name: "Tasks",
    components: {MyPagination, Menu, Navbar, MyFilter},
    data() {
      return {
        page: {
          name: null,
          get: null,   //get all tasks, get by author, by candidates
          showFilter: true,
          user_id: 1,
          currentPage: 0,
          pageSize: 10
        },
        errors: [],
        show: true,
        getTasks: {},
        sort: null,
        sortDir: null,
        filter: {
          find_name: '',
          date_from: '',
          date_to: '',
          due_from: '',
          due_to: '',
          selectedUser: {},
          skillsF: [
            {name: '', value: 0}
          ]
        },
        fields: {
          name: {
            key: 'name',
            label: 'Name',
            thClass: null,
            tdClass: null
          },
          status: {
            key: 'status',
            label: 'Status',
            thClass: null,
            tdClass: null
          },
          createdTime: {
            key: 'createdTime',
            label: 'Date of creating',
            thClass: null,
            tdClass: null
          },
          deadline: {
            key: 'deadline',
            label: 'To',
            thClass: null,
            tdClass: null
          },
          author: {
            key: 'author',
            label: 'Author',
            thClass: null,
            tdClass: null
          },
          skills: {
            key: 'skills',
            label: 'Skills',
            thClass: null,
            tdClass: null
          },
          assignedUser: {
            key: 'assignedUser',
            label: 'Assigned',
            thClass: null,
            tdClass: null
          }
        }
      }
    },
    methods: {
      retrieveTasks() {
        axios.get('http://localhost:80/api/v1/task', {
            params: {
              size: this.page.pageSize,
              page: this.page.currentPage,
              pageName: this.page.get,
              id: this.page.user_id,
              find_name: this.filter.find_name,
              sort: this.sort,
              date_from: this.filter.date_from,
              date_to: this.filter.date_to,
              due_from: this.filter.due_from,
              due_to: this.filter.due_to,
              skillsF: JSON.stringify(this.filter.skillsF)
                .replace("[", "")
                .replace("]", ""),
              author: this.filter.selectedUser.name,
              filter: JSON.stringify(this.filter)
                .replace("[", "")
                .replace("]", "")
            },
            headers: {
              Authorization: 'Bearer ' + localStorage.getItem('JWT')
            }
          }
        ).then(response => {
          console.log(response.data);
          if (response) {
            this.getTasks = response.data;
          }
        }).catch(e => {
          this.errors.push(e);
        });
        console.log(this.getTasks);
      },
      dateConstructor: function (date) {
        return moment(date.replace("T", " ").substring(0, 22)).format('Do / MM / YYYY');
      },
      // Исполльзовать, когда при обновлении таблицы хотим перейти на первую страницу
      refreshList() {
        this.page.currentPage = 0;
        this.retrieveTasks();
        this.show = false;
        this.$nextTick(() => {
          this.show = true
        });
      },
      changePage(changeTo) {
        this.page.currentPage = changeTo;
        this.retrieveTasks();
        this.show = false;
        this.$nextTick(() => {
          this.show = true
        })
      },
      changePerPage(perPage) {
        this.page.pageSize = perPage;
        this.refreshList();
      },
      changeSort(sortBy) {
        this.sort = sortBy;
        this.refreshList();
      },
      changeSortDir(Dir) {
        this.sortDir = Dir;
        this.refreshList();
      },
      getUserId() {
        axios.get('http://localhost:80/api/v1/me', {
            headers: {
              Authorization: 'Bearer ' + localStorage.getItem('JWT')
            }
          }
        ).then(response => {
          console.log(response.data);
          if (response) {
            this.page.user_id = response.data.id
          }
        }).catch(e => {
          this.errors.push(e);
          console.log(e);
        });
      },
      extractPageParam() {
        this.page.name = this.$route.params.pageName;
        switch (this.page.name) {
          case 'search':
            // this.fields.status.thClass = 'd-none';
            // this.fields.status.tdClass = 'd-none';
            // this.fields.assignedUser.thClass = 'd-none';
            // this.fields.assignedUser.tdClass = 'd-none';
            this.page.get = 'tasks';
            break;
          case 'candidates':
            this.page.get = 'candidate';
            break;
          case 'mine':
            this.fields.author.thClass = 'd-none';
            this.fields.author.tdClass = 'd-none';
            this.page.get = 'author';
            break;
          case 'in_work':
            this.fields.assignedUser.thClass = 'd-none';
            this.fields.assignedUser.tdClass = 'd-none';
            this.fields.status.thClass = 'd-none';
            this.fields.status.tdClass = 'd-none';
            this.page.get = 'in_work';
            break;
          default:
            break;
        }
      },
      goToTask(record) {
        if (this.getTasks.items[0].id > -1) {
          this.$router.push({name: 'Task', params: {id: record.id}});
        }
      },
      handleFilter(filter) {
        this.filter = filter;
        this.refreshList();
      }
    },
    mounted() {
      this.getUserId();
      this.extractPageParam();
      this.refreshList();
    }
  };
</script>
