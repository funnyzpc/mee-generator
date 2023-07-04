
DROP TABLE IF EXISTS MEE_GENERATOR.CODE_GEN_CONFIG;
CREATE TABLE mee_generator.code_gen_config (
  CONFIG_ID INT8 PRIMARY KEY  COMMENT 'ID',
  TABLE_NAME VARCHAR(255) COMMENT '表名',
  AUTHOR VARCHAR(255) COMMENT '作者',
  COVER VARCHAR(1) COMMENT '是否覆盖',
  MODULE_NAME VARCHAR(255) COMMENT '模块名称',
  PACK VARCHAR(255) COMMENT '至于哪个包下',
  PATH VARCHAR(255) COMMENT '前端代码生成的路径',
  API_PATH VARCHAR(255) COMMENT '前端Api文件路径',
  PREFIX VARCHAR(255) COMMENT '表前缀',
  API_ALIAS VARCHAR(255) COMMENT '接口名称'
) ENGINE=INNODB DEFAULT CHARSET=UTF8MB4 COMMENT='代码生成器配置';


DROP TABLE IF EXISTS MEE_GENERATOR.GEN_TABLE;
CREATE TABLE mee_generator.GEN_TABLE (
  TABLE_ID INT8 PRIMARY KEY COMMENT '编号',
  TABLE_NAME VARCHAR(200) COMMENT '表名称',
  TABLE_COMMENT VARCHAR(500) COMMENT '表描述',
  SUB_TABLE_NAME VARCHAR(64) COMMENT '关联子表的表名',
  SUB_TABLE_FK_NAME VARCHAR(64) COMMENT '子表关联的外键名',
  CLASS_NAME VARCHAR(100) COMMENT '实体类名称',
  TPL_CATEGORY VARCHAR(200) COMMENT '使用的模板',
  PACKAGE_NAME VARCHAR(100) COMMENT '生成包路径',
  MODULE_NAME VARCHAR(30) COMMENT '生成模块名',
  BUSINESS_NAME VARCHAR(30) COMMENT '生成业务名',
  FUNCTION_NAME VARCHAR(50) COMMENT '生成功能名',
  FUNCTION_VERSION VARCHAR(20) DEFAULT 'v1.0' COMMENT '功能版本',
  FUNCTION_AUTHOR VARCHAR(50) COMMENT '生成功能作者',
  GEN_TYPE CHAR(1) COMMENT '生成代码方式',
  GEN_PATH VARCHAR(200) COMMENT '生成路径',
  OPTIONS VARCHAR(1000) COMMENT '其它生成选项',
  CREATE_BY VARCHAR(64) COMMENT '创建者',
  CREATE_TIME TIMESTAMP(6) COMMENT '创建时间',
  UPDATE_BY VARCHAR(64) COMMENT '更新者',
  UPDATE_TIME TIMESTAMP(6) COMMENT '更新时间',
  REMARK VARCHAR(500) COMMENT '备注'
)ENGINE=INNODB DEFAULT CHARSET=UTF8MB4 COMMENT='代码生成业务表';


