<template>
  <div class="container" style="padding: 24px 0">
    <a-card title="子维度行动方案">
      <div style="margin-bottom: 12px; display: flex; gap: 8px; align-items: center">
        <a-input
          v-model:value="subDimCode"
          placeholder="子维度代码过滤（可选）"
          style="width: 240px"
        />
        <a-button @click="reload" style="margin-left: 8px">刷新</a-button>
      </div>

      <admin-list :fetchData="fetchList" :columns="columns" row-key="id" :showSearch="false">
        <template #bodyCell="{ record, column }">
          <span v-if="column.key === 'actions'">
            <a-button type="link" @click="openEdit(record)">编辑</a-button>
          </span>
        </template>
      </admin-list>
    </a-card>

    <a-modal v-model:open="modalVisible" title="编辑行动方案" @ok="save">
      <a-form :model="form">
        <a-form-item label="子维度代码">
          <a-input v-model:value="form.subDimCode" disabled />
        </a-form-item>
        <a-form-item label="最小分">
          <a-input-number v-model:value="form.minScore" style="width: 100%" />
        </a-form-item>
        <a-form-item label="最大分">
          <a-input-number v-model:value="form.maxScore" style="width: 100%" />
        </a-form-item>
        <a-form-item label="方案">
          <a-textarea v-model:value="form.actionPlan" rows="4" />
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
  { title: '子维度', dataIndex: 'subDimCode', key: 'subDimCode' },
  { title: '最小分', dataIndex: 'minScore', key: 'minScore' },
  { title: '最大分', dataIndex: 'maxScore', key: 'maxScore' },
  { title: '方案', dataIndex: 'actionPlan', key: 'actionPlan' },
  { title: '操作', key: 'actions' },
]

const subDimCode = ref('')
const modalVisible = ref(false)
const form = ref({ id: null, subDimCode: '', minScore: 0, maxScore: 0, actionPlan: '' })

async function fetchList() {
  return await admin.subScoreActionList(subDimCode.value)
}

function reload() {
  window.dispatchEvent(new CustomEvent('admin-refresh'))
}

function openEdit(record) {
  form.value = {
    id: record.id,
    subDimCode: record.subDimCode,
    minScore: record.minScore,
    maxScore: record.maxScore,
    actionPlan: record.actionPlan,
  }
  modalVisible.value = true
}

async function save() {
  if (!form.value.id) return
  await admin.updateSubScoreAction(
    form.value.id,
    form.value.minScore,
    form.value.maxScore,
    form.value.actionPlan,
  )
  modalVisible.value = false
  window.dispatchEvent(new CustomEvent('admin-refresh'))
}
</script>

<style scoped></style>
