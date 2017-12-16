package com.mybatis.controller;

import com.alibaba.fastjson.JSONObject;
import com.mybatis.modles.HobbyGroup;
import com.mybatis.service.IService.HobbyIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by 51667 on 2017/12/15.
 */
@RestController
@RequestMapping("/hello")
public class HelloController {

    @Autowired
    private HobbyIService hobbyIService;

    @RequestMapping("/index")
    public String index(){
        List<HobbyGroup> hobbyGroupList=hobbyIService.findHobbyGroup();
        return JSONObject.toJSONString(hobbyGroupList);
    }

    @RequestMapping("/index2")
    public String index2(@RequestParam(value = "hobbyGroupList",required = false)List<HobbyGroup> hobbyGroupList, Model model, ModelAndView modelAndView){
        hobbyGroupList=hobbyIService.findHobbyGroup();
        model.addAttribute("name","zhao");
        return "hello";//返回页面
    }
}
