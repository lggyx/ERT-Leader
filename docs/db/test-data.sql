--  =========================================================
--  ERT 领导力测评系统 · 测试数据初始化脚本（MySQL 8.x）
--  覆盖全表：user、assessment、question、option、answer、
--  dimension、sub_dimension、portrait、ert_score_desc、
--  sub_score_action、contact
--  可直接 source 执行；生产请勿使用！
--  =========================================================

USE `ert-leader`;

-- 1. 用户表（3 条普通用户 + 1 条机构管理员 + 1 条超级管理员）
INSERT INTO `user`
(mobile, email, password, name, gender, birth_date, status, role)
VALUES
    ('13800000001', 'zhangsan@test.com', '$2a$10$N.zpmQdq5pGzVwXuZn7eXOfVjVrD5sGfCjW.qa3lQi9tG5lK1dWjC', '张三', 1, '1990-01-01', 1, 'USER'),
    ('13800000002', 'lisi@test.com', '$2a$10$N.zpmQdq5pGzVwXuZn7eXOfVjVrD5sGfCjW.qa3lQi9tG5lK1dWjC', '李四', 2, '1988-03-15', 1, 'USER'),
    ('13800000003', 'wangwu@test.com', '$2a$10$N.zpmQdq5pGzVwXuZn7eXOfVjVrD5sGfCjW.qa3lQi9tG5lK1dWjC', '王五', 1, '1992-07-20', 0, 'USER'),   -- 禁用
    ('13800000004', 'instadmin@ert.com', '$2a$10$N.zpmQdq5pGzVwXuZn7eXOfVjVrD5sGfCjW.qa3lQi9tG5lK1dWjC', '机构管理员', 1, '1985-05-10', 1, 'INST_ADMIN'),
    ('13800000005', 'super@ert.com', '$2a$10$N.zpmQdq5pGzVwXuZn7eXOfVjVrD5sGfCjW.qa3lQi9tG5lK1dWjC', '超级管理员', 1, '1980-12-01', 1, 'SUPER_ADMIN');

-- 2. 维度（3 条）
INSERT INTO `dimension` (`code`, `name`, `description`)
VALUES
    ('E', '执行', '执行力维度：目标设定、结果导向、计划推进等'),
    ('R', '关系', '关系维度：同理心、冲突管理、激励他人等'),
    ('T', '思考', '思考维度：战略视野、系统思考、创新突破等');

-- 3. 子维度（12 条）
INSERT INTO `sub_dimension` (`code`, `dimension_code`, `name`, `description`)
VALUES
    ('E1', 'E', '目标设定', '能否清晰设定可衡量的目标'),
    ('E2', 'E', '结果导向', '是否以结果为核心驱动力'),
    ('E3', 'E', '计划推进', '能否制定并推进详细计划'),
    ('E4', 'E', '高效决策', '能否快速做出高质量决策'),
    ('R1', 'R', '同理心', '能否设身处地理解他人'),
    ('R2', 'R', '冲突管理', '能否有效化解冲突'),
    ('R3', 'R', '激励他人', '能否激发团队潜力'),
    ('R4', 'R', '影响力', '能否影响利益相关者'),
    ('T1', 'T', '战略视野', '能否看到长期趋势与机会'),
    ('T2', 'T', '系统思考', '能否识别复杂系统中的关键要素'),
    ('T3', 'T', '创新突破', '能否提出并推动创新方案'),
    ('T4', 'T', '风险管理', '能否预判并规避重大风险');

