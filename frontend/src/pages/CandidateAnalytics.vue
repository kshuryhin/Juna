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
    <button class="tablinks" @click="navigateToSalariesAnalytics">Salaries</button>
    <button class="tablinks active" @click="navigateToCandidatesAnalytics">Candidates</button>
  </div>
  <div class="chart-container">
    <label for="parameter-select">Choose a parameter:</label>
    <select id="parameter-select" v-model="selectedParameter">
      <option value="grade">Grade</option>
      <option value="country">Country</option>
      <option value="englishLevel">English Level</option>
      <option value="employmentType">Employment Type</option>
      <option value="category">Category</option>
    </select>
    <div>
      <canvas id="candidateAnalyticsChart"></canvas>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import { Chart } from 'chart.js/auto';
import {logout} from "@/utils/auth";

export default {
  data() {
    return {
      selectedParameter: 'grade',
    };
  },
  mounted() {
    this.updateChart();
  },
  watch: {
    selectedParameter() {
      this.updateChart();
    },
  },
  computed: {
    currentRole() {
      return localStorage.getItem("role");
    },
  },
  methods: {
    navigateToGeneralAnalytics(){
      this.$router.push({name:"analytics"})
    },
    navigateToSalariesAnalytics(){
      this.$router.push({name:"salariesAnalytics"})
    },
    navigateToCandidatesAnalytics(){
      this.$router.push({name: "candidatesAnalytics"})
    },
    parseCandidateData(data, parameter) {
      const groupedData = data
          .filter(candidate => candidate.isActive)
          .reduce((acc, candidate) => {
            const key = candidate[parameter];
            if (!acc[key]) {
              acc[key] = { count: 0, totalSalary: 0 };
            }
            acc[key].count++;
            acc[key].totalSalary += candidate.salaryExpectations;
            return acc;
          }, {});

      const labels = Object.keys(groupedData);
      const avgSalaries = labels.map(key => groupedData[key].totalSalary / groupedData[key].count);

      const chartData = {
        labels: labels,
        datasets: [
          {
            label: 'Average Salary',
            data: avgSalaries,
            backgroundColor: 'rgba(255, 99, 132, 0.6)',
            borderColor: 'rgba(255, 99, 132, 1)',
            borderWidth: 1,
          },
        ],
      };
      return chartData;
    },
    async getData(parameter) {
      try {
        const response = await axios.get('http://localhost:8085/analytics/candidates', {
          headers: { Authorization: localStorage.getItem('token') },
        });
        const chartData = this.parseCandidateData(response.data, parameter);
        this.renderChart(chartData);
      } catch (error) {
        console.error(error);
      }
    },
    renderChart(data) {
      if (this.chartInstance) {
        this.chartInstance.destroy();
      }
      this.chartInstance = new Chart('candidateAnalyticsChart', {
        type: 'bar',
        data: data,
        options: {
          scales: {
            y: {
              beginAtZero: true,
            },
          },
        },
      });
    },
    updateChart: async function() {
      await this.getData(this.selectedParameter);
    },
    async logout() {
      logout();
      this.$router.push('/');
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
