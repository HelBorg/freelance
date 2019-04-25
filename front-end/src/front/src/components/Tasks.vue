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
                     small>
            </b-table>
          </div>
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
          status: true,
          filter: true,
          author: true
        },
        errors:[],
        show: true,
        trash: null,
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
            label: 'Name',
            sortable: true
          },
          status: {
            label: 'Status'
          },
          date_from: {
            label: 'Date of creating',
            sortable: true
          },
          deadline: {
            label: 'To',
            sortable: true
          },
          task_rate: {
            label: 'Rate',
            sortable: true
          },
          author: {
            label: 'Author',
            sortable: true
          },
          skills: {
            label: 'Skills'
          },
          assigned: {
            label: 'Assigned'
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
          page: this.getTasks.currentPage
        }}
        )
          .then(response => {
            console.log(response.data)
            if (response) {
              this.getTasks.tasks = response.data.tasks;
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
            this.page.status = false;
            break;
        }
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
