
DROP TABLE IF EXISTS "code_gen_config";
CREATE TABLE "code_gen_config" (
  "config_id" int8 NOT NULL,
  "table_name" varchar(255) COLLATE "pg_catalog"."default",
  "author" varchar(255) COLLATE "pg_catalog"."default",
  "cover" varchar(1) COLLATE "pg_catalog"."default",
  "module_name" varchar(255) COLLATE "pg_catalog"."default",
  "pack" varchar(255) COLLATE "pg_catalog"."default",
  "path" varchar(255) COLLATE "pg_catalog"."default",
  "api_path" varchar(255) COLLATE "pg_catalog"."default",
  "prefix" varchar(255) COLLATE "pg_catalog"."default",
  "api_alias" varchar(255) COLLATE "pg_catalog"."default"
);

COMMENT ON COLUMN code_gen_config."config_id" IS 'ID';
COMMENT ON COLUMN code_gen_config."table_name" IS '表名';
COMMENT ON COLUMN code_gen_config."author" IS '作者';
COMMENT ON COLUMN code_gen_config."cover" IS '是否覆盖';
COMMENT ON COLUMN code_gen_config."module_name" IS '模块名称';
COMMENT ON COLUMN code_gen_config."pack" IS '至于哪个包下';
COMMENT ON COLUMN code_gen_config."path" IS '前端代码生成的路径';
COMMENT ON COLUMN code_gen_config."api_path" IS '前端Api文件路径';
COMMENT ON COLUMN code_gen_config."prefix" IS '表前缀';
COMMENT ON COLUMN code_gen_config."api_alias" IS '接口名称';
COMMENT ON TABLE  code_gen_config IS '代码生成器配置';

DROP TABLE IF EXISTS "gen_table";
CREATE TABLE "gen_table" (
  "table_id" int8 NOT NULL,
  "table_name" varchar(200) COLLATE "pg_catalog"."default",
  "table_comment" varchar(500) COLLATE "pg_catalog"."default",
  "sub_table_name" varchar(64) COLLATE "pg_catalog"."default",
  "sub_table_fk_name" varchar(64) COLLATE "pg_catalog"."default",
  "class_name" varchar(100) COLLATE "pg_catalog"."default",
  "tpl_category" varchar(200) COLLATE "pg_catalog"."default",
  "package_name" varchar(100) COLLATE "pg_catalog"."default",
  "module_name" varchar(30) COLLATE "pg_catalog"."default",
  "business_name" varchar(30) COLLATE "pg_catalog"."default",
  "function_name" varchar(50) COLLATE "pg_catalog"."default",
  "function_version" varchar(20) COLLATE "pg_catalog"."default" DEFAULT 'v1.0'::character varying,
  "function_author" varchar(50) COLLATE "pg_catalog"."default",
  "gen_type" char(1) COLLATE "pg_catalog"."default",
  "gen_path" varchar(200) COLLATE "pg_catalog"."default",
  "options" varchar(1000) COLLATE "pg_catalog"."default",
  "create_by" varchar(64) COLLATE "pg_catalog"."default",
  "create_time" timestamp(6),
  "update_by" varchar(64) COLLATE "pg_catalog"."default",
  "update_time" timestamp(6),
  "remark" varchar(500) COLLATE "pg_catalog"."default"
);

COMMENT ON COLUMN gen_table."table_id" IS '编号';
COMMENT ON COLUMN gen_table."table_name" IS '表名称';
COMMENT ON COLUMN gen_table."table_comment" IS '表描述';
COMMENT ON COLUMN gen_table."sub_table_name" IS '关联子表的表名';
COMMENT ON COLUMN gen_table."sub_table_fk_name" IS '子表关联的外键名';
COMMENT ON COLUMN gen_table."class_name" IS '实体类名称';
COMMENT ON COLUMN gen_table."tpl_category" IS '使用的模板';
COMMENT ON COLUMN gen_table."package_name" IS '生成包路径';
COMMENT ON COLUMN gen_table."module_name" IS '生成模块名';
COMMENT ON COLUMN gen_table."business_name" IS '生成业务名';
COMMENT ON COLUMN gen_table."function_name" IS '生成功能名';
COMMENT ON COLUMN gen_table."function_version" IS '功能版本';
COMMENT ON COLUMN gen_table."function_author" IS '生成功能作者';
COMMENT ON COLUMN gen_table."gen_type" IS '生成代码方式';
COMMENT ON COLUMN gen_table."gen_path" IS '生成路径';
COMMENT ON COLUMN gen_table."options" IS '其它生成选项';
COMMENT ON COLUMN gen_table."create_by" IS '创建者';
COMMENT ON COLUMN gen_table."create_time" IS '创建时间';
COMMENT ON COLUMN gen_table."update_by" IS '更新者';
COMMENT ON COLUMN gen_table."update_time" IS '更新时间';
COMMENT ON COLUMN gen_table."remark" IS '备注';
COMMENT ON TABLE  gen_table IS '代码生成业务表';

