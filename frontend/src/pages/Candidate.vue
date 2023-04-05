<template>
<body>
<header>
  <div class="logo">
    <h1>Juna Jobs</h1>
  </div>
  <nav>
    <ul>
      <router-link :to="{ name: 'employerProfile'}">My Profile</router-link>
      <router-link :to="{ name: 'candidates'}">Candidates</router-link>
      <li><a href="#">Analytics</a></li>
      <li><a @click="this.logout()" href="#">Logout</a></li>
    </ul>
  </nav>
</header>
<main v-if="candidate">
  <div class="photo-container">
    <img :src="imageUrl" alt="Your Name" class="image-preview">
  </div>
  <div class="candidate-info">
    <div>
      <h2>{{ candidate.position }}</h2>
      <div class="candidate">{{candidate.userDetails.firstName}} {{candidate.userDetails.lastName}}</div>
    </div>
    <div class="salary">
      ${{ candidate.salaryExpectations }}
    </div>
  </div>
  <div class="description">
    <h3>Work experience</h3>
    <p>{{ candidate.workExperience }}</p>
  </div>
  <div class="description">
    <h3>Pet projects</h3>
    <p>{{ candidate.petProjectsDescription }}</p>
  </div>
  <div class="skills">
    <h3>Skills</h3>
    <ul>
      <li v-for="skill in candidate.skills" :key="skill">{{ skill.name }}</li>
    </ul>
  </div>
  <div class="candidate-other-info">
    <h3>Other Info</h3>
    <ul>
      <li><strong>Employment Type:</strong> {{ candidate.employmentType }}</li>
      <li><strong>Country:</strong> {{ candidate.country }}</li>
      <li><strong>City:</strong> {{ candidate.city }}</li>
      <li><strong>Category:</strong> {{ candidate.category }}</li>
      <li><strong>English Level:</strong> {{ candidate.englishLevel }}</li>
    </ul>
  </div>
  <br>
  <div class="candidate-contact-info">
    <h3>Contact Info</h3>
    <ul>
      <li><strong>Email:</strong> {{ candidate.userDetails.email }}</li>
      <li><strong>Phone Number:</strong> {{ candidate.phoneNumber }}</li>
      <li>
        <strong>LinkedIn: </strong>
        <a class="contact-link" :href="candidate.linkedinLink" target="_blank">{{ candidate.linkedinLink }}</a>
      </li>
      <li>
        <strong>Telegram: </strong>
        <a class="contact-link" :href="candidate.telegramLink" target="_blank">{{ candidate.telegramLink }}</a>
      </li>
      <li>
        <strong>GitHub: </strong>
        <a class="contact-link" :href="candidate.githubLink" target="_blank">{{ candidate.githubLink }}</a>
      </li>
    </ul>
  </div>
  <button @click="saveCandidate" :class="{ 'save-button': !isSaverInJob, 'unsave-button': isSaverInJob, 'toggled-button': isSaverInJob }">{{ isSaverInJob ? 'Unsave' : 'Save' }}</button>  </main>
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
import candidate from "./Candidate.vue";
import Candidates from "@/pages/Candidates.vue";

export default {
  name: "Candidate",
  mixins: [authMixin, roleMixin],
  requiredRole: roles.EMPLOYER,
  data() {
    return {
      displayedDate:{},
      candidate:{},
      imageName: "default.png",
      imageUrl: require(`../assets/uploads/candidates/default.png`),
    };
  },
  methods: {
    async logout() {
      logout()
      this.$router.push('/');
    },
    async saveCandidate(){
     //TBD
    },
     async fetchCandidateInfo() {
      try {
        const id = this.$route.params.id;
        await axios.get(`http://localhost:8085/candidates/${id}`, {
          headers: { Authorization: localStorage.getItem("token") },
        }).then(response => {
          this.candidate = response.data;
          this.imageName = response.data.photoLink===null?this.imageName:response.data.photoLink;
          this.imageUrl = require(`../assets/uploads/candidates/${this.imageName}`)
          this.candidate.englishLevel = this.candidate.englishLevel.replace("_", " ")
        })
      } catch (error) {
        console.log(error);
      }
    },
  },
   created() {
     this.fetchCandidateInfo();
  },
};
</script>


<style scoped>

.candidate-contact-info {
  margin-bottom: 30px;
}

.candidate-contact-info ul {
  list-style: none;
  padding-left: 0;
  color: black;
}

.candidate-contact-info li {
  margin-bottom: 5px;
}

.contact-link {
  color: #168FF0;
  text-decoration: none;
}

.contact-link:hover {
  color: white;
  background-color: #168FF0;
  text-decoration: underline;
}
  img {
  display: block;
  width: 150px;
  height: 150px;
  border-radius: 50%;
  margin: 0 auto 20px;
}

.toggled-button {
  background-color: #168FF0;
  color: white;
}
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

header a {
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

.candidate {
  font-size: 16px;
  color: #676666;
}

.salary {
  color: #42b983;
  font-size: 24px;
  font-weight: bold;
}

.description p {
  color: black;
  line-height: 1.5;
  margin: 0;
}

.skills ul {
  list-style: none;
  padding-left: 0;
}

.skills li {
  display: inline-block;
  padding: 5px 10px;
  margin-right: 10px;
  margin-bottom: 10px;
  border: 1px solid #168FF0;
  border-radius: 5px;
}


.candidate-other-info {
  margin-bottom: 20px;
}

.candidate-other-info ul {
  list-style: none;
  padding-left: 0;
  color: black;
}

.candidate-other-info li {
  margin-bottom: 10px;
}

.candidate-other-info li strong {
  font-weight: bold;
  margin-right: 10px;
}

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