<template>
  <div id="container">
      <Navbar/>
      <Menu/>
      <Task></Task>
    </div>
</template>


<script>
  import Navbar from "./Navbar";
  import Menu from "./Menu";
  import Task from "./Task";
  export default {
    name: 'home',
    components: {Menu, Navbar, Task}
  }
</script>
