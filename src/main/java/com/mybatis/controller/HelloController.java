package com.mybatis.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.mybatis.modles.HobbyGroup;
import com.mybatis.service.IService.HobbyIService;
import com.mybatis.service.RedisService;
import com.mybatis.utils.PageBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger LOGGER= LoggerFactory.getLogger(HelloController.class);

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
        LOGGER.info("springboot自带日志");
        LOGGER.error("测试error日志");
        PageHelper.startPage(2, 4);
        List<HobbyGroup> hobbyGroupList=hobbyIService.findHobbyGroup();
        long countNums=hobbyIService.groupCount();
        PageBean<HobbyGroup> pageData=new PageBean<>(1, 2, Integer.parseInt(countNums+""));
        pageData.setItems(hobbyGroupList);
        return JSONObject.toJSONString(pageData.getItems());
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

    //分页
    @RequestMapping("/index4")
    public String index4(Integer pageNum,Integer pageSize ){
        if(null==pageSize){
            pageSize=4;
        }
        if(null==pageNum){
            pageSize=1;
        }
        PageHelper.startPage(2, 4);
        List<HobbyGroup> hobbyGroupList=hobbyIService.findHobbyGroup();
        long countNums=hobbyIService.groupCount();
        PageBean<HobbyGroup> pageData=new PageBean<>(1, 2, Integer.parseInt(countNums+""));
        pageData.setItems(hobbyGroupList);
        return JSONObject.toJSONString(pageData.getItems());
    }
}
