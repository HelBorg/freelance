<template>
  <b-table id="tasks"
           title="Tasks"
           :items="tasks"
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
        {{skill.skillName.name}}:{{skill.level}}
      </div>
    </template>
    <template slot="assignedUser" slot-scope="row">
      <div v-if="row.item.assignedUser">
        {{row.item.assignedUser.name}}
      </div>
    </template>
  </b-table>
</template>

<script>
  import moment from 'moment';

  export default {
    name: "Table_for_tasks",
    props:{
      tasks: Object
    },
    data() {
      return {
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
      extractPageParam() {
        switch (this.$route.params.pageName) {
          case 'search':
            this.fields.status.thClass = 'd-none';
            this.fields.status.tdClass = 'd-none';
            this.fields.assignedUser.thClass = 'd-none';
            this.fields.assignedUser.tdClass = 'd-none';
            break;
          case 'candidates':
            break;
          case 'mine':
            this.fields.author.thClass = 'd-none';
            this.fields.author.tdClass = 'd-none';
            break;
          case 'in_work':
            this.fields.assignedUser.thClass = 'd-none';
            this.fields.assignedUser.tdClass = 'd-none';
            this.fields.status.thClass = 'd-none';
            this.fields.status.tdClass = 'd-none';
            break;
          default:
            break;
        }
      },
      goToTask(record) {
        if (this.tasks[0].id > -1) {
          this.$router.push({name: 'Task', params: {id: record.id}});
        }
      },
      dateConstructor: function (date) {
        return moment(date.replace("T", " ").substring(0, 22)).format('Do / MM / YYYY');
      }
    },
    mounted() {
      this.extractPageParam();
    }
  }
</script>

<style scoped>

</style>