INSERT INTO "gen_table" ("table_id", "table_name", "table_comment", "sub_table_name", "sub_table_fk_name", "class_name", "tpl_category", "package_name", "module_name", "business_name", "function_name", "function_version", "function_author", "gen_type", "gen_path", "options", "create_by", "create_time", "update_by", "update_time", "remark") VALUES (6, 'sys_user_post', '用户与岗位关联表', NULL, NULL, 'SysRoleMenu', 'crud', 'com.mee.generator.system', 'system', 'menu', '角色和菜单关联', 'v1.0', 'shadow', '0', '/', NULL, 'sys', '2022-10-28 15:36:29.124', 'sys', '2022-10-28 15:36:29.124', NULL);
INSERT INTO "gen_table" ("table_id", "table_name", "table_comment", "sub_table_name", "sub_table_fk_name", "class_name", "tpl_category", "package_name", "module_name", "business_name", "function_name", "function_version", "function_author", "gen_type", "gen_path", "options", "create_by", "create_time", "update_by", "update_time", "remark") VALUES (9, 'sys_menu', '菜单权限表', NULL, NULL, 'SysDept', 'crud', 'com.mee.generator.system', 'system', 'dept', '部门', 'v1.0', 'shadow', '0', '/', NULL, 'sys', '2022-10-28 15:36:29.186', 'sys', '2022-10-28 15:36:29.186', NULL);
INSERT INTO "gen_table" ("table_id", "table_name", "table_comment", "sub_table_name", "sub_table_fk_name", "class_name", "tpl_category", "package_name", "module_name", "business_name", "function_name", "function_version", "function_author", "gen_type", "gen_path", "options", "create_by", "create_time", "update_by", "update_time", "remark") VALUES (10, 'sys_post', '岗位信息表', NULL, NULL, 'SysMenu', 'crud', 'com.mee.generator.system', 'system', 'menu', '菜单权限', 'v1.0', 'shadow', '0', '/', NULL, 'sys', '2022-10-28 15:36:29.205', 'sys', '2022-10-28 15:36:29.205', NULL);
INSERT INTO "gen_table" ("table_id", "table_name", "table_comment", "sub_table_name", "sub_table_fk_name", "class_name", "tpl_category", "package_name", "module_name", "business_name", "function_name", "function_version", "function_author", "gen_type", "gen_path", "options", "create_by", "create_time", "update_by", "update_time", "remark") VALUES (11, 'sys_job_log', '定时任务调度日志表', NULL, NULL, 'SysPost', 'crud', 'com.mee.generator.system', 'system', 'post', '岗位信息', 'v1.0', 'shadow', '0', '/', NULL, 'sys', '2022-10-28 15:36:29.23', 'sys', '2022-10-28 15:36:29.23', NULL);
INSERT INTO "gen_table" ("table_id", "table_name", "table_comment", "sub_table_name", "sub_table_fk_name", "class_name", "tpl_category", "package_name", "module_name", "business_name", "function_name", "function_version", "function_author", "gen_type", "gen_path", "options", "create_by", "create_time", "update_by", "update_time", "remark") VALUES (12, 'sys_role', '角色信息表', NULL, NULL, 'SysJobLog', 'crud', 'com.mee.generator.system', 'system', 'log', '定时任务调度日志', 'v1.0', 'shadow', '0', '/', NULL, 'sys', '2022-10-28 15:36:29.247', 'sys', '2022-10-28 15:36:29.247', NULL);
INSERT INTO "gen_table" ("table_id", "table_name", "table_comment", "sub_table_name", "sub_table_fk_name", "class_name", "tpl_category", "package_name", "module_name", "business_name", "function_name", "function_version", "function_author", "gen_type", "gen_path", "options", "create_by", "create_time", "update_by", "update_time", "remark") VALUES (7, 'sys_user_role', '用户和角色关联表', 'NONE', 'NONE', 'SysUserPost', 'crud', 'com.mee.generator.system', 'system', 'post', '用户与岗位关联', 'v1.0', 'shadow', '0', '/', '{}', 'sys', '2022-10-28 15:36:29.142', 'sys', '2022-10-28 15:36:29.142', NULL);
INSERT INTO "gen_table" ("table_id", "table_name", "table_comment", "sub_table_name", "sub_table_fk_name", "class_name", "tpl_category", "package_name", "module_name", "business_name", "function_name", "function_version", "function_author", "gen_type", "gen_path", "options", "create_by", "create_time", "update_by", "update_time", "remark") VALUES (13, 'sys_config', '参数配置表', 'init', NULL, 'SysRole', 'crud', 'com.mee.generator.system', 'system', 'role', '角色信息', 'v1.0', 'shadow', '0', '/', NULL, 'sys', '2022-10-28 15:36:29.265', 'sys', '2022-10-28 15:36:29.265', NULL);
INSERT INTO "gen_table" ("table_id", "table_name", "table_comment", "sub_table_name", "sub_table_fk_name", "class_name", "tpl_category", "package_name", "module_name", "business_name", "function_name", "function_version", "function_author", "gen_type", "gen_path", "options", "create_by", "create_time", "update_by", "update_time", "remark") VALUES (2303131105241000, 'sys_quartz_job', '定时任务', NULL, NULL, 'SysQuartzJob', 'crud', 'com.mee.system', 'system', 'job', '定时任务', 'v1.0', 'shadow', NULL, NULL, NULL, 'auto', '2023-03-13 11:05:24.159', NULL, NULL, NULL);
INSERT INTO "gen_table" ("table_id", "table_name", "table_comment", "sub_table_name", "sub_table_fk_name", "class_name", "tpl_category", "package_name", "module_name", "business_name", "function_name", "function_version", "function_author", "gen_type", "gen_path", "options", "create_by", "create_time", "update_by", "update_time", "remark") VALUES (2303312215391000, 'qrtz_task_log', '任务执行日志', NULL, NULL, 'QrtzTaskLog', 'crud', 'com.mee.system', 'system', 'log', '任务执行日志', 'v1.0', 'shadow', NULL, NULL, NULL, 'auto', '2023-03-31 22:15:39.063977', NULL, NULL, NULL);


