# 选项不显示问题诊断指南

## 问题现象

答题页面（Assessment.vue）中，题目内容能显示，但选项（Options）没有出现。

## 快速诊断步骤

### 1. 打开浏览器开发工具

按 **F12** 打开浏览器开发工具 → **Console** 标签

### 2. 查看加载日志

在测评页面加载时，应该看到以下日志：

```
[Assessment] Raw response: [...]
[Assessment] Loaded questions (array): [...]
[Assessment] Final questions: [...]
[Assessment] First question: { questionId, subDimCode, content, options: [...], ... }
```

### 3. 检查选项数据结构

查看 `[Assessment] First question` 中是否包含 `options` 字段。

**正确的结构**：

```javascript
{
  questionId: 1,
  subDimCode: "E1",
  subDimName: "能力1",
  content: "你的问题是什么？",
  seq: 1,
  options: [
    { optionId: 1, label: "选项A", score: 10, seq: 1 },
    { optionId: 2, label: "选项B", score: 20, seq: 2 },
    { optionId: 3, label: "选项C", score: 30, seq: 3 }
  ]
}
```

**错误的结构**（缺少 options）：

```javascript
{
  questionId: 1,
  content: "你的问题是什么？",
  // ❌ 缺少 options 字段
}
```

## 常见原因与解决方案

### 原因1：后端未返回 options 字段

**症状**：Console 日志显示 `options: undefined` 或 `options: []`

**解决**：

1. 检查后端接口 `GET /api/assessment/{id}/questions` 是否正确关联选项
2. 确认后端返回的 GetQuestionsVO 包含完整的 options 数组
3. 查看后端 SQL 是否 JOIN 了 option 表

**后端示例（Spring Boot）**：

```java
@PostMapping("/{assessmentId}/questions")
public Result<List<GetQuestionsVO>> getQuestions(@PathVariable Long assessmentId) {
    List<Question> questions = questionService.findByAssessmentId(assessmentId);
    return questions.stream()
        .map(q -> new GetQuestionsVO(
            q.getId(),
            q.getSubDimCode(),
            q.getContent(),
            q.getOptions()  // ✅ 确保包含选项
        ))
        .collect(Collectors.toList());
}
```

### 原因2：响应拦截器处理错误

**症状**：Console 显示 `Raw response` 为空或格式异常

**检查**：在 `src/api/index.js` 中，响应拦截器是否正确提取了数据

```javascript
instance.interceptors.response.use(
  (res) => {
    console.log('[API Response]', res.data)
    const result = res.data
    // ✅ 确保返回的是数组或包含 records 的对象
    if (result && (result.code === 200 || result.code === 0)) {
      return result.data || result
    }
    return result
  },
  ...
)
```

**如果后端返回格式为**：

```json
{
  "code": 200,
  "msg": "success",
  "data": [
    { "questionId": 1, "options": [...] },
    { "questionId": 2, "options": [...] }
  ]
}
```

则拦截器应该返回 `result.data`（数组）。

### 原因3：前端 v-for 绑定错误

**症状**：显示 "暂无选项数据"，且 JSON.stringify(currentQuestion) 显示 options 存在

**可能原因**：

- `optionId` 字段名不匹配（后端可能是 `id`、`optId` 等）
- `label` 字段名不匹配（后端可能是 `name`、`content` 等）

**解决**：根据后端实际返回字段名修改模板：

```vue
<!-- 如果后端返回的是 { id, name, score } -->
<a-radio
  v-for="opt in currentQuestion.options"
  :key="opt.id"           <!-- 改成实际字段 -->
  :value="opt.id"         <!-- 改成实际字段 -->
>
  {{ opt.name }}          <!-- 改成实际字段 -->
</a-radio>
```

### 原因4：CORS/网络错误

**症状**：Console 显示红色错误信息或网络请求失败

**检查**：

1. Network 标签中是否看到 `POST /api/assessment/{id}/questions` 请求？
2. 响应状态码是多少？(200 / 403 / 404 / 500)
3. 响应体是否为空？

**解决**：

- 状态码 403：CORS 问题，参考 `CORS_GUIDE.md`
- 状态码 404：检查 assessmentId 是否正确
- 状态码 500：后端异常，查看后端日志

## 调试命令

### 在 Console 中手动测试 API

```javascript
// 导入 API
import { assessment } from './api/index.js'

// 测试获取题目（将 123 替换为实际的 assessmentId）
assessment.getQuestions(123).then((res) => {
  console.log('Response:', res)
  console.log('First question:', res[0])
  console.log('First question options:', res[0]?.options)
})
```

### 查看当前题目数据

```javascript
// 在页面中执行
console.log('Current question:', window.__ASSESSMENT_DEBUG__ || 'Not available')
```

## 完整数据流验证

1. **后端输出**：查看后端日志，确认 SQL 查询结果

   ```
   SELECT q.*, o.* FROM question q LEFT JOIN option o ON q.id = o.question_id
   WHERE assessment_id = ?
   ```

2. **API 响应**：在 Network 标签中查看完整 JSON

   ```json
   {
     "code": 200,
     "msg": "success",
     "data": [
       {
         "questionId": 1,
         "content": "...",
         "options": [...]
       }
     ]
   }
   ```

3. **前端处理**：Console 中查看日志

   ```
   [API Response] { code: 200, msg: "success", data: [...] }
   [Assessment] Loaded questions (array): [...]
   [Assessment] First question: { ..., options: [...] }
   ```

4. **页面渲染**：选项应该正常显示

## 快速修复清单

- [ ] 后端确认返回了 options 字段（不是空数组）
- [ ] 检查字段名是否匹配（optionId, label）
- [ ] 刷新页面并清空缓存（Ctrl+Shift+Delete）
- [ ] 检查浏览器 Console 中是否有 JavaScript 错误
- [ ] 确认 CORS 配置正确（参考 CORS_GUIDE.md）
- [ ] 在 Network 标签中验证 API 响应

## 联系后端团队

如仍无法解决，请收集以下信息：

1. **浏览器 Console 输出**（复制 [Assessment] 开头的所有日志）
2. **Network 标签中的 POST 请求响应体**（右键 → Copy → Copy response）
3. **后端返回的实际 JSON 结构**

这样后端团队可以快速诊断问题。
