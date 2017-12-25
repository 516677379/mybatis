package com.mybatis.service;

import com.mybatis.dao.HobbyMapper;
import com.mybatis.modles.HobbyGroup;
import com.mybatis.service.IService.HobbyIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 51667 on 2017/12/15.
 */
@Service
public class HobbyService implements HobbyIService {

    @Autowired
    private HobbyMapper hobbyMapper;

    public List<HobbyGroup> findHobbyGroup(){
        System.out.println("hobbycache无缓存的时候调用这里");
        return hobbyMapper.findHobbyGroup();
    }

    public long groupCount(){
        System.out.println("hobbyCountCache无缓存的时候调用这里");
        return hobbyMapper.groupCount();
    }
}
