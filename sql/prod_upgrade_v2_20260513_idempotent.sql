-- ============================================================
-- 生产环境数据库升级脚本 V2（幂等版）
-- 生成日期: 2026-05-13
-- 基准: 以研发环境(ry_vue-dev.sql)为准，同步生产环境(ry-vue-backup.sql)
-- 适用数据库: MySQL 8.0
-- 特性: 重复执行安全，已存在的列/索引自动跳过
-- 执行前必读:
--   1. 务必先备份生产数据库
--   2. 建议在业务低峰期执行
--   3. 所有操作均为 ALTER / CREATE，不删除行数据
-- ============================================================

SET FOREIGN_KEY_CHECKS = 0;

-- ------------------------------------------------------------
-- 辅助存储过程（幂等工具函数）
-- ------------------------------------------------------------
DELIMITER $$

DROP PROCEDURE IF EXISTS SafeAddColumn$$
CREATE PROCEDURE SafeAddColumn(
    IN p_table VARCHAR(64),
    IN p_column VARCHAR(64),
    IN p_definition VARCHAR(500),
    IN p_after VARCHAR(64)
)
BEGIN
    DECLARE col_exists INT DEFAULT 0;
    SELECT COUNT(*) INTO col_exists FROM INFORMATION_SCHEMA.COLUMNS
    WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = p_table AND COLUMN_NAME = p_column;
    IF col_exists = 0 THEN
        SET @sql = CONCAT('ALTER TABLE `', p_table, '` ADD COLUMN `', p_column, '` ', p_definition);
        IF p_after IS NOT NULL AND p_after != '' THEN
            SET @sql = CONCAT(@sql, ' AFTER `', p_after, '`');
        END IF;
        PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;
        SELECT CONCAT('Added column ', p_column, ' to ', p_table) AS msg;
    ELSE
        SELECT CONCAT('Column ', p_column, ' already exists in ', p_table, ', skipped') AS msg;
    END IF;
END$$

DROP PROCEDURE IF EXISTS SafeDropColumn$$
CREATE PROCEDURE SafeDropColumn(
    IN p_table VARCHAR(64),
    IN p_column VARCHAR(64)
)
BEGIN
    DECLARE col_exists INT DEFAULT 0;
    SELECT COUNT(*) INTO col_exists FROM INFORMATION_SCHEMA.COLUMNS
    WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = p_table AND COLUMN_NAME = p_column;
    IF col_exists > 0 THEN
        SET @sql = CONCAT('ALTER TABLE `', p_table, '` DROP COLUMN `', p_column, '`');
        PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;
        SELECT CONCAT('Dropped column ', p_column, ' from ', p_table) AS msg;
    ELSE
        SELECT CONCAT('Column ', p_column, ' not found in ', p_table, ', skipped') AS msg;
    END IF;
END$$

DROP PROCEDURE IF EXISTS SafeAddIndex$$
CREATE PROCEDURE SafeAddIndex(
    IN p_table VARCHAR(64),
    IN p_index VARCHAR(64),
    IN p_columns VARCHAR(500),
    IN p_unique INT
)
BEGIN
    DECLARE idx_exists INT DEFAULT 0;
    SELECT COUNT(*) INTO idx_exists FROM INFORMATION_SCHEMA.STATISTICS
    WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = p_table AND INDEX_NAME = p_index;
    IF idx_exists = 0 THEN
        IF p_unique = 1 THEN
            SET @sql = CONCAT('ALTER TABLE `', p_table, '` ADD UNIQUE KEY `', p_index, '` (', p_columns, ')');
        ELSE
            SET @sql = CONCAT('ALTER TABLE `', p_table, '` ADD KEY `', p_index, '` (', p_columns, ')');
        END IF;
        PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;
        SELECT CONCAT('Added index ', p_index, ' to ', p_table) AS msg;
    ELSE
        SELECT CONCAT('Index ', p_index, ' already exists on ', p_table, ', skipped') AS msg;
    END IF;
END$$

