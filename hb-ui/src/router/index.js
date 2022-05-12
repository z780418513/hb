import {createRouter, createWebHashHistory, RouteRecordRaw} from "vue-router";

const routes = [
    { path: '/',
        name: 'Home',
        component: () => import("@/views/Home.vue")},
    {
        path: '/login',
        name: 'Login',
        component: () => import("@/views/Login.vue")
    }
]

const router = createRouter({
    history: createWebHashHistory(),
    routes
})

export default router;