INSERT INTO gen_table (table_id, table_name, table_comment, sub_table_name, sub_table_fk_name, class_name, tpl_category, package_name, module_name, business_name, function_name, function_version, function_author, gen_type, gen_path, options, create_by, create_time, update_by, update_time, remark) VALUES (6, 'sys_user_post', '用户与岗位关联表', NULL, NULL, 'SysRoleMenu', 'crud', 'com.mee.generator.system', 'system', 'menu', '角色和菜单关联', 'v1.0', 'shadow', '0', '/', NULL, 'sys', '2022-10-28 15:36:29.124', 'sys', '2022-10-28 15:36:29.124', NULL);
INSERT INTO gen_table (table_id, table_name, table_comment, sub_table_name, sub_table_fk_name, class_name, tpl_category, package_name, module_name, business_name, function_name, function_version, function_author, gen_type, gen_path, options, create_by, create_time, update_by, update_time, remark) VALUES (9, 'sys_menu', '菜单权限表', NULL, NULL, 'SysDept', 'crud', 'com.mee.generator.system', 'system', 'dept', '部门', 'v1.0', 'shadow', '0', '/', NULL, 'sys', '2022-10-28 15:36:29.186', 'sys', '2022-10-28 15:36:29.186', NULL);
INSERT INTO gen_table (table_id, table_name, table_comment, sub_table_name, sub_table_fk_name, class_name, tpl_category, package_name, module_name, business_name, function_name, function_version, function_author, gen_type, gen_path, options, create_by, create_time, update_by, update_time, remark) VALUES (10, 'sys_post', '岗位信息表', NULL, NULL, 'SysMenu', 'crud', 'com.mee.generator.system', 'system', 'menu', '菜单权限', 'v1.0', 'shadow', '0', '/', NULL, 'sys', '2022-10-28 15:36:29.205', 'sys', '2022-10-28 15:36:29.205', NULL);
INSERT INTO gen_table (table_id, table_name, table_comment, sub_table_name, sub_table_fk_name, class_name, tpl_category, package_name, module_name, business_name, function_name, function_version, function_author, gen_type, gen_path, options, create_by, create_time, update_by, update_time, remark) VALUES (11, 'sys_job_log', '定时任务调度日志表', NULL, NULL, 'SysPost', 'crud', 'com.mee.generator.system', 'system', 'post', '岗位信息', 'v1.0', 'shadow', '0', '/', NULL, 'sys', '2022-10-28 15:36:29.23', 'sys', '2022-10-28 15:36:29.23', NULL);
INSERT INTO gen_table (table_id, table_name, table_comment, sub_table_name, sub_table_fk_name, class_name, tpl_category, package_name, module_name, business_name, function_name, function_version, function_author, gen_type, gen_path, options, create_by, create_time, update_by, update_time, remark) VALUES (12, 'sys_role', '角色信息表', NULL, NULL, 'SysJobLog', 'crud', 'com.mee.generator.system', 'system', 'log', '定时任务调度日志', 'v1.0', 'shadow', '0', '/', NULL, 'sys', '2022-10-28 15:36:29.247', 'sys', '2022-10-28 15:36:29.247', NULL);
INSERT INTO gen_table (table_id, table_name, table_comment, sub_table_name, sub_table_fk_name, class_name, tpl_category, package_name, module_name, business_name, function_name, function_version, function_author, gen_type, gen_path, options, create_by, create_time, update_by, update_time, remark) VALUES (7, 'sys_user_role', '用户和角色关联表', 'NONE', 'NONE', 'SysUserPost', 'crud', 'com.mee.generator.system', 'system', 'post', '用户与岗位关联', 'v1.0', 'shadow', '0', '/', '{}', 'sys', '2022-10-28 15:36:29.142', 'sys', '2022-10-28 15:36:29.142', NULL);
INSERT INTO gen_table (table_id, table_name, table_comment, sub_table_name, sub_table_fk_name, class_name, tpl_category, package_name, module_name, business_name, function_name, function_version, function_author, gen_type, gen_path, options, create_by, create_time, update_by, update_time, remark) VALUES (13, 'sys_config', '参数配置表', 'init', NULL, 'SysRole', 'crud', 'com.mee.generator.system', 'system', 'role', '角色信息', 'v1.0', 'shadow', '0', '/', NULL, 'sys', '2022-10-28 15:36:29.265', 'sys', '2022-10-28 15:36:29.265', NULL);
INSERT INTO gen_table (table_id, table_name, table_comment, sub_table_name, sub_table_fk_name, class_name, tpl_category, package_name, module_name, business_name, function_name, function_version, function_author, gen_type, gen_path, options, create_by, create_time, update_by, update_time, remark) VALUES (2303131105241000, 'sys_quartz_job', '定时任务', NULL, NULL, 'SysQuartzJob', 'crud', 'com.mee.system', 'system', 'job', '定时任务', 'v1.0', 'shadow', NULL, NULL, NULL, 'auto', '2023-03-13 11:05:24.159', NULL, NULL, NULL);
INSERT INTO gen_table (table_id, table_name, table_comment, sub_table_name, sub_table_fk_name, class_name, tpl_category, package_name, module_name, business_name, function_name, function_version, function_author, gen_type, gen_path, options, create_by, create_time, update_by, update_time, remark) VALUES (2303312215391000, 'qrtz_task_log', '任务执行日志', NULL, NULL, 'QrtzTaskLog', 'crud', 'com.mee.system', 'system', 'log', '任务执行日志', 'v1.0', 'shadow', NULL, NULL, NULL, 'auto', '2023-03-31 22:15:39.063977', NULL, NULL, NULL);

