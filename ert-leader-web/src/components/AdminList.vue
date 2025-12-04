<template>
  <div>
    <div style="margin-bottom: 12px; display: flex; gap: 8px; align-items: center">
      <a-input-search
        v-if="showSearch"
        placeholder="搜索"
        style="width: 240px"
        @search="onSearch"
      />
      <div style="margin-left: auto">
        <slot name="actions"></slot>
      </div>
    </div>

    <div>
      <div v-if="loading" style="text-align: center; padding: 24px">加载中…</div>
      <div v-else>
        <div class="responsive-table">
          <a-table :columns="columns" :data-source="data" :pagination="false" :row-key="rowKey">
            <template v-if="$slots.bodyCell" #bodyCell="slotProps">
              <slot name="bodyCell" v-bind="slotProps"></slot>
            </template>
          </a-table>
        </div>

        <div style="margin-top: 12px; text-align: right">
          <a-pagination :current="page" :page-size="pageSize" :total="total" @change="changePage" />
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, watch, onMounted, onUnmounted } from 'vue'

const props = defineProps({
  fetchData: { type: Function, required: true },
  columns: { type: Array, required: true },
  rowKey: { type: String, default: 'id' },
  showSearch: { type: Boolean, default: true },
})

const data = ref([])
const loading = ref(false)
const page = ref(1)
const pageSize = ref(10)
const total = ref(0)
const search = ref('')

async function load() {
  loading.value = true
  try {
    const res = await props.fetchData(page.value, pageSize.value, search.value)
    // 支持 PageResult { total, records } 或数组
    if (res && res.records) {
      data.value = res.records
      total.value = res.total || res.records.length
    } else if (Array.isArray(res)) {
      data.value = res
      total.value = res.length
    } else {
      data.value = []
      total.value = 0
    }
  } catch (err) {
    console.error('AdminList load error', err)
    data.value = []
    total.value = 0
  } finally {
    loading.value = false
  }
}

function changePage(p) {
  page.value = p
  load()
}

function onSearch(val) {
  search.value = val
  page.value = 1
  load()
}

onMounted(() => {
  load()
  // 监听全局刷新事件，允许外部模块触发局部刷新
  window.addEventListener('admin-refresh', load)
})

onUnmounted(() => {
  window.removeEventListener('admin-refresh', load)
})

watch([page, pageSize], () => load())
</script>

<style scoped>
.responsive-table {
  overflow-x: auto;
}
</style>
