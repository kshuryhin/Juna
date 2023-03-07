import { createRouter, createWebHashHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import Registration from "@/pages/Registration.vue";
import HomePage from "@/pages/HomePage.vue";
import CandidateHome from "@/pages/Vacancies.vue";
import EmployerHome from "@/pages/Candidates.vue";
import MentorHome from "@/pages/Students.vue";
import Login from "@/pages/Login.vue";
import Vacancies from "@/pages/Vacancies.vue";
import Students from "@/pages/Students.vue";
import Candidates from "@/pages/Candidates.vue";

const routes = [
    {
        path: '/about',
        name: 'about',
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () => import(/* webpackChunkName: "about" */ '../views/AboutView.vue')
    },
    {
        path: '/signup',
        component: Registration
    },
    {
        path: '/',
        component: HomePage
    },
    {
        path: '/vacancies',
        name: 'vacancies',
        component: Vacancies
    },
    {
        path: '/candidates',
        component: Candidates
    },
    {
        path: '/students',
        component: Students
    },
    {
        path: '/login',
        component: Login
    },



]

const router = createRouter({
    history: createWebHashHistory(),
    routes
})

export default router
