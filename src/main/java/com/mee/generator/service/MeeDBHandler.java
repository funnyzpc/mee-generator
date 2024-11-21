package com.mee.generator.service;

import com.mee.generator.core.model.MeeResult;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 数据库操作
 *
 * @author shaoow
 * @version 1.0
 * @className MeeDBHandler
 * @date 2023/7/5 13:40
 */
public interface MeeDBHandler {

    Map<String,String> dbInfo();
    MeeResult<List> exec(String sql);
    MeeResult query(String sql,Integer page_no,Integer page_size);
    void export(String sql, Integer pageNo, Integer pageSize, HttpServletResponse response);
}
