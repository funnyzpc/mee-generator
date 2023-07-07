package com.mee.generator.mapper;

import org.apache.ibatis.annotations.Mapper;

/**
 * 数据库操作相关查询
 *
 * @author shaoow
 * @version 1.0
 * @className MeeDBHandlerMapper
 * @date 2023/7/5 13:56
 */
@Mapper
public interface MeeDBHandlerMapper {

    /**
     * 获取数据库当前schema
     * @return
     */
    String findSchema();


}
