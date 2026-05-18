-- ============================================================
-- 生产环境数据库升级脚本 V2
-- 生成日期: 2026-05-13
-- 基准: 以研发环境(ry_vue-dev.sql)为准，同步生产环境(ry-vue-backup.sql)
-- 适用数据库: MySQL 8.0
-- 执行前必读:
--   1. 务必先备份生产数据库
--   2. 建议在业务低峰期执行
--   3. 所有操作均为 ALTER，不删除行数据
-- ============================================================

SET FOREIGN_KEY_CHECKS = 0;

-- ============================================================
-- 一、ele_catalog（元素目录表）
-- ============================================================

-- 1.1 新增字段
ALTER TABLE ele_catalog
    ADD COLUMN order_num INT DEFAULT 0 COMMENT '显示顺序' AFTER catalog_name,
    ADD COLUMN ancestors VARCHAR(50) DEFAULT '' COMMENT '祖级列表（如 0,100,101）' AFTER parent_id,
    ADD COLUMN del_flag CHAR(1) DEFAULT '0' COMMENT '删除标志（0存在 2删除）' AFTER state;

-- 1.2 字段类型调整
-- 先处理 NULL 值，再修改约束
UPDATE ele_catalog SET parent_id = 0 WHERE parent_id IS NULL;
ALTER TABLE ele_catalog
    MODIFY COLUMN catalog_name VARCHAR(50) NOT NULL COMMENT '目录名称',
    MODIFY COLUMN parent_id BIGINT DEFAULT 0 COMMENT '父目录ID（0表示顶级）';

-- 1.3 删除冗余字段
ALTER TABLE ele_catalog DROP COLUMN parent_name;

-- 1.4 添加索引
ALTER TABLE ele_catalog ADD UNIQUE KEY uk_catalog_code (catalog_code);

-- 1.5 初始化 ancestors（已有数据的补偿）
UPDATE ele_catalog SET ancestors = '0' WHERE parent_id = 0 AND (ancestors IS NULL OR ancestors = '');


-- ============================================================
-- 二、ele_table（元素表）
-- ============================================================

-- 2.1 新增字段
ALTER TABLE ele_table
    ADD COLUMN del_flag CHAR(1) DEFAULT '0' COMMENT '删除标志（0存在 2删除）' AFTER state,
    ADD COLUMN order_num INT DEFAULT 0 COMMENT '显示顺序' AFTER dept_id,
    ADD COLUMN ele_default_value VARCHAR(64) DEFAULT NULL COMMENT '元素默认值' AFTER order_num;

-- 2.2 字段类型调整
ALTER TABLE ele_table
    MODIFY COLUMN ele_name VARCHAR(100) NOT NULL COMMENT '元素名称';

-- 2.3 添加索引
ALTER TABLE ele_table ADD UNIQUE KEY uk_ele_code (ele_code);
ALTER TABLE ele_table ADD KEY idx_catalog_id (catalog_id);
ALTER TABLE ele_table ADD KEY idx_ele_name (ele_name);


-- ============================================================
-- 三、exp_assets（资产信息表）
-- ============================================================

-- 3.1 新增字段
ALTER TABLE exp_assets
    ADD COLUMN del_flag CHAR(1) NOT NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）' AFTER status,
    ADD COLUMN order_num INT DEFAULT 0 COMMENT '显示顺序' AFTER remark;

-- 3.2 字段类型调整（先处理已有 NULL 值）
UPDATE exp_assets SET assets_name = '' WHERE assets_name IS NULL;
UPDATE exp_assets SET assets_type = '' WHERE assets_type IS NULL;

ALTER TABLE exp_assets
    MODIFY COLUMN assets_name VARCHAR(64) NOT NULL COMMENT '资产名称',
    MODIFY COLUMN assets_type VARCHAR(64) NOT NULL COMMENT '资产类型',
    MODIFY COLUMN assets_threshold BIGINT DEFAULT 0 NULL COMMENT '最低库存';

-- 3.3 添加索引
ALTER TABLE exp_assets ADD KEY idx_create_by (create_by);
ALTER TABLE exp_assets ADD KEY idx_assets_type (assets_type);


-- ============================================================
-- 四、exp_assets_use（资产出库记录表）
-- ============================================================

-- 4.1 新增审计字段
ALTER TABLE exp_assets_use
    ADD COLUMN create_by VARCHAR(64) DEFAULT '' COMMENT '创建者' AFTER remark,
    ADD COLUMN update_by VARCHAR(64) DEFAULT '' COMMENT '更新者' AFTER create_by,
    ADD COLUMN update_time DATETIME NULL COMMENT '更新时间' AFTER update_by;


-- ============================================================
-- 五、exp_file_info（模版文件信息表）—— 新建表
-- ============================================================

CREATE TABLE exp_file_info (
    file_id    INT NOT NULL AUTO_INCREMENT COMMENT '模版文件id',
    file_name  VARCHAR(50)  DEFAULT '' COMMENT '模版文件名称',
    file_path  VARCHAR(255) DEFAULT '' COMMENT '模版文件路径',
    exp_id     BIGINT DEFAULT NULL COMMENT '实验ID',
    PRIMARY KEY (file_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='模版文件信息表';


-- ============================================================
-- 六、sys_user（用户信息表）—— RuoYi 框架升级补充
-- ============================================================
-- 新代码 SysUserMapper.xml 查询了 pwd_update_date，生产环境缺少此字段
ALTER TABLE sys_user
    ADD COLUMN pwd_update_date DATETIME DEFAULT NULL COMMENT '密码最后更新时间' AFTER login_date;


-- ============================================================
-- 七、sys_menu（菜单权限表）—— RuoYi 框架升级补充
-- ============================================================
-- 新代码 SysMenuMapper.selectMenuTreeAll 查询了 route_name，生产环境缺少此字段
ALTER TABLE sys_menu
    ADD COLUMN route_name VARCHAR(50) DEFAULT '' COMMENT '路由名称' AFTER perms;


-- ============================================================
-- 八、sys_notice_read（公告已读记录表）—— RuoYi 框架升级补充
-- ============================================================
-- 新代码 SysNoticeReadServiceImpl.selectNoticeListWithReadStatus 查询了此表，生产环境缺少此表
CREATE TABLE sys_notice_read (
    read_id   BIGINT NOT NULL AUTO_INCREMENT COMMENT '已读主键',
    notice_id INT    NOT NULL COMMENT '公告id',
    user_id   BIGINT NOT NULL COMMENT '用户id',
    read_time DATETIME NOT NULL COMMENT '阅读时间',
    PRIMARY KEY (read_id),
    UNIQUE KEY uk_user_notice (user_id, notice_id) COMMENT '同一用户同一公告只记录一次'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='公告已读记录表';


SET FOREIGN_KEY_CHECKS = 1;

-- ============================================================
-- 升级完成
-- ============================================================
