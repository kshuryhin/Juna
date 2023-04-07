<template>
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
  <main v-if="job">
    <div class="candidate-info">
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
        <li><strong>Date Posted:</strong> {{ displayedDate }}</li>
        <li><strong>Category:</strong> {{ job.category }}</li>
      </ul>
    </div>
    <button @click="applyVacancy" :disabled="isCandidateInJob" :class="{ 'apply-button': true, 'disabled-button': isCandidateInJob }">Apply</button>
    <button @click="saveVacancy" :class="{ 'save-button': !isSaverInJob, 'unsave-button': isSaverInJob, 'toggled-button': isSaverInJob }">{{ isSaverInJob ? 'Unsave' : 'Save' }}</button>  </main>
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
  mixins: [authMixin, roleMixin, silentLoginMixin],
  requiredRole: roles.CANDIDATE,
  data() {
    return {
      displayedDate:{},
      candidate:{},
      job: null,
    };
  },
  computed: {
    isSaverInJob() {
      return this.job.savers.some(c => c.id === this.candidate.id);
    },
    isCandidateInJob() {
      return this.job.candidates.some(c => c.id === this.candidate.id);
    },
  },
  created() {
    this.getCandidateByEmail()
  },
  methods: {
     getCandidateByEmail() {
      try {
         axios.get('http://localhost:8085/candidates/email', {
          headers: {
            Authorization : localStorage.getItem('token')
          }
        }).then(response => {
          this.candidate = response.data;
          console.log(this.candidate)
        })
      }catch (error){
        console.error(error)
      }
    },
     async logout() {
      logout()
       this.$router.push('/');
    },
    async saveVacancy(){
      try {
        if (this.isSaverInJob) {
          this.job.savers = this.job.savers.filter(c => c.id !== this.candidate.id);
        } else {
          this.job.savers.push(this.candidate);
        }
        await axios.put(`http://localhost:8085/vacancies/${this.job.id}`, this.job, {
          headers: {
            Authorization: localStorage.getItem('token')
          }
        });
      } catch (error) {
        console.error(error);
      }
    },
    async applyVacancy(){
      try{
        this.job.candidates.push(this.candidate)
        await axios.put(`http://localhost:8085/vacancies/${this.job.id}`, this.job, {
          headers: {
            Authorization: localStorage.getItem('token')
          }
        })
      }catch (error) {
        console.error(error)
      }
    },
    async fetchVacancyInfo() {
      try {
        const id = this.$route.params.id;
        const response = await axios.get(`http://localhost:8085/vacancies/${id}`, {
          headers: { Authorization: localStorage.getItem("token") },
        });
        this.job = response.data;
        this.imageName = response.data.photoLink===null?this.imageName:response.data.photoLink;
        this.imageUrl = require(`../assets/uploads/candidates/${this.imageName}`)
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

.toggled-button {
  background-color: #168FF0;
  color: white;
}

.apply-button.disabled-button {
  background-color: #ccc;
  color: white;
  cursor: not-allowed;
  pointer-events: none;
}
/* Body Styles */
body {
  background-color: white;
  font-family: Arial, sans-serif;
  min-height: 100vh;
  color: #168FF0; /* changed text color to #168FF0 */
  display: flex;
  flex-direction: column;
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

.unsave-button {
  border: 1px solid red;
  margin-right: 10px;
  padding: 10px 20px;
  border-radius: 5px;
  background-color: #FFF;
  color: red;
  font-weight: bold;
  text-decoration: none;
}


/* Main Styles */
main {
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
  flex: 1;
}

.candidate-info {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.candidate-info h2 {
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
  border: 1px solid #168FF0;
  margin-right: 10px;
  padding: 10px 20px;
  border-radius: 5px;
  background-color: #FFF;
  color: #168FF0;
  font-weight: bold;
  text-decoration: none;
}

.apply-button:hover,
.save-button:hover {
  background-color: #168FF0;
  color: white;
}

.unsave-button:hover {
  background-color: red;
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