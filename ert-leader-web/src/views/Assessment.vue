<template>
  <div class="container" style="padding: 24px 0">
    <a-card :title="`测评 ${assessmentId}`">
      <div v-if="loading">加载题目中…</div>
      <div v-else-if="!questions.length">当前没有题目</div>

      <div v-else>
        <div style="margin-bottom: 12px">进度：{{ currentIndex + 1 }} / {{ questions.length }}</div>
        <a-card v-if="currentQuestion && currentQuestion.content">
          <div style="font-weight: 600">{{ currentQuestion.content }}</div>
          <div
            v-if="!currentQuestion.options || currentQuestion.options.length === 0"
            style="color: #ff4d4f; margin-top: 12px"
          >
            暂无选项数据 (调试: {{ JSON.stringify(currentQuestion) }})
          </div>
          <a-radio-group v-else v-model:value="selectedOption">
            <a-space direction="vertical" style="margin-top: 12px">
              <a-radio
                v-for="opt in currentQuestion.options"
                :key="opt.optionId"
                :value="opt.optionId"
              >
                {{ opt.label }}
              </a-radio>
            </a-space>
          </a-radio-group>
        </a-card>
        <a-card v-else style="color: #ff4d4f">
          题目加载异常：{{ JSON.stringify(currentQuestion) }}
        </a-card>

        <div style="margin-top: 16px">
          <a-button @click="prev" :disabled="currentIndex === 0">上一步</a-button>
          <a-button type="primary" style="margin-left: 8px" @click="submitAnswer"
            >提交并下一题</a-button
          >
          <a-button style="margin-left: 8px" @click="doComplete">完成测评</a-button>
        </div>
      </div>
    </a-card>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
defineOptions({ name: 'AssessmentPage' })
import { assessment } from '../api'

const route = useRoute()
const router = useRouter()
const assessmentId = route.params.id

const questions = ref([])
const loading = ref(true)
const currentIndex = ref(0)
const selectedOption = ref(null)

const currentQuestion = computed(() => questions.value[currentIndex.value] || {})

async function loadQuestions() {
  loading.value = true
  try {
    const res = await assessment.getQuestions(assessmentId)
    console.log('[Assessment] Raw response:', res)
    // 响应拦截器已处理，直接返回 GetQuestionsVO[] 数组或 { records: [...] }
    if (Array.isArray(res)) {
      questions.value = res
      console.log('[Assessment] Loaded questions (array):', res)
    } else if (res && res.records) {
      questions.value = res.records
      console.log('[Assessment] Loaded questions (records):', res.records)
    } else if (res && Array.isArray(res.data)) {
      questions.value = res.data
      console.log('[Assessment] Loaded questions (data array):', res.data)
    } else {
      questions.value = []
      console.warn('[Assessment] Unexpected response format:', res)
    }
    console.log('[Assessment] Final questions:', questions.value)
    if (questions.value.length > 0) {
      console.log('[Assessment] First question:', questions.value[0])
    }
  } catch (err) {
    console.error('[Assessment] Error loading questions:', err)
  } finally {
    loading.value = false
  }
}

async function submitAnswer() {
  if (!selectedOption.value) {
    window.alert('请选择一个选项')
    return
  }
  try {
    console.log('[Assessment] Submitting answer:', {
      assessmentId,
      questionId: currentQuestion.value.questionId,
      optionId: selectedOption.value,
    })
    // 使用新的 API 签名：answer(assessmentId, questionId, optionId)
    await assessment.answer(assessmentId, currentQuestion.value.questionId, selectedOption.value)
    console.log('[Assessment] Answer submitted successfully')
    // 清空选中，跳到下一题
    selectedOption.value = null
    if (currentIndex.value < questions.value.length - 1) {
      currentIndex.value++
    } else {
      window.alert('已提交到最后一题，可点击完成测评')
    }
  } catch (err) {
    console.error('[Assessment] Error submitting answer:', err)
    window.alert('提交答案失败：' + (err.response?.data?.msg || err.message))
  }
}

function prev() {
  if (currentIndex.value > 0) currentIndex.value--
}

async function doComplete() {
  try {
    await assessment.complete(assessmentId)
    router.push(`/assessment/result/${assessmentId}`)
  } catch (err) {
    console.error(err)
    window.alert('完成测评失败')
  }
}

onMounted(loadQuestions)
</script>
