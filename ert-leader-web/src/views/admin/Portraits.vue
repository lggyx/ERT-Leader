<template>
  <div class="container" style="padding: 24px 0">
    <a-card title="领导力画像（27项）">
      <admin-list
        ref="adminListRef"
        :fetch-data="fetchList"
        :columns="columns"
        row-key="id"
        :show-pagination="false"
      >
        <template #actions>
          <a-button type="primary" @click="adminListRef?.refresh()">刷新</a-button>
        </template>
        <template #bodyCell="{ record, column }">
          <span v-if="column.key === 'actions'">
            <a-button type="link" @click="openModal(record)">编辑</a-button>
          </span>
        </template>
      </admin-list>
    </a-card>

    <a-modal
      v-model:open="modalVisible"
      title="编辑画像描述"
      :confirm-loading="modalLoading"
      @ok="handleSave"
    >
      <a-form ref="formRef" :model="formState" :rules="formRules" layout="vertical">
        <a-form-item label="画像名称">
          <a-input v-model:value="formState.name" disabled />
        </a-form-item>
        <a-form-item label="描述" name="description">
          <a-textarea v-model:value="formState.description" :rows="6" />
        </a-form-item>
      </a-form>
    </a-modal>
  </div>
</template>

<script setup>
defineOptions({ name: 'AdminPortraits' })
import { ref, reactive } from 'vue'
import { Form, message } from 'ant-design-vue'
import AdminList from '../../components/AdminList.vue'
import { admin } from '../../api'

const useForm = Form.useForm
const adminListRef = ref(null)

const columns = [
  { title: 'ID', dataIndex: 'id', key: 'id', width: 80 },
  { title: '名称', dataIndex: 'name', key: 'name', width: 200 },
  { title: '描述', dataIndex: 'description', key: 'description', ellipsis: true },
  { title: '操作', key: 'actions', fixed: 'right', width: 100 },
]

// 模态框与表单
const modalVisible = ref(false)
const modalLoading = ref(false)
const editingId = ref(null)

const formState = reactive({
  name: '',
  description: '',
})
const formRules = {
  description: [{ required: true, message: '请输入描述内容' }],
}
const { resetFields, validate } = useForm(formState, formRules)

// 获取列表 (API 返回的是全量数组)
async function fetchList() {
  return await admin.portraitList()
}

// 打开编辑模态框
function openModal(record) {
  editingId.value = record.id
  formState.name = record.name
  formState.description = record.description
  modalVisible.value = true
}

// 保存
async function handleSave() {
  try {
    await validate()
    modalLoading.value = true
    await admin.updatePortrait(editingId.value, formState.description)
    message.success('更新成功')
    modalVisible.value = false
    adminListRef.value?.refresh()
  } catch (error) {
    message.error('更新失败')
  } finally {
    modalLoading.value = false
  }
}
</script>

<style scoped></style>
