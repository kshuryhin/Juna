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
import EditVacancy from "@/pages/EditVacancy.vue";
import Mentors from "@/pages/Mentors.vue";
import Applications from "@/pages/Applications.vue";
import Application from "@/pages/Application.vue";
import VacancyAnalytics from "@/pages/VacancyAnalytics.vue";
import Employer from "@/pages/Employer.vue";
import Mentor from "@/pages/Mentor.vue";
import MentorCourses from "@/pages/MentorCourses.vue";
import Courses from "@/pages/Courses.vue";
import Course from "@/pages/Course.vue";
import Analytics from "@/pages/Analytics.vue";
import SalariesAnalytics from "@/pages/SalariesAnalytics.vue";
import CandidateAnalytics from "@/pages/CandidateAnalytics.vue";
import Lesson from "@/pages/Lesson.vue";
import VideoLinks from "@/pages/VideoLinks.vue";
import MentorProfile from "@/pages/MentorProfile.vue";
import MentorMyCourses from "@/pages/MentorMyCourses.vue";
import AddCourse from "@/pages/AddCourse.vue";
import AddLesson from "@/pages/AddLesson.vue";
import EditCourse from "@/pages/EditCourse.vue";
import EditLesson from "@/pages/EditLesson.vue";
import MentorsSignUp from "@/pages/MentorsSignUp.vue";
import MentorsLogin from "@/pages/MentorsLogin.vue";
import AppliedCourses from "@/pages/AppliedCourses.vue";

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
        path: '/employer/:id/vacancy/:vacancyId',
        name: 'employer',
        component: Employer,
        props: true
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
        path: '/editVacancy/:id',
        name: 'editVacancy',
        component: EditVacancy,
        props: true
    },
    {
        path: '/vacancy/analytics/:id',
        name: 'vacancyAnalytics',
        component: VacancyAnalytics,
        props: true
    },
    {
        path: '/applications/vacancy/:id',
        name: 'applications',
        component: Applications,
        props: true
    },
    {
        path: '/application/:id/vacancy/:vacancyId',
        name: 'application',
        component: Application,
        props: true
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
    },
    {
        path:'/mentor/:id',
        name: 'mentor',
        component: Mentor,
    },
    {
        path: '/mentor/:id/courses',
        name: 'mentor-courses',
        component: MentorCourses,
    },
    {
        path: '/courses',
        name: 'courses',
        component: Courses,
    },
    {
        path: '/course/:id',
        name: 'course',
        component: Course
    },
    {
        path: '/analytics',
        name: 'analytics',
        component: Analytics
    },
    {
        path: '/analytics/salaries',
        name: 'salariesAnalytics',
        component: SalariesAnalytics
    },
    {
        path: '/analytics/candidates',
        name: 'candidatesAnalytics',
        component: CandidateAnalytics
    },
    {
        path: '/course/:courseId/lesson/:orderInCourse',
        name: 'lesson',
        component: Lesson
    },

    {
        path: '/course/:courseId/lesson/:orderInCourse/videos',
        name: 'videoLinks',
        component: VideoLinks
    },
    {
        path: '/mentor/:id/profile',
        name: 'mentorProfile',
        component: MentorProfile
    },
    {
        path: '/mentor/:id/my-courses',
        name: 'mentorMyCourses',
        component: MentorMyCourses
    },
    {
        path: '/mentor/:id/add-course',
        name: 'addCourse',
        component: AddCourse
    },
    {
        path: '/course/:id/add-lesson',
        name: 'addLesson',
        component: AddLesson
    },
    {
        path: '/course/:id/edit',
        name: 'editCourse',
        component: EditCourse
    },
    {
        path: '/course/:courseId/lesson/:orderInCourse/edit',
        name: 'editLesson',
        component: EditLesson,
    },
    {
        path: '/mentors/signup',
        name: 'mentorsSignup',
        component: MentorsSignUp
    },
    {
        path: '/mentors/login',
        name: 'mentorsLogin',
        component: MentorsLogin
    },
    {
        path: '/courses/applied',
        name: 'appliedCourses',
        component: AppliedCourses
    }



]

const router = createRouter({
    history: createWebHashHistory(),
    routes})

export default router
