//package com.mee.generator.test.mapper;
//
//import com.github.pagehelper.Page;
//import com.github.pagehelper.PageHelper;
//import com.github.pagehelper.PageInfo;
//import com.mee.generator.core.dao.DB1SQLDao;
//import com.mee.generator.entity.GenTableColumn;
//import com.mee.generator.mapper.GenTableColumnMapper;
//import com.mee.generator.util.JacksonUtil;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@SpringBootTest
//public class GenTableColumnMapperTest {
//    @Autowired
//    private GenTableColumnMapper genTableColumnMapper;
//
//    @Autowired
//    private DB1SQLDao db1SQLDao;
//
//    @Test
//    public void test01(){
//        // PageHelper.startPage(2,4);
//        Map<String,Object> param =new HashMap<>(2,1);
//        param.put("table_id",14);
//        param.put("page",PageHelper.startPage(3,5));
//        List<Map> list = genTableColumnMapper.findList(param);
//        System.out.println(JacksonUtil.toJsonString(list));
//        PageHelper.startPage(1,5);
//        List<Map> list2 = db1SQLDao.query("com.mee.generator.mapper.GenTableColumnMapper.findList",param);
//        System.out.println(JacksonUtil.toJsonString(list2));
//    }
//
//    @Test
//    public void test011(){
//        Map<String,Object> param =new HashMap<>(2,1);
//        param.put("table_id",14);
//        {
//            Page<Object> objects = PageHelper.startPage(2, 5);
//            // objects.toPageInfo();
//            PageInfo<Map> info1 = PageInfo.of(genTableColumnMapper.findList(param));
//            System.out.println(JacksonUtil.toJsonString(info1));
//        }
//        {
//            PageHelper.startPage(1, 5);
//            PageInfo<Map> info2 = PageInfo.of(db1SQLDao.query("com.mee.generator.mapper.GenTableColumnMapper.findList", param));
//            System.out.println(JacksonUtil.toJsonString(info2));
//        }
//    }
//
//
//    @Test
//    public void test02(){
//        {
//            Map<String, Object> param = new HashMap<>(2, 1);
//            param.put("table_id", 14);
//            param.put("page", PageHelper.startPage(1, 4));
//            PageInfo page_info = PageInfo.of(genTableColumnMapper.findList2(param));
//            System.out.println(JacksonUtil.toJsonString(page_info));
//        }
//        {
//            Map<String,Object> param =new HashMap<>(2,1);
//            param.put("table_id",14);
//            param.put("page",PageHelper.startPage(2,4));
//            PageInfo page_info = PageInfo.of(genTableColumnMapper.findList2(param));
//            System.out.println(JacksonUtil.toJsonString(page_info));
//        }
//    }
//
//    @Test
//    public void test04(){
//        List<GenTableColumn> result = genTableColumnMapper.selectDbTableColumnsByName("sys_menu");
//        System.out.println(JacksonUtil.toJsonString(result));
//        System.out.println(result.size()+"条数据");
//    }
//
//    @Test
//    public void test05(){
//        // PageHelper.startPage(2,4);
//        Map<String,Object> param =new HashMap<>(2,1);
//        param.put("column_id","221102153705001022");
//        param.put("page",PageHelper.startPage(3,5));
//
//        GenTableColumn result = db1SQLDao.queryOne("com.mee.generator.mapper.GenTableColumnMapper.findList",param);
////        System.out.println(JacksonUtil.toJsonString(list2));
//        System.out.println(result.toString());
//    }
//
//}
