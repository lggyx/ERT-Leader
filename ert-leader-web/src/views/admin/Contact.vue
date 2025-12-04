<template>
  <div class="container" style="padding: 24px 0">
    <a-card title="系统联系信息">
      <a-form :model="form" layout="vertical">
        <a-form-item label="公司">
          <a-input v-model:value="form.company" />
        </a-form-item>
        <a-form-item label="电话">
          <a-input v-model:value="form.phone" />
        </a-form-item>
        <a-form-item label="邮箱">
          <a-input v-model:value="form.email" />
        </a-form-item>
        <a-form-item label="地址">
          <a-textarea v-model:value="form.address" rows="3" />
        </a-form-item>
        <a-form-item>
          <a-button type="primary" @click="save">保存</a-button>
        </a-form-item>
      </a-form>
    </a-card>
  </div>
</template>

<script setup>
defineOptions({ name: 'AdminContact' })
import { ref, onMounted } from 'vue'
import { admin } from '../../api'

const form = ref({ company: '', phone: '', email: '', address: '' })

async function load() {
  const res = await admin.getContact()
  if (res)
    form.value = {
      company: res.company || '',
      phone: res.phone || '',
      email: res.email || '',
      address: res.address || '',
    }
}

async function save() {
  await admin.updateContact(
    form.value.company,
    form.value.phone,
    form.value.email,
    form.value.address,
  )
  window.alert('保存成功')
}

onMounted(load)
</script>

<style scoped></style>
