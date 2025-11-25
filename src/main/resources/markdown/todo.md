# 开发需求

| 功能大类               | 子功能                          | 说明/入口                                                    |
| ---------------------- | ------------------------------- | ------------------------------------------------------------ |
| 门户页                 | 1. 门户页（移动端、PC端）       | 统一首页                                                     |
|                        | 2. 用户登录                     | 右上角【登录】按钮                                           |
|                        | 3. 注册                         | 右上角【注册】按钮                                           |
|                        | 4. 开始测评入口                 | 含以下三项                                                   |
|                        | 4.1 极速测评                    | 气泡框                                                       |
|                        | 4.2 完整测评                    | 气泡框                                                       |
|                        | 4.3 我的历史测评报告            | 气泡框                                                       |
| 登录页                 | 1. 收集测评用户个人信息         | Web 表单                                                     |
| 答题页面               | 1. 展示题目内容                 |                                                              |
|                        | 2. 收集用户答案                 |                                                              |
| 测评结果页             | 1. 展示测评整体结果             |                                                              |
|                        | 2. 总体定位                     | 领导力画像及三大能力图谱，按总得分展示分析                   |
|                        | 3. 子维度详情                   | 12 项能力强弱分析，按实际得分详细展示                        |
|                        | 4. 改善建议                     | 专属行动方案                                                 |
|                        | 5. 保存结果                     | 将测评结果保存为本地图片                                     |
| 特殊项                 | 1. 多端适配                     | 适配移动端，自适应 PC 端                                     |
| 用户管理               | 1. 可用性管理                   | 启用/禁用所有用户                                            |
|                        | 2. 配置机构/学校管理员          |                                                              |
| 分类题目管理           | 1. 题目内容的增、删、改、查     |                                                              |
| 三大能力配置管理       | 1. 展示并可编辑三大能力信息     |                                                              |
| 子能力配置管理         | 1. 展示并可编辑 12 项子能力信息 |                                                              |
| 27 项领导力画像        | 1. 配置描述                     | 按三大能力得分组合（如高高中、低中高）配置 27 项总体定位描述 |
| ERT 得分管理           | 1. 得分区间分析                 | 配置 E、R、T 三大能力得分对应区间的分析内容展示              |
| 子项得分及行动方案管理 | 1. 得分区间分析                 | 配置 12 项子能力得分对应区间的分析内容展示                   |
|                        | 2. 行动方案及建议               | 用于测评结果页“专属行动方案”展示                             |
| 历史测评结果页         | 1. 查看所有用户历史测评结果     |                                                              |
|                        | 2. 管理员保存结果               | 保存时添加公司名称、联系方式等，以图片形式保存至本地         |
| 联系我们配置项         | 1. 维护公司及联系方式信息       |                                                              |
| 门户页内容配置项       | 可配置标题                      | 1. 了解 ERT<br>2. 常见问题<br>3. 隐私政策<br>4. 使用条款<br>5. 应用指南 |

# 后端配置

下面给出三部分交付物，可直接落地：  
1. 后端接口清单（REST 风格，供前端/移动端调用）  
2. 数据库 ER 图（文字描述 + 表清单）  
3. 初始化 SQL 文件（MySQL 8.x 语法，含表结构、主键、外键、索引、基础字典数据）

--------------------------------------------------
一、后端接口清单

