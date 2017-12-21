package com.mybatis.dao;

import com.mybatis.config.MyMapper;
import com.mybatis.modles.HobbyGroup;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * Created by 51667 on 2017/12/20.
 */
@Mapper
@Component
public interface HobbyDao  extends MyMapper<HobbyGroup> {
}
