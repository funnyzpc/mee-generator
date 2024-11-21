package com.mee.generator.web;

import com.mee.generator.core.model.MeeResult;
import com.mee.generator.entity.*;
import com.mee.generator.mapper.Gen2TableMapper;
import com.mee.generator.mapper.Gen2ColumnMapper;
import com.mee.generator.service.impl.CodeGen2ServiceImpl;
import com.mee.generator.service.impl.GenTable2ServiceImpl;
import com.mee.generator.util.ResultBuild;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
*   代码生成(for mee_admin)
*   @className  CodeGen2Controller
*   @author     shadow
*   @date       2023/4/8 9:03 PM
*   @version    v1.0
*/
@Controller
@RequestMapping("code_gen2")
public class Gen2Controller {
    /**
     * 日志
     */
    private static final Logger LOG = LoggerFactory.getLogger(Gen2Controller.class);

    @Autowired
    private Gen2TableMapper genTable2Mapper;
    @Autowired
    private Gen2ColumnMapper genTableColumn2Mapper;
    @Autowired
    private GenTable2ServiceImpl genTable2Service;
    @Autowired
    private CodeGen2ServiceImpl codeGen2Service;

    /**
     * 主页
     * @return
     */
    @GetMapping("gen2.html")
    public String index(){
        return "gen2/gen2";
    }

    /**
     * 预览
     *
     * @param m
     * @param table_id
     * @return
     */
    @GetMapping("gen2_preview.html")
    public String preview(Model m,String table_id){
        m.addAttribute("table_id",table_id);
        return "gen2/gen2_preview";
    }

    /**
     * 基本信息
     *
     * @param m .
     * @param table_id .
     * @return .
     */
    @GetMapping("gen2_table.html")
    public String gen2Table(Model m,String table_id){
        if(null==table_id || "".equals(table_id.trim())){
            throw new RuntimeException("table_id is empty!");
        }
        m.addAttribute("table_id",table_id);
        return "gen2/gen2_table";
    }

    /**
     * 字段信息
     *
     * @param m .
     * @param table_id .
     * @return .
     */
    @GetMapping("gen2_field.html")
    public String gen2Field(Model m,String table_id){
        if(null==table_id || "".equals(table_id.trim())){
            throw new RuntimeException("table_id is empty!");
        }
        m.addAttribute("table_id",table_id);
        return "gen2/gen2_field";
    }

    /**
     * 导入表
     *
     * @return .
     */
    @GetMapping("gen2_import.html")
    public String gen2Import(){
        return "gen2/gen2_import";
    }

    /**
     * 查询代码生成列表
     */
    @GetMapping(value="list",produces = "application/json")
    @ResponseBody
    public MeeResult<List<Gen2Table>> list(String table_name, String table_comment, @RequestParam(defaultValue = "0")int page_no, @RequestParam(defaultValue = "10")int page_size) {
        Map<String,Object> param = new HashMap<>(8,1);
        param.put("table_name",(null==table_name || "".equals(table_name))?null:"%"+table_name+"%");
        param.put("table_comment",(null==table_comment || "".equals(table_comment))?null:"%"+table_comment+"%");
        param.put("page_no",page_no);
        param.put("page_size",page_size);
        param.put("_offset",page_no*page_size);
        List<Gen2Table> list= genTable2Mapper.findList(param);
        return ResultBuild.build(list);
    }

    @GetMapping(value="find_by_table_id",produces = "application/json")
    @ResponseBody
    public MeeResult<Map> findByTableId(String table_id) {
        if( null==table_id ){
            return ResultBuild.fail("必要参数为空");
        }
        Gen2Table table = genTable2Mapper.findById(table_id);
        List<Gen2Column> list = genTableColumn2Mapper.findByTableId(table_id);
        Map<String, Object> map = new HashMap<String, Object>(4,1);
        map.put("table", table);
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
        int delete_table_count = genTable2Mapper.deleteByTableId(table_id);
        int delete_column_count = genTableColumn2Mapper.deleteByTableId(table_id);
        LOG.info("已删除{}表数据{}条，列数据{}条",table_id,delete_table_count,delete_column_count);
        return ResultBuild.SUCCESS();
    }

    /**
     * 预览代码
     */
    @GetMapping("/preview/{table_id}")
    @ResponseBody
    public MeeResult preview(@PathVariable("table_id") String table_id)throws Exception {
        return genTable2Service.previewCode(table_id);
    }

    /**
     * 修改保存代码生成业务
     */
    @PutMapping("/gen2_table/update")
    @ResponseBody
    public MeeResult gen2BaseUpdate(Gen2Table genTable) {
        return genTable2Service.updateGenTable(genTable);
    }

    @PutMapping("/gen2_field/update")
    @ResponseBody
    public MeeResult gen2FieldUpdate(@RequestBody List<Gen2Column> genTableColumnList) {
        return codeGen2Service.gen2FieldUpdate(genTableColumnList);
    }

    /**
     * 下载代码
     * @param response .
     * @param table_id .
     * @throws Exception
     */
    @GetMapping("/download")
    public void download(HttpServletResponse response, String table_id) throws Exception {
        codeGen2Service.download(response,table_id);
    }


}