DROP TABLE IF EXISTS MEE_GENERATOR.GEN_TABLE_COLUMN;
CREATE TABLE MEE_GENERATOR.GEN_TABLE_COLUMN (
  COLUMN_ID INT8 PRIMARY KEY COMMENT '编号',
  TABLE_ID INT8 COMMENT '归属表编号',
  COLUMN_NAME VARCHAR(200) COMMENT '列名称',
  COLUMN_COMMENT VARCHAR(500) COMMENT '列描述',
  COLUMN_TYPE VARCHAR(100) COMMENT '列类型',
  JAVA_TYPE VARCHAR(500) COMMENT 'JAVA类型',
  JAVA_FIELD VARCHAR(200) COMMENT 'JAVA字段名',
  IS_PK CHAR(1) COMMENT '是否主键（1是）',
  IS_INCREMENT CHAR(1) COMMENT '是否自增（1是）',
  IS_REQUIRED CHAR(1) COMMENT '是否必填（1是）',
  IS_INSERT CHAR(1) COMMENT '是否为插入字段（1是）',
  IS_EDIT CHAR(1) COMMENT '是否编辑字段（1是）',
  IS_LIST CHAR(1) COMMENT '是否列表字段（1是）',
  IS_QUERY CHAR(1) COMMENT '是否查询字段（1是）',
  QUERY_TYPE VARCHAR(200) COMMENT '查询方式（等于、不等于、大于、小于、范围）',
  HTML_TYPE VARCHAR(200) COMMENT '显示类型（文本框、文本域、下拉框、复选框、单选框、日期控件）',
  DICT_TYPE VARCHAR(200) COMMENT '字典类型',
  SORT INT4 COMMENT '排序',
  CREATE_BY VARCHAR(64) COMMENT '创建者',
  CREATE_TIME TIMESTAMP(6) COMMENT '创建时间'
)ENGINE=INNODB DEFAULT CHARSET=UTF8MB4 COMMENT='代码生成业务表字段';