-- 4. 题目（每子维度 3 题，共 36 题）
INSERT INTO `question` (`sub_dim_code`, `content`, `seq`)
VALUES
    ('E1', '我善于把愿景转化为可衡量的目标。', 1),
    ('E1', '我会为目标设定清晰的截止日期。', 2),
    ('E1', '我能够将目标分解为关键结果（KR）。', 3),
    ('E2', '我关注结果胜过关注过程。', 1),
    ('E2', '我经常复盘未达成结果的原因。', 2),
    ('E2', '我会为结果设定奖惩机制。', 3),
    ('E3', '我习惯用甘特图或里程碑管理进度。', 1),
    ('E3', '我会提前识别并解决资源瓶颈。', 2),
    ('E3', '我每周都会检查计划偏差并调整。', 3),
    ('E4', '我能在信息不完整时做出决策。', 1),
    ('E4', '我会用数据+直觉结合做判断。', 2),
    ('E4', '我决策后愿意承担后果。', 3),
    ('R1', '我能准确说出对方内心的担忧。', 1),
    ('R1', '我习惯先倾听再表达观点。', 2),
    ('R1', '我会用复述确认理解对方。', 3),
    ('R2', '我能把冲突转化为建设性讨论。', 1),
    ('R2', '我擅长寻找双赢方案。', 2),
    ('R2', '我会在冲突后快速修复关系。', 3),
    ('R3', '我经常表扬他人的具体行为。', 1),
    ('R3', '我会给下属挑战性任务以激发潜力。', 2),
    ('R3', '我帮助团队成员制定成长计划。', 3),
    ('R4', '我能用故事影响他人接受我的观点。', 1),
    ('R4', '我会提前识别关键利益相关者。', 2),
    ('R4', '我擅长用数据+情感双重说服。', 3),
    ('T1', '我关注行业未来 3 年的变化趋势。', 1),
    ('T1', '我会把长期趋势转化为机会清单。', 2),
    ('T1', '我每年会更新自己的战略假设。', 3),
    ('T2', '我常用因果回路图分析问题。', 1),
    ('T2', '我能识别系统中的增强回路。', 2),
    ('T2', '我会找到杠杆解而非症状解。', 3),
    ('T3', '我鼓励团队提出颠覆式想法。', 1),
    ('T3', '我习惯用低成本实验验证创新。', 2),
    ('T3', '我会为创新设定容错预算。', 3),
    ('T4', '我会定期做 premortem 分析。', 1),
    ('T4', '我为每个高风险项目设退出条件。', 2),
    ('T4', '我关注灰犀牛而非黑天鹅。', 3);

-- 5. 选项（每题 5 级李克量表，分值 1-5）
-- 采用存储过程批量生成，避免手写 180 行
DELIMITER $$
CREATE PROCEDURE init_options()
BEGIN
  DECLARE done INT DEFAULT 0;
  DECLARE qid BIGINT;
  DECLARE cur CURSOR FOR SELECT id FROM question;
DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;
OPEN cur;
read_loop: LOOP
    FETCH cur INTO qid;
    IF done THEN LEAVE read_loop; END IF;
INSERT INTO `option` (`question_id`, `label`, `score`, `seq`)
VALUES
    (qid, '非常不符合', 1, 1),
    (qid, '较不符合', 2, 2),
    (qid, '一般', 3, 3),
    (qid, '较符合', 4, 4),
    (qid, '非常符合', 5, 5);
END LOOP;
CLOSE cur;
END$$
DELIMITER ;
CALL init_options();
DROP PROCEDURE init_options;

-- 6. 测评记录（3 条完成 + 1 条未完成）
INSERT INTO `assessment`
(user_id, type, status, e_score, r_score, t_score, portrait_id)
VALUES
    (1, 'FULL', 'DONE', 85, 72, 90, 17),
    (2, 'QUICK', 'DONE', 60, 80, 65, 10),
    (4, 'FULL', 'DONE', 90, 85, 88, 26),
    (1, 'QUICK', 'INIT', NULL, NULL, NULL, NULL);

-- 7. 答题（仅已完成测评有答案，随机取中间值 3 分）
INSERT INTO `answer` (`assessment_id`, `question_id`, `option_id`)
SELECT a.id, q.id, o.id
FROM assessment a
         JOIN question q ON 1=1
         JOIN `option` o ON o.question_id = q.id AND o.score = 3
WHERE a.status = 'DONE';

