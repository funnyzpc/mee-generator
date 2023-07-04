package ${packageName}.controller;

import java.util.List;
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
import com.mee.common.core.controller.BaseController;
import com.mee.common.enums.BusinessType;
import ${packageName}.domain.${ClassName};
import ${packageName}.service.I${ClassName}Service;
import com.mee.common.utils.poi.ExcelUtil;
#*
#if($table.isCrud || $table.isSub)
import com.mee.common.core.page.TableDataInfo;
#elseif($table.tree)
#end
*#
/**
 * ${functionName}Controller
 * 
 * @author ${author}
 * @date ${datetime}
 */
@RestController
@RequestMapping("/${moduleName}/${businessName}")
public class ${ClassName}Controller extends BaseController{
    @Autowired
    private ${ClassName}Service ${className}Service;

    /**
     * 查询${functionName}列表
     */
    @AuthPermission("${permissionPrefix}:list")
    @GetMapping("/list")
#if($table.isCrud() || $table.isSub())
    public Map list(${ClassName} ${className}){
        startPage();
        List<${ClassName}> list = ${className}Service.select${ClassName}List(${className});
        return getDataTable(list);
    }
#elseif($table.tree)
    public Map list(${ClassName} ${className}){
        List<${ClassName}> list = ${className}Service.select${ClassName}List(${className});
        return ResultBuild.success(list);
    }
#end

    /**
     * 导出${functionName}列表
     */
    @AuthPermission("${permissionPrefix}:export") #*@Log(title = "${functionName}", businessType = BusinessType.EXPORT)*#
    @PostMapping("/export")
    public void export(HttpServletResponse response, ${ClassName} ${className}){
        List<${ClassName}> list = ${className}Service.select${ClassName}List(${className});
        ExcelUtil<${ClassName}> util = new ExcelUtil<${ClassName}>(${ClassName}.class);
        util.exportExcel(response, list, "${functionName}数据");
    }

    /**
     * 获取${functionName}详细信息
     */
    @AuthPermission("${permissionPrefix}:query")
    @GetMapping(value = "/{${pkColumn.java_field}}")
    public Map getInfo(@PathVariable("${pkColumn.java_field}") ${pkColumn.java_type} ${pkColumn.java_field}){
        return ResultBuild.success(${className}Service.select${ClassName}By${pkColumn.cap_java_field}(${pkColumn.java_field}));
    }

    /**
     * 新增${functionName}
     */
    @AuthPermission("${permissionPrefix}:add") #*@Log(title = "${functionName}", businessType = BusinessType.INSERT)*#
    @PostMapping
    public Map add(@RequestBody ${ClassName} ${className}){
        return ResultBuild.success(${className}Service.insert${ClassName}(${className}));
    }

    /**
     * 修改${functionName}
     */
    @AuthPermission("${permissionPrefix}:edit")  #*@Log(title = "${functionName}", businessType = BusinessType.UPDATE)*#
    @PutMapping
    public Map edit(@RequestBody ${ClassName} ${className}){
        return ResultBuild.success(${className}Service.update${ClassName}(${className}));
    }

    /**
     * 删除${functionName}
     */
    @AuthPermission("${permissionPrefix}:remove")  #*@Log(title = "${functionName}", businessType = BusinessType.DELETE)*#
	@DeleteMapping("/{${pkColumn.java_field}s}")
    public Map remove(@PathVariable ${pkColumn.java_type}[] ${pkColumn.java_field}s){
        return ResultBuild.success(${className}Service.delete${ClassName}By${pkColumn.cap_java_field}s(${pkColumn.java_field}s));
    }
}
