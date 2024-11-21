package com.mee.generator.web;

import com.mee.generator.core.model.MeeResult;
import com.mee.generator.service.MeeDBHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 数据库相关操作
 *
 * @author shaoow
 * @version 1.0
 * @className MeeDBHandlerController
 * @date 2023/7/5 14:33
 */
@Controller
@RequestMapping("db_handler")
public class MeeDBHandlerController {

    @Autowired
    private MeeDBHandler meeDBHandler;

    /**
     * 主页
     * @return
     */
    @GetMapping
    public String index(Model m){
        Map<String, String> data = meeDBHandler.dbInfo();
        m.addAllAttributes(data);
        return "db/db_handler";
    }

    /**
     * 执行(ddl\dml\dql),无返回数据
     * @param sql 执行语句
     * @return .
     */
    @PostMapping("exec")
    @ResponseBody
    public MeeResult<List> exec(@RequestParam(required = true)String sql){
        return meeDBHandler.exec(sql);
    }

    /**
     * 查询(dql)，有返回数据
     * @param sql 查询语句
     * @return .
     */
    @GetMapping("query")
    @ResponseBody
    public MeeResult<List> query(@RequestParam(required = true) String sql,
                                 @RequestParam(required = true) Integer page_no,
                                 @RequestParam(required = true) Integer page_size){
        return meeDBHandler.query(sql,page_no,page_size);
    }

    /**
     * 查询(dql)，有返回数据
     * @param sql 查询语句
     * @return .
     */
    @GetMapping("export")
    @ResponseBody
    public void export(@RequestParam(required = true) String sql,
                                  @RequestParam(required = true) Integer page_no,
                                  @RequestParam(required = true) Integer page_size,
                                  HttpServletResponse response){
        meeDBHandler.export(sql,page_no,page_size,response);
    }

}
