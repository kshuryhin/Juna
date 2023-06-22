<template>
    <body>
    <header>
        <div class="logo">
            <h1>Juna Mentors</h1>
        </div>
        <nav>
            <ul>
                <router-link :to="{ name: 'mentors'}">Mentors</router-link>
              <router-link :to="{ name: 'courses'}">Courses</router-link>
                <router-link :to="{ name: 'appliedCourses'}">Applied Courses</router-link>
                <li><a @click="this.logout()" href="#">Logout</a></li>
            </ul>
        </nav>
    </header>

    <main>

        <h1>{{ this.lesson.name }}</h1>
        <div class="tabs">
            <button class="tablinks active" @click="" id="defaultOpen">Lesson</button>
            <button class="tablinks" @click="navigateToVideoLinks()">Videos</button>
        </div>
        <p class="lesson-text">{{ this.lesson.text }}</p>
        <div class="button-group">
            <button class="prev-button" v-bind:class="{centered:isCentered, disabled:isPreviousDisabled}" @click="previousLesson()">&lt; Previous</button>
            <button class="next-button" v-bind:class="{centered:isCentered, disabled:isNextDisabled}" @click="nextLesson()">Next &gt;</button>
        </div>
    </main>

<!--    <footer>-->
<!--        <div class="footer-bottom">-->
<!--            <p>&copy; 2023 Juna Jobs</p>-->
<!--        </div>-->
<!--    </footer>-->
    </body>
</template>
<script>
import axios from "axios";
import authMixin from "@/components/authMixin";
import roleMixin from "@/components/roleMixin";
import roles from "@/roles";
import {logout} from "@/utils/auth";
import silentLoginMixin from "@/components/silentLoginMixin";

export default {
    name: "Lesson",
    // mixins: [authMixin, roleMixin, silentLoginMixin],
    // requiredRole: roles.CANDIDATE,

    data() {
        return {
            lesson: {},
            course: {},
            isPreviousDisabled: false,
            isNextDisabled: false,
            isCentered: false,
        };
    },
    methods: {
        async fetchLessonInfo() {
            const courseId = this.$route.params.courseId
            const orderInCourse = this.$route.params.orderInCourse

            const response = await axios.get(`http://localhost:8085/lessons/course/${courseId}/lesson/${orderInCourse}`)
            this.lesson = response.data

            const courseRequest = await axios.get(`http://localhost:8085/courses/${courseId}`)
            this.course = courseRequest.data


            if (this.lesson.orderInCourse == 1) {
                this.isPreviousDisabled = true
                this.isCentered = true
            }

            if (this.lesson.orderInCourse == this.course.lessons.length) {
                this.isNextDisabled = true
                this.isCentered = true
            }

        },
        navigateToVideoLinks() {
            this.$router.push({name: 'videoLinks', params: {courseId: this.course.id, orderInCourse:this.lesson.orderInCourse}})
        },

        nextLesson() {
            this.$router.push({
                name: 'lesson',
                params: {
                    course: this.course.id,
                    orderInCourse: parseInt(this.lesson.orderInCourse) + 1
                }
            })
            setTimeout(function() {
                window.location.reload()
            }, 100)
        },

        previousLesson() {
            this.$router.push({name: 'lesson', params:{course: this.course.id, orderInCourse: (parseInt(this.lesson.orderInCourse)-1)}})
            setTimeout(function() {
                window.location.reload()
            }, 100) 
        },

    },
    async mounted() {
        await this.fetchLessonInfo();
    },

};

</script>
<style scoped>

.disabled {
    display: none;
}

.centered {
    margin: auto;
}

.button-group {
    display: flex;
    justify-content: space-between;
    margin-top: 40px;
}
.prev-button,
.next-button {
    background-color: transparent;
    border: none;
    color: #168FF0;
    font-size: 20px;
    font-weight: bold;
    cursor: pointer;
}

.lesson-text {
    color: black;
    width: 70%;
    margin: auto;
    text-align: justify;
    line-height: 1.5;
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
    padding: 16px 24px;
    margin: 0 10px;
    border-radius: 4px 4px 0 0;
    font-size: 16px;
    margin-bottom: -10px;
}

.tablinks:hover, .active {
    background-color: #168FF0;
    color: white;
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