INSERT INTO gen_table_column (column_id, table_id, column_name, column_comment, column_type, java_type, java_field, is_pk, is_increment, is_required, is_insert, is_edit, is_list, is_query, query_type, html_type, dict_type, sort, create_by, create_time) VALUES (221102153705001022, 15, 'update_time', '更新时间', 'datetime', 'Date', 'updateTime', '0', '0', NULL, '1', '1', NULL, NULL, '=', 'datetime', '', 18, 'init', '2022-11-02 15:37:42.272723');
INSERT INTO gen_table_column (column_id, table_id, column_name, column_comment, column_type, java_type, java_field, is_pk, is_increment, is_required, is_insert, is_edit, is_list, is_query, query_type, html_type, dict_type, sort, create_by, create_time) VALUES (2303312215391001, 2303312215391000, 'id', '主键', 'numeric', 'Long', 'id', '1', '0', '1', '1', NULL, NULL, NULL, 'EQ', 'input', NULL, 1, 'auto', '2023-03-31 22:15:39.063977');
INSERT INTO gen_table_column (column_id, table_id, column_name, column_comment, column_type, java_type, java_field, is_pk, is_increment, is_required, is_insert, is_edit, is_list, is_query, query_type, html_type, dict_type, sort, create_by, create_time) VALUES (2303312215391002, 2303312215391000, 'tid', '任务ID QRTZ_TASK=>ID', 'numeric', 'Long', 'tid', '0', '0', '0', '1', '1', '1', '1', 'EQ', 'input', NULL, 2, 'auto', '2023-03-31 22:15:39.063977');
INSERT INTO gen_table_column (column_id, table_id, column_name, column_comment, column_type, java_type, java_field, is_pk, is_increment, is_required, is_insert, is_edit, is_list, is_query, query_type, html_type, dict_type, sort, create_by, create_time) VALUES (2303312215391003, 2303312215391000, 'break_time', '发生时间', 'timestamp', 'Date', 'breakTime', '0', '0', '0', '1', '1', '1', '1', 'EQ', 'datetime', NULL, 3, 'auto', '2023-03-31 22:15:39.063977');
INSERT INTO gen_table_column (column_id, table_id, column_name, column_comment, column_type, java_type, java_field, is_pk, is_increment, is_required, is_insert, is_edit, is_list, is_query, query_type, html_type, dict_type, sort, create_by, create_time) VALUES (2303312215391004, 2303312215391000, 'host', '主机IP', 'varchar', 'String', 'host', '0', '0', '0', '1', '1', '1', '1', 'EQ', 'input', NULL, 4, 'auto', '2023-03-31 22:15:39.063977');
INSERT INTO gen_table_column (column_id, table_id, column_name, column_comment, column_type, java_type, java_field, is_pk, is_increment, is_required, is_insert, is_edit, is_list, is_query, query_type, html_type, dict_type, sort, create_by, create_time) VALUES (2303312215391005, 2303312215391000, 'host_name', '主机名称', 'varchar', 'String', 'hostName', '0', '0', '0', '1', '1', '1', '1', 'LIKE', 'input', NULL, 5, 'auto', '2023-03-31 22:15:39.063977');
INSERT INTO gen_table_column (column_id, table_id, column_name, column_comment, column_type, java_type, java_field, is_pk, is_increment, is_required, is_insert, is_edit, is_list, is_query, query_type, html_type, dict_type, sort, create_by, create_time) VALUES (2303312215391006, 2303312215391000, 'application_name', '应用名称', 'varchar', 'String', 'applicationName', '0', '0', '0', '1', '1', '1', '1', 'LIKE', 'input', NULL, 6, 'auto', '2023-03-31 22:15:39.063977');
INSERT INTO gen_table_column (column_id, table_id, column_name, column_comment, column_type, java_type, java_field, is_pk, is_increment, is_required, is_insert, is_edit, is_list, is_query, query_type, html_type, dict_type, sort, create_by, create_time) VALUES (2303312215391007, 2303312215391000, 'application_port', '应用端口', 'varchar', 'String', 'applicationPort', '0', '0', '0', '1', '1', '1', '1', 'EQ', 'input', NULL, 7, 'auto', '2023-03-31 22:15:39.063977');
INSERT INTO gen_table_column (column_id, table_id, column_name, column_comment, column_type, java_type, java_field, is_pk, is_increment, is_required, is_insert, is_edit, is_list, is_query, query_type, html_type, dict_type, sort, create_by, create_time) VALUES (2303312215391008, 2303312215391000, 'application_env', '应用所属环境', 'varchar', 'String', 'applicationEnv', '0', '0', '0', '1', '1', '1', '1', 'EQ', 'input', NULL, 8, 'auto', '2023-03-31 22:15:39.063977');
INSERT INTO gen_table_column (column_id, table_id, column_name, column_comment, column_type, java_type, java_field, is_pk, is_increment, is_required, is_insert, is_edit, is_list, is_query, query_type, html_type, dict_type, sort, create_by, create_time) VALUES (2303312215391009, 2303312215391000, 'exception', '错误消息(e.getMessage())', 'varchar', 'String', 'exception', '0', '0', '0', '1', '1', '1', '1', 'EQ', 'input', NULL, 9, 'auto', '2023-03-31 22:15:39.063977');
INSERT INTO gen_table_column (column_id, table_id, column_name, column_comment, column_type, java_type, java_field, is_pk, is_increment, is_required, is_insert, is_edit, is_list, is_query, query_type, html_type, dict_type, sort, create_by, create_time) VALUES (2303312215391010, 2303312215391000, 'exception_detail', '异常/错误详情', 'varchar', 'String', 'exceptionDetail', '0', '0', '0', '1', '1', '1', '1', 'EQ', 'input', NULL, 10, 'auto', '2023-03-31 22:15:39.063977');
INSERT INTO gen_table_column (column_id, table_id, column_name, column_comment, column_type, java_type, java_field, is_pk, is_increment, is_required, is_insert, is_edit, is_list, is_query, query_type, html_type, dict_type, sort, create_by, create_time) VALUES (2303312215391011, 2303312215391000, 'data', '接口数据', 'varchar', 'String', 'data', '0', '0', '0', '1', '1', '1', '1', 'EQ', 'input', NULL, 11, 'auto', '2023-03-31 22:15:39.063977');
INSERT INTO gen_table_column (column_id, table_id, column_name, column_comment, column_type, java_type, java_field, is_pk, is_increment, is_required, is_insert, is_edit, is_list, is_query, query_type, html_type, dict_type, sort, create_by, create_time) VALUES (2303312215391012, 2303312215391000, 's', '状态 0.失败/异常 1.正常', 'varchar', 'String', 's', '0', '0', '1', '1', '1', '1', '1', 'EQ', 'input', NULL, 12, 'auto', '2023-03-31 22:15:39.063977');
INSERT INTO gen_table_column (column_id, table_id, column_name, column_comment, column_type, java_type, java_field, is_pk, is_increment, is_required, is_insert, is_edit, is_list, is_query, query_type, html_type, dict_type, sort, create_by, create_time) VALUES (2303312215391013, 2303312215391000, 'create_time', '创建时间', 'timestamp', 'Date', 'createTime', '0', '0', '0', '1', NULL, NULL, NULL, 'EQ', 'datetime', NULL, 13, 'auto', '2023-03-31 22:15:39.063977');