DROP TABLE IF EXISTS "gen_table_column";
CREATE TABLE "gen_table_column" (
  "column_id" int8 primary key,
  "table_id" int8,
  "column_name" varchar(200) COLLATE "pg_catalog"."default",
  "column_comment" varchar(500) COLLATE "pg_catalog"."default",
  "column_type" varchar(100) COLLATE "pg_catalog"."default",
  "java_type" varchar(500) COLLATE "pg_catalog"."default",
  "java_field" varchar(200) COLLATE "pg_catalog"."default",
  "is_pk" char(1) COLLATE "pg_catalog"."default",
  "is_increment" char(1) COLLATE "pg_catalog"."default",
  "is_required" char(1) COLLATE "pg_catalog"."default",
  "is_insert" char(1) COLLATE "pg_catalog"."default",
  "is_edit" char(1) COLLATE "pg_catalog"."default",
  "is_list" char(1) COLLATE "pg_catalog"."default",
  "is_query" char(1) COLLATE "pg_catalog"."default",
  "query_type" varchar(200) COLLATE "pg_catalog"."default" DEFAULT 'EQ'::character varying,
  "html_type" varchar(200) COLLATE "pg_catalog"."default",
  "dict_type" varchar(200) COLLATE "pg_catalog"."default" DEFAULT ''::character varying,
  "sort" int4,
  "create_by" varchar(64) COLLATE "pg_catalog"."default" DEFAULT ''::character varying,
  "create_time" timestamp(6)
);

COMMENT ON COLUMN gen_table_column."column_id" IS '编号';
COMMENT ON COLUMN gen_table_column."table_id" IS '归属表编号';
COMMENT ON COLUMN gen_table_column."column_name" IS '列名称';
COMMENT ON COLUMN gen_table_column."column_comment" IS '列描述';
COMMENT ON COLUMN gen_table_column."column_type" IS '列类型';
COMMENT ON COLUMN gen_table_column."java_type" IS 'JAVA类型';
COMMENT ON COLUMN gen_table_column."java_field" IS 'JAVA字段名';
COMMENT ON COLUMN gen_table_column."is_pk" IS '是否主键（1是）';
COMMENT ON COLUMN gen_table_column."is_increment" IS '是否自增（1是）';
COMMENT ON COLUMN gen_table_column."is_required" IS '是否必填（1是）';
COMMENT ON COLUMN gen_table_column."is_insert" IS '是否为插入字段（1是）';
COMMENT ON COLUMN gen_table_column."is_edit" IS '是否编辑字段（1是）';
COMMENT ON COLUMN gen_table_column."is_list" IS '是否列表字段（1是）';
COMMENT ON COLUMN gen_table_column."is_query" IS '是否查询字段（1是）';
COMMENT ON COLUMN gen_table_column."query_type" IS '查询方式（等于、不等于、大于、小于、范围）';
COMMENT ON COLUMN gen_table_column."html_type" IS '显示类型（文本框、文本域、下拉框、复选框、单选框、日期控件）';
COMMENT ON COLUMN gen_table_column."dict_type" IS '字典类型';
COMMENT ON COLUMN gen_table_column."sort" IS '排序';
COMMENT ON COLUMN gen_table_column."create_by" IS '创建者';
COMMENT ON COLUMN gen_table_column."create_time" IS '创建时间';
COMMENT ON TABLE  gen_table_column IS '代码生成业务表字段';


