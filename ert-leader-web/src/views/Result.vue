<template>
  <div class="container" style="padding: 24px 0">
    <a-card title="测评结果">
      <div v-if="loading">加载结果中…</div>
      <div v-else-if="!result" style="color: #ff4d4f">
        无结果数据 (调试: {{ JSON.stringify(result) }})
      </div>
      <div v-else>
        <!-- 基础信息 -->
        <a-descriptions bordered title="基础信息" size="small" style="margin-bottom: 24px">
          <a-descriptions-item label="测评ID">{{ result.assessmentId }}</a-descriptions-item>
          <a-descriptions-item label="用户名">{{ result.userName }}</a-descriptions-item>
          <a-descriptions-item label="测评类型">{{
            result.type === 'QUICK' ? '极速测评(12题)' : '完整测评(36题)'
          }}</a-descriptions-item>
          <a-descriptions-item label="完成时间">{{
            formatDate(result.completedAt)
          }}</a-descriptions-item>
        </a-descriptions>

        <!-- 三大维度得分 -->
        <a-divider />
        <h3>三大维度得分</h3>
        <div class="dimension-card-row" style="margin-bottom: 24px">
          <a-card class="dimension-card" title="E 维度">
            <div class="dim-body">
              <div class="dim-score dim-score-e">{{ result.dimensionScores?.e?.score || '—' }}</div>
              <div class="dim-level">级别：{{ result.dimensionScores?.e?.level || '—' }}</div>
              <div class="dim-desc">{{ result.dimensionScores?.e?.description || '' }}</div>
            </div>
          </a-card>

          <a-card class="dimension-card" title="R 维度">
            <div class="dim-body">
              <div class="dim-score dim-score-r">{{ result.dimensionScores?.r?.score || '—' }}</div>
              <div class="dim-level">级别：{{ result.dimensionScores?.r?.level || '—' }}</div>
              <div class="dim-desc">{{ result.dimensionScores?.r?.description || '' }}</div>
            </div>
          </a-card>

          <a-card class="dimension-card" title="T 维度">
            <div class="dim-body">
              <div class="dim-score dim-score-t">{{ result.dimensionScores?.t?.score || '—' }}</div>
              <div class="dim-level">级别：{{ result.dimensionScores?.t?.level || '—' }}</div>
              <div class="dim-desc">{{ result.dimensionScores?.t?.description || '' }}</div>
            </div>
          </a-card>
        </div>

        <!-- 领导力画像 -->
        <a-divider />
        <h3>领导力画像定位</h3>
        <a-card style="margin-bottom: 24px">
          <div style="font-size: 16px; font-weight: bold; color: #1890ff">
            {{ result.portrait?.description || '未定位' }}
          </div>
          <div style="margin-top: 12px; line-height: 1.8; color: #666">
            {{ result.portrait?.description || '暂无画像描述' }}
          </div>
        </a-card>

        <!-- 子维度得分详情 -->
        <a-divider />
        <h3>子维度能力详情（12项能力分析）</h3>
        <a-table
          v-if="result.subDimensionScores && result.subDimensionScores.length > 0"
          :columns="subDimensionColumns"
          :data-source="result.subDimensionScores"
          :pagination="false"
          size="small"
          rowKey="code"
          style="margin-bottom: 24px"
        />
        <div v-else style="color: #999; text-align: center">暂无子维度数据</div>

        <!-- 行动方案 -->
        <a-divider />
        <h3>专属行动方案</h3>
        <a-collapse
          v-if="result.subDimensionScores && result.subDimensionScores.length > 0"
          accordion
        >
          <a-collapse-panel
            v-for="item in result.subDimensionScores"
            :key="item.code"
            :header="`${item.code} - ${item.name}`"
          >
            <div style="white-space: pre-wrap; line-height: 1.8">
              {{ item.actionPlan || '暂无行动方案' }}
            </div>
          </a-collapse-panel>
        </a-collapse>
        <div v-else style="color: #999; text-align: center">暂无行动方案</div>
      </div>
    </a-card>
  </div>
</template>

<script setup>
defineOptions({ name: 'ResultView' })
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { assessment } from '../api'

const route = useRoute()
const id = route.params.id
const loading = ref(true)
const result = ref(null)

const subDimensionColumns = [
  {
    title: '子维度代码',
    dataIndex: 'code',
    key: 'code',
    width: 100,
  },
  {
    title: '能力名称',
    dataIndex: 'name',
    key: 'name',
  },
  {
    title: '得分',
    dataIndex: 'score',
    key: 'score',
    width: 80,
    align: 'center',
  },
  {
    title: '所属维度',
    dataIndex: 'dimension',
    key: 'dimension',
    width: 100,
  },
]

function formatDate(dateString) {
  if (!dateString) return '—'
  try {
    const date = new Date(dateString)
    return date.toLocaleString('zh-CN')
  } catch {
    return dateString
  }
}

async function load() {
  loading.value = true
  try {
    const res = await assessment.result(id)
    console.log('[Result] API Response:', res)
    // 响应拦截器已处理，返回 ResultVO
    result.value = res
    console.log('[Result] Loaded result:', result.value)
  } catch (err) {
    console.error('[Result] Error loading result:', err)
  } finally {
    loading.value = false
  }
}

onMounted(load)
</script>

<style scoped>
h3 {
  margin-top: 24px;
  margin-bottom: 12px;
  font-size: 16px;
  font-weight: 600;
}

/* 三大维度卡片样式 */
.dimension-card-row {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 16px;
}
.dimension-card {
  min-height: 200px;
  display: flex;
  align-items: center;
  justify-content: center;
}
.dim-body {
  text-align: center;
  width: 100%;
}
.dim-score {
  font-size: 32px;
  font-weight: 700;
}
.dim-score-e {
  color: #1890ff;
}
.dim-score-r {
  color: #52c41a;
}
.dim-score-t {
  color: #fa541c;
}
.dim-level {
  color: #666;
  margin-top: 8px;
}
.dim-desc {
  color: #999;
  font-size: 12px;
  margin-top: 8px;
  line-height: 1.4;
  max-height: 3.6em; /* limit lines */
  overflow: hidden;
}

@media (max-width: 768px) {
  .dimension-card-row {
    grid-template-columns: 1fr;
  }
  .dimension-card {
    min-height: 160px;
  }
  .dim-score {
    font-size: 28px;
  }
  .dim-desc {
    font-size: 13px;
    max-height: 4.2em;
    padding: 0 8px;
  }
}
</style>
