# 测评结果页面展示指南

## 功能概述

测评结果页（Result.vue）完整展示测评的所有数据，包括：

1. **基础信息** - 测评ID、用户名、类型、完成时间
2. **三大维度得分** - E、R、T 三个维度的分数、级别、描述（彩色卡片展示）
3. **领导力画像定位** - 根据得分匹配的27项领导力画像
4. **子维度能力详情** - 12项能力的逐项分析（表格形式）
5. **专属行动方案** - 每个子维度的改进建议（可折叠列表）

## 页面结构

```
测评结果
├── 基础信息 (描述列表)
│   ├── 测评ID
│   ├── 用户名
│   ├── 测评类型（QUICK/FULL）
│   └── 完成时间
├── 三大维度得分 (彩色卡片)
│   ├── E维度 - 蓝色
│   ├── R维度 - 绿色
│   └── T维度 - 橙色
├── 领导力画像定位 (信息卡)
│   └── 27项画像中匹配的描述
├── 子维度能力详情 (表格)
│   ├── 代码 | 名称 | 得分 | 所属维度
│   └── ...（12行）
└── 专属行动方案 (可折叠)
    ├── [E1 - 能力1] → 行动方案文本
    ├── [E2 - 能力2] → 行动方案文本
    └── ...
```

## 数据来源

API 端点：`GET /api/assessment/{assessmentId}/result`

**返回数据结构** (ResultVO):

```javascript
{
  assessmentId: 9,
  userName: "用户名",
  type: "QUICK",              // 测评类型
  completedAt: "2025-12-03T23:01:23.583+08:00",
  dimensionScores: {          // 三大维度
    e: {
      score: 75,              // E维度得分
      level: "H",             // 级别（H/M/L）
      description: "执行力..."
    },
    r: {
      score: 100,
      level: "H",
      description: "关系管理..."
    },
    t: {
      score: 81,
      level: "H",
      description: "思维力..."
    }
  },
  portrait: {                 // 领导力画像
    code: "HHM",              // 编码（三个H/M/L的组合）
    description: "高高中的领导力画像..."
  },
  subDimensionScores: [       // 12项子维度
    {
      code: "E1",
      name: "能力1",
      score: 70,
      dimension: "E",
      actionPlan: "改进方案..."
    },
    // ... 11 更多项
  ]
}
```

## 显示效果

### 基础信息区

```
┌─────────────────────────────┐
│ 测评ID    │ 9               │
│ 用户名    │ 张三            │
│ 测评类型  │ 极速测评(12题) │
│ 完成时间  │ 2025年12月4日  │
└─────────────────────────────┘
```

### 三大维度得分区（彩色卡片）

```
┌──────────┐  ┌──────────┐  ┌──────────┐
│ E 维度   │  │ R 维度   │  │ T 维度   │
│   75     │  │  100     │  │   81     │
│ 级别：H  │  │ 级别：H  │  │ 级别：H  │
│ 执行力.. │  │ 关系管.. │  │ 思维力.. │
└──────────┘  └──────────┘  └──────────┘
```

### 子维度表格

```
┌──┬──────┬────┬────┐
│代│能力名│得分│维度│
├──┼──────┼────┼────┤
│E1│能力1 │ 70 │ E  │
│E2│能力2 │ 65 │ E  │
│E3│能力3 │ 80 │ E  │
│E4│能力4 │ 75 │ E  │
│R1│能力5 │ 95 │ R  │
│..│ ...  │... │ .. │
└──┴──────┴────┴────┘
```

### 行动方案（折叠列表）

```
▼ E1 - 能力1
  执行能力是指...
  建议改进方案：
  1. 制定更详细的计划
  2. 定期检查进度
  ...

▶ E2 - 能力2
  ...

▶ E3 - 能力3
  ...
```

## 常见问题

### Q1: 结果页显示 "无结果数据"

**症状**：页面显示红色错误信息

**排查**：

1. 打开浏览器 F12 → Console
2. 查看 `[Result] API Response` 的内容
3. 如果为空，检查：
   - assessmentId 是否正确（URL 中 `/assessment/result/{id}`)
   - 后端是否返回了完整的 ResultVO
   - 响应拦截器是否正确处理

### Q2: 三大维度得分显示 "—"

**原因**：`result.dimensionScores` 为 null 或缺少字段

**解决**：

- 检查后端 SQL 是否计算了 E、R、T 三个维度的得分
- 确保返回了 `{ e: {score, level, description}, r: {...}, t: {...} }`

### Q3: 表格没有显示子维度信息

**原因**：`subDimensionScores` 为空或格式不对

**解决**：

- 检查后端是否返回了12项子维度数据
- 确保每项包含：`{ code, name, score, dimension, actionPlan }`

### Q4: 行动方案折叠列表为空

**原因**：`actionPlan` 字段为空或未关联

**解决**：

- 检查数据库中 `sub_score_action` 表是否有数据
- 确保测评完成时正确关联了行动方案

## 调试命令

在浏览器 Console 中查看完整响应：

```javascript
// 查看原始 API 响应
const res =
  /* 复制 [Result] API Response 的内容 */

  // 检查三大维度数据
  console.log('E score:', res.dimensionScores?.e?.score)
console.log('R score:', res.dimensionScores?.r?.score)
console.log('T score:', res.dimensionScores?.t?.score)

// 检查子维度数据
console.log('Sub dimensions count:', res.subDimensionScores?.length)
console.log('First item:', res.subDimensionScores?.[0])

// 检查画像数据
console.log('Portrait:', res.portrait)
```

## 测试场景

### 场景1：完整结果展示

- 所有字段都有数据
- 三个维度都显示彩色卡片
- 表格显示12项能力
- 行动方案能正常折叠/展开

### 场景2：部分数据缺失

- 某个维度得分为空 → 显示 "—"
- 没有画像描述 → 显示 "未定位"
- 没有行动方案 → 显示 "暂无行动方案"

### 场景3：数据加载失败

- API 返回 404 → 显示网络错误
- API 返回 500 → 显示后端错误
- 网络超时 → 重试或提示

## 优化建议

### 当前版本功能

✅ 完整的四层数据展示（基础、维度、子维度、行动方案）  
✅ 彩色卡片区分三大维度  
✅ 表格和折叠列表展示详细信息  
✅ 详细的日志记录便于调试

### 后续可优化方向

- [ ] 添加 PDF 导出功能
- [ ] 添加对比上次结果功能
- [ ] 添加分享/邮件功能
- [ ] 支持富文本展示行动方案
- [ ] 添加雷达图展示三维得分
- [ ] 添加打印样式优化

## 相关文件

- `src/views/Result.vue` - 结果页面组件
- `src/api/index.js` - API 调用
- `ASSESSMENT_DEBUG.md` - 测评诊断指南

## 信息采集

如遇到问题，请收集：

1. **浏览器 Console 输出**

   ```
   [Result] API Response: {...}
   [Result] Loaded result: {...}
   ```

2. **Network 标签中的响应**
   - URL: `http://localhost:8912/api/assessment/{id}/result`
   - 完整 JSON 响应体

3. **测评ID**（用于后端查询）

这样可快速定位问题。
