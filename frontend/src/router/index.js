import { createRouter, createWebHistory } from "vue-router";

import Home from "@/views/Home.vue";

import userRoutes from './user.js';

const routes = [
    {
        path: '/',
        component: Home
    },
    ...userRoutes
];

const router = createRouter({
    history: createWebHistory(),
    routes
});

export default router;
