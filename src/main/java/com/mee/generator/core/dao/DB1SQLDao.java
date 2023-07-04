package com.mee.generator.core.dao;


import com.mee.generator.entity.BaseEntity;

import java.util.List;
import java.util.Map;

/**
 * @author shadow
 * 系统数据库接口 mysql
 */
public interface DB1SQLDao extends SQLDao {

    <T> T queryOne(String id, Map params);
    <T extends Object,R> List<R> query(String id, T params);

    int create(String id, Object params);
    int create(String id, List params);
//    <P extends BaseEntity> int update(String id, P params);
    int update(String id, Object param);
    int delete(String id, Object params);
}
