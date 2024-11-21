package com.mee.generator.web;

import com.mee.generator.core.model.MeeResult;
import com.mee.generator.entity.Gen2Table;
import com.mee.generator.util.ResultBuild;
import com.mee.generator.mapper.Gen2TableMapper;
import com.mee.generator.mapper.Gen2ColumnMapper;
import com.mee.generator.service.impl.Gen2ImportServiceImpl;
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
@RequestMapping("code_gen2_import")
public class Gen2ImportController {

    @Autowired
    private Gen2TableMapper genTable2Mapper;
    @Autowired
    private Gen2ColumnMapper genTableColumn2Mapper;

    @Autowired
    private Gen2ImportServiceImpl gen2ImportService;

//    @GetMapping("gen2_preview.html")
//    public String preview(){
//        return "gen2_preview";
//    }

    /**
     * 查询数据库列表
     */
    @GetMapping(value = "list",produces = "application/json")
    @ResponseBody
    public MeeResult<List<Gen2Table>> list(String table_name, String table_comment, @RequestParam(defaultValue = "0") int page_no, @RequestParam(defaultValue = "10") int page_size) {
        Map<String,Object> param = new HashMap<>(4,1);
        param.put("table_name",(null==table_name || "".equals(table_name))?null:table_name+"%");
        param.put("table_comment",(null==table_comment || "".equals(table_comment))?null:table_comment+"%");
        param.put("page_no",page_no);
        param.put("page_size",page_size);
        param.put("_offset",page_no*page_size);
        List<Gen2Table> list= genTable2Mapper.findTableList(param);
        return ResultBuild.build(list);
    }

    /**
     * 导入表
     *
     * @param table_list .
     * @return .
     */
    @PostMapping("/save_table")
    @ResponseBody
    public MeeResult saveTable(@RequestBody String[] table_list) {
        return gen2ImportService.importGenTable(table_list);
    }

}
