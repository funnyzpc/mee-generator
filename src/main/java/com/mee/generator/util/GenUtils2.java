package com.mee.generator.util;

import com.mee.generator.entity.Gen2Column;
import com.mee.generator.enums.JavaTypeEnum;

/**
 * 代码生成器 工具类
 * 
 * @author mee
 */
public class GenUtils2 {

//    /**
//     * 初始化表信息
//     */
//    public static void initTable(GenTable genTable, String operName){
//        genTable.setClass_name(convertClassName(genTable.getTable_name()));
//        genTable.setPackage_name(GenConfig.getPackageName());
//        genTable.setModule_name(getModuleName(GenConfig.getPackageName()));
//        genTable.setBusiness_name(getBusinessName(genTable.getTable_name()));
//        genTable.setFunction_name(replaceText(genTable.getTable_comment()));
//        genTable.setFunction_author(GenConfig.getAuthor());
//        genTable.setCreate_by(operName);
//    }

//    /**
//     * 初始化列属性字段
//     */
//    public static void initColumnField(GenTableColumn column, GenTable table) {
//        String dataType = getDbType(column.getColumn_type());
//        String columnName = column.getColumn_name();
//        column.setTable_id(table.getTable_id());
//        column.setCreate_by(table.getCreate_by());
//        // 设置java字段名
//        column.setJava_field(StringUtils.toCamelCase(columnName));
//        // 设置默认类型
//        column.setJava_type(JavaTypeEnum.STRING.value);
//        column.setQuery_type(GenConstants.QUERY_EQ);
//
//        if (arraysContains(GenConstants.COLUMNTYPE_STR, dataType) || arraysContains(GenConstants.COLUMNTYPE_TEXT, dataType))
//        {
//            // 文本类型 字符串长度超过500设置为文本域
//            Integer columnLength = getColumnLength(column.getColumn_type());
//            String htmlType = columnLength >= 500 || arraysContains(GenConstants.COLUMNTYPE_TEXT, dataType) ?  HtmlTypeEnum.TEXTAREA.value : HtmlTypeEnum.INPUT.value;
//            column.setHtml_type(htmlType);
//        }
//        else if (arraysContains(GenConstants.COLUMNTYPE_TIME, dataType))
//        {
//            // 日期时间类型
//            column.setJava_type(JavaTypeEnum.DATE.value);
//            column.setHtml_type(HtmlTypeEnum.DATETIME.value);
//        }
//        else if (arraysContains(GenConstants.COLUMNTYPE_NUMBER, dataType))
//        {
//            // 数字类型
//            column.setHtml_type(HtmlTypeEnum.INPUT.value);
//
//            // 如果是浮点型 统一用BigDecimal
//            String[] str = StringUtils.split(StringUtils.substringBetween(column.getColumn_type(), "(", ")"), ",");
//            if (str != null && str.length == 2 && Integer.parseInt(str[1]) > 0)
//            {
//                column.setJava_type(JavaTypeEnum.BIGDECIMAL.value);
//            }
//            // 如果是整形
//            else if (str != null && str.length == 1 && Integer.parseInt(str[0]) <= 10)
//            {
//                column.setJava_type(JavaTypeEnum.INTEGER.value);
//            }
//            // 长整形
//            else
//            {
//                column.setJava_type(JavaTypeEnum.LONG.value);
//            }
//        }
//
//        // 插入字段（默认所有字段都需要插入）
//        column.setIs_insert(GenConstants.REQUIRE);
//
//        // 编辑字段
//        if (!arraysContains(GenConstants.COLUMNNAME_NOT_EDIT, columnName) && !column.is_pk())
//        {
//            column.setIs_edit(GenConstants.REQUIRE);
//        }
//        // 列表字段
//        if (!arraysContains(GenConstants.COLUMNNAME_NOT_LIST, columnName) && !column.is_pk())
//        {
//            column.setIs_list(GenConstants.REQUIRE);
//        }
//        // 查询字段
//        if (!arraysContains(GenConstants.COLUMNNAME_NOT_QUERY, columnName) && !column.is_pk())
//        {
//            column.setIs_query(GenConstants.REQUIRE);
//        }
//
//        // 查询字段类型
//        if (StringUtils.endsWithIgnoreCase(columnName, "name"))
//        {
//            column.setQuery_type(GenConstants.QUERY_LIKE);
//        }
//        // 状态字段设置单选框
//        if (StringUtils.endsWithIgnoreCase(columnName, "status"))
//        {
//            column.setHtml_type(HtmlTypeEnum.RADIO.value);
//        }
//        // 类型&性别字段设置下拉框
//        else if (StringUtils.endsWithIgnoreCase(columnName, "type")
//                || StringUtils.endsWithIgnoreCase(columnName, "sex"))
//        {
//            column.setHtml_type(HtmlTypeEnum.SELECT.value);
//        }
//        // 图片字段设置图片上传控件
//        else if (StringUtils.endsWithIgnoreCase(columnName, "image"))
//        {
//            column.setHtml_type(HtmlTypeEnum.IMAGE_UPLOAD.value);
//        }
//        // 文件字段设置文件上传控件
//        else if (StringUtils.endsWithIgnoreCase(columnName, "file"))
//        {
//            column.setHtml_type(HtmlTypeEnum.FILE_UPLOAD.value);
//        }
//        // 内容字段设置富文本控件
//        else if (StringUtils.endsWithIgnoreCase(columnName, "content"))
//        {
//            column.setHtml_type(HtmlTypeEnum.EDITOR.value);
//        }
//    }

