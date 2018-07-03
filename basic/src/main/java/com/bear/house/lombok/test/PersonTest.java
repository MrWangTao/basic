package com.bear.house.lombok.test;

import com.bear.house.lombok.entity.Person;

/**
 * @author WangTao
 *         Created at 18/7/3 下午2:22.
 */
public class PersonTest {

    public static void main(String[] args) {
        Person test = Person.builder().name("test").build();
        /*Person build = */test.toBuilder().age(12).build();
        System.out.println("test=" + test);
    }

}
