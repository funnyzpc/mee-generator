package com.mee.generator.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.mee.generator.common.constant.Constants;
import com.mee.generator.common.constant.GenConstants;
import com.mee.generator.enums.TplCategoryEnum;
import com.mee.generator.mapper.GenTableColumnMapper;
import com.mee.generator.mapper.GenTableMapper;
import com.mee.generator.util.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mee.generator.entity.GenTable;
import com.mee.generator.entity.GenTableColumn;

/**
 * 业务 服务层实现
 * 
 * @author mee
 */
@Service
public class GenTableServiceImpl {
    private static final Logger log = LoggerFactory.getLogger(GenTableServiceImpl.class);

    @Autowired
    private GenTableMapper genTableMapper;

    @Autowired
    private GenTableColumnMapper genTableColumnMapper;

    /**
     * 查询业务信息
     * 
     * @param id 业务ID
     * @return 业务信息
     */
    public GenTable selectGenTableById(Long id)
    {
        GenTable genTable = genTableMapper.selectGenTableById(id);
        setTableFromOptions(genTable);
        return genTable;
    }

//    /**
//     * 查询业务列表
//     *
//     * @param genTable 业务信息
//     * @return 业务集合
//     */
//    @Override
//    public List<GenTable> selectGenTableList(GenTable genTable)
//    {
//        return genTableMapper.selectGenTableList(genTable);
//    }

//    /**
//     * 查询据库列表
//     *
//     * @param genTable 业务信息
//     * @return 数据库表集合
//     */
//    public List<GenTable> selectDbTableList(GenTable genTable) {
//        return genTableMapper.selectDbTableList(new HashMap());
//    }

    /**
     * 查询据库列表
     * 
     * @param tableNames 表名称组
     * @return 数据库表集合
     */
    public List<GenTable> selectDbTableListByNames(String[] tableNames)
    {
        return genTableMapper.selectDbTableListByNames(tableNames);
    }

    /**
     * 查询所有表信息
     * 
     * @return 表信息集合
     */
    public List<GenTable> selectGenTableAll()
    {
        return genTableMapper.selectGenTableAll();
    }

    /**
     * 修改业务
     * 
     * @param genTable 业务信息
     * @return 结果
     */
//    @Transactional
//    public void updateGenTable(GenTable genTable)
//    {
////        String options = JSON.toJSONString(genTable.getParams());
//        String options = JacksonUtil.toJsonString(genTable.getParams());
//        genTable.setOptions(options);
//        int row = genTableMapper.updateGenTable(genTable);
//        if (row > 0)
//        {
//            for (GenTableColumn cenTableColumn : genTable.getColumns())
//            {
//                genTableColumnMapper.updateGenTableColumn(cenTableColumn);
//            }
//        }
//    }

    public int updateGenTable(GenTable genTable) {
        String options = JacksonUtil.toJsonString(genTable.getParams());
        genTable.setOptions(options);
        return genTableMapper.updateGenTable(genTable);
    }

    /**
     * 删除业务对象
     * 
     * @param tableIds 需要删除的数据ID
     * @return 结果
     */
//    @Override
//    @Transactional
//    public void deleteGenTableByIds(Long[] tableIds)
//    {
//        genTableMapper.deleteGenTableByIds(tableIds);
//        genTableColumnMapper.deleteGenTableColumnByIds(tableIds);
//    }

    /**
     * 导入表结构
     * 
     * @param tableList 导入表列表
     */
//    @Transactional
    public void importGenTable(List<GenTable> tableList)
    {
//        String operName = SecurityUtils.getUsername();
        String operName = "auto";
        try
        {
            for (GenTable table : tableList)
            {
                String tableName = table.getTable_name();
                GenUtils.initTable(table, operName);
                int row = genTableMapper.insertGenTable(table);
                if (row > 0)
                {
                    // 保存列信息
                    List<GenTableColumn> genTableColumns = genTableColumnMapper.selectDbTableColumnsByName(tableName);
                    for (GenTableColumn column : genTableColumns)
                    {
                        GenUtils.initColumnField(column, table);
                        genTableColumnMapper.insertGenTableColumn(column);
                    }
                }
            }
        }
        catch (Exception e)
        {
            throw new RuntimeException("导入失败：" + e.getMessage());
        }
    }

