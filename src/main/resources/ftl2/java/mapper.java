package ${base_package}.module.${module_name}.mapper;


import java.util.List;
import java.lang.String;
import org.apache.ibatis.annotations.Mapper;
import ${base_package}.module.${module_name}.entity.${class_name};

/**
 * ${table_comment} ${class_name}Mapper 接口
 *
 * @author  ${author}
 * @version ${version}
 * @date    ${datetime}
*/
@Mapper
public interface ${class_name}Mapper {

    /**
     * 查询${table_comment}列表
     *
     * @param param (${class_name} or Map,${table_comment})
     * @return ${table_comment}集合
     */
    List<${class_name}> findList(Object param);

    /**
     * 按主键查询${table_comment}
     *
     * @param ${mapper_key_info.java_field} 主键
     * @return ${table_comment}
     */
    ${class_name} findBy${mapper_key_info.gs_field}(${mapper_key_info.java_type} ${mapper_key_info.java_field});

    /**
     * 新增${table_comment}
     *
     * @param param (${class_name} or Map,${table_comment})
     * @return 结果
     */
    int insert(Object param);

    /**
     * 批量新增 ${table_comment}
     *
     * @param param (List<${class_name} or Map)>,${table_comment})
     * @return 结果
     */
    int insertBatch(List<?> param);

    /**
     * 修改${table_comment}
     *
     * @param param (${class_name} or Map,${table_comment})
     * @return 结果
     */
    int update(Object param);

    /**
     * 删除${table_comment}
     *
     * @param ${mapper_key_info.java_field} 主键
     * @return 结果
     */
    int deleteBy${mapper_key_info.gs_field}(${mapper_key_info.java_type} ${mapper_key_info.java_field});

    /**
     * 批量删除${table_comment}
     *
     * @param ${mapper_key_info.java_field}s 主键集合
     * @return 结果
     */
    int deleteBatch(${mapper_key_info.java_type}[] ${mapper_key_info.java_field}s);

}
