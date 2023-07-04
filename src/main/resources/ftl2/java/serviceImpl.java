package ${base_package}.module.${module_name}.service.impl;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import ${base_package}.common.util.SeqGenUtil;
import ${base_package}.common.util.DateUtil;
import ${base_package}.common.util.JacksonUtil;
import ${base_package}.common.util.MeeResult;
import ${base_package}.common.util.ResultBuild;
import ${base_package}.common.util.excel.ExcelReadUtil;
import ${base_package}.common.util.excel.ExcelWriteUtil;
import ${base_package}.core.configuration.ShiroUtils;
import ${base_package}.core.dao.DBSQLDao;
import ${base_package}.core.model.Page;
import ${base_package}.module.${module_name}.entity.${class_name};
import ${base_package}.module.${module_name}.service.${class_name}Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mee.common.service.SeqGenServiceImpl;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.lang.String;
import java.lang.Integer;
import java.lang.Long;


<#-- 头注释 -->
/**
 * ${table_comment}(${class_name}) 业务接口
 *
 * @author  ${author}
 * @version ${version}
 * @date    ${datetime}
*/
@Service
public class ${class_name}ServiceImpl implements ${class_name}Service {

    /**
    *   日志
    */
    private static final Logger LOG = LoggerFactory.getLogger(${class_name}ServiceImpl.class);

    /**
    *   DAO
    */
    @Autowired
    public DBSQLDao dbSQLDao;
    
