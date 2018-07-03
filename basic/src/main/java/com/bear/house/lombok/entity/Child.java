package com.bear.house.lombok.entity;

import lombok.Builder;
import lombok.Data;

/**
 * 继承中主要是需要重写一下全参数构造
 *
 * @author WangTao
 *         Created at 18/6/20 下午4:07.
 */
@Data
public class Child extends Parent {
    private String address;

    @Builder
    public Child(String name, String address) {
        super(name);
        this.address = address;
    }
}
