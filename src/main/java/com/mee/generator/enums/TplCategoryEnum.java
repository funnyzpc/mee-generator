package com.mee.generator.enums;

/**
 * 使用的模板类型
 * @author shadow
 */
public enum TplCategoryEnum implements BaseEnum<String>{

    /** 单表（增删改查） */
    CRUD( "crud","单表增删改查"),

    /** 树表（增删改查） */
    TREE( "tree","树表增删改查"),

    /** 主子表（增删改查） */
    SUB("sub","主子表增删改查");

    /**
     * 值
     */
    public final String value;

    /**
     * 标签
     */
    public final String label;

    TplCategoryEnum(String value,String label) {
        this.value=value;
        this.label=label;
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