| 模块 | 接口名称              | 方法   | URL                             | 主要入参                    | 主要出参                                              | 备注            |
| ---- | --------------------- | ------ | ------------------------------- | --------------------------- | ----------------------------------------------------- | --------------- |
| 门户 | 获取门户配置          | GET    | /api/portal/config              | -                           | 标题、ERT介绍、常见问题、隐私政策、使用条款、应用指南 | 可缓存          |
| 用户 | 发送验证码            | POST   | /api/user/send-otp              | mobile/email                | -                                                     | 注册/登录前调用 |
| 用户 | 用户注册              | POST   | /api/user/register              | mobile/email, otp, password | userId, token                                         |                 |
| 用户 | 用户登录              | POST   | /api/user/login                 | mobile/email, password      | userId, token                                         |                 |
| 用户 | 查看自己资料          | GET    | /api/user/profile               | -                           | 全部用户字段                                          | 需登录          |
| 用户 | 更新自己资料          | PUT    | /api/user/profile               | 姓名、性别、生日等          | -                                                     |                 |
| 测评 | 创建测评记录          | POST   | /api/assessment                 | type=QUICK/FULL             | assessmentId                                          | 返回问卷token   |
| 测评 | 拉取题目              | GET    | /api/assessment/{id}/questions  | -                           | 题目列表                                              |                 |
| 测评 | 提交答案              | POST   | /api/assessment/{id}/answer     | questionId, optionId        | -                                                     | 支持批量        |
| 测评 | 计算结果              | POST   | /api/assessment/{id}/compute    | -                           | E/R/T得分、12子项得分、画像ID                         |                 |
| 测评 | 获取报告              | GET    | /api/assessment/{id}/report     | -                           | 完整报告JSON                                          |                 |
| 测评 | 下载报告图片          | GET    | /api/assessment/{id}/report.png | -                           | 二进制PNG                                             |                 |
| 测评 | 我的历史测评          | GET    | /api/assessment/history         | page, size                  | 列表                                                  |                 |
| 管理 | 用户列表（管理）      | GET    | /admin/user                     | keyword, status, page       | 分页列表                                              | 需管理员token   |
| 管理 | 启用/禁用用户         | PUT    | /admin/user/{id}/status         | status=0/1                  | -                                                     |                 |
| 管理 | 设为机构管理员        | PUT    | /admin/user/{id}/role           | role=INST_ADMIN             | -                                                     |                 |
| 题库 | 题目列表（管理）      | GET    | /admin/question                 | dimension, page             |                                                       |                 |
| 题库 | 新增题目              | POST   | /admin/question                 | 题干、选项、维度、分值      | questionId                                            |                 |
| 题库 | 更新题目              | PUT    | /admin/question/{id}            | 同上                        | -                                                     |                 |
| 题库 | 删除题目              | DELETE | /admin/question/{id}            | -                           | -                                                     |                 |
| 配置 | 三大能力配置          | GET    | /admin/dimension                | -                           | E/R/T信息                                             |                 |
| 配置 | 更新三大能力          | PUT    | /admin/dimension/{code}         | 名称、描述                  | -                                                     |                 |
| 配置 | 12子能力配置          | GET    | /admin/sub-dimension            | -                           | 列表                                                  |                 |
| 配置 | 更新子能力            | PUT    | /admin/sub-dimension/{code}     | 名称、描述                  | -                                                     |                 |
| 配置 | 27画像配置            | GET    | /admin/portrait                 | -                           | 列表                                                  |                 |
| 配置 | 更新画像描述          | PUT    | /admin/portrait/{id}            | E/R/T高低组合、描述         | -                                                     |                 |
| 配置 | ERT得分区间描述       | GET    | /admin/ert-score-desc           | dimension                   | 区间列表                                              |                 |
| 配置 | 更新ERT得分描述       | PUT    | /admin/ert-score-desc/{id}      | min,max,描述                | -                                                     |                 |
| 配置 | 子项得分区间&行动方案 | GET    | /admin/sub-score-action         | subCode                     | 区间列表                                              |                 |
| 配置 | 更新子项行动方案      | PUT    | /admin/sub-score-action/{id}    | min,max,行动方案            | -                                                     |                 |
| 历史 | 全部历史报告（管理）  | GET    | /admin/assessment               | keyword, page               | 列表                                                  |                 |
| 历史 | 管理员导出报告图      | POST   | /admin/assessment/{id}/export   | company, contact            | 二进制PNG                                             |                 |
| 联系 | 获取联系信息          | GET    | /api/contact                    | -                           | 公司名、电话、邮箱、地址                              |                 |
| 联系 | 更新联系信息（管理）  | PUT    | /admin/contact                  | 同上                        | -                                                     |                 |

--------------------------------------------------
二、数据库 ER 文字描述

实体与关系（①1:N  ②N:M）

User(用户) ①----< Assessment(测评记录)  
Assessment ①----< Answer(答题)  
Question(题目) ①----< Answer  
Question ①----< Option(选项)  
Dimension(三大能力) ①----< SubDimension(12子能力)  
SubDimension ①----< Question（每题归属一个子能力）  
Portrait(27画像) 由 E/R/T 高低组合唯一确定  
ERTScoreDesc / SubScoreAction 均为维度得分区间配置  
Contact 仅一条记录，全局行

