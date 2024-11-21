package com.mee.generator.test.service;

import com.mee.generator.util.JacksonUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Map;

/**
 * DDL测试用
 *
 * @author shaoow
 * @version 1.0
 * @className JdbcTemplateTest
 * @date 2023/7/5 13:16
 */
@SpringBootTest
@ActiveProfiles("postgresql")
public class JdbcTemplateTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void test01(){
        String sql = " \n" +
                "DROP TABLE IF EXISTS \"sys_user\";\n" +
                "CREATE TABLE \"sys_user\" (\n" +
                "  \"id\" int8 PRIMARY KEY,\n" +
                "  \"dept_id\" int8,\n" +
                "  \"user_name\" varchar(50) NOT NULL,\n" +
                "  \"nick_name\" varchar(50),\n" +
                "  \"gender\" varchar(1),\n" +
                "  \"phone\" varchar(22),\n" +
                "  \"email\" varchar(50) ,\n" +
                "  \"password\" varchar(256) NOT NULL,\n" +
                "  \"register_date\" timestamp(6) NOT NULL,\n" +
                "  \"last_login_date\" timestamp(6),\n" +
                "  \"pwd_reset_time\" timestamp(6),\n" +
                "  \"status\" numeric(1) NOT NULL,\n" +
                "  \"del_flag\" numeric(1) NOT NULL,\n" +
                "  \"create_time\" timestamp(6) NOT NULL,\n" +
                "  \"create_by\" varchar(50),\n" +
                "  \"update_time\" timestamp(6) NOT NULL,\n" +
                "  \"update_by\" varchar(50)\n" +
                ");\n" +
                "\n" +
                "COMMENT ON COLUMN \"sys_user\".\"id\" IS '表ID/用户ID';\n" +
                "COMMENT ON COLUMN \"sys_user\".\"dept_id\" IS '部门ID(保留字段暂不使用)';\n" +
                "COMMENT ON COLUMN \"sys_user\".\"user_name\" IS '用户名称';\n" +
                "COMMENT ON COLUMN \"sys_user\".\"nick_name\" IS '用户昵称';\n" +
                "COMMENT ON COLUMN \"sys_user\".\"gender\" IS 'M.男 F.女';\n" +
                "COMMENT ON COLUMN \"sys_user\".\"phone\" IS '手机号码';\n" +
                "COMMENT ON COLUMN \"sys_user\".\"email\" IS '用户email(可用于登陆)';\n" +
                "COMMENT ON COLUMN \"sys_user\".\"password\" IS '用户密码';\n" +
                "COMMENT ON COLUMN \"sys_user\".\"register_date\" IS '用户注册时间';\n" +
                "COMMENT ON COLUMN \"sys_user\".\"last_login_date\" IS '最后登录时间';\n" +
                "COMMENT ON COLUMN \"sys_user\".\"pwd_reset_time\" IS '密码最后重置时间';\n" +
                "COMMENT ON COLUMN \"sys_user\".\"status\" IS '状态1.启用 0.禁用';\n" +
                "COMMENT ON COLUMN \"sys_user\".\"del_flag\" IS '删除标记1.正常 0.删除';\n" +
                "COMMENT ON COLUMN \"sys_user\".\"create_time\" IS '创建时间';\n" +
                "COMMENT ON COLUMN \"sys_user\".\"create_by\" IS '创建人';\n" +
                "COMMENT ON COLUMN \"sys_user\".\"update_time\" IS '创建时间';\n" +
                "COMMENT ON COLUMN \"sys_user\".\"update_by\" IS '创建人';\n" +
                "COMMENT ON TABLE \"sys_user\" IS '系统::用户信息表'; commit;";
        jdbcTemplate.execute(sql);
    }

    @Test
    public void test02(){
        //String sql = "select 1,32,3";
        String sql = "select * from gen2_column gc";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);
        System.out.println(JacksonUtil.toJsonString(maps));
    }

}