    /**
     * 预览代码
     * 
     * @param tableId 表编号
     * @return 预览数据列表
     */
    public Map<String, String> previewCode(Long tableId) {
        Map<String, String> dataMap = new LinkedHashMap<>();
        // 查询表信息
        GenTable table = genTableMapper.selectGenTableById(tableId);
        // 设置主子表信息
        setSubTable(table);
        // 设置主键列信息
        setPkColumn(table);
        VelocityInitializer.initVelocity();

        VelocityContext context = VelocityUtils.prepareContext(table);

        // 获取模板列表
        List<String> templates = VelocityUtils.getTemplateList(table.getTpl_category());
        for (String template : templates){
            // 渲染模板
            StringWriter sw = new StringWriter();
            Template tpl = Velocity.getTemplate(template, Constants.UTF8);
            tpl.merge(context, sw);
            dataMap.put(template, sw.toString());
        }
        return dataMap;
    }

//    /**
//     * 生成代码（下载方式）
//     *
//     * @param tableName 表名称
//     * @return 数据
//     */
//    public byte[] downloadCode(String tableName)
//    {
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        ZipOutputStream zip = new ZipOutputStream(outputStream);
//        generatorCode(tableName, zip);
//        IOUtils.closeQuietly(zip);
//        return outputStream.toByteArray();
//    }
//
//    /**
//     * 生成代码（自定义路径）
//     *
//     * @param table_id 表id
//     */
//    public void generatorCode(Long table_id) {
//        // 查询表信息
//        GenTable table = genTableMapper.selectGenTableById(table_id);
//        // 设置主子表信息
//        setSubTable(table);
//        // 设置主键列信息
//        setPkColumn(table);
//
//        VelocityInitializer.initVelocity();
//
//        VelocityContext context = VelocityUtils.prepareContext(table);
//
//        // 获取模板列表
//        List<String> templates = VelocityUtils.getTemplateList(table.getTpl_category());
//        for (String template : templates)
//        {
//            if (!StringUtils.containsAny(template, "sql.ftl", "api.js.vm", "index.vue.vm", "index-tree.vue.vm"))
//            {
//                // 渲染模板
//                StringWriter sw = new StringWriter();
//                Template tpl = Velocity.getTemplate(template, Constants.UTF8);
//                tpl.merge(context, sw);
//                try {
//                    String path = getGenPath(table, template);
//                    FileUtils.writeStringToFile(new File(path), sw.toString(), CharsetKit.UTF_8);
//                }
//                catch (IOException e)
//                {
//                    throw new RuntimeException("渲染模板失败，表名：" + table.getTable_name());
//                }
//            }
//        }
//    }

