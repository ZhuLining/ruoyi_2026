# 生产环境数据库升级操作手册 V2

**升级日期**: 2026-05-13  
**基准**: 以研发环境 `ry_vue-dev.sql` 为准，同步生产环境 `ry-vue-backup.sql`  
**数据库**: MySQL 8.0  
**目标库**: `ry-vue`

---

## 一、升级前准备

### 1.1 确认当前代码版本已发布
确保以下前后端代码已打包/编译完成，随时可以上线：
- `ruoyi-admin` 后端代码（实验、资产、要素模块）
- `ruoyi-ui` 前端代码（对应页面）

### 1.2 备份生产数据库
**必须先备份，不可跳过。**

```bash
# 登录生产服务器，执行备份
mysqldump -u root -p --single-transaction --routines --triggers ry-vue \
  > /backup/ry-vue-pre-upgrade-$(date +%Y%m%d%H%M%S).sql
```

> `--single-transaction` 保证 InnoDB 一致性备份，不锁表。

### 1.3 确认脚本文件已上传到服务器
将以下文件上传到生产服务器（如 `/home/ruoyi/sql/`）：
- `prod_upgrade_v2_20260513_idempotent.sql` （**强烈推荐**，幂等安全）
- `prod_upgrade_v2_20260513.sql` （备用，纯 ALTER 语句）

---

## 二、升级范围说明

本次升级仅同步 **研发环境和生产环境之间存在实际差异** 的表结构。

### 2.1 需要变更的表（8 个）

| 表名 | 变更类型 | 主要变更 |
|------|----------|----------|
| `ele_catalog` | ALTER | +order_num, +ancestors, +del_flag, 字段扩宽, 删除parent_name, 唯一索引 |
| `ele_table` | ALTER | +del_flag, +order_num, +ele_default_value, 字段扩宽, 索引 |
| `exp_assets` | ALTER | +del_flag, +order_num, NOT NULL约束, 索引 |
| `exp_assets_use` | ALTER | +create_by, +update_by, +update_time |
| `exp_file_info` | CREATE | 新建模版文件信息表 |
| `sys_user` | ALTER | +pwd_update_date（RuoYi 框架升级补充） |
| `sys_menu` | ALTER | +route_name（RuoYi 框架升级补充） |
| `sys_notice_read` | CREATE | 新建公告已读记录表（RuoYi 框架升级补充） |

### 2.2 无需变更的表（11 个）

| 表名 | 原因 |
|------|------|
| `exp_assets_catalog` | 研发和生产结构完全一致 |
| `exp_ele` | 研发和生产结构完全一致 |
| `exp_ele_data` | 研发和生产结构完全一致 |
| `exp_exp_assets` | 研发和生产结构完全一致 |
| `exp_operate_log` | 研发和生产结构完全一致 |
| `exp_sop_tem_catalog` | 研发和生产结构完全一致 |
| `exp_sop_tem_table` | 研发和生产结构完全一致 |
| `exp_step` | 研发和生产结构完全一致 |
| `exp_table` | 研发和生产结构完全一致 |
| `exp_task_finish` | 研发和生产结构完全一致 |
| `exp_tem_table` | 研发和生产结构完全一致 |

---

## 三、升级执行步骤

### 3.1 选择业务低峰期
建议在 **非工作时间** 或 **实验/资产操作较少的时段** 执行，避免 ALTER 期间短暂锁表影响写入。

### 3.2 登录 MySQL 并选择数据库
```bash
mysql -u root -p
```
```sql
USE ry-vue;
```

### 3.3 执行升级脚本（强烈推荐幂等版）
```sql
SOURCE /home/ruoyi/sql/prod_upgrade_v2_20260513_idempotent.sql;
```

或命令行直接执行：
```bash
mysql -u root -p ry-vue < /home/ruoyi/sql/prod_upgrade_v2_20260513_idempotent.sql
```

### 3.4 观察输出
幂等版脚本执行时会输出每个操作的执行结果：
```
+------------------------------------------------+
| msg                                            |
+------------------------------------------------+
| Added column del_flag to exp_assets            |
| Column order_num already exists in exp_assets, skipped |
+------------------------------------------------+
```

如出现错误，立即记录错误信息，不要继续执行后续步骤。

---

## 四、升级后验证

### 4.1 检查变更后的表结构
```sql
-- 检查 ele_catalog
DESCRIBE ele_catalog;

-- 检查 ele_table
DESCRIBE ele_table;

-- 检查 exp_assets
DESCRIBE exp_assets;

-- 检查 exp_assets_use
DESCRIBE exp_assets_use;

-- 检查 exp_file_info 是否已创建
SHOW TABLES LIKE 'exp_file_info';

-- 检查 sys_user 新增字段
DESCRIBE sys_user;

-- 检查 sys_menu 新增字段
DESCRIBE sys_menu;

-- 检查 sys_notice_read 是否已创建
SHOW TABLES LIKE 'sys_notice_read';
```