INSERT INTO "gen_table_column" ("column_id", "table_id", "column_name", "column_comment", "column_type", "java_type", "java_field", "is_pk", "is_increment", "is_required", "is_insert", "is_edit", "is_list", "is_query", "query_type", "html_type", "dict_type", "sort", "create_by", "create_time") VALUES (221102153705001022, 15, 'update_time', '更新时间', 'datetime', 'Date', 'updateTime', '0', '0', NULL, '1', '1', NULL, NULL, '=', 'datetime', '', 18, 'init', '2022-11-02 15:37:42.272723');
INSERT INTO "gen_table_column" ("column_id", "table_id", "column_name", "column_comment", "column_type", "java_type", "java_field", "is_pk", "is_increment", "is_required", "is_insert", "is_edit", "is_list", "is_query", "query_type", "html_type", "dict_type", "sort", "create_by", "create_time") VALUES (2303312215391001, 2303312215391000, 'id', '主键', 'numeric', 'Long', 'id', '1', '0', '1', '1', NULL, NULL, NULL, 'EQ', 'input', NULL, 1, 'auto', '2023-03-31 22:15:39.063977');
INSERT INTO "gen_table_column" ("column_id", "table_id", "column_name", "column_comment", "column_type", "java_type", "java_field", "is_pk", "is_increment", "is_required", "is_insert", "is_edit", "is_list", "is_query", "query_type", "html_type", "dict_type", "sort", "create_by", "create_time") VALUES (2303312215391002, 2303312215391000, 'tid', '任务ID QRTZ_TASK=>ID', 'numeric', 'Long', 'tid', '0', '0', '0', '1', '1', '1', '1', 'EQ', 'input', NULL, 2, 'auto', '2023-03-31 22:15:39.063977');
INSERT INTO "gen_table_column" ("column_id", "table_id", "column_name", "column_comment", "column_type", "java_type", "java_field", "is_pk", "is_increment", "is_required", "is_insert", "is_edit", "is_list", "is_query", "query_type", "html_type", "dict_type", "sort", "create_by", "create_time") VALUES (2303312215391003, 2303312215391000, 'break_time', '发生时间', 'timestamp', 'Date', 'breakTime', '0', '0', '0', '1', '1', '1', '1', 'EQ', 'datetime', NULL, 3, 'auto', '2023-03-31 22:15:39.063977');
INSERT INTO "gen_table_column" ("column_id", "table_id", "column_name", "column_comment", "column_type", "java_type", "java_field", "is_pk", "is_increment", "is_required", "is_insert", "is_edit", "is_list", "is_query", "query_type", "html_type", "dict_type", "sort", "create_by", "create_time") VALUES (2303312215391004, 2303312215391000, 'host', '主机IP', 'varchar', 'String', 'host', '0', '0', '0', '1', '1', '1', '1', 'EQ', 'input', NULL, 4, 'auto', '2023-03-31 22:15:39.063977');
INSERT INTO "gen_table_column" ("column_id", "table_id", "column_name", "column_comment", "column_type", "java_type", "java_field", "is_pk", "is_increment", "is_required", "is_insert", "is_edit", "is_list", "is_query", "query_type", "html_type", "dict_type", "sort", "create_by", "create_time") VALUES (2303312215391005, 2303312215391000, 'host_name', '主机名称', 'varchar', 'String', 'hostName', '0', '0', '0', '1', '1', '1', '1', 'LIKE', 'input', NULL, 5, 'auto', '2023-03-31 22:15:39.063977');
INSERT INTO "gen_table_column" ("column_id", "table_id", "column_name", "column_comment", "column_type", "java_type", "java_field", "is_pk", "is_increment", "is_required", "is_insert", "is_edit", "is_list", "is_query", "query_type", "html_type", "dict_type", "sort", "create_by", "create_time") VALUES (2303312215391006, 2303312215391000, 'application_name', '应用名称', 'varchar', 'String', 'applicationName', '0', '0', '0', '1', '1', '1', '1', 'LIKE', 'input', NULL, 6, 'auto', '2023-03-31 22:15:39.063977');
INSERT INTO "gen_table_column" ("column_id", "table_id", "column_name", "column_comment", "column_type", "java_type", "java_field", "is_pk", "is_increment", "is_required", "is_insert", "is_edit", "is_list", "is_query", "query_type", "html_type", "dict_type", "sort", "create_by", "create_time") VALUES (2303312215391007, 2303312215391000, 'application_port', '应用端口', 'varchar', 'String', 'applicationPort', '0', '0', '0', '1', '1', '1', '1', 'EQ', 'input', NULL, 7, 'auto', '2023-03-31 22:15:39.063977');
INSERT INTO "gen_table_column" ("column_id", "table_id", "column_name", "column_comment", "column_type", "java_type", "java_field", "is_pk", "is_increment", "is_required", "is_insert", "is_edit", "is_list", "is_query", "query_type", "html_type", "dict_type", "sort", "create_by", "create_time") VALUES (2303312215391008, 2303312215391000, 'application_env', '应用所属环境', 'varchar', 'String', 'applicationEnv', '0', '0', '0', '1', '1', '1', '1', 'EQ', 'input', NULL, 8, 'auto', '2023-03-31 22:15:39.063977');
INSERT INTO "gen_table_column" ("column_id", "table_id", "column_name", "column_comment", "column_type", "java_type", "java_field", "is_pk", "is_increment", "is_required", "is_insert", "is_edit", "is_list", "is_query", "query_type", "html_type", "dict_type", "sort", "create_by", "create_time") VALUES (2303312215391009, 2303312215391000, 'exception', '错误消息(e.getMessage())', 'varchar', 'String', 'exception', '0', '0', '0', '1', '1', '1', '1', 'EQ', 'input', NULL, 9, 'auto', '2023-03-31 22:15:39.063977');
INSERT INTO "gen_table_column" ("column_id", "table_id", "column_name", "column_comment", "column_type", "java_type", "java_field", "is_pk", "is_increment", "is_required", "is_insert", "is_edit", "is_list", "is_query", "query_type", "html_type", "dict_type", "sort", "create_by", "create_time") VALUES (2303312215391010, 2303312215391000, 'exception_detail', '异常/错误详情', 'varchar', 'String', 'exceptionDetail', '0', '0', '0', '1', '1', '1', '1', 'EQ', 'input', NULL, 10, 'auto', '2023-03-31 22:15:39.063977');
INSERT INTO "gen_table_column" ("column_id", "table_id", "column_name", "column_comment", "column_type", "java_type", "java_field", "is_pk", "is_increment", "is_required", "is_insert", "is_edit", "is_list", "is_query", "query_type", "html_type", "dict_type", "sort", "create_by", "create_time") VALUES (2303312215391011, 2303312215391000, 'data', '接口数据', 'varchar', 'String', 'data', '0', '0', '0', '1', '1', '1', '1', 'EQ', 'input', NULL, 11, 'auto', '2023-03-31 22:15:39.063977');
INSERT INTO "gen_table_column" ("column_id", "table_id", "column_name", "column_comment", "column_type", "java_type", "java_field", "is_pk", "is_increment", "is_required", "is_insert", "is_edit", "is_list", "is_query", "query_type", "html_type", "dict_type", "sort", "create_by", "create_time") VALUES (2303312215391012, 2303312215391000, 's', '状态 0.失败/异常 1.正常', 'varchar', 'String', 's', '0', '0', '1', '1', '1', '1', '1', 'EQ', 'input', NULL, 12, 'auto', '2023-03-31 22:15:39.063977');
INSERT INTO "gen_table_column" ("column_id", "table_id", "column_name", "column_comment", "column_type", "java_type", "java_field", "is_pk", "is_increment", "is_required", "is_insert", "is_edit", "is_list", "is_query", "query_type", "html_type", "dict_type", "sort", "create_by", "create_time") VALUES (2303312215391013, 2303312215391000, 'create_time', '创建时间', 'timestamp', 'Date', 'createTime', '0', '0', '0', '1', NULL, NULL, NULL, 'EQ', 'datetime', NULL, 13, 'auto', '2023-03-31 22:15:39.063977');


