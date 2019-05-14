<template>
  <div v-if="pagesCount>1">
    <b-button variant="light"
              @click="changePage(0)"
              :disabled="currentPage===0">
      First
    </b-button>
    <b-button variant="light"
              @click="changePage(currentPage - 1)"
              :disabled="currentPage===0">
      Prev
    </b-button>
    <b-button variant="light"
              v-for="index in pagesCount"
              @click="changePage(index - 1)"
    >
      {{index}}
    </b-button>
    <b-button variant="light"
              @click="changePage(pagesCount - 1)"
              :disabled="currentPage===(pagesCount-1)">
      Next
    </b-button>
    <b-button variant="light"
              @click="changePage(pagesCount - 1)"
              :disabled="currentPage===(pagesCount-1)">
      Last
    </b-button>
  </div>
</template>

<script>
  export default {
    name: "MyPagination",
    props: {
      currentPage: Number,
      pagesCount: Number
    },
    methods: {
      changePage(toPage) {
        this.$emit('changePage', toPage);
      }
    }
  }
</script>

<style scoped>

</style>
