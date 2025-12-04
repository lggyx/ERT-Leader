<template>
  <div class="container" style="padding: 24px 0">
    <a-card title="领导力画像（27项）">
      <admin-list :fetchData="fetchList" :columns="columns" row-key="id">
        <template #bodyCell="{ record, column }">
          <span v-if="column.key === 'actions'">
            <a-button type="link" @click="openEdit(record)">编辑</a-button>
          </span>
        </template>
      </admin-list>
    </a-card>

    <a-modal v-model:open="modalVisible" title="编辑画像描述" @ok="save">
      <a-form :model="form">
        <a-form-item label="ID">
          <a-input v-model:value="form.id" disabled />
        </a-form-item>
        <a-form-item label="描述">
          <a-textarea v-model:value="form.description" rows="4" />
        </a-form-item>
      </a-form>
    </a-modal>
  </div>
</template>

<script setup>
defineOptions({ name: 'AdminPortraits' })
import { ref } from 'vue'
import AdminList from '../../components/AdminList.vue'
import { admin } from '../../api'

const columns = [
  { title: 'ID', dataIndex: 'id', key: 'id' },
  { title: '名称', dataIndex: 'name', key: 'name' },
  { title: '描述', dataIndex: 'description', key: 'description' },
  { title: '操作', key: 'actions' },
]

const modalVisible = ref(false)
const form = ref({ id: null, description: '' })

async function fetchList() {
  return await admin.portraitList()
}

function openEdit(record) {
  form.value = { id: record.id, description: record.description }
  modalVisible.value = true
}

async function save() {
  if (!form.value.id) return
  await admin.updatePortrait(form.value.id, form.value.description)
  modalVisible.value = false
  window.dispatchEvent(new CustomEvent('admin-refresh'))
}
</script>

<style scoped></style>