-- 配置 GEN2_CONFIG
DROP TABLE IF EXISTS GEN2_CONFIG;
CREATE TABLE GEN2_CONFIG (
  ID INT8 PRIMARY KEY,
  AUTHOR VARCHAR(255) ,
  VERSION VARCHAR(20) NOT NULL DEFAULT 'V1.0',
  CAMEL_CASE VARCHAR(1) NOT NULL,
  BASE_PACKAGE VARCHAR(255) ,
  MODULE_NAME VARCHAR(50) ,
  DATABASE_ID VARCHAR(255),
  STATUS INT2,
  "desc" VARCHAR(100),
  UPDATE_VERSION INT4 NOT NULL DEFAULT '1',
  UPDATE_TIME TIMESTAMP NOT NULL
);

COMMENT ON TABLE  GEN2_CONFIG IS '单体代码生成::配置';
COMMENT ON COLUMN GEN2_CONFIG.ID IS '表ID';
COMMENT ON COLUMN GEN2_CONFIG.AUTHOR IS '作者';
COMMENT ON COLUMN GEN2_CONFIG.VERSION IS '版本(v1.0)';
COMMENT ON COLUMN GEN2_CONFIG.CAMEL_CASE IS '接口、字段是否驼峰格式 1.是 0.否';
COMMENT ON COLUMN GEN2_CONFIG.BASE_PACKAGE IS '包基础名称(example:com.mee.generator)';
COMMENT ON COLUMN GEN2_CONFIG.MODULE_NAME IS '模块所在目录（example：BASE_PACKAGE.module.MODULE_NAME)';
COMMENT ON COLUMN GEN2_CONFIG.DATABASE_ID IS '数据类型(default,mysql、postgresql、oracle等等)';
COMMENT ON COLUMN GEN2_CONFIG.STATUS IS '状态 1.启用 0.禁用（一般至少有一个启用的）';
COMMENT ON COLUMN GEN2_CONFIG."desc" IS '描述';
COMMENT ON COLUMN GEN2_CONFIG.UPDATE_VERSION IS '更新版本';
COMMENT ON COLUMN GEN2_CONFIG.UPDATE_TIME IS '更新时间';

-- 表 GEN2_TABLE
DROP TABLE IF EXISTS GEN2_TABLE;
CREATE TABLE GEN2_TABLE(
  ID INT8 PRIMARY KEY,
  TABLE_NAME VARCHAR(200),
  TABLE_COMMENT VARCHAR(500),
  CURRENT_DB VARCHAR(50),
  DATABASE_ID VARCHAR(50),
  CLASS_NAME VARCHAR(127),
  BASE_PACKAGE VARCHAR(100),
  MODULE_NAME VARCHAR(30),
  AUTHOR VARCHAR(50),
  VERSION VARCHAR(50),
  CAMEL_CASE VARCHAR(1),
  BACK_DIR VARCHAR(100),
  FRONT_DIR VARCHAR(100),
  "desc" VARCHAR(255),
  CREATE_BY VARCHAR(64),
  CREATE_TIME TIMESTAMP,
  UPDATE_BY VARCHAR(64),
  UPDATE_TIME TIMESTAMP
);

