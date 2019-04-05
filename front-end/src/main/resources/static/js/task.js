var skills = [];


var skillsService= {
  findAll(fn) {
    axios
      .get('/api/v1/skills')
      .then(response => fn(response))
      .catch(error => console.log(error))
  }
};
