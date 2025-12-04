<template>
  <div class="container" style="padding: 24px 0">
    <a-card title="ERT 得分区间">
      <admin-list :fetchData="fetchList" :columns="columns" row-key="id">
        <template #bodyCell="{ record, column }">
          <span v-if="column.key === 'actions'">
            <a-button type="link" @click="openEdit(record)">编辑</a-button>
          </span>
        </template>
      </admin-list>
    </a-card>

    <a-modal v-model:open="modalVisible" title="编辑得分区间" @ok="save">
      <a-form :model="form">
        <a-form-item label="最小分">
          <a-input-number v-model:value="form.minScore" style="width: 100%" />
        </a-form-item>
        <a-form-item label="最大分">
          <a-input-number v-model:value="form.maxScore" style="width: 100%" />
        </a-form-item>
        <a-form-item label="描述">
          <a-textarea v-model:value="form.description" rows="3" />
        </a-form-item>
      </a-form>
    </a-modal>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import AdminList from '../../components/AdminList.vue'
import { admin } from '../../api'

const columns = [
  { title: 'ID', dataIndex: 'id', key: 'id' },
  { title: '最小分', dataIndex: 'minScore', key: 'minScore' },
  { title: '最大分', dataIndex: 'maxScore', key: 'maxScore' },
  { title: '描述', dataIndex: 'description', key: 'description' },
  { title: '操作', key: 'actions' },
]

const modalVisible = ref(false)
const form = ref({ id: null, minScore: 0, maxScore: 0, description: '' })

async function fetchList() {
  return await admin.ertScoreDescList()
}

function openEdit(record) {
  form.value = {
    id: record.id,
    minScore: record.minScore,
    maxScore: record.maxScore,
    description: record.description,
  }
  modalVisible.value = true
}

async function save() {
  if (!form.value.id) return
  await admin.updateErtScoreDesc(
    form.value.id,
    form.value.minScore,
    form.value.maxScore,
    form.value.description,
  )
  modalVisible.value = false
  window.dispatchEvent(new CustomEvent('admin-refresh'))
}
</script>

<style scoped></style>
