package com.baizhi.zcy;

import org.junit.Test;

public class Test11 {
    @Test
    public void test1() {
        /*String s1 = new StringBuilder("go")
                .append("od").toString();
        System.out.println(s1.intern() == s1);*/

        String s2 = new StringBuilder("ja")
                .append("va").toString();
        System.out.println(s2.intern() == s2);
    }
}
