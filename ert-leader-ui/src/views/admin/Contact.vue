<template>
  <div class="container" style="padding: 24px 0">
    <a-card title="系统联系信息" :loading="loading">
      <a-skeleton v-if="loading" active />
      <a-form
        v-else
        ref="formRef"
        :model="formState"
        :rules="formRules"
        layout="vertical"
        style="max-width: 600px; margin: 0 auto"
      >
        <a-form-item label="公司名称" name="company">
          <a-input v-model:value="formState.company" />
        </a-form-item>
        <a-form-item label="联系电话" name="phone">
          <a-input v-model:value="formState.phone" />
        </a-form-item>
        <a-form-item label="电子邮箱" name="email">
          <a-input v-model:value="formState.email" />
        </a-form-item>
        <a-form-item label="公司地址" name="address">
          <a-textarea v-model:value="formState.address" :rows="3" />
        </a-form-item>
        <a-form-item>
          <a-button type="primary" :loading="saving" @click="handleSave">保存更新</a-button>
        </a-form-item>
      </a-form>
    </a-card>
  </div>
</template>

<script setup>
defineOptions({ name: 'AdminContact' })
import { ref, onMounted, reactive } from 'vue'
import { admin } from '../../api'
import { Form, message } from 'ant-design-vue'

const useForm = Form.useForm

const loading = ref(true)
const saving = ref(false)
const formRef = ref()

const formState = reactive({
  company: '',
  phone: '',
  email: '',
  address: '',
})

const formRules = {
  company: [{ required: true, message: '请输入公司名称' }],
  phone: [{ required: true, message: '请输入联系电话' }],
  email: [
    { required: true, message: '请输入电子邮箱' },
    { type: 'email', message: '请输入有效的邮箱地址' },
  ],
  address: [{ required: true, message: '请输入公司地址' }],
}

const { validate } = useForm(formState, formRules)

async function loadContact() {
  loading.value = true
  try {
    const data = await admin.getContact()
    if (data) {
      formState.company = data.company
      formState.phone = data.phone
      formState.email = data.email
      formState.address = data.address
    }
  } catch (error) {
    message.error('加载联系信息失败')
  } finally {
    loading.value = false
  }
}

async function handleSave() {
  saving.value = true
  try {
    await validate()
    await admin.updateContact(
      formState.company,
      formState.phone,
      formState.email,
      formState.address,
    )
    message.success('保存成功')
  } catch (error) {
    message.error('保存失败，请检查输入')
  } finally {
    saving.value = false
  }
}

onMounted(loadContact)
</script>

<style scoped></style>
