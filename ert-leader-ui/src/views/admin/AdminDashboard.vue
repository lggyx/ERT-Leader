<template>
  <a-layout style="min-height: 100vh">
    <a-layout-sider v-model:collapsed="collapsed" collapsible>
      <div class="logo">ERT Admin</div>
      <a-menu v-model:selectedKeys="selectedKeys" theme="dark" mode="inline">
        <a-menu-item key="/admin/users">
          <router-link to="/admin/users">
            <user-outlined />
            <span>用户管理</span>
          </router-link>
        </a-menu-item>
        <a-menu-item key="/admin/assessments">
          <router-link to="/admin/assessments">
            <file-search-outlined />
            <span>测评记录</span>
          </router-link>
        </a-menu-item>
        <a-sub-menu key="sub1">
          <template #title>
            <span>
              <setting-outlined />
              <span>内容配置</span>
            </span>
          </template>
          <a-menu-item key="/admin/questions"
            ><router-link to="/admin/questions">题目管理</router-link></a-menu-item
          >
          <a-menu-item key="/admin/dimensions"
            ><router-link to="/admin/dimensions">维度配置</router-link></a-menu-item
          >
          <a-menu-item key="/admin/sub-dimensions"
            ><router-link to="/admin/sub-dimensions">子维度配置</router-link></a-menu-item
          >
          <a-menu-item key="/admin/portrait"
            ><router-link to="/admin/portrait">画像配置</router-link></a-menu-item
          >
        </a-sub-menu>
        <a-sub-menu key="sub2">
          <template #title>
            <span>
              <sliders-outlined />
              <span>得分配置</span>
            </span>
          </template>
          <a-menu-item key="/admin/ert-score-desc"
            ><router-link to="/admin/ert-score-desc">ERT得分区间</router-link></a-menu-item
          >
          <a-menu-item key="/admin/sub-score-action"
            ><router-link to="/admin/sub-score-action">子维度行动方案</router-link></a-menu-item
          >
        </a-sub-menu>
        <a-menu-item key="/admin/contact">
          <router-link to="/admin/contact">
            <phone-outlined />
            <span>系统信息</span>
          </router-link>
        </a-menu-item>
      </a-menu>
    </a-layout-sider>
    <a-layout>
      <a-layout-header style="background: #fff; padding: 0 16px">
        <!-- Can add header content here, like user info dropdown -->
      </a-layout-header>
      <a-layout-content style="margin: 0 16px">
        <a-breadcrumb style="margin: 16px 0">
          <a-breadcrumb-item>Admin</a-breadcrumb-item>
          <a-breadcrumb-item>{{ currentRouteName }}</a-breadcrumb-item>
        </a-breadcrumb>
        <div :style="{ padding: '24px', background: '#fff', minHeight: '360px' }">
          <!-- 子路由组件将在这里渲染 -->
          <router-view />
        </div>
      </a-layout-content>
      <a-layout-footer style="text-align: center">
        ERT Leader Web ©2024 Created by Gemini
      </a-layout-footer>
    </a-layout>
  </a-layout>
</template>

<script setup>
import { ref, watch, computed } from 'vue'
import { useRoute } from 'vue-router'
import {
  UserOutlined,
  SettingOutlined,
  FileSearchOutlined,
  SlidersOutlined,
  PhoneOutlined,
} from '@ant-design/icons-vue'

const route = useRoute()

const collapsed = ref(false)
const selectedKeys = ref(['/admin/users'])

// 监听路由变化，更新菜单选中状态
watch(
  () => route.path,
  (newPath) => {
    selectedKeys.value = [newPath]
  },
  { immediate: true },
)

// 计算当前路由的显示名称
const currentRouteName = computed(() => {
  // 从路由元信息获取或直接使用 name
  return route.meta.title || route.name || 'Dashboard'
})
</script>

<style scoped>
.logo {
  height: 32px;
  margin: 16px;
  background: rgba(255, 255, 255, 0.3);
  color: white;
  text-align: center;
  line-height: 32px;
  font-size: 16px;
  font-weight: bold;
}
</style>
