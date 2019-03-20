<template>
    <strong class = "hw"> VueJS variable msg = {{ msg }}</strong>
</template>

<script>
export default {
  data(){
  	return {
  		msg: "Hello, World !"
  	}
  }
}
</script>

<style>
  .hw{
    color: red;
  }
</style>
