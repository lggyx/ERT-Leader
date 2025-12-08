<template>
  <div class="container" style="padding: 24px 0">
    <a-card title="测评记录管理">
      <admin-list
        ref="adminListRef"
        :fetch-data="fetchList"
        :columns="columns"
        row-key="id"
      >
        <template #actions>
          <a-space>
            <a-input
              v-model:value="searchParams.userId"
              placeholder="按用户ID搜索"
              @pressEnter="handleSearch"
            />
            <a-select
              v-model:value="searchParams.status"
              placeholder="按状态筛选"
              style="width: 150px"
              allow-clear
              @change="handleSearch"
            >
              <a-select-option value="INIT">初始</a-select-option>
              <a-select-option value="IN_PROGRESS">进行中</a-select-option>
              <a-select-option value="COMPLETED">已完成</a-select-option>
              <a-select-option value="CANCELLED">已取消</a-select-option>
            </a-select>
            <a-button type="primary" @click="handleSearch">搜索</a-button>
            <a-button @click="resetSearch">重置</a-button>
          </a-space>
        </template>
        <template #bodyCell="{ record, column }">
          <span v-if="column.key === 'status'">
            <a-tag :color="getStatusColor(record.status)">{{
              getStatusText(record.status)
            }}</a-tag>
          </span>
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
import { ref, reactive } from 'vue'
import AdminList from '../../components/AdminList.vue'
import { admin } from '../../api'
import { useRouter } from 'vue-router'
import { Tag } from 'ant-design-vue'

const router = useRouter()
const adminListRef = ref(null)

const searchParams = reactive({
  userId: undefined,
  status: undefined,
})

const columns = [
  { title: 'ID', dataIndex: 'id', key: 'id', width: 80 },
  { title: '用户ID', dataIndex: 'userId', key: 'userId', width: 120, ellipsis: true },
  { title: '类型', dataIndex: 'type', key: 'type', width: 100 },
  { title: '状态', dataIndex: 'status', key: 'status', width: 100 },
  { title: '创建时间', dataIndex: 'createdAt', key: 'createdAt', width: 180 },
  { title: '更新时间', dataIndex: 'updateAt', key: 'updateAt', width: 180 },
  { title: '操作', key: 'actions', fixed: 'right', width: 100 },
]

async function fetchList(page = 1, pageSize = 10) {
  return await admin.assessmentPage(
    page,
    pageSize,
    searchParams.status,
    searchParams.userId,
  )
}

function handleSearch() {
  adminListRef.value?.search()
}

function resetSearch() {
  searchParams.userId = undefined
  searchParams.status = undefined
  handleSearch()
}

function getStatusText(status) {
  switch (status) {
    case 'INIT':
      return '初始'
    case 'IN_PROGRESS':
      return '进行中'
    case 'COMPLETED':
      return '已完成'
    case 'CANCELLED':
      return '已取消'
    default:
      return status
  }
}

function getStatusColor(status) {
  switch (status) {
    case 'INIT':
      return 'blue'
    case 'IN_PROGRESS':
      return 'orange'
    case 'COMPLETED':
      return 'green'
    case 'CANCELLED':
      return 'red'
    default:
      return 'default'
  }
}

function viewResult(record) {
  router.push({ path: `/result/${record.id}` })
}
</script>

<style scoped></style>