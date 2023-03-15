<template>
  <body>
  <header>
    <div class="logo">
      <h1>Juna Jobs</h1>
    </div>
    <nav>
      <ul>
        <li><a href="#">My Profile</a></li>
        <router-link :to="{ name: 'vacancies'}">Vacancies</router-link>
        <li><a href="#">Analytics</a></li>
        <li><a @click="this.logout()" href="#">Logout</a></li>
      </ul>
    </nav>
  </header>
  <main v-if="job">
    <div class="vacancy-info">
      <div>
        <h2>{{ job.name }}</h2>
        <div class="employer">{{ job.employer.companyName}}, {{job.employer.userDetails.firstName}} {{job.employer.userDetails.lastName}}</div>
      </div>
      <div class="salary">
        ${{ job.salaryFrom }} - ${{ job.salaryTo }}
      </div>
    </div>
    <div class="vacancy-description">
      <h3>Description</h3>
      <p>{{ job.description }}</p>
    </div>
    <div class="vacancy-skills">
      <h3>Requirements</h3>
      <ul>
        <li v-for="skill in job.skills" :key="skill">{{ skill.name }}</li>
        <li>Experience: {{job.grade}}</li>
        <li>English: {{job.englishLevel}}</li>
      </ul>
    </div>
    <div class="vacancy-other-info">
      <h3>Other Info</h3>
      <ul>
        <li><strong>Employment Type:</strong> {{ job.employmentType }}</li>
        <li><strong>Country:</strong> {{ job.country }}</li>
        <li><strong>Date Posted:</strong> {{ job.datePosted }}</li>
        <li><strong>Category:</strong> {{ job.category }}</li>
      </ul>
    </div>
    <a href="#" class="apply-button">Apply</a>
    <a href="#" class="save-button">Save</a>
  </main>
  <main v-else>
    <p>Loading...</p>
  </main>
  <footer>
    <div class="footer-bottom">
      <p>&copy; 2023 Juna Jobs</p>
    </div>
  </footer>
  </body>
</template>
<script>
import axios from "axios";
import authMixin from "@/components/authMixin.js";
import roleMixin from "@/components/roleMixin.js";
import roles from "@/roles";
import { logout } from '@/utils/auth';
import silentLoginMixin from "@/components/silentLoginMixin";

export default {
  name: "Vacancy",
  mixins: [authMixin, roleMixin],
  requiredRole: roles.CANDIDATE,
  data() {
    return {
      job: null,
    };
  },
  methods: {
     async logout() {
      logout()
       this.$router.push('/');
    },
    async fetchVacancyInfo() {
      try {
        const id = this.$route.params.id;
        const response = await axios.get(`http://localhost:8085/vacancies/${id}`, {
          headers: { Authorization: localStorage.getItem("token") },
        })
            .catch(error => {
              silentLoginMixin.methods.silentLogin(error)
            });
        this.job = response.data;
        this.job.datePosted = new Date(this.job.datePosted).toLocaleDateString();
        console.log(this.job);
      } catch (error) {
        console.log(error);
      }
    },
  },
  async mounted() {
    await this.fetchVacancyInfo();
  },
};
</script>


<style scoped>
/* Body Styles */
body {
  background-color: white;
  font-family: Arial, sans-serif;
  color: #168FF0; /* changed text color to #168FF0 */
}

h3 {
  color: black;
}

/* Header Styles */
header {
  background-color: #168FF0;
  color: white;
  text-align: center;
  padding: 20px;
  display: flex;
  justify-content: space-between;
  align-items: center;
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
  /* Apply button styles */
  border: none;
  cursor: pointer;
}

a:hover {
  background-color: #0E6CB3;
}

/* Save button styles */
.save-button {
  border: 1px solid #168FF0;
}

/* Main Styles */
main {
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
}

.vacancy-info {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.vacancy-info h2 {
  margin: 0;
}

.employer {
  font-size: 16px;
  color: #CCC;
}

.salary {
  color: #42b983;
  font-size: 24px;
  font-weight: bold;
}

.vacancy-description p {
  color: black;
  line-height: 1.5;
  margin: 0;
}

.vacancy-skills ul {
  list-style: none;
  padding-left: 0;
}

.vacancy-skills li {
  display: inline-block;
  padding: 5px 10px;
  margin-right: 10px;
  margin-bottom: 10px;
  border: 1px solid #168FF0;
  border-radius: 5px;
}


.vacancy-other-info {
  margin-bottom: 20px;
}

.vacancy-other-info ul {
  list-style: none;
  padding-left: 0;
  color: black;
}

.vacancy-other-info li {
  margin-bottom: 10px;
}

.vacancy-other-info li strong {
  font-weight: bold;
  margin-right: 10px;
}

.apply-button,
.save-button {
  display: inline-block;
  margin-right: 10px;
  padding: 10px 20px;
  border-radius: 5px;
  background-color: #FFF;
  color: #168FF0;
  text-decoration: none;
}

.apply-button:hover,
.save-button:hover {
  background-color: #168FF0;
  color: white;
}

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

ul {
  list-style-type: none;
  padding: 0;
  margin: 0;
}
li {
  display: inline-block;
  margin-right: 20px;
}
footer {
  background-color: #f8f8f8;
  padding: 20px;
  margin-top: 50px;
  border-top: 1px solid #e5e5e5;
}

.footer-bottom {
  text-align: center;
  font-size: 14px;
  color: #aaa;
}
</style>