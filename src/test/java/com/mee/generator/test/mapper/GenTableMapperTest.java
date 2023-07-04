package com.mee.generator.test.mapper;

import com.mee.generator.entity.GenTable;
import com.mee.generator.mapper.GenTableMapper;
import com.mee.generator.util.JacksonUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class GenTableMapperTest {

    @Autowired
    private GenTableMapper genTableMapper;


    @Test
    public void test01(){
        String[] tablenames = {"sys_dict"};
        List<GenTable> genTables = genTableMapper.selectDbTableListByNames(tablenames);
        System.out.println(JacksonUtil.toJsonString(genTables));
    }



}
