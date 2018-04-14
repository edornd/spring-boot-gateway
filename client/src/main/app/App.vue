<template>
  <div id="app">
    <img src="./assets/logo.png">
    <h1>{{ msg }}</h1>
    <h2>Welcome</h2>
    <p>Logged in as:</p>
    <p>{{user}}</p>
    <p>Visit <a href="/me">http://localhost:8080/me</a> for the full Principal instance.</p>
    <p>Visit <a href="/api/user">http://localhost:8080/api/user</a> for the Principal in the Auth server (proxied)</p>
    <div>
      <h2>Things taken from the resource server</h2>
      <ul>
        <li v-for="item in items">{{item}}</li>
      </ul>
    </div>
  </div>
</template>

<script>
  import axios from 'axios'

  export default {
    name: 'app',
    data () {
      return {
        msg: 'Welcome to Your Vue.js App',
        user: null,
        items: []
      }
    },
    created(){
      axios.get("/me").then(response =>{
        this.user = response.data.name;
      }).catch(error => {
        this.user = `Error: ${error.statusText}`
      });
      axios.get("/api/resource").then(response =>{
        this.items = response.data;
      }).catch(err =>{
        this.items = [];
      })
    }
  }
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}

h1, h2 {
  font-weight: normal;
}

ul {
  list-style-type: none;
  padding: 0;
}

li {
  display: inline-block;
  margin: 0 10px;
}

a {
  color: #42b983;
}
</style>
