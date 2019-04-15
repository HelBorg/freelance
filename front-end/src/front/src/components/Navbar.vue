<template>
  <b-navbar toggleable="lg" type="dark" variant="dark" class="margin-down-10">
    <!-- Надо найти картинку-->
    <b-navbar-brand>
      <img src="https://placekitten.com/g/30/30" class="d-inline-block align-top" alt="Kitten">
    </b-navbar-brand>

    <b-navbar-brand href="#">Freelance</b-navbar-brand>

    <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

    <b-collapse id="nav-collapse" is-nav>

      <!-- Right aligned nav items -->
      <b-navbar-nav class="ml-auto">
        <b-nav-item-dropdown right>
          <!-- Using 'button-content' slot -->
          <template slot="button-content"><em>User</em></template>
          <b-dropdown-item href="#">Profile</b-dropdown-item>
          <b-dropdown-item :to="'login'">Sign Out</b-dropdown-item>
        </b-nav-item-dropdown>
      </b-navbar-nav>
    </b-collapse>
  </b-navbar>
</template>

<script>
  export default {
    name: "Navbar"
  }
</script>

<style scoped>
 .margin-down-10{
   margin-bottom: 10px;
 }
</style>