-- 配置 GEN2_CONFIG
DROP TABLE IF EXISTS MEE_GENERATOR.GEN2_CONFIG;
CREATE TABLE MEE_GENERATOR.GEN2_CONFIG (
  ID INT8 PRIMARY KEY  COMMENT '表ID',
  AUTHOR VARCHAR(255)  COMMENT '作者',
  VERSION VARCHAR(20) NOT NULL DEFAULT 'V1.0' COMMENT '版本(v1.0)',
  CAMEL_CASE VARCHAR(1) NOT NULL COMMENT '接口、字段是否驼峰格式 1.是 0.否',
  BASE_PACKAGE VARCHAR(255)  COMMENT '包基础名称(example:com.mee.generator)',
  MODULE_NAME VARCHAR(50)  COMMENT '模块所在目录（example：BASE_PACKAGE.module.MODULE_NAME)',
  BACK_DIR VARCHAR(127) COMMENT '后端代码所在目录',
  FRONT_DIR VARCHAR(127) COMMENT '前端代码所在目录',
  DATABASE_ID VARCHAR(255) COMMENT '数据类型(default,mysql、postgresql、oracle等等)',
  STATUS INT2 COMMENT '状态 1.启用 0.禁用（一般至少有一个启用的）',
  `desc` VARCHAR(100) COMMENT '描述',
  UPDATE_VERSION INT4 NOT NULL DEFAULT '1' COMMENT '更新版本',
  UPDATE_TIME TIMESTAMP NOT NULL COMMENT '更新时间'
)ENGINE=INNODB DEFAULT CHARSET=UTF8MB4 COMMENT='单体代码生成::配置';

-- 表 GEN2_TABLE
DROP TABLE IF EXISTS MEE_GENERATOR.GEN2_TABLE;
CREATE TABLE MEE_GENERATOR.GEN2_TABLE(
  ID INT8 PRIMARY KEY COMMENT 'ID',
  TABLE_NAME VARCHAR(200) COMMENT '表名称',
  TABLE_COMMENT VARCHAR(500) COMMENT '表描述',
  CURRENT_DB VARCHAR(50) COMMENT '当前数据库类型(mysql、oracle、postgresql)',
  DATABASE_ID VARCHAR(50) COMMENT 'DB语句类型(mysql、oracle、postgresql)',
  CLASS_NAME VARCHAR(127) COMMENT '实体类名称',
  BASE_PACKAGE VARCHAR(100) COMMENT '包基础路径',
  MODULE_NAME VARCHAR(30) COMMENT '代码所在模块',
  AUTHOR VARCHAR(50) COMMENT '作者',
  VERSION VARCHAR(50) COMMENT '版本',
  CAMEL_CASE VARCHAR(1) COMMENT '接口、字段是否驼峰格式 1.是 0.否',
  BACK_DIR VARCHAR(100) COMMENT '下载后端代码所在文件夹',
  FRONT_DIR VARCHAR(100) COMMENT '下载前端代码所在文件夹',
  `desc` VARCHAR(255) COMMENT '备注',
  CREATE_BY VARCHAR(64) COMMENT '创建者',
  CREATE_TIME TIMESTAMP COMMENT '创建时间',
  UPDATE_BY VARCHAR(64) COMMENT '更新者',
  UPDATE_TIME TIMESTAMP COMMENT '更新时间'
) ENGINE=INNODB DEFAULT CHARSET=UTF8MB4 COMMENT='单体代码生成::业务表';


