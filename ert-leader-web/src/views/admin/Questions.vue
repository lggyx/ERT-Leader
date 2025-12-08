<template>
  <div class="container" style="padding: 24px 0">
    <a-card title="题目管理">
      <admin-list
        ref="adminListRef"
        :fetch-data="fetchQuestions"
        :columns="columns"
        row-key="id"
      >
        <template #actions>
          <a-space>
            <a-input
              v-model:value="searchParams.keyword"
              placeholder="按题干搜索"
              @pressEnter="handleSearch"
            />
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
            <a-button type="primary" @click="openModal()">新增题目</a-button>
          </a-space>
        </template>
        <template #bodyCell="{ record, column }">
          <span v-if="column.key === 'actions'">
            <a-button type="link" @click="openModal(record)">编辑</a-button>
            <a-popconfirm
              title="确定删除这道题目吗？"
              ok-text="确定"
              cancel-text="取消"
              @confirm="handleDelete(record)"
            >
              <a-button type="link" danger>删除</a-button>
            </a-popconfirm>
          </span>
          <span v-if="column.key === 'content'">
            <a-tooltip :title="record.content">
              {{
                record.content.length > 50
                  ? `${record.content.substring(0, 50)}...`
                  : record.content
              }}
            </a-tooltip>
          </span>
        </template>
      </admin-list>
    </a-card>

    <a-modal
      v-model:open="modalVisible"
      :title="isEdit ? '编辑题目' : '新增题目'"
      :confirm-loading="modalLoading"
      @ok="handleSave"
      @cancel="resetForm"
    >
      <a-form ref="formRef" :model="formState" :rules="formRules" layout="vertical">
        <a-form-item label="所属维度/子维度" name="subDimCode">
          <a-cascader
            v-model:value="formState.subDimCode"
            :options="dimensionOptions"
            placeholder="请选择题目所属的子维度"
          />
        </a-form-item>
        <a-form-item label="题干内容" name="content">
          <a-textarea v-model:value="formState.content" :rows="4" />
        </a-form-item>
        <a-form-item label="显示序号" name="seq">
          <a-input-number v-model:value="formState.seq" style="width: 100%" />
        </a-form-item>
      </a-form>
    </a-modal>
  </div>
</template>

<script setup>
defineOptions({ name: 'AdminQuestions' })
import { ref, reactive, onMounted } from 'vue'
import { Form, message } from 'ant-design-vue'
import AdminList from '../../components/AdminList.vue'
import { admin } from '../../api'

const useForm = Form.useForm

const adminListRef = ref(null)
const searchParams = reactive({ keyword: '', subDimCode: undefined })

// 表格列定义
const columns = [
  { title: 'ID', dataIndex: 'id', key: 'id', width: 80 },
  { title: '题干', dataIndex: 'content', key: 'content', ellipsis: true },
  { title: '所属维度', dataIndex: 'dimensionName', key: 'dimensionName', width: 150 },
  { title: '子维度', dataIndex: 'subDimName', key: 'subDimName', width: 150 },
  { title: '序号', dataIndex: 'seq', key: 'seq', width: 80 },
  { title: '操作', key: 'actions', fixed: 'right', width: 150 },
]

// 模态框与表单相关
const modalVisible = ref(false)
const modalLoading = ref(false)
const isEdit = ref(false)
const editingId = ref(null)
const formRef = ref()

const formState = reactive({
  subDimCode: [],
  content: '',
  seq: 1,
})
const formRules = {
  subDimCode: [{ required: true, message: '请选择子维度' }],
  content: [{ required: true, message: '请输入题干内容' }],
  seq: [{ required: true, message: '请输入序号' }],
}

const { resetFields, validate } = useForm(formState, formRules)

// 维度/子维度数据
const dimensionOptions = ref([])
const allSubDimensions = ref([])

// 获取并处理维度数据用于表单和筛选
async function loadDimensions() {
  try {
    const dims = await admin.dimensionList()
    const options = []
    const subDims = []
    for (const dim of dims) {
      const children = await admin.subDimensionList(dim.code)
      subDims.push(...children)
      options.push({
        value: dim.code,
        label: dim.name,
        children: children.map((sub) => ({
          value: sub.code,
          label: sub.name,
        })),
      })
    }
    dimensionOptions.value = options
    allSubDimensions.value = subDims
  } catch (error) {
    message.error('加载维度数据失败')
  }
}

// 获取题目列表
async function fetchQuestions(page = 1, pageSize = 10) {
  return await admin.questionPage(
    page,
    pageSize,
    searchParams.subDimCode,
    searchParams.keyword,
  )
}

// 搜索与重置
function handleSearch() {
  adminListRef.value?.search()
}
function resetSearch() {
  searchParams.keyword = ''
  searchParams.subDimCode = undefined
  handleSearch()
}

// 打开模态框 (新增/编辑)
function openModal(record) {
  if (record) {
    isEdit.value = true
    editingId.value = record.id
    // Ant Design Cascader 需要一个包含父节点路径的数组
    formState.subDimCode = [record.dimensionCode, record.subDimCode]
    formState.content = record.content
    formState.seq = record.seq
  } else {
    isEdit.value = false
    editingId.value = null
  }
  modalVisible.value = true
}

// 保存 (创建/更新)
async function handleSave() {
  try {
    await validate()
    modalLoading.value = true
    const payload = {
      subDimCode: formState.subDimCode[1], // 取数组最后一个作为子维度代码
      content: formState.content,
      seq: formState.seq,
    }
    if (isEdit.value) {
      await admin.updateQuestion(editingId.value, payload)
      message.success('更新成功')
    } else {
      await admin.createQuestion(payload.subDimCode, payload.content, payload.seq)
      message.success('创建成功')
    }
    modalVisible.value = false
    adminListRef.value?.refresh()
  } catch (error) {
    message.error(`操作失败: ${error.message || '请检查输入'}`)
  } finally {
    modalLoading.value = false
  }
}

// 重置表单
function resetForm() {
  resetFields()
}

// 删除
async function handleDelete(record) {
  try {
    await admin.deleteQuestion(record.id)
    message.success('删除成功')
    adminListRef.value?.refresh()
  } catch (error) {
    message.error('删除失败')
  }
}

// 组件挂载时加载初始数据
onMounted(loadDimensions)
</script>

<style scoped></style>
