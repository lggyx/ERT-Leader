<template>
  <div class="container" style="padding: 24px 0">
    <a-card title="三大能力维度">
      <admin-list :fetchData="fetchDimensions" :columns="columns" row-key="code">
        <template #bodyCell="{ record, column }">
          <span v-if="column.key === 'actions'">
            <a-button type="link" @click="openEdit(record)">编辑</a-button>
          </span>
        </template>
      </admin-list>
    </a-card>

    <a-modal v-model:open="modalVisible" title="编辑维度" @ok="save">
      <a-form :model="form">
        <a-form-item label="代码">
          <a-input v-model:value="form.code" disabled />
        </a-form-item>
        <a-form-item label="名称">
          <a-input v-model:value="form.name" />
        </a-form-item>
        <a-form-item label="描述">
          <a-textarea v-model:value="form.description" rows="4" />
        </a-form-item>
      </a-form>
    </a-modal>
  </div>
</template>

<script setup>
defineOptions({ name: 'AdminDimensions' })
import { ref } from 'vue'
import AdminList from '../../components/AdminList.vue'
import { admin } from '../../api'

const columns = [
  { title: '代码', dataIndex: 'code', key: 'code' },
  { title: '名称', dataIndex: 'name', key: 'name' },
  { title: '描述', dataIndex: 'description', key: 'description' },
  { title: '操作', key: 'actions' },
]

const modalVisible = ref(false)
const form = ref({ code: '', name: '', description: '' })

async function fetchDimensions() {
  return await admin.dimensionList()
}

function openEdit(record) {
  form.value = { code: record.code, name: record.name, description: record.description }
  modalVisible.value = true
}

async function save() {
  if (!form.value.code) return
  await admin.updateDimension(form.value.code, form.value.name, form.value.description)
  modalVisible.value = false
  window.dispatchEvent(new CustomEvent('admin-refresh'))
}
</script>

<style scoped></style>
