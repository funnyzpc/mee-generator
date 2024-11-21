//package com.mee.generator.service.impl;
//
//import com.mee.generator.common.constant.GenConstants;
//import com.mee.generator.core.model.MeeResult;
//import com.mee.generator.entity.*;
//import com.mee.generator.enums.CamelCaseEnum;
//import com.mee.generator.mapper.Gen2TableMapper;
//import com.mee.generator.mapper.Gen2ColumnMapper;
//import com.mee.generator.mapper.Gen2ConfigMapper;
//import com.mee.generator.util.*;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//
///**
//*   代码生成表倒入操作
//*   @className  CodeGen2ImportServiceImpl
//*   @author     shadow
//*   @date       2023/4/10 8:42 PM
//*   @version    v1.0
//*/
//@Service
//public class CodeGen2ImportServiceImpl {
//    /**
//     * 日志
//     */
//    private static final Logger LOG = LoggerFactory.getLogger(CodeGen2ImportServiceImpl.class);
//
//    @Autowired
//    private Gen2TableMapper genTable2Mapper;
//    @Autowired
//    private Gen2ConfigMapper genTableConfig2Mapper;
//    @Autowired
//    private Gen2ColumnMapper genTableColumn2Mapper;
//    @Autowired
//    private SeqGenServiceImpl seqGenService;
//    @Value("${spring.datasource.platform}")
//    private String platform;
//
//    /**
//     * 导入表结构
//     *
//     * @param table_item 导入表列表
//     */
//    @Transactional(readOnly = false,rollbackFor = Exception.class)
//    public MeeResult importGenTable(String[] table_item) {
//        // 基础配置信息
//        Gen2Config config2 = genTableConfig2Mapper.findOneEnable();
//        if( null==config2 || null==config2.getVersion()){
//            LOG.error("未能找到有效的生成配置:{}",table_item);
//            return ResultBuild.fail("未能找到有效的生成配置");
//        }
//        // 查询表信息
//        List<Gen2Table> table_list = genTable2Mapper.findTableByNames(table_item);
//        final String operator_name = "auto";
//        final LocalDateTime now = DateUtil.now();
//        for (Gen2Table table : table_list){
//            String table_name = table.getTable_name();
////            GenUtils.initTable(table, operator_name);
//            table.setId(seqGenService.genShortPrimaryKey());
//            //table.setTable_name();
//            //table.setTable_comment();
//            table.setCurrent_db(platform.toLowerCase());
//            table.setDatabase_id(config2.getDatabase_id());
//            table.setClass_name(GenUtils.convertClassName(table_name));
//            table.setBase_package(config2.getBase_package());
//            table.setModule_name(config2.getModule_name());
//            table.setAuthor(config2.getAuthor());
//            table.setVersion(config2.getVersion());
//            table.setCamel_case(config2.getCamel_case());
////            table.setDesc();
//            table.setCreate_by(operator_name);
//            table.setCreate_time(now);
//            table.setUpdate_by(operator_name);
//            table.setUpdate_time(now);
//            if( genTable2Mapper.insert(table)>0 ){
//                this.genColumn(table);
//            }
//        }
//        return ResultBuild.build(table_list.size());
//    }
//
//    public void genColumn(Gen2Table table){
//        final String table_id = table.getId();
//        final String table_name = table.getTable_name();
//        final String opt_name = table.getCreate_by();
//        final LocalDateTime now = table.getCreate_time();
//        // 保存列信息
//        List<Gen2Column> table_columns = genTableColumn2Mapper.findByName(table_name);
//        if( null==table_columns || table_columns.isEmpty()){
//            LOG.error("未能找到列信息:{}",table_name);
//            return;
//        }
//        List<Gen2Column> column_list = new ArrayList<Gen2Column>(table_columns.size());
//        for (Gen2Column column : table_columns) {
//            final String column_name = column.getColumn_name();
//            column.setId(seqGenService.genPrimaryKey());
//            column.setTable_id(table_id);
////            column.setColumn_name();
////            column.setColumn_comment();
////            column.setColumn_type();
//            column.setJava_type(GenUtils2.toJavaType(column));
//            // 需要处理驼峰问题
//            column.setJava_field(CamelCaseEnum.Y.equals(table.getCamel_case())?StringUtils.toCamelCase(column_name):column.getColumn_name());
////            column.setIs_pk();
////            column.setIs_increment();
////            column.setIs_required();
//            column.setIs_insert("1");
//            column.setIs_edit("1");
//            column.setIs_list("0");
//            // 查询字段
//            column.setIs_query("0");
//            if (!GenUtils.arraysContains(GenConstants.COLUMNNAME_NOT_QUERY,column_name) && !"!".equals(column.getIs_pk()) ){
//                column.setIs_query(GenConstants.REQUIRE);
//            }
//            column.setQuery_type("=");
//            column.setHtml_type("input"); //TODO ...
//            column.setDict_type(null);
////            column.setSort();
//            column.setCreate_by(opt_name);
//            column.setCreate_time(now);
//            column.setUpdate_by(opt_name);
//            column.setUpdate_time(now);
////            GenUtils.initColumnField(column, table);
//            column_list.add(column);
//        }
//        int insert_count = genTableColumn2Mapper.insertBatch(column_list);
//        LOG.info("已写入表{} {}列数据",table_columns,insert_count);
//    }
//
//
//}
