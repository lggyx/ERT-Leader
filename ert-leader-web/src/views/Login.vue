<template>
  <div
    class="container"
    style="
      display: flex;
      justify-content: center;
      align-items: center;
      min-height: 100vh;
      padding: 24px;
    "
  >
    <div style="width: 100%; max-width: 400px">
      <a-card title="登录">
        <a-form :model="form" @submit.prevent="onSubmit">
          <a-form-item label="账号">
            <a-input v-model:value="form.account" placeholder="手机号或邮箱" />
          </a-form-item>
          <a-form-item label="密码">
            <a-input-password v-model:value="form.password" />
          </a-form-item>
          <a-form-item>
            <a-button type="primary" block @click="onSubmit">登录</a-button>
          </a-form-item>
        </a-form>
      </a-card>
    </div>
  </div>
</template>

<script setup>
defineOptions({ name: 'LoginView' })
import { reactive } from 'vue'
import { useRouter } from 'vue-router'
import { auth } from '../api'

const router = useRouter()
const form = reactive({ account: '', password: '' })

async function onSubmit() {
  try {
    console.log('[Login] Submitting:', { account: form.account })
    const result = await auth.login(form.account, form.password)
    console.log('[Login] Response:', result)
    // 响应拦截器已处理，直接返回 data 中的 LoginVO
    const token = result?.token
    if (token) {
      localStorage.setItem('TOKEN', token)
      console.log('[Login] Success, navigating to home')
      router.push('/')
    } else {
      console.error('[Login] No token in response')
      window.alert('登录失败：未获得有效token')
    }
  } catch (err) {
    console.error('[Login] Error:', err)
    const msg = err.response?.data?.msg || err.message || '登录失败，请检查账号或网络'
    window.alert(`登录失败：${msg}`)
  }
}
</script>