COMMENT ON TABLE  GEN2_TABLE IS '单体代码生成::业务表';
COMMENT ON COLUMN GEN2_TABLE.ID IS 'ID';
COMMENT ON COLUMN GEN2_TABLE.TABLE_NAME IS '表名称';
COMMENT ON COLUMN GEN2_TABLE.TABLE_COMMENT IS '表描述';
COMMENT ON COLUMN GEN2_TABLE.CURRENT_DB IS '当前数据库类型(mysql、oracle、postgresql)';
COMMENT ON COLUMN GEN2_TABLE.DATABASE_ID IS 'DB语句类型(mysql、oracle、postgresql)';
COMMENT ON COLUMN GEN2_TABLE.CLASS_NAME IS '实体类名称';
COMMENT ON COLUMN GEN2_TABLE.BASE_PACKAGE IS '包基础路径';
COMMENT ON COLUMN GEN2_TABLE.MODULE_NAME IS '代码所在模块';
COMMENT ON COLUMN GEN2_TABLE.AUTHOR IS '作者';
COMMENT ON COLUMN GEN2_TABLE.VERSION IS '版本';
COMMENT ON COLUMN GEN2_TABLE.CAMEL_CASE IS '接口、字段是否驼峰格式 1.是 0.否';
COMMENT ON COLUMN GEN2_TABLE.BACK_DIR IS '下载后端代码所在文件夹';
COMMENT ON COLUMN GEN2_TABLE.FRONT_DIR IS '下载前端代码所在文件夹';

COMMENT ON COLUMN GEN2_TABLE."desc" IS '备注';
COMMENT ON COLUMN GEN2_TABLE.CREATE_BY IS '创建者';
COMMENT ON COLUMN GEN2_TABLE.CREATE_TIME IS '创建时间';
COMMENT ON COLUMN GEN2_TABLE.UPDATE_BY IS '更新者';
COMMENT ON COLUMN GEN2_TABLE.UPDATE_TIME IS '更新时间';

-- 字段 GEN_TABLE_COLUMN
DROP TABLE IF EXISTS GEN2_COLUMN;
CREATE TABLE GEN2_COLUMN(
  ID INT8 PRIMARY KEY,
  TABLE_ID INT8,
  COLUMN_NAME VARCHAR(200) ,
  COLUMN_COMMENT VARCHAR(500) ,
  COLUMN_TYPE VARCHAR(100) ,
  JAVA_TYPE VARCHAR(255) ,
  JAVA_FIELD VARCHAR(255) ,
  IS_PK VARCHAR(1) ,
  IS_INCREMENT VARCHAR(1) ,
  IS_REQUIRED VARCHAR(1) ,
  IS_INSERT VARCHAR(1) ,
  IS_EDIT VARCHAR(1) ,
  IS_LIST VARCHAR(1) ,
  IS_QUERY VARCHAR(1) ,
  QUERY_TYPE VARCHAR(200) DEFAULT 'EQ',
  HTML_TYPE VARCHAR(200),
  DICT_TYPE VARCHAR(200),
  SORT INT4,
  CREATE_BY VARCHAR(64) NOT NULL,
  CREATE_TIME TIMESTAMP NOT NULL,
  UPDATE_BY VARCHAR(64),
  UPDATE_TIME TIMESTAMP
);

COMMENT ON TABLE  GEN2_COLUMN IS '单体代码生成::业务字段';
COMMENT ON COLUMN GEN2_COLUMN.ID IS '编号';
COMMENT ON COLUMN GEN2_COLUMN.TABLE_ID IS '归属表编号(GEN2_TABLE=>ID)';
COMMENT ON COLUMN GEN2_COLUMN.COLUMN_NAME IS '列名称';
COMMENT ON COLUMN GEN2_COLUMN.COLUMN_COMMENT IS '列描述';
COMMENT ON COLUMN GEN2_COLUMN.COLUMN_TYPE IS '列类型';
COMMENT ON COLUMN GEN2_COLUMN.JAVA_TYPE IS 'JAVA类型';
COMMENT ON COLUMN GEN2_COLUMN.JAVA_FIELD IS 'JAVA字段名';
COMMENT ON COLUMN GEN2_COLUMN.IS_PK IS '是否主键（1是）';
COMMENT ON COLUMN GEN2_COLUMN.IS_INCREMENT IS '是否自增（1是）';
COMMENT ON COLUMN GEN2_COLUMN.IS_REQUIRED IS '是否必填（1是）';
COMMENT ON COLUMN GEN2_COLUMN.IS_INSERT IS '是否为插入字段（1是）';
COMMENT ON COLUMN GEN2_COLUMN.IS_EDIT IS '是否编辑字段（1是）';
COMMENT ON COLUMN GEN2_COLUMN.IS_LIST IS '是否列表字段（1是）';
COMMENT ON COLUMN GEN2_COLUMN.IS_QUERY IS '是否查询字段（1是）';
COMMENT ON COLUMN GEN2_COLUMN.QUERY_TYPE IS '查询方式（等于、不等于、大于、小于、范围）若范围会生成两个插叙字段_start、_end';
COMMENT ON COLUMN GEN2_COLUMN.HTML_TYPE IS '显示类型（文本框、文本域、下拉框、复选框、单选框、日期控件）';
COMMENT ON COLUMN GEN2_COLUMN.DICT_TYPE IS '字典类型';
COMMENT ON COLUMN GEN2_COLUMN.SORT IS '排序';
COMMENT ON COLUMN GEN2_COLUMN.CREATE_BY IS '创建者';
COMMENT ON COLUMN GEN2_COLUMN.CREATE_TIME IS '创建时间';
COMMENT ON COLUMN GEN2_COLUMN.UPDATE_BY IS '修改人';
COMMENT ON COLUMN GEN2_COLUMN.UPDATE_TIME IS '修改时间';