    /**
     * 校验数组是否包含指定值
     * 
     * @param arr 数组
     * @param targetValue 值
     * @return 是否包含
     */
    public static boolean arraysContains(String[] arr, String targetValue) {
        for( String field:arr){
            if(field.toLowerCase().equals(targetValue.toLowerCase())){
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
//        return Arrays.asList(arr).contains(targetValue);
    }

    /**
     * 获取模块名
     * 
     * @param packageName 包名
     * @return 模块名
     */
    public static String getModuleName(String packageName) {
        int lastIndex = packageName.lastIndexOf(".");
        int nameLength = packageName.length();
        return StringUtils.substring(packageName, lastIndex + 1, nameLength);
    }

//    /**
//     * 获取业务名
//     *
//     * @param tableName 表名
//     * @return 业务名
//     */
//    public static String getBusinessName(String tableName) {
//        int lastIndex = tableName.lastIndexOf("_");
//        int nameLength = tableName.length();
//        return StringUtils.substring(tableName, lastIndex + 1, nameLength);
//    }
//
//    /**
//     * 表名转换成Java类名
//     *
//     * @param table_name 表名称
//     * @return 类名
//     */
//    public static String convertClassName(String table_name) {
//        boolean autoRemovePre = GenConfig.getAutoRemovePre();
//        String table_prefix = GenConfig.getTablePrefix();
//        if (autoRemovePre && StringUtils.isNotEmpty(table_prefix))
//        {
//            String[] searchList = StringUtils.split(table_prefix, ",");
//            table_name = replaceFirst(table_name, searchList);
//        }
//        return StringUtils.convertToCamelCase(table_name);
//    }

    /**
     * 批量替换前缀
     * 
     * @param replacementm 替换值
     * @param searchList 替换列表
     * @return
     */
    public static String replaceFirst(String replacementm, String[] searchList) {
        String text = replacementm;
        for (String searchString : searchList)
        {
            if (replacementm.startsWith(searchString))
            {
                text = replacementm.replaceFirst(searchString, "");
                break;
            }
        }
        return text;
    }

    /**
     * 关键字替换
     * 
     * @param text 需要被替换的名字
     * @return 替换后的名字
     */
    public static String replaceText(String text) {
        if(null==text || "".equals(text.trim())){
            return "";
        }
        return (text.replaceAll( "(?:表|MEE)", ""));
    }

//    /**
//     * 获取数据库类型字段
//     *
//     * @param columnType 列类型
//     * @return 截取后的列类型
//     */
//    public static String getDbType(String columnType) {
////        if (StringUtils.indexOf(columnType, "(") > 0)
//        if (columnType.indexOf("(") > 0){
//            return StringUtils.substringBefore(columnType, "(");
//        } else{
//            return columnType;
//        }
//    }
//
//    /**
//     * 获取字段长度
//     *
//     * @param columnType 列类型
//     * @return 截取后的列类型
//     */
//    public static Integer getColumnLength(String columnType) {
//        if (StringUtils.indexOf(columnType, "(") > 0) {
//            String length = StringUtils.substringBetween(columnType, "(", ")");
//            return Integer.valueOf(length);
//        } else{
//            return 0;
//        }
//    }


    /**
     *  获取java类型
     * @param column2
     * @return
     */
    public static String toJavaType( Gen2Column column2 ){
        final String column_type = column2.getColumn_type();
//         默认类型
//        column2.setJava_type(JavaTypeEnum.STRING.value);
        // 主键必须是String （防止丢失精度）
        if( "1".equals(column2.getIs_pk())){
            return JavaTypeEnum.STRING.value;
        }
        // 日期时间类型
        switch (column_type){
            case "varchar":
                // oracle
            case "varchar2":
            case "bpchar":
            case "char":
            case "text":
                return JavaTypeEnum.STRING.value;
            case "date":
                return JavaTypeEnum.LOCALDATE.value;
            case "datetime":
            case "timestamp":
            case "timestamptz":
                return JavaTypeEnum.LOCALDATETIME.value;
            case "smallint":
            case "int":
            case "int2":
            case "int4":
                return JavaTypeEnum.INTEGER.value;
            case "int8":
                return JavaTypeEnum.LONG.value;
            case "float4":
                return JavaTypeEnum.DOUBLE.value;
            case "float8":
                return JavaTypeEnum.BIGDECIMAL.value;
            case "decimal":
            case "numeric":
                // 不含有小数位的均为String
                if( null==column2.getScale() || column2.getScale()==0 ){
                    return JavaTypeEnum.STRING.value;
                }
                return JavaTypeEnum.BIGDECIMAL.value;
            // oracle
            case "NUMBER":
                // 不含有小数位的均为Long
                if( null==column2.getScale() || column2.getScale()==0 ){
                    return JavaTypeEnum.LONG.value;
                }
                return JavaTypeEnum.BIGDECIMAL.value;
            default:
                return JavaTypeEnum.STRING.value;
        }

    }

    /**
     * 表名转换成Java类名
     *
     * @param table_name 表名称
     * @return 类名
     */
    public static String convertClassName(String table_name) {
//        boolean autoRemovePre = GenConfig.getAutoRemovePre();
//        String table_prefix = GenConfig.getTablePrefix();
//        if (autoRemovePre && StringUtils.isNotEmpty(table_prefix))
//        {
//            String[] searchList = StringUtils.split(table_prefix, ",");
//            table_name = replaceFirst(table_name, searchList);
//        }
        return StringUtils.convertToCamelCase(table_name);
    }

}
