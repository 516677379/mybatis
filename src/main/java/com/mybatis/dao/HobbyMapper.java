package com.mybatis.dao;

import com.mybatis.modles.HobbyGroup;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by 51667 on 2017/12/15.
 */
//@Repository
    @Component
public interface HobbyMapper {

    @Select("select * from hobby_group;")
    List<HobbyGroup> findHobbyGroup();
}
