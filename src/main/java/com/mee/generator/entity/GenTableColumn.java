package com.mee.generator.entity;

import com.mee.generator.util.StringUtils;


/**
 * 代码生成业务字段表 gen_table_column
 * 
 * @author mee
 */
public class GenTableColumn extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long column_id;

    /** 归属表编号 */
    private Long table_id;

    /** 列名称 */
    private String column_name;

    /** 列描述 */
    private String column_comment;

    /** 列类型 */
    private String column_type;

    /** JAVA类型 */
    private String java_type;

    /** JAVA字段名 */
    //@NotBlank(message = "Java属性不能为空")
    private String java_field;

    /** 是否主键（1是） */
    private String is_pk;

    /** 是否自增（1是） */
    private String is_increment;

    /** 是否必填（1是） */
    private String is_required;

    /** 是否为插入字段（1是） */
    private String is_insert;

    /** 是否编辑字段（1是） */
    private String is_edit;

    /** 是否列表字段（1是） */
    private String is_list;

    /** 是否查询字段（1是） */
    private String is_query;

    /** 查询方式（EQ等于、NE不等于、GT大于、LT小于、LIKE模糊、BETWEEN范围） */
    private String query_type;

    /** 显示类型（input文本框、textarea文本域、select下拉框、checkbox复选框、radio单选框、datetime日期控件、image图片上传控件、upload文件上传控件、editor富文本控件） */
    private String html_type;

    /** 字典类型 */
    private String dict_type;

    /** 排序 */
    private Integer sort;
    /** 主键字段 **/
    private String cap_java_field;

    public Long getColumn_id() {
        return column_id;
    }

    public void setColumn_id(Long column_id) {
        this.column_id = column_id;
    }

    public Long getTable_id() {
        return table_id;
    }

    public void setTable_id(Long table_id) {
        this.table_id = table_id;
    }

    public String getColumn_name() {
        return column_name;
    }

    public void setColumn_name(String column_name) {
        this.column_name = column_name;
    }

    public String getColumn_comment() {
        return column_comment;
    }

    public void setColumn_comment(String column_comment) {
        this.column_comment = column_comment;
    }

    public String getColumn_type() {
        return column_type;
    }

    public void setColumn_type(String column_type) {
        this.column_type = column_type;
    }

    public String getJava_type() {
        return java_type;
    }

    public void setJava_type(String java_type) {
        this.java_type = java_type;
    }

    public String getJava_field() {
        return java_field;
    }

    public void setJava_field(String java_field) {
        this.java_field = java_field;
    }

    public String getIs_pk() {
        return is_pk;
    }

    public void setIs_pk(String is_pk) {
        this.is_pk = is_pk;
    }

    public String getIs_increment() {
        return is_increment;
    }

    public void setIs_increment(String is_increment) {
        this.is_increment = is_increment;
    }

    public String getIs_required() {
        return is_required;
    }

    public void setIs_required(String is_required) {
        this.is_required = is_required;
    }

    public String getIs_insert() {
        return is_insert;
    }

    public void setIs_insert(String is_insert) {
        this.is_insert = is_insert;
    }

    public String getIs_edit() {
        return is_edit;
    }

    public void setIs_edit(String is_edit) {
        this.is_edit = is_edit;
    }

    public String getIs_list() {
        return is_list;
    }

    public void setIs_list(String is_list) {
        this.is_list = is_list;
    }

    public String getIs_query() {
        return is_query;
    }

    public void setIs_query(String is_query) {
        this.is_query = is_query;
    }

    public String getQuery_type() {
        return query_type;
    }

    public void setQuery_type(String query_type) {
        this.query_type = query_type;
    }

    public String getHtml_type() {
        return html_type;
    }

    public void setHtml_type(String html_type) {
        this.html_type = html_type;
    }

    public String getDict_type() {
        return dict_type;
    }

    public void setDict_type(String dict_type) {
        this.dict_type = dict_type;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

//    public boolean isSuperColumn() {
//        return isSuperColumn(this.java_field);
//    }
//
//    public static boolean isSuperColumn(String javaField) {
//        return StringUtils.equalsAnyIgnoreCase(javaField,
//                // BaseEntity
//                "create_by", "create_time", "update_by", "update_time", "remark",
//                // TreeEntity
//                "parent_name", "parent_id", "order_num", "ancestors");
//    }

//    public boolean isUsableColumn()
//    {
//        return isUsableColumn(java_field);
//    }
//
//    public static boolean isUsableColumn(String javaField)
//    {
//        // isSuperColumn()中的名单用于避免生成多余Domain属性，若某些属性在生成页面时需要用到不能忽略，则放在此处白名单
//        return StringUtils.equalsAnyIgnoreCase(javaField, "parentId", "orderNum", "remark");
//    }

//    public String readConverterExp()
//    {
//        String remarks = StringUtils.substringBetween(this.column_comment, "（", "）");
//        StringBuffer sb = new StringBuffer();
//        if (StringUtils.isNotEmpty(remarks))
//        {
//            for (String value : remarks.split(" "))
//            {
//                if (StringUtils.isNotEmpty(value))
//                {
//                    Object startStr = value.subSequence(0, 1);
//                    String endStr = value.substring(1);
//                    sb.append("").append(startStr).append("=").append(endStr).append(",");
//                }
//            }
//            return sb.deleteCharAt(sb.length() - 1).toString();
//        }
//        else
//        {
//            return this.column_comment;
//        }
//    }

    public boolean is_list()
    {
        return is_list(this.is_list);
    }

    public boolean is_list(String is_list)
    {
        return is_list != null && "1".equals(is_list);
    }

    public boolean is_pk()
    {
        return is_pk(this.is_pk);
    }

    public boolean is_pk(String is_pk)
    {
        return is_pk != null && "1".equals(is_pk);
    }
    public boolean is_insert()
    {
        return is_insert(this.is_insert);
    }

    public boolean is_insert(String is_insert)
    {
        return is_insert != null && "1".equals(is_insert);
    }

    public boolean is_edit()
    {
        return is_insert(this.is_edit);
    }

    public boolean is_edit(String is_edit)
    {
        return is_edit != null && "1".equals(is_edit);
    }

    public String getCap_java_field() {
        return cap_java_field;
    }

    public void setCap_java_field(String cap_java_field) {
        this.cap_java_field = cap_java_field;
    }
}
