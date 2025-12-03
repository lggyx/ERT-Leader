import axios from 'axios'

const baseURL = import.meta.env.VITE_API_BASE || 'http://localhost:8912'
const instance = axios.create({
  baseURL,
  timeout: 10000,
  withCredentials: true, // 允许跨域请求时携带 cookies
  headers: {
    'Content-Type': 'application/json',
  },
})

// 请求拦截器：自动附加 token
instance.interceptors.request.use((config) => {
  const token = localStorage.getItem('TOKEN')
  if (token) {
    config.headers = config.headers || {}
    config.headers.Authorization = `Bearer ${token}`
  }
  console.log(
    `[API] ${config.method.toUpperCase()} ${config.baseURL}${config.url}`,
    config.data || config.params,
  )
  return config
})

// 响应拦截器：处理标准返回格式 { code, msg, data }
instance.interceptors.response.use(
  (res) => {
    console.log(`[API Response]`, res.data)
    const result = res.data
    // 如果后端返回 code 不为 200 或 0，可在此处理错误
    if (result && (result.code === 200 || result.code === 0)) {
      return result.data || result
    }
    return result
  },
  (error) => {
    console.error('[API Error]', {
      message: error.message,
      status: error.response?.status,
      statusText: error.response?.statusText,
      data: error.response?.data,
      url: error.config?.url,
    })
    return Promise.reject(error)
  },
)

// ========== 认证模块 ==========
export const auth = {
  // 用户登录 (POST /api/auth/login)
  // 请求体: { account: string, password: string }
  login(account, password) {
    return instance.post('/api/auth/login', { account, password })
  },

  // 用户注册 (POST /api/auth/register)
  // 请求体: UserDTO { mobile, email, password, name, gender, birthDate }
  register(payload) {
    return instance.post('/api/auth/register', payload)
  },

  // 获取当前用户信息 (GET /api/auth/current)
  current() {
    return instance.get('/api/auth/current')
  },
}

// ========== 测评流程模块 ==========
export const assessment = {
  // 创建测评记录 (POST /api/assessment/create)
  // 请求体: { type: 'QUICK' | 'FULL' }
  create(type) {
    return instance.post('/api/assessment/create', { type })
  },

  // 获取测评题目 (POST /api/assessment/{assessmentId}/questions)
  getQuestions(assessmentId) {
    return instance.post(`/api/assessment/${assessmentId}/questions`)
  },

  // 提交答案 (POST /api/assessment/{assessmentId}/answer)
  // 请求体: { questionId: number, optionId: number }
  answer(assessmentId, questionId, optionId) {
    return instance.post(`/api/assessment/${assessmentId}/answer`, {
      questionId,
      optionId,
    })
  },

  // 完成测评 (POST /api/assessment/{assessmentId}/complete)
  complete(assessmentId) {
    return instance.post(`/api/assessment/${assessmentId}/complete`)
  },

  // 获取测评结果 (GET /api/assessment/{assessmentId}/result)
  result(assessmentId) {
    return instance.get(`/api/assessment/${assessmentId}/result`)
  },

  // 查询测评历史 (GET /api/assessment/history)
  // 查询参数: page, pageSize
  history(page = 1, pageSize = 10) {
    return instance.get('/api/assessment/history', {
      params: { page, pageSize },
    })
  },
}

