package com.mee.generator.service.impl;

import com.mee.generator.core.model.MeeResult;
import com.mee.generator.entity.GenTable;
import com.mee.generator.entity.GenTableColumn;
import com.mee.generator.util.ResultBuild;
import com.mee.generator.mapper.GenTableColumnMapper;
import com.mee.generator.mapper.GenTableMapper;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

/**
 * 代码生成
 * @author shadow
 */
@Service
public class CodeGen1ServiceImpl {
    private static final Logger log = LoggerFactory.getLogger(CodeGen1ServiceImpl.class);
    @Autowired
    private GenTableMapper genTableMapper;
    @Autowired
    private GenTableColumnMapper genTableColumnMapper;

    @Autowired
    private GenTableServiceImpl genTableService;

    @Transactional(readOnly = false,rollbackFor = Exception.class)
    public MeeResult gen1FieldUpdate(List<GenTableColumn> genTableColumnList){
        if( null==genTableColumnList || genTableColumnList.isEmpty() ){
            return ResultBuild.fail("必要参数不可为空！");
        }
        for( GenTableColumn item:genTableColumnList ){
            genTableColumnMapper.updateGenTableColumn(item);
        }
        return ResultBuild.SUCCESS();
    }


    public void download(HttpServletResponse response, Long table_id)throws IOException{
        if( null==table_id ){
            throw  new RuntimeException("必要参数不应为空[table_id]");
        }
        GenTable genTable = genTableMapper.selectGenTableById(table_id);
        if( genTable==null ){
            throw  new RuntimeException("未能找到表配置["+table_id+"]");
        }
        byte[] data = this.downloadCode(genTable);
        genCode(response, data,genTable.getTable_name());
    }


    /**
     * 生成代码（下载方式）
     *
     * @param table 表
     * @return 数据
     */
    public byte[] downloadCode( GenTable table) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);
        genTableService.generatorCode(table, zip);
        IOUtils.closeQuietly(zip);
        return outputStream.toByteArray();
    }
    /**
     * 生成zip文件
     */
    private void genCode(HttpServletResponse response, byte[] data,String table_name) throws IOException {
        response.reset();
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Expose-Headers", "Content-Disposition");
        response.setHeader("Content-Disposition", "attachment; filename=\""+table_name+".zip\"");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream; charset=UTF-8");
        IOUtils.write(data, response.getOutputStream());
    }
}
