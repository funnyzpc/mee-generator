package com.mee.generator.test.mapper;

import com.mee.generator.core.dao.DB1SQLDao;

import com.mee.generator.util.JacksonUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

/**
 * Gen2TableMapperTest
 *
 * @author shadow
 * @version 1.0
 * @className Gen2TableMapperTest
 * @date 2024/3/13 9:21
 */
@ActiveProfiles("oracle")
@SpringBootTest
public class Gen2TableMapperTest {


    @Autowired
    private DB1SQLDao db1SQLDao;


    @Test
    public void test01(){
        List result = db1SQLDao.query("com.mee.generator.mapper.Gen2TableMapper.findList");
        System.out.println(JacksonUtil.toJsonString(result));
    }


}
