import { createRouter, createWebHistory } from "vue-router";

import Home from "@/views/Home.vue";

import userRoutes from './user.js';
import adminRoutes from './admin.js';
import codingroomRoutes from "./codingroom.js";

const routes = [
    {
        path: '/',
        component: Home
    },
    ...userRoutes,
    ...adminRoutes,
    ...codingroomRoutes
];

const router = createRouter({
    history: createWebHistory(),
    routes
});

export default router;
