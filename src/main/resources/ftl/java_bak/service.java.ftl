package ${packageName}.service;

import java.util.List;
import ${packageName}.domain.${ClassName};

/**
 * ${functionName}Service接口
 * 
 * @author  ${author}
 * @version ${version}
 * @date    ${datetime}
 */
public interface ${ClassName}Service
{
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
     * 批量删除${functionName}
     * 
     * @param ${pkColumn.java_field}s 需要删除的${functionName}主键集合
     * @return 结果
     */
    public int delete${ClassName}By${pkColumn.cap_java_field}s(${pkColumn.java_type}[] ${pkColumn.java_field}s);

    /**
     * 删除${functionName}信息
     * 
     * @param ${pkColumn.java_field} ${functionName}主键
     * @return 结果
     */
    public int delete${ClassName}By${pkColumn.cap_java_field}(${pkColumn.java_type} ${pkColumn.java_field});
}
