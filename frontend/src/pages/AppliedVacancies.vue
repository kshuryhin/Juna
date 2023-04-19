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
        <router-link class="route-active" :to="{ name: 'candidateProfile'}">My Profile</router-link>
        <router-link :to="{ name: 'vacancies'}">Vacancies</router-link>
        <router-link :to="{ name: 'analytics'}">Analytics</router-link>
        <li><a @click="this.logout()" href="#">Logout</a></li>
      </ul>
    </nav>
  </header>

  <div class="tabs">
    <button class="tablinks" @click="navigateToPersonalInfo" id="defaultOpen">Personal Info</button>
    <button class="tablinks" @click="navigateToChangePassword">Change Password</button>
    <button class="tablinks" @click="navigateToSavedVacancies">Saved Vacancies</button>
    <button class="tablinks active" @click="navigateToAppliedVacancies">Applied Vacancies</button>
  </div>
  <main>
    <div class="search-container"> <!-- Add the search container -->
      <section id="search" class="search-centered">
        <h2 class="search-label">Search</h2>
        <div class="search-row">
          <input id="search" type="text" v-model="searchTerm" placeholder="Search by vacancy name" class="search-input" />
          <button @click="searchJobs" class="search-button">Search</button>
        </div>
      </section>

    </div>

    <div class="content-container">
      <section id="jobs">
        <ul id="job-listings">
          <li class="job" v-for="job in filteredJobs" :key="job.id">
            <h3>{{ job.name }}</h3>
            <p class="country">Country: {{ job.country }}</p>
            <p class="englishLevel">English level: {{ job.englishLevel }}</p>
            <p class="employmentType">Employment Type: {{ job.employmentType }}</p>
            <p class="salaryFrom">Salary From: {{ job.salaryFrom }}$</p>
            <p class="salaryTo">Salary To: {{ job.salaryTo }}$</p>
            <p class="description">{{ job.description }}</p>
            <router-link :to="{ name: 'vacancy', params: { id: job.id }}">Apply Now</router-link>
          </li>
        </ul>
      </section>
    </div>
  </main>
  <footer>
    <div class="footer-bottom">
      <p>&copy; 2023 Juna Jobs</p>
    </div>
  </footer>
  </body>
  </html>
</template>

<script>
import axios from 'axios';
import roles from '@/roles';
import authMixin from '@/components/authMixin.js';
import roleMixin from '@/components/roleMixin.js';

import {logout} from '@/utils/auth';
import silentLoginMixin from "@/components/silentLoginMixin";

export default {
  mixins: [authMixin, roleMixin, silentLoginMixin],
  requiredRole: roles.CANDIDATE,
  data() {
    return {
      searchTerm: '',
      filteredJobs: [],
      jobs: [],
      candidate: {}
    };
  },
  created() {
    this.getCandidateByEmail()
  },
  methods: {
    navigateToChangePassword() {
      this.$router.push('/change');
    },
    navigateToPersonalInfo() {
      this.$router.push('/candidate/profile');
    },
    navigateToSavedVacancies() {
      this.$router.push('/candidate/saved');
    },
    navigateToAppliedVacancies() {
      this.$router.push('/candidate/applied');
    },
    async getCandidateByEmail() {
      try {
        await axios.get('http://localhost:8085/candidates/email', {
          headers: {
            Authorization : localStorage.getItem('token')
          }
        }).then(response => {
          this.candidate = response.data;
          this.fetchJobs()
        })
      }catch (error){
        console.error(error)
      }
    },
    async fetchJobs() {
      try {
        console.log(this.candidate.id)
        const response = await axios.get(`http://localhost:8085/vacancies/candidates/${this.candidate.id}`, {
          headers: {
            Authorization: localStorage.getItem('token'),
          },
        });
        this.jobs = response.data;
        this.filteredJobs = response.data
      } catch (error) {
        console.error(error);
      }
    },
    searchJobs() {
      if (this.searchTerm) {
        this.filteredJobs = this.jobs.filter(job =>
            job.name.toLowerCase().includes(this.searchTerm.toLowerCase())
        );
      } else {
        this.filteredJobs = this.jobs;
      }
    },
    async logout() {
      logout();
      this.$router.push('/');
    },
  },
};
</script>


<style scoped>
html {
  height: 100%;
  min-height: 100vh;
}

body {
  display: flex;
  flex-direction: column;
  min-height: 100vh;
  margin: 0;
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
  display: flex;
  flex-direction: column;
  align-items: flex-start;
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
  width: 50%; /* Change to 50% */
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

.category {
  font-style: italic;
}

.salaryFrom {
  font-style: italic;
}

.salaryTo {
  font-style: italic;
}

.grade {
  font-style: italic;
}

.englishLevel {
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

.router-link-active {
  text-decoration: underline;
}

nav a.active,
nav a:hover {
  background-color: #168FF0;
}
/* Add these new styles to your existing CSS */
.search-container {
  position: absolute;
  width: 500px;
  left: 50%;
  top: 0;
  transform: translateX(-50%);
  z-index: 1;
}

.search-row {
  width: 100%;
}

.search-centered {
  display: flex;
  flex-direction: column;
  align-items: center;
}


.search-label {
  margin-bottom: 10px;
}

.search-input {
  width: 100%; /* Increase the width */
  padding: 12px 20px;
  margin: 8px 0;
  box-sizing: border-box;
  border: 2px solid #168FF0;
  border-radius: 12px; /* Increase border radius */
  font-size: 16px;
  outline: none;
}

.content-container {
  display: flex;
  flex-direction: row;
  justify-content: center; /* Change to center */
  margin-top: 200px; /* Adjust this value to position the content-container below the search container */
}

main {
  position: relative;
  margin: 20px;
  flex-grow: 1;
}
footer {
  background-color: #f8f8f8;
  padding: 20px;
  border-top: 1px solid #e5e5e5;
}

.footer-bottom {
  text-align: center;
  font-size: 14px;
  color: #aaa;
}
.route-active{
  text-decoration: underline;
}
.tabs {
  display: flex;
  justify-content: center;
  margin-bottom: 20px;
  margin-top: 20px;
}
.tablinks {
  background-color: white;
  color: #168FF0;
  border: none;
  outline: none;
  cursor: pointer;
  padding: 16px 24px; /* increase padding */
  margin: 0 10px;
  border-radius: 4px 4px 0 0;
  font-size: 16px; /* increase font size */
  margin-bottom: -10px;
}

.tablinks:hover, .active {
  background-color: #168FF0;
  color: white;
}

</style>