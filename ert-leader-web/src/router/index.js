import { createRouter, createWebHistory } from 'vue-router'
import { auth } from '../api'

const routes = [
  {
    path: '/',
    name: 'Home',
    component: () => import('../views/List.vue'),
  },
  {
    path: '/admin',
    name: 'Admin',
    component: () => import('../views/admin/AdminDashboard.vue'),
    children: [
      { path: 'users', name: 'AdminUsers', component: () => import('../views/admin/Users.vue') },
      {
        path: 'questions',
        name: 'AdminQuestions',
        component: () => import('../views/admin/Questions.vue'),
      },
      {
        path: 'dimensions',
        name: 'AdminDimensions',
        component: () => import('../views/admin/Dimensions.vue'),
      },
      {
        path: 'sub-dimensions',
        name: 'AdminSubDimensions',
        component: () => import('../views/admin/SubDimensions.vue'),
      },
      {
        path: 'ert-score-desc',
        name: 'AdminErtScoreDesc',
        component: () => import('../views/admin/ErtScoreDesc.vue'),
      },
      {
        path: 'sub-score-action',
        name: 'AdminSubScoreAction',
        component: () => import('../views/admin/SubScoreAction.vue'),
      },
      {
        path: 'portrait',
        name: 'AdminPortrait',
        component: () => import('../views/admin/Portraits.vue'),
      },
      {
        path: 'contact',
        name: 'AdminContact',
        component: () => import('../views/admin/Contact.vue'),
      },
      {
        path: 'assessments',
        name: 'AdminAssessments',
        component: () => import('../views/admin/Assessments.vue'),
      },
    ],
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

// 管理员路由守卫：访问 /admin/* 需要管理员权限
router.beforeEach(async (to, from, next) => {
  try {
    if (to.path.startsWith('/admin')) {
      const token = localStorage.getItem('TOKEN')
      if (!token) {
        return next({ path: '/login', query: { redirect: to.fullPath } })
      }
      // 请求后端获取当前用户信息并验证角色
      const user = await auth.current()
      const roles = user?.role ? [user.role] : user?.roles || []
      if (Array.isArray(roles) && roles.includes('ADMIN')) return next()
      return next({ path: '/' })
    }
  } catch {
    // 任何异常视为未认证，跳转登录
    return next({ path: '/login', query: { redirect: to.fullPath } })
  }
  return next()
})

export default router
