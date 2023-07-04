package com.mee.generator.common.web;

import com.mee.generator.common.service.IndexServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
*   主页
*   @className  IndexController
*   @author     shadow
*   @date       2023/6/18 10:32 PM
*   @version    v1.0
*/
@Controller
@RequestMapping
public class IndexController {

    @Autowired
    private IndexServiceImpl indexService;

    /**
     * 页面
     * @return
     */
    @GetMapping(value={"","index.html"})
    public String index(){
        return "index";
    }

    /**
     * 获取菜单树
     *
     * @return
     */
    @GetMapping(value = "menus",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map menus(){
        return indexService.buildMenu();
    }


}
