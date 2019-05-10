<template>
  <div id="tasksList">
    <div>
      <Navbar/>
    </div>
    <div>
      <div>
        <Menu/>
      </div>
      <div style="float:right; width: 75%">
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
                          text="Sort by:">
                <b-dropdown-item @click="changeSort(date_from)">Date of creation</b-dropdown-item>
                <b-dropdown-item @click="changeSort(date_to)">Deadline</b-dropdown-item>
                <b-dropdown-item>None</b-dropdown-item>
              </b-dropdown>
            </div>
            <div v-if="this.show">
            <b-table id="tasks"
                     title="Tasks"
                     :items="getTasks.tasks"
                     :fields="fields"
                     small
                     hover
                     striped
                     @row-clicked="goToTask">
              <template slot="skills" slot-scope="row">
                <div v-for="skill in row.item.skills">
                  {{skill}}
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

            <b-col style="width: 40px;">
              <div>
                <MyFilter :show="this.page.showFilter"
                          @submit="handleSubmit"/>
              </div>
            </b-col>
        </b-row>
      </div>
    </div>
  </div>
</template>
<script>
  import Menu from "../Menu";
  import Navbar from "../Navbar";
  import axios from 'axios';
  import MyFilter from "./MyFilter";
  import MyPagination from "./MyPagination";

  export default {
    name: "Tasks",
    components: {MyPagination, Menu, Navbar, MyFilter},
    data() {
      return {
        sort: '',
        page: {
          name: null,
          get: null,   //get all tasks, get by author, by candidates
          showFilter: false,
          user_id: 1,
          find: null,
          date_from: null,
          date_to: null,
          currentPage: 0,
          pageSize: 10
        },
        errors: [],
        show: true,
        getTasks: {
          tasks: [{name: 'Sorry, there is no tasks in here yet', id: -1}],
          hasPreviousPage: null,
          hasNextPage: null,
          pagesCount: 0,
          sort: null,
          find_name: null,
          find_date_from: null,
          find_date_to: null,
          filter: null
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
          date_from: {
            key: 'date_from',
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
          rate: {
            key: 'rate',
            label: 'Rate',
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
          assigned: {
            key: 'assigned',
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
              find_name: this.getTasks.find_name,
              sort: this.getTasks.sort,
              date_from: this.getTasks.find_date_from,
              date_to: this.getTasks.find_date_to
            },
            headers: {
              Authorization: 'Bearer ' + localStorage.getItem('JWT')
            }
          }
        ).then(response => {
          console.log(response.data);
          if (response.data.items) {
            this.getTasks.hasNextPage = response.data.hasNextPage;
            this.getTasks.hasPreviousPage = response.data.hasPreviousPage;
            this.getTasks.pagesCount = response.data.pagesCount;
            this.getTasks.sort = response.data.sort;
            this.getTasks.find = response.data.find;
            this.getTasks.tasks = []; //remove default msg from tasks
            for (let t = 0; t < this.page.pageSize; t++) {
              // let date = response.data.items[t].deadline;
              // let date_from = date.getUTCFullYear() + "/" + (date.getUTCMonth() + 1) + "/" + date.getUTCDate();
              // date = response.data.items[t].createdTime;

              this.getTasks.tasks.push({
                id: response.data.items[t].id,
                name: response.data.items[t].name,
                status: response.data.items[t].status,
                date_from: null,
                deadline: null,
                rate: response.data.items[t].rate,
                author: response.data.items[t].author.name,
                skills: [],
                assigned: null
              });
              //Skills
              for (let sk in response.data.items[t].skills) {
                this.getTasks.tasks[t].skills.push(response.data.items[t].skills[sk].skillName.name);
              }
              //Assigned user
              // let assign = response.data.items[t].assignedUser.name;
              // if (assign != null) {
              //   this.getTasks.tasks[t].assign = assign;
              // } else {
              //   this.getTasks.tasks[t].assign = null;
              // }
            }
          }
        })
          .catch(e => {
            this.errors.push(e);
          });
      },
      dateConstructor: function (date) {
        if (date == null) {
          return null;
        } else {
          return date.getUTCDate() + "/" + (date.getUTCMonth() + 1) + "/" + date.getUTCFullYear();
        }
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
        this.getTasks.sort = sortBy;
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
            this.fields.status.thClass = 'd-none';
            this.fields.status.tdClass = 'd-none';
            this.fields.assigned.thClass = 'd-none';
            this.fields.assigned.tdClass = 'd-none';
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
            this.fields.assigned.thClass = 'd-none';
            this.fields.assigned.tdClass = 'd-none';
            this.fields.status.thClass = 'd-none';
            this.fields.status.tdClass = 'd-none';
            this.page.get = 'in_work';
            break;
          default:
            break;
        }
      },
      goToTask(record) {
        if (this.getTasks.tasks[0].id > -1) {
          this.$router.push({name: 'Task', params: {id: record.id}});
        }
      },
      handleSubmit(find_name, date_to, date_from) {
        console.log(find_name + date_to + date_from);
        this.getTasks.find_name = find_name;
        this.getTasks.find_date_to = date_to;
        this.getTasks.find_date_from = date_from;
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

<style scoped>

</style>
