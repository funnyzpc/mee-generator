package com.mee.generator.mapper;

import com.mee.generator.entity.Gen2Config;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
*   代码生成基本配置
*   @className  GenTableConfig2Mapper
*   @author     shadow
*   @date       2023/4/8 8:48 PM
*   @version    v1.0
*/
@Mapper
public interface Gen2ConfigMapper {

    List<Gen2Config> findList(Object param);
    Gen2Config findById(String id);
    Gen2Config findOneEnable();
    Integer findMaxVersion();
    int delete(String id);
    int insert(Gen2Config genTableConfig2);

    int update(Object genTableConfig2);

    int toDisabled(Map<String, Object> param);
}
