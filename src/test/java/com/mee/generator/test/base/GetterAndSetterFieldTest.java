package com.mee.generator.test.base;

import org.junit.jupiter.api.Test;

import java.util.Locale;

/**
 * 字段生成器
 *
 * @author shadow
 * @version v1.0
 * @className GetterAndSetterFieldTest
 * @date 2023/4/12 11:17 PM
 */
public class GetterAndSetterFieldTest {

    @Test
    public void test01(){
        String field="a";
        String prefix = field.substring(0, 1).toUpperCase(Locale.ROOT);
        if( field.length()==1 ){
            System.out.println(prefix);
        }else{
            System.out.println(prefix+field.substring(1));
        }
    }
}
