package ${base_package}.module.${module_name}.web;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ${base_package}.module.${module_name}.service.${class_name}Service;
import ${base_package}.module.${module_name}.entity.${class_name};

<#-- 导入包 -->
<#if entity_package??>
<#list entity_package as package>
<#if package??>
import ${package};
</#if>
</#list>
</#if>

<#-- 头注释 -->
/**
 * ${table_comment}web接口(${class_name}Controller)
 *
 * @author  ${author}
 * @version ${version}
 * @date    ${datetime}
 */
@RestController
@RequestMapping("/${gs_table}")
public class ${class_name}Controller{

    /**
    * 业务处理类
    */
    @Autowired
    private ${class_name}Service ${class_name_camel}Service;

    /**
     * 查询 ${table_comment} 列表
     */
    @AuthPermission("${module_name}:${gs_table}:list")
    @GetMapping("/list")
    public Map list(
        @RequestParam(defaultValue="1")Integer ${page_info.page_no_field}, @RequestParam(defaultValue="10")Integer ${page_info.page_size_field}
        <#list columns as c>
        <#if c?? && "1"==c.is_query>
         <#--字段注解-->
         <#if c.field_annotation??>
         <#list c.field_annotation as annotation>
        ${annotation}
         </#list>
         </#if>
        ,${c.java_type} ${c.java_field}
        </#if>
        </#list>
    ){
        return ${class_name_camel}Service.list(Integer ${page_info.page_no_field},Integer ${page_info.page_size_field}
                    <#list columns as c><#if c?? && "1"==c.is_query>,${c.java_field}</#if></#list>
        );
    }
    <#--
    /**
     * 导出${table_comment}列表
     */
    @AuthPermission("${module_name}:${gs_table}:export")
    @PostMapping("/export")
    public void export(HttpServletResponse response, ${class_name} ${class_name}){
        List<${class_name}> list = ${class_name}Service.select${class_name}List(${class_name});
        ExcelUtil<${class_name}> util = new ExcelUtil<${class_name}>(${class_name}.class);
        util.exportExcel(response, list, "${table_comment}数据");
    }
    -->

    /**
     * ${table_comment}::详细信息
     */
    @AuthPermission("${module_name}:${gs_table}:list")
    @GetMapping("/id")
    public Map findBy${mapper_key_info.gs_field}(${mapper_key_info.java_type} id){
        return ${class_name_camel}Service.findBy${mapper_key_info.gs_field}( id );
    }

    /**
     * ${table_comment}::新增
     */
    @AuthPermission("${module_name}:${gs_table}:add")
    @PostMapping
    public Map add(@RequestBody ${class_name} ${class_name_camel}){
        return ${class_name_camel}Service.add( ${class_name_camel} );
    }

    /**
     * ${table_comment}::修改
     */
    @AuthPermission("${module_name}:${gs_table}:edit")
    @PutMapping
    public Map edit(@RequestBody ${class_name} ${class_name_camel} ){
        return ${class_name_camel}Service.edit( ${class_name_camel} );
    }

    /**
     * ${table_comment}::删除
     */
    @AuthPermission("${module_name}:${gs_table}:remove")
    @DeleteMapping("/delete")
    public Map deleteBy${mapper_key_info.gs_field}(${mapper_key_info.java_type} id){
        return ${class_name_camel}Service.deleteBy${mapper_key_info.gs_field}(id);
    }

    /**
     * ${table_comment}::批量删除
     */
    @AuthPermission("${module_name}:${gs_table}:remove")
    @DeleteMapping("/deleteBatch")
    public Map deleteBatch(${mapper_key_info.java_type}[] ids){
        return ${class_name_camel}Service.deleteBatch(ids);
    }

}