-- 8. 27 领导者画像（示例 8 条，其余可同理补齐）
INSERT INTO `portrait` (`e_level`, `r_level`, `t_level`, `description`)
VALUES
    ('H', 'H', 'H', '卓越领导者：三高型，执行力、关系力、思考力均衡突出，适合担任首席官员或全面业务负责人'),
    ('H', 'H', 'L', '执行+关系型：擅长落地与带队，战略思考稍弱，适合事业部总经理或区域总裁'),
    ('H', 'L', 'H', '执行+思考型：战略落地能力强，但需提升影响力，适合创新业务 CEO/CTO'),
    ('L', 'H', 'H', '关系+思考型：愿景与影响力强，但落地节奏需加强，适合战略顾问或董事会角色'),
    ('H', 'L', 'L', '单执行型：雷厉风行，但缺战略与影响力，适合项目总监或运营 VP'),
    ('L', 'H', 'L', '单关系型：人际润滑与激励强，缺战略与落地，适合 HR 副总裁或政商关系负责人'),
    ('L', 'L', 'H', '单思考型：战略与风险洞察强，缺执行与关系，适合首席战略官或外部专家'),
    ('L', 'L', 'L', '潜力型：三项皆低，处于领导旅程起点，需系统培养');

-- 9. ERT 得分区间描述（每维度 3 段）
INSERT INTO `ert_score_desc` (`dimension_code`, `min_score`, `max_score`, `description`)
VALUES
    ('E', 0, 40, '执行较弱：需从目标设定与计划推进开始系统提升'),
    ('E', 41, 70, '执行中等：已具备基础，可在决策速度与结果复盘上加强'),
    ('E', 71, 100, '执行优秀：高结果导向，可辅导他人并复制方法论'),
    ('R', 0, 40, '关系较弱：建议从倾听与同理心训练开始'),
    ('R', 41, 70, '关系中等：已能建立信任，需提升冲突管理与影响力'),
    ('R', 71, 100, '关系优秀：人际影响力突出，可担任组织文化与变革引领者'),
    ('T', 0, 40, '思考较弱：建议学习战略思维框架与系统思考工具'),
    ('T', 41, 70, '思考中等：能看到长期趋势，需加强风险与创新平衡'),
    ('T', 71, 100, '思考优秀：战略前瞻与系统洞察突出，可担任首席战略官');

-- 10. 子维度得分 & 行动方案（每子维度 3 段）
INSERT INTO `sub_score_action` (`sub_dim_code`, `min_score`, `max_score`, `action_plan`)
VALUES
    ('E1', 0, 25, '行动方案：使用 SMART 工具每周设定 1 个微目标，并找同事复盘'),
    ('E1', 26, 50, '行动方案：参加 OKR 工作坊，把年度目标拆成季度 KR，并每月检查完成率'),
    ('E1', 51, 75, '行动方案：成为团队 OKR 教练，帮助成员撰写关键结果并分享最佳实践'),
    ('R1', 0, 25, '行动方案：每天午休前用 10 分钟练习「复述+情感标注」对话技巧，连续 21 天'),
    ('R1', 26, 50, '行动方案：参加非暴力沟通线上课程，并在下次冲突中刻意使用观察-感受-需求-请求四步法'),
    ('R1', 51, 75, '行动方案：成为公司「同理心」内部讲师，设计 1 小时微课并讲授 3 次'),
    ('T1', 0, 25, '行动方案：订阅 2 个行业研报，每月输出 1 份趋势摘要分享给团队'),
    ('T1', 26, 50, '行动方案：使用 PESTEL+情景规划法，为业务做 3 年沙盘推演并找高管评审'),
    ('T1', 51, 75, '行动方案：牵头建立公司级「战略雷达」系统，每季度刷新机会与风险清单');

-- 11. 联系信息（全局 1 行）
INSERT INTO `contact` (`id`, `company`, `phone`, `email`, `address`)
VALUES
    (1, 'ERT 领导力研究院', '400-123-4567', 'support@ertleader.com', '上海市浦东新区张江高科技园区 700 号');

-- 12. 自增序列矫正（可选）
-- ALTER TABLE user AUTO_INCREMENT = 10000;
-- ALTER TABLE assessment AUTO_INCREMENT = 10000;
-- ALTER TABLE question AUTO_INCREMENT = 10000;
-- ALTER TABLE `option` AUTO_INCREMENT = 100000;

SELECT '✅ 测试数据初始化完成，请开始接口/前端调试！' AS msg;