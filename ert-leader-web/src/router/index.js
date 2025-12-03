import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  {
    path: '/',
    name: 'Home',
    component: () => import('../views/List.vue'),
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/Login.vue'),
  },
  {
    path: '/assessment/:id',
    name: 'Assessment',
    component: () => import('../views/Assessment.vue'),
    props: true,
  },
  {
    path: '/assessment/result/:id',
    name: 'Result',
    component: () => import('../views/Result.vue'),
    props: true,
  },
]

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes,
})

export default router
