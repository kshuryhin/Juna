<template>
  <!DOCTYPE html>
  <html>
  <head>
    <title>Juna - Jobs in IT</title>
    <link rel="stylesheet" type="text/css" href="style.css">
  </head>
  <body>
  <header>
    <div class="logo">
      <h1>Juna</h1>
    </div>
    <nav>
      <ul>

        <li><router-link to="/login">Find Job</router-link></li>
        <li><a href="#">Find Mentor</a></li>
        <li><router-link to="/login">Find Candidate</router-link></li>
      </ul>
    </nav>
  </header>

  <section class="hero">
    <div class="hero-content">
      <h2>Welcome to Juna</h2>
      <p>Find your dream job in IT or connect with a mentor to grow your career. Juna is the perfect platform for both job seekers and hiring managers.</p>
      <router-link class="cta-btn" to="/signup">Sign Up</router-link>
    </div>
  </section>

  <section class="features">
    <div class="feature-box">
      <h3>Find a Job</h3>
      <p>Search through our extensive database of IT job listings to find the perfect fit for your skills and experience.</p>
    </div>
    <div class="feature-box">
      <h3>Find a Mentor</h3>
      <p>Connect with experienced professionals in your field to gain valuable insights and guidance.</p>
    </div>
    <div class="feature-box">
      <h3>Find a Candidate</h3>
      <p>Post your job listings and connect with qualified candidates who are looking for opportunities in the IT industry.</p>
    </div>
  </section>

  <section class="about-us">
    <div class="container">
      <h2>About Us</h2>
      <p>Juna is a platform designed to help connect IT professionals with job opportunities and mentorship programs. Our goal is to create a supportive community for everyone in the tech industry.</p>
      <p>Whether you're a candidate looking for your next job, an employer searching for top talent, or a mentor wanting to give back to the community, Juna has something for you.</p>
    </div>
  </section>


  <footer>
    <p>&copy; 2023 Juna. All rights reserved.</p>
  </footer>
  </body>
  </html>

</template>
<script>
import roles from "@/roles";
export default {
  name: "MainPage.vue",
  methods: {

  },

  created: function() {
    const token = localStorage.getItem('token')
    const role = localStorage.getItem('role')
    if (token != null) {
      switch (role) {
        case roles.CANDIDATE:
          this.$router.push('/vacancies')
          break
        case roles.EMPLOYER:
          this.$router.push('/candidates')
          break
        case roles.MENTOR:
          this.$router.push('/students')
          break
        default:
          alert("Unknown role")
      }
    }
  }
}
</script>

<style scoped>
@import url('https://fonts.cdnfonts.com/css/poetsen-one');

/* General styles */
body {
  margin: 0;
  padding: 0;
  font-family: Arial, sans-serif;
}

a {
  color: inherit;
  text-decoration: none;
}

/* Header styles */
header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 20px;
  background-color: #168FF0;
  box-shadow: 0px 2px 10px rgba(0, 0, 0, 0.1);
}

.logo h1 {
  margin: 0;
  color: white;
  font-family: 'Poetsen One', sans-serif;
  font-size: 70px;
  font-weight: bold;
}

nav ul {
  display: flex;
  list-style: none;
  margin: 0;
  padding: 0;
}

nav li {
  margin-left: 20px;
}

nav li:first-child {
  margin-left: 0;
}

/* Styling for hero section */
.hero {
  background-color: #f2f2f2;
  padding: 100px 0;
  text-align: center;
}

.hero-content {
  max-width: 600px;
  margin: 0 auto;
}

.hero h2 {
  font-size: 48px;
  margin-bottom: 20px;
}

.hero p {
  font-size: 20px;
  line-height: 1.5;
  margin-bottom: 40px;
}

.cta-btn {
  display: inline-block;
  padding: 15px 30px;
  font-size: 20px;
  font-weight: bold;
  background-color: #168FF0FF;
  color: #fff;
  text-decoration: none;
  border-radius: 5px;
}

.cta-btn:hover {
  background-color: #168FF0FF;
}

/* Styling for features section */
.features {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  margin-top: 80px;
}

.feature-box {
  flex: 0 1 300px;
  margin: 20px;
  background-color: #fff;
  padding: 30px;
  text-align: center;
  border-radius: 5px;
  box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
}

.feature-box h3 {
  font-size: 24px;
  margin-bottom: 20px;
}

.feature-box p {
  font-size: 16px;
  line-height: 1.5;
  margin-bottom: 30px;
}

.feature-box a {
  display: inline-block;
  padding: 10px 20px;
  font-size: 16px;
  font-weight: bold;
  background-color: #168FF0FF;
  color: #fff;
  text-decoration: none;
  border-radius: 5px;
}

.feature-box a:hover {
  background-color: #168FF0FF;
}

/* Styling for footer section */
footer {
  background-color: #f2f2f2;
  padding: 20px 0;
  text-align: center;
  font-size: 14px;
}

.about-us {
  background-color: #fff;
  padding: 50px 0;
}

.about-us h2 {
  font-size: 2rem;
  color: #168FF0;
  text-align: center;
}

.about-us p {
  font-size: 1.2rem;
  line-height: 1.5;
  margin-bottom: 1rem;
  text-align: justify;
  max-width: 700px;
  margin: 0 auto;
  padding: 0 20px;
}



</style>