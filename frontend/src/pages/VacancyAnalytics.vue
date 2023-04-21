<template>
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
  <div class="main">
    <h3>{{ name }}</h3>
    <p>Country: {{ country }}</p>
    <p>Employment Type: {{ employmentType }}</p>
    <p>English Level: {{ englishLevel }}</p>
    <p>Category: {{ category }}</p>
    <p>Date Posted: {{ formattedDatePosted }}</p>
    <div class="analytics-container">
      <h4>Applications, Savings and Views</h4>
      <canvas ref="applicationsChart" height="100"/>
    </div>
    <div v-if="allAnalytics" class="analytics-container">
      <h4>Vacancy Views Comparison</h4>
      <label for="comparison-select">Compare by:</label>
      <select id="category" name="Category" v-model="comparisonParameter">
        <option value="ALL">All</option>
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
      <canvas ref="comparisonChart" height="100"/>
    </div>
  </div>
  <footer>
    <div class="footer-bottom">
      <p>&copy; 2023 Juna Jobs</p>
    </div>
  </footer>
</template>

<script>
import axios from "axios";
import { Chart } from "chart.js";
import authMixin from "@/components/authMixin";
import roleMixin from "@/components/roleMixin";
import silentLoginMixin from "@/components/silentLoginMixin";
import roles from "@/roles";
import {logout} from "@/utils/auth";

export default {
  name: "VacancyAnalytics",
  mixins: [authMixin, roleMixin, silentLoginMixin],
  requiredRole: roles.EMPLOYER,
  data() {
    return {
      name: "",
      country: "",
      employmentType: "",
      englishLevel: "",
      category: "",
      datePosted: null,
      analytics: null,
      allAnalytics: [],
      comparisonParameter: "ALL",
      comparisonChart:null,
    };
  },
  watch: {
    comparisonParameter() {
      if (this.allAnalytics.length > 0) {
        this.initComparisonChart(this.allAnalytics);
      }
    },
  },
  computed: {
    formattedDatePosted() {
      return this.datePosted ? this.datePosted.toLocaleDateString() : "";
    },
  },
  async created() {
    const originId = this.$route.params.id;
    const response = await axios.get(`http://localhost:8085/analytics/vacancies/origin/${originId}`, {
      headers: {Authorization: localStorage.getItem('token')}
    });
    const allResponse = await axios.get(`http://localhost:8085/analytics/vacancies`, {
      headers: {Authorization: localStorage.getItem('token')}
    });

    this.analytics = response.data;
    this.allAnalytics = allResponse.data;
    this.name = this.analytics.name;
    this.country = this.analytics.country;
    this.employmentType = this.analytics.employmentType;
    this.englishLevel = this.analytics.englishLevel;
    this.category = this.analytics.category;
    this.datePosted = new Date(this.analytics.datePosted);

    if (this.analytics) {
      this.initCharts(this.analytics);
    }

    if (this.allAnalytics.length > 0) {
      this.initComparisonChart(this.allAnalytics);
    }
  },
  methods: {
    async logout() {
      logout()
      this.$router.push('/');
    },

    initComparisonChart(allAnalytics) {
    if (this.comparisonChart) this.comparisonChart.destroy()

      const filteredAnalytics = allAnalytics.filter(
          (item) => item.category === this.comparisonParameter || this.comparisonParameter === "ALL"
      );

      const labels = filteredAnalytics.map((item, index) => `Vacancy ${index + 1}`);
      const views = filteredAnalytics.map((item) => item.views);

      const comparisonCtx = this.$refs.comparisonChart.getContext("2d");
      this.comparisonChart = new Chart(comparisonCtx, {
        type: "line",
        data: {
          labels: labels,
          datasets: [
            {
              label: "Views",
              data: views,
              borderColor: "rgba(75, 192, 192, 1)",
              backgroundColor: "rgba(75, 192, 192, 0.2)",
            },
            {
              label: "Current Vacancy",
              data: Array.from({ length: views.length }, () => this.analytics.views),
              borderColor: "rgba(255, 99, 132, 1)",
              backgroundColor: "rgba(255, 99, 132, 0.2)",
              pointRadius: 5,
            },
          ],
        },
        options: {
          scales: {
            y: {
              beginAtZero: true,
            },
          },
        },
      });
    },
    initCharts(analytics) {
      // Applications, Savings & Views Chart
      const applicationsCtx = this.$refs.applicationsChart.getContext("2d");
      const applicationsChart = new Chart(applicationsCtx, {
        type: "bar",
        data: {
          labels: ["Applications", "Savings", "Views"],
          datasets: [
            {
              label: "Data",
              data: [analytics.applications, analytics.savings, analytics.views],
              backgroundColor: [
                "rgba(255, 99, 132, 0.2)",
                "rgba(255, 206, 86, 0.2)",
                "rgba(75, 192, 192, 0.2)",
              ],
              borderColor: [
                "rgba(255, 99, 132, 1)",
                "rgba(255, 206, 86, 1)",
                "rgba(75, 192, 192, 1)",
              ],
              borderWidth: 1,
            },
          ],
        },
        options: {
          scales: {
            y: {
              beginAtZero: true,
            },
          },
        },
      });
    },
  },
};
</script>
<style scoped>
body {
  background-color: white;
  font-family: Arial, sans-serif;
  color: #168FF0;
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

.main {
  margin: 100px 0px;
}

.analytics-container {
  margin: 100px 0px;
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

.router-link-active {
  text-decoration: underline;
}

.chart-container {
  margin: 100px 0;
}

</style>