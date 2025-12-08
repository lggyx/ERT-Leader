<template>
  <div class="container" style="padding: 24px 0">
    <a-card title="用户管理">
      <admin-list
        ref="adminListRef"
        :fetch-data="fetchUsers"
        :columns="columns"
        row-key="id"
      >
        <template #actions>
          <a-space>
            <a-input
              v-model:value="searchParams.keyword"
              placeholder="按用户名搜索"
              @pressEnter="handleSearch"
            />
            <a-select
              v-model:value="searchParams.status"
              placeholder="按状态筛选"
              style="width: 120px"
              allow-clear
              @change="handleSearch"
            >
              <a-select-option value="ENABLED">启用</a-select-option>
              <a-select-option value="DISABLED">禁用</a-select-option>
            </a-select>
            <a-select
              v-model:value="searchParams.role"
              placeholder="按角色筛选"
              style="width: 120px"
              allow-clear
              @change="handleSearch"
            >
              <a-select-option value="ADMIN">管理员</a-select-option>
              <a-select-option value="USER">用户</a-select-option>
            </a-select>
            <a-button type="primary" @click="handleSearch">搜索</a-button>
            <a-button @click="resetSearch">重置</a-button>
          </a-space>
        </template>
        <template #bodyCell="{ record, column }">
          <span v-if="column.key === 'role'">
            <a-tag :color="record.role === 'ADMIN' ? 'gold' : 'blue'">{{
              record.role === 'ADMIN' ? '管理员' : '用户'
            }}</a-tag>
          </span>
          <span v-if="column.key === 'status'">
            <a-tag :color="record.status === 'ENABLED' ? 'green' : 'red'">{{
              record.status === 'ENABLED' ? '启用' : '禁用'
            }}</a-tag>
          </span>
          <span v-if="column.key === 'actions'">
            <a-button type="link" @click="toggleStatus(record)">{{
              record.status === 'ENABLED' ? '禁用' : '启用'
            }}</a-button>
            <a-button type="link" @click="editRole(record)">设置角色</a-button>
          </span>
        </template>
      </admin-list>
    </a-card>

    <a-modal v-model:open="roleVisible" title="设置角色" @ok="saveRole">
      <a-select v-model:value="selectedRole" style="width: 100%">
        <a-select-option value="ADMIN">管理员</a-select-option>
        <a-select-option value="USER">用户</a-select-option>
      </a-select>
    </a-modal>
  </div>
</template>

<script setup>
defineOptions({ name: 'AdminUsers' })
import { ref, reactive } from 'vue'
import { message } from 'ant-design-vue'
import AdminList from '../../components/AdminList.vue'
import { admin } from '../../api'

const adminListRef = ref(null)

const searchParams = reactive({
  keyword: '',
  status: undefined,
  role: undefined,
})

const columns = [
  { title: 'ID', dataIndex: 'id', key: 'id', width: 80 },
  { title: '用户名', dataIndex: 'name', key: 'name' },
  { title: '邮箱', dataIndex: 'email', key: 'email' },
  { title: '角色', dataIndex: 'role', key: 'role', align: 'center' },
  { title: '状态', dataIndex: 'status', key: 'status', align: 'center' },
  { title: '创建时间', dataIndex: 'createdAt', key: 'createdAt' },
  { title: '操作', key: 'actions', fixed: 'right', width: 200 },
]

const roleVisible = ref(false)
const selectedUser = ref(null)
const selectedRole = ref(null)

// 改造 fetchData 函数以接收动态参数
async function fetchUsers(page = 1, pageSize = 10) {
  return await admin.userPage(
    page,
    pageSize,
    searchParams.status,
    searchParams.role,
    searchParams.keyword,
  )
}

// 触发子组件刷新
function refresh() {
  if (adminListRef.value) {
    adminListRef.value.refresh()
  }
}

function handleSearch() {
  if (adminListRef.value) {
    adminListRef.value.search() // search 会重置页码到第一页
  }
}

function resetSearch() {
  searchParams.keyword = ''
  searchParams.status = undefined
  searchParams.role = undefined
  handleSearch()
}

async function toggleStatus(record) {
  try {
    const newStatus = record.status === 'ENABLED' ? 'DISABLED' : 'ENABLED'
    await admin.updateUserStatus(record.id, newStatus)
    message.success('更新成功')
    refresh()
  } catch (error) {
    message.error('更新失败')
  }
}

function editRole(record) {
  selectedUser.value = record
  selectedRole.value = record.role || 'USER'
  roleVisible.value = true
}

async function saveRole() {
  if (!selectedUser.value) return
  try {
    await admin.updateUserRole(selectedUser.value.id, selectedRole.value)
    roleVisible.value = false
    message.success('设置成功')
    refresh()
  } catch (error) {
    message.error('设置失败')
  }
}
</script>

<style scoped></style>