    /**
    * 主键生成器
    */
    @Autowired
    public SeqGenServiceImpl seqGenService;

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
    @Override
    public MeeResult<Page<${class_name}>> list(Integer ${page_info.page_no_field},Integer ${page_info.page_size_field} <#list columns as c><#if c?? && "1"==c.is_query>,${c.java_type} ${c.java_field}</#if></#list>){
      LOG.info("导出接收到参数 {},{}<#list columns as c><#if c?? && "1"==c.is_query>${",{}"}</#if></#list>",${page_info.page_no_field},${page_info.page_size_field} <#list columns as c><#if c?? && "1"==c.is_query>,${c.java_field}</#if></#list>);
      Map<String,Object> param = new HashMap<String,Object>(${columns?size-1},1);
      <#list columns as c>
      <#if c?? && "1"==c.is_query>
      param.put("${c.java_field}",null==${c.java_field}||"".equals(${c.java_field})?null:<#if "like"==c.query_type>"%"+${c.java_field}+"%"<#else>${c.java_field}</#if> );
      </#if>
      </#list>
      //Page list = dbSQLDao.list("${base_package}.module.${module_name}.mapper.${table_name}.findList",param,${page_info.page_no_field},${page_info.page_size_field});
      Page list = dbSQLDao.list("${base_package}.xml.${module_name}.${class_name}.findList",param,${page_info.page_no_field},${page_info.page_size_field});
      return ResultBuild.build(list);
    }

    /**
     * ${table_comment}::按主键查询
     *
     * @param ${mapper_key_info.java_field} 主键
     * @return ${table_comment}
    */
    @Override
    public MeeResult<${class_name}> findBy${mapper_key_info.gs_field}(${mapper_key_info.java_type} ${mapper_key_info.java_field}){
      LOG.info("开始查询:{}",${mapper_key_info.java_field});
      //if( "".equals(${mapper_key_info.java_field})){
      //  LOG.error("必要参数为空:{}",${mapper_key_info.java_field});
      //  return ResultBuild.fail("必要参数为空[${mapper_key_info.java_field}]");
      //}
      Map<String,Object> param = new HashMap<String,Object>(2,1);
      param.put("${mapper_key_info.java_field}",${mapper_key_info.java_field});
      //${class_name} ${class_name_camel} = dbSQLDao.queryOne("${base_package}.module.${module_name}.mapper.${table_name}.findBy${mapper_key_info.gs_field}", param);
      ${class_name} ${class_name_camel} = dbSQLDao.queryOne("${base_package}.xml.${module_name}.${class_name}.findBy${mapper_key_info.gs_field}", param);
      return ResultBuild.build(${class_name_camel});
    }

    /**
     * ${table_comment}::新增
     *
     * @param ${class_name_camel} ${table_comment}
     * @return 插入条数
    */
    @Override
    public MeeResult<Integer> add(${class_name} ${class_name_camel}){
      LOG.info("接收到参数 {}", ${class_name_camel});
      <#assign _act=0>
      if(<#list columns as c><#if c?? && "1"==c.is_required && "0"==c.is_pk><#if _act gt 0>||</#if>null==${class_name_camel}.get${c.gs_field}()<#assign _act++></#if></#list> ){
          return ResultBuild.fail("参数缺失请检查~");
      }
      final LocalDateTime now = DateUtil.now();
      final String user_id = ShiroUtils.getUserId();
      // 主键
      ${class_name_camel}.set${mapper_key_info.gs_field}(seqGenService.genPrimaryKey());
      // 通用字段
      <#list columns as c><#if c?? && ("create_by"==c.column_name || "create_time"==c.column_name||"update_by"==c.column_name||"update_time"==c.column_name)>
      ${class_name_camel}.set${c.gs_field}(${c.column_name?contains("time")?string("now","user_id")});
      </#if></#list>
      //int insert_count = dbSQLDao.create("${base_package}.module.${module_name}.mapper.${table_name}.insert",${class_name_camel});
      int insert_count = dbSQLDao.create("${base_package}.xml.${module_name}.${class_name}.insert",${class_name_camel});
      return ResultBuild.build(insert_count);
    }

    <#--
    /**
     * ${table_comment}::批量新增
     *
     * @param List<${class_name}(or Map)> ${table_comment}
     * @return 插入条数
    */
    @Override
    public Map insertBatch(List<?> param){

      return null;
    }
    -->

    /**
     * ${table_comment}::修改
     *
     * @param ${class_name_camel} ${table_comment}
     * @return 更新条数
    */
    @Override
    public MeeResult<Integer> update(${class_name} ${class_name_camel}){
      LOG.info("接收到参数 {}",${class_name_camel});
      <#assign _uct=0>
      if( <#list columns as c><#if c?? && "1"==c.is_required><#if _uct gt 0>||</#if>null==${class_name_camel}.get${c.gs_field}()<#assign _uct++></#if></#list> ){
          return ResultBuild.fail("必要参数缺失，请检查~");
      }
      final LocalDateTime now = DateUtil.now();
      final String user_id = ShiroUtils.getUserId();
      // 通用字段
      <#list columns as c><#if c?? && ("update_by"==c.column_name||"update_time"==c.column_name)>
      ${class_name_camel}.set${c.gs_field}(${c.column_name?contains("time")?string("now","user_id")});
      </#if></#list>
      //int update_count = dbSQLDao.update("${base_package}.module.${module_name}.mapper.${table_name}.update",${class_name_camel});
      int update_count = dbSQLDao.update("${base_package}.xml.${module_name}.${class_name}.update",${class_name_camel});
      LOG.info("已更新${table_comment}明细：{}->{}条",${class_name_camel},update_count);
      return ResultBuild.build(update_count);
    }

    /**
     * ${table_comment}::删除
     *
     * @id ${table_comment} 主键
     * @return 删除条数
    */
    @Override
    public MeeResult<Integer> deleteBy${mapper_key_info.gs_field}(${mapper_key_info.java_type} ${mapper_key_info.java_field}){
      LOG.info("开始查询:{}",${mapper_key_info.java_field});
      if( "".equals(${mapper_key_info.java_field})){
          LOG.error("必要参数为空:{}",${mapper_key_info.java_field});
          return ResultBuild.fail("必要参数为空[${mapper_key_info.java_field}]");
      }
      Map<String,Object> param = new HashMap<String,Object>(2,1);
      param.put("${mapper_key_info.java_field}",${mapper_key_info.java_field});
      //${mapper_key_info.java_type} ${mapper_key_info.java_field} = dbSQLDao.queryOne("${base_package}.module.${module_name}.mapper.${table_name}.deleteBy${mapper_key_info.gs_field}", param);
      int delete_count = dbSQLDao.delete("${base_package}.xml.${module_name}.${class_name}.deleteBy${mapper_key_info.gs_field}", param);
      return ResultBuild.build( delete_count );
    }

    /**
     * ${table_comment}::批量删除
     *
     * @${mapper_key_info.java_field}s ${table_comment} 主键集合
     * @return 删除条数
    */
    @Override
    public MeeResult<Integer> deleteBatch(${mapper_key_info.java_type}[] ${mapper_key_info.java_field}s){
      if( 0==${mapper_key_info.java_field}s.length ){
        LOG.error("必要参数为空:{}",${mapper_key_info.java_field}s);
        return ResultBuild.fail("必要参数为空[${mapper_key_info.java_field}]");
      }
      Map<String,Object> param = new HashMap<String,Object>(2,1);
      param.put("list",${mapper_key_info.java_field}s);
      //int delete_count = dbSQLDao.delete("${base_package}.module.${module_name}.mapper.${table_name}.deleteBatch", param);
      int delete_count = dbSQLDao.delete("${base_package}.xml.${module_name}.${class_name}.deleteBatch", param);
      LOG.info("已删除记录{}->{}条",${mapper_key_info.java_field}s,delete_count);
      return ResultBuild.build(delete_count);
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
    @Override
    public void doExport(HttpServletResponse response,Integer ${page_info.page_no_field},Integer ${page_info.page_size_field}
<#list columns as c><#if c?? && "1"==c.is_query>,${c.java_type} ${c.java_field}</#if></#list>) {
        LOG.info("导出接收到参数 {},{}<#list columns as c><#if c?? && "1"==c.is_query>${",{}"}</#if></#list>",${page_info.page_no_field},${page_info.page_size_field} <#list columns as c><#if c?? && "1"==c.is_query>,${c.java_field}</#if></#list>);
        Map<String,Object> param = new HashMap<String,Object>(${columns?size-1},1);
    <#list columns as c>
    <#if c?? && "1"==c.is_query>
        param.put("${c.java_field}",null==${c.java_field}||"".equals(${c.java_field})?null:<#if "like"==c.query_type>"%"+${c.java_field}+"%"<#else>${c.java_field}</#if> );
    </#if>
    </#list>
        //Page list = dbSQLDao.list("${base_package}.module.${module_name}.mapper.${table_name}.findList",param,${page_info.page_no_field},${page_info.page_size_field});
        Page list = dbSQLDao.list("${base_package}.xml.${module_name}.${class_name}.findList",param,${page_info.page_no_field},${page_info.page_size_field});
        List<${class_name}> data_list = list.getData();
        <#assign _ctf=0>
        String[] header_field= { <#list columns as c><#if c?? && "0"==c.is_pk><#if _ctf gt 0>,</#if>"${c.column_comment}\n${c.java_field}"<#assign _ctf++></#if></#list> };
        <#assign _ctf=0>
        String[] data_field = { <#list columns as c><#if c?? && "0"==c.is_pk><#if _ctf gt 0>,</#if>"${c.java_field}"<#assign _ctf++></#if></#list> };
        File file = ExcelWriteUtil.toXlsxByObj( data_list,header_field,data_field);
        String seq = SeqGenUtil.genSeq();
        ResultBuild.toResponse(response,file,"${table_comment}_导出_"+seq+".xlsx");
    }

    /**
     * ${table_comment}:导入
     * @param file 文件
     * @return 導入結果
    */
    @Override
    public MeeResult doImport(MultipartFile file)throws Exception {
        List<Map> data_list = ExcelReadUtil.xlsx2Map(file.getInputStream(), 8);
        LOG.info("解析到数据为:\n{}", JacksonUtil.toJsonString(data_list));
        if( null==data_list || data_list.isEmpty() ){
            return ResultBuild.fail("读取到数据为空，请检查～");
        }
        final String user_id = ShiroUtils.getUserId();
        final LocalDateTime now = DateUtil.now();
        for( Map<String,Object> item:data_list ){
        <#assign _ct=0>
            if(
            <#list columns as c><#if c?? && "1"==c.is_required && "0"==c.is_pk && "create_time"!=c.column_name && "create_by"!=c.column_name><#if _ct gt 0>||</#if>"".equals(item.get("${c.java_field}"))<#assign _ct++></#if></#list>
            ){
                <#assign _ct=0><#-- 重置计数器 -->
                return ResultBuild.fail("必要参数缺失请检查[<#list columns as c><#if c?? && "1"==c.is_required && "0"==c.is_pk && "create_time"!=c.column_name && "create_by"!=c.column_name><#if _ct gt 0>、</#if>${c.java_field}<#assign _ct++>${_ct}</#if></#list>]");
            }
            item.put("id",seqGenService.genMediumPrimaryKey());
            item.put("create_by",user_id);
            item.put("create_time",now);
            item.put("update_by",user_id);
            item.put("update_time",now);
        }
        int insert_count = dbSQLDao.create("${base_package}.xml.${module_name}.${class_name}.insertBatch", data_list);
        LOG.info("${table_name}写入数据{}条", insert_count);
        return ResultBuild.success("成功导入:"+data_list.size() + "条数据");
    }
}
