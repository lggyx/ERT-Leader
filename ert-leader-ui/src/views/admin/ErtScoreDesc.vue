<template>
  <div class="container" style="padding: 24px 0">
    <a-card title="ERT 得分区间">
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
      title="编辑得分区间"
      :confirm-loading="modalLoading"
      @ok="handleSave"
    >
      <a-form ref="formRef" :model="formState" :rules="formRules" layout="vertical">
        <a-form-item label="最小分" name="minScore">
          <a-input-number v-model:value="formState.minScore" style="width: 100%" />
        </a-form-item>
        <a-form-item label="最大分" name="maxScore">
          <a-input-number v-model:value="formState.maxScore" style="width: 100%" />
        </a-form-item>
        <a-form-item label="描述" name="description">
          <a-textarea v-model:value="formState.description" :rows="3" />
        </a-form-item>
      </a-form>
    </a-modal>
  </div>
</template>

<script setup>
defineOptions({ name: 'AdminErtScoreDesc' })
import { ref, reactive } from 'vue'
import { Form, message } from 'ant-design-vue'
import AdminList from '../../components/AdminList.vue'
import { admin } from '../../api'

const useForm = Form.useForm
const adminListRef = ref(null)

const columns = [
  { title: 'ID', dataIndex: 'id', key: 'id', width: 80 },
  { title: '最小分', dataIndex: 'minScore', key: 'minScore', width: 100 },
  { title: '最大分', dataIndex: 'maxScore', key: 'maxScore', width: 100 },
  { title: '描述', dataIndex: 'description', key: 'description', ellipsis: true },
  { title: '操作', key: 'actions', fixed: 'right', width: 100 },
]

const modalVisible = ref(false)
const modalLoading = ref(false)
const editingId = ref(null)

const formState = reactive({ id: null, minScore: 0, maxScore: 0, description: '' })

const formRules = {
  minScore: [{ required: true, type: 'number', message: '请输入最小分' }],
  maxScore: [
    { required: true, type: 'number', message: '请输入最大分' },
    {
      validator: async (_rule, value) => {
        if (value && value <= formState.minScore) {
          return Promise.reject('最大分必须大于最小分')
        }
        return Promise.resolve()
      },
    },
  ],
  description: [{ required: true, message: '请输入描述' }],
}
const { validate } = useForm(formState, formRules)

async function fetchList() {
  return await admin.ertScoreDescList()
}

function openModal(record) {
  editingId.value = record.id
  formState.minScore = record.minScore
  formState.maxScore = record.maxScore
  formState.description = record.description
  modalVisible.value = true
}

async function handleSave() {
  try {
    await validate()
    modalLoading.value = true
    await admin.updateErtScoreDesc(
      editingId.value,
      formState.minScore,
      formState.maxScore,
      formState.description,
    )
    message.success('更新成功')
    modalVisible.value = false
    adminListRef.value?.refresh()
  } catch (error) {
    message.error(`更新失败: ${error.message || '请检查输入'}`)
  } finally {
    modalLoading.value = false
  }
}
</script>

<style scoped></style>
