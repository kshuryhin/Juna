import { createRouter, createWebHashHistory } from 'vue-router'
import Registration from "@/pages/Registration.vue";
import EmployerHome from "@/pages/EmployerHome.vue";
import MentorHome from "@/pages/MentorHome.vue";
import Login from "@/pages/Login.vue";
import MainPage from "@/pages/MainPage.vue";
import Vacancies from "@/pages/Vacancies.vue";
import Vacancy from "@/pages/Vacancy.vue";
import CandidateProfile from "@/pages/CandidateProfile.vue";
import ChangePassword from "@/pages/ChangePassword.vue";
import SavedVacancies from "@/pages/SavedVacancies.vue";
import AppliedVacancies from "@/pages/AppliedVacancies.vue";
import Candidates from "@/pages/Candidates.vue";
import Candidate from "@/pages/Candidate.vue";
import EmployerProfile from "@/pages/EmployerProfile.vue";
import ChangePasswordForEmployer from "@/pages/ChangePasswordForEmployer.vue";
import CreateVacancy from "@/pages/CreateVacancy.vue";
import MyVacancies from "@/pages/MyVacancies.vue";
import SavedCandidates from "@/pages/SavedCandidates.vue";
import Mentors from "@/pages/Mentors.vue";
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
        path: '/candidates',
        name: 'candidates',
        component: Candidates
    },
    {
       path: '/candidate/:id',
       name: 'candidate',
       component: Candidate,
       props: true,
    },
    {
        path: '/vacancies',
        name: 'vacancies',
        component: Vacancies
    },
    {
        path: '/myVacancies',
        component: MyVacancies
    },
    {
        path: '/vacancy/:id',
        name: 'vacancy',
        component: Vacancy,
        props: true
    },
    {
        path: '/candidate/profile',
        name: 'candidateProfile',
        component: CandidateProfile,
        props: true
    },
    {
        path: '/employer/profile',
        name: 'employerProfile',
        component: EmployerProfile,
        props: true
    },
    {
        path: '/candidate/saved',
        name: 'savedVacancies',
        component: SavedVacancies
    },
    {
        path: '/employer/saved',
        name: 'savedCandidates',
        component: SavedCandidates
    },
    {
        path: '/candidate/applied',
        component: AppliedVacancies
    },
    {
        path: '/change',
        component: ChangePassword
    },
    {
        path: '/change/employer',
        component: ChangePasswordForEmployer
    },
    {
        path: '/createVacancy',
        component: CreateVacancy
    },
    {
        path: '/students',
        component: MentorHome
    },
    {
        path: '/login',
        component: Login
    },
    {
        path: '/mentors',
        name: 'mentors',
        component: Mentors
    }



]

const router = createRouter({
    history: createWebHashHistory(),
    routes})

export default router
