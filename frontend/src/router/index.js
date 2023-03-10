import { createRouter, createWebHashHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import Registration from "@/pages/Registration.vue";
import CandidateHome from "@/pages/CandidateHome.vue";
import EmployerHome from "@/pages/EmployerHome.vue";
import MentorHome from "@/pages/MentorHome.vue";
import Login from "@/pages/Login.vue";
import MainPage from "@/pages/MainPage.vue";
import Vacancies from "@/pages/Vacancies.vue";
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
        path: '/vacs',
        component: Vacancies
    },
    {
        path: '/',
        component: MainPage
    },
    {
        path: '/vacancies',
        name: 'vacancies',
        component: Vacancies
    },
    {
        path: '/candidates',
        component: EmployerHome
    },
    {
        path: '/students',
        component: MentorHome
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
