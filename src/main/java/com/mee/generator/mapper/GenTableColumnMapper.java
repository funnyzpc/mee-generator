package com.mee.generator.mapper;

import java.util.List;
import java.util.Map;

import com.mee.generator.entity.GenTableColumn;
import org.apache.ibatis.annotations.Mapper;

/**
 * 业务字段 数据层
 * 
 * @author mee
 */
@Mapper
public interface GenTableColumnMapper {
    /**
     * 根据表名称查询列信息
     * 
     * @param table_name 表名称
     * @return 列信息
     */
    List<GenTableColumn> selectDbTableColumnsByName(String table_name);

    /**
     * 查询业务字段列表
     * 
     * @param tableId 业务字段编号
     * @return 业务字段集合
     */
    List<GenTableColumn> selectGenTableColumnListByTableId(Long tableId);

    /**
     * 新增业务字段
     * 
     * @param genTableColumn 业务字段信息
     * @return 结果
     */
    int insertGenTableColumn(GenTableColumn genTableColumn);

    /**
     * 修改业务字段
     * 
     * @param genTableColumn 业务字段信息
     * @return 结果
     */
    int updateGenTableColumn(GenTableColumn genTableColumn);

    /**
     * 删除业务字段
     * 
     * @param genTableColumns 列数据
     * @return 结果
     */
    int deleteGenTableColumns(List<GenTableColumn> genTableColumns);

//    /**
//     * 批量删除业务字段
//     *
//     * @param ids 需要删除的数据ID
//     * @return 结果
//     */
//    public int deleteGenTableColumnByIds(Long[] ids);
    int deleteByTableId(Long table_id);

    List<Map> findList(Map param);
    List<Map> findList2(Map param);
}
