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
        <input type="text" placeholder="Course Name" class="lesson_name" v-model="this.course.name">
        <br><br>
        <textarea placeholder="Course description" class="lesson_text" v-model="this.course.description"></textarea>
        <br><br>
        <button class="save_btn" @click="updateCourse()">Save</button>
        <h1>Lessons</h1>
        <button @click="addLesson()" class="save_btn">Add Lesson</button>

        <ol class="ordered-list">
            <li v-for="lesson in sortedLessons" :key="lesson.orderInCourse" @click="navigateToLesson(lesson.orderInCourse)">
                {{ lesson.name }}
            </li>
        </ol>
        <br><br>
        <button class="delete_btn" @click="deleteCourse()">Delete</button>

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
    name: "Mentor",
    // mixins: [authMixin, roleMixin, silentLoginMixin],
    // requiredRole: roles.CANDIDATE,
    // components: { vSelect },

    data() {
        return {
            course : {},
            lessons: [],
        };
    },
    methods: {
        async fetchCourseInfo() {
            const id = this.$route.params.id
            const response = await axios.get(`http://localhost:8085/courses/${id}`)
            this.course = response.data
            this.lessons = this.course.lessons
        },

        updateCourse() {
            const mentorId = this.$route.params.id
            axios.put(`http://localhost:8085/courses/${this.course.id}`, this.course)
                .then(this.$router.go())
        },

        addLesson() {
            this.$router.push({name: 'addLesson', params: {id: this.course.id}})
        },

        navigateToLesson(orderInCourse) {
            this.$router.push({name: 'editLesson', params: {courseId: this.course.id, orderInCourse: orderInCourse}})
        },

        async deleteCourse() {
            await axios.delete(`http://localhost:8085/courses/${this.course.id}`)
            await this.sleep(100)
            this.$router.push({name: 'courses'})
        },


        sleep(ms) {
            return new Promise(resolve => setTimeout(resolve, ms));
        }
    },

    computed: {
        sortedLessons() {
            return this.lessons.sort((a,b) => a.orderInCourse - b.orderInCourse)
        }
    },

    mounted() {
        this.fetchCourseInfo()
    }

};

</script>
<style scoped>

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

.ordered-list {
    list-style-type: decimal;
    width: 80%;
    margin: auto;
    text-align: left;
    font-size: 1.2em;
    padding-left: 3%;

    & li {
        cursor: pointer;
    }
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
    height: 120px;
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
    //display: inline-block;
    //margin-right: 20px;
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


