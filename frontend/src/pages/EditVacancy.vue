<template>
  <html>
  <head>
    <meta charset="utf-8">
    <title>Edit Vacancy</title>
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
    <button class="tablinks active" @click="navigateToEditVacancy" id="defaultOpen">Edit Vacancy</button>
    <button class="tablinks" @click="navigateToApplications">Applications</button>
  </div>
  <div class="container">
    <div id="create-vacancy" class="tabcontent">
      <h2>Edit Vacancy</h2>

      <div class="create-vacancy">
        <div class="form-group">
          <label class="label-bold" for="name">Name</label>
          <input class="form-control" type="text" id="name" name="name" v-model="vacancy.name" required>
        </div>

        <div class="form-group">
          <label class="label-bold" for="country">Country</label>
          <input type="text" id="country" name="country" v-model="vacancy.country" required>
        </div>

        <div class="form-group">
          <label class="label-bold" for="salaryFrom">Salary From</label>
          <input type="number" id="salaryFrom" name="salaryFrom" v-model="vacancy.salaryFrom" required>
        </div>


        <div class="form-group">
          <label class="label-bold" for="salaryTo">Salary To</label>
          <input type="number" id="salaryTo" name="salaryTo" v-model="vacancy.salaryTo" required>
        </div>


        <div class="form-group">
          <label class="label-bold" for="category">Category</label>
          <select id="category" name="category" v-model="vacancy.category">
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
          <select id="employmentType" name="employmentType" v-model="vacancy.employmentType">
            <option value="">Please select</option>
            <option value="REMOTE">REMOTE</option>
            <option value="OFFICE">OFFICE</option>
            <option value="ALL">ALL</option>
          </select>
        </div>

        <div class="form-group">
          <label class="label-bold" for="englishLevel">English Level</label>
          <select id="englishLevel" name="englishLevel" v-model="vacancy.englishLevel">
            <option value="NO_ENGLISH">NO_ENGLISH</option>
            <option value="BEGINNER">BEGINNER</option>
            <option value="PRE_INTERMEDIATE">PRE_INTERMEDIATE</option>
            <option value="INTERMEDIATE">INTERMEDIATE</option>
            <option value="UPPER_INTERMEDIATE">UPPER_INTERMEDIATE</option>
            <option value="ADVANCED">ADVANCED</option>
          </select>
        </div>

        <div class="form-group">
          <label class="label-bold" for="grade">Grade</label>
          <select id="grade" name="grade" v-model="vacancy.grade">
            <option value="TRAINEE">TRAINEE</option>
            <option value="JUNIOR">JUNIOR</option>
            <option value="MIDDLE">MIDDLE</option>
            <option value="SENIOR">SENIOR</option>
          </select>
        </div>

        <div class="form-group">
          <label class="label-bold" for="description">Description</label>
          <textarea id="description" name="description" v-model="vacancy.description"></textarea>
        </div>

        <div class="skills-container">
          <h2>Skills</h2>
          <div>
            <h3 class="skills-title">Vacancy Skills:</h3>
            <ul class="skills">
              <li v-for="skill in vacancy.skills" :key="skill.id">{{ skill.name }}</li>
            </ul>
          </div>
          <div>
            <h3>Add Skills:</h3>
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
              <button @click="this.updateSkills()" class="update-skills-btn">Add Skills</button>
            </div>
          </div>
        </div>
          <button @click="updateVacancy" type="submit" class="update-btn">Update</button>
          <button @click="deleteVacancy" type="submit" class="delete-btn">Delete</button>
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
import authMixin from "@/components/authMixin";
import roleMixin from "@/components/roleMixin";
import roles from "@/roles";
import {logout} from "@/utils/auth";
import silentLoginMixin from "@/components/silentLoginMixin";

