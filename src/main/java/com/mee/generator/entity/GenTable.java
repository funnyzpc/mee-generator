package com.mee.generator.entity;;

import java.io.Serializable;
import java.util.List;

import com.mee.generator.enums.TplCategoryEnum;
import com.mee.generator.util.StringUtils;


/**
 * 业务表 gen_table
 * 
 * @author mee
 */
public class GenTable extends BaseEntity implements Serializable
{
    /**
     * 序列化标识
     */
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long table_id;

    /** 表名称 */
    //@NotBlank(message = "表名称不能为空")
    private String table_name;

    /** 表描述 */
    //@NotBlank(message = "表描述不能为空")
    private String table_comment;

    /** 关联父表的表名 */
    private String sub_table_name;

    /** 本表关联父表的外键名 */
    private String sub_table_fk_name;

    /** 实体类名称(首字母大写) */
    //@NotBlank(message = "实体类名称不能为空")
    private String class_name;

    /** 使用的模板（crud单表操作 tree树表操作 sub主子表操作） */
    private String tpl_category;

    /** 生成包路径 */
    //@NotBlank(message = "生成包路径不能为空")
    private String package_name;

    /** 生成模块名 */
    //@NotBlank(message = "生成模块名不能为空")
    private String module_name;

    /** 生成业务名 */
    //@NotBlank(message = "生成业务名不能为空")
    private String business_name;

    /** 生成功能名 */
    //@NotBlank(message = "生成功能名不能为空")
    private String function_name;

    /** 生成作者 */
    //@NotBlank(message = "作者不能为空")
    private String function_author;

    /** 版本 **/
    private String function_version;

    /** 生成代码方式（0zip压缩包 1自定义路径） */
    private String gen_type;

    /** 生成路径（不填默认项目路径） */
    private String gen_path;

    /** 主键信息 */
    private GenTableColumn pk_column;

    /** 子表信息 */
    private GenTable sub_table;

    /** 表列信息 */
//    @Valid
    private List<GenTableColumn> columns;

    /** 其它生成选项 */
    private String options;

    /** 树编码字段 */
    private String tree_code;

    /** 树父编码字段 */
    private String tree_parent_code;

    /** 树名称字段 */
    private String tree_name;

    /** 上级菜单ID字段 */
    private String parent_menu_id;

    /** 上级菜单名称字段 */
    private String parent_menu_name;

    public Long getTable_id() {
        return table_id;
    }

    public void setTable_id(Long table_id) {
        this.table_id = table_id;
    }

    public String getTable_name() {
        return table_name;
    }

    public void setTable_name(String table_name) {
        this.table_name = table_name;
    }

    public String getTable_comment() {
        return table_comment;
    }

    public void setTable_comment(String table_comment) {
        this.table_comment = table_comment;
    }

    public String getSub_table_name() {
        return sub_table_name;
    }

    public void setSub_table_name(String sub_table_name) {
        this.sub_table_name = sub_table_name;
    }

    public String getSub_table_fk_name() {
        return sub_table_fk_name;
    }

