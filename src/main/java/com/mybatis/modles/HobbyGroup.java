package com.mybatis.modles;


import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 1、使用@Entity进行实体类的持久化操作，当JPA检测到我们的实体类当中有
 * @Entity 注解的时候，会在数据库中生成对应的表结构信息。
 * 2.如何指定主键以及主键的生成策略？
 * 使用@Id指定主键.
 */
@Entity
@Table(name = "hobby_group")
@Data
public class HobbyGroup implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long group_id;

    @Column(name = "group_name")
    private String group_name;

    private Long f_id;

    private Integer is_online;


}