### 4.2 检查索引是否创建成功
```sql
-- ele_catalog
SHOW INDEX FROM ele_catalog;

-- ele_table
SHOW INDEX FROM ele_table;

-- exp_assets
SHOW INDEX FROM exp_assets;
```

### 4.3 检查数据完整性
```sql
-- 确认 exp_assets 原有数据未丢失
SELECT COUNT(*) FROM exp_assets;

-- 确认 ele_catalog parent_id 已修正（无 NULL）
SELECT COUNT(*) FROM ele_catalog WHERE parent_id IS NULL;
-- 预期结果: 0

-- 确认 parent_name 已删除
SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS
WHERE TABLE_SCHEMA = 'ry-vue' AND TABLE_NAME = 'ele_catalog' AND COLUMN_NAME = 'parent_name';
-- 预期结果: 0
```

### 4.4 重启应用并功能验证
1. 重启后端服务
2. 登录系统，依次验证：
   - 元素管理页面正常加载（树形目录）
   - 资产管理页面正常加载（入库、出库）
   - 实验管理页面正常加载（文件上传）
   - 各模块的增删改查操作正常

---

## 五、回滚方案（如升级失败）

### 5.1 数据回滚
如果升级过程中出现不可逆问题，使用备份恢复：

```bash
# 停止应用服务
systemctl stop ruoyi-admin

# 恢复数据库
mysql -u root -p ry-vue < /backup/ry-vue-pre-upgrade-XXXXXXXX.sql

# 重启应用服务
systemctl start ruoyi-admin
```

### 5.2 部分回滚（仅回滚新增字段）
如果仅某几个字段有问题，可单独 DROP：
```sql
-- 示例：回滚 ele_catalog 变更
ALTER TABLE ele_catalog DROP COLUMN order_num;
ALTER TABLE ele_catalog DROP COLUMN ancestors;
ALTER TABLE ele_catalog DROP COLUMN del_flag;
ALTER TABLE ele_catalog ADD COLUMN parent_name VARCHAR(30) DEFAULT NULL COMMENT '父级目录名称' AFTER catalog_code;
ALTER TABLE ele_catalog DROP INDEX uk_catalog_code;
ALTER TABLE ele_catalog MODIFY COLUMN catalog_name VARCHAR(30) NOT NULL;
ALTER TABLE ele_catalog MODIFY COLUMN parent_id BIGINT DEFAULT NULL;
```

> 注意：`DROP COLUMN` 会永久删除该列数据，执行前确认业务不再依赖该字段。

### 5.3 sys_user 回滚
```sql
ALTER TABLE sys_user DROP COLUMN pwd_update_date;
```

### 5.4 sys_menu 回滚
```sql
ALTER TABLE sys_menu DROP COLUMN route_name;
```

### 5.5 sys_notice_read 回滚
```sql
DROP TABLE sys_notice_read;
```

---

## 六、本次升级涉及变更详情

### 6.1 ele_catalog（元素目录表）

| 变更 | 内容 |
|------|------|
| +order_num | INT DEFAULT 0，显示顺序 |
| +ancestors | VARCHAR(50) DEFAULT ''，祖级列表 |
| +del_flag | CHAR(1) DEFAULT '0'，逻辑删除 |
| MODIFY catalog_name | VARCHAR(30) → VARCHAR(50) |
| MODIFY parent_id | NULL → DEFAULT 0 |
| DROP parent_name | 冗余字段，通过 parent_id JOIN 获取 |
| ADD UNIQUE | uk_catalog_code(catalog_code) |
| UPDATE | 将 parent_id IS NULL 修正为 0 |
| UPDATE | 根目录 ancestors 初始化为 '0' |

### 6.2 ele_table（元素表）

| 变更 | 内容 |
|------|------|
| +del_flag | CHAR(1) DEFAULT '0'，逻辑删除 |
| +order_num | INT DEFAULT 0，显示顺序 |
| +ele_default_value | VARCHAR(64) DEFAULT NULL，元素默认值 |
| MODIFY ele_name | VARCHAR(30) → VARCHAR(100) |
| ADD UNIQUE | uk_ele_code(ele_code) |
| ADD KEY | idx_catalog_id(catalog_id) |
| ADD KEY | idx_ele_name(ele_name) |

### 6.3 exp_assets（资产信息表）

| 变更 | 内容 |
|------|------|
| +del_flag | CHAR(1) NOT NULL DEFAULT '0'，逻辑删除 |
| +order_num | INT DEFAULT 0，显示顺序 |
| MODIFY assets_name | NULL → NOT NULL |
| MODIFY assets_type | NULL → NOT NULL |
| MODIFY assets_threshold | NOT NULL → NULL（语义更清晰） |
| ADD KEY | idx_create_by(create_by) |
| ADD KEY | idx_assets_type(assets_type) |

> 注意：assets_name 和 assets_type 修改为 NOT NULL 前，会自动将已有 NULL 值填充为空字符串 ''。

