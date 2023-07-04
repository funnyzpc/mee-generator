package com.mee.generator.enums;


/**
 * java类型枚举
 * @author shadow
 *
 */
public enum JavaTypeEnum implements BaseEnum<String> {

    /** 字符串类型 */
    STRING("String","字符串类型"),

    /** 整型 */
    INTEGER("Integer","整型"),

    /** 长整型 */
    LONG("Long","长整型"),

    /** 浮点型 */
    DOUBLE("Double","浮点型"),

    /** 高精度计算类型 */
    BIGDECIMAL("BigDecimal","高精度计算类型"),

    /** 时间类型 */
    @Deprecated
    DATE("Date","时间类型"),
    /** 日期类型(java8) **/
    LOCALDATE("LocalDate","日期类型(java8)"),
    /** 日期时间类型(java8) **/
    LOCALDATETIME("LocalDateTime","日期时间类型(java8)"),
    /** boolean类型 **/
    BOOLEAN("Boolean","boolean类型")
    ;

    /**
     * 值
     */
    public final String value;

    /**
     * 标签
     */
    public final String label;

    JavaTypeEnum(String value, String label) {
        this.value = value;
        this.label = label;
    }

    @Override
    public String value() {
        return this.value;
    }

    @Override
    public String label() {
        return this.label;
    }
}
