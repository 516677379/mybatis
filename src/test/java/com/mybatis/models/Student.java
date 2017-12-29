package com.mybatis.models;

import lombok.Data;

/**
 * Created by zyf on 2017/12/29.
 */
@Data
public class Student {
    private String examid;
    private String idcard;
    private String name;
    private String location;
    private double grade;
}
