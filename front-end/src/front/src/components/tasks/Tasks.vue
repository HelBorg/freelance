<template>
  <div id="container">
    <div>
      <Navbar/>
    </div>
    <div>
      <div>
        <Menu/>
      </div>
      <div style="float:right; width: 60%">
        <div>
          <b-table id="tasks"
                   title="Tasks"
                   :items="getTasks.tasks"
                   :fields="fields"
                   small
                   @row-clicked="goToTask"
          >
            <template slot="skills" slot-scope="row">
              <div v-for="skill in row.item.skills">
                {{skill}}
              </div>
            </template>
          </b-table>
        </div>

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
        <div v-if="page.showFilter">
          <Filter></Filter>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import Menu from "../Menu";
  import Navbar from "../Navbar";
  import axios from 'axios';
  import Filter from "./Filter";

  export default {
    name: "Tasks",
    components: {Menu, Navbar, Filter},
    data() {
      return {
        page: {
          name: null,
          get: null,   //get all tasks, get by author, by candidates
          showFilter: true,
          user_id: 1,
          find: null
        },
        indicator: 10,
        errors: [],
        show: true,
        getTasks: {
          tasks: [{name: 'Sorry, there is no tasks in here yet'}],
          hasPreviousPage: null,
          hasNextPage: null,
          pagesCount: null,
          sort: null,
          currentPage: 0,
          pageSize: 3,
          find: null
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
                id: this.page.user_id
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
                  id: response.data.tasks[t].id,
                  name: response.data.tasks[t].name,
                  status: response.data.tasks[t].status,
                  date_from: null,
                  deadline: response.data.tasks[t].deadline,
                  rate: response.data.tasks[t].rate,
                  author: response.data.tasks[t].author.name,
                  skills: [],
                  assigned: response.data.tasks[t].assignedUser
                });
                for (let sk in response.data.tasks[t].skills) {
                  this.getTasks.tasks[t].skills.push(response.data.tasks[t].skills[sk].skillName.name);
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
      },
      changePage(changeTo) {
        console.log(this.getTasks.currentPage);
        this.getTasks.currentPage = changeTo;
        this.refreshList();
        this.show = false
        this.$nextTick(() => {
          this.show = true
        })
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
            this.page.get = 'candidate';
            break;
          case 'mine':
            this.fields.author.thClass = 'd-none';
            this.fields.author.tdClass = 'd-none';
            this.page.showFilter = false;
            this.page.get = 'author';
            break;
          case 'in_work':
            this.page.get = 'assigned';
            break;
          default:
            break;
        }
      },
      goToTask(record) {
        this.$router.push({name: 'Task', params: {id: record.id}});
      }
    },
    mounted() {
      this.extractPageParam();
      this.retrieveTasks();
    }
  };
</script>

<style scoped>

</style>
