<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<!--
@description    ${table_comment} ${class_name}(${table_name}) Dao
@author         ${author}
@date           ${datetime}
-->
<mapper namespace="${base_package}.module.${module_name}.mapper.${table_name}">

    <!-- 字段 -->
    <resultMap type="${base_package}.module.${module_name}.${class_name}" id="${class_name}Result">
      <#list columns as c>
        <#if c??>
        <result property="${c.java_field}" column="${c.column_name}"/>
        </#if>
      </#list>
    </resultMap>

    <!-- 查询 -->
    <select id="findList" resultMap="${class_name}Result">
        select * from ${table_name}
        <where>
        <#list columns as c>
          <#if c?? && c.java_type??>
            <#if "String"==c.java_type>
            <if test="${c.java_field} != null and ${c.java_field} != ''"> and ${c.column_name} = #${""}{c.java_field}</if>
            <#else>
            <if test="${c.java_field} != null"> and ${c.column_name} = #${""}{c.java_field}</if>
            </#if>
          </#if>
         </#list>
        </where>
        order by ${mapper_key_info.column_name} desc
    </select>

    <!-- 按主键${mapper_key_info.column_name}查询 -->
    <select id="findBy${mapper_key_info.gs_field}" resultMap="${class_name}Result">
        select * from ${table_name}
        where ${mapper_key_info.column_name}=<#noparse>#{</#noparse>${mapper_key_info.java_field}<#noparse>}</#noparse>
    </select>

    <!-- 插入 -->
    <insert id="insert">
        insert into ${table_name}(
        <#list columns as c>
        <#if c??>
            ${c.column_name}<#if c_has_next>,</#if>
        </#if>
        </#list>
        ) values (
            <#list columns as c>
            <#if c??>
            #${""}{${c.java_field}}<#if c_has_next>,</#if>
            </#if>
            </#list>
        )
    </insert>

    <!-- 批量插入 -->
    <insert id="insertBatch">
        insert into ${table_name}(
           <#list columns as c>
           <#if c??>
           ${c.column_name}<#if c_has_next>,</#if>
           </#if>
           </#list>
        ) values
        <foreach collection ="list" item="i" separator =",">
        (
       <#list columns as c>
       <#if c??>
           #${""}{i.${c.java_field}}<#if c_has_next>,</#if>
       </#if>
       </#list>
        )
        </foreach>
    </insert>

    <!-- 更新 -->
    <update id="update">
        update ${table_name}
        <set>
        <#list columns as c>
          <#if c?? && c.java_type?? && "1"!=c.is_pk>
            <#if "String"==c.java_type>
            <if test="${c.java_field} != null and ${c.java_field} != ''">${c.column_name}=#${""}{c.java_field},</if>
            <#else>
            <if test="${c.java_field} != null">${c.column_name}=#${""}{c.java_field},</if>
            </#if>
          </#if>
        </#list>
        </set>
        where ${mapper_key_info.column_name}=<#noparse>#{</#noparse>${mapper_key_info.java_field}<#noparse>}</#noparse>
    </update>

    <!-- 按主键${mapper_key_info.column_name}删除 -->
    <delete id="deleteBy${mapper_key_info.gs_field}">
        delete from ${table_name} where ${mapper_key_info.column_name}=<#noparse>#{</#noparse>${mapper_key_info.java_field}<#noparse>}</#noparse>
    </delete>

    <!-- 批量删除 -->
    <delete id="deleteBatch">
        delete from ${table_name} where ${mapper_key_info.column_name} in
        <foreach collection ="list" item="item" open="(" close=")" separator =",">
            <#noparse>#{item}</#noparse>
        </foreach>
    </delete>

</mapper>