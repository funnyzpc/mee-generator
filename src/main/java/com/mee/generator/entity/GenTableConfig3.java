package com.mee.generator.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.mee.generator.enums.CamelCaseEnum;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * @description 单体代码生成::配置
 * @author shadow
 * @date 2023-04-08 20:32:02
 */
public class GenTableConfig3 {

     // 表ID
     private String id;
     
     // 作者
    private String author;
 
     // 版本(v1.0)
    private String version;
 
     // 接口、字段是否驼峰格式 Y.是 N.否
    private CamelCaseEnum camel_case;
 
     // 包基础名称(example:com.mee.generator)
    private String base_package;
 
     // 模块所在目录（example：BASE_PACKAGE.module.MODULE_NAME)
    private String module_name;
 
//     // 后端代码所在目录
//    private String back_dir;
//
//     // 前端代码所在目录
//    private String front_dir;
 
     // 数据类型(default,mysql、postgresql、oracle等等)
    private String database_id;
    /**
     * 状态 1.启用 0.禁用（一般至少有一个启用的）
     */
    private Integer status;
    /**
     * 描述
     */
    private String desc;

     // 更新版本
    private Integer update_version;
 
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

    public void setCamel_case(CamelCaseEnum camel_case) {
        this.camel_case = camel_case;
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

    public String getDatabase_id() {
        return database_id;
    }

    public void setDatabase_id(String database_id) {
        this.database_id = database_id;
    }

    public Integer getUpdate_version() {
        return update_version;
    }

    public void setUpdate_version(Integer update_version) {
        this.update_version = update_version;
    }

    public LocalDateTime getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(LocalDateTime update_time) {
        this.update_time = update_time;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "GenTableConfig3{" +
                "id='" + id + '\'' +
                ", author='" + author + '\'' +
                ", version='" + version + '\'' +
                ", camel_case='" + camel_case + '\'' +
                ", base_package='" + base_package + '\'' +
                ", module_name='" + module_name + '\'' +
                ", database_id='" + database_id + '\'' +
                ", status=" + status +
                ", desc='" + desc + '\'' +
                ", update_version=" + update_version +
                ", update_time=" + update_time +
                '}';
    }

}
