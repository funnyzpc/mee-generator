package ${packageName}.mapper;

import java.util.List;
import ${packageName}.domain.${ClassName};
#if($table.sub)
import ${packageName}.domain.${subClassName};
#end

/**
 * ${functionName}Mapper接口
 * 
 * @author  ${author}
 * @version ${version}
 * @date    ${datetime}
 */
public interface ${ClassName}Mapper {
    /**
     * 查询${functionName}
     * 
     * @param ${pkColumn.java_field} ${functionName}主键
     * @return ${functionName}
     */
    public ${ClassName} select${ClassName}By${pkColumn.cap_java_field}(${pkColumn.java_type} ${pkColumn.java_field});

    /**
     * 查询${functionName}列表
     * 
     * @param ${className} ${functionName}
     * @return ${functionName}集合
     */
    public List<${ClassName}> select${ClassName}List(${ClassName} ${className});

    /**
     * 新增${functionName}
     * 
     * @param ${className} ${functionName}
     * @return 结果
     */
    public int insert${ClassName}(${ClassName} ${className});

    /**
     * 修改${functionName}
     * 
     * @param ${className} ${functionName}
     * @return 结果
     */
    public int update${ClassName}(${ClassName} ${className});

    /**
     * 删除${functionName}
     * 
     * @param ${pkColumn.java_field} ${functionName}主键
     * @return 结果
     */
    public int delete${ClassName}By${pkColumn.cap_java_field}(${pkColumn.java_type} ${pkColumn.java_field});

    /**
     * 批量删除${functionName}
     * 
     * @param ${pkColumn.java_field}s 需要删除的数据主键集合
     * @return 结果
     */
    public int delete${ClassName}By${pkColumn.cap_java_field}s(${pkColumn.java_type}[] ${pkColumn.java_field}s);
#if($table.sub)

    /**
     * 批量删除${subTable.functionName}
     * 
     * @param ${pkColumn.java_field}s 需要删除的数据主键集合
     * @return 结果
     */
    public int delete${subClassName}By${subTableFkClassName}s(${pkColumn.java_type}[] ${pkColumn.java_field}s);
    
    /**
     * 批量新增${subTable.functionName}
     * 
     * @param ${subclassName}List ${subTable.functionName}列表
     * @return 结果
     */
    public int batch${subClassName}(List<${subClassName}> ${subclassName}List);
    

    /**
     * 通过${functionName}主键删除${subTable.functionName}信息
     * 
     * @param ${pkColumn.java_field} ${functionName}ID
     * @return 结果
     */
    public int delete${subClassName}By${subTableFkClassName}(${pkColumn.java_type} ${pkColumn.java_field});
#end
}
