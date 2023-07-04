package com.mee.generator.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ZipUtil;
import com.mee.generator.common.model.MeeResult;
import com.mee.generator.entity.Gen2Column;
import com.mee.generator.entity.Gen2Table;
import com.mee.generator.mapper.Gen2ColumnMapper;
import com.mee.generator.mapper.Gen2TableMapper;
import com.mee.generator.util.ResultBuild;
import com.mee.generator.util.SystemUtil;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * 代码生成
 * @author shadow
 */
@Service
public class CodeGen2ServiceImpl {
    /**
     * 日志
     */
    private static final Logger LOG = LoggerFactory.getLogger(CodeGen2ServiceImpl.class);
    @Autowired
    private Gen2TableMapper genTable2Mapper;
    @Autowired
    private Gen2ColumnMapper genTableColumn2Mapper;

    @Autowired
    private GenTable2ServiceImpl genTable2Service;

    @Transactional(readOnly = false,rollbackFor = Exception.class)
    public MeeResult gen2FieldUpdate(List<Gen2Column> genTableColumnList){
        LOG.info("请求参数:{}",genTableColumnList);
        if( null==genTableColumnList || genTableColumnList.isEmpty() ){
            return ResultBuild.fail("必要参数不可为空！");
        }
        for( Gen2Column item:genTableColumnList ){
//            genTableColumn2Mapper.updateGenTableColumn(item);
            genTableColumn2Mapper.update(item);
        }
        return ResultBuild.SUCCESS();
    }


    /**
     * 下载代码
     * @param response response
     * @param table_id 文件id
     * @throws IOException
     */
    public void download(HttpServletResponse response, String table_id)throws Exception{
        if( null==table_id || "".equals(table_id.trim()) ){
            throw  new RuntimeException("必要参数不应为空[table_id]");
        }
        // 生成代码
        MeeResult result = genTable2Service.previewCode(table_id);
        if( null==result || !ResultBuild.is_ok(result) ){
            throw new RuntimeException("未能生成代码");
        }
        // 准备数据
        Gen2Table gen_table2 = genTable2Mapper.findById(table_id);
        String class_name = gen_table2.getClass_name();
        String table_name = gen_table2.getTable_name();
        String zip_dir = File.separator+gen_table2.getTable_name();
        File code_dir = new File(SystemUtil.file_base_path+zip_dir);
        code_dir.mkdirs();
        LinkedHashMap<String,String> data= (LinkedHashMap<String, String>) result.getData();
        for( Map.Entry<String,String> item:data.entrySet()){
            String file_name = this.buildFileName(item.getKey(),class_name,table_name);
            String file_content = item.getValue();
            // 写入文件
            File code_file = new File(code_dir.getPath()+File.separator+file_name);
            FileUtil.writeBytes(file_content.getBytes(StandardCharsets.UTF_8),code_file);
        }
        // File zip_file = ZipUtil.zip(code_dir.getPath(),code_dir.getPath());
        File zip_file = ZipUtil.zip(code_dir.getPath());
        // 写入response
        this.toResponse(response,FileUtil.readBytes(zip_file),zip_file.getName());
        // remove file
        FileUtil.del(code_dir);
        FileUtil.del(zip_file);
    }

    /**
     * 构建代码文件名
     * @param source_file_name 原始文件信息
     * @param class_name 类名
     * @return 文件名
     */
    private String buildFileName(String source_file_name,String class_name,String table_name){
        String file_name = source_file_name
                .replace("ftl2/java/","")
                .replace("ftl2/xml/","")
//                .replace("ftl2/js/","")
                .replace("ftl2/web/","")
                .replace(".ftl","");
        switch (file_name){
            case "entity.java":
                return class_name+".java";
            case "dto.java":
                return class_name+"DTO.java";
            case "page.html":
                return table_name+".html";
            case "page.js":
                return table_name+".js";
            default:
                // example: mapper.java to FileMapper
                String prefix = file_name.substring(0, 1).toUpperCase(Locale.ROOT);
                return class_name+(prefix+file_name.substring(1));
        }
    }
    /**
     * 生成zip文件
     */
    private void toResponse(HttpServletResponse response, byte[] data,String table_name) throws IOException {
        response.reset();
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Expose-Headers", "Content-Disposition");
        response.setHeader("Content-Disposition", "attachment; filename=\""+table_name+"\"");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream; charset=UTF-8");
        IOUtils.write(data, response.getOutputStream());
    }
}
