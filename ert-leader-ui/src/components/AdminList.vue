<template>
  <div>
    <div
      style="
        margin-bottom: 16px;
        display: flex;
        justify-content: space-between;
        align-items: center;
      "
    >
      <slot name="actions"></slot>
    </div>

    <a-table
      :columns="columns"
      :data-source="dataSource"
      :pagination="showPagination ? pagination : false"
      :row-key="rowKey"
      :loading="loading"
      @change="handleTableChange"
    >
      <template v-if="$slots.bodyCell" #bodyCell="slotProps">
        <slot name="bodyCell" v-bind="slotProps"></slot>
      </template>
    </a-table>
  </div>
</template>

<script setup>
import { ref, onMounted, defineProps, defineExpose, reactive } from 'vue'

const props = defineProps({
  fetchData: {
    type: Function,
    required: true,
  },
  columns: {
    type: Array,
    required: true,
  },
  rowKey: {
    type: String,
    default: 'id',
  },
  showPagination: {
    type: Boolean,
    default: true,
  },
})

const dataSource = ref([])
const loading = ref(false)
const pagination = reactive({
  current: 1,
  pageSize: 10,
  total: 0,
  showSizeChanger: true,
  pageSizeOptions: ['10', '20', '50'],
})

// 加载数据
async function loadData() {
  loading.value = true
  try {
    const res = await props.fetchData(
      pagination.current,
      pagination.pageSize,
    )
    // 优先处理分页对象
    if (res && typeof res.records !== 'undefined') {
      dataSource.value = res.records
      pagination.total = res.total
    }
    // 其次处理纯数组
    else if (Array.isArray(res)) {
      dataSource.value = res
      pagination.total = res.length
    }
    // 处理异常情况
    else {
      console.warn('AdminList: fetchData did not return a valid array or PageResult object.')
      dataSource.value = []
      pagination.total = 0
    }
  } catch (error) {
    console.error('AdminList: Failed to fetch data.', error)
    dataSource.value = []
    pagination.total = 0
  } finally {
    loading.value = false
  }
}

// 处理表格变化（仅在显示分页时有效）
function handleTableChange(pager) {
  if (!props.showPagination) return
  pagination.current = pager.current
  pagination.pageSize = pager.pageSize
  loadData()
}

function refresh() {
  loadData()
}

function search() {
  if (props.showPagination) {
    pagination.current = 1
  }
  loadData()
}

defineExpose({
  refresh,
  search,
})

onMounted(() => {
  loadData()
})
</script>