// ========== 用户管理模块 ==========
export const admin = {
  // 分页查询用户列表 (GET /api/admin/user/page)
  // 查询参数: page, pageSize, status, role, keyword
  userPage(page = 1, pageSize = 10, status, role, keyword) {
    return instance.get('/api/admin/user/page', {
      params: { page, pageSize, status, role, keyword },
    })
  },

  // 启用/禁用用户 (PUT /api/admin/user/{userId}/status)
  // 查询参数: status
  updateUserStatus(userId, status) {
    return instance.put(`/api/admin/user/${userId}/status`, null, {
      params: { status },
    })
  },

  // 设置用户角色 (PUT /api/admin/user/{userId}/role)
  // 查询参数: role
  updateUserRole(userId, role) {
    return instance.put(`/api/admin/user/${userId}/role`, null, {
      params: { role },
    })
  },

  // ========== 题目管理模块 ==========
  // 分页查询题目 (GET /api/admin/question/page)
  // 查询参数: page, pageSize, subDimCode, keyword
  questionPage(page = 1, pageSize = 10, subDimCode, keyword) {
    return instance.get('/api/admin/question/page', {
      params: { page, pageSize, subDimCode, keyword },
    })
  },

  // 创建题目 (POST /api/admin/question)
  // 请求体: { subDimCode: string, content: string, seq: number }
  createQuestion(subDimCode, content, seq) {
    return instance.post('/api/admin/question', {
      subDimCode,
      content,
      seq,
    })
  },

  // 更新题目 (PUT /api/admin/question/{questionId})
  // 请求体: { subDimCode?, content?, seq? }
  updateQuestion(questionId, payload) {
    return instance.put(`/api/admin/question/${questionId}`, payload)
  },

  // 删除题目 (DELETE /api/admin/question/{questionId})
  deleteQuestion(questionId) {
    return instance.delete(`/api/admin/question/${questionId}`)
  },

  // ========== 维度配置管理 ==========
  // 查询三大能力维度 (GET /api/admin/dimension/list)
  dimensionList() {
    return instance.get('/api/admin/dimension/list')
  },

  // 更新维度描述 (PUT /api/admin/dimension/{code})
  // 查询参数: dimension (DimensionDTO)
  updateDimension(code, name, description) {
    return instance.put(`/api/admin/dimension/${code}`, null, {
      params: { dimension: JSON.stringify({ name, description }) },
    })
  },

  // ========== 子维度配置管理 ==========
  // 查询子维度列表 (GET /api/admin/sub-dimension/list)
  // 查询参数: dimensionCode
  subDimensionList(dimensionCode) {
    return instance.get('/api/admin/sub-dimension/list', {
      params: { dimensionCode },
    })
  },

  // 更新子维度 (PUT /api/admin/sub-dimension/{code})
  // 查询参数: code, 请求体: { name?, description? }
  updateSubDimension(code, payload) {
    return instance.put(`/api/admin/sub-dimension/${code}`, payload)
  },

  // ========== ERT得分区间管理 ==========
  // 查询得分区间 (GET /api/admin/ert-score-desc/list)
  ertScoreDescList() {
    return instance.get('/api/admin/ert-score-desc/list')
  },

  // 更新得分区间 (PUT /api/admin/ert-score-desc/{id})
  // 请求体: { minScore, maxScore, description }
  updateErtScoreDesc(id, minScore, maxScore, description) {
    return instance.put(`/api/admin/ert-score-desc/${id}`, {
      minScore,
      maxScore,
      description,
    })
  },

  // ========== 子维度行动方案管理 ==========
  // 查询行动方案 (GET /api/admin/sub-score-action/list)
  // 查询参数: subDimCode
  subScoreActionList(subDimCode) {
    return instance.get('/api/admin/sub-score-action/list', {
      params: { subDimCode },
    })
  },

  // 更新行动方案 (PUT /api/admin/sub-score-action/{id})
  // 请求体: { minScore, maxScore, actionPlan }
  updateSubScoreAction(id, minScore, maxScore, actionPlan) {
    return instance.put(`/api/admin/sub-score-action/${id}`, {
      minScore,
      maxScore,
      actionPlan,
    })
  },

  // ========== 27项领导力画像配置 ==========
  // 查询画像列表 (GET /api/admin/portrait/list)
  portraitList() {
    return instance.get('/api/admin/portrait/list')
  },

  // 更新画像描述 (PUT /api/admin/portrait/{id})
  // 请求体: { description }
  updatePortrait(id, description) {
    return instance.put(`/api/admin/portrait/${id}`, { description })
  },

  // ========== 系统配置管理 ==========
  // 获取联系信息 (GET /api/admin/contact)
  getContact() {
    return instance.get('/api/admin/contact')
  },

  // 更新联系信息 (PUT /api/admin/contact)
  // 请求体: { company, phone, email, address }
  updateContact(company, phone, email, address) {
    return instance.put('/api/admin/contact', {
      company,
      phone,
      email,
      address,
    })
  },

  // ========== 历史测评记录 ==========
  // 管理员查询所有测评记录 (GET /api/admin/assessment/page)
  // 查询参数: page, pageSize, status, userId
  assessmentPage(page = 1, pageSize = 10, status, userId) {
    return instance.get('/api/admin/assessment/page', {
      params: { page, pageSize, status, userId },
    })
  },
}

export default instance
