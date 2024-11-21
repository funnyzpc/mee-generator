package com.mee.generator.web;

import com.mee.generator.core.model.MeeResult;
import com.mee.generator.entity.GenTable;
import com.mee.generator.entity.GenTableColumn;
import com.mee.generator.util.ResultBuild;
import com.mee.generator.mapper.GenTableColumnMapper;
import com.mee.generator.mapper.GenTableMapper;
import com.mee.generator.service.impl.CodeGen1ServiceImpl;
import com.mee.generator.service.impl.GenTableServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  代码生成（v1）
 * @author shadow
 */
@Controller
@RequestMapping("code_gen1")
public class CodeGen1Controller {

    @Autowired
    private GenTableMapper genTableMapper;
    @Autowired
    private GenTableColumnMapper genTableColumnMapper;
    @Autowired
    private GenTableServiceImpl genTableService;
    @Autowired
    private CodeGen1ServiceImpl codeGen1Service;

    @GetMapping("gen1.html")
    public String index(){
        return "gen1/gen1";
    }

    /**
     * 预览
     *
     * @param m
     * @param table_id
     * @return
     */
    @GetMapping("gen1_preview.html")
    public String preview(Model m,String table_id){
        m.addAttribute("table_id",table_id);
        return "gen1/gen1_preview";
    }

    /**
     * 基本信息
     *
     * @param m .
     * @param table_id .
     * @return .
     */
    @GetMapping("gen1_base.html")
    public String gen1Base(Model m,String table_id){
        if(null==table_id || "".equals(table_id.trim())){
            throw new RuntimeException("table_id is empty!");
        }
        m.addAttribute("table_id",table_id);
        return "gen1/gen1_base";
    }

    /**
     * 字段信息
     *
     * @param m .
     * @param table_id .
     * @return .
     */
    @GetMapping("gen1_field.html")
    public String gen1Field(Model m,String table_id){
        if(null==table_id || "".equals(table_id.trim())){
            throw new RuntimeException("table_id is empty!");
        }
        m.addAttribute("table_id",table_id);
        return "gen1/gen1_field";
    }

    /**
     * 生成信息
     *
     * @param m .
     * @param table_id .
     * @return .
     */
    @GetMapping("gen1_generate.html")
    public String gen1Generate(Model m,String table_id){
        if(null==table_id || "".equals(table_id.trim())){
            throw new RuntimeException("table_id is empty!");
        }
        m.addAttribute("table_id",table_id);
        return "gen1/gen1_generate";
    }

    /**
     * 导入表
     *
     * @return .
     */
    @GetMapping("gen1_import.html")
    public String gen1Import(){
        return "gen1/gen1_import";
    }

    /**
     * 查询代码生成列表
     */
    @GetMapping(value="list",/*consumes = "application/json",*/produces = "application/json")
    @ResponseBody
    public MeeResult list(String table_name, String table_comment, @RequestParam(defaultValue = "0")int page_no, @RequestParam(defaultValue = "10")int page_size) {
        Map<String,Object> param = new HashMap<>(4,1);
        param.put("table_name",(null==table_name || "".equals(table_name))?null:"%"+table_name+"%");
        param.put("table_comment",(null==table_comment || "".equals(table_comment))?null:"%"+table_comment+"%");
        param.put("page_no",page_no);
        param.put("page_size",page_size);
        param.put("_offset",page_no*page_size);
        List list= genTableMapper.findList(param);
        return ResultBuild.build(list);
    }

    @GetMapping(value="find_by_table_id",produces = "application/json")
    @ResponseBody
    public MeeResult findByTableId(Long table_id) {
        if( null==table_id ){
            return ResultBuild.fail("必要参数为空");
        }

        GenTable table = genTableMapper.selectGenTableById(table_id);
        List<GenTableColumn> list = genTableColumnMapper.selectGenTableColumnListByTableId(table_id);
        Map<String, Object> map = new HashMap<String, Object>(4,1);
        map.put("base", table);
        map.put("field", list);
        return ResultBuild.build(map);
    }


    /**
     * 删除代码生成
     * @param table_id .
     */
    @Transactional(readOnly = false,rollbackFor = Exception.class)
    @DeleteMapping("/{table_id}")
    @ResponseBody
    public MeeResult remove(@PathVariable Long table_id) {
        if( null==table_id ){
            return ResultBuild.fail("必要参数不可为空【table_id】");
        }
        genTableMapper.deleteGenTableByTableId(table_id);
        genTableColumnMapper.deleteByTableId(table_id);
        return ResultBuild.SUCCESS();
    }

    /**
     * 预览代码
     */
    @GetMapping("/preview/{table_id}")
    @ResponseBody
    public MeeResult<Map<String, String>> preview(@PathVariable("table_id") Long table_id) {
        Map<String, String> dataMap = genTableService.previewCode(table_id);
        return ResultBuild.build(dataMap);
    }

    /**
     * 修改保存代码生成业务
     */
    @PutMapping("/gen1_base/update")
    @ResponseBody
    public MeeResult gen1BaseUpdate(GenTable genTable) {
        if( null==genTable || null==genTable.getTable_id() ){
            return ResultBuild.fail("必要参数不可为空！");
        }
        genTableService.updateGenTable(genTable);
        return ResultBuild.SUCCESS();
    }

    @PutMapping("/gen1_generate/update")
    @ResponseBody
    public MeeResult gen1GenerateUpdate(GenTable genTable) {
        if( null==genTable || null==genTable.getTable_id() ){
            return ResultBuild.fail("必要参数不可为空！");
        }
        genTableService.validateEdit(genTable);
        genTableService.updateGenTable(genTable);
        return ResultBuild.SUCCESS();
    }

    @PutMapping("/gen1_field/update")
    @ResponseBody
    public MeeResult gen1FieldUpdate(@RequestBody List<GenTableColumn> genTableColumnList) {
      return codeGen1Service.gen1FieldUpdate(genTableColumnList);
    }

    @GetMapping("/download")
    public void download(HttpServletResponse response, Long table_id) throws IOException {
        codeGen1Service.download(response,table_id);
    }


}
