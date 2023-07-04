package com.mee.generator.mapper;

import com.mee.generator.entity.Gen2Table;
import org.apache.ibatis.annotations.Mapper;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
*   表基本信息
*   @className  GenTable2Mapper
*   @author     shadow
*   @date       2023/4/8 8:48 PM
*   @version    v1.0
*/
@Mapper
public interface Gen2TableMapper {
    List<Gen2Table> findList(Map<String, Object> param);

    Gen2Table findById(Object table_id);
    LinkedHashMap findByIdToMap(Object table_id);

    int deleteByTableId(Long table_id);

    List<Gen2Table> findTableList(Map<String, Object> param);

    List<Gen2Table> findTableByNames(String[] list);

    int insert(Gen2Table table);
    int update(Gen2Table table);
}
