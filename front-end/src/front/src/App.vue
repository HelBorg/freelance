<template>
  <div id="container">
    <div id="navbar">
    <b-navbar toggleable="lg" type="dark" variant="dark">
      <!-- Надо найти картинку-->
      <b-navbar-brand href="#">
        <img src="https://placekitten.com/g/30/30" class="d-inline-block align-top" alt="Kitten">
      </b-navbar-brand>

      <b-navbar-brand href="#">Freelance</b-navbar-brand>

      <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

      <b-collapse id="nav-collapse" is-nav>

        <!-- Right aligned nav items -->
        <b-navbar-nav class="ml-auto">
          <b-nav-form>
            <b-form-input size="sm" class="mr-sm-2" placeholder="Search"></b-form-input>
            <b-button size="sm" class="my-2 my-sm-0" type="submit">Search</b-button>
          </b-nav-form>

          <b-nav-item-dropdown right>
            <!-- Using 'button-content' slot -->
            <template slot="button-content"><em>User</em></template>
            <b-dropdown-item href="#">Profile</b-dropdown-item>
            <b-dropdown-item href="#">Sign Out</b-dropdown-item>
          </b-nav-item-dropdown>
        </b-navbar-nav>
      </b-collapse>
    </b-navbar>
  </div >
    <div id="menu"><b-list-group>
      <b-list-group-item href="#" active>Task</b-list-group-item>
      <b-list-group-item button>Search</b-list-group-item>
      <b-list-group-item button>In work</b-list-group-item>
      <b-list-group-item button>Candidates</b-list-group-item>
      <b-list-group-item button>Mine</b-list-group-item>
      <b-list-group-item href="#" active>Messages</b-list-group-item>
      <b-list-group-item button>Inbox</b-list-group-item>
      <b-list-group-item button>Outbox</b-list-group-item>
    </b-list-group></div>
  </div>
</template>

<script>
  export default {
    name: "navBar"
  }
</script>

