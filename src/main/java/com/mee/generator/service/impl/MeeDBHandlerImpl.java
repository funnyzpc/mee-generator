package com.mee.generator.service.impl;

import com.mee.generator.common.model.MeeResult;
import com.mee.generator.mapper.MeeDBHandlerMapper;
import com.mee.generator.service.MeeDBHandler;
import com.mee.generator.util.CSVWriteUtil;
import com.mee.generator.util.ResultBuild;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据库操作
 *
 * @author shaoow
 * @version 1.0
 * @className MeeDBHandlerImpl
 * @date 2023/7/5 13:40
 */
@Service
public class MeeDBHandlerImpl implements MeeDBHandler {

    /**
     * 日志
     */
    private static final Logger LOG = LoggerFactory.getLogger(MeeDBHandlerImpl.class);

    /**
     * DB厂商
     */
    @Value("${spring.datasource.platform}")
    private String dbPlatform;

    @Autowired
    private MeeDBHandlerMapper meeDBHandlerMapper;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Map<String,String> dbInfo(){
        String schema = meeDBHandlerMapper.findSchema();
        Map<String,String> result = new HashMap<>(2,1);
        result.put("platform",dbPlatform);
        result.put("schema",schema);
        return result;
    }
    /**
     * 执行语句（ddl、dml、dql）
     * @param sql
     * @return
     */
    @Override
    public MeeResult<List> exec(String sql){
        if( "".equals(sql.trim()) ){
            return ResultBuild.fail("必要参数不可为空![SQL]");
        }
        if(sql.trim().toUpperCase().startsWith("SELECT")){
            return ResultBuild.fail("执行不可为查询语句(SELECT)!");
        }
        //Map<String,Object> result = new HashMap<String,Object>(2,1);
        List<Object[]> result = new ArrayList<>(2);
        try{
            jdbcTemplate.execute(sql);
            result.add(new String[]{"执行结果"});
            result.add(new Boolean[]{Boolean.TRUE});
        }catch (Exception e){
            LOG.error("执行出现错误:{}",sql,e);
            result.add(new String[]{"执行结果","异常消息"});
            result.add(new Object[]{Boolean.FALSE,e.getMessage()});
        }
        return ResultBuild.build(result);
    }

    @Override
    public MeeResult query(String sql,Integer page_no,Integer page_size){
        if( "".equals(sql.trim()) ){
            return ResultBuild.fail("必要参数不可为空![SQL]");
        }
        if(!sql.trim().toUpperCase().startsWith("SELECT")){
            return ResultBuild.fail("必须是查询(SELECT)语句!");
        }
        String _sql=sql;
        if(!this.hasLimit(sql)){
            _sql=this.buildPage(sql,page_no,page_size);
        }
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(_sql);
        List<Object[]> data_list = this.fetch(sqlRowSet,10000);
        return ResultBuild.build(data_list);
    }

    @Override
    public void export(String sql, Integer page_no, Integer page_size, HttpServletResponse response) {
        if( "".equals(sql.trim()) ){
            throw new RuntimeException("SQL不可为空");
        }
        if(!sql.trim().toUpperCase().startsWith("SELECT")){
            throw new RuntimeException("尽可为查询语句");
        }
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(sql);
        List<Object[]> data_list = this.fetch(sqlRowSet,200000);
        File file = CSVWriteUtil.toCSV(data_list);
        ResultBuild.toResponse(response,file);
    }

    private Boolean hasLimit(String sql){
        switch (dbPlatform){
            case "postgresql":
            case "mysql":
                return sql.toUpperCase().contains(" LIMIT ");
            case "oracle":
                return sql.toUpperCase().contains(" ROWNUM ");
            default:
                return Boolean.FALSE;
        }
    }

    private String buildPage( String sql,Integer page_no,Integer page_size ){
        switch (dbPlatform){
            case "postgresql":
            case "mysql":
                return sql+" limit "+page_size+" offset "+(page_size*page_no)+"";
            case "oracle":
                return sql;
            default:
                return sql;
        }
    }

    public List<Object[]> fetch(SqlRowSet resultSet,int max_count)  {
        long begin_time=System.currentTimeMillis();
        int idx = 0;
        List<Object[]> data_list = new ArrayList<Object[]>(max_count);
        SqlRowSetMetaData meta_data = resultSet.getMetaData();
        int column_count = meta_data.getColumnCount();
        String[] header_info = new String[column_count];
        for(int j=0;j<column_count;j++ ){
            String columnName = meta_data.getColumnName(j + 1);
            String columnLabel = meta_data.getColumnLabel(j + 1);
            header_info[j]=columnName.equals(columnLabel)?columnName:(columnName+"/"+columnLabel);
        }
        data_list.add(header_info);
        while (resultSet.next()) {
            // 大于指定行则结束
            if( idx > max_count){
                break;
            }
            idx++;
            Object[] data_item = new Object[meta_data.getColumnCount()];
            for(int i=1;i<=meta_data.getColumnCount();i++){
                data_item[i-1]=resultSet.getObject(i);
            }
            data_list.add(data_item);
        }
        long end_time=System.currentTimeMillis();
        LOG.info("fetch::total_count:" + idx + ";fetchSize:" + (data_list.size() - 1) + ";耗时:" + (end_time - begin_time) + "ms");
        return data_list;
    }



}
