<template>
  <div class="container" style="padding: 24px 0">
    <a-card title="子维度管理">
      <div style="margin-bottom: 16px; display: flex; gap: 8px; align-items: center">
        <a-select
          v-model:value="selectedDimension"
          placeholder="选择父维度"
          style="width: 220px"
          allow-clear
          @change="handleDimensionChange"
          :options="dims"
          :field-names="{ label: 'name', value: 'code' }"
        />
        <div style="margin-left: auto">
          <a-button type="primary" @click="adminListRef?.refresh()">刷新</a-button>
        </div>
      </div>

      <admin-list
        ref="adminListRef"
        :fetch-data="fetchSubDimensions"
        :columns="columns"
        row-key="code"
        :show-pagination="false"
      >
        <template #bodyCell="{ record, column }">
          <span v-if="column.key === 'actions'">
            <a-button type="link" @click="openModal(record)">编辑</a-button>
          </span>
        </template>
      </admin-list>
    </a-card>

    <a-modal
      v-model:open="modalVisible"
      title="编辑子维度"
      :confirm-loading="modalLoading"
      @ok="handleSave"
    >
      <a-form ref="formRef" :model="formState" :rules="formRules" layout="vertical">
        <a-form-item label="代码">
          <a-input v-model:value="formState.code" disabled />
        </a-form-item>
        <a-form-item label="名称" name="name">
          <a-input v-model:value="formState.name" />
        </a-form-item>
        <a-form-item label="描述" name="description">
          <a-textarea v-model:value="formState.description" :rows="4" />
        </a-form-item>
      </a-form>
    </a-modal>
  </div>
</template>

<script setup>
defineOptions({ name: 'AdminSubDimensions' })
import { ref, onMounted, reactive } from 'vue'
import { Form, message } from 'ant-design-vue'
import AdminList from '../../components/AdminList.vue'
import { admin } from '../../api'

const useForm = Form.useForm

const adminListRef = ref(null)

const columns = [
  { title: '代码', dataIndex: 'code', key: 'code', width: 150 },
  { title: '名称', dataIndex: 'name', key: 'name', width: 200 },
  { title: '描述', dataIndex: 'description', key: 'description', ellipsis: true },
  { title: '操作', key: 'actions', fixed: 'right', width: 100 },
]

const dims = ref([])
const selectedDimension = ref(undefined)

const modalVisible = ref(false)
const modalLoading = ref(false)

const formState = reactive({ code: '', name: '', description: '' })
const formRules = {
  name: [{ required: true, message: '请输入子维度名称' }],
  description: [{ required: true, message: '请输入子维度描述' }],
}
const { validate } = useForm(formState, formRules)

async function fetchSubDimensions() {
  if (!selectedDimension.value) {
    return []
  }
  return await admin.subDimensionList(selectedDimension.value)
}

async function loadDimensions() {
  try {
    dims.value = await admin.dimensionList()
    // 默认选中第一个维度（如果存在）
    if (dims.value.length > 0) {
      selectedDimension.value = dims.value[0].code
    }
  } catch (error) {
    message.error('加载维度列表失败')
  }
}

function handleDimensionChange() {
  adminListRef.value?.refresh()
}

function openModal(record) {
  formState.code = record.code
  formState.name = record.name
  formState.description = record.description
  modalVisible.value = true
}

async function handleSave() {
  try {
    await validate()
    modalLoading.value = true
    await admin.updateSubDimension(formState.code, {
      name: formState.name,
      description: formState.description,
    })
    message.success('更新成功')
    modalVisible.value = false
    adminListRef.value?.refresh()
  } catch (error) {
    message.error('更新失败')
  } finally {
    modalLoading.value = false
  }
}

onMounted(loadDimensions)
</script>

<style scoped></style>