-- 字段 GEN2_COLUMN
DROP TABLE IF EXISTS MEE_GENERATOR.GEN2_COLUMN;
CREATE TABLE MEE_GENERATOR.GEN2_COLUMN(
  ID INT8 PRIMARY KEY COMMENT '编号',
  TABLE_ID INT8 COMMENT '归属表编号(GEN2_TABLE=>ID)',
  COLUMN_NAME VARCHAR(200)  COMMENT '列名称',
  COLUMN_COMMENT VARCHAR(500)  COMMENT '列描述',
  COLUMN_TYPE VARCHAR(100)  COMMENT '列类型',
  JAVA_TYPE VARCHAR(255)  COMMENT 'JAVA类型',
  JAVA_FIELD VARCHAR(255)  COMMENT 'JAVA字段名',
  IS_PK VARCHAR(1)  COMMENT '是否主键（1是）',
  IS_INCREMENT VARCHAR(1)  COMMENT '是否自增（1是）',
  IS_REQUIRED VARCHAR(1)  COMMENT '是否必填（1是）',
  IS_INSERT VARCHAR(1)  COMMENT '是否为插入字段（1是）',
  IS_EDIT VARCHAR(1)  COMMENT '是否编辑字段（1是）',
  IS_LIST VARCHAR(1)  COMMENT '是否列表字段（1是）',
  IS_QUERY VARCHAR(1)  COMMENT '是否查询字段（1是）',
  QUERY_TYPE VARCHAR(200) DEFAULT 'EQ' COMMENT '查询方式（等于、不等于、大于、小于、范围）若范围会生成两个插叙字段_start、_end',
  HTML_TYPE VARCHAR(200) COMMENT '显示类型（文本框、文本域、下拉框、复选框、单选框、日期控件）',
  DICT_TYPE VARCHAR(200) COMMENT '字典类型',
  SORT INT4 COMMENT '排序',
  CREATE_BY VARCHAR(64) NOT NULL COMMENT '创建者',
  CREATE_TIME TIMESTAMP NOT NULL COMMENT '创建时间',
  UPDATE_BY VARCHAR(64) COMMENT '修改人',
  UPDATE_TIME TIMESTAMP COMMENT '修改时间'
) ENGINE=INNODB DEFAULT CHARSET=UTF8MB4 COMMENT='单体代码生成::业务字段';

-- 配置 GEN_TABLE_CONFIG3
DROP TABLE IF EXISTS MEE_GENERATOR.GEN_TABLE_CONFIG3;
CREATE TABLE mee_generator.GEN_TABLE_CONFIG3 (
    ID INT8 PRIMARY KEY                              COMMENT '表ID',
    AUTHOR VARCHAR(255)  COMMENT '作者',
    VERSION VARCHAR(20) DEFAULT 'V1.0' NOT NULL COMMENT '版本(v1.0)',
    CAMEL_CASE VARCHAR(1) NOT NULL COMMENT '接口、字段是否驼峰格式 1.是 0.否',
    BASE_PACKAGE VARCHAR(255)  COMMENT '包基础名称(example:com.mee.generator)',
    MODULE_NAME VARCHAR(50)  COMMENT '模块所在目录（example：BASE_PACKAGE.module.MODULE_NAME)',
    BACK_DIR VARCHAR(127) COMMENT '后端代码所在目录',
    FRONT_DIR VARCHAR(127) COMMENT '前端代码所在目录',
    DATABASE_ID VARCHAR(255) COMMENT '数据类型(default,mysql、postgresql、oracle等等)',
    STATUS INT2 COMMENT '状态 1.启用 0.禁用（一般至少有一个启用的）',
    `desc` VARCHAR(100) COMMENT '描述',
    UPDATE_VERSION INT4 DEFAULT '1' NOT NULL COMMENT '更新版本',
    UPDATE_TIME TIMESTAMP NOT NULL COMMENT '更新时间'
) ENGINE=INNODB DEFAULT CHARSET=UTF8MB4 COMMENT='代码生成::配置';