-- 配置 GEN_TABLE_CONFIG3
CREATE TABLE GEN_TABLE_CONFIG3 (
ID INT8 PRIMARY KEY,
AUTHOR VARCHAR(255) ,
VERSION VARCHAR(20) DEFAULT 'V1.0' NOT NULL,
CAMEL_CASE VARCHAR(1) NOT NULL,
BASE_PACKAGE VARCHAR(255) ,
MODULE_NAME VARCHAR(50) ,
BACK_DIR VARCHAR(127),
FRONT_DIR VARCHAR(127),
DATABASE_ID VARCHAR(255),
STATUS INT2,
"desc" VARCHAR(100),
UPDATE_VERSION INT4 DEFAULT '1' NOT NULL,
UPDATE_TIME TIMESTAMP NOT NULL
);

COMMENT ON TABLE  GEN_TABLE_CONFIG3 IS '代码生成::配置';
COMMENT ON COLUMN GEN_TABLE_CONFIG3.ID IS '表ID';
COMMENT ON COLUMN GEN_TABLE_CONFIG3.AUTHOR IS '作者';
COMMENT ON COLUMN GEN_TABLE_CONFIG3.VERSION IS '版本(v1.0)';
COMMENT ON COLUMN GEN_TABLE_CONFIG3.CAMEL_CASE IS '接口、字段是否驼峰格式 1.是 0.否';
COMMENT ON COLUMN GEN_TABLE_CONFIG3.BASE_PACKAGE IS '包基础名称(example:com.mee.generator)';
COMMENT ON COLUMN GEN_TABLE_CONFIG3.MODULE_NAME IS '模块所在目录（example：BASE_PACKAGE.module.MODULE_NAME)';
COMMENT ON COLUMN GEN_TABLE_CONFIG3.BACK_DIR IS '后端代码所在目录';
COMMENT ON COLUMN GEN_TABLE_CONFIG3.FRONT_DIR IS '前端代码所在目录';
COMMENT ON COLUMN GEN_TABLE_CONFIG3.DATABASE_ID IS '数据类型(default,mysql、postgresql、oracle等等)';
COMMENT ON COLUMN GEN_TABLE_CONFIG3.STATUS IS '状态 1.启用 0.禁用（一般至少有一个启用的）';
COMMENT ON COLUMN GEN_TABLE_CONFIG3."desc" IS '描述';
COMMENT ON COLUMN GEN_TABLE_CONFIG3.UPDATE_VERSION IS '更新版本';
COMMENT ON COLUMN GEN_TABLE_CONFIG3.UPDATE_TIME IS '更新时间';

-- 表 GEN_TABLE3
CREATE TABLE GEN_TABLE3(
ID INT8 PRIMARY KEY,
TABLE_NAME VARCHAR(200),
TABLE_COMMENT VARCHAR(500),
CURRENT_DB VARCHAR(50),
DATABASE_ID VARCHAR(50),
CLASS_NAME VARCHAR(127),
BASE_PACKAGE VARCHAR(100),
MODULE_NAME VARCHAR(30),
AUTHOR VARCHAR(50),
VERSION VARCHAR(50),
CAMEL_CASE VARCHAR(1),
BACK_DIR VARCHAR(100),
FRONT_DIR VARCHAR(100),
"desc" VARCHAR(255),
CREATE_BY VARCHAR(64),
CREATE_TIME TIMESTAMP NOT NULL,
UPDATE_BY VARCHAR(64),
UPDATE_TIME TIMESTAMP
);

