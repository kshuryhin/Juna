<template>
  <html>
  <head>
    <meta charset="utf-8">
    <title>Candidate Profile</title>
    <link rel="stylesheet" href="style.css">
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
        <router-link :to="{ name: 'analytics'}">Analytics</router-link>
        <li><a @click="this.logout()" href="#">Logout</a></li>
      </ul>
    </nav>
  </header>
  <div class="container">
    <div class="tabs">
      <button class="tablinks active" @click="navigateToPersonalInfo" id="defaultOpen">Personal Info</button>
      <button class="tablinks" @click="navigateToChangePassword">Change Password</button>
      <button class="tablinks" @click="navigateToSavedVacancies">Saved Vacancies</button>
      <button class="tablinks" @click="navigateToAppliedVacancies">Applied Vacancies</button>
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
          <label class="label-bold" for="position">Position</label>
          <input type="position" id="position" name="position" v-model="position" required>
        </div>

        <div class="form-group">
          <label class="label-bold" for="phone">Phone Number</label>
          <input type="tel" id="phone" name="phone" v-model="phone">
        </div>

        <div class="form-group">
          <label class="label-bold" for="city">City</label>
          <input type="text" id="city" name="city" v-model="city">
        </div>

        <div class="form-group">
          <label class="label-bold" for="country">Country</label>
          <input type="text" id="country" name="country" v-model="country">
        </div>

        <div class="form-group">
          <label class="label-bold" for="linkedinLink">Linked In</label>
          <input type="text" id="linkedinLink" name="linkedinLink" v-model="linkedinLink">
        </div>

        <div class="form-group">
          <label class="label-bold" for="telegramLink">Telegram</label>
          <input type="text" id="telegramLink" name="linkedinLink" v-model="telegramLink">
        </div>

        <div class="form-group">
          <label class="label-bold" for="githubLink">Git Hub</label>
          <input type="text" id="githubLink" name="githubLink" v-model="githubLink">
        </div>


        <div class="form-group">
          <label class="label-bold" for="category">Category</label>
          <select id="category" name="category" v-model="category">
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
        </div>


        <div class="form-group">
          <label class="label-bold" for="employmentType">Employment Type</label>
          <select id="employmentType" name="employmentType" v-model="employmentType">
            <option value="">Please select</option>
            <option value="REMOTE">REMOTE</option>
            <option value="OFFICE">OFFICE</option>
            <option value="ALL">ALL</option>
          </select>
        </div>

        <div class="form-group">
          <label class="label-bold" for="englishLevel">English Level</label>
          <select id="englishLevel" name="englishLevel" v-model="englishLevel">
            <option value="NO_ENGLISH">NO_ENGLISH</option>
            <option value="BEGINNER">BEGINNER</option>
            <option value="PRE_INTERMEDIATE">PRE_INTERMEDIATE</option>
            <option value="INTERMEDIATE">INTERMEDIATE</option>
            <option value="UPPER_INTERMEDIATE">UPPER_INTERMEDIATE</option>
            <option value="ADVANCED">ADVANCED</option>
          </select>
        </div>

        <div class="form-group">
          <label class="label-bold" for="isActive">Active</label>
          <div class="switch-container">
            <input type="checkbox" id="isActive" name="isActive" :checked="isActive" @change="toggleIsActive" class="switch-checkbox">
            <label for="isActive" class="switch-label"></label>
          </div>
        </div>


        <div class="form-group">
          <label class="label-bold" for="salaryExpectations">Salary Expectations</label>
          <input type="salaryExpectations" id="salaryExpectations" name="salaryExpectations" v-model="salaryExpectations">
        </div>

        <div class="form-group">
          <label class="label-bold" for="grade">Grade</label>
          <select id="grade" name="grade" v-model="grade">
            <option value="TRAINEE">TRAINEE</option>
            <option value="JUNIOR">JUNIOR</option>
            <option value="MIDDLE">MIDDLE</option>
            <option value="SENIOR">SENIOR</option>
          </select>
        </div>



        <div class="form-group">
          <label class="label-bold" for="workExperience">Work Experience</label>
          <textarea id="workExperience" name="workExperience" v-model="workExperience"></textarea>
        </div>

        <div class="form-group">
          <label class="label-bold" for="petProjectsDescription">Pet Projects</label>
          <textarea id="petProjectsDescription" name="petProjectsDescription" v-model="petProjectsDescription"></textarea>
        </div>

        <div class="skills-container">
          <h2>Skills</h2>
          <div>
            <h3 class="skills-title">Current Skills:</h3>
            <ul class="skills">
              <li v-for="skill in candidateSkills" :key="skill.id">{{ skill.name }}</li>
            </ul>
          </div>
          <div>
            <h3>Update Skills:</h3>
            <div v-if="skills.length === 0">
              <p>Loading skills...</p>
            </div>
            <div v-else>
              <div class="dropdown">
                <button class="btn btn-secondary dropdown-toggle" type="button" id="skillsDropdown" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                  Select Skills
                </button>
                <div class="dropdown-menu" aria-labelledby="skillsDropdown">
                  <label v-for="skill in skills" :key="skill.id" class="dropdown-item">
                    <input type="checkbox" v-model="selectedSkills" :value="skill.name" class="dropdown-checkbox" />
                    {{ skill.name }}
                  </label>
                </div>
              </div>
              <button @click="this.updateSkills()" class="update-skills-btn">Update Skills</button>
            </div>
          </div>
        </div>


        <form @submit.prevent="this.updateProfile()">
          <button type="submit" class="update-btn" :disabled="isUpdating">Save</button>
        </form>
      </div>
    </div>

    <div id="saved-vacancies" class="tabcontent">
      <h2>Saved Vacancies</h2>
      <p>No saved vacancies yet.</p>
    </div>

    <div id="applied-vacancies" class="tabcontent">
      <h2>Applied Vacancies</h2>
      <p>No applied vacancies yet.</p>
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
import silentLoginMixin from "@/components/silentLoginMixin";

