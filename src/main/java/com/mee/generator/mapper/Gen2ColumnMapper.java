package com.mee.generator.mapper;

import com.mee.generator.entity.Gen2Column;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;


/**
*   表字段操作
*   @className  GenTableColumn2Mapper
*   @author     shadow
*   @date       2023/4/8 8:47 PM
*   @version    v1.0
*/
@Mapper
public interface Gen2ColumnMapper {
    List<Gen2Column> findList(Object param);
    List<Gen2Column> findByName(String table_name);
    List<Gen2Column> findByTableId(Object table_id);
    List<Map> findByTableIdToMap(Object table_id);

    int deleteByTableId(Object table_id);
    /**
     * 修改业务字段
     *
     * @param genTableColumn 业务字段信息
     * @return 结果
     */
    int update(Gen2Column genTableColumn);
    int insert(Gen2Column column);
    int insertBatch(List<Gen2Column> columns);
}
