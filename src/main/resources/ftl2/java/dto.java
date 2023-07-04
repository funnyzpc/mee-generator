package ${base_package}.module.${module_name}.dto;
import java.lang.String;
import java.lang.Integer;
import java.io.Serializable;
import java.lang.Long;
import java.time.LocalDateTime;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;

<#-- 头注释 -->
/**
 * ${table_comment}对象 ${table_name}
 * 
 * @author  ${author}
 * @version ${version}
 * @date    ${datetime}
 */
//@ApiModel("${class_name}DTO(${table_comment})")
public class ${class_name}DTO implements Serializable{

  /**
  * 序列化标识
   */
  private static final long serialVersionUID = 1L;

<#-- field 实体类字段 -->
<#list columns as c>
<#if c??>
  <#-- 字段注释 -->
  /**
  * ${c.column_comment!c.java_field}
  */
  //@ApiModelProperty("${c.column_comment!c.java_field}")
  <#--字段注解-->
  <#if c.field_annotation??>
  <#list c.field_annotation as annotation>
  ${annotation}
  </#list>
  </#if>
  <#--字段-->
  private ${c.java_type} ${c.java_field};

</#if>
</#list>

<#-- Getter and Setter -->
<#list columns as c>
<#if c??>
  public ${c.java_type} get${c.gs_field}() {
    return ${c.java_field};
  }

  public ${class_name}DTO set${c.gs_field}(${c.java_type} ${c.java_field}) {
    this.${c.java_field}=${c.java_field};
    return this;
  }
</#if>
</#list>

<#-- toString -->
  @Override
  public String toString() {
      return "${class_name}DTO::{"+
      <#list columns as c>
      <#if c??>
      "<#if !c?is_first>, </#if>${c.java_field}:"+this.${c.java_field}+
      </#if>
      </#list>
      "}";
  }
}
