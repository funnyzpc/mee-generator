package com.mee.generator.service.impl;

import com.mee.generator.core.model.MeeResult;
import com.mee.generator.entity.Gen2Config;
import com.mee.generator.util.ResultBuild;
import com.mee.generator.mapper.Gen2ConfigMapper;
import com.mee.generator.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * 代码生成配置业务类
 *
 * @author shadow
 * @version v1.0
 * @className CodeGen2ConfigServiceImpl
 * @date 2023/4/8 10:39 PM
 */
@Service
public class CodeGen2ConfigServiceImpl {
    private static final Logger LOG = LoggerFactory.getLogger(CodeGen2ConfigServiceImpl.class);

    @Autowired
    private Gen2ConfigMapper genTableConfig2Mapper;
    @Autowired
    private SeqGenServiceImpl seqGenService;

    @Transactional(rollbackFor = Exception.class)
    public MeeResult add(Gen2Config gen2Config) {
        if( null==gen2Config.getDatabase_id() || "".equals(gen2Config.getDatabase_id()) ){
            return ResultBuild.fail("必要参数不可为空！[数据库类型]");
        }
        gen2Config.setId(seqGenService.genShortPrimaryKey());
        gen2Config.setStatus(0);
        // genTableConfig2.setUpdate_version();
        gen2Config.setUpdate_time(DateUtil.now());
        Integer max_version = genTableConfig2Mapper.findMaxVersion();
        gen2Config.setUpdate_version(max_version);
        int insert_count =  genTableConfig2Mapper.insert(gen2Config);
        return ResultBuild.build(insert_count);
    }

    @Transactional(rollbackFor = Exception.class)
    public MeeResult update(Gen2Config genTableConfig2) {
        if( null==genTableConfig2.getId() || "".equals(genTableConfig2.getId()) || null==genTableConfig2.getDatabase_id() ){
            return ResultBuild.fail("必要参数不可为空！[id、数据库类型]");
        }
        genTableConfig2.setUpdate_time(DateUtil.now());
        int update_count =  genTableConfig2Mapper.update(genTableConfig2);
        return ResultBuild.build(update_count);
    }

    /**
     * 删除数据
     * @param id 数据主键
     * @return .
     */
    public MeeResult delete(String id) {
        if( null==id || "".equals(id) ){
            return ResultBuild.fail("必要参数不可为空【ID】");
        }
        // 是否已经开启（status=1）
        Gen2Config genTableConfig2 = genTableConfig2Mapper.findById(id);
        if( null==genTableConfig2 ){
            return ResultBuild.fail("未能找到配置id="+id);
        }
        if( 1==genTableConfig2.getStatus() ){
            return ResultBuild.fail("已经启用的配置项不可删除");
        }
        // 执行物理删除
        int delete_count = genTableConfig2Mapper.delete(id);
        return ResultBuild.build(delete_count);
    }

    /**
     * 启用配置
     * @param genTableConfig2
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public MeeResult<Integer> enable(Gen2Config genTableConfig2) {
        if( null==genTableConfig2 || null==genTableConfig2.getId() || "".equals(genTableConfig2.getId())){
            LOG.error("必要参数不可为空:id->{}",genTableConfig2);
            return ResultBuild.fail("必要参数不可为空[id]");
        }
        Map<String,Object> param = new HashMap<>(4,1);
        param.put("id",genTableConfig2.getId());
        param.put("status",1);
        param.put("update_time",DateUtil.now());
        // 先启用
        int update_count = genTableConfig2Mapper.update(param);
        // 其他的禁用
        param.put("status",0);
        int update_count2 = genTableConfig2Mapper.toDisabled(param);
        LOG.info("已经启用{} {}，禁用{}",genTableConfig2.getId(),update_count,update_count2);
        return ResultBuild.build(update_count+update_count2);
    }
}
