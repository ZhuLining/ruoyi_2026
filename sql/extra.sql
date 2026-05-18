-- 清理已存在的要素和管理
DELETE FROM sys_menu WHERE menu_name = '要素管理' OR parent_id = 2002;
DELETE FROM sys_menu WHERE menu_name = '资产管理' OR parent_id = 2003;

-- ============================================================
-- 资产管理-新 菜单及权限初始化脚本
-- 挂在实验管理(parent_id=2000)下
-- 执行后刷新页面即可访问

-- ============================
-- 1. 二级菜单：资产管理-新
-- parent_id=200 表示挂在"实验管理"下
-- ============================
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES (2100, '资产管理', 2000, 6, 'expAssets', 'exp/assets/index', '', '', 1, 0, 'C', '0', '0', 'system:expAssets:list', 'tree-table', 'admin', sysdate(), '', null, '资产管理菜单');

-- ============================
-- 2. 按钮权限（挂在 资产管理-新 下）
-- ============================
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES (2101, '资产查询', 2100, 1,  '', '', '', '', 1, 0, 'F', '0', '0', 'system:expAssets:query',  '#', 'admin', sysdate(), '', null, '');

INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES (2102, '资产入库', 2100, 2,  '', '', '', '', 1, 0, 'F', '0', '0', 'system:expAssets:add',    '#', 'admin', sysdate(), '', null, '');

INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES (2103, '资产修改', 2100, 3,  '', '', '', '', 1, 0, 'F', '0', '0', 'system:expAssets:edit',   '#', 'admin', sysdate(), '', null, '');

INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES (2104, '资产删除', 2100, 4,  '', '', '', '', 1, 0, 'F', '0', '0', 'system:expAssets:remove','#', 'admin', sysdate(), '', null, '');

INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES (2105, '资产出库', 2100, 5,  '', '', '', '', 1, 0, 'F', '0', '0', 'system:expAssets:use',   '#', 'admin', sysdate(), '', null, '');

-- 目录查询
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES (2106, '目录查询', 2100, 6,  '', '', '', '', 1, 0, 'F', '0', '0', 'system:assetsCatalog:query',  '#', 'admin', sysdate(), '', null, '');
-- 目录新增
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES (2107, '目录新增', 2100, 7,  '', '', '', '', 1, 0, 'F', '0', '0', 'system:assetsCatalog:add',    '#', 'admin', sysdate(), '', null, '');
-- 目录修改
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES (2108, '目录修改', 2100, 8,  '', '', '', '', 1, 0, 'F', '0', '0', 'system:assetsCatalog:edit',   '#', 'admin', sysdate(), '', null, '');
-- 目录删除
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES (2109, '目录删除', 2100, 9,  '', '', '', '', 1, 0, 'F', '0', '0', 'system:assetsCatalog:remove','#', 'admin', sysdate(), '', null, '');


-- ============================================================
-- 要素管理模块菜单及权限初始化脚本
-- 执行后无需手动配置菜单，直接刷新页面即可访问
-- ============================================================

-- 1. 二级菜单：要素管理
-- parent_id=1 表示挂在"系统管理"下
-- ============================
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES (118, '要素管理', 2000, 10, 'ele', 'system/ele/index', '', '', 1, 0, 'C', '0', '0', 'system:eleTable:list', 'tree', 'admin', sysdate(), '', null, '要素管理菜单');

-- ============================
-- 2. 按钮权限（挂在 要素管理 下）
-- ============================
-- 元素查询
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES (1061, '元素查询', 118, 1,  '', '', '', '', 1, 0, 'F', '0', '0', 'system:eleTable:query',  '#', 'admin', sysdate(), '', null, '');
-- 元素新增
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES (1062, '元素新增', 118, 2,  '', '', '', '', 1, 0, 'F', '0', '0', 'system:eleTable:add',    '#', 'admin', sysdate(), '', null, '');
-- 元素修改
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES (1063, '元素修改', 118, 3,  '', '', '', '', 1, 0, 'F', '0', '0', 'system:eleTable:edit',   '#', 'admin', sysdate(), '', null, '');
-- 元素删除
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES (1064, '元素删除', 118, 4,  '', '', '', '', 1, 0, 'F', '0', '0', 'system:eleTable:remove','#', 'admin', sysdate(), '', null, '');
-- 目录查询
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES (1065, '目录查询', 118, 5,  '', '', '', '', 1, 0, 'F', '0', '0', 'system:eleCatalog:query',  '#', 'admin', sysdate(), '', null, '');
-- 目录新增
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES (1066, '目录新增', 118, 6,  '', '', '', '', 1, 0, 'F', '0', '0', 'system:eleCatalog:add',    '#', 'admin', sysdate(), '', null, '');
-- 目录修改
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES (1067, '目录修改', 118, 7,  '', '', '', '', 1, 0, 'F', '0', '0', 'system:eleCatalog:edit',   '#', 'admin', sysdate(), '', null, '');
-- 目录删除
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES (1068, '目录删除', 118, 8,  '', '', '', '', 1, 0, 'F', '0', '0', 'system:eleCatalog:remove','#', 'admin', sysdate(), '', null, '');
