<template>
  <div class="container" style="padding: 24px 0">
    <a-card title="测评记录管理">
      <div style="margin-bottom: 12px; display: flex; gap: 8px; align-items: center">
        <a-input-search placeholder="用户 ID 或关键字" style="width: 240px" @search="onSearch" />
        <div style="margin-left: auto"></div>
      </div>

      <admin-list :fetchData="fetchList" :columns="columns" row-key="id">
        <template #bodyCell="{ record, column }">
          <span v-if="column.key === 'actions'">
            <a-button type="link" @click="viewResult(record)">查看结果</a-button>
          </span>
        </template>
      </admin-list>
    </a-card>
  </div>
</template>

<script setup>
defineOptions({ name: 'AdminAssessments' })
import { ref } from 'vue'
import AdminList from '../../components/AdminList.vue'
import { admin } from '../../api'
import { useRouter } from 'vue-router'

const router = useRouter()
const keyword = ref('')

const columns = [
  { title: 'ID', dataIndex: 'id', key: 'id' },
  { title: '用户', dataIndex: 'userId', key: 'userId' },
  { title: '状态', dataIndex: 'status', key: 'status' },
  { title: '更新时间', dataIndex: 'updateAt', key: 'updateAt' },
  { title: '操作', key: 'actions' },
]

async function fetchList(page = 1, pageSize = 10) {
  return await admin.assessmentPage(page, pageSize, undefined, keyword.value)
}

function onSearch(val) {
  keyword.value = val
  window.dispatchEvent(new CustomEvent('admin-refresh'))
}

function viewResult(record) {
  router.push({ path: `/assessment/result/${record.id}` })
}
</script>

<style scoped></style>
