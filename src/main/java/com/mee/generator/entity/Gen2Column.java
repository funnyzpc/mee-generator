package com.mee.generator.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * @description 单体代码生成::业务字段
 * @author shadow
 * @date 2023-04-08 20:32:04
 */
public class Gen2Column {

     // 主键
     private String id;
     
     // 归属表编号(GEN_TABLE2=>ID)
    private String table_id;
 
     // 列名称
    private String column_name;
 
     // 列描述
    private String column_comment;
 
     // 列类型
    private String column_type;
 
     // JAVA类型
    private String java_type;
 
     // JAVA字段名
    private String java_field;
 
     // 是否主键（1是）
    private String is_pk;
 
     // 是否自增（1是）
    private String is_increment;
 
     // 是否必填（1是）
    private String is_required;
 
     // 是否为插入字段（1是）
    @Deprecated
    private String is_insert;
 
     // 是否编辑字段（1是）
    private String is_edit;
 
     // 是否列表字段（1是）
    @Deprecated
    private String is_list;
 
     // 是否查询字段（1是）
    private String is_query;
 
     // 查询方式（等于、不等于、大于、小于、范围）若范围会生成两个插叙字段_start、_end
    private String query_type;
 
     // 显示类型（文本框、文本域、下拉框、复选框、单选框、日期控件）
    private String html_type;
 
     // 字典类型
    private String dict_type;
 
     // 排序
    private Integer sort;
 
     // 创建者
    private String create_by;
 
     // 创建时间
   @JsonSerialize(using = LocalDateTimeSerializer.class)
   @JsonFormat(shape=JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss")
   @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime create_time;
 
     // 修改人
    private String update_by;
 
     // 修改时间
   @JsonSerialize(using = LocalDateTimeSerializer.class)
   @JsonFormat(shape=JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss")
   @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime update_time;

   // -------- 扩展字段，非数据库字段 --------
    Integer length;
    Integer scale;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTable_id() {
        return table_id;
    }

    public void setTable_id(String table_id) {
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

    public String getCreate_by() {
        return create_by;
    }

    public void setCreate_by(String create_by) {
        this.create_by = create_by;
    }

    public LocalDateTime getCreate_time() {
        return create_time;
    }

    public void setCreate_time(LocalDateTime create_time) {
        this.create_time = create_time;
    }

    public String getUpdate_by() {
        return update_by;
    }

    public void setUpdate_by(String update_by) {
        this.update_by = update_by;
    }

    public LocalDateTime getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(LocalDateTime update_time) {
        this.update_time = update_time;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Integer getScale() {
        return scale;
    }

    public void setScale(Integer scale) {
        this.scale = scale;
    }
}
