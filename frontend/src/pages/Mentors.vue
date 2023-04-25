<template>
  <!DOCTYPE html>
    <html>
    <head>
        <title>Juna Mentors</title>
    </head>
    <body>
    <header>
        <div class="logo">
            <h1>Juna Mentors</h1>
        </div>
        <nav>
            <ul>
                <router-link :to="{ name: 'mentors'}">Mentors</router-link>
                <router-link :to="{ name: ''}">Applied Courses</router-link>
                <li><a @click="this.logout()" href="#">Logout</a></li>
            </ul>
        </nav>
    </header>


    <main>
        <div class="search-container">
            <section id="search" class="search-centered">
                <h2 class="search-label">Search</h2>
                <div class="search-row">
                    <input id="search" type="text" v-model="searchTerm" placeholder="Search by name" class="search-input" />
                    <button @click="searchJobs" class="search-button">Search</button>
                </div>
            </section>

        </div>

        <div class="content-container">
            <section id="filters">
                <h2>Filters</h2>
                <form @submit.prevent="applyFilters">
                    <label for="category">Category:</label>
                    <select id="category" name="Category" v-model="filters.selectedCategory">
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
                    <label for="sortType">Sort by:</label>
                    <select name="sorting" id="sortType" v-model="filters.sortType">
                        <option value="">Dont sort</option>
                        <option value="BY_RATING">By Rating</option>
                        <option value="BY_Courses_NUMBER">By Number of courses</option>
                    </select>
                    <button type="submit">Apply Filters</button>
                </form>
            </section>
            <section id="jobs">
                <h2>Available Mentors</h2>
                <ul id="job-listings">
                    <li class="job" v-for="mentor in mentors" :key="mentor.id">
                        <h3>{{ mentor.firstName }} {{ mentor.lastName }}</h3>
                        <p class="description">{{ mentor.description }}</p>

                        <router-link :to="{ name: 'mentor', params: { id: mentor.id }}">Visit</router-link>
                    </li>
                </ul>
            </section>
        </div>
    </main>
    </body>
    </html>
</template>

<script>
import axios from 'axios';
import roles from '@/roles';
import authMixin from '@/components/authMixin.js';
import roleMixin from '@/components/roleMixin.js';

import {logout} from '@/utils/auth';
import silentLoginMixin from "@/components/silentLoginMixin";

export default {
    // mixins: [authMixin, roleMixin, silentLoginMixin],
    requiredRole: roles.CANDIDATE,
    data() {
        return {
            filters: {
                selectedCategory: '',
                searchTerm: '',
                sortType: '',
            },
            mentors: [],
            allMentors: [],
        };
    },
    methods: {
        async fetchMentors() {
            try {
                const response = await axios.get('http://localhost:8082/api/v1/mentors', {
                    // headers: {
                    //     Authorization: localStorage.getItem('token'),
                    // },
                });

                this.allMentors = response.data;
                this.mentors = this.allMentors;
            } catch (error) {
                console.error(error);
            }
        },
        searchJobs() {
            const searchTerm = this.searchTerm.toLowerCase();
            this.mentors = this.allMentors.filter((mentor) =>
                `${mentor.firstName} ${mentor.lastName}`.toLowerCase().includes(searchTerm)
            );
        },
        async applyFilters() {

        },
        async logout() {
            logout();
            this.$router.push('/');
        },
    },
    mounted() {
        // this.applyFilters();
        this.fetchMentors();
    },
};
</script>


<style scoped>
/* Set body background and font */
body {
    background-color: white;
    font-family: Arial, sans-serif;
}

/* Set header style */
header {
    background-color: #168FF0;
    color: white;
    text-align: center;
    padding: 20px;
    display: flex;
    justify-content: space-between;
    align-items: center;
}

/* Set main container style */
main {
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    margin: 20px;
}

/* Set filters section style */
#filters {
    display: flex;
    flex-direction: column;
    align-items: flex-start;
    width: 30%;
}

/* Set filters form style */
form {
    display: flex;
    flex-direction: column;
    align-items: flex-start;
}

/* Set label style */
label {
    margin-bottom: 10px;
}

/* Set select style */
select {
    margin-bottom: 20px;
    padding: 5px;
    border-radius: 5px;
    border: none;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); /* Add a subtle box shadow */
    background-color: #f5f5f5; /* Add a light gray background color */
    font-size: 16px; /* Set a font size */
    font-weight: 500; /* Set a font weight */
    color: #333; /* Set a text color */
    transition: all 0.3s ease; /* Add a smooth transition on hover and focus */
}

select:hover,
select:focus {
    outline: none; /* Remove the default outline */
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.2); /* Add a stronger box shadow on hover and focus */
    background-color: #eee; /* Darken the background color on hover and focus */
}

/* Set button style */
button {
    background-color: #168FF0;
    color: white;
    padding: 10px 20px;
    border-radius: 5px;
    border: none;
    cursor: pointer;
}

/* Set jobs section style */
#jobs {
    width: 65%;
}

/* Set job list style */
ul {
    list-style-type: none;
    padding: 0;
}

/* Set job item style */
.job {
    margin-bottom: 20px;
    border: 2px solid #168FF0;
    border-radius: 10px;
    padding: 10px;
}

.job .description {
    display: -webkit-box;
    -webkit-line-clamp: 3;
    -webkit-box-orient: vertical;
    overflow: hidden;
    text-overflow: ellipsis;
}


/* Set job title style */
h3 {
    color: #168FF0;
}

.country {
    font-style: italic;
}

.category {
    font-style: italic;
}

.salaryFrom {
    font-style: italic;
}

.salaryTo {
    font-style: italic;
}

.grade {
    font-style: italic;
}

.englishLevel {
    font-style: italic;
}

/* Set apply now link style */
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
}

/* Set logo style */
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

.router-link-active {
    text-decoration: underline;
}

nav a.active,
nav a:hover {
    background-color: #168FF0;
}
/* Add these new styles to your existing CSS */
.search-container {
    position: absolute;
    width: 500px;
    left: 50%;
    top: 0;
    transform: translateX(-50%);
    z-index: 1;
}

.search-row {
    width: 100%;
}

.search-centered {
    display: flex;
    flex-direction: column;
    align-items: center;
}


.search-label {
    margin-bottom: 10px;
}

.search-input {
    width: 100%; /* Increase the width */
    padding: 12px 20px;
    margin: 8px 0;
    box-sizing: border-box;
    border: 2px solid #168FF0;
    border-radius: 12px; /* Increase border radius */
    font-size: 16px;
    outline: none;
}

.content-container {
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    margin-top: 200px; /* Adjust this value to position the content-container below the search container */
}

main {
    position: relative;
    margin: 20px;
}


</style>
