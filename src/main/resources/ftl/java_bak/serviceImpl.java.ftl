package ${packageName}.service.impl;

import java.util.List;
#foreach ($column in $columns)
#if($column.java_field == 'createTime' || $column.java_field == 'updateTime')
import com.mee.common.utils.DateUtils;
#break
#end
#end
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
#if($table.sub)
import java.util.ArrayList;
import com.mee.common.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import ${packageName}.domain.${subClassName};
#end
import ${packageName}.mapper.${ClassName}Mapper;
import ${packageName}.domain.${ClassName};
import ${packageName}.service.I${ClassName}Service;

/**
 * ${functionName}Service业务层处理
 * 
 * @author  ${author}
 * @version ${version}
 * @date    ${datetime}
 */
@Service
public class ${ClassName}ServiceImpl implements ${ClassName}Service{
    @Autowired
    private ${ClassName}Mapper ${className}Mapper;

    /**
     * 查询${functionName}
     * 
     * @param ${pkColumn.java_field} ${functionName}主键
     * @return ${functionName}
     */
    @Override
    public ${ClassName} select${ClassName}By${pkColumn.cap_java_field}(${pkColumn.java_type} ${pkColumn.java_field}){
        return ${className}Mapper.select${ClassName}By${pkColumn.cap_java_field}(${pkColumn.java_field});
    }

    /**
     * 查询${functionName}列表
     * 
     * @param ${className} ${functionName}
     * @return ${functionName}
     */
    @Override
    public List<${ClassName}> select${ClassName}List(${ClassName} ${className}){
        return ${className}Mapper.select${ClassName}List(${className});
    }

    /**
     * 新增${functionName}
     * 
     * @param ${className} ${functionName}
     * @return 结果
     */
#if($table.sub)
    @Transactional
#end
    @Override
    public int insert${ClassName}(${ClassName} ${className})
    {
#foreach ($column in $columns)
#if($column.java_field == 'createTime')
        ${className}.setCreateTime(DateUtils.getNowDate());
#end
#end
#if($table.sub)
        int rows = ${className}Mapper.insert${ClassName}(${className});
        insert${subClassName}(${className});
        return rows;
#else
        return ${className}Mapper.insert${ClassName}(${className});
#end
    }

    /**
     * 修改${functionName}
     * 
     * @param ${className} ${functionName}
     * @return 结果
     */
#if($table.sub)
    @Transactional
#end
    @Override
    public int update${ClassName}(${ClassName} ${className}){
#foreach ($column in $columns)
#if($column.java_field == 'updateTime')
        ${className}.setUpdateTime(DateUtils.getNowDate());
#end
#end
#if($table.sub)
        ${className}Mapper.delete${subClassName}By${subTableFkClassName}(${className}.get${pkColumn.cap_java_field}());
        insert${subClassName}(${className});
#end
        return ${className}Mapper.update${ClassName}(${className});
    }

    /**
     * 批量删除${functionName}
     * 
     * @param ${pkColumn.java_field}s 需要删除的${functionName}主键
     * @return 结果
     */
#if($table.sub)
    @Transactional
#end
    @Override
    public int delete${ClassName}By${pkColumn.cap_java_field}s(${pkColumn.java_type}[] ${pkColumn.java_field}s){
#if($table.sub)
        ${className}Mapper.delete${subClassName}By${subTableFkClassName}s(${pkColumn.java_field}s);
#end
        return ${className}Mapper.delete${ClassName}By${pkColumn.cap_java_field}s(${pkColumn.java_field}s);
    }

    /**
     * 删除${functionName}信息
     * 
     * @param ${pkColumn.java_field} ${functionName}主键
     * @return 结果
     */
#if($table.sub)
    @Transactional
#end
    @Override
    public int delete${ClassName}By${pkColumn.cap_java_field}(${pkColumn.java_type} ${pkColumn.java_field}){
#if($table.sub)
        ${className}Mapper.delete${subClassName}By${subTableFkClassName}(${pkColumn.java_field});
#end
        return ${className}Mapper.delete${ClassName}By${pkColumn.cap_java_field}(${pkColumn.java_field});
    }
#if($table.sub)

    /**
     * 新增${subTable.functionName}信息
     * 
     * @param ${className} ${functionName}对象
     */
    public void insert${subClassName}(${ClassName} ${className}){
        List<${subClassName}> ${subclassName}List = ${className}.get${subClassName}List();
        ${pkColumn.java_type} ${pkColumn.java_field} = ${className}.get${pkColumn.cap_java_field}();
        if (StringUtils.isNotNull(${subclassName}List))
        {
            List<${subClassName}> list = new ArrayList<${subClassName}>();
            for (${subClassName} ${subclassName} : ${subclassName}List)
            {
                ${subclassName}.set${subTableFkClassName}(${pkColumn.java_field});
                list.add(${subclassName});
            }
            if (list.size() > 0)
            {
                ${className}Mapper.batch${subClassName}(list);
            }
        }
    }
#end
}
