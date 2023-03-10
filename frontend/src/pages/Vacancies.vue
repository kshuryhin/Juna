<template>
  <!DOCTYPE html>
  <html>
  <head>
    <title>Juna Jobs</title>
    <link rel="stylesheet" type="text/css" href="style.css">
  </head>
  <body>
  <header>
    <div class="logo">
      <h1>Juna Jobs</h1>
    </div>
    <nav>
      <ul>
        <li><a href="#">My Profile</a></li>
        <li><a href="#" class="active">Vacancies</a></li>
        <li><a href="#">Analytics</a></li>
        <li><a @click="this.logout()" href="#">Logout</a></li>
      </ul>
    </nav>
  </header>


  <main>
    <section id="filters">
      <h2>Filters</h2>
      <form @submit.prevent="applyFilters">
        <label for="country">Country:</label>
        <select id="country" name="Country" v-model="filters.selectedCountry">
          <option value="">All</option>
          <option value="Ukraine">Ukraine</option>
          <option value="Germany">Germany</option>
          <option value="USA">USA</option>
          <option value="England">England</option>
        </select>
        <label for="role">Role:</label>
        <select id="role" name="role" v-model="filters.selectedRole">
          <option value="">All</option>
          <option value="frontend">Frontend Developer</option>
          <option value="backend">Backend Developer</option>
          <option value="fullstack">Full Stack Developer</option>
        </select>
        <button type="submit">Apply Filters</button>
      </form>
    </section>
    <section id="jobs">
      <h2>Available Jobs</h2>
      <ul id="job-listings">
        <li class="job" v-for="job in jobs" :key="job.id">
          <h3>{{ job.name }}</h3>
          <p class="country">{{ job.country }}</p>
          <p class="description">{{ job.description }}</p>
          <a :href="job.applyUrl">Apply Now</a>
        </li>
      </ul>
    </section>
  </main>
  </body>
  </html>
</template>

<script>
import axios from 'axios';
import roles from "@/roles";

export default {
  created: function (){
    const role = localStorage.getItem('role')
    if (role !== roles.CANDIDATE) {
      this.$router.push('/')
    }
  },
  data() {
    return {
      filters: {
        selectedCountry: '',
        selectedRole: '',
      },
      jobs: []
    };
  },
  methods: {
    logout() {
      axios.put('http://localhost:8085/logout', {}, {
        headers: {
          'Authorization': localStorage.getItem('token')
        }
      })
      localStorage.clear()
      this.$router.push('/')
    },
    applyFilters() {
      let params = {}
      if (this.filters.selectedCountry) {
        params.country = this.filters.selectedCountry
      }
      if (this.filters.selectedRole) {
        params.role = this.filters.selectedRole
      }
      axios.get('http://localhost:8085/vacancies', {
        headers: {
          Authorization: localStorage.getItem('token')
        },
        params: params
      }).then(response => {
        this.jobs = response.data
      }).catch(error => {
        console.log(error)
      })
    }
  },
  mounted() {
    this.applyFilters();
  }
};
</script>



<style scoped>
/* Set body background and font */
body {
  background-color: white;
  font-family: Arial, sans-serif;
}

/* Set header style */
header {
  background-color: #168FF0;
  color: white;
  text-align: center;
  padding: 20px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

/* Set main container style */
main {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  margin: 20px;
}

/* Set filters section style */
#filters {
  width: 30%;
}

/* Set filters form style */
form {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
}

/* Set label style */
label {
  margin-bottom: 10px;
}

/* Set select style */
select {
  margin-bottom: 20px;
  padding: 5px;
  border-radius: 5px;
  border: none;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); /* Add a subtle box shadow */
  background-color: #f5f5f5; /* Add a light gray background color */
  font-size: 16px; /* Set a font size */
  font-weight: 500; /* Set a font weight */
  color: #333; /* Set a text color */
  transition: all 0.3s ease; /* Add a smooth transition on hover and focus */
}

select:hover,
select:focus {
  outline: none; /* Remove the default outline */
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.2); /* Add a stronger box shadow on hover and focus */
  background-color: #eee; /* Darken the background color on hover and focus */
}

/* Set button style */
button {
  background-color: #168FF0;
  color: white;
  padding: 10px 20px;
  border-radius: 5px;
  border: none;
  cursor: pointer;
}

/* Set jobs section style */
#jobs {
  width: 65%;
}

/* Set job list style */
ul {
  list-style-type: none;
  padding: 0;
}

/* Set job item style */
.job {
  margin-bottom: 20px;
  border: 2px solid #168FF0;
  border-radius: 10px;
  padding: 10px;
}

.job .description {
  display: -webkit-box;
  -webkit-line-clamp: 3;
  -webkit-box-orient: vertical;
  overflow: hidden;
  text-overflow: ellipsis;
}


/* Set job title style */
h3 {
  color: #168FF0;
}

.country {
  font-style: italic;
}

/* Set apply now link style */
a {
  background-color: #168FF0;
  color: white;
  padding: 10px 20px;
  border-radius: 5px;
  text-align: center;
  display: inline-block;
  margin-top: 10px;
  text-decoration: none;
  font-weight: bold;
}

/* Set logo style */
.logo {
  float: left;
}

/* Set navigation style */
nav {
  float: right;
  display: flex;
  justify-content: flex-end;
}

nav ul {
  list-style: none;
  margin: 0;
  padding: 0;
  display: flex;
}

nav li {
  margin-left: 20px;
}

nav a {
  display: block;
  padding: 10px;
  color: white;
  text-decoration: none;
}

nav a.active,
nav a:hover {
  background-color: #168FF0;
}

</style>
