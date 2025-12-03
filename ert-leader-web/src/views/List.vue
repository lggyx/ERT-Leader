<template>
  <a-row gutter="16" style="padding: 24px">
    <a-col :span="18">
      <a-card title="欢迎使用 ERT 测评">
        <div style="margin-bottom: 16px">
          <a-button type="primary" @click="create('QUICK')">开始极速测评</a-button>
          <a-button style="margin-left: 8px" @click="create('FULL')">开始完整测评</a-button>
          <a-button style="margin-left: 8px" @click="goHistory">我的测评历史</a-button>
        </div>
        <a-divider />
        <a-table :columns="columns" :data-source="questions" row-key="questionId" />
      </a-card>
    </a-col>
  </a-row>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { admin, assessment } from '../api'

const router = useRouter()
const questions = ref([])

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
    console.warn('加载题目失败')
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

function goHistory() {
  router.push('/login')
}

load()
</script>
