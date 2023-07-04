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
    MeeResult<Page<${class_name}>> list(Integer ${page_info.page_no_field},Integer ${page_info.page_size_field} <#list columns as c><#if c?? && "1"==c.is_query>,${c.java_type} ${c.java_field}</#if></#list>);

    /**
     * ${table_comment}:详细信息
     * @param ${mapper_key_info.java_field} 主鍵
     * @return 主鍵記錄
     */
    MeeResult<${class_name}> findBy${mapper_key_info.gs_field}(${mapper_key_info.java_type} ${mapper_key_info.java_field});

    /**
     * ${table_comment}:新增
     * @param ${class_name_camel} ${table_comment}:對象
     * @return 新增結果
    */
    MeeResult<Integer> add(${class_name} ${class_name_camel});
    <#--
    /**
     * 批量新增 ${table_comment}
     *
     * @param List<${class_name}(or Map)> ${table_comment}
     * @return 插入条数
     */
        MeeResult<Integer> insertBatch(List<?> param);
    -->

    /**
     * ${table_comment}:修改
     * @param ${class_name_camel} ${table_comment}:對象
     * @return 修改結果
    */
    MeeResult<Integer> update(${class_name} ${class_name_camel});

    /**
     * ${table_comment}:删除
     * @param ${mapper_key_info.java_field} 主鍵
     * @return 刪除結果
    */
    MeeResult<Integer> deleteBy${mapper_key_info.gs_field}(${mapper_key_info.java_type} ${mapper_key_info.java_field});

    /**
     * ${table_comment}:批量删除
     * @param ${mapper_key_info.java_field}s 逐漸列表
     * @return 批量刪除結果
    */
    MeeResult<Integer> deleteBatch(${mapper_key_info.java_type}[] ${mapper_key_info.java_field}s);

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
    void doExport(HttpServletResponse response,@NotNull Integer ${page_info.page_no_field},@NotNull Integer ${page_info.page_size_field}
        <#list columns as c><#if c?? && "1"==c.is_query>,${c.java_type} ${c.java_field}</#if></#list>);

    /**
     * ${table_comment}:导入
     * @param file 文件
     * @return 導入結果
     */
     MeeResult doImport(MultipartFile file)throws Exception;

}
