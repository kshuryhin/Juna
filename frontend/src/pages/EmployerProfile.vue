<template>
  <html>
  <head>
    <meta charset="utf-8">
    <title>Employer Profile</title>
  </head>
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
  <div class="container">
    <div class="tabs">
      <button class="tablinks active" @click="navigateToPersonalInfo" id="defaultOpen">Personal Info</button>
      <button class="tablinks" @click="navigateToChangePassword">Change Password</button>
      <button class="tablinks" @click="navigateToSavedCandidates">Saved Candidates</button>
    </div>

    <div id="personal-info" class="tabcontent">
      <h2>Personal Info</h2>

      <div class="personal-info">
        <div class="photo-container">
          <img :src="imageUrl" alt="Your Name" class="image-preview">
          <label for="photo" class="file-label">Upload Photo</label>
          <input type="file" id="photo" name="photo" accept="image/*" @change="onFileSelected" class="file-input">
        </div>
        <div class="form-group">
          <label class="label-bold" for="firstName">First Name</label>
          <input class="form-control" type="text" id="firstName" name="firstName" v-model="firstName" required>
        </div>

        <div class="form-group">
          <label class="label-bold" for="lastName">Last Name</label>
          <input type="text" id="lastName" name="lastName" v-model="lastName" required>
        </div>

        <div class="form-group">
          <label class="label-bold" for="email">Email</label>
          <input type="email" id="email" name="email" v-model="email" required readonly>
        </div>

        <div class="form-group">
          <label class="label-bold" for="companyName">Company Name</label>
          <input type="companyName" id="companyName" name="companyName" v-model="companyName" required>
        </div>

        <div class="form-group">
          <label class="label-bold" for="companyWebSite">Company Website</label>
          <input type="companyWebSite" id="companyWebSite" name="companyWebSite" v-model="companyWebSite">
        </div>

        <div class="form-group">
          <label class="label-bold" for="country">Country</label>
          <input type="text" id="country" name="country" v-model="country">
        </div>


        <div class="form-group">
          <label class="label-bold" for="workExperience">Company Description</label>
          <textarea id="workExperience" name="workExperience" v-model="companyDescription"></textarea>
        </div>

        <form @submit.prevent="this.updateProfile()">
          <button type="submit" class="update-btn" :disabled="isUpdating">Save</button>
        </form>
      </div>
    </div>
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
import axios from "axios";
// import vSelect from 'vue-select';
import authMixin from "@/components/authMixin";
import roleMixin from "@/components/roleMixin";
import roles from "@/roles";
import {logout} from "@/utils/auth";
import Candidates from "@/pages/Candidates.vue";
import silentLoginMixin from "@/components/silentLoginMixin";

export default {
  name: "EmployerProfile",
  mixins: [authMixin, roleMixin, silentLoginMixin],
  requiredRole: roles.EMPLOYER,

  data() {
    return {
      id: 0,
      firstName: "",
      lastName: "",
      email:"",
      companyName: "",
      companyDescription: "",
      country: "",
      companyWebSite: "",
      vacancies: [],
      isUpdating: false,
      imageName: "default.png",
      imageUrl: require(`../assets/uploads/employers/default.png`),

    };
  },
  methods: {
    navigateToChangePassword() {
      this.$router.push('/change/employer');
    },
    navigateToPersonalInfo() {
      this.$router.push({'name':'employerProfile'});
    },
    navigateToSavedCandidates() {
     //TBD
    },

    async logout() {
      logout()
      this.$router.push('/');
    },
    onFileSelected(event) {
      const file = event.target.files[0]
      const formData = new FormData()
      formData.append('file', file)
      axios.post(`http://localhost:8085/employers/upload`, formData,  {
        headers: { Authorization: localStorage.getItem("token") },
      })
          .then(response => {
            this.imageUrl = require(`../assets/uploads/employers/${response.data}`);
            this.imageName = response.data
          })
          .catch(error => {
            console.log(error)
          })
    },

    updateProfile() {
      // Disable the "Update" button while the request is being sent
      this.isUpdating = true;
      // Prepare the request body
      const requestBody = {
        userDetails: {
          email: this.email,
          firstName: this.firstName,
          lastName: this.lastName
        },
        companyName: this.companyName,
        country: this.country,
        companyDescription: this.companyDescription,
        companyWebSite: this.companyWebSite,
        companyLogoUrl: this.imageName,
        vacancies: this.vacancies
      };

      axios.put(`http://localhost:8085/employers/${this.id}`, requestBody, {
        headers: { Authorization: localStorage.getItem("token") },
      })
          .then(response => {
            // Handle successful response
            console.log(response.data);
          })
          .catch(error => {
            // Handle error
            console.log(error.response.data);
          })
          .finally(() => {
            // Re-enable the "Update" button after the request is complete
            this.isUpdating = false;
          });
    },
  },

  mounted() {
    axios.get(`http://localhost:8085/employers/email`, {
      headers: { Authorization: localStorage.getItem("token") },
    }).then(response => {
      this.id = response.data.id;
      this.firstName = response.data.userDetails.firstName;
      this.lastName = response.data.userDetails.lastName;
      this.email = response.data.userDetails.email;
      this.companyName = response.data.companyName;
      this.companyDescription = response.data.companyDescription;
      this.country = response.data.country;
      this.companyWebSite = response.data.companyWebSite
      this.vacancies = response.data.vacancies;
      this.imageName = response.data.companyLogoUrl===null?this.imageName:response.data.companyLogoUrl;
      this.imageUrl = require(`../assets/uploads/employers/${this.imageName}`)
      this.vacancies = response.data.vacancies;
    }).catch(error => {
      console.log(error);
    });
  }
};

