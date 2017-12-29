package com.mybatis;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by zyf on 2017/12/29.
 */
public class CollectionsTest {
    @Test
    public void SetTest(){
        Set set=new HashSet<>();
        set.add("a");
        System.out.println("有a:"+set.contains("a")+" 有b:"+set.contains("b"));
    }
}
