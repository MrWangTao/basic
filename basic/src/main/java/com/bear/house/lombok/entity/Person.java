package com.bear.house.lombok.entity;

import lombok.Builder;
import lombok.Data;

/**
 * @see 'http://jnb.ociweb.com/jnb/jnbJan2010.html'
 * @Data 生成get、set方法，构造函数（包含@NonNull和final字段）， 还有继承自Object的equal、hashCode、canEqual、toString方法
 * @Builder 生成builder()方法和全参构造， @Data产生的构造函数丢失
 *          toBuilder=true, 标识我需要生成一个tobuilder()方法，便于使用'.'调用属性方法
 *          注意：
 *              同一个对象使用toBuilder(), 不使用对象接收toBuilder().xxx().builder(),获取不到该数据
 * @author WangTao
 *         Created at 18/6/27 下午2:17.
 */
@Data
@Builder(toBuilder = true)
public class Person {
    private String name;
    private int age;
    private final String address;
    private final String school;
}
