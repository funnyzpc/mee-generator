package com.mee.generator.common.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.mee.generator.entity.ResultBuild;
import com.mee.generator.util.JacksonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 主框架
 *
 * @author shaoow
 * @version 1.0
 * @className IndexServiceImpl
 * @date 2023/6/15 11:16
 */
@Service
public class IndexServiceImpl {
    private static final Logger LOG = LoggerFactory.getLogger(IndexServiceImpl.class);

    //static final String MENU_STR = "[{\"id\":1,\"title\":\"系统管理\",\"icon\":\"system\",\"path\":\"/system\",\"hidden\":true,\"children\":[{\"id\":11,\"title\":\"用户管理\",\"icon\":\"user\",\"path\":\"/user.html\",\"hidden\":false,\"target\":\"_content\"},{\"id\":12,\"title\":\"角色管理\",\"icon\":\"peoples\",\"path\":\"/role\",\"hidden\":true,\"target\":\"_content\"},{\"id\":13,\"title\":\"菜单管理\",\"icon\":\"tree-table\",\"path\":\"/_menu.html\",\"hidden\":false,\"target\":\"_content\"},{\"id\":14,\"title\":\"部门管理\",\"icon\":\"tree\",\"path\":\"https://www.csdn.net/\",\"hidden\":false,\"target\":\"_content\"},{\"id\":15,\"title\":\"岗位管理\",\"icon\":\"post\",\"path\":\"/post\",\"hidden\":true,\"target\":\"_content\"},{\"id\":16,\"title\":\"字典管理\",\"icon\":\"dict\",\"path\":\"/dict\",\"hidden\":true,\"target\":\"_content\"},{\"id\":11,\"title\":\"参数设置\",\"icon\":\"edit\",\"path\":\"/config\",\"hidden\":true,\"target\":\"_content\"},{\"id\":17,\"title\":\"通知公告\",\"icon\":\"message\",\"path\":\"/notice\",\"hidden\":true,\"target\":\"_content\"},{\"id\":18,\"title\":\"日志管理\",\"icon\":\"log\",\"path\":\"/log\",\"hidden\":false,\"children\":[{\"id\":181,\"title\":\"操作日志\",\"icon\":\"form\",\"path\":\"/operlog.html\",\"hidden\":false,\"target\":\"_content\"},{\"id\":182,\"title\":\"登录日志\",\"icon\":\"logininfor\",\"path\":\"/logininfor\",\"hidden\":false,\"target\":\"_content\"}]}]},{\"id\":2,\"title\":\"系统监控\",\"icon\":\"monitor\",\"path\":\"/monitor\",\"hidden\":true,\"children\":[{\"id\":21,\"title\":\"在线用户\",\"icon\":\"online\",\"path\":\"/online\",\"hidden\":false,\"target\":\"_content\"}]},{\"id\":3,\"title\":\"系统工具\",\"icon\":\"tool\",\"path\":\"/tool\",\"hidden\":true,\"children\":[{\"id\":31,\"title\":\"表单构建\",\"icon\":\"build\",\"path\":\"/build\",\"hidden\":false,\"target\":\"_content\"},{\"id\":32,\"title\":\"代码生成\",\"icon\":\"code\",\"path\":\"/gen\",\"hidden\":true,\"target\":\"_content\"},{\"id\":33,\"title\":\"系统接口\",\"icon\":\"swagger\",\"path\":\"/swagger\",\"hidden\":false,\"target\":\"_content\"}]},{\"id\":5,\"title\":\"代码生成(mee_admin_v2)\",\"icon\":\"code_gen1\",\"path\":\"/code_gen1\",\"hidden\":false,\"children\":[{\"id\":51,\"title\":\"代码生成\",\"icon\":\"code\",\"path\":\"/code_gen1/gen1.html\",\"hidden\":false,\"target\":\"_content\"},{\"id\":52,\"title\":\"生成配置\",\"icon\":\"configure\",\"path\":\"/code_gen1/gen1_cfg.html\",\"hidden\":false,\"target\":\"_content\"}]},{\"id\":6,\"title\":\"代码生成(mee_admin)\",\"icon\":\"code_gen2\",\"path\":\"/code_gen2\",\"hidden\":false,\"children\":[{\"id\":61,\"title\":\"代码生成\",\"icon\":\"code\",\"path\":\"/code_gen2/gen2.html\",\"hidden\":false,\"target\":\"_content\"},{\"id\":62,\"title\":\"生成配置\",\"icon\":\"configure\",\"path\":\"/gen2_config/gen2_config.html\",\"hidden\":false,\"target\":\"_content\"}]},{\"id\":7,\"title\":\"代码生成(sccfc)\",\"icon\":\"code_gen3\",\"path\":\"/code_gen3\",\"hidden\":false,\"children\":[{\"id\":71,\"title\":\"代码生成\",\"icon\":\"code\",\"path\":\"/code_gen3/gen3.html\",\"hidden\":false,\"target\":\"_content\"},{\"id\":72,\"title\":\"生成配置\",\"icon\":\"configure\",\"path\":\"/code_gen3_config/gen3_config.html\",\"hidden\":false,\"target\":\"_content\"}]},{\"id\":4,\"title\":\"shadow website\",\"icon\":\"guide\",\"path\":\"http://baidu.com\",\"hidden\":false,\"target\":\"_blank\"}]";
    //static final String MENU_STR = "[{\"id\":6,\"title\":\"代码生成(for mee_admin)\",\"icon\":\"code_gen2\",\"path\":\"/code_gen2\",\"hidden\":false,\"children\":[{\"id\":61,\"title\":\"代码生成\",\"icon\":\"code\",\"path\":\"/code_gen2/gen2.html\",\"hidden\":false,\"target\":\"_content\"},{\"id\":62,\"title\":\"生成配置\",\"icon\":\"configure\",\"path\":\"/gen2_config/gen2_config.html\",\"hidden\":false,\"target\":\"_content\"}]},{\"id\":4,\"title\":\"shadow's blog\",\"icon\":\"guide\",\"path\":\"http://cnblogs.com/funnyzpc\",\"hidden\":false,\"target\":\"_content\"}]";
    static final String MENU_STR = "[{\"id\":3,\"title\":\"SQL执行\",\"path\":\"/db_handler\",\"target\":\"_content\",\"hidden\":false},{\"id\":6,\"title\":\"代码生成(for mee_admin)\",\"icon\":\"code_gen2\",\"path\":\"/code_gen2\",\"hidden\":false,\"children\":[{\"id\":61,\"title\":\"代码生成\",\"icon\":\"code\",\"path\":\"/code_gen2/gen2.html\",\"hidden\":false,\"target\":\"_content\"},{\"id\":62,\"title\":\"生成配置\",\"icon\":\"configure\",\"path\":\"/gen2_config/gen2_config.html\",\"hidden\":false,\"target\":\"_content\"}]},{\"id\":4,\"title\":\"shadow\\u0027s blog\",\"icon\":\"guide\",\"path\":\"http://cnblogs.com/funnyzpc\",\"hidden\":false,\"target\":\"_content\"}]";
    public Map buildMenu(){
        return ResultBuild.success(JacksonUtil.toObject(MENU_STR,new TypeReference<List<HashMap>>(){}));
    }


}
