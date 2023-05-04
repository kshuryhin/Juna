<template>
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

        <h1>{{ this.lesson.name }}</h1>
        <div class="tabs">
            <button class="tablinks" @click="navigateToLesson()" id="defaultOpen">Lesson</button>
            <button class="tablinks active">Videos</button>
        </div>
        <div v-for="link in videoLinksArray">
            <iframe width="560" height="315" :src="link" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" allowfullscreen></iframe>
        </div>
    </main>

</template>

<script>
import axios from "axios";

export default {
    data() {
        return {
            lesson: {},
            videoLinks: [],
            courseId: 0,
            orderInCourse: 0,
        }
    },

    methods: {
        async fetchVideoLinks() {
            this.courseId = this.$route.params.courseId
            this.orderInCourse = this.$route.params.orderInCourse
            const response = await axios.get(`http://localhost:8082/api/v1/lessons/course/${this.courseId}/lesson/${this.orderInCourse}`)
            this.lesson = response.data
            this.videoLinks = this.lesson.videoLinks
        },

        navigateToLesson() {
            this.$router.push({name: 'lesson', params: {courseId: this.courseId, orderInCourse: this.orderInCourse}})
        },
    },

    mounted() {
        this.fetchVideoLinks()
    },
    computed: {
        videoLinksArray() {
            return this.videoLinks.map((videoLink) => videoLink.link);
        }
    }
}
</script>

<style lang="scss" scoped>
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

/*.router-link-active {*/
/*    text-decoration: underline;*/
/*}*/

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

.tabcontent {
    display: none;
    padding: 20px;
    border-top: none;
}

#personal-info {
    display: block;
}

#saved-vacancies {
    display: none;
}

#applied-vacancies {
    display: none;
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
    border-radius: 3px;
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

.file-input-container {
    position: relative;
    display: inline-block;
}

#photo {
    display: none;
}


.file-label:hover {
    background-color: #0E6CB3;
}

.photo-container {
    text-align: center;
    margin-bottom: 20px;
}

.image-preview {
    display: inline-block;
    width: 150px;
    height: 150px;
    object-fit: cover;
    border-radius: 50%;
    margin-bottom: 20px;
    border: 3px solid #168FF0;
}

.file-label {
    display: inline-block;
    background-color: #168FF0;
    color: white;
    padding: 10px 20px;
    border-radius: 5px;
    font-weight: bold;
    cursor: pointer;
    margin-top: 10px;
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