DROP PROCEDURE IF EXISTS SafeModifyColumn$$
CREATE PROCEDURE SafeModifyColumn(
    IN p_table VARCHAR(64),
    IN p_column VARCHAR(64),
    IN p_definition VARCHAR(500)
)
BEGIN
    SET @sql = CONCAT('ALTER TABLE `', p_table, '` MODIFY COLUMN `', p_column, '` ', p_definition);
    PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;
    SELECT CONCAT('Modified column ', p_column, ' in ', p_table) AS msg;
END$$

DROP PROCEDURE IF EXISTS SafeCreateTable$$
CREATE PROCEDURE SafeCreateTable(
    IN p_table VARCHAR(64),
    IN p_ddl VARCHAR(2000)
)
BEGIN
    DECLARE tbl_exists INT DEFAULT 0;
    SELECT COUNT(*) INTO tbl_exists FROM INFORMATION_SCHEMA.TABLES
    WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = p_table;
    IF tbl_exists = 0 THEN
        SET @sql = p_ddl;
        PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;
        SELECT CONCAT('Created table ', p_table) AS msg;
    ELSE
        SELECT CONCAT('Table ', p_table, ' already exists, skipped') AS msg;
    END IF;
END$$

DELIMITER ;


-- ============================================================
-- 一、ele_catalog（元素目录表）
-- ============================================================

CALL SafeAddColumn('ele_catalog', 'order_num', "INT DEFAULT 0 COMMENT '显示顺序'", 'catalog_name');
CALL SafeAddColumn('ele_catalog', 'ancestors', "VARCHAR(50) DEFAULT '' COMMENT '祖级列表（如 0,100,101）'", 'parent_id');
CALL SafeAddColumn('ele_catalog', 'del_flag', "CHAR(1) DEFAULT '0' COMMENT '删除标志（0存在 2删除）'", 'state');

UPDATE ele_catalog SET parent_id = 0 WHERE parent_id IS NULL;

CALL SafeModifyColumn('ele_catalog', 'catalog_name', "VARCHAR(50) NOT NULL COMMENT '目录名称'");
CALL SafeModifyColumn('ele_catalog', 'parent_id', "BIGINT DEFAULT 0 COMMENT '父目录ID（0表示顶级）'");

CALL SafeDropColumn('ele_catalog', 'parent_name');

CALL SafeAddIndex('ele_catalog', 'uk_catalog_code', 'catalog_code', 1);

UPDATE ele_catalog SET ancestors = '0' WHERE parent_id = 0 AND (ancestors IS NULL OR ancestors = '');


-- ============================================================
-- 二、ele_table（元素表）
-- ============================================================

CALL SafeAddColumn('ele_table', 'del_flag', "CHAR(1) DEFAULT '0' COMMENT '删除标志（0存在 2删除）'", 'state');
CALL SafeAddColumn('ele_table', 'order_num', "INT DEFAULT 0 COMMENT '显示顺序'", 'dept_id');
CALL SafeAddColumn('ele_table', 'ele_default_value', "VARCHAR(64) DEFAULT NULL COMMENT '元素默认值'", 'order_num');

CALL SafeModifyColumn('ele_table', 'ele_name', "VARCHAR(100) NOT NULL COMMENT '元素名称'");

CALL SafeAddIndex('ele_table', 'uk_ele_code', 'ele_code', 1);
CALL SafeAddIndex('ele_table', 'idx_catalog_id', 'catalog_id', 0);
CALL SafeAddIndex('ele_table', 'idx_ele_name', 'ele_name', 0);


-- ============================================================
-- 三、exp_assets（资产信息表）
-- ============================================================

CALL SafeAddColumn('exp_assets', 'del_flag', "CHAR(1) NOT NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）'", 'status');
CALL SafeAddColumn('exp_assets', 'order_num', "INT DEFAULT 0 COMMENT '显示顺序'", 'remark');

UPDATE exp_assets SET assets_name = '' WHERE assets_name IS NULL;
UPDATE exp_assets SET assets_type = '' WHERE assets_type IS NULL;