export default {
  name: "CandidateProfile",
  mixins: [authMixin, roleMixin, silentLoginMixin],
  requiredRole: roles.CANDIDATE,
  // components: { vSelect },

  data() {
    return {
      id: 0,
      firstName: "",
      lastName: "",
      position:"",
      isActive:false,
      email: "",
      phone: "",
      city: "",
      country: "",
      category: "",
      grade: "",
      employmentType: "",
      englishLevel:"",
      salaryExpectations: "",
      workExperience: "",
      petProjectsDescription:"",
      linkedinLink:"",
      telegramLink:"",
      githubLink:"",
      skills:[],
      selectedSkills:[],
      candidateSkills:[],
      isUpdating: false,
      imageName: "default.png",
      imageUrl: require(`../assets/uploads/candidates/default.png`),

    };
  },
  methods: {
    toggleIsActive: function() {
      this.isActive = !this.isActive;
    },
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
    async logout() {
      logout()
      this.$router.push('/');
    },
    onFileSelected(event) {
      const file = event.target.files[0]
      const formData = new FormData()
      formData.append('file', file)
      axios.post(`http://localhost:8085/candidates/upload`, formData,  {
        headers: { Authorization: localStorage.getItem("token") },
      })
          .then(response => {
            this.imageUrl = require(`../assets/uploads/candidates/${response.data}`);
            this.imageName = response.data
          })
          .catch(error => {
            console.log(error)
          })
    },
    updateSkills() {
      const tempSkills = [];
      // Add selected skills to tempSkills
      this.selectedSkills.forEach(skill => {
        const selectedSkill = this.skills.find(s => s.name === skill);
        tempSkills.push({ id: selectedSkill.id, name: skill });
      });
      // Filter out unselected skills from tempSkills
      this.candidateSkills = tempSkills.filter(skill => {
        return this.selectedSkills.includes(skill.name);
      });
    },
    updateProfile() {
      // Disable the "Update" button while the request is being sent
      this.isUpdating = true;
      console.log(this.candidateSkills)

      // Prepare the request body
      const requestBody = {
        userDetails: {
          email: this.email,
          firstName: this.firstName,
          lastName: this.lastName
        },
        position: this.position,
        isActive: this.isActive,
        salaryExpectations: parseInt(this.salaryExpectations.replace("$", "")),
        grade: this.grade,
        country: this.country,
        city: this.city,
        skills: this.candidateSkills,
        category: this.category,
        englishLevel: this.englishLevel,
        employmentType: this.employmentType,
        workExperience: this.workExperience,
        phoneNumber: this.phone,
        photoLink: this.imageName,
        linkedinLink: this.linkedinLink,
        telegramLink: this.telegramLink,
        githubLink: this.githubLink,
        petProjectsDescription: this.petProjectsDescription
      };

      axios.put(`http://localhost:8085/candidates/${this.id}`, requestBody, {
        headers: { Authorization: localStorage.getItem("token") },
      })
          .then(response => {
            this.updateCandidateAnalytics(response.data)
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
    updateCandidateAnalytics(candidateAnalytics){
      axios.put(`http://localhost:8085/analytics/candidates`, candidateAnalytics, {
        headers: {Authorization: localStorage.getItem('token')}
      })
    },
    fetchSkills() {
      axios.get(`http://localhost:8085/skills`, {
        headers: { Authorization: localStorage.getItem("token") },
      }).then(response => {
        this.skills = response.data
        this.selectedSkills = this.skills.filter(skill => this.candidateSkills.some(candidateSkill => candidateSkill.name === skill.name)).map(skill => skill.name);
        setTimeout(() => {
          const dropdownBtn = document.querySelector('.dropdown-toggle');
          const dropdownMenu = document.querySelector('.dropdown-menu');

          // Add an event listener to the button to toggle the menu visibility
          dropdownBtn.addEventListener('click', () => {
            dropdownMenu.classList.toggle('show');
          });

          document.addEventListener('click', (event) => {
            if (!dropdownBtn.contains(event.target) && !dropdownMenu.contains(event.target)) {
              dropdownMenu.classList.remove('show');
            }
          });
        });
      }).catch(error => {
        console.log(error);
      });
    },
  },

  mounted() {
    axios.get(`http://localhost:8085/candidates/email`, {
      headers: { Authorization: localStorage.getItem("token") },
    }).then(response => {
      this.linkedinLink = response.data.linkedinLink;
      this.telegramLink = response.data.telegramLink;
      this.githubLink = response.data.githubLink;
      this.firstName = response.data.userDetails.firstName;
      this.id = response.data.id;
      this.lastName = response.data.userDetails.lastName;
      this.email = response.data.userDetails.email;
      this.phone = response.data.phoneNumber;
      this.position = response.data.position;
      this.isActive = response.data.isActive;
      this.city = response.data.city;
      this.grade = response.data.grade;
      this.country = response.data.country;
      this.category = response.data.category;
      this.employmentType = response.data.employmentType;
      this.englishLevel = response.data.englishLevel;
      this.salaryExpectations = (response.data.salaryExpectations===null?"0":response.data.salaryExpectations)+"$"
      this.workExperience = response.data.workExperience;
      this.imageName = response.data.photoLink===null?this.imageName:response.data.photoLink;
      this.imageUrl = require(`../assets/uploads/candidates/${this.imageName}`)
      this.petProjectsDescription = response.data.petProjectsDescription
      this.candidateSkills = response.data.skills;
      console.log(this.imageUrl)
      this.fetchSkills();
    }).catch(error => {
      console.log(error);
    });
  }
};

</script>
<style scoped>
.switch-container {
  position: relative;
  display: inline-block;
  width: 57px;
  height: 20px;
}

.switch-label {
  position: absolute;
  cursor: pointer;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: #ccc;
  transition: 0.4s;
  border-radius: 20px;
}

.switch-label:before {
  position: absolute;
  content: "";
  height: 14px;
  width: 14px;
  left: 3px;
  bottom: 3px;
  background-color: white;
  transition: 0.4s;
  border-radius: 50%;
}

.switch-checkbox:checked + .switch-label {
  background-color: #168FF0;
}

.switch-checkbox:checked + .switch-label:before {
  transform: translateX(20px);
}

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

#saved-vacancies {
  display: none;
}

#applied-vacancies {
  display: none;
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

.skills-container {
  margin: 20px 0;
}

.skills-title {
  margin-top: 0;
}
.skills {
  margin: 0;
  padding: 0;
  list-style: none;
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


.dropdown {
  position: relative;
  display: inline-block;
}

.dropdown-menu {
  position: absolute;
  top: 100%;
  left: 0;
  z-index: 1000;
  display: none;
  float: left;
  min-width: 10rem;
  padding: 0.5rem 0;
  margin: 0.125rem 0 0;
  max-height: 100px;
  overflow-y: auto;
  font-size: 1rem;
  color: #212529;
  text-align: left;
  list-style: none;
  background-color: #fff;
  background-clip: padding-box;
  border: 1px solid rgba(0, 0, 0, 0.15);
  border-radius: 0.25rem;
}

.dropdown-menu.show {
  display: block;
}

.dropdown-item {
  display: block;
  width: 100%;
  padding: 0.25rem 1.5rem;
  clear: both;
  font-weight: 400;
  color: #212529;
  text-align: inherit;
  white-space: nowrap;
  background-color: transparent;
  border: 0;
}

.dropdown-item:focus,
.dropdown-item:hover {
  color: #16181b;
  text-decoration: none;
  background-color: #f8f9fa;
}

.dropdown-menu .dropdown-item.disabled,
.dropdown-menu .dropdown-item:disabled {
  color: #6c757d;
  background-color: transparent;
}

.dropdown-menu .dropdown-item.active,
.dropdown-menu .dropdown-item:active {
  color: #fff;
  text-decoration: none;
  background-color: #007bff;
}

.btn-secondary {
  background-color: #fff;
  border: 1px solid #ced4da;
  color: #495057;
  cursor: pointer;
  padding: 0.375rem 0.75rem;
  font-size: 1rem;
  line-height: 1.5;
  border-radius: 0.25rem;
}

.btn-secondary:focus,
.btn-secondary:hover {
  background-color: #e9ecef;
}

.btn-secondary:focus {
  box-shadow: none;
}

.btn-secondary.dropdown-toggle::after {
  display: inline-block;
  margin-left: 0.255em;
  vertical-align: 0.255em;
  content: "";
  border-top: 0.3em solid;
  border-right: 0.3em solid transparent;
  border-bottom: 0;
  border-left: 0.3em solid transparent;
}


.update-skills-btn {
  margin-top: 5px;
  padding: 5px 10px;
  font-size: 14px;
  background-color: #007bff;
  color: #fff;
  border: 1px solid #007bff;
  border-radius: 3px;
  cursor: pointer;
}

.file-input-container {
  position: relative;
  display: inline-block;
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