COMMENT ON TABLE  GEN_TABLE3 IS '代码生成::业务表';
COMMENT ON COLUMN GEN_TABLE3.ID IS 'ID';
COMMENT ON COLUMN GEN_TABLE3.TABLE_NAME IS '表名称';
COMMENT ON COLUMN GEN_TABLE3.TABLE_COMMENT IS '表描述';
COMMENT ON COLUMN GEN_TABLE3.CURRENT_DB IS '当前数据库类型(mysql、oracle、postgresql)';
COMMENT ON COLUMN GEN_TABLE3.DATABASE_ID IS 'DB语句类型(mysql、oracle、postgresql)';
COMMENT ON COLUMN GEN_TABLE3.CLASS_NAME IS '实体类名称';
COMMENT ON COLUMN GEN_TABLE3.BASE_PACKAGE IS '包基础路径';
COMMENT ON COLUMN GEN_TABLE3.MODULE_NAME IS '代码所在模块';
COMMENT ON COLUMN GEN_TABLE3.AUTHOR IS '作者';
COMMENT ON COLUMN GEN_TABLE3.VERSION IS '版本';
COMMENT ON COLUMN GEN_TABLE3.CAMEL_CASE IS '接口、字段是否驼峰格式 1.是 0.否';
COMMENT ON COLUMN GEN_TABLE3.BACK_DIR IS '下载后端代码所在文件夹';
COMMENT ON COLUMN GEN_TABLE3.FRONT_DIR IS '下载前端代码所在文件夹';
COMMENT ON COLUMN GEN_TABLE3."desc" IS '备注';
COMMENT ON COLUMN GEN_TABLE3.CREATE_BY IS '创建者';
COMMENT ON COLUMN GEN_TABLE3.CREATE_TIME IS '创建时间';
COMMENT ON COLUMN GEN_TABLE3.UPDATE_BY IS '更新者';
COMMENT ON COLUMN GEN_TABLE3.UPDATE_TIME IS '更新时间';

-- 字段 GEN_TABLE_COLUMN3
CREATE TABLE GEN_TABLE_COLUMN3(
ID INT8 PRIMARY KEY,
TABLE_ID INT8,
COLUMN_NAME VARCHAR(200) ,
COLUMN_COMMENT VARCHAR(500) ,
COLUMN_TYPE VARCHAR(100) ,
JAVA_TYPE VARCHAR(255) ,
JAVA_FIELD VARCHAR(255) ,
IS_PK VARCHAR(1) ,
IS_INCREMENT VARCHAR(1) ,
IS_REQUIRED VARCHAR(1) ,
IS_INSERT VARCHAR(1) ,
IS_EDIT VARCHAR(1) ,
IS_LIST VARCHAR(1) ,
IS_QUERY VARCHAR(1) ,
QUERY_TYPE VARCHAR(200) DEFAULT 'EQ' NOT NULL,
HTML_TYPE VARCHAR(200),
DICT_TYPE VARCHAR(200),
SORT INT4,
CREATE_BY VARCHAR(64) NOT NULL,
CREATE_TIME TIMESTAMP NOT NULL,
UPDATE_BY VARCHAR(64),
UPDATE_TIME TIMESTAMP
);

COMMENT ON TABLE  GEN_TABLE_COLUMN3 IS '代码生成::业务字段';
COMMENT ON COLUMN GEN_TABLE_COLUMN3.ID IS '编号';
COMMENT ON COLUMN GEN_TABLE_COLUMN3.TABLE_ID IS '归属表编号(GEN_TABLE3=>ID)';
COMMENT ON COLUMN GEN_TABLE_COLUMN3.COLUMN_NAME IS '列名称';
COMMENT ON COLUMN GEN_TABLE_COLUMN3.COLUMN_COMMENT IS '列描述';
COMMENT ON COLUMN GEN_TABLE_COLUMN3.COLUMN_TYPE IS '列类型';
COMMENT ON COLUMN GEN_TABLE_COLUMN3.JAVA_TYPE IS 'JAVA类型';
COMMENT ON COLUMN GEN_TABLE_COLUMN3.JAVA_FIELD IS 'JAVA字段名';
COMMENT ON COLUMN GEN_TABLE_COLUMN3.IS_PK IS '是否主键（1是）';
COMMENT ON COLUMN GEN_TABLE_COLUMN3.IS_INCREMENT IS '是否自增（1是）';
COMMENT ON COLUMN GEN_TABLE_COLUMN3.IS_REQUIRED IS '是否必填（1是）';
COMMENT ON COLUMN GEN_TABLE_COLUMN3.IS_INSERT IS '是否为插入字段（1是）';
COMMENT ON COLUMN GEN_TABLE_COLUMN3.IS_EDIT IS '是否编辑字段（1是）';
COMMENT ON COLUMN GEN_TABLE_COLUMN3.IS_LIST IS '是否列表字段（1是）';
COMMENT ON COLUMN GEN_TABLE_COLUMN3.IS_QUERY IS '是否查询字段（1是）';
COMMENT ON COLUMN GEN_TABLE_COLUMN3.QUERY_TYPE IS '查询方式（等于、不等于、大于、小于、范围）若范围会生成两个插叙字段_start、_end';
COMMENT ON COLUMN GEN_TABLE_COLUMN3.HTML_TYPE IS '显示类型（文本框、文本域、下拉框、复选框、单选框、日期控件）';
COMMENT ON COLUMN GEN_TABLE_COLUMN3.DICT_TYPE IS '字典类型';
COMMENT ON COLUMN GEN_TABLE_COLUMN3.SORT IS '排序';
COMMENT ON COLUMN GEN_TABLE_COLUMN3.CREATE_BY IS '创建者';
COMMENT ON COLUMN GEN_TABLE_COLUMN3.CREATE_TIME IS '创建时间';
COMMENT ON COLUMN GEN_TABLE_COLUMN3.UPDATE_BY IS '修改人';
COMMENT ON COLUMN GEN_TABLE_COLUMN3.UPDATE_TIME IS '修改时间';
