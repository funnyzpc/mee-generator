package com.mee.generator.enums;

/**
 * 驼峰枚举
 *
 * @author shadow
 * @version 1.0
 * @className CamelCaseEnum
 * @date 2023/4/24 16:16
 */
public enum CamelCaseEnum {

    /**
     * 非驼峰
     * */
    N("N","非驼峰/原生"),

    /**
     * 驼峰
     * */
    Y("Y","驼峰");

    /**
     * 值
     */
    public final String value;

    /**
     * 标签
     */
    public final String label;

    CamelCaseEnum(String value,String label) {
        this.value=value;
        this.label=label;
    }

}
