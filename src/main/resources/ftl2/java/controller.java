package ${base_package}.module.${module_name}.web;

import javax.servlet.http.HttpServletResponse;

import ${base_package}.common.util.MeeResult;
import ${base_package}.core.model.Page;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import ${base_package}.module.${module_name}.service.${class_name}Service;
import ${base_package}.module.${module_name}.entity.${class_name};
import org.springframework.web.multipart.MultipartFile;

import java.lang.String;
import java.lang.Integer;
import java.lang.Long;



<#-- 头注释 -->
/**
 * ${table_comment}web接口(${class_name}Controller)
 *
 * @author  ${author}
 * @version ${version}
 * @date    ${datetime}
 */
@Controller
@RequestMapping("/${module_name}/${gs_table}")
public class ${class_name}Controller{

    /**
    * 业务处理类
    */
    @Autowired
    private ${class_name}Service ${class_name_camel}Service;

    /**
     * ${table_comment}:页面
     * @return 页面
     */
    @RequiresPermissions("${module_name}:${gs_table}:list")
    @GetMapping
    public String index(){
        return "${module_name}/${gs_table}";
    }

    /**
     *  ${table_comment}:列表
     * @param ${page_info.page_no_field}    分页
     * @param ${page_info.page_size_field}  分页大小
    <#list columns as c>
    <#if c?? && "1"==c.is_query>
     * @param ${c.java_field} ${c.column_comment!c.java_field}
    </#if>
    </#list>
     * @return 分頁數據
     */
    @RequiresPermissions("${module_name}:${gs_table}:list")
    @GetMapping("/list")
    @ResponseBody
    public MeeResult<Page<${class_name}>> list(
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
        return ${class_name_camel}Service.list(${page_info.page_no_field},${page_info.page_size_field}<#list columns as c><#if c?? && "1"==c.is_query>,${c.java_field}</#if></#list> );
    }

    /**
     * ${table_comment}:详细信息
     * @param ${mapper_key_info.java_field} 主鍵
     * @return 主鍵記錄
    */
    @RequiresPermissions("${module_name}:${gs_table}:list")
    @GetMapping("/${mapper_key_info.java_field}")
    @ResponseBody
    public MeeResult<${class_name}> findBy${mapper_key_info.gs_field}(@RequestParam(required = true)${mapper_key_info.java_type} ${mapper_key_info.java_field}){
        return ${class_name_camel}Service.findBy${mapper_key_info.gs_field}( ${mapper_key_info.java_field} );
    }

    /**
     * ${table_comment}:新增
     * @param ${class_name_camel} ${table_comment}:對象
     * @return 新增結果
    */
    @RequiresPermissions("${module_name}:${gs_table}:add")
    @PostMapping("add")
    @ResponseBody
    public MeeResult<Integer> add(@RequestBody(required = true) ${class_name} ${class_name_camel}){
        return ${class_name_camel}Service.add( ${class_name_camel} );
    }

    /**
     * ${table_comment}:修改
     * @param ${class_name_camel} ${table_comment}:對象
     * @return 修改結果
     */
    @RequiresPermissions("${module_name}:${gs_table}:update")
    @PutMapping("update")
    @ResponseBody
    public MeeResult<Integer> update(@RequestBody(required = true) ${class_name} ${class_name_camel} ){
        return ${class_name_camel}Service.update( ${class_name_camel} );
    }

    /**
     * ${table_comment}:删除
     * @param ${mapper_key_info.java_field} 主鍵
     * @return 刪除結果
     */
    @RequiresPermissions("${module_name}:${gs_table}:delete")
    @DeleteMapping("/delete")
    @ResponseBody
    public MeeResult<Integer> deleteBy${mapper_key_info.gs_field}(@RequestParam(required = true) ${mapper_key_info.java_type} ${mapper_key_info.java_field}){
        return ${class_name_camel}Service.deleteBy${mapper_key_info.gs_field}(${mapper_key_info.java_field});
    }

    /**
     * ${table_comment}:批量删除
     * @param ${mapper_key_info.java_field}s 逐漸列表
     * @return 批量刪除結果
     */
    @RequiresPermissions("${module_name}:${gs_table}:delete")
    @DeleteMapping("/delete_batch")
    @ResponseBody
    public MeeResult<Integer> deleteBatch(@RequestBody(required = true) ${mapper_key_info.java_type}[] ${mapper_key_info.java_field}s){
        return ${class_name_camel}Service.deleteBatch(${mapper_key_info.java_field}s);
    }

    /**
     *  ${table_comment}:导出
     * @param response   响应体
     * @param ${page_info.page_no_field}    分页
     * @param ${page_info.page_size_field}  分页大小
    <#list columns as c>
    <#if c?? && "1"==c.is_query>
     * @param ${c.java_field} ${c.column_comment!c.java_field}
    </#if>
    </#list>
     * @return 導出excel文件
     */
    @RequiresPermissions("${module_name}:${gs_table}:export")
    @GetMapping("export")
    public void doExport(HttpServletResponse response,
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
        )
    {
        ${class_name_camel}Service.doExport(response,page_no,page_size<#list columns as c><#if c?? && "1"==c.is_query>,${c.java_field}</#if></#list> );
    }

    /**
     * ${table_comment}:导入
     * @param file 文件
     * @return 導入結果
     */
    @RequiresPermissions("${module_name}:${gs_table}:import")
    @PostMapping("import")
    @ResponseBody
    public MeeResult<String> doImport(@RequestParam(value = "file",required = true) MultipartFile file
    /*@RequestParam(value = "name",required = false) String name*/)throws Exception{
        return ${class_name_camel}Service.doImport(file);
    }
}
