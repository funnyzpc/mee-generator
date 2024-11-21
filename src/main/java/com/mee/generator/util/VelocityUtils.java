package com.mee.generator.util;

import java.util.*;

import com.mee.generator.common.constant.GenConstants;
import com.mee.generator.entity.GenTable;
import com.mee.generator.entity.GenTableColumn;
import com.mee.generator.enums.HtmlTypeEnum;
import com.mee.generator.enums.JavaTypeEnum;
import com.mee.generator.enums.TplCategoryEnum;
import org.apache.velocity.VelocityContext;

/**
 * 模板处理工具类
 * 
 * @author mee
 */
public class VelocityUtils {
    /** 项目空间路径 */
    private static final String PROJECT_PATH = "main/java";

    /** mybatis空间路径 */
    private static final String MYBATIS_PATH = "main/resources/mapper";

    /** 默认上级菜单，系统工具 */
    private static final String DEFAULT_PARENT_MENU_ID = "3";

    /**
     * 设置模板变量信息
     *
     * @return 模板列表
     */
    public static VelocityContext prepareContext(GenTable genTable) {
        String moduleName = genTable.getModule_name();
        String businessName = genTable.getBusiness_name();
        String packageName = genTable.getPackage_name();
        String tplCategory = genTable.getTpl_category();
        String functionName = genTable.getFunction_name();

        VelocityContext velocityContext = new VelocityContext();
        velocityContext.put("tplCategory", genTable.getTpl_category());
        velocityContext.put("tableName", genTable.getTable_name());
        velocityContext.put("functionName", StringUtils.isNotEmpty(functionName) ? functionName : "【请填写功能名称】");
        velocityContext.put("ClassName", genTable.getClass_name());
        velocityContext.put("className", StringUtils.uncapitalize(genTable.getClass_name()));
        velocityContext.put("moduleName", genTable.getModule_name());
        velocityContext.put("BusinessName", StringUtils.capitalize(genTable.getBusiness_name()));
        velocityContext.put("businessName", genTable.getBusiness_name());
        velocityContext.put("basePackage", getPackagePrefix(packageName));
        velocityContext.put("packageName", packageName);
        velocityContext.put("author", genTable.getFunction_author());
        velocityContext.put("version", genTable.getFunction_version());
//        velocityContext.put("datetime", DateUtils.getDate());
//        velocityContext.put("datetime", DateUtil.nowDay().format(DateUtil.FORMAT_DAY));
        velocityContext.put("datetime", DateUtil.now().format(DateUtil.FORMAT_DAY_TIME));
        velocityContext.put("pkColumn", genTable.getPk_column());
        velocityContext.put("importList", getImportList(genTable));
        velocityContext.put("permissionPrefix", getPermissionPrefix(moduleName, businessName));
        velocityContext.put("columns", genTable.getColumns());
        velocityContext.put("table", genTable);
        velocityContext.put("dicts", getDicts(genTable));
        setMenuVelocityContext(velocityContext, genTable);
        if (TplCategoryEnum.TREE.value.equals(tplCategory)){
            setTreeVelocityContext(velocityContext, genTable);
        }
        if (TplCategoryEnum.SUB.value.equals(tplCategory)){
            setSubVelocityContext(velocityContext, genTable);
        }
        return velocityContext;
    }

    public static void setMenuVelocityContext(VelocityContext context, GenTable genTable) {
        String options = genTable.getOptions();
        if(null==options || "".equals(options)){
            context.put("parentMenuId", "");
            return;
        }
//        JSONObject paramsObj = JSON.parseObject(options);
        HashMap paramsObj = JacksonUtil.toObject(options, HashMap.class);
        String parentMenuId = getParentMenuId(paramsObj);
        context.put("parentMenuId", parentMenuId);
    }

    public static void setTreeVelocityContext(VelocityContext context, GenTable genTable) {
        String options = genTable.getOptions();
        HashMap paramsObj = JacksonUtil.toObject(options,HashMap.class);
        String treeCode = getTreecode(paramsObj);
        String treeParentCode = getTreeParentCode(paramsObj);
        String treeName = getTreeName(paramsObj);

        context.put("treeCode", treeCode);
        context.put("treeParentCode", treeParentCode);
        context.put("treeName", treeName);
        context.put("expandColumn", getExpandColumn(genTable));
        if (paramsObj.containsKey(GenConstants.TREE_PARENT_CODE)){
            context.put("tree_parent_code", paramsObj.get(GenConstants.TREE_PARENT_CODE));
        }
        if (paramsObj.containsKey(GenConstants.TREE_NAME)){
            context.put("tree_name", paramsObj.get(GenConstants.TREE_NAME));
        }
    }

