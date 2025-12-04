<template>
  <div class="container" style="padding: 24px 0">
    <a-card title="欢迎使用 ERT 测评">
      <div style="margin-bottom: 16px">
        <a-button type="primary" @click="create('QUICK')">开始极速测评</a-button>
        <a-button style="margin-left: 8px" @click="create('FULL')">开始完整测评</a-button>
        <a-button style="margin-left: 8px" @click="goHistory">我的测评历史</a-button>
        <a-button style="float: right" type="link" @click="goLogin">登录</a-button>
      </div>
      <a-divider />
      <div class="responsive-table">
        <a-table :columns="columns" :data-source="questions" row-key="questionId" />
      </div>

      <a-modal
        :open="historyVisible"
        title="我的测评历史"
        width="800"
        @cancel="historyVisible = false"
        :footer="null"
      >
        <div v-if="historyLoading">加载中…</div>
        <div v-else>
          <a-table
            :columns="historyColumns"
            :data-source="historyList"
            row-key="id"
            :pagination="{ pageSize: 10 }"
          >
            <template #bodyCell="{ record, column }">
              <span v-if="column && column.key === 'action'">
                <a-button type="link" @click="viewHistory(record.id)">查看结果</a-button>
              </span>
            </template>
          </a-table>
        </div>
      </a-modal>
    </a-card>
  </div>
</template>

<script setup>
defineOptions({ name: 'ListPage' })
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { admin, assessment } from '../api'

const router = useRouter()
const questions = ref([])

// 测评历史相关
const historyVisible = ref(false)
const historyList = ref([])
const historyLoading = ref(false)
const historyColumns = [
  { title: '测评ID', dataIndex: 'id', key: 'id' },
  { title: '类型', dataIndex: 'type', key: 'type' },
  { title: '状态', dataIndex: 'status', key: 'status' },
  { title: '完成时间', dataIndex: 'updateAt', key: 'updateAt' },
  { title: '操作', key: 'action' },
]
const columns = [
  { title: 'ID', dataIndex: 'questionId', key: 'questionId' },
  { title: '子维度', dataIndex: 'subDimName', key: 'subDimName' },
  { title: '题干', dataIndex: 'content', key: 'content' },
]

async function load() {
  try {
    const res = await admin.questionPage(1, 10)
    // 响应拦截器已处理，返回 PageResult { total, records }
    if (res && res.records) questions.value = res.records
  } catch (err) {
    console.warn('加载题目失败', err)
    questions.value = []
  }
}

async function create(type) {
  try {
    const res = await assessment.create(type)
    // 响应拦截器已处理，返回 CreateAssessmentVO
    const id = res?.assessmentId
    if (id) router.push(`/assessment/${id}`)
  } catch (err) {
    console.error(err)
    window.alert('创建测评失败')
  }
}

async function goHistory() {
  historyLoading.value = true
  try {
    const res = await assessment.history(1, 100)
    // 如果后端使用 PageResult 返回 { total, records }
    if (res && res.records) historyList.value = res.records
    else if (Array.isArray(res)) historyList.value = res
    else historyList.value = []
    historyVisible.value = true
  } catch (err) {
    console.error('[List] load history failed', err)
    window.alert('加载测评历史失败')
  } finally {
    historyLoading.value = false
  }
}

function viewHistory(id) {
  if (id) router.push(`/assessment/result/${id}`)
  else window.alert('无法跳转：无效测评 ID')
}

function goLogin() {
  router.push('/login')
}

load()
</script>
