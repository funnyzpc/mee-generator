package com.mee.generator.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.mee.generator.enums.CamelCaseEnum;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * @description 单体代码生成::业务表
 * @author shadow
 * @date 2023-04-08 20:32:03
 */
public class Gen2Table {

    /**
     * ID
      */
     private String id;

    /**
     * 表名称
      */
    private String table_name;
 
     // 表描述
    private String table_comment;
 
     // 当前数据库类型(mysql、oracle、postgresql)
    private String current_db;
    private String database_id;

     // 实体类名称
    private String class_name;
 
     // 生成包路径
    private String base_package;
 
     // 生成模块名
    private String module_name;
    private String author;

    /**
     * 内部版本
     */
    private String version;
    /**
     * 是否驼峰
     */
    private CamelCaseEnum camel_case;

    /**备注
     *
     */
    private String desc;
 
     // 创建者
    private String create_by;
 
     // 创建时间
   @JsonSerialize(using = LocalDateTimeSerializer.class)
   @JsonFormat(shape=JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss")
   @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
   private LocalDateTime create_time;
 
     // 更新者
   private String update_by;
 
     // 更新时间
   @JsonSerialize(using = LocalDateTimeSerializer.class)
   @JsonFormat(shape=JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss")
   @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
   private LocalDateTime update_time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getCurrent_db() {
        return current_db;
    }

    public void setCurrent_db(String current_db) {
        this.current_db = current_db;
    }

    public String getDatabase_id() {
        return database_id;
    }

    public void setDatabase_id(String database_id) {
        this.database_id = database_id;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public String getBase_package() {
        return base_package;
    }

    public void setBase_package(String base_package) {
        this.base_package = base_package;
    }

    public String getModule_name() {
        return module_name;
    }

    public void setModule_name(String module_name) {
        this.module_name = module_name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public CamelCaseEnum getCamel_case() {
        return camel_case;
    }

    public Gen2Table setCamel_case(CamelCaseEnum camel_case) {
        this.camel_case = camel_case;
        return this;
    }
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
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

    @Override
    public String toString() {
        return "Gen2Table{" +
                "id='" + id + '\'' +
                ", table_name='" + table_name + '\'' +
                ", table_comment='" + table_comment + '\'' +
                ", current_db='" + current_db + '\'' +
                ", database_id='" + database_id + '\'' +
                ", class_name='" + class_name + '\'' +
                ", base_package='" + base_package + '\'' +
                ", module_name='" + module_name + '\'' +
                ", author='" + author + '\'' +
                ", version='" + version + '\'' +
                ", camel_case='" + camel_case + '\'' +
                ", desc='" + desc + '\'' +
                ", create_by='" + create_by + '\'' +
                ", create_time=" + create_time +
                ", update_by='" + update_by + '\'' +
                ", update_time=" + update_time +
                '}';
    }
}
