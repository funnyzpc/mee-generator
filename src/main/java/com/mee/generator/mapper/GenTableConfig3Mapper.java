package com.mee.generator.mapper;

import com.mee.generator.entity.GenTableConfig3;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
*   代码生成基本配置
*   @className  GenTableConfig3Mapper
*   @author     shadow
*   @date       2023/4/8 8:48 PM
*   @version    v1.0
*/
@Mapper
public interface GenTableConfig3Mapper {

    List<GenTableConfig3> findList(Object param);
    GenTableConfig3 findById(String id);
    GenTableConfig3 findOneEnable();
    int delete(String id);
    int insert(GenTableConfig3 genTableConfig2);

    int update(Object genTableConfig2);

    int toDisabled(Map<String, Object> param);
}