    /**
     * 同步数据库
     * 
     * @param tableName 表名称
     */
//    @Transactional
    public void synchDb(String tableName)
    {
        GenTable table = genTableMapper.selectGenTableByName(tableName);
        List<GenTableColumn> tableColumns = table.getColumns();
        Map<String, GenTableColumn> tableColumnMap = tableColumns.stream().collect(Collectors.toMap(GenTableColumn::getColumn_name, Function.identity()));

        List<GenTableColumn> dbTableColumns = genTableColumnMapper.selectDbTableColumnsByName(tableName);
        if (StringUtils.isEmpty(dbTableColumns))
        {
            throw new RuntimeException("同步数据失败，原表结构不存在");
        }
        List<String> dbTableColumnNames = dbTableColumns.stream().map(GenTableColumn::getColumn_name).collect(Collectors.toList());

        dbTableColumns.forEach(column -> {
            GenUtils.initColumnField(column, table);
            if (tableColumnMap.containsKey(column.getColumn_name()))
            {
                GenTableColumn prevColumn = tableColumnMap.get(column.getColumn_name());
                column.setColumn_id(prevColumn.getColumn_id());
                if (column.is_list())
                {
                    // 如果是列表，继续保留查询方式/字典类型选项
                    column.setDict_type(prevColumn.getDict_type());
                    column.setQuery_type(prevColumn.getQuery_type());
                }
                if (StringUtils.isNotEmpty(prevColumn.getIs_required()) && !column.is_pk()
                        && (column.is_insert() || column.is_edit())
                        && ((column.isUsableColumn()) || (!column.isSuperColumn())))
                {
                    // 如果是(新增/修改&非主键/非忽略及父属性)，继续保留必填/显示类型选项
                    column.setIs_required(prevColumn.getIs_required());
                    column.setHtml_type(prevColumn.getHtml_type());
                }
                genTableColumnMapper.updateGenTableColumn(column);
            }
            else
            {
                genTableColumnMapper.insertGenTableColumn(column);
            }
        });

        List<GenTableColumn> delColumns = tableColumns.stream().filter(column -> !dbTableColumnNames.contains(column.getColumn_name())).collect(Collectors.toList());
        if (StringUtils.isNotEmpty(delColumns))
        {
            genTableColumnMapper.deleteGenTableColumns(delColumns);
        }
    }

//    /**
//     * 批量生成代码（下载方式）
//     *
//     * @param tableNames 表数组
//     * @return 数据
//     */
//    public byte[] downloadCode(String[] tableNames)
//    {
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        ZipOutputStream zip = new ZipOutputStream(outputStream);
//        for (String tableName : tableNames)
//        {
//            generatorCode(tableName, zip);
//        }
//        IOUtils.closeQuietly(zip);
//        return outputStream.toByteArray();
//    }

    /**
     * 查询表信息并生成代码
     */
    public void generatorCode(GenTable table, ZipOutputStream zip) {
//        // 查询表信息
//        GenTable table = genTableMapper.selectGenTableByName(tableName);
        // 设置主子表信息
        setSubTable(table);
        // 设置主键列信息
        setPkColumn(table);

        VelocityInitializer.initVelocity();

        VelocityContext context = VelocityUtils.prepareContext(table);

        // 获取模板列表
        List<String> templates = VelocityUtils.getTemplateList(table.getTpl_category());
        for (String template : templates) {
            // 渲染模板
            StringWriter sw = new StringWriter();
            Template tpl = Velocity.getTemplate(template, Constants.UTF8);
            tpl.merge(context, sw);
            try {
                // 添加到zip
                zip.putNextEntry(new ZipEntry(VelocityUtils.getFileName(template, table)));
                IOUtils.write(sw.toString(), zip, Constants.UTF8);
                IOUtils.closeQuietly(sw);
                zip.flush();
                zip.closeEntry();
            }  catch (IOException e) {
                log.error("渲染模板失败，表名：" + table.getTable_name(), e);
            }
        }
    }

