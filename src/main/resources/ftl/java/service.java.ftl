package ${base_package}.module.${module_name}.service;

import java.util.List;
import java.util.Map;
import ${base_package}.module.${module_name}.entity.${class_name};
<#-- 导入包 -->
<#if entity_package??>
<#list entity_package as package>
<#if package?? && !package?contains('jackson') && !package?contains('DateTimeFormat')>
import ${package};
</#if>
</#list>
</#if>

<#-- 头注释 -->
/**
 * ${table_comment}(${class_name}) 业务接口
 * 
 * @author  ${author}
 * @version ${version}
 * @date    ${datetime}
 */
public interface ${class_name}Service{
    /**
     * 查询${table_comment}列表
     *
     * @param ${class_name}(or Map) ${table_comment}
     * @return ${table_comment}分页集合
     */
    Map list(Integer ${page_info.page_no_field},Integer ${page_info.page_size_field} <#list columns as c><#if c?? && "1"==c.is_query>,${c.java_type} ${c.java_field}</#if></#list>);

    /**
     * 按主键查询${table_comment}
     *
     * @param ${table_comment}主键
     * @return ${table_comment}
     */
    Map findBy${mapper_key_info.gs_field}(${mapper_key_info.java_type} ${mapper_key_info.java_field});

    /**
     * 新增${table_comment}
     *
     * @param ${class_name}(or Map) ${table_comment}
     * @return 插入条数
     */
    Map add(${class_name} ${class_name_camel});
    <#--
    /**
     * 批量新增 ${table_comment}
     *
     * @param List<${class_name}(or Map)> ${table_comment}
     * @return 插入条数
     */
    Map insertBatch(List<?> param);
    -->
    /**
     * 修改${table_comment}
     *
     * @param ${class_name}(or Map) ${table_comment}
     * @return 更新条数
     */
    Map edit(${class_name} ${class_name_camel});

    /**
     * 删除${table_comment}
     *
     * @id ${table_comment} 主键
     * @return 删除条数
     */
    Map deleteBy${mapper_key_info.gs_field}(${mapper_key_info.java_type} ${mapper_key_info.java_field});

    /**
     * 批量删除${table_comment}
     *
     * @${mapper_key_info.java_field}s ${table_comment} 主键集合
     * @return 删除条数
     */
    Map deleteBatch(${mapper_key_info.java_type}[] ${mapper_key_info.java_field}s);

}
