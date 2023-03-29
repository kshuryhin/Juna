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
        <router-link :to="{ name: 'candidateProfile'}">My Profile</router-link>
        <router-link :to="{ name: 'vacancies'}">Vacancies</router-link>
        <li><a href="#">Analytics</a></li>
        <li><a @click="this.logout()" href="#">Logout</a></li>
      </ul>
    </nav>
  </header>


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
    <section id="filters">
      <h2>Filters</h2>
      <form @submit.prevent="applyFilters">
        <label for="category">Category:</label>
        <select id="category" name="Category" v-model="filters.selectedCategory">
          <option value="">All</option>
          <option value="JS">JS</option>
          <option value="HTML">HTML</option>
          <option value="PHP">PHP</option>
          <option value="RUBY">RUBY</option>
          <option value="PYTHON">PYTHON</option>
          <option value="JAVA">JAVA</option>
          <option value="NET">.NET</option>
          <option value="SCALA">SCALA</option>
          <option value="C">C</option>
          <option value="MOBILE">MOBILE</option>
          <option value="TESTING">TESTING</option>
          <option value="DEVOPS">DEVOPS</option>
          <option value="ADMIN">ADMIN</option>
          <option value="DESIGN">DESIGN</option>
          <option value="PM">PM</option>
          <option value="GAME">GAME</option>
          <option value="OTHER">OTHER</option>
        </select>
        <label for="country">Country:</label>
        <select id="country" name="Country" v-model="filters.selectedCountry">
          <option value="">All</option>
          <option value="Ukraine">Ukraine</option>
          <option value="Germany">Germany</option>
          <option value="USA">USA</option>
          <option value="England">England</option>
        </select>
        <label for="salaryFrom">Salary From:</label>
        <select id="salaryFrom" name="SalaryFrom" v-model="filters.selectedSalaryFrom">
          <option value="">All</option>
          <option value="500">500</option>
          <option value="1000">1000</option>
          <option value="1500">1500</option>
          <option value="2000">2000</option>
          <option value="custom">Custom</option>
        </select>
        <input v-if="filters.selectedSalaryFrom === 'custom'" type="number" v-model="filters.selectedCustomSalaryFrom"/>
        <label for="grade">Grade:</label>
        <select id="grade" name="grade" v-model="filters.selectedGrade">
          <option value="">All</option>
          <option value="TRAINEE">TRAINEE</option>
          <option value="JUNIOR">JUNIOR</option>
          <option value="MIDDLE">MIDDLE</option>
          <option value="SENIOR">SENIOR</option>
        </select>
        <label for="employmentType">Employment Type:</label>
        <select id="employmentType" name="employmentType" v-model="filters.selectedEmploymentType">
          <option value="">All</option>
          <option value="REMOTE">Remote</option>
          <option value="OFFICE">Office</option>
        </select>
        <label for="englishLevel">English Level:</label>
        <select id="englishLevel" name="englishLevel" v-model="filters.selectedEnglishLevel">
          <option value="">All</option>
          <option value="NO_ENGLISH">No English</option>
          <option value="BEGINNER">Beginner</option>
          <option value="PRE_INTERMEDIATE">Pre Intermediate</option>
          <option value="INTERMEDIATE">Intermediate</option>
          <option value="UPPER_INTERMEDIATE">Upper Intermediate</option>
          <option value="ADVANCED">Advanced</option>
        </select>
        <button type="submit">Apply Filters</button>
      </form>
    </section>
    <section id="jobs">
      <h2>Available Jobs</h2>
      <ul id="job-listings">
        <li class="job" v-for="job in jobs" :key="job.id">
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
  </body>
  </html>
</template>

<script>
import axios from 'axios';
import roles from '@/roles';
import authMixin from '@/components/authMixin.js';
import roleMixin from '@/components/roleMixin.js';

import {logout} from '@/utils/auth';

export default {
  mixins: [authMixin, roleMixin],
  requiredRole: roles.CANDIDATE,
  data() {
    return {
      filters: {
        selectedCategory: '',
        selectedCountry: '',
        selectedSalaryFrom: '',
        selectedCustomSalaryFrom: '',
        selectedGrade: '',
        selectedEmploymentType: '',
        selectedEnglishLevel: '',
        searchTerm: '',
      },
      jobs: [],
      allJobs: [],
    };
  },
  methods: {
    async fetchJobs() {
      try {
        const response = await axios.get('http://localhost:8085/vacancies', {
          headers: {
            Authorization: localStorage.getItem('token'),
          },
        });

        this.allJobs = response.data;
        this.jobs = this.allJobs;
      } catch (error) {
        console.error(error);
      }
    },
    searchJobs() {
      const searchTerm = this.searchTerm.toLowerCase();
      this.jobs = this.allJobs.filter((job) =>
          job.name.toLowerCase().includes(searchTerm)
      );
    },
    async applyFilters() {
      try {
        const { selectedCountry, selectedEnglishLevel, selectedEmploymentType, selectedGrade, selectedCategory, selectedSalaryFrom, selectedCustomSalaryFrom } = this.filters;
        let params = {};

        if (selectedCountry) params.country = selectedCountry;
        if (selectedEnglishLevel) params.englishLevel = selectedEnglishLevel;
        if (selectedEmploymentType) params.employmentType = selectedEmploymentType;
        if (selectedGrade) params.grade = selectedGrade;
        if (selectedCategory) params.category = selectedCategory;

        if (selectedSalaryFrom === 'custom' && selectedCustomSalaryFrom !== '') {
          this.filters.selectedSalaryFrom = selectedCustomSalaryFrom;
        }

        if (selectedSalaryFrom) params.salaryFrom = selectedSalaryFrom;

        const response = await axios.get('http://localhost:8085/vacancies', {
          headers: {
            Authorization: localStorage.getItem('token'),
          },
          params,
        });

        this.jobs = response.data;
      } catch (error) {
        console.error(error);
      }
    },
    async logout() {
      logout();
      this.$router.push('/');
    },
  },
  mounted() {
    this.applyFilters();
    this.fetchJobs();
  },
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
  justify-content: space-between;
  margin-top: 200px; /* Adjust this value to position the content-container below the search container */
}

main {
  position: relative;
  margin: 20px;
}


</style>
