# ERT 领导力评测系统 - 前端项目

## 项目概述

基于 **Vue 3 + JavaScript + Vue Router + Ant Design Vue** 开发的领导力测评系统前端应用。支持用户登录、参加测评（极速/完整）、查看测评结果等功能。管理员可管理用户、题目、维度、画像等配置。

## 技术栈

- **Vue 3** (`^3.5.22`) - 前端框架
- **Vite** (`^7.2.4`) - 构建工具
- **Vue Router** (`^4.6.3`) - 路由管理
- **Ant Design Vue** (`^4.2.6`) - UI 组件库
- **Axios** (`^1.5.0`) - HTTP 客户端
- **JavaScript (ES6+)** - 编程语言

## 快速开始

### 1. 安装依赖

```bash
npm install
```

### 2. 启动开发服务器

```bash
npm run dev
```

默认运行在 `http://localhost:8913`。

### 3. 构建生产版本

```bash
npm run build
```

### 4. 代码格式化与检查

```bash
npm run lint
npm run format
```

## 项目结构

```
src/
├── main.js              # 应用入口、Ant Design 配置、Axios 初始化
├── App.vue              # 根组件
├── router/
│   └── index.js         # 路由配置
├── api/
│   └── index.js         # API 封装层（完全按 OpenAPI 参数格式）
├── views/
│   ├── Login.vue        # 登录页
│   ├── List.vue         # 首页（题目列表 + 测评入口）
│   ├── Assessment.vue   # 答题页
│   └── Result.vue       # 结果页
└── index.html           # HTML 入口
```

## API 接口调用方式

### 认证模块 (auth)

```javascript
import { auth } from '@/api'

// 登录 - POST /api/auth/login (JSON body)
await auth.login('account', 'password')
// 返回: { token, expiresIn, userInfo }

// 注册 - POST /api/auth/register (JSON body)
await auth.register({ mobile, email, password, name, gender, birthDate })

// 获取当前用户 - GET /api/auth/current
await auth.current()
```

### 测评模块 (assessment)

```javascript
import { assessment } from '@/api'

// 创建测评 - POST /api/assessment/create (JSON body)
await assessment.create('QUICK') // 'QUICK' 或 'FULL'

// 获取题目 - POST /api/assessment/{id}/questions (无 body)
await assessment.getQuestions(assessmentId)

// 提交答案 - POST /api/assessment/{id}/answer (JSON body)
await assessment.answer(assessmentId, questionId, optionId)

// 完成测评 - POST /api/assessment/{id}/complete (无 body)
await assessment.complete(assessmentId)

// 获取结果 - GET /api/assessment/{id}/result
await assessment.result(assessmentId)

// 查询历史 - GET /api/assessment/history (Query params)
await assessment.history(page, pageSize)
```

### 管理员模块 (admin)

```javascript
import { admin } from '@/api'

// 用户管理
admin.userPage(page, pageSize, status?, role?, keyword?)
admin.updateUserStatus(userId, status)
admin.updateUserRole(userId, role)

// 题目管理
admin.questionPage(page, pageSize, subDimCode?, keyword?)
admin.createQuestion(subDimCode, content, seq)
admin.updateQuestion(questionId, payload)
admin.deleteQuestion(questionId)

// 维度管理
admin.dimensionList()
admin.subDimensionList(dimensionCode)
admin.portraitList()
admin.ertScoreDescList()

// 系统配置
admin.getContact()
admin.updateContact(company, phone, email, address)
```

## API 请求参数规范

根据 OpenAPI 文档，API 参数传递方式已全部修正：

- **JSON Body 请求**：`POST /api/auth/login { account, password }`
- **Query 参数请求**：`GET /api/admin/user/page?page=1&pageSize=10`
- **路径参数请求**：`PUT /api/admin/user/{userId}/status?status=1`

## 常见问题

### 样式加载问题

Ant Design CSS 通过 CDN 在 `index.html` 中引入：

```html
<link rel="stylesheet" href="https://unpkg.com/ant-design-vue@4.2.6/dist/antd.css" />
```

### Token 管理

登录后自动保存到 `localStorage['TOKEN']`，所有请求自动附加 `Authorization: Bearer {token}` 请求头。

### API 基地址配置

创建 `.env` 文件配置：

```env
VITE_API_BASE=http://localhost:8912
```

## 开发流程

1. **添加 API 方法**：在 `src/api/index.js` 中按 OpenAPI 参数格式添加
2. **创建页面**：在 `src/views/` 中创建 `.vue` 文件
3. **配置路由**：在 `src/router/index.js` 中添加路由
4. **页面开发**：使用 Ant Design Vue 组件、调用 API、处理数据

## 后续开发方向

- [ ] 用户管理页（分页表格、启用/禁用）
- [ ] 题目管理页（CRUD）
- [ ] 维度/画像配置页
- [ ] 历史记录查看与导出
- [ ] 响应式移动端适配
- [ ] 单元测试 & E2E 测试

## 许可证

MIT

## Recommended IDE Setup

[VS Code](https://code.visualstudio.com/) + [Vue (Official)](https://marketplace.visualstudio.com/items?itemName=Vue.volar) (and disable Vetur).

## Recommended Browser Setup

- Chromium-based browsers (Chrome, Edge, Brave, etc.):
  - [Vue.js devtools](https://chromewebstore.google.com/detail/vuejs-devtools/nhdogjmejiglipccpnnnanhbledajbpd)
  - [Turn on Custom Object Formatter in Chrome DevTools](http://bit.ly/object-formatters)
- Firefox:
  - [Vue.js devtools](https://addons.mozilla.org/en-US/firefox/addon/vue-js-devtools/)
  - [Turn on Custom Object Formatter in Firefox DevTools](https://fxdx.dev/firefox-devtools-custom-object-formatters/)

## Customize configuration

See [Vite Configuration Reference](https://vite.dev/config/).

## Project Setup

```sh
npm install
```

### Compile and Hot-Reload for Development

```sh
npm run dev
```

### Compile and Minify for Production

```sh
npm run build
```

### Lint with [ESLint](https://eslint.org/)

```sh
npm run lint
```
