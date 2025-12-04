<template>
  <div class="container" style="padding: 24px 0">
    <a-card title="用户管理">
      <admin-list :fetchData="fetchUsers" :columns="columns" row-key="id">
        <template #actions>
          <a-button type="primary" @click="refresh">刷新</a-button>
        </template>
        <template #bodyCell="{ record, column }">
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
import { ref } from 'vue'
import AdminList from '../../components/AdminList.vue'
import { admin } from '../../api'

const columns = [
  { title: 'ID', dataIndex: 'id', key: 'id' },
  { title: '用户名', dataIndex: 'name', key: 'name' },
  { title: '角色', dataIndex: 'role', key: 'role' },
  { title: '状态', dataIndex: 'status', key: 'status' },
  { title: '操作', key: 'actions' },
]

const roleVisible = ref(false)
const selectedUser = ref(null)
const selectedRole = ref(null)

async function fetchUsers(page = 1, pageSize = 10, keyword = '') {
  return await admin.userPage(page, pageSize, undefined, undefined, keyword)
}

function refresh() {
  window.dispatchEvent(new CustomEvent('admin-refresh'))
}

function toggleStatus(record) {
  const newStatus = record.status === 'ENABLED' ? 'DISABLED' : 'ENABLED'
  admin
    .updateUserStatus(record.id, newStatus)
    .then(() => {
      window.alert('更新成功')
      window.dispatchEvent(new CustomEvent('admin-refresh'))
    })
    .catch(() => window.alert('更新失败'))
}

function editRole(record) {
  selectedUser.value = record
  selectedRole.value = record.role || 'USER'
  roleVisible.value = true
}

async function saveRole() {
  if (!selectedUser.value) return
  await admin.updateUserRole(selectedUser.value.id, selectedRole.value)
  roleVisible.value = false
  window.alert('设置成功')
  window.dispatchEvent(new CustomEvent('admin-refresh'))
}
</script>

<style scoped></style>
