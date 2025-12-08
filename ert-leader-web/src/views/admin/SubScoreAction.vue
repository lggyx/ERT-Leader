<template>
  <div class="container" style="padding: 24px 0">
    <a-card title="子维度行动方案">
      <admin-list
        ref="adminListRef"
        :fetch-data="fetchList"
        :columns="columns"
        row-key="id"
        :show-pagination="false"
      >
        <template #actions>
          <a-space>
            <a-select
              v-model:value="searchParams.subDimCode"
              placeholder="按子维度筛选"
              style="width: 200px"
              allow-clear
              show-search
              :options="allSubDimensions"
              :field-names="{ label: 'name', value: 'code' }"
              @change="handleSearch"
            />
            <a-button type="primary" @click="handleSearch">搜索</a-button>
            <a-button @click="resetSearch">重置</a-button>
          </a-space>
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
      title="编辑行动方案"
      :confirm-loading="modalLoading"
      @ok="handleSave"
    >
      <a-form ref="formRef" :model="formState" :rules="formRules" layout="vertical">
        <a-form-item label="子维度" name="subDimCode">
          <a-select
            v-model:value="formState.subDimCode"
            placeholder="请选择子维度"
            style="width: 100%"
            :options="allSubDimensions"
            :field-names="{ label: 'name', value: 'code' }"
            disabled
          />
        </a-form-item>
        <a-form-item label="最小分" name="minScore">
          <a-input-number v-model:value="formState.minScore" style="width: 100%" />
        </a-form-item>
        <a-form-item label="最大分" name="maxScore">
          <a-input-number v-model:value="formState.maxScore" style="width: 100%" />
        </a-form-item>
        <a-form-item label="方案" name="actionPlan">
          <a-textarea v-model:value="formState.actionPlan" :rows="4" />
        </a-form-item>
      </a-form>
    </a-modal>
  </div>
</template>

<script setup>
defineOptions({ name: 'AdminSubScoreAction' })
import { ref, reactive, onMounted } from 'vue'
import { Form, message } from 'ant-design-vue'
import AdminList from '../../components/AdminList.vue'
import { admin } from '../../api'

const useForm = Form.useForm

const adminListRef = ref(null)
const searchParams = reactive({ subDimCode: undefined })

const columns = [
  { title: 'ID', dataIndex: 'id', key: 'id', width: 80 },
  { title: '子维度', dataIndex: 'subDimName', key: 'subDimName', width: 150 },
  { title: '最小分', dataIndex: 'minScore', key: 'minScore', width: 100 },
  { title: '最大分', dataIndex: 'maxScore', key: 'maxScore', width: 100 },
  { title: '方案', dataIndex: 'actionPlan', key: 'actionPlan', ellipsis: true },
  { title: '操作', key: 'actions', fixed: 'right', width: 100 },
]

const modalVisible = ref(false)
const modalLoading = ref(false)
const editingId = ref(null)

const formState = reactive({
  subDimCode: '',
  minScore: 0,
  maxScore: 0,
  actionPlan: '',
})
const formRules = {
  subDimCode: [{ required: true, message: '请选择子维度' }],
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
  actionPlan: [{ required: true, message: '请输入行动方案' }],
}
const { validate } = useForm(formState, formRules)

const allSubDimensions = ref([])

async function loadSubDimensions() {
  try {
    const dims = await admin.dimensionList()
    const subDimsPromises = dims.map((dim) => admin.subDimensionList(dim.code))
    const allSubDims = await Promise.all(subDimsPromises)
    allSubDimensions.value = allSubDims.flat()
  } catch (error) {
    message.error('加载子维度数据失败')
  }
}

async function fetchList() {
  return await admin.subScoreActionList(searchParams.subDimCode)
}

function handleSearch() {
  adminListRef.value?.refresh()
}

function resetSearch() {
  searchParams.subDimCode = undefined
  handleSearch()
}

function openModal(record) {
  editingId.value = record.id
  formState.subDimCode = record.subDimCode
  formState.minScore = record.minScore
  formState.maxScore = record.maxScore
  formState.actionPlan = record.actionPlan
  modalVisible.value = true
}

async function handleSave() {
  try {
    await validate()
    modalLoading.value = true
    await admin.updateSubScoreAction(
      editingId.value,
      formState.minScore,
      formState.maxScore,
      formState.actionPlan,
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

onMounted(loadSubDimensions)
</script>

<style scoped></style>