-- 表 GEN_TABLE3
DROP TABLE IF EXISTS MEE_GENERATOR.GEN_TABLE3;
CREATE TABLE MEE_GENERATOR.GEN_TABLE3(
    ID INT8 PRIMARY KEY COMMENT 'ID',
    TABLE_NAME VARCHAR(200) COMMENT '表名称',
    TABLE_COMMENT VARCHAR(500) COMMENT '表描述',
    CURRENT_DB VARCHAR(50) COMMENT '当前数据库类型(mysql、oracle、postgresql)',
    DATABASE_ID VARCHAR(50) COMMENT 'DB语句类型(mysql、oracle、postgresql)',
    CLASS_NAME VARCHAR(127) COMMENT '实体类名称',
    BASE_PACKAGE VARCHAR(100) COMMENT '包基础路径',
    MODULE_NAME VARCHAR(30) COMMENT '代码所在模块',
    AUTHOR VARCHAR(50) COMMENT '作者',
    VERSION VARCHAR(50) COMMENT '版本',
    CAMEL_CASE VARCHAR(1) COMMENT '接口、字段是否驼峰格式 1.是 0.否',
    BACK_DIR VARCHAR(100) COMMENT '下载后端代码所在文件夹',
    FRONT_DIR VARCHAR(100) COMMENT '下载前端代码所在文件夹',
    `desc` VARCHAR(255) COMMENT '备注',
    CREATE_BY VARCHAR(64) COMMENT '创建者',
    CREATE_TIME TIMESTAMP NOT NULL COMMENT '创建时间',
    UPDATE_BY VARCHAR(64) COMMENT '更新者',
    UPDATE_TIME TIMESTAMP COMMENT '更新时间'
)ENGINE=INNODB DEFAULT CHARSET=UTF8MB4 COMMENT='代码生成::业务表';


-- 字段 GEN_TABLE_COLUMN3
DROP TABLE IF EXISTS MEE_GENERATOR.GEN_TABLE_COLUMN3;
CREATE TABLE mee_generator.GEN_TABLE_COLUMN3(
    ID INT8 PRIMARY KEY         COMMENT '编号',
    TABLE_ID INT8 COMMENT '归属表编号(GEN_TABLE3=>ID)',
    COLUMN_NAME VARCHAR(200)  COMMENT '列名称',
    COLUMN_COMMENT VARCHAR(500)  COMMENT '列描述',
    COLUMN_TYPE VARCHAR(100)  COMMENT '列类型',
    JAVA_TYPE VARCHAR(255)  COMMENT 'JAVA类型',
    JAVA_FIELD VARCHAR(255)  COMMENT 'JAVA字段名',
    IS_PK VARCHAR(1)  COMMENT '是否主键（1是）',
    IS_INCREMENT VARCHAR(1)  COMMENT '是否自增（1是）',
    IS_REQUIRED VARCHAR(1)  COMMENT '是否必填（1是）',
    IS_INSERT VARCHAR(1)  COMMENT '是否为插入字段（1是）',
    IS_EDIT VARCHAR(1)  COMMENT '是否编辑字段（1是）',
    IS_LIST VARCHAR(1)  COMMENT '是否列表字段（1是）',
    IS_QUERY VARCHAR(1)       COMMENT '是否查询字段（1是）',
    QUERY_TYPE VARCHAR(200) NOT NULL  COMMENT '查询方式（等于、不等于、大于、小于、范围）若范围会生成两个插叙字段_start、_end',
    HTML_TYPE VARCHAR(200) COMMENT '显示类型（文本框、文本域、下拉框、复选框、单选框、日期控件）',
    DICT_TYPE VARCHAR(200) COMMENT '字典类型',
    SORT INT4 COMMENT '排序',
    CREATE_BY VARCHAR(64) NOT NULL COMMENT '创建者',
    CREATE_TIME TIMESTAMP NOT NULL COMMENT '创建时间',
    UPDATE_BY VARCHAR(64) COMMENT '修改人',
    UPDATE_TIME TIMESTAMP COMMENT '修改时间'
)ENGINE=INNODB DEFAULT CHARSET=UTF8MB4 COMMENT='代码生成::业务字段';
