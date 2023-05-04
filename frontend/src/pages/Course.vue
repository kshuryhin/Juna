<template>
    <body>
    <header>
        <div class="logo">
            <h1>Juna Mentors</h1>
        </div>
        <nav>
            <ul>
                <router-link :to="{ name: 'mentors'}">Mentors</router-link>
                <router-link :to="{ name: ''}">Applied Courses</router-link>
                <li><a @click="logout" href="#">Logout</a></li>
            </ul>
        </nav>
    </header>

    <main>

        <h1>{{ course.name }}</h1>

        <p class="course_description">{{ course.description }}</p><br><br>

        <ol class="ordered-list">
            <li v-for="(lesson, index) in lessons" :key="index" @click="navigateToLesson(index+1)">
                 {{ lesson.name }}
            </li>
        </ol>

    </main>
    </body>
</template>

<script>
import axios from "axios";

export default {
    data() {
        return {
            course: {},
            lessons: [],
        }
    },

    methods: {
        async fetchCourseInfo() {
            const id = this.$route.params.id
            const response = await axios.get(`http://localhost:8082/api/v1/courses/${id}`)
            this.course = response.data
            this.lessons = this.course.lessons
        },

        navigateToLesson(orderInCourse) {
            this.$router.push({name: 'lesson', params: {courseId: this.course.id, orderInCourse: orderInCourse}})
        },

    },

    mounted() {
        this.fetchCourseInfo()
    }
}
</script>

<style scoped>


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
/*li {*/
/*    display: inline-block;*/
/*    margin-right: 20px;*/
/*}*/

.router-link-active {
    text-decoration: underline;
}

.header h1 {
    margin: 0;
}


.ordered-list {
    list-style-type: decimal;
    width: 80%;
    margin: 0 auto;
    text-align: left;
    font-size: 1.2em;
    padding-left: 3%;
}

.ordered-list li {
    margin-bottom: 10px;
    cursor: pointer;
}

.course_description {
    color: black;
    text-align: justify;
    width: 80%;
    margin: auto;
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

</style>



