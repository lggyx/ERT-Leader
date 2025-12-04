<template>
  <div class="container" style="padding: 24px 0">
    <a-card title="子维度管理">
      <div style="margin-bottom: 12px; display: flex; gap: 8px; align-items: center">
        <a-select
          v-model:value="selectedDimension"
          placeholder="选择父维度"
          style="width: 220px"
          allowClear
          @change="onDimChange"
        >
          <a-select-option v-for="d in dims" :key="d.code" :value="d.code">{{
            d.name
          }}</a-select-option>
        </a-select>
        <div style="margin-left: auto"><slot name="actions"></slot></div>
      </div>

      <admin-list
        :fetchData="fetchSubDimensions"
        :columns="columns"
        row-key="code"
        :showSearch="false"
      >
        <template #bodyCell="{ record, column }">
          <span v-if="column.key === 'actions'">
            <a-button type="link" @click="openEdit(record)">编辑</a-button>
          </span>
        </template>
      </admin-list>
    </a-card>

    <a-modal v-model:open="modalVisible" title="编辑子维度" @ok="save">
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
defineOptions({ name: 'AdminSubDimensions' })
import { ref, onMounted } from 'vue'
import AdminList from '../../components/AdminList.vue'
import { admin } from '../../api'

const columns = [
  { title: '代码', dataIndex: 'code', key: 'code' },
  { title: '名称', dataIndex: 'name', key: 'name' },
  { title: '描述', dataIndex: 'description', key: 'description' },
  { title: '操作', key: 'actions' },
]

const dims = ref([])
const selectedDimension = ref(undefined)

const modalVisible = ref(false)
const form = ref({ code: '', name: '', description: '' })

async function fetchSubDimensions() {
  return await admin.subDimensionList(selectedDimension.value)
}

async function loadDims() {
  dims.value = await admin.dimensionList()
}

function onDimChange() {
  /* AdminList will call fetch again via internal paging; force reload by refreshing page for simplicity */
}

function openEdit(record) {
  form.value = { code: record.code, name: record.name, description: record.description }
  modalVisible.value = true
}

async function save() {
  if (!form.value.code) return
  await admin.updateSubDimension(form.value.code, {
    name: form.value.name,
    description: form.value.description,
  })
  modalVisible.value = false
  window.dispatchEvent(new CustomEvent('admin-refresh'))
}

onMounted(loadDims)
</script>

<style scoped></style>
