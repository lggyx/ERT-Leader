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