import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import Antd from 'ant-design-vue'
import axios from 'axios'
import './styles/global.css'

const app = createApp(App)

// 全局注册 Ant Design Vue
app.use(Antd)
app.use(router)

// axios 默认实例
const apiBase = import.meta.env.VITE_API_BASE || 'http://localhost:8912'
const axiosInstance = axios.create({ baseURL: apiBase })

// 简单的请求拦截器：自动带上 token
axiosInstance.interceptors.request.use((config) => {
  const token = localStorage.getItem('TOKEN')
  if (token) {
    config.headers = config.headers || {}
    config.headers.Authorization = `Bearer ${token}`
  }
  return config
})

app.config.globalProperties.$axios = axiosInstance

app.mount('#app')
