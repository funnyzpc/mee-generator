package com.mee.generator.enums;

/**
 *  枚举类接口定义
 * @author shadow
 * @param <K>
 */
public interface BaseEnum<K> {

    /**
     *  key
     * @return .
     */
    K value();

    /**
     * label
     * @return .
     */
    String label();

}
