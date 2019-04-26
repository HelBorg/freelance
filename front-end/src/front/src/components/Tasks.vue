<template>
  <div id="container">
    <div>
      <Navbar/>
    </div>
    <div>
      <b-row>
      <b-col cols="3">
        <Menu/>
      </b-col>
      <b-col>
        <b-card>
          <div>
            <b-table id="tasks"
                     title="Tasks"
                     :items="getTasks.tasks"
                     :fields="fields"
                     small
                     @row-clicked="goToTask(item.id)"
            >
            </b-table>
          </div>

<!--          Pagination-->
          <div v-if="getTasks.pagesCount>1">
            <b-button variant="light"
                      @click="changePage(0)"
                      active="getTasks.currentPage!=0">
              First
            </b-button>
            <b-button variant="light"
                      @click="changePage(getTasks.currentPage - 1)"
                      active="getTasks.currentPage!=0">
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
                      active="getTasks.currentPage!=getTasks.pageCount-1">
              Next
            </b-button>
            <b-button variant="light"
                      @click="changePage(getTasks.pagesCount - 1)"
                      active="getTasks.currentPage!=getTasks.pageCount-1">
              Last
            </b-button>
          </div>
        </b-card>
      </b-col>
      </b-row>
    </div>
  </div>
</template>

<script>
  import Menu from "./Menu";
  import Navbar from "./Navbar";
  import axios from 'axios'

  export default {
    name: "Tasks",
    components: { Menu, Navbar},
    data() {
      return {
        page:{
          name: null,
          get: null,   //get all tasks, get by author, by candidates
          user_id: null
        },
        errors:[],
        show: true,
        task:{
          id: null,
          name: null,
          date_from: null,
          status: null,
          deadline: null,
          rate: null,
          author: null,
          skills: [],
          assigned: []
        },
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
            sortable: true,
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
            sortable: true,
            thClass: null,
            tdClass: null
          },
          deadline: {
            key: 'deadline',
            label: 'To',
            sortable: true,
            thClass: null,
            tdClass: null
          },
          rate: {
            key: 'rate',
            label: 'Rate',
            sortable: true,
            thClass: null,
            tdClass: null
          },
          author: {
            key: 'author',
            label: 'Author',
            sortable: true,
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
        axios.get('http://localhost:80/api/v1/tasks',{
          params:
        {
          size: this.getTasks.pageSize,
          page: this.getTasks.currentPage,
          pageName: this.page.get,
          id: this.page.user_id
        }}
        )
          .then(response => {
            console.log(response.data);
            if (response) {
              this.getTasks.tasks = []; //remove default msg from tasks
              for (let t in response.data.tasks) {
                this.task.id = response.data.tasks[t].id;
                this.task.name = response.data.tasks[t].name;
                this.task.status = response.data.tasks[t].status;
                this.task.date_from = null;
                this.task.deadline = response.data.tasks[t].deadline;
                this.task.rate = response.data.tasks[t].rate;
                this.task.author = response.data.tasks[t].author.name;
                for(let skill in response.data.tasks[t].taskSkills) {
                  this.task.skills.push(skill.name);
                }
                for (let assign in response.data.tasks[t].assignedUser) {
                  this.task.assigned.push(assign.name);
                }
                this.getTasks.tasks.push(this.task);
                this.task.skills = null;
                this.task.assigned = null;
              }
              this.getTasks.hasNextPage = response.data.hasNextPage;
              this.getTasks.hasPreviousPage = response.data.hasPreviousPage;
              this.getTasks.pagesCount = response.data.pagesCount;
              this.getTasks.sort = response.data.sort;
              this.getTasks.find = response.data.find;
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
        switch(this.page.name) {
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
            this.page.get = 'author';
            break;
          case 'in_work':
            this.page.get = 'assigned';
            break;
          default: break;
        }
      },
      goToTask(id) {
        this.$router.push('task/:'+id);
      },
    },
    mounted() {
      this.extractPageParam();
      this.retrieveTasks();
    }
  };
</script>

<style scoped>

</style>
