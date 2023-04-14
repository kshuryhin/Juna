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
  <main v-if="employer">
    <div class="photo-container">
      <img :src="imageUrl" alt="Your Name" class="image-preview">
    </div>
    <div class="employer-info">
      <div>
        <h2>{{ employer.companyName }}</h2>
        <div class="employer">{{employer.userDetails.firstName}} {{employer.userDetails.lastName}}</div>
      </div>
      <div class="country">
        {{ employer.country }}
      </div>
    </div>
    <div class="description">
      <h3>Company Description</h3>
      <p>{{ employer.companyDescription }}</p>
    </div>
    <br>
    <div class="employer-contact-info">
      <h3>Contact Info</h3>
      <ul>
        <li>
          <strong>Company Website: </strong>
          <a class="contact-link" :href="employer.companyWebSite" target="_blank">{{ employer.companyWebSite }}</a>
        </li>
      </ul>
    </div>
    <button @click="backToVacancy" class="back-button">Back to vacancy</button></main>
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
  name: "Employer",
  mixins: [authMixin, roleMixin, silentLoginMixin],
  requiredRole: roles.CANDIDATE,
  data() {
    return {
      displayedDate:{},
      employer:{},
      imageName: "default.png",
      imageUrl: require(`../assets/uploads/employers/default.png`),
    };
  },
  methods: {
    async logout() {
      logout()
      this.$router.push('/');
    },
    async backToVacancy(){
      const vacancyId = this.$route.params.vacancyId;
      this.$router.push(`/vacancy/${vacancyId}`)
    },
    async fetchEmployerInfo() {
      try {
        const id = this.$route.params.id;
        await axios.get(`http://localhost:8085/employers/id/${id}`, {
          headers: { Authorization: localStorage.getItem("token") },
        }).then(response => {
          this.employer = response.data;
          this.imageName = response.data.companyLogoUrl===null?this.imageName:response.data.companyLogoUrl;
          this.imageUrl = require(`../assets/uploads/employers/${this.imageName}`)
        })
      } catch (error) {
        console.log(error);
      }
    },
  },
  created() {
    this.fetchEmployerInfo();
  },
};
</script>


<style scoped>

.employer-contact-info {
  margin-bottom: 30px;
}

.employer-contact-info ul {
  list-style: none;
  padding-left: 0;
  color: black;
}

.employer-contact-info li {
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

/* Main Styles */
main {
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
}

.employer-info {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.employer-info h2 {
  margin: 0;
}

.employer {
  font-size: 16px;
  color: #676666;
}

.country {
  color: #168FF0;
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


.employer-other-info {
  margin-bottom: 20px;
}

.employer-other-info ul {
  list-style: none;
  padding-left: 0;
  color: black;
}

.employer-other-info li {
  margin-bottom: 10px;
}

.employer-other-info li strong {
  font-weight: bold;
  margin-right: 10px;
}

.back-button {
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

.back-button:hover {
  background-color: #168FF0;
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