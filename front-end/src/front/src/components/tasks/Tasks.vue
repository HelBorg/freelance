<template>
  <div id="tasksList" v-if="this.show">
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

            <!--          Pagination-->
            <div v-if="getTasks.pagesCount>1">
              <b-button variant="light"
                        @click="changePage(0)"
                        :disabled="getTasks.currentPage==0">
                First
              </b-button>
              <b-button variant="light"
                        @click="changePage(getTasks.currentPage - 1)"
                        :disabled="getTasks.currentPage==0">
                Prev
              </b-button>
              <b-button variant="light"
                        v-for="index in getTasks.pagesCount"
                        @click="changePage(index - 1)"
              >
                {{index}}
              </b-button>
              <b-button variant="light"
                        @click="changePage(getTasks.currentPage + 1)"
                        :disabled="getTasks.currentPage==(getTasks.pagesCount-1)">
                Next
              </b-button>
              <b-button variant="light"
                        @click="changePage(getTasks.pagesCount - 1)"
                        :disabled="getTasks.currentPage==(getTasks.pagesCount-1)">
                Last
              </b-button>
            </div>
          </b-col>
          <b-col>
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

  export default {
    name: "Tasks",
    components: {Menu, Navbar, MyFilter},
    data() {
      return {
        sort: '',
        page: {
          name: null,
          get: null,   //get all tasks, get by author, by candidates
          showFilter: true,
          user_id: 1,
          find: null,
          date_from: null,
          date_to: null
        },
        errors: [],
        show: true,
        getTasks: {
          tasks: [{name: 'Sorry, there is no tasks in here yet'}],
          hasPreviousPage: null,
          hasNextPage: null,
          pagesCount: null,
          sort: null,
          currentPage: 0,
          pageSize: 10,
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
        },
      }
    },
    methods: {
      retrieveTasks() {
        axios.get('http://localhost:80/api/v1/task', {
            params:
              {
                size: this.getTasks.pageSize,
                page: this.getTasks.currentPage,
                pageName: this.page.get,
                id: this.page.user_id,
                find_name: this.getTasks.find_name,
                sort: this.getTasks.sort,
                date_from: this.getTasks.find_date_from,
                date_to: this.getTasks.find_date_to
              }
          }
        )
          .then(response => {
            console.log(response.data);
            if (response) {
              this.getTasks.hasNextPage = response.data.hasNextPage;
              this.getTasks.hasPreviousPage = response.data.hasPreviousPage;
              this.getTasks.pagesCount = response.data.pagesCount;
              this.getTasks.sort = response.data.sort;
              this.getTasks.find = response.data.find;
              this.getTasks.tasks = []; //remove default msg from tasks
              for (let t = 0; t < this.getTasks.pageSize; t++) {
                this.getTasks.tasks.push({
                  id: response.data.items[t].id,
                  name: response.data.items[t].name,
                  status: response.data.items[t].status,
                  date_from: null,
                  deadline: response.data.items[t].deadline,
                  rate: response.data.items[t].rate,
                  author: response.data.items[t].author.name,
                  skills: [],
                  assigned: response.data.items[t].assignedUser
                });
                for (let sk in response.data.items[t].skills) {
                  this.getTasks.tasks[t].skills.push(response.data.items[t].skills[sk].skillName.name);
                }
              }
            }
          })
          .catch(e => {
            this.errors.push(e)
          });
      },
      refreshList() {
        this.retrieveTasks();
        this.show = false;
        this.$nextTick(() => {
          this.show = true
        });
      },
      changePage(changeTo) {
        console.log(this.getTasks.currentPage);
        this.getTasks.currentPage = changeTo;
        this.refreshList();
        this.show = false;
        this.$nextTick(() => {
          this.show = true
        })
      },
      changePerPage(perPage) {
        this.getTasks.pageSize = perPage;
        this.refreshList();
      },
      changeSort(sortBy) {
        this.getTasks.sort = sortBy;
        this.refreshList();
      },
      getUserId() {
        fetch('/api/v1/me', {
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
                this.page.user_id = data.id
              })
            }
          )
      },
      extractPageParam() {
        this.page.name = this.$route.params.pageName;
        switch (this.page.name) {
          case 'search':
            this.fields.status.thClass = 'd-none';
            this.fields.status.tdClass = 'd-none';
            this.page.get = 'tasks';
            break;
          case 'candidates':
            this.getUserId();
            this.page.get = 'candidate';
            break;
          case 'mine':
            this.getUserId();
            this.fields.author.thClass = 'd-none';
            this.fields.author.tdClass = 'd-none';
            this.page.get = 'author';
            break;
          case 'in_work':
            this.getUserId();
            this.page.get = 'assigned';
            break;
          default:
            break;
        }
      },
      goToTask(record) {
        this.$router.push({name: 'Task', params: {id: record.id}});
      },
      handleSubmit(find_name, date_to, date_from) {
        this.getTasks.find_name = find_name;
        this.getTasks.find_date_to = date_to;
        this.getTasks.find_date_from = date_from;
        this.refreshList();
      }
    },
    mounted() {
      this.extractPageParam();
      this.refreshList();
    }
  };
</script>

<style scoped>

</style>
