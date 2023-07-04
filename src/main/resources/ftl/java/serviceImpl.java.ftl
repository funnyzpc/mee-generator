package ${base_package}.module.${module_name}.service.impl.${class_name}ServiceImpl;

import java.util.List;
import java.util.Map;
import ${base_package}.module.${module_name}.entity.${class_name};
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ${base_package}.core.dao.DB1SQLDao;
import ${base_package}.model.Page;
import ${base_package}.common.service.SeqGenServiceImpl;
import ${base_package}.common.util.ResultBuild;

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
@Service
public class ${class_name}ServiceImpl implements ${class_name}Service{

    /**
    *   日志
    */
    private static final Logger LOG = LoggerFactory.getLogger(SysDictDetailServiceImpl.class);

    /**
    *   Dao
    */
    @Autowired
    public DB1SQLDao db1SQLDao;
    /**
    * 主键生成器
    */
    @Autowired
    public SeqGenServiceImpl seqGenService;

    /**
     * 查询${table_comment}列表
     *
     * @param ${class_name}(or Map) ${table_comment}
     * @return ${table_comment}分页集合
    */
    @Override
    public Map list(Integer ${page_info.page_no_field},Integer ${page_info.page_size_field} ${(columns?size > 0)?string(",","")} <#list columns as c><#if c?? && "1"==c.is_query>,${c.java_type} ${c.java_field}</#if></#list>){
      LOG.info("接收到参数 {},{}${(columns?size > 0)?string(",","")} <#list columns as c><#if c?? && "1"==c.is_query>${"{},"}</#if></#list>",${page_info.page_no_field},${page_info.page_size_field} ${(columns?size > 0)?string(",","")}<#list columns as c><#if c?? && "1"==c.is_query>,${c.java_field}</#if></#list>);
      Map<String,Object> param = new HashMap<String,Object>(${columns?size+2},1);
      <#list columns as c>
      <#if c??>
      param.put("${c.java_field}",null==${c.java_field}||"".equals(${c.java_field})?null:<#if "like"==c.query_type>"%"+${c.java_field}+"%"<#else>${c.java_field}</#if> );
      </#if>
      </#list>
      Page list = db1SQLDao.list("${base_package}.module.${module_name}.mapper.${table_name}.findList",param,${page_info.page_no_field},${page_info.page_size_field});
      return ResultBuild.success(list);
    }

    /**
     * ${table_comment}::按主键查询
     *
     * @param ${table_comment}主键
     * @return ${table_comment}
    */
    @Override
    public Map findBy${mapper_key_info.gs_field}(${mapper_key_info.java_type} ${mapper_key_info.java_field}){
      LOG.info("开始查询:{}",dict_id);
      if(null==${mapper_key_info.java_field} || "".equals(${mapper_key_info.java_field})){
        LOG.error("必要参数为空:{}",${mapper_key_info.java_field});
        return ResultBuild.fail("必要参数为空[${mapper_key_info.java_field}]");
      }
      Map<String,Object> param = new HashMap<String,Object>(2,1);
      param.put("${mapper_key_info.java_field}",${mapper_key_info.java_field});
      ${class_name} ${class_name_camel} = db1SQLDao.queryOne("${base_package}.module.${module_name}.mapper.${table_name}.findBy${mapper_key_info.gs_field}", param);
      return ResultBuild.success(${class_name_camel});
    }

    /**
     * ${table_comment}::新增
     *
     * @param ${class_name}(or Map) ${table_comment}
     * @return 插入条数
    */
    @Override
    public Map add(${class_name} ${class_name_camel}){
      LOG.info("接收到参数 {}", sysDictDetail);
      if(null == ${class_name_camel}
      <#list columns as c><#if c?? && "1"==c.is_required> || null==${class_name_camel}.get${c.gs_field}()</#if></#list> ){
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
      int insert_count = db1SQLDao.create("${base_package}.module.${module_name}.mapper.${table_name}.insert",${class_name_camel});
      return ResultBuild.success(insert_count);
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
     * @param ${class_name}(or Map) ${table_comment}
     * @return 更新条数
    */
    @Override
    public Map edit(${class_name} ${class_name_camel}){
      LOG.info("接收到参数 {}", sysDictDetail);
      if(null == ${class_name_camel}
      <#list columns as c><#if c?? && "1"==c.is_required>||null==${class_name_camel}.get${c.gs_field}()</#if></#list> ){
          return ResultBuild.fail("必要参数缺失，请检查~");
      }
      final LocalDateTime now = DateUtil.now();
      final String user_id = ShiroUtils.getUserId();

      // 通用字段
      <#list columns as c><#if c?? && ("update_by"==c.column_name||"update_time"==c.column_name)>
      ${class_name_camel}.set${c.gs_field}(${c.column_name?contains("time")?string("now","user_id")});
      </#if></#list>

      int update_count = db1SQLDao.update("${base_package}.module.${module_name}.mapper.${table_name}.update",${class_name_camel});
      LOG.info("已更新${table_comment}明细：{}->{}条",${class_name_camel},update_count);
      return ResultBuild.success(update_count);
    }

    /**
     * ${table_comment}::删除
     *
     * @id ${table_comment} 主键
     * @return 删除条数
    */
    @Override
    public Map deleteBy${mapper_key_info.gs_field}(${mapper_key_info.java_type} ${mapper_key_info.java_field}){
      LOG.info("开始查询:{}",${mapper_key_info.java_field});
      if(null==${mapper_key_info.java_field} || "".equals(${mapper_key_info.java_field})){
          LOG.error("必要参数为空:{}",${mapper_key_info.java_field});
          return ResultBuild.fail("必要参数为空[${mapper_key_info.java_field}]");
      }
      Map<String,Object> param = new HashMap<String,Object>(2,1);
      param.put("${mapper_key_info.java_field}",${mapper_key_info.java_field});
      ${mapper_key_info.java_type} ${mapper_key_info.java_field} = db1SQLDao.queryOne("${base_package}.module.${module_name}.mapper.${table_name}.deleteBy${mapper_key_info.gs_field}", param);
      return ResultBuild.success(${mapper_key_info.java_field});
    }

    /**
     * ${table_comment}::批量删除
     *
     * @${mapper_key_info.java_field}s ${table_comment} 主键集合
     * @return 删除条数
    */
    @Override
    Map deleteBatch(${mapper_key_info.java_type}[] ${mapper_key_info.java_field}s){
      if( null==${mapper_key_info.java_field}s || 0==${mapper_key_info.java_field}s.length ){
        LOG.error("必要参数为空:{}",${mapper_key_info.java_field});
        return ResultBuild.fail("必要参数为空[${mapper_key_info.java_field}]");
      }
      Map<String,Object> param = new HashMap<String,Object>(2,1);
      param.put("list",${mapper_key_info.java_field}s);
      int delete_count = db1SQLDao.delete("${base_package}.module.${module_name}.mapper.${table_name}.deleteBatch", param);
      LOG.info("已删除记录{}->{}条",${mapper_key_info.java_field}s,delete_count);
      return ResultBuild.success(delete_count);
    }

}
