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
                <li><a @click="this.logout()" href="#">Logout</a></li>
            </ul>
        </nav>
    </header>

    <main>
        <br><br>
        <input type="text" placeholder="Lesson Name" class="lesson_name" v-model="this.lesson.name">
        <br><br>
        <textarea placeholder="Text" class="lesson_text" v-model="this.lesson.text"></textarea>
        <br>
        <h2>Videos</h2>
        <div v-for="(link, index) in videoLinks" :key="index">
            <input type="text" placeholder="Link" class="videoLink" v-model="link.link">
            <div class="close-button" @click="deleteVideoLink(index)">
                <div class="line"></div>
                <div class="line"></div>
            </div>
        </div>
        <button @click="addVideoLink()">Add Video</button>
        <br><br>
        <button class="save_btn" @click="saveLesson()">Save</button>
        <br><br>
        <button class="delete_btn" @click="deleteLesson()">Delete</button>
    </main>

    <footer>
        <div class="footer-bottom">
            <p>&copy; 2023 Juna Jobs</p>
        </div>
    </footer>
    </body>
</template>
<script>
import axios from "axios";
// import vSelect from 'vue-select';
import authMixin from "@/components/authMixin";
import roleMixin from "@/components/roleMixin";
import roles from "@/roles";
import {logout} from "@/utils/auth";
import silentLoginMixin from "@/components/silentLoginMixin";

export default {
    name: "Lesson",
    // mixins: [authMixin, roleMixin, silentLoginMixin],
    // requiredRole: roles.CANDIDATE,
    // components: { vSelect },

    data() {
        return {
            lesson: {},
            videoLinks: [{link: ''}],
        };
    },
    methods: {

        async fetchLessonInfo() {
            const courseId = this.$route.params.courseId
            const orderInCourse = this.$route.params.orderInCourse


            const response = await axios.get(`http://localhost:8082/api/v1/lessons/course/${courseId}/lesson/${orderInCourse}`)
            this.lesson = response.data
            this.videoLinks = this.lesson.videoLinks
        },

        async saveLesson() {
            const courseId = this.$route.params.courseId
            const orderInCourse = this.$route.params.orderInCourse
            this.lesson.videoLinks = this.videoLinks
            await axios.put(`http://localhost:8082/api/v1/lessons/course/${courseId}/lesson/${orderInCourse}`, this.lesson)
            await this.sleep(100)
            this.$router.push({name: 'editCourse', params: {id: courseId}})
        },

        addVideoLink() {
            this.videoLinks.push({link: ''})
        },

        async deleteLesson() {
            const courseId = this.$route.params.courseId
            await axios.delete(`http://localhost:8082/api/v1/lessons/${this.lesson.id}`)
            await this.sleep(100)
            this.$router.push({name: 'editCourse', params: {id: courseId}})
        },

        deleteVideoLink(index) {
            this.videoLinks.splice(index, 1)
        },


        sleep(ms) {
            return new Promise(resolve => setTimeout(resolve, ms));
        }
    },

    mounted() {
        this.fetchLessonInfo()
    }
};

</script>
<style scoped>

.close-button {
    position: relative;
    margin-left: 1100px;
    margin-top: -25px;
    width: 30px;
    height: 30px;
    cursor: pointer;
}

.line {
    width: 100%;
    height: 2px;
    background-color: #000;
    top: 50%;
    transform: translateY(-50%);
}

.line:first-child {
    transform: rotate(45deg);
}

.line:last-child {
    transform: rotate(-45deg);
}


.videoLink {
    border: 2px solid lightgray;
    border-radius: 10px;
    width: 30%;
    margin: auto;
    height: 40px;
}

.delete_btn {
    border: 1px solid darkred;
    padding: 10px 20px;
    border-radius: 5px;
    background-color: darkred;
    color: white;
    font-weight: bold;
    text-decoration: none;
    cursor: pointer;

}

.save_btn {
    border: 1px solid #168FF0;
    padding: 10px 20px;
    border-radius: 5px;
    background-color: #168FF0;
    color: white;
    font-weight: bold;
    text-decoration: none;
    cursor: pointer;
}

.lesson_text {
    border: 2px solid lightgray;
    border-radius: 10px;
    width: 60%;
    margin: auto;
    height: 500px;
    resize: none;

    &:focus {
        outline: none !important;
        border: 2px solid #168FF0;
    }
}


.lesson_name {
    border: 2px solid lightgray;
    border-radius: 10px;
    width: 60%;
    margin: auto;
    height: 40px;
}

input[type=text]:focus {
    outline: none !important;
    border: 2px solid #168FF0;
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


