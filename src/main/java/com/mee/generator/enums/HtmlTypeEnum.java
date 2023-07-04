package com.mee.generator.enums;

public enum HtmlTypeEnum implements BaseEnum<String>{

    /** 文本框 */
    INPUT("input","文本框"),

    /** 文本域 */
    TEXTAREA("textarea","文本域"),

    /** 下拉框 */
    SELECT("select","下拉框"),

    /** 单选框 */
    RADIO("radio","单选框"),

    /** 复选框 */
    CHECKBOX("checkbox","复选框"),

    /** 日期控件 */
    DATETIME("datetime","日期控件"),

    /** 图片上传控件 */
    IMAGE_UPLOAD("imageUpload","图片上传控件"),

    /** 文件上传控件 */
    FILE_UPLOAD("fileUpload","文件上传控件"),

    /** 富文本控件 */
    EDITOR("editor","富文本控件");
    /**
     * 值
     */
    public final String value;

    /**
     * 标签
     */
    public final String label;

    HtmlTypeEnum(String value,String label) {
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