    /**
     * 修改保存参数校验
     * 
     * @param genTable 业务信息
     */
    public void validateEdit(GenTable genTable) {
        if (TplCategoryEnum.TREE.value.equals(genTable.getTpl_category())) {
//            String options = JSON.toJSONString(genTable.getParams());
//            JSONObject paramsObj = JSON.parseObject(options);
            HashMap paramsObj = JacksonUtil.toObject(JacksonUtil.toJsonString(genTable.getParams()), HashMap.class);
//            if (StringUtils.isEmpty(paramsObj.getString(GenConstants.TREE_CODE)))
            if (StringUtils.isEmpty((String)paramsObj.get(GenConstants.TREE_CODE))) {
                throw new RuntimeException("树编码字段不能为空");
            }
//            else if (StringUtils.isEmpty(paramsObj.getString(GenConstants.TREE_PARENT_CODE)))
            else if (StringUtils.isEmpty((String)paramsObj.get(GenConstants.TREE_PARENT_CODE))) {
                throw new RuntimeException("树父编码字段不能为空");
            }
//            else if (StringUtils.isEmpty(paramsObj.getString(GenConstants.TREE_NAME)))
            else if (StringUtils.isEmpty((String)paramsObj.get(GenConstants.TREE_NAME))) {
                throw new RuntimeException("树名称字段不能为空");
            } else if (TplCategoryEnum.SUB.value.equals(genTable.getTpl_category())) {
                if (StringUtils.isEmpty(genTable.getSub_table_name())) {
                    throw new RuntimeException("关联子表的表名不能为空");
                }  else if (StringUtils.isEmpty(genTable.getSub_table_fk_name()))  {
                    throw new RuntimeException("子表关联的外键名不能为空");
                }
            }
        }
    }

    /**
     * 设置主键列信息
     * 
     * @param table 业务表信息
     */
    public void setPkColumn(GenTable table) {
        for (GenTableColumn column : table.getColumns())  {
            if (column.is_pk()) {
                column.setCap_java_field(StringUtils.convertToCamelCase(column.getColumn_name()));
                table.setPk_column(column);
                break;
            }
        }
        if (StringUtils.isNull(table.getPk_column())) {
            table.setPk_column(table.getColumns().get(0));
        }
        if (TplCategoryEnum.SUB.value.equals(table.getTpl_category())) {
            for (GenTableColumn column : table.getSub_table().getColumns()) {
                if (column.is_pk()) {
                    table.getSub_table().setPk_column(column);
                    break;
                }
            }
            if (StringUtils.isNull(table.getSub_table().getPk_column())) {
                table.getSub_table().setPk_column(table.getSub_table().getColumns().get(0));
            }
        }
    }

    /**
     * 设置主子表信息
     * 
     * @param table 业务表信息
     */
    public void setSubTable(GenTable table)
    {
        String subTableName = table.getSub_table_name();
        if (StringUtils.isNotEmpty(subTableName))
        {
            table.setSub_table(genTableMapper.selectGenTableByName(subTableName));
        }
    }

    /**
     * 设置代码生成其他选项值
     * 
     * @param genTable 设置后的生成对象
     */
    public void setTableFromOptions(GenTable genTable) {
//        JSONObject paramsObj = JSON.parseObject(genTable.getOptions());
        HashMap<String,String> paramsObj = JacksonUtil.toObject(genTable.getOptions(),HashMap.class);
        if (StringUtils.isNotNull(paramsObj))
        {
            String treeCode = paramsObj.get(GenConstants.TREE_CODE);
            String treeParentCode = paramsObj.get(GenConstants.TREE_PARENT_CODE);
            String treeName = paramsObj.get(GenConstants.TREE_NAME);
            String parentMenuId = paramsObj.get(GenConstants.PARENT_MENU_ID);
            String parentMenuName = paramsObj.get(GenConstants.PARENT_MENU_NAME);

            genTable.setTree_code(treeCode);
            genTable.setTree_parent_code(treeParentCode);
            genTable.setTree_name(treeName);
            genTable.setParent_menu_id(parentMenuId);
            genTable.setParent_menu_name(parentMenuName);
        }
    }

    /**
     * 获取代码生成地址
     * 
     * @param table 业务表信息
     * @param template 模板文件路径
     * @return 生成地址
     */
    public static String getGenPath(GenTable table, String template)
    {
        String genPath = table.getGen_path();
        if (StringUtils.equals(genPath, "/"))
        {
            return System.getProperty("user.dir") + File.separator + "src" + File.separator + VelocityUtils.getFileName(template, table);
        }
        return genPath + File.separator + VelocityUtils.getFileName(template, table);
    }
}
