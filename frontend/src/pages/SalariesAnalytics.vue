<template>
  <header>
    <div class="logo">
      <h1>Juna Jobs</h1>
    </div>
    <nav>
      <ul>
        <router-link
            v-if="currentRole === 'CANDIDATES'"
            :to="{ name: 'candidateProfile' }"
        >
          My Profile
        </router-link>
        <router-link
            v-if="currentRole === 'EMPLOYERS'"
            :to="{ name: 'employerProfile' }"
        >
          My Profile
        </router-link>
        <router-link v-if="currentRole === 'CANDIDATES'" :to="{ name: 'vacancies' }">
          Vacancies
        </router-link>
        <router-link v-if="currentRole === 'EMPLOYERS'" :to="{ name: 'candidates' }">
          Candidates
        </router-link>
        <router-link :to="{ name: 'analytics' }" class="route-active">Analytics</router-link>
        <li>
          <a @click="this.logout()" href="#">Logout</a>
        </li>
      </ul>
    </nav>
  </header>
  <div class="tabs">
    <button class="tablinks" @click="navigateToGeneralAnalytics" id="defaultOpen">General Analytics</button>
    <button class="tablinks active" @click="navigateToSalariesAnalytics">Salaries</button>
    <button class="tablinks" @click="navigateToCandidatesAnalytics">Candidates</button>
  </div>
  <div class="chart-container">
    <div class="parameter-select">
      <label for="parameter">Choose a parameter:</label>
      <select id="parameter" v-model="selectedParameter">
        <option value="grade">Grade</option>
        <option value="country">Country</option>
        <option value="employmentType">Employment Type</option>
        <option value="englishLevel">English Level</option>
        <option value="category">Category</option>
      </select>
    </div>
    <canvas id="myChart"></canvas>
  </div>
  <footer>
    <div class="footer-bottom">
      <p>&copy; 2023 Juna Jobs</p>
    </div>
  </footer>
</template>

<script>
import axios from "axios";
import { Chart } from 'chart.js/auto';
import {logout} from "@/utils/auth";

export default {
  data() {
    return {
      selectedParameter: "grade",
      chart: null,
    };
  },
  methods: {
    navigateToSalariesAnalytics(){
      this.$router.push({name:"salariesAnalytics"})
    },
    navigateToGeneralAnalytics(){
      this.$router.push({name:"analytics"})
    },
    navigateToCandidatesAnalytics(){
      this.$router.push({name: "candidatesAnalytics"})
    },
    async updateChart() {
      await this.getData(this.selectedParameter);
    },

    async getData() {
      try {
        const response = await axios.get('http://localhost:8085/analytics/vacancies', {
          headers: { Authorization: localStorage.getItem('token') },
        });
        const chartData = this.parseData(response.data, this.selectedParameter);
        this.renderChart(chartData);
      } catch (error) {
        console.error(error);
      }
    },
    renderChart(chartData) {
      const ctx = document.getElementById('myChart').getContext('2d');
      if (this.chart) {
        this.chart.destroy(); // Destroy the previous chart instance before creating a new one
      }
      this.chart = new Chart(ctx, {
        type: 'bar',
        data: chartData,
        options: {
          scales: {
            y: {
              beginAtZero: true,
            },
          },
        },
      });
    },
    parseData(data, selectedParameter) {
      const chartData = {
        labels: [],
        datasets: [{
          label: 'Salaries',
          data: [],
          fill: false,
          backgroundColor: 'rgba(54, 162, 235, 0.6)', // Update this line to change the color of columns
          borderColor: 'rgba(54, 162, 235, 1)',
          borderWidth: 1,
          tension: 0.1,
        }],
      };

      const groupedData = data.reduce((acc, item) => {
        const key = item[selectedParameter];
        if (!acc[key]) {
          acc[key] = {
            count: 0,
            salarySum: 0,
          };
        }
        acc[key].count++;
        acc[key].salarySum += item.salaryFrom;
        return acc;
      }, {});

      for (const key in groupedData) {
        chartData.labels.push(key);
        chartData.datasets[0].data.push(groupedData[key].salarySum / groupedData[key].count);
      }

      return chartData;
    },
    async logout() {
      logout();
      this.$router.push('/');
    },
  },
  computed: {
    currentRole() {
      return localStorage.getItem("role");
    },
  },
  mounted() {
      this.updateChart();
    },
  watch: {
    selectedParameter() {
      this.updateChart();
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

.route-active {
  text-decoration: underline;
}

.chart-container {
  margin: 100px 0;
}
</style>
