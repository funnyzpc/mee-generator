package com.mee.generator.common.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.mee.generator.core.model.MeeResult;
import com.mee.generator.util.ResultBuild;
import com.mee.generator.util.JacksonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

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

    private static final String MENU_STR = "[{\"id\":4,\"title\":\"SQL执行\",\"path\":\"/db_handler\",\"target\":\"_content\",\"hidden\":false},{\"id\":5,\"title\":\"代码生成(mee_admin_v2)\",\"icon\":\"code_gen1\",\"path\":\"/code_gen1\",\"hidden\":false,\"children\":[{\"id\":51,\"title\":\"代码生成\",\"icon\":\"code\",\"path\":\"/code_gen1/gen1.html\",\"hidden\":false,\"target\":\"_content\"},{\"id\":52,\"title\":\"生成配置\",\"icon\":\"configure\",\"path\":\"/code_gen1/gen1_cfg.html\",\"hidden\":false,\"target\":\"_content\"}]},{\"id\":6,\"title\":\"代码生成(mee_admin)\",\"icon\":\"code_gen2\",\"path\":\"/code_gen2\",\"hidden\":false,\"children\":[{\"id\":61,\"title\":\"代码生成\",\"icon\":\"code\",\"path\":\"/code_gen2/gen2.html\",\"hidden\":false,\"target\":\"_content\"},{\"id\":62,\"title\":\"生成配置\",\"icon\":\"configure\",\"path\":\"/gen2_config/gen2_config.html\",\"hidden\":false,\"target\":\"_content\"}]},{\"id\":4,\"title\":\"shadow' blog\",\"icon\":\"guide\",\"path\":\"https://cnblogs.com/funnyzpc\",\"hidden\":false,\"target\":\"_content\"}]";
    public MeeResult<List<HashMap>> buildMenu(){
        return ResultBuild.build(JacksonUtil.toObject(MENU_STR,new TypeReference<List<HashMap>>(){}));
    }


}
