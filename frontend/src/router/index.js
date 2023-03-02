import { createRouter, createWebHashHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import Registration from "@/pages/Registration.vue";
import HomePage from "@/pages/HomePage.vue";
import CandidateHome from "@/pages/CandidateHome.vue";
import EmployerHome from "@/pages/EmployerHome.vue";
import MentorHome from "@/pages/MentorHome.vue";

const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView
  },
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
    path: '/home',
    component: HomePage
  },
  {
    path: '/vacancies',
    name: 'vacancies',
    component: CandidateHome
  },
  {
    path: '/candidates',
    component: EmployerHome
  },
  {
    path: '/students',
    component: MentorHome

  }


]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

export default router