    public void setSub_table_fk_name(String sub_table_fk_name) {
        this.sub_table_fk_name = sub_table_fk_name;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public String getTpl_category() {
        return tpl_category;
    }

    public void setTpl_category(String tpl_category) {
        this.tpl_category = tpl_category;
    }

    public String getPackage_name() {
        return package_name;
    }

    public void setPackage_name(String package_name) {
        this.package_name = package_name;
    }

    public String getModule_name() {
        return module_name;
    }

    public void setModule_name(String module_name) {
        this.module_name = module_name;
    }

    public String getBusiness_name() {
        return business_name;
    }

    public void setBusiness_name(String business_name) {
        this.business_name = business_name;
    }

    public String getFunction_name() {
        return function_name;
    }

    public void setFunction_name(String function_name) {
        this.function_name = function_name;
    }

    public String getFunction_author() {
        return function_author;
    }

    public void setFunction_author(String function_author) {
        this.function_author = function_author;
    }

    public String getGen_type() {
        return gen_type;
    }

    public void setGen_type(String gen_type) {
        this.gen_type = gen_type;
    }

    public String getGen_path() {
        return gen_path;
    }

    public void setGen_path(String gen_path) {
        this.gen_path = gen_path;
    }

    public GenTableColumn getPk_column() {
        return pk_column;
    }

    public void setPk_column(GenTableColumn pk_column) {
        this.pk_column = pk_column;
    }

    public GenTable getSub_table() {
        return sub_table;
    }

    public void setSub_table(GenTable sub_table) {
        this.sub_table = sub_table;
    }

    public List<GenTableColumn> getColumns() {
        return columns;
    }

    public void setColumns(List<GenTableColumn> columns) {
        this.columns = columns;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public String getTree_code() {
        return tree_code;
    }

    public void setTree_code(String tree_code) {
        this.tree_code = tree_code;
    }

    public String getTree_parent_code() {
        return tree_parent_code;
    }

    public void setTree_parent_code(String tree_parent_code) {
        this.tree_parent_code = tree_parent_code;
    }

    public String getTree_name() {
        return tree_name;
    }

    public void setTree_name(String tree_name) {
        this.tree_name = tree_name;
    }

    public String getParent_menu_id() {
        return parent_menu_id;
    }

    public void setParent_menu_id(String parent_menu_id) {
        this.parent_menu_id = parent_menu_id;
    }

    public String getParent_menu_name() {
        return parent_menu_name;
    }

    public void setParent_menu_name(String parent_menu_name) {
        this.parent_menu_name = parent_menu_name;
    }

//    public boolean isSuperColumn(String java_field) {
//        return isSuperColumn(this.tpl_category, java_field);
//    }
//
//    public static boolean isSuperColumn(String tplCategory, String javaField) {
//        if (isTree(tplCategory))  {
//            return StringUtils.equalsAnyIgnoreCase(javaField,
//                    ArrayUtils.addAll(GenConstants.TREE_ENTITY, GenConstants.BASE_ENTITY));
//        }
//        return StringUtils.equalsAnyIgnoreCase(javaField, GenConstants.BASE_ENTITY);
//    }

    public boolean isTree(){
        return isTree(this.tpl_category);
    }

    public static boolean isTree(String tplCategory){
//        return tplCategory != null && StringUtils.equals(GenConstants.TPL_TREE, tplCategory);
        return tplCategory != null && StringUtils.equals(TplCategoryEnum.TREE.value, tplCategory);
    }

    // ---------------- 扩展 -----------------
    public boolean isCrud() {
        return isCrud(this.tpl_category);
    }
    public static boolean isCrud(String tpl_category){
//        return tpl_category != null && StringUtils.equals(GenConstants.TPL_CRUD, tpl_category);
        return tpl_category != null && StringUtils.equals(TplCategoryEnum.CRUD.value, tpl_category);
    }

    public boolean isSub() {
        return isSub(this.tpl_category);
    }
    public static boolean isSub(String tpl_category) {
//        return tpl_category != null && StringUtils.equals(GenConstants.TPL_SUB, tpl_category);
        return tpl_category != null && StringUtils.equals(TplCategoryEnum.SUB.value, tpl_category);
    }

    public String getFunction_version() {
        return function_version;
    }

    public void setFunction_version(String function_version) {
        this.function_version = function_version;
    }

    @Override
    public String toString() {
        return "GenTable{" +
                "table_id=" + table_id +
                ", table_name='" + table_name + '\'' +
                ", table_comment='" + table_comment + '\'' +
                ", sub_table_name='" + sub_table_name + '\'' +
                ", sub_table_fk_name='" + sub_table_fk_name + '\'' +
                ", class_name='" + class_name + '\'' +
                ", tpl_category='" + tpl_category + '\'' +
                ", package_name='" + package_name + '\'' +
                ", module_name='" + module_name + '\'' +
                ", business_name='" + business_name + '\'' +
                ", function_name='" + function_name + '\'' +
                ", function_author='" + function_author + '\'' +
                ", function_version='" + function_version + '\'' +
                ", gen_type='" + gen_type + '\'' +
                ", gen_path='" + gen_path + '\'' +
                ", pk_column=" + pk_column +
                ", sub_table=" + sub_table +
                ", columns=" + columns +
                ", options='" + options + '\'' +
                ", tree_code='" + tree_code + '\'' +
                ", tree_parent_code='" + tree_parent_code + '\'' +
                ", tree_name='" + tree_name + '\'' +
                ", parent_menu_id='" + parent_menu_id + '\'' +
                ", parent_menu_name='" + parent_menu_name + '\'' +
                '}';
    }
}