    public static void setSubVelocityContext(VelocityContext context, GenTable genTable){
        GenTable subTable = genTable.getSub_table();
        String subTableName = genTable.getSub_table_name();
        String subTableFkName = genTable.getSub_table_fk_name();
        String subClassName = genTable.getSub_table().getClass_name();
        String subTableFkClassName = StringUtils.convertToCamelCase(subTableFkName);

        context.put("subTable", subTable);
        context.put("subTableName", subTableName);
        context.put("subTableFkName", subTableFkName);
        context.put("subTableFkClassName", subTableFkClassName);
        context.put("subTableFkclassName", StringUtils.uncapitalize(subTableFkClassName));
        context.put("subClassName", subClassName);
        context.put("subclassName", StringUtils.uncapitalize(subClassName));
        context.put("subImportList", getImportList(genTable.getSub_table()));
    }

    /**
     * 获取模板信息
     *
     * @return 模板列表
     */
    public static List<String> getTemplateList(String tplCategory){
        List<String> templates = new ArrayList<String>();
        templates.add("vm/java/domain.java.vm");
        templates.add("vm/java/mapper.java.vm");
        templates.add("vm/java/service.java.vm");
        templates.add("vm/java/serviceImpl.java.vm");
        templates.add("vm/java/controller.java.vm");
        templates.add("vm/xml/mapper.xml.vm");
        templates.add("vm/sql/sql.vm");
        templates.add("vm/js/api.js.vm");
        if (TplCategoryEnum.CRUD.value.equals(tplCategory))  {
            templates.add("vm/vue/index.vue.vm");
        } else if (TplCategoryEnum.TREE.value.equals(tplCategory)) {
            templates.add("vm/vue/index-tree.vue.vm");
        }else if (TplCategoryEnum.SUB.value.equals(tplCategory)) {
            templates.add("vm/vue/index.vue.vm");
            templates.add("vm/java/sub-domain.java.vm");
        }
        return templates;
    }

    /**
     * 获取文件名
     */
    public static String getFileName(String template, GenTable genTable) {
        // 文件名称
        String fileName = "";
        // 包路径
        String packageName = genTable.getPackage_name();
        // 模块名
        String moduleName = genTable.getModule_name();
        // 大写类名
        String className = genTable.getClass_name();
        // 业务名称
        String businessName = genTable.getBusiness_name();

        String javaPath = PROJECT_PATH + "/" + StringUtils.replace(packageName, ".", "/");
        String mybatisPath = MYBATIS_PATH + "/" + moduleName;
        String vuePath = "vue";

        if (template.contains("domain.java.vm")) {
            fileName = StringUtils.format("{}/domain/{}.java", javaPath, className);
        }
        if (template.contains("sub-domain.java.vm") && StringUtils.equals(TplCategoryEnum.SUB.value, genTable.getTpl_category())) {
            fileName = StringUtils.format("{}/domain/{}.java", javaPath, genTable.getSub_table().getClass_name());
        } else if (template.contains("mapper.java.vm")) {
            fileName = StringUtils.format("{}/mapper/{}Mapper.java", javaPath, className);
        } else if (template.contains("service.java.vm")){
            fileName = StringUtils.format("{}/service/I{}Service.java", javaPath, className);
        } else if (template.contains("serviceImpl.java.vm")){
            fileName = StringUtils.format("{}/service/impl/{}ServiceImpl.java", javaPath, className);
        }else if (template.contains("controller.java.vm")){
            fileName = StringUtils.format("{}/controller/{}Controller.java", javaPath, className);
        } else if (template.contains("mapper.xml.vm")) {
            fileName = StringUtils.format("{}/{}Mapper.xml", mybatisPath, className);
        }else if (template.contains("sql.ftl")){
            fileName = businessName + "Menu.sql";
        }else if (template.contains("api.js.vm")){
            fileName = StringUtils.format("{}/api/{}/{}.js", vuePath, moduleName, businessName);
        }else if (template.contains("index.vue.vm")){
            fileName = StringUtils.format("{}/views/{}/{}/index.vue", vuePath, moduleName, businessName);
        }else if (template.contains("index-tree.vue.vm")){
            fileName = StringUtils.format("{}/views/{}/{}/index.vue", vuePath, moduleName, businessName);
        }
        return fileName;
    }

    /**
     * 获取包前缀
     *
     * @param packageName 包名称
     * @return 包前缀名称
     */
    public static String getPackagePrefix(String packageName) {
        int lastIndex = packageName.lastIndexOf(".");
        return StringUtils.substring(packageName, 0, lastIndex);
    }

