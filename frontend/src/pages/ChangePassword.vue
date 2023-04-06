<template>
  <body>
  <div id="app">
    <header>
      <div class="logo">
        <h1>Juna Jobs</h1>
      </div>
      <nav>
        <ul>
          <router-link class="route-active" :to="{ name: 'candidateProfile'}">My Profile</router-link>
          <router-link :to="{ name: 'vacancies'}">Vacancies</router-link>
          <li><a href="#">Analytics</a></li>
          <li><a @click="this.logout()" href="#">Logout</a></li>
        </ul>
      </nav>
    </header>
    <main class="main-content">
      <div class="tabs">
        <button class="tablinks" @click="navigateToPersonalInfo">Personal Info</button>
        <button class="tablinks active" @click="navigateToChangePassword" id="defaultOpen">Change Password</button>
        <button class="tablinks" @click="navigateToSavedVacancies">Saved Vacancies</button>
        <button class="tablinks" @click="navigateToAppliedVacancies">Applied Vacancies</button>
      </div>
        <div class="change-password">
          <h2>Change Password</h2>
          <form @submit.prevent="handleChangePassword">
            <div class="input-group" :class="{ 'has-error': newPasswordsMismatch }">
              <label for="oldPassword">Enter your old password:</label>
              <input type="password" id="oldPassword" v-model="oldPassword" required />
            </div>
            <div class="input-group">
              <label for="newPassword">Enter new password:</label>
              <input type="password" id="newPassword" v-model="newPassword" required />
            </div>
            <div class="input-group">
              <label for="repeatNewPassword">Repeat new password:</label>
              <input type="password" id="repeatNewPassword" v-model="repeatNewPassword" required />
            </div>
            <button class="submit" type="submit">Submit</button>
          </form>
        </div>
    </main>
    <footer>
      <div class="footer-bottom">
        <p>&copy; 2023 Juna Jobs</p>
      </div>
    </footer>
  </div>

  </body>
</template>

<script>
import axios from "axios";
import {logout} from "@/utils/auth";
import authMixin from "@/components/authMixin";
import roleMixin from "@/components/roleMixin";
import roles from "@/roles";
import silentLoginMixin from "@/components/silentLoginMixin";


export default {
  mixins: [authMixin, roleMixin, silentLoginMixin],
  requiredRole: roles.CANDIDATE,
  data() {
    return {
      oldPassword: '',
      newPassword: '',
      repeatNewPassword: '',
    };
  },
  computed: {
    newPasswordsMismatch() {
      return this.newPassword !== this.repeatNewPassword;
    },
  },
  methods: {
    async logout() {
      logout()
      this.$router.push('/');
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
    async handleChangePassword() {
      if (this.newPasswordsMismatch) {
        alert('New passwords do not match');
        return;
      }
      const requestBody = {
        oldPassword: this.oldPassword,
        newPassword: this.newPassword,
      }
      axios.post(`http://localhost:8085/change/password`,  requestBody, {
        headers: { Authorization: localStorage.getItem("token") },
      }).then(response => {
        if (response.data.success) {
          alert('Password changed successfully');
        } else {
          alert('Old password is incorrect');
        }
      })

      // Reset the form
      this.oldPassword = '';
      this.newPassword = '';
      this.repeatNewPassword = '';
    },
  },
};
</script>

<style scoped>

.has-error {
  position: relative;
}

.has-error input {
  border-color: red;
}

.error-message {
  position: absolute;
  color: red;
  font-size: 0.8rem;
  margin-top: 5px;
}

header {
  font-family: Arial, sans-serif;
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

.route-active{
  text-decoration: underline;
}

.header h1 {
  margin: 0;
}

.change-password {
  width: 300px;
  margin: 0 auto;
  background-color: white;
  border-radius: 5px;
  padding: 2rem;
}

h2 {
  text-align: center;
  color: #168FF0;
  margin-bottom: 1.5rem;
}

.input-group {
  margin-bottom: 1rem;
}

label {
  display: block;
  margin-bottom: 0.5rem;
  color: #333;
  font-weight: bold;
}

input[type='password'] {
  width: 100%;
  padding: 0.5rem;
  border: 1px solid #168FF0;
  border-radius: 5px;
  color: #333;
  font-size: 0.9rem;
}

button {
  display: block;
  margin: 0 auto;
  padding: 0.5rem;
  background-color: #168FF0;
  border: none;
  color: white;
  font-size: 0.9rem;
  cursor: pointer;
  border-radius: 5px;
  transition: background-color 0.3s;
}

button:hover {
  background-color: #1372d4;
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
body,
html {
  height: 100%;
  margin: 0;
}

#app {
  display: flex;
  flex-direction: column;
  min-height: 100vh;
}

/* Add a flex-grow property to the main content to take up the remaining space */
.main-content {
  flex-grow: 1;
  margin: 0;
  padding: 20px;
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

.submit {
  font-weight: bold;
}
</style>

