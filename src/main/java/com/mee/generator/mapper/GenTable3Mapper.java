package com.mee.generator.mapper;

import com.mee.generator.entity.GenTable3;
import org.apache.ibatis.annotations.Mapper;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
*   表基本信息
*   @className  GenTable3Mapper
*   @author     shadow
*   @date       2023/4/8 8:48 PM
*   @version    v1.0
*/
@Mapper
public interface GenTable3Mapper {
    List<GenTable3> findList(Map<String, Object> param);

    GenTable3 findById(Object table_id);
    LinkedHashMap findByIdToMap(Object table_id);

    int deleteByTableId(Long table_id);

    List<GenTable3> findTableList(Map<String, Object> param);

    List<GenTable3> findTableByNames(String[] list);

    int insert(GenTable3 table);
    int update(GenTable3 table);
}