    /**
     * 根据列类型获取导入包
     * 
     * @param genTable 业务表对象
     * @return 返回需要导入的包列表
     */
    public static HashSet<String> getImportList(GenTable genTable) {
        List<GenTableColumn> columns = genTable.getColumns();
        GenTable subGenTable = genTable.getSub_table();
        HashSet<String> importList = new HashSet<String>();
        if (StringUtils.isNotNull(subGenTable)) {
            importList.add("java.util.List");
        }
        for (GenTableColumn column : columns) {
            if (!column.isSuperColumn() && JavaTypeEnum.DATE.value.equals(column.getJava_type())) {
                // importList.add("java.util.Date");
                // importList.add("com.fasterxml.jackson.annotation.JsonFormat");
                // 日期事件类依赖
                importList.add("java.time.LocalDateTime");
                importList.add("com.fasterxml.jackson.databind.annotation.JsonSerialize");
                importList.add("com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer");
                importList.add("org.springframework.format.annotation.DateTimeFormat");
                importList.add("com.fasterxml.jackson.annotation.JsonFormat");
            } else if (!column.isSuperColumn() && JavaTypeEnum.BIGDECIMAL.value.equals(column.getJava_type())) {
                importList.add("java.math.BigDecimal");
            }
        }
        return importList;
    }

    /**
     * 根据列类型获取字典组
     * 
     * @param genTable 业务表对象
     * @return 返回字典组
     */
    public static String getDicts(GenTable genTable) {
        List<GenTableColumn> columns = genTable.getColumns();
        Set<String> dicts = new HashSet<String>();
        addDicts(dicts, columns);
        if (StringUtils.isNotNull(genTable.getSub_table())) {
            List<GenTableColumn> subColumns = genTable.getSub_table().getColumns();
            addDicts(dicts, subColumns);
        }
        return StringUtils.join(dicts, ", ");
    }

    /**
     * 添加字典列表
     * 
     * @param dicts 字典列表
     * @param columns 列集合
     */
    public static void addDicts(Set<String> dicts, List<GenTableColumn> columns) {
        for (GenTableColumn column : columns) {
            if (!column.isSuperColumn() && StringUtils.isNotEmpty(column.getDict_type()) && StringUtils.equalsAny(
                    column.getHtml_type(),
                    new String[] { HtmlTypeEnum.SELECT.value, HtmlTypeEnum.RADIO.value, HtmlTypeEnum.CHECKBOX.value }))
            {
                dicts.add("'" + column.getDict_type() + "'");
            }
        }
    }

    /**
     * 获取权限前缀
     *
     * @param moduleName 模块名称
     * @param businessName 业务名称
     * @return 返回权限前缀
     */
    public static String getPermissionPrefix(String moduleName, String businessName) {
        return StringUtils.format("{}:{}", moduleName, businessName);
    }

    /**
     * 获取上级菜单ID字段
     *
     * @param paramsObj 生成其他选项
     * @return 上级菜单ID字段
     */
    public static String getParentMenuId(Map paramsObj) {
        if (StringUtils.isNotEmpty(paramsObj) && paramsObj.containsKey(GenConstants.PARENT_MENU_ID)
                && StringUtils.isNotEmpty((String)paramsObj.get(GenConstants.PARENT_MENU_ID)))
        {
            return (String)paramsObj.get(GenConstants.PARENT_MENU_ID);
        }
        return DEFAULT_PARENT_MENU_ID;
    }

    /**
     * 获取树编码
     *
     * @param paramsObj 生成其他选项
     * @return 树编码
     */
    public static String getTreecode(Map paramsObj)
    {
        if (paramsObj.containsKey(GenConstants.TREE_CODE))
        {
            return StringUtils.toCamelCase((String)paramsObj.get(GenConstants.TREE_CODE));
        }
        return StringUtils.EMPTY;
    }

    /**
     * 获取树父编码
     *
     * @param paramsObj 生成其他选项
     * @return 树父编码
     */
    public static String getTreeParentCode(Map paramsObj){
        if (paramsObj.containsKey(GenConstants.TREE_PARENT_CODE)){
            return StringUtils.toCamelCase((String)paramsObj.get(GenConstants.TREE_PARENT_CODE));
        }
        return StringUtils.EMPTY;
    }

    /**
     * 获取树名称
     *
     * @param paramsObj 生成其他选项
     * @return 树名称
     */
    public static String getTreeName(Map paramsObj){
        if (paramsObj.containsKey(GenConstants.TREE_NAME)){
            return StringUtils.toCamelCase((String)paramsObj.get(GenConstants.TREE_NAME));
        }
        return StringUtils.EMPTY;
    }

    /**
     * 获取需要在哪一列上面显示展开按钮
     *
     * @param genTable 业务表对象
     * @return 展开按钮列序号
     */
    public static int getExpandColumn(GenTable genTable){
        String options = genTable.getOptions();
//        JSONObject paramsObj = JSON.parseObject(options);
        HashMap paramsObj = JacksonUtil.toObject(options,HashMap.class);
        String treeName = (String)paramsObj.get(GenConstants.TREE_NAME);
        int num = 0;
        for (GenTableColumn column : genTable.getColumns()){
            if (column.is_list()){
                num++;
                String columnName = column.getColumn_name();
                if (columnName.equals(treeName)){
                    break;
                }
            }
        }
        return num;
    }
}
