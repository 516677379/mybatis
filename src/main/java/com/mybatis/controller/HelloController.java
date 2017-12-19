package com.mybatis.controller;

import com.alibaba.fastjson.JSONObject;
import com.mybatis.modles.HobbyGroup;
import com.mybatis.service.IService.HobbyIService;
import com.mybatis.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by 51667 on 2017/12/15.
 */
@Controller
@RequestMapping("/hello")
public class HelloController {

    @Autowired
    private HobbyIService hobbyIService;

    @Autowired
    private RedisService redisService;

    //@Autowired
    //private StringRedisTemplate stringRedisTemplate;


    //集群暂时弄不了 TODO
    //@Autowired
    //private JedisCluster jedisCluster;

    @RequestMapping("/index")
    @ResponseBody
    public String index(){
        List<HobbyGroup> hobbyGroupList=hobbyIService.findHobbyGroup();
        return JSONObject.toJSONString(hobbyGroupList);
    }

    @RequestMapping("/index2")
    public ModelAndView index2(@RequestParam(value = "hobbyGroupList",required = false)List<HobbyGroup> hobbyGroupList,Model model){
        hobbyGroupList=hobbyIService.findHobbyGroup();
        model.addAttribute("sex","男");
        ModelAndView mv=new ModelAndView();
        mv.addObject("name","tong");
        mv.addObject("hobbyGroupList",hobbyGroupList);
        mv.setViewName("hello");
        return mv;//返回页面
    }
    //@RequestMapping("/index3")
    //public String index2( Model model){
    //    stringRedisTemplate.opsForValue().set("ID","123");
    //    System.out.println(stringRedisTemplate.opsForValue().get("ID"));
    //    model.addAttribute("sex","男");
    //    return "/view/test";//返回页面
    //}

    @RequestMapping("/index3")
    public String index2( Model model){
        redisService.set("today","20171219");
        System.out.println(redisService.get("today"));
        model.addAttribute("sex","男");
        return "/view/test";//返回页面
    }
}