--------------------------------------------------
三、初始化 SQL 文件（MySQL 8）


```sql
-- 0. 创建数据库（若不存在）
CREATE DATABASE IF NOT EXISTS `ert-leader`
    DEFAULT CHARACTER SET utf8mb4
    COLLATE utf8mb4_0900_ai_ci;
USE `ert-leader`;

-- 1. 用户表
CREATE TABLE `user`
(
    `id`         BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键',
    `mobile`     VARCHAR(20) UNIQUE COMMENT '手机号',
    `email`      VARCHAR(100) UNIQUE COMMENT '邮箱',
    `password`   VARCHAR(60) NOT NULL COMMENT '密码（加密）',
    `name`       VARCHAR(50) COMMENT '姓名',
    `gender`     TINYINT COMMENT '性别：0 未知，1 男，2 女',
    `birth_date` DATE COMMENT '出生日期',
    `status`     TINYINT     DEFAULT 1 COMMENT '状态：0 禁用，1 启用',
    `role`       VARCHAR(20) DEFAULT 'USER' COMMENT '角色：USER 普通用户，INST_ADMIN 机构管理员，SUPER_ADMIN 超级管理员',
    `created_at` DATETIME    DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` DATETIME    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
);

-- 2. 测评记录表
CREATE TABLE `assessment`
(
    `id`          BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键',
    `user_id`     BIGINT                NOT NULL COMMENT '用户ID',
    `type`        ENUM ('QUICK','FULL') NOT NULL COMMENT '测评类型：QUICK 快速，FULL 完整',
    `status`      VARCHAR(20) DEFAULT 'INIT' COMMENT '状态：INIT 初始化，DONE 已完成',
    `e_score`     TINYINT CHECK (e_score BETWEEN 0 AND 100) COMMENT 'E 维度得分',
    `r_score`     TINYINT CHECK (r_score BETWEEN 0 AND 100) COMMENT 'R 维度得分',
    `t_score`     TINYINT CHECK (t_score BETWEEN 0 AND 100) COMMENT 'T 维度得分',
    `portrait_id` INT COMMENT '对应画像ID',
    `created_at`  DATETIME    DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`  DATETIME    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    CONSTRAINT fk_a_user FOREIGN KEY (user_id) REFERENCES `user` (id)
);

-- 3. 维度表
CREATE TABLE `dimension`
(
    `code`        VARCHAR(1) PRIMARY KEY COMMENT '维度编码：E/R/T',
    `name`        VARCHAR(30) NOT NULL COMMENT '维度名称',
    `description` TEXT COMMENT '维度描述'
);

-- 4. 子维度表
CREATE TABLE `sub_dimension`
(
    `code`           VARCHAR(10) PRIMARY KEY COMMENT '子维度编码',
    `dimension_code` VARCHAR(1) NOT NULL COMMENT '所属维度编码',
    `name`           VARCHAR(50) COMMENT '子维度名称',
    `description`    TEXT COMMENT '子维度描述',
    CONSTRAINT fk_sub_dim FOREIGN KEY (dimension_code) REFERENCES `dimension` (code)
);

-- 5. 题目表
CREATE TABLE `question`
(
    `id`           BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键',
    `sub_dim_code` VARCHAR(10) NOT NULL COMMENT '所属子维度编码',
    `content`      TEXT        NOT NULL COMMENT '题干',
    `seq`          INT DEFAULT 0 COMMENT '排序号',
    CONSTRAINT fk_q_sub FOREIGN KEY (sub_dim_code) REFERENCES `sub_dimension` (code)
);

-- 6. 选项表
CREATE TABLE `option`
(
    `id`          BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键',
    `question_id` BIGINT  NOT NULL COMMENT '所属题目ID',
    `label`       VARCHAR(100) COMMENT '选项文本',
    `score`       TINYINT NOT NULL COMMENT '选项分值',
    `seq`         INT DEFAULT 0 COMMENT '排序号',
    CONSTRAINT fk_o_q FOREIGN KEY (question_id) REFERENCES `question` (id) ON DELETE CASCADE
);

-- 7. 答题表（用户答案）
CREATE TABLE `answer`
(
    `assessment_id` BIGINT NOT NULL COMMENT '测评记录ID',
    `question_id`   BIGINT NOT NULL COMMENT '题目ID',
    `option_id`     BIGINT NOT NULL COMMENT '选项ID',
    PRIMARY KEY (assessment_id, question_id),
    CONSTRAINT fk_an_a FOREIGN KEY (assessment_id) REFERENCES `assessment` (id),
    CONSTRAINT fk_an_q FOREIGN KEY (question_id) REFERENCES `question` (id),
    CONSTRAINT fk_an_o FOREIGN KEY (option_id) REFERENCES `option` (id)
);

-- 8. 领导者画像表（27 种组合）
CREATE TABLE `portrait`
(
    `id`          INT AUTO_INCREMENT PRIMARY KEY COMMENT '主键',
    `e_level`     CHAR(1) NOT NULL COMMENT 'E 维度等级：H 高，L 低',
    `r_level`     CHAR(1) NOT NULL COMMENT 'R 维度等级：H 高，L 低',
    `t_level`     CHAR(1) NOT NULL COMMENT 'T 维度等级：H 高，L 低',
    `description` TEXT COMMENT '画像描述',
    UNIQUE KEY uk_p (e_level, r_level, t_level)
);

-- 9. ERT 得分区间描述表
CREATE TABLE `ert_score_desc`
(
    `id`             INT AUTO_INCREMENT PRIMARY KEY COMMENT '主键',
    `dimension_code` VARCHAR(1) NOT NULL COMMENT '维度编码：E/R/T',
    `min_score`      TINYINT    NOT NULL COMMENT '区间最小值',
    `max_score`      TINYINT    NOT NULL COMMENT '区间最大值',
    `description`    TEXT COMMENT '区间描述',
    CONSTRAINT fk_ert_dim FOREIGN KEY (dimension_code) REFERENCES `dimension` (code)
);

-- 10. 子维度得分 & 行动方案表
CREATE TABLE `sub_score_action`
(
    `id`           INT AUTO_INCREMENT PRIMARY KEY COMMENT '主键',
    `sub_dim_code` VARCHAR(10) NOT NULL COMMENT '子维度编码',
    `min_score`    TINYINT     NOT NULL COMMENT '区间最小值',
    `max_score`    TINYINT     NOT NULL COMMENT '区间最大值',
    `action_plan`  TEXT COMMENT '行动方案',
    CONSTRAINT fk_ssa_sub FOREIGN KEY (sub_dim_code) REFERENCES `sub_dimension` (code)
);

-- 11. 联系信息表（全局仅一行）
CREATE TABLE `contact`
(
    `id`      TINYINT PRIMARY KEY DEFAULT 1 COMMENT '主键，固定为 1',
    `company` VARCHAR(100) COMMENT '公司名称',
    `phone`   VARCHAR(30) COMMENT '联系电话',
    `email`   VARCHAR(100) COMMENT '联系邮箱',
    `address` VARCHAR(200) COMMENT '办公地址',
    CHECK (id = 1)
);

/* ========== 基础数据示例 ========== */

-- 12. 维度
INSERT INTO `dimension` (`code`, `name`, `description`)
VALUES ('E', '执行', '执行能力维度'),
       ('R', '关系', '关系处理能力维度'),
       ('T', '思考', '战略思考能力维度');

-- 13. 子维度（示例只列 3 条，其余同理）
INSERT INTO `sub_dimension` (`code`, `dimension_code`, `name`, `description`)
VALUES ('E1', 'E', '目标设定', '能否清晰设定目标'),
       ('E2', 'E', '结果导向', '是否以结果为导向'),
       ('R1', 'R', '同理心', '能否设身处地理解他人');

-- 14. 领导者画像（示例 2 条）
INSERT INTO `portrait` (`e_level`, `r_level`, `t_level`, `description`)
VALUES ('H', 'H', 'H', '卓越领导者：高执行、高关系、高思考'),
       ('L', 'L', 'L', '潜在领导者：三方面均需提升');

-- 15. 联系信息占位
INSERT INTO `contact` (`id`, `company`, `phone`, `email`, `address`)
VALUES (1, 'ERT 领导力研究院', '400-123-4567', 'support@ertleader.com', '上海市浦东新区张江高科技园区');
```
