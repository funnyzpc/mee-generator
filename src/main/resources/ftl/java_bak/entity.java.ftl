package ${packageName}.domain;

#foreach ($import in $importList)
import ${import};
#end
#*
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.mee.common.annotation.Excel;

#if($table.crud || $table.sub)
import com.mee.common.core.domain.BaseEntity;
#elseif($table.tree)
import com.mee.common.core.domain.TreeEntity;
#end
*#

/**
 * ${functionName}对象 ${tableName}
 * 
 * @author  ${author}
 * @version ${version}
 * @date    ${datetime}
 */
#if($table.crud || $table.sub)
#set($Entity="BaseEntity")
#elseif($table.tree)
#set($Entity="TreeEntity")
#end
#*public class ${ClassName} extends ${Entity}*#
public class ${ClassName} implements Serializable{
    private static final long serialVersionUID = 1L;

#foreach ($column in $columns)
#if(!$table.isSuperColumn($column.java_field))
    /** $column.column_comment */
#if($column.list)
#set($parentheseIndex=$column.column_comment.indexOf("（"))
#if($parentheseIndex != -1)
#set($comment=$column.column_comment.substring(0, $parentheseIndex))
#else
#set($comment=$column.column_comment)
#end
#if($parentheseIndex != -1)
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
#elseif($column.java_type == 'Date')
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "${comment}", width = 30, dateFormat = "yyyy-MM-dd")
#else
    @Excel(name = "${comment}")
#end
#end
    private $column.java_type $column.java_field;

#end
#end
#if($table.sub)
    /** $table.subTable.functionName信息 */
    private List<${subClassName}> ${subclassName}List;

#end
#foreach ($column in $columns)
#if(!$table.isSuperColumn($column.java_field))
#if($column.java_field.length() > 2 && $column.java_field.substring(1,2).matches("[A-Z]"))
#set($AttrName=$column.java_field)
#else
#set($AttrName=$column.java_field.substring(0,1).toUpperCase() + ${column.java_field.substring(1)})
#end
    public void set${AttrName}($column.java_type $column.java_field){
        this.$column.java_field = $column.java_field;
    }

    public $column.java_type get${AttrName}(){
        return $column.java_field;
    }
#end
#end

#if($table.sub)
    public List<${subClassName}> get${subClassName}List()
    {
        return ${subclassName}List;
    }

    public void set${subClassName}List(List<${subClassName}> ${subclassName}List)
    {
        this.${subclassName}List = ${subclassName}List;
    }

#end
#*
    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
#foreach ($column in $columns)
#if($column.java_field.length() > 2 && $column.java_field.substring(1,2).matches("[A-Z]"))
#set($AttrName=$column.java_field)
#else
#set($AttrName=$column.java_field.substring(0,1).toUpperCase() + ${column.java_field.substring(1)})
#end
            .append("${column.java_field}", get${AttrName}())
#end
#if($table.sub)
            .append("${subclassName}List", get${subClassName}List())
#end
            .toString();
    }
*#
    @Override
    public String toString() {
#*
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
#foreach ($column in $columns)
#if($column.java_field.length() > 2 && $column.java_field.substring(1,2).matches("[A-Z]"))
#set($AttrName=$column.java_field)
#else
#set($AttrName=$column.java_field.substring(0,1).toUpperCase() + ${column.java_field.substring(1)})
#end
            .append("${column.java_field}", get${AttrName}())
#end
#if($table.sub)
            .append("${subclassName}List", get${subClassName}List())
#end
            .toString();
*#
            return "${ClassName} {" +
                    #foreach ($column in $columns)
                            "\"${column.java_field}\":" + "\"" +get${AttrName}() +"\","+
                            #end
                            '}';
    }
}

