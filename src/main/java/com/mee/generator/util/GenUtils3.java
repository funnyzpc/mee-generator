package com.mee.generator.util;

import com.mee.generator.entity.GenTableColumn3;
import com.mee.generator.enums.JavaTypeEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * 代码生成器 工具类
 * 
 * @author mee
 */
public class GenUtils3 {

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

    /**
     * 获取业务名
     * 
     * @param tableName 表名
     * @return 业务名
     */
    public static String getBusinessName(String tableName) {
        int lastIndex = tableName.lastIndexOf("_");
        int nameLength = tableName.length();
        return StringUtils.substring(tableName, lastIndex + 1, nameLength);
    }

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
     * @param column3
     * @return
     */
    public static String toJavaType( GenTableColumn3 column3 ){
        final String column_type = column3.getColumn_type();
//         默认类型
//        column2.setJava_type(JavaTypeEnum.STRING.value);
        // 主键必须是String （防止丢失精度）
        if( "1".equals(column3.getIs_pk())){
            return JavaTypeEnum.LONG.value;
        }
        // 日期时间类型
        switch (column_type){
            // oracle
            case "VARCHAR2":
            case "varchar":
            case "bpchar":
            case "char":
            case "text":
                return JavaTypeEnum.STRING.value;
            case "DATE":
            case "TIMESTAMP(6)":
                return JavaTypeEnum.DATE.value;
            case "date":
                return JavaTypeEnum.LOCALDATE.value;
            case "datetime":
            case "timestamp":
            case "timestamptz":
                return JavaTypeEnum.LOCALDATETIME.value;
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
                if( null==column3.getScale() || column3.getScale()==0 ){
                    return JavaTypeEnum.STRING.value;
                }
                return JavaTypeEnum.BIGDECIMAL.value;
            default:
                return JavaTypeEnum.STRING.value;
        }
    }

    /**
     * 是否忽略字段 (适用于编辑、插入、列表、查询这四大字段)
     * @return
     */
    private static List<String> IGNORE_FIELDS=new ArrayList<String>(8){{
        add("updateTime");
        add("updateUser");
        add("createTime");
        add("createUser");
        add("jpaVersion");
    }};
    public static Boolean isIgnoreField(String java_filed){
        return IGNORE_FIELDS.contains(java_filed);
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
