package com.mybatis.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.mybatis.modles.HobbyGroup;
import com.mybatis.service.AsyncTaskService;
import com.mybatis.service.IService.HobbyIService;
import com.mybatis.service.RedisService;
import com.mybatis.utils.PageBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.task.TaskRejectedException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;


/**
 * Created by 51667 on 2017/12/15.
 */
@Controller
@RequestMapping("/hello")
@Api(value="/hello", tags="helloword")
public class HelloController {

    private static final Logger LOGGER= LoggerFactory.getLogger(HelloController.class);

    @Autowired
    private HobbyIService hobbyIService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    AsyncTaskService asyncTaskService;

    //集群暂时弄不了 TODO
    //@Autowired
    //private JedisCluster jedisCluster;

    @RequestMapping("/index")
    @ResponseBody
    @Cacheable(value = "hobbyCache",keyGenerator = "keyGenerator")
    @ApiOperation(value = "index",notes = "indexTest")
    public String index(@RequestParam(required =false,defaultValue ="4") Integer pageSize,@RequestParam(required = false,defaultValue = "1") Integer pageNum){
        LOGGER.info("springboot自带日志");
        LOGGER.error("测试error日志");
        PageHelper.startPage(pageNum, pageSize);
        List<HobbyGroup> hobbyGroupList=hobbyIService.findHobbyGroup();
        long countNums=hobbyIService.groupCount();
        PageBean<HobbyGroup> pageData=new PageBean<>(1, 2, Integer.parseInt(countNums+""));
        pageData.setItems(hobbyGroupList);
        return JSONObject.toJSONString(pageData.getItems());
    }

    @RequestMapping("/index2")
    @ApiOperation(value = "index2",notes = "index2Test")
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
    public String index3( Model model){
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

    @RequestMapping("/index5")
    @ResponseBody
    public String index5( Model model){
        redisTemplate.convertAndSend("phone","18888888888");
        LOGGER.info("Publisher sendes Topic...");
        return "success";//返回页面
    }

    @RequestMapping("/cacheTest")
    @ResponseBody
    public String cacheTest( Model model){
        PageHelper.startPage(2, 4);
        List<HobbyGroup> hobbyGroupList=hobbyIService.findHobbyGroup();
        long countNums=hobbyIService.groupCount();
        PageBean<HobbyGroup> pageData=new PageBean<>(1, 2, Integer.parseInt(countNums+""));
        pageData.setItems(hobbyGroupList);
        return JSONObject.toJSONString(pageData.getItems());
    }

    @RequestMapping("/path")
    @ResponseBody
    public String path(HttpServletRequest request){
        try {
            //问题存在 C:\Users\Administrator\AppData\Local\Temp\
            //根目录路径
            String rootPath = request.getRealPath("/");
            System.out.println("rootPath:"+rootPath);
            //tmp
            System.out.println("tmp:"+System.getProperty("java.io.tmpdir"));
            //
            String filePath=  request.getSession().getServletContext().getRealPath("/");
            System.out.println("filePath"+filePath);
            //项目的class目录
            String resourcePath=ResourceUtils.getURL("classpath:").getPath();
            System.out.println("resourcePath"+resourcePath);
            File file=new File(resourcePath);
            if(!file.exists()) file = new File("");
            System.out.println("file:"+file.getAbsolutePath());

            //如果上传目录为/static/images/upload/，则可以如下获取：
            File upload = new File(file.getAbsolutePath(),"static/images/upload/");
            if(!upload.exists()) upload.mkdirs();
            //在开发测试模式时，得到的地址为：{项目跟目录}/target/static/images/upload/
            //在打包成jar正式发布时，得到的地址为：{发布jar包目录}/static/images/upload/
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return "success";//返回页面
    }
    @RequestMapping("/asy")
    @ResponseBody
    public String asy(HttpServletRequest request)throws InterruptedException,ExecutionException{
        for(int i=0;i<20;i++){
            asyncTaskService.executeAsyncTask(i);
        }
        List<Future<String>> listFuture = new ArrayList<Future<String>>();// 存放所有的线程，用于获取结果
        try {
            for (int i = 1; i <= 100; i++) {
                while (true) {
                    // 线程池超过最大线程数时，会抛出TaskRejectedException，则等待1s，直到不抛出异常为止
                    Future<String> future = asyncTaskService.asyncInvokeReturnFuture(i);
                    listFuture.add(future);
                    break;
                }
            }
        } catch (TaskRejectedException e){
            System.out.println("线程池满，等待1S。");
            Thread.sleep(1000);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 获取值。get是阻塞式，等待当前线程完成才返回值
        for (Future<String> future : listFuture) {
            System.out.println(future.get());
        }
        return "ok";
    }
}