### 6.4 exp_assets_use（资产出库记录表）

| 变更 | 内容 |
|------|------|
| +create_by | VARCHAR(64) DEFAULT ''，创建者 |
| +update_by | VARCHAR(64) DEFAULT ''，更新者 |
| +update_time | DATETIME，更新时间 |

### 6.5 exp_file_info（模版文件信息表）—— 新建表

```sql
CREATE TABLE exp_file_info (
    file_id    INT NOT NULL AUTO_INCREMENT COMMENT '模版文件id',
    file_name  VARCHAR(50)  DEFAULT '' COMMENT '模版文件名称',
    file_path  VARCHAR(255) DEFAULT '' COMMENT '模版文件路径',
    exp_id     BIGINT DEFAULT NULL COMMENT '实验ID',
    PRIMARY KEY (file_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='模版文件信息表';
```

### 6.6 sys_user（用户信息表）—— RuoYi 框架升级补充

| 变更 | 内容 |
|------|------|
| +pwd_update_date | DATETIME DEFAULT NULL，密码最后更新时间 |

> 说明：此字段由 RuoYi 框架升级引入，`SysUserMapper.xml` 中 `selectUserByUserName` 查询了该字段。生产环境和旧测试环境均缺少此字段。

### 6.7 sys_menu（菜单权限表）—— RuoYi 框架升级补充

| 变更 | 内容 |
|------|------|
| +route_name | VARCHAR(50) DEFAULT ''，路由名称 |

> 说明：此字段由 RuoYi 框架升级引入，`SysMenuMapper.selectMenuTreeAll` 查询了该字段。生产环境和旧测试环境均缺少此字段。

### 6.8 sys_notice_read（公告已读记录表）—— RuoYi 框架升级补充

```sql
CREATE TABLE sys_notice_read (
    read_id   BIGINT NOT NULL AUTO_INCREMENT COMMENT '已读主键',
    notice_id INT    NOT NULL COMMENT '公告id',
    user_id   BIGINT NOT NULL COMMENT '用户id',
    read_time DATETIME NOT NULL COMMENT '阅读时间',
    PRIMARY KEY (read_id),
    UNIQUE KEY uk_user_notice (user_id, notice_id) COMMENT '同一用户同一公告只记录一次'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='公告已读记录表';
```

> 说明：此表由 RuoYi 框架升级引入，`SysNoticeReadServiceImpl.selectNoticeListWithReadStatus` 查询了此表。生产环境和旧测试环境均缺少此表。

---

## 七、注意事项

1. **脚本选择**：生产环境强烈建议使用 `prod_upgrade_v2_20260513_idempotent.sql`，重复执行不会报错。
2. **锁表风险**：`ALTER TABLE` 在 MySQL 8.0 中大部分场景为在线 DDL（Online DDL），但仍可能在表末尾添加列时短暂锁表。建议在低峰期执行。
3. **唯一索引冲突**：`uk_catalog_code`、`uk_ele_code` 为唯一索引。如果生产数据中存在重复值，创建索引会失败。执行前建议先检查：
   ```sql
   SELECT catalog_code, COUNT(*) FROM ele_catalog GROUP BY catalog_code HAVING COUNT(*) > 1;
   SELECT ele_code, COUNT(*) FROM ele_table GROUP BY ele_code HAVING COUNT(*) > 1;
   ```
4. **与 V1 脚本的区别**：V2 脚本基于实际研发/生产差异生成，只包含真正需要同步的变更（5 个表），去掉了 V1 中大量研发环境也没有的字段（如 exp_ele 的 del_flag、exp_step 的 state 等）。
5. **研发环境也需执行**：虽然研发环境比生产更接近目标结构，但研发环境的 `ele_catalog` 也缺少 `idx_parent_id`、`idx_state`、`idx_create_by` 等索引（这些索引来自 `alter_script_tables.sql`，两边都没有）。如需添加，可在研发和生产都执行后另行补充。

---

## 八、执行人签字

| 项目 | 内容 |
|------|------|
| 执行人 | |
| 执行时间 | |
| 执行结果 | 成功 / 失败 |
| 异常记录 | |
| 验证人 | |

---

## 附录：相关文件清单

| 文件 | 路径 | 说明 |
|------|------|------|
| 研发备份 SQL | `LiangYan/ry_vue-dev.sql` | 升级基准 |
| 生产备份 SQL | `LiangYan/ry-vue-backup.sql` | 升级前备份 |
| 升级脚本 V2（幂等版） | `sql/prod_upgrade_v2_20260513_idempotent.sql` | **推荐使用** |
| 升级脚本 V2（普通版） | `sql/prod_upgrade_v2_20260513.sql` | 备用 |
| 操作手册 V2 | `sql/PROD_UPGRADE_GUIDE_V2_20260513.md` | 本文档 |
| 研发核对报告 | `sql/DEV_ENV_CHECK_REPORT_20260513.md` | 详细核对过程 |