CALL SafeModifyColumn('exp_assets', 'assets_name', "VARCHAR(64) NOT NULL COMMENT '资产名称'");
CALL SafeModifyColumn('exp_assets', 'assets_type', "VARCHAR(64) NOT NULL COMMENT '资产类型'");
CALL SafeModifyColumn('exp_assets', 'assets_threshold', "BIGINT DEFAULT 0 NULL COMMENT '最低库存'");

CALL SafeAddIndex('exp_assets', 'idx_create_by', 'create_by', 0);
CALL SafeAddIndex('exp_assets', 'idx_assets_type', 'assets_type', 0);


-- ============================================================
-- 四、exp_assets_use（资产出库记录表）
-- ============================================================

CALL SafeAddColumn('exp_assets_use', 'create_by', "VARCHAR(64) DEFAULT '' COMMENT '创建者'", 'remark');
CALL SafeAddColumn('exp_assets_use', 'update_by', "VARCHAR(64) DEFAULT '' COMMENT '更新者'", 'create_by');
CALL SafeAddColumn('exp_assets_use', 'update_time', "DATETIME NULL COMMENT '更新时间'", 'update_by');


-- ============================================================
-- 五、exp_file_info（模版文件信息表）—— 新建表
-- ============================================================

CALL SafeCreateTable('exp_file_info',
    'CREATE TABLE exp_file_info (
        file_id    INT NOT NULL AUTO_INCREMENT COMMENT \'模版文件id\',
        file_name  VARCHAR(50)  DEFAULT \'\' COMMENT \'模版文件名称\',
        file_path  VARCHAR(255) DEFAULT \'\' COMMENT \'模版文件路径\',
        exp_id     BIGINT DEFAULT NULL COMMENT \'实验ID\',
        PRIMARY KEY (file_id)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT=\'模版文件信息表\'');


-- ============================================================
-- 六、sys_user（用户信息表）—— RuoYi 框架升级补充
-- ============================================================
-- 新代码 SysUserMapper.xml 查询了 pwd_update_date，生产环境缺少此字段
CALL SafeAddColumn('sys_user', 'pwd_update_date', "DATETIME DEFAULT NULL COMMENT '密码最后更新时间'", 'login_date');


-- ============================================================
-- 七、sys_menu（菜单权限表）—— RuoYi 框架升级补充
-- ============================================================
-- 新代码 SysMenuMapper.selectMenuTreeAll 查询了 route_name，生产环境缺少此字段
CALL SafeAddColumn('sys_menu', 'route_name', "VARCHAR(50) DEFAULT '' COMMENT '路由名称'", 'perms');


-- ============================================================
-- 八、sys_notice_read（公告已读记录表）—— RuoYi 框架升级补充
-- ============================================================
-- 新代码 SysNoticeReadServiceImpl.selectNoticeListWithReadStatus 查询了此表，生产环境缺少此表
CALL SafeCreateTable('sys_notice_read',
    'CREATE TABLE sys_notice_read (
        read_id   BIGINT NOT NULL AUTO_INCREMENT COMMENT \'已读主键\',
        notice_id INT    NOT NULL COMMENT \'公告id\',
        user_id   BIGINT NOT NULL COMMENT \'用户id\',
        read_time DATETIME NOT NULL COMMENT \'阅读时间\',
        PRIMARY KEY (read_id),
        UNIQUE KEY uk_user_notice (user_id, notice_id) COMMENT \'同一用户同一公告只记录一次\'
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT=\'公告已读记录表\'');


-- ============================================================
-- 清理辅助存储过程
-- ============================================================
DROP PROCEDURE IF EXISTS SafeAddColumn;
DROP PROCEDURE IF EXISTS SafeDropColumn;
DROP PROCEDURE IF EXISTS SafeAddIndex;
DROP PROCEDURE IF EXISTS SafeModifyColumn;
DROP PROCEDURE IF EXISTS SafeCreateTable;

SET FOREIGN_KEY_CHECKS = 1;

SELECT '生产数据库升级 V2 完成！' AS result;
