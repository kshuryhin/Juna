<template>
  <!DOCTYPE html>
  <html>
  <head>
    <title>Juna Jobs</title>
  </head>
  <body>
  <header>
    <div class="logo">
      <h1>Juna Jobs</h1>
    </div>
    <nav>
      <ul>
        <router-link class="route-active" :to="{ name: 'employerProfile'}">My Profile</router-link>
        <router-link :to="{ name: 'candidates'}">Candidates</router-link>
        <router-link :to="{ name: 'analytics'}">Analytics</router-link>
        <li><a @click="this.logout()" href="#">Logout</a></li>
      </ul>
    </nav>
  </header>

  <div class="tabs">
    <button class="tablinks" @click="navigateToEditVacancy">Edit Vacancy</button>
    <button class="tablinks active" @click="navigateToApplications" id="defaultOpen">Applications</button>
  </div>
  <div class="search-container">
    <section id="search" class="search-centered">
      <h2 class="search-label">Search</h2>
      <div class="search-row">
        <input id="search" type="text" v-model="searchTerm" placeholder="Search by candidate position" class="search-input" />
        <button @click="searchCandidates" class="search-button">Search</button>
      </div>
    </section>
  </div>
  <main v-if="filteredCandidates.length > 0">
    <div class="content-container">
      <section id="candidates">
        <h2>Available Candidates</h2>
        <ul id="candidate-listings">
          <li class="candidate" v-for="candidate in filteredCandidates" :key="candidate.id">
            <h3 @click="navigateToApplication(candidate.id)" class="clickable">{{ candidate.position }}</h3>
            <div class="photo-container">
              <img :src="candidate.photoLink ? require(`../assets/uploads/candidates/${candidate.photoLink}`) : require(`../assets/uploads/candidates/default.png`)" alt="Your Name" class="image-preview">
            </div>
            <p class="grade">Grade: {{ candidate.grade }}</p>
            <p class="salaryExpectations">Salary Expectations: {{ candidate.salaryExpectations }}$</p>
            <p class="description">{{ candidate.petProjectsDescription }}</p>
          </li>
        </ul>
      </section>
    </div>
  </main>
  <div class="no-candidates" v-else>
    <h2>No saved candidates</h2>
  </div>
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
  requiredRole: roles.EMPLOYER,
  data() {
    return {
      searchTerm: '',
      filteredCandidates:[],
      candidates: [],
      vacancy: {}
    };
  },
  methods: {
    navigateToEditVacancy(){
      this.$router.push({name: 'editVacancy',
        params: { id: this.vacancy.id }})
    },
    navigateToApplications(){
      this.$router.push({name: 'applications', params: {id: this.vacancy.id}})
    },
    navigateToApplication(id){
      this.$router.push({ name: 'application', params: { id: id, vacancyId: this.vacancy.id } });
    },
    searchCandidates() {
      if (this.searchTerm) {
        this.filteredCandidates = this.candidates.filter(candidate =>
            candidate.position.toLowerCase().includes(this.searchTerm.toLowerCase())
        );
      } else {
        this.filteredCandidates = this.candidates;
      }
    },
    async logout() {
      logout();
      this.$router.push('/');
    },
  },
  mounted() {
    const id = this.$route.params.id;
    axios.get(`http://localhost:8085/vacancies/${id}`, {
      headers: { Authorization: localStorage.getItem("token") },
    }).then(response => {
      this.vacancy = response.data
      this.candidates = response.data.candidates;
      this.filteredCandidates = this.candidates;
    }).catch(error => {
      console.log(error);
    });
  },
};
</script>


<style scoped>

.photo-container {
  margin-right: 20px;
}

.image-preview {
  width: 100px; /* Adjust the width as desired */
  height: 100px; /* Adjust the height as desired */
  object-fit: cover; /* Maintain aspect ratio and fill the container */
  border-radius: 50%; /* Add a border-radius to round the corners */
}

body {
  background-color: white;
  font-family: Arial, sans-serif;
  min-height: 100vh;
  display: flex;
  flex-direction: column;

}

header {
  background-color: #168FF0;
  color: white;
  text-align: center;
  padding: 20px;
  display: flex;
  justify-content: space-between;
  align-items: center;
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

.clickable {
  cursor: pointer;
}

main {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
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

/* Set filters form style */
form {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
}

label {
  margin-bottom: 10px;
}

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

#candidates {
  width: 65%;
}

ul {
  list-style-type: none;
  padding: 0;
}

.candidate {
  margin-bottom: 20px;
  border: 2px solid #168FF0;
  border-radius: 10px;
  padding: 10px;
}

.candidate .description {
  display: -webkit-box;
  -webkit-line-clamp: 3;
  -webkit-box-orient: vertical;
  overflow: hidden;
  text-overflow: ellipsis;
}


h3 {
  color: #168FF0;
}

.salaryExpectations {
  font-style: italic;
}


.grade {
  font-style: italic;
}

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

.logo {
  float: left;
}

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

.search-container {
  margin-top: 270px;
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
  justify-content: center;
  margin-top: 200px; /* Adjust this value to position the content-container below the search container */
}

.no-candidates {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-grow: 1;
  font-size: 24px;
  font-weight: bold;
  color: #999;
}

main {
  position: relative;
  margin: 20px;
}

.route-active {
  text-decoration: underline;
}

</style>