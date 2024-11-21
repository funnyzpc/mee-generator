package com.mee.generator.web;

import com.mee.generator.core.model.MeeResult;
import com.mee.generator.entity.GenTable;
import com.mee.generator.util.ResultBuild;
import com.mee.generator.mapper.GenTableColumnMapper;
import com.mee.generator.mapper.GenTableMapper;
import com.mee.generator.service.impl.CodeGen1ImportServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author shadow
 * 表格数据导入
 */
@Controller
@RequestMapping("code_gen1_import")
public class CodeGen1ImportController {

    @Autowired
    private GenTableMapper genTableMapper;
    @Autowired
    private GenTableColumnMapper genTableColumnMapper;

    @Autowired
    private CodeGen1ImportServiceImpl codeGen1ImportService;

//    @GetMapping("gen1.html")
//    public String index(){
//        return "gen1/gen1";
//    }

    @GetMapping("gen1_preview.html")
    public String preview(){
        return "gen1_preview";
    }

    /**
     * 查询数据库列表
     */
    @GetMapping(value = "list",produces = "application/json")
    @ResponseBody
    public MeeResult list(String table_name, String table_comment, @RequestParam(defaultValue = "0") int page_no,@RequestParam(defaultValue = "10") int page_size) {
        Map<String,Object> param = new HashMap<>(4,1);
        param.put("table_name",(null==table_name || "".equals(table_name))?null:table_name+"%");
        param.put("table_comment",(null==table_comment || "".equals(table_comment))?null:table_comment+"%");
        param.put("page_no",page_no);
        param.put("page_size",page_size);
        List<GenTable> list= genTableMapper.selectDbTableList(param);
        return ResultBuild.build(list);
    }

    /**
     * 导入表
     *
     * @param table_list 表信息
     * @return MeeResult<List<GenTable>>
     */
    @PostMapping("/save_table")
    @ResponseBody
    public MeeResult saveTable(@RequestBody(required = true) String[] table_list) {
        // 查询表信息
        List<GenTable> tableList = genTableMapper.selectDbTableListByNames(table_list);
        codeGen1ImportService.importGenTable(tableList);
        return ResultBuild.SUCCESS();
    }

}