export default {
  name: "EditVacancy",
  mixins: [authMixin, roleMixin, silentLoginMixin],
  requiredRole: roles.EMPLOYER,

  data() {
    return {
      vacancy: {},
      employer: {},
      skills: [],
      selectedSkills:[]
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
    async logout() {
      logout()
      this.$router.push('/');
    },
    validateDescription() {
      const description = this.vacancy.description;
      if (description.length < 2000) {
        alert("Description must contain at least 2000 symbols.");
        return false;
      }
      return true;
    },
    deleteVacancy(){
      const id = this.$route.params.id;
      axios.delete(`http://localhost:8085/vacancies/${id}`, {
        headers: { Authorization: localStorage.getItem("token") },
      }).then(response => {
        alert("Vacancy was deleted successfully!")
        this.$router.push('/myVacancies')
      }).catch(error => {
            console.log(error)
          })
    },
    updateVacancy() {
      if (this.validateDescription()){
        const id = this.$route.params.id;
        axios.put(`http://localhost:8085/vacancies/${id}`, this.vacancy, {
          headers: { Authorization: localStorage.getItem("token") },
        })
            .then(response => {
              const updatedVacancy = response.data
              axios.get(`http://localhost:8085/analytics/vacancies/origin/${id}`, {
                headers: {Authorization: localStorage.getItem('token')}
              }).then(response => this.updateVacancyAnalytics(response.data, updatedVacancy))
              alert("Vacancy was updated successfully!");
            })
            .catch(error => {
              // Handle error
              console.log(error.response.data);
            })
      }
    },
    updateVacancyAnalytics(vacancyAnalytics, updatedVacancy){
      console.log(updatedVacancy)
      vacancyAnalytics.name = updatedVacancy.name
      vacancyAnalytics.country = updatedVacancy.country
      vacancyAnalytics.salaryFrom = updatedVacancy.salaryFrom
      vacancyAnalytics.salaryTo = updatedVacancy.salaryTo
      vacancyAnalytics.grade = updatedVacancy.grade
      vacancyAnalytics.employmentType = updatedVacancy.employmentType
      vacancyAnalytics.englishLevel = updatedVacancy.englishLevel
      vacancyAnalytics.category = updatedVacancy.category

      axios.put(`http://localhost:8085/analytics/vacancies/${vacancyAnalytics.id}`, vacancyAnalytics, {
        headers: {Authorization: localStorage.getItem('token')},
      }).then(response => {
        console.log("Analytics saved")
      }).catch(error => {
        console.log(error)
      })
    },
    fetchVacancy(){
      const id = this.$route.params.id;
      axios.get(`http://localhost:8085/vacancies/${id}`, {
        headers: { Authorization: localStorage.getItem("token") },
      }).then(response => {
        this.vacancy = response.data;
        this.fetchSkills()
      })
    },
    fetchSkills() {
      axios.get(`http://localhost:8085/skills`, {
        headers: { Authorization: localStorage.getItem("token") },
      }).then(response => {
        this.skills = response.data
        this.selectedSkills = this.skills.filter(skill => this.vacancy.skills.some(vacancySkill => vacancySkill.name === skill.name)).map(skill => skill.name);
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
    updateSkills() {
      const tempSkills = [];
      this.selectedSkills.forEach(skill => {
        const selectedSkill = this.skills.find(s => s.name === skill);
        tempSkills.push({ id: selectedSkill.id, name: skill });
      });
      this.vacancy.skills = tempSkills.filter(skill => {
        return this.selectedSkills.includes(skill.name);
      });
    },
  },

  mounted() {
    axios.get(`http://localhost:8085/employers/email`, {
      headers: { Authorization: localStorage.getItem("token") },
    }).then(response => {
      this.employer = response.data
    })
    this.fetchVacancy()
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
  border: none;
  cursor: pointer;
}

a:hover {
  background-color: #0E6CB3;
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

.header h1 {
  margin: 0;
}

.tabcontent {
  display: none;
  padding: 20px;
  border-top: none;
}

#create-vacancy {
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
  height: 200px;
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

select::-ms-expand {
  display: none;
}

select::-webkit-select {
  -webkit-appearance: none;
  appearance: none;
}

.create-vacancy img {
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

.delete-btn {
  border: 1px solid red;
  margin-right: 10px;
  padding: 10px 20px;
  border-radius: 5px;
  background-color: #FFF;
  color: red;
  font-weight: bold;
  text-decoration: none;
}

.delete-btn:hover {
  background-color: red;
  color: white;
}

.update-btn {
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

.update-btn:hover {
  background-color: #168FF0;
  color: white;
}

.route-active {
  text-decoration: underline;
}
</style>