package com.mee.generator.mapper;

import com.mee.generator.entity.GenTableColumn;
import com.mee.generator.entity.GenTableColumn3;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;


/**
*   表字段操作
*   @className  GenTableColumn3Mapper
*   @author     shadow
*   @date       2023/4/8 8:47 PM
*   @version    v1.0
*/
@Mapper
public interface GenTableColumn3Mapper {
    List<GenTableColumn3> findList(Object param);
    List<GenTableColumn3> findByName(String table_name);
    List<GenTableColumn3> findByTableId(Object table_id);
    List<Map> findByTableIdToMap(Object table_id);
    /**
     * 修改业务字段
     *
     * @param genTableColumn 业务字段信息
     * @return 结果
     */
    int updateGenTableColumn(GenTableColumn3 genTableColumn);
    int deleteByTableId(Object table_id);

    int insert(GenTableColumn3 column);
    int insertBatch(List<GenTableColumn3> columns);
}
