<template>
  <div class="container" style="padding: 24px 0">
    <a-card title="题目管理">
      <admin-list :fetchData="fetchQuestions" :columns="columns" row-key="id">
        <template #actions>
          <a-button type="primary" @click="openCreate">新增题目</a-button>
        </template>
        <template #bodyCell="{ record, column }">
          <span v-if="column.key === 'actions'">
            <a-button type="link" @click="openEdit(record)">编辑</a-button>
            <a-button type="link" @click="del(record)">删除</a-button>
          </span>
        </template>
      </admin-list>
    </a-card>

    <a-modal v-model:open="modalVisible" :title="modalTitle" @ok="save">
      <a-form :model="form">
        <a-form-item label="子维度代码">
          <a-input v-model:value="form.subDimCode" />
        </a-form-item>
        <a-form-item label="题干" required>
          <a-textarea v-model:value="form.content" rows="4" />
        </a-form-item>
        <a-form-item label="序号">
          <a-input-number v-model:value="form.seq" style="width: 100%" />
        </a-form-item>
      </a-form>
    </a-modal>
  </div>
</template>

<script setup>
defineOptions({ name: 'AdminQuestions' })
import { ref } from 'vue'
import AdminList from '../../components/AdminList.vue'
import { admin } from '../../api'

const columns = [
  { title: 'ID', dataIndex: 'id', key: 'id' },
  { title: '子维度', dataIndex: 'subDimName', key: 'subDimName' },
  { title: '题干', dataIndex: 'content', key: 'content' },
  { title: '操作', key: 'actions' },
]

const modalVisible = ref(false)
const modalTitle = ref('')
const form = ref({ subDimCode: '', content: '', seq: 1, id: null })

async function fetchQuestions(page = 1, pageSize = 10, keyword = '') {
  return await admin.questionPage(page, pageSize, undefined, keyword)
}

function openCreate() {
  modalTitle.value = '新增题目'
  form.value = { subDimCode: '', content: '', seq: 1, id: null }
  modalVisible.value = true
}
function openEdit(record) {
  modalTitle.value = '编辑题目'
  form.value = {
    subDimCode: record.subDimCode,
    content: record.content,
    seq: record.seq,
    id: record.id,
  }
  modalVisible.value = true
}

async function save() {
  if (!form.value.content) {
    window.alert('题干不能为空')
    return
  }
  if (form.value.id) {
    await admin.updateQuestion(form.value.id, {
      subDimCode: form.value.subDimCode,
      content: form.value.content,
      seq: form.value.seq,
    })
    window.alert('更新成功')
  } else {
    await admin.createQuestion(form.value.subDimCode, form.value.content, form.value.seq)
    window.alert('创建成功')
  }
  modalVisible.value = false
  window.dispatchEvent(new CustomEvent('admin-refresh'))
}

async function del(record) {
  if (!confirm('确认删除？')) return
  await admin.deleteQuestion(record.id)
  window.alert('删除成功')
  window.dispatchEvent(new CustomEvent('admin-refresh'))
}
</script>

<style scoped></style>
