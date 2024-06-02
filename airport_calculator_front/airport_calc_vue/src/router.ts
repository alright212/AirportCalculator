// src/router/index.ts
import { createRouter, createWebHistory } from 'vue-router';
import type { RouteRecordRaw } from 'vue-router';
import DistanceCalculator from '@/components/DistanceCalculator.vue';

const routes: Array<RouteRecordRaw> = [
    {
        path: '/',
        name: 'Home',
        component: DistanceCalculator,
    },
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;
