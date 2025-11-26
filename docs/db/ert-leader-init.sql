--  =========================================================
--  ERT 领导力测评系统 · 优化建表脚本（带中文注释）
--  MySQL 8.x 专用，保留完整外键 + 级联 + 校验
--  =========================================================
DROP DATABASE IF EXISTS `ert-leader`;
CREATE DATABASE IF NOT EXISTS `ert-leader`
    DEFAULT CHARACTER SET utf8mb4
    COLLATE utf8mb4_0900_ai_ci;
USE `ert-leader`;

SET FOREIGN_KEY_CHECKS = 0; -- 临时关闭外键检查，方便按任意顺序导数据
SET SQL_MODE = 'TRADITIONAL';
-- 启用严格模式，防止脏数据

-- 1. 三大能力维度表（E/R/T）
CREATE TABLE `dimension`
(
    `code`        VARCHAR(1) PRIMARY KEY COMMENT '维度编码：E执行/R关系/T思考',
    `name`        VARCHAR(30) NOT NULL COMMENT '维度名称',
    `description` TEXT COMMENT '维度说明（富文本）'
) ENGINE = InnoDB COMMENT ='三大能力维度主表';

-- 2. 子维度表（12 项细分能力）
CREATE TABLE `sub_dimension`
(
    `code`           VARCHAR(10) PRIMARY KEY COMMENT '子维度编码，如 E1',
    `dimension_code` VARCHAR(1) NOT NULL COMMENT '所属大维度',
    `name`           VARCHAR(50) COMMENT '子维度名称',
    `description`    TEXT COMMENT '子维度说明',
    CONSTRAINT fk_sub_dim
        FOREIGN KEY (`dimension_code`) REFERENCES `dimension` (`code`)
            ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB COMMENT ='12 项子能力配置';

-- 3. 用户表
CREATE TABLE `user`
(
    `id`         BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '用户主键',
    `mobile`     VARCHAR(20) UNIQUE COMMENT '手机号（唯一）',
    `email`      VARCHAR(100) UNIQUE COMMENT '邮箱（唯一）',
    `password`   VARCHAR(60) NOT NULL COMMENT '密码（bcrypt 加密）',
    `name`       VARCHAR(50) COMMENT '真实姓名',
    `gender`     TINYINT COMMENT '性别：0 未知，1 男，2 女',
    `birth_date` DATE COMMENT '出生日期',
    `status`     TINYINT     DEFAULT 1 COMMENT '账号状态：0 禁用，1 启用',
    `role`       VARCHAR(20) DEFAULT 'USER' COMMENT '角色：USER/INST_ADMIN/SUPER_ADMIN',
    `created_at` DATETIME    DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
    `updated_at` DATETIME    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间'
) ENGINE = InnoDB COMMENT ='测评用户档案';

-- 4. 测评记录表（一次测评一条）
CREATE TABLE `assessment`
(
    `id`          BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '测评记录主键',
    `user_id`     BIGINT                NOT NULL COMMENT '用户外键',
    `type`        ENUM ('QUICK','FULL') NOT NULL COMMENT '测评类型：QUICK 极速，FULL 完整',
    `status`      VARCHAR(20) DEFAULT 'INIT' COMMENT '测评状态：INIT 进行中，DONE 已完成',
    `e_score`     TINYINT CHECK (`e_score` BETWEEN 0 AND 100) COMMENT 'E 维度得分',
    `r_score`     TINYINT CHECK (`r_score` BETWEEN 0 AND 100) COMMENT 'R 维度得分',
    `t_score`     TINYINT CHECK (`t_score` BETWEEN 0 AND 100) COMMENT 'T 维度得分',
    `portrait_id` INT COMMENT '27 画像外键',
    `created_at`  DATETIME    DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`  DATETIME    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    CONSTRAINT fk_a_user
        FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
            ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB COMMENT ='用户测评记录';

-- 5. 题目表（每题归属一个子维度）
CREATE TABLE `question`
(
    `id`           BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '题目主键',
    `sub_dim_code` VARCHAR(10) NOT NULL COMMENT '子维度外键',
    `content`      TEXT        NOT NULL COMMENT '题干（富文本）',
    `seq`          INT DEFAULT 0 COMMENT '同一子维度下的排序号',
    CONSTRAINT fk_q_sub
        FOREIGN KEY (`sub_dim_code`) REFERENCES `sub_dimension` (`code`)
            ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB COMMENT ='测评题库';

-- 6. 选项表（每题 5 级李克量表）
CREATE TABLE `option`
(
    `id`          BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '选项主键',
    `question_id` BIGINT  NOT NULL COMMENT '所属题目',
    `label`       VARCHAR(100) COMMENT '选项文本（如"非常符合"）',
    `score`       TINYINT NOT NULL COMMENT '分值（1-5）',
    `seq`         INT DEFAULT 0 COMMENT '选项顺序',
    CONSTRAINT fk_o_q
        FOREIGN KEY (`question_id`) REFERENCES `question` (`id`)
            ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB COMMENT ='题目选项';

-- 7. 答题表（用户答案）
CREATE TABLE `answer`
(
    `assessment_id` BIGINT NOT NULL COMMENT '测评记录外键',
    `question_id`   BIGINT NOT NULL COMMENT '题目外键',
    `option_id`     BIGINT NOT NULL COMMENT '选项外键',
    PRIMARY KEY (`assessment_id`, `question_id`) COMMENT '同一测评同一题只能答一次',
    CONSTRAINT fk_an_a
        FOREIGN KEY (`assessment_id`) REFERENCES `assessment` (`id`)
            ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_an_q
        FOREIGN KEY (`question_id`) REFERENCES `question` (`id`)
            ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_an_o
        FOREIGN KEY (`option_id`) REFERENCES `option` (`id`)
            ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB COMMENT ='用户答题明细';

-- 8. 27 种领导者画像
CREATE TABLE `portrait`
(
    `id`          INT AUTO_INCREMENT PRIMARY KEY COMMENT '画像主键',
    `e_level`     CHAR(1) NOT NULL COMMENT 'E 维度等级：H 高，L 低',
    `r_level`     CHAR(1) NOT NULL COMMENT 'R 维度等级：H 高，L 低',
    `t_level`     CHAR(1) NOT NULL COMMENT 'T 维度等级：H 高，L 低',
    `description` TEXT COMMENT '画像描述（富文本）',
    UNIQUE KEY uk_p (`e_level`, `r_level`, `t_level`) COMMENT '三维组合唯一'
) ENGINE = InnoDB COMMENT ='27 种领导力画像';

-- 9. ERT 维度得分区间文字说明
CREATE TABLE `ert_score_desc`
(
    `id`             INT AUTO_INCREMENT PRIMARY KEY COMMENT '主键',
    `dimension_code` VARCHAR(1) NOT NULL COMMENT '维度 E/R/T',
    `min_score`      TINYINT    NOT NULL COMMENT '区间下限（含）',
    `max_score`      TINYINT    NOT NULL COMMENT '区间上限（含）',
    `description`    TEXT COMMENT '区间文字描述',
    CONSTRAINT fk_ert_dim
        FOREIGN KEY (`dimension_code`) REFERENCES `dimension` (`code`)
            ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB COMMENT ='维度得分区间解释';

-- 10. 子维度得分 & 专属行动方案
CREATE TABLE `sub_score_action`
(
    `id`           INT AUTO_INCREMENT PRIMARY KEY COMMENT '主键',
    `sub_dim_code` VARCHAR(10) NOT NULL COMMENT '子维度编码',
    `min_score`    TINYINT     NOT NULL COMMENT '区间下限',
    `max_score`    TINYINT     NOT NULL COMMENT '区间上限',
    `action_plan`  TEXT COMMENT '可执行的具体行动方案（富文本）',
    CONSTRAINT fk_ssa_sub
        FOREIGN KEY (`sub_dim_code`) REFERENCES `sub_dimension` (`code`)
            ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB COMMENT ='子维度得分行动方案';

-- 11. 联系信息（全局仅一行）
CREATE TABLE `contact`
(
    `id`      TINYINT PRIMARY KEY DEFAULT 1 COMMENT '固定主键=1，全局唯一',
    `company` VARCHAR(100) COMMENT '公司名称',
    `phone`   VARCHAR(30) COMMENT '联系电话',
    `email`   VARCHAR(100) COMMENT '联系邮箱',
    `address` VARCHAR(200) COMMENT '办公地址',
    CONSTRAINT chk_contact_id CHECK (`id` = 1)
) ENGINE = InnoDB COMMENT ='系统联系信息（仅一行）';

SET FOREIGN_KEY_CHECKS = 1; -- 开启外键检查