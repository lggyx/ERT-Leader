<template>
  <div class="container" style="padding: 24px 0">
    <a-card title="管理面板">
      <a-row :gutter="16">
        <a-col :span="6"
          ><a-button type="link" @click="go('/admin/users')">用户管理</a-button></a-col
        >
        <a-col :span="6"
          ><a-button type="link" @click="go('/admin/questions')">题目管理</a-button></a-col
        >
        <a-col :span="6"
          ><a-button type="link" @click="go('/admin/dimensions')">维度配置</a-button></a-col
        >
        <a-col :span="6"
          ><a-button type="link" @click="go('/admin/sub-dimensions')">子维度配置</a-button></a-col
        >
        <a-col :span="6"
          ><a-button type="link" @click="go('/admin/ert-score-desc')">ERT得分区间</a-button></a-col
        >
        <a-col :span="6"
          ><a-button type="link" @click="go('/admin/sub-score-action')"
            >子维度行动方案</a-button
          ></a-col
        >
        <a-col :span="6"
          ><a-button type="link" @click="go('/admin/portrait')">画像配置</a-button></a-col
        >
        <a-col :span="6"
          ><a-button type="link" @click="go('/admin/contact')">系统配置</a-button></a-col
        >
        <a-col :span="6"
          ><a-button type="link" @click="go('/admin/assessments')">所有测评记录</a-button></a-col
        >
      </a-row>
    </a-card>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router'
const router = useRouter()
function go(path) {
  router.push(path)
}
</script>

<style scoped></style>