</script>
<style scoped>
body {
  background-color: white;
  font-family: Arial, sans-serif;
  color: #168FF0;
}

.container {
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
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

.router-link-active {
  text-decoration: underline;
}

.header h1 {
  margin: 0;
}

.tabs {
  display: flex;
  justify-content: center;
  margin-bottom: 20px;
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

.tabcontent {
  display: none;
  padding: 20px;
  border-top: none;
}

#personal-info {
  display: block;
}

.form-group {
  display: flex;
  align-items: center;
  margin-bottom: 20px;
}

.label-bold {
  font-weight: bold;
}

.form-group label {
  flex-basis: 30%;
  margin-right: 1em;
  font-size: 16px;
  color: #555;
}

.form-group input,
.form-group textarea {
  flex-basis: 70%;
  padding: 10px;
  border-radius: 5px;
}

.form-group input:focus,
.form-group textarea:focus,
.form-group input:not(:placeholder-shown),
.form-group textarea:not(:placeholder-shown) {
  transition: all 0.5s ease-in-out;
}


.form-group input:focus,
.form-group textarea:focus {
  outline: none;
  box-shadow: 0 0 3px #bbb;
}

.form-group input:hover,
.form-group textarea:hover {
  background-color: #e0e0e0;
}

.form-group textarea {
  flex-basis: 70%;
  padding: 10px;
  border-radius: 5px;
  height: 200px; /* adjust this value as needed */
}

.form-group select {
  flex-basis: 70%;
  padding: 10px;
  border-radius: 5px;
  border: 1px solid #ccc;
}

.form-group select:focus {
  outline: none;
  box-shadow: 0 0 3px #bbb;
}

.form-group select:hover {
  background-color: #e0e0e0;
}

/* Hide default select arrow */
select::-ms-expand {
  display: none;
}

select::-webkit-select {
  -webkit-appearance: none;
  appearance: none;
}


.personal-info button[type=submit] {
  background-color: #168FF0;
  color: white;
  padding: 10px 20px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.personal-info button[type=submit]:hover {
  background-color: #0D5CA5;
}

.personal-info img {
  display: block;
  width: 150px;
  height: 150px;
  border-radius: 50%;
  margin: 0 auto 20px;
}

.skills li {
  margin-bottom: 5px;
  display: inline-block;
  padding: 5px 10px;
  border: 1px solid #168FF0;
  border-radius: 5px;
  border-radius: 3px;
  color: #168FF0;
  font-weight: bold;
  margin-right: 10px;
}

#photo {
  display: none;
}


.file-label:hover {
  background-color: #0E6CB3;
}

.photo-container {
  text-align: center;
  margin-bottom: 20px;
}

.image-preview {
  display: inline-block;
  width: 150px;
  height: 150px;
  object-fit: cover;
  border-radius: 50%;
  margin-bottom: 20px;
  border: 3px solid #168FF0;
}

.file-label {
  display: inline-block;
  background-color: #168FF0;
  color: white;
  padding: 10px 20px;
  border-radius: 5px;
  font-weight: bold;
  cursor: pointer;
  margin-top: 10px;
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