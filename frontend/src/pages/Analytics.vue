<template>
  <header>
    <div class="logo">
      <h1>Juna Jobs</h1>
    </div>
    <nav>
      <ul>
        <router-link
            v-if="currentRole === 'CANDIDATES'"
            class="route-active"
            :to="{ name: 'candidateProfile' }"
        >
          My Profile
        </router-link>
        <router-link
            v-if="currentRole === 'EMPLOYERS'"
            class="route-active"
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
        <router-link :to="{ name: 'analytics' }">Analytics</router-link>
        <li>
          <a @click="this.logout()" href="#">Logout</a>
        </li>
      </ul>
    </nav>
  </header>

  <div class="tabs">
    <button class="tablinks active" @click="navigateToGeneralAnalytics" id="defaultOpen">General Analytics</button>
    <button class="tablinks" @click="navigateToSalariesAnalytics">Salaries</button>
    <button class="tablinks" @click="navigateToCandidatesAnalytics">Candidates</button>
  </div>
  <div class="chart-container">
    <canvas id="myChart"></canvas>
  </div>
  <div class="chart-container">
    <canvas id="myCandidateChart"></canvas>
  </div>
  <footer>
    <div class="footer-bottom">
      <p>&copy; 2023 Juna Jobs</p>
    </div>
  </footer>
</template>

<script>
import axios from 'axios';
import { Chart } from 'chart.js/auto';
import authMixin from "@/components/authMixin";
import roleMixin from "@/components/roleMixin";
import silentLoginMixin from "@/components/silentLoginMixin";
import {logout} from "@/utils/auth";

export default {
  mixins: [authMixin, silentLoginMixin],
  name: 'Analytics',
  mounted() {
    this.getData();
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
    async getData() {
      try {
        const response = await axios.get('http://localhost:8085/analytics/vacancies', {
          headers: {Authorization: localStorage.getItem('token')}
        });
        const chartData = this.parseData(response.data);
        const candidateResponse = await axios.get('http://localhost:8085/analytics/candidates', {
          headers: {Authorization: localStorage.getItem('token')}
        });
        const candidateChartData = this.parseCandidateData(candidateResponse.data);



        this.renderChart(chartData);
        this.renderCandidateChart(candidateChartData);
      } catch (error) {
        console.error(error);
      }
    },
    parseCandidateData(data) {
      const filteredData = data.filter(candidate => candidate.category !== null);
      const groupedCandidates = filteredData.reduce((result, candidate) => {
        if (!result[candidate.category]) {
          result[candidate.category] = { active: 0, inactive: 0 };
        }
        if (candidate.isActive) {
          result[candidate.category].active++;
        } else {
          result[candidate.category].inactive++;
        }
        return result;
      }, {});

      const chartData = {
        labels: Object.keys(groupedCandidates),
        datasets: [
          {
            label: "Active Candidates",
            data: Object.values(groupedCandidates).map(item => item.active),
            backgroundColor: "rgba(75, 192, 192, 0.2)",
            borderColor: "rgba(75, 192, 192, 1)",
            borderWidth: 1,
          },
          {
            label: "Inactive Candidates",
            data: Object.values(groupedCandidates).map(item => item.inactive),
            backgroundColor: "rgba(255, 99, 132, 0.2)",
            borderColor: "rgba(255, 99, 132, 1)",
            borderWidth: 1,
          },
        ],
      };

      return chartData;
    },
    parseData(data) {
      const chartData = {
        labels: [],
        datasets: [{
          label: 'Views',
          data: [],
          fill: false,
          borderColor: 'blue',
          tension: 0.1
        }, {
          label: 'Applications',
          data: [],
          fill: false,
          borderColor: 'red',
          tension: 0.1
        }]
      };
      for (const item of data) {
        chartData.labels.push(item.datePosted);
        chartData.datasets[0].data.push(item.views);
        chartData.datasets[1].data.push(item.applications);
      }
      return chartData;
    },
    renderCandidateChart(data) {
      const chart = new Chart("myCandidateChart", {
        type: "bar",
        data: data,
        options: {
          scales: {
            y: {
              beginAtZero: true,
            },
          },
          indexAxis: "y",
        },
      });
    },
    renderChart(data) {
      const chart = new Chart('myChart', {
        type: 'line',
        data: data,
        options: {
          scales: {
            y: {
              beginAtZero: true
            }
          }
        }
      });
    },
    async logout() {
      logout();
      this.$router.push('/');
    },
  }
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

.router-link-active {
  text-decoration: underline;
}

.chart-container {
  margin: 100px 0;
}

</style>


