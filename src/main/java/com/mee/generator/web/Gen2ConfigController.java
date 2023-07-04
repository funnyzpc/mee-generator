package com.mee.generator.web;

import com.mee.generator.common.model.MeeResult;
import com.mee.generator.entity.Gen2Config;
import com.mee.generator.mapper.Gen2ConfigMapper;
import com.mee.generator.mapper.Gen2TableMapper;
import com.mee.generator.service.impl.CodeGen2ConfigServiceImpl;
import com.mee.generator.util.ResultBuild;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
*   代码生成(for mee_admin)
*   @className  CodeGen2ConfigController
*   @author     shadow
*   @date       2023/4/8 9:03 PM
*   @version    v1.0
*/
@Controller
//@RequestMapping("code_gen2_config")
@RequestMapping("gen2_config")
public class Gen2ConfigController {

    @Autowired
    private Gen2TableMapper genTable2Mapper;
    @Autowired
    private Gen2ConfigMapper genTableConfig2Mapper;
    @Autowired
    private CodeGen2ConfigServiceImpl codeGen2ConfigService;

    /**
     * 主页
     * @return
     */
    @GetMapping("gen2_config.html")
    public String index(){
        return "gen2/gen2_config";
    }

    /**
     * 基本信息
     *
     * @param m .
     * @param type 1.新增 2.修改
     * @param id .
     * @return .
     */
    @GetMapping("gen2_config_form.html")
    public String gen2ConfigForm(Model m,int type,String id){
        if(type ==2 && (null==id || "".equals(id.trim())) ){
            throw new RuntimeException("for update id is not allow be empty!");
        }
        m.addAttribute("type",type);
        m.addAttribute("id",id);
        return "gen2/gen2_config_form";
    }

    /**
     * 查询配置列表
     */
    @GetMapping(value="list",produces = "application/json")
    @ResponseBody
    public MeeResult list(String version, String database_id, @RequestParam(defaultValue = "0")int page_no, @RequestParam(defaultValue = "10")int page_size) {
        Map<String,Object> param = new HashMap<>(4,1);
        param.put("version",(null==version || "".equals(version))?null:"%"+version+"%");
        param.put("database_id",(null==database_id || "".equals(database_id))?null:database_id+"%");
        param.put("page_no",page_no);
        param.put("page_size",page_size);
        List<Gen2Config> list= genTableConfig2Mapper.findList(param);
        return ResultBuild.build(list);
    }

    /**
     * 查询
     * @param type 类型 1.新增 2.修改
     * @param id   数据ID
     * @return .
     */
    @GetMapping(value="find_by_id",produces = "application/json")
    @ResponseBody
    public MeeResult findById(int type,String id) {
        if( null==id ){
            return ResultBuild.fail("必要参数为空");
        }
        Gen2Config genTableConfig2 = new Gen2Config();
        if(1==type){
            genTableConfig2=new Gen2Config();
            // 不开启
            genTableConfig2.setStatus(0);
            // 不使用驼峰模式
            genTableConfig2.setCamel_case("0");
            // default
            genTableConfig2.setDatabase_id("default");
        }else if(2==type){
            genTableConfig2=genTableConfig2Mapper.findById(id);
        }else{
            throw new RuntimeException("type must be in [1,2]");
        }
        return ResultBuild.build(genTableConfig2);
    }


    /**
     * 删除代码生成配置
     * @param id .
     */
    @Transactional(readOnly = false,rollbackFor = Exception.class)
    @DeleteMapping
    @ResponseBody
    public MeeResult remove( String id) {
        if( null==id ){
            return ResultBuild.fail("必要参数不可为空【table_id】");
        }
        return codeGen2ConfigService.delete(id);
    }

    /**
     * 添加
     * @param gen2Config
     * @return
     */
    @PostMapping("/add")
    @ResponseBody
    public MeeResult add(@RequestBody(required = true) Gen2Config gen2Config) {
        return codeGen2ConfigService.add(gen2Config);
    }

    /**
     * 修改保存代码生成业务
     */
    @PutMapping("/update")
    @ResponseBody
    public MeeResult update(@RequestBody(required=true) Gen2Config genTableConfig2) {
        return codeGen2ConfigService.update(genTableConfig2);
    }

    /**
     * 修改保存代码生成业务
     */
    @PostMapping("/enable")
    @ResponseBody
    public MeeResult enable(@RequestBody(required = true) Gen2Config genTableConfig2) {
        return codeGen2ConfigService.enable(genTableConfig2);
    }

    /**
     * 修改保存代码生成业务
     */
    @DeleteMapping("/delete")
    @ResponseBody
    public MeeResult delete(String id ) {
        return codeGen2ConfigService.delete(id);
    }


//    /**
//     * 预览代码（example） TODO。。。
//     */
//    @GetMapping("/preview/{table_id}")
//    @ResponseBody
//    public Map preview(@PathVariable("table_id") Long table_id) {
//        Map<String, String> dataMap = genTable2Service.previewCode(table_id);
//        return ResultBuild.success(dataMap);
//    }


}
