package com.mee.generator.service.impl;

import com.mee.generator.core.model.MeeResult;
import com.mee.generator.entity.*;
import com.mee.generator.enums.CamelCaseEnum;
import com.mee.generator.mapper.Gen2TableMapper;
import com.mee.generator.mapper.Gen2ColumnMapper;
import com.mee.generator.util.*;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringWriter;
import java.util.*;

/**
 * 业务 服务层实现
 * 
 * @author shadow
 */
@Service
public class GenTable2ServiceImpl {
    private static final Logger LOG = LoggerFactory.getLogger(GenTable2ServiceImpl.class);

    @Autowired
    private Gen2TableMapper genTable2Mapper;

    @Autowired
    private Gen2ColumnMapper genTableColumn2Mapper;


//    /**
//     * 查询据库列表
//     *
//     * @param tableNames 表名称组
//     * @return 数据库表集合
//     */
//    public List<Gen2Table> selectDbTableListByNames(String[] tableNames) {
//        return genTable2Mapper.findTableByNames(tableNames);
//    }

//    /**
//     * 查询所有表信息
//     *
//     * @return 表信息集合
//     */
//    public List<GenTable> selectGenTableAll()
//    {
//        return genTable2Mapper.selectGenTableAll();
//    }

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

    public MeeResult updateGenTable(Gen2Table genTable) {
//        String options = JacksonUtil.toJsonString(genTable.getParams());
//        genTable.setOptions(options);

        if( null==genTable || null==genTable.getId() ){
            return ResultBuild.fail("必要参数不可为空！");
        }

        final String id = genTable.getId();
        final CamelCaseEnum camel_case = genTable.getCamel_case();
        // 是否驼峰字段更新则更新
        Gen2Table gen2Table = genTable2Mapper.findById(id);
        if( null==camel_case || null==gen2Table || null==gen2Table.getCamel_case()  ){
            return ResultBuild.fail("记录为空！");
        }
        if( !camel_case.equals(gen2Table.getCamel_case()) ){
            List<Gen2Column> gen2Columns = genTableColumn2Mapper.findByTableId(id);
            for( Gen2Column item:gen2Columns ){
                final String column_name = item.getColumn_name().toLowerCase(Locale.ROOT);
                item.setJava_field(CamelCaseEnum.Y.equals(camel_case)?StringUtils.toCamelCase(column_name):column_name);
                genTableColumn2Mapper.updateGenTableColumn(item);
                // genTableColumn2Mapper.update(item);
            }
        }

        genTable.setUpdate_by("auto");
        genTable.setUpdate_time(DateUtil.now());
        int update_count = genTable2Mapper.update(genTable);
        return ResultBuild.build(update_count);
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
     * 预览代码
     * 
     * @param table_id 表编号
     * @return 预览数据列表
     */
    public MeeResult previewCode(String table_id) throws IOException, TemplateException {
        // 查询表信息
        LinkedHashMap table = genTable2Mapper.findByIdToMap(table_id);
        List<Map> colums = genTableColumn2Mapper.findByTableIdToMap(table_id);
        if(  null==table || colums.isEmpty()){
            LOG.error("表或列信息为空:{}",table_id);
            return ResultBuild.fail("表或列信息为空:"+table_id);
        }
        // 表名处理
        this.genGetterAndSetterTable(table);
        // 分页字段
        this.genPageField(table);
        // 模板内数据
        this.genGetterAndSetterField(colums);
        // 实体类包及字段注解
        this.genEntityPackage(table,colums);
//        // 实体类toString方法
//        this.genToString(table,colums);

        // 数据库字段处理(关键字及多db支持处理)
        this.genMultipleDbAndField(table,colums);
        // mapper.java内主键
        this.genMapperKeyInfo(table,colums);

        table.put("columns",colums);
        table.put("datetime",DateUtil.now().format(DateUtil.FORMAT_DAY_TIME));
        // 获取模板列表
        List<String> templates = FreeMarkerUtils.getTemplateList2();
        // 初始化freemarker
        Configuration cfg = FreeMarkerUtils.initFreemarker();
        // 逐个渲染
        Map<String, String> data = new LinkedHashMap<>(templates.size());
        for (String template : templates){
            StringWriter sw = new StringWriter();
            freemarker.template.Template process = cfg.getTemplate(template);
            process.process(table,sw);
            data.put(template, sw.toString());
        }
        return ResultBuild.build(data);
    }

    /**
     * 数据库关键字处理及多数据库支持
     * @param table 表信息
     * @param colums 列信息
     */
    private void genMultipleDbAndField(LinkedHashMap table, List<Map> colums) {
        // database_id拆成数组，如果为空则赋值为current_db
        String database_id=(String)table.get("database_id");
        if( null==database_id || "".equals(database_id.trim()) ){
            table.put("database_id",(database_id=(String)table.get("current_db")) );
        }
        String[] database_ids = database_id.split(",");
        table.put("database_ids",database_ids);

        // 循环columns,根据不同数据库厂商生成db字段同时处理好关键字问题
        for( String db:database_ids ){
            // 获取关键字列表
            List<String> db_keywords = this.getDBKeywords(db);
            for(Map c:colums){
                // 根据关键字生成最终db字段(for mybatis xml)
                c.put("column_name_"+db,this.parseToDBField((String)c.get("column_name"),db,db_keywords));
            }
        }
    }

    /**
     * 处理关键字并生成适合对应数据库厂商之字段
     * @param column_name
     * @param db
     * @param db_keywords
     * @return
     */
    private Object parseToDBField(String column_name, String db, List<String> db_keywords) {
        boolean is_keywords = db_keywords.contains(column_name);
        switch (db){
            case "mysql":
                return is_keywords?("`"+column_name+"`"):column_name.toUpperCase();
            case "postgresql":
                return is_keywords?("\""+column_name+"\""):column_name.toUpperCase();
            case "oracle":
                return is_keywords?("\""+column_name.toUpperCase()+"\""):column_name.toUpperCase();
            case "sqlserver":
                return is_keywords?("["+column_name+"]"):column_name.toUpperCase();
            default:
                throw new RuntimeException("未知数据库厂商 "+db);
        }
    }

    /**
     * 获取各个数据库厂商关键字
     * @param db_id 数据库类型
     * @return
     */
    private List<String> getDBKeywords(String db_id){
        // 关键字默认定义都是小写的，最终会根据厂商以及关键字生成对应符合的数据库字段
        switch (db_id){
            case "mysql":
                return Arrays.asList(FreeMarkerUtils.MYSQL_KEYWORDS);
            case "postgresql":
                return Arrays.asList(FreeMarkerUtils.POSTGRESQL_KEYWORDS);
            case "oracle":
                return Arrays.asList(FreeMarkerUtils.ORACLE_KEYWORDS);
            case "sqlserver":
                return Arrays.asList(FreeMarkerUtils.SQLSERVER_KEYWORDS);
            default:
                throw new RuntimeException("未能找到定义的关键字for "+db_id);
        }
    }

    /**
     * 根据配置生成表名及entity别名
     * @param table 表
     */
    private void genGetterAndSetterTable(LinkedHashMap table) {
        String camel_case = (String)table.get("camel_case");
        // 如果是驼峰就取class_name并且第一个单词转小写
        // 如果非驼峰就直接取 table_name
        if(CamelCaseEnum.Y.value.equals(camel_case) ){
            String java_field = (String)table.get("class_name");
            String prefix = java_field.substring(0, 1).toLowerCase(Locale.ROOT);
            if( java_field.length()==1 ){
                table.put("gs_table",prefix);
            }else{
                table.put("gs_table",prefix+java_field.substring(1));
            }
        }else{
            table.put("gs_table",((String)table.get("table_name")).toLowerCase());
        }
//        table.put("table_name_lower",((String)table.get("table_name")).toLowerCase());


        // entity别名 SysUser => sysUser;
        String class_name = (String) table.get("class_name");
        String prefix = class_name.substring(0, 1).toLowerCase(Locale.ROOT);
        if( class_name.length()==1 ){
            table.put("class_name_camel",prefix);
        }else{
            table.put("class_name_camel",prefix+class_name.substring(1));
        }
    }

    /**
     * 生成分页字段
     * @param table  表
     */
    private void genPageField(LinkedHashMap table){
        String camel_case = (String)table.get("camel_case");
        Map<String,String> page_info = new HashMap<>(2,1);
        if( "1".equals(camel_case) ){
            page_info.put("page_no_field","pageNo");
            page_info.put("page_size_field","pageSize");
        }else{
            page_info.put("page_no_field","page_no");
            page_info.put("page_size_field","page_size");
        }
        table.put("page_info",page_info);
    }

    /**
     * 表主键信息
     * @param table  表
     * @param colums 字段
     */
    private void genMapperKeyInfo(LinkedHashMap table, List<Map> colums) {
        Map<String,Object> key_info = new HashMap<>(4,1);
        for( Map field:colums ){
            if( "1".equals(field.get("is_pk")) ){
                key_info.put("java_field",field.get("java_field"));
                key_info.put("java_type",field.get("java_type"));
                key_info.put("java_import",FreeMarkerUtils.JAVA_TYPE.get(field.get("java_type")));
                key_info.put("gs_field",field.get("gs_field"));
                key_info.put("column_name",field.get("column_name"));
                key_info.put("column_comment",field.get("column_comment"));
                // 数据库xml字段
                String[] database_ids = (String[]) table.get("database_ids");
                for( String db_id:database_ids ){
                    key_info.put("column_name_"+db_id,field.get("column_name_"+db_id));
                }
                table.put("mapper_key_info",key_info);
                return;
            }
        }
        if( !key_info.containsKey("java_field") ){
            key_info.put("java_field","id");
            key_info.put("java_type","String");
            // column_name first character to upper
            key_info.put("gs_field","Id");
            key_info.put("column_name","id");
            key_info.put("column_comment","主键");
            // 数据库xml字段
            String[] database_ids = (String[]) table.get("database_ids");
            for( String db_id:database_ids ){
                key_info.put("column_name_"+db_id,"id");
            }
            table.put("mapper_key_info",key_info);
        }
    }

//    /**
//     * 生成 toString
//     * @param table 表信息
//     * @param colums 字段信息
//     */
//    private void genToString(LinkedHashMap table, List<Map> colums) {
//        StringJoiner sj = new StringJoiner("\t");
//        for(Map field:colums ){
//            sj.add("\""+field.get("java_field")+":\"+this."+field.get("java_field")+"+\",\"+\n");
//        }
//        table.put("to_string","\""+table.get("class_name")+"::{\"+\n\t"+sj+"\t\"}"+"\"");
//    }

    /**
     * Entity 导入包字段注解
     * @param table .
     * @param columns .
     */
    private void genEntityPackage(Map table,List<Map> columns){
        Set<String> entity_package = new LinkedHashSet<>(columns.size());
        entity_package.add(FreeMarkerUtils.JAVA_TYPE.get("String"));
        entity_package.add(FreeMarkerUtils.JAVA_TYPE.get("Integer"));
        entity_package.add(FreeMarkerUtils.JAVA_TYPE.get("Serializable"));
        for( Map field:columns ){
            String java_type = (String)field.get("java_type");
            entity_package.add(FreeMarkerUtils.JAVA_TYPE.get(java_type));
            if( "LocalDateTime".equals(java_type) ){
                String[] field_annotation={
                    "@JsonSerialize(using = LocalDateTimeSerializer.class)",
                    "@JsonFormat(shape=JsonFormat.Shape.STRING,pattern=\"yyyy-MM-dd HH:mm:ss\")",
                    "@DateTimeFormat(pattern=\"yyyy-MM-dd HH:mm:ss\")"
                };
                field.put("field_annotation",field_annotation);
                entity_package.add(FreeMarkerUtils.JAVA_TYPE.get("JsonSerialize"));
                entity_package.add(FreeMarkerUtils.JAVA_TYPE.get("LocalDateTimeSerializer"));
                entity_package.add(FreeMarkerUtils.JAVA_TYPE.get("JsonFormat"));
                entity_package.add(FreeMarkerUtils.JAVA_TYPE.get("DateTimeFormat"));
            }else if( "LocalDate".equals(java_type) ){
                String[] field_annotation = {
                    "@JsonSerialize(using = LocalDateSerializer.class)",
                    "@JsonFormat(shape=JsonFormat.Shape.STRING,pattern=\"yyyy-MM-dd\")",
                    "@DateTimeFormat(pattern=\"yyyy-MM-dd\")",
                };
                field.put("field_annotation",field_annotation);
                entity_package.add(FreeMarkerUtils.JAVA_TYPE.get("JsonSerialize"));
                entity_package.add(FreeMarkerUtils.JAVA_TYPE.get("LocalDateTimeSerializer"));
                entity_package.add(FreeMarkerUtils.JAVA_TYPE.get("JsonFormat"));
                entity_package.add(FreeMarkerUtils.JAVA_TYPE.get("DateTimeFormat"));
            }
        }
        table.put("entity_package",entity_package);
    }

    /**
     * 生成Getter以及Setter方法名
     *  user_name => User_name
     *  userName  => UserName
     */
    private void genGetterAndSetterField(List<Map> colums){
        for(Map field:colums){
            String java_field = (String)field.get("java_field");
            String prefix = java_field.substring(0, 1).toUpperCase(Locale.ROOT);
            if( java_field.length()==1 ){
                field.put("gs_field",prefix);
            }else{
                field.put("gs_field",prefix+java_field.substring(1));
            }
        }
    }


//    /**
//     * 查询表信息并生成代码
//     */
//    public void generatorCode(GenTable2 table, ZipOutputStream zip) {
//        Map<String, Object> stringObjectMap = this.previewCode(table.getId());
//        // 获取模板列表
//        List<String> templates = VelocityUtils.getTemplateList(table.getTpl_category());
//        for (String template : templates) {
//            // 渲染模板
//            StringWriter sw = new StringWriter();
//            Template tpl = Velocity.getTemplate(template, Constants.UTF8);
//            tpl.merge(context, sw);
//            try {
//                // 添加到zip
//                zip.putNextEntry(new ZipEntry(VelocityUtils.getFileName(template, table)));
//                IOUtils.write(sw.toString(), zip, Constants.UTF8);
//                IOUtils.closeQuietly(sw);
//                zip.flush();
//                zip.closeEntry();
//            }  catch (IOException e) {
//                LOG.error("渲染模板失败，表名：" + table.getTable_name(), e);
//            }
//        }
//    }

//    /**
//     * 修改保存参数校验
//     *
//     * @param genTable 业务信息
//     */
//    public void validateEdit(GenTable genTable) {
//        if (TplCategoryEnum.TREE.value.equals(genTable.getTpl_category())) {
////            String options = JSON.toJSONString(genTable.getParams());
////            JSONObject paramsObj = JSON.parseObject(options);
//            HashMap paramsObj = JacksonUtil.toObject(JacksonUtil.toJsonString(genTable.getParams()), HashMap.class);
////            if (StringUtils.isEmpty(paramsObj.getString(GenConstants.TREE_CODE)))
//            if (StringUtils.isEmpty((String)paramsObj.get(GenConstants.TREE_CODE))) {
//                throw new RuntimeException("树编码字段不能为空");
//            }
////            else if (StringUtils.isEmpty(paramsObj.getString(GenConstants.TREE_PARENT_CODE)))
//            else if (StringUtils.isEmpty((String)paramsObj.get(GenConstants.TREE_PARENT_CODE))) {
//                throw new RuntimeException("树父编码字段不能为空");
//            }
////            else if (StringUtils.isEmpty(paramsObj.getString(GenConstants.TREE_NAME)))
//            else if (StringUtils.isEmpty((String)paramsObj.get(GenConstants.TREE_NAME))) {
//                throw new RuntimeException("树名称字段不能为空");
//            } else if (TplCategoryEnum.SUB.value.equals(genTable.getTpl_category())) {
//                if (StringUtils.isEmpty(genTable.getSub_table_name())) {
//                    throw new RuntimeException("关联子表的表名不能为空");
//                }  else if (StringUtils.isEmpty(genTable.getSub_table_fk_name()))  {
//                    throw new RuntimeException("子表关联的外键名不能为空");
//                }
//            }
//        }
//    }

//    /**
//     * 设置主键列信息
//     *
//     * @param table 业务表信息
//     */
//    public void setPkColumn(GenTable table) {
//        for (GenTableColumn column : table.getColumns())  {
//            if (column.is_pk()) {
//                column.setCap_java_field(StringUtils.convertToCamelCase(column.getColumn_name()));
//                table.setPk_column(column);
//                break;
//            }
//        }
//        if (StringUtils.isNull(table.getPk_column())) {
//            table.setPk_column(table.getColumns().get(0));
//        }
//        if (TplCategoryEnum.SUB.value.equals(table.getTpl_category())) {
//            for (GenTableColumn column : table.getSub_table().getColumns()) {
//                if (column.is_pk()) {
//                    table.getSub_table().setPk_column(column);
//                    break;
//                }
//            }
//            if (StringUtils.isNull(table.getSub_table().getPk_column())) {
//                table.getSub_table().setPk_column(table.getSub_table().getColumns().get(0));
//            }
//        }
//    }

//    /**
//     * 设置主子表信息
//     *
//     * @param table 业务表信息
//     */
//    public void setSubTable(GenTable table) {
//        String subTableName = table.getSub_table_name();
//        if (StringUtils.isNotEmpty(subTableName)){
//            table.setSub_table(genTable2Mapper.findTableByNames(subTableName));
//        }
//    }

//    /**
//     * 设置代码生成其他选项值
//     *
//     * @param genTable 设置后的生成对象
//     */
//    public void setTableFromOptions(GenTable genTable) {
////        JSONObject paramsObj = JSON.parseObject(genTable.getOptions());
//        HashMap<String,String> paramsObj = JacksonUtil.toObject(genTable.getOptions(),HashMap.class);
//        if (StringUtils.isNotNull(paramsObj))
//        {
//            String treeCode = paramsObj.get(GenConstants.TREE_CODE);
//            String treeParentCode = paramsObj.get(GenConstants.TREE_PARENT_CODE);
//            String treeName = paramsObj.get(GenConstants.TREE_NAME);
//            String parentMenuId = paramsObj.get(GenConstants.PARENT_MENU_ID);
//            String parentMenuName = paramsObj.get(GenConstants.PARENT_MENU_NAME);
//
//            genTable.setTree_code(treeCode);
//            genTable.setTree_parent_code(treeParentCode);
//            genTable.setTree_name(treeName);
//            genTable.setParent_menu_id(parentMenuId);
//            genTable.setParent_menu_name(parentMenuName);
//        }
//    }
//
//    /**
//     * 获取代码生成地址
//     *
//     * @param table 业务表信息
//     * @param template 模板文件路径
//     * @return 生成地址
//     */
//    public static String getGenPath(GenTable table, String template)
//    {
//        String genPath = table.getGen_path();
//        if (StringUtils.equals(genPath, "/"))
//        {
//            return System.getProperty("user.dir") + File.separator + "src" + File.separator + VelocityUtils.getFileName(template, table);
//        }
//        return genPath + File.separator + VelocityUtils.getFileName(template, table);
//    }

}
