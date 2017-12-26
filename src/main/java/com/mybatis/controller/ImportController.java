package com.mybatis.controller;


import com.mybatis.modles.HobbyGroup;
import com.mybatis.service.IService.HobbyIService;
import com.mybatis.utils.ExcelView.HobbyExcelView;
import com.mybatis.utils.ExcelViewUtil;
import com.mybatis.utils.ImportExcelUtil;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 51667 on 2017/12/25.
 * springboot的file.transferTo(xxx);如果xxx不是绝对路径，会将文件存放在C:\Users\xx\AppData\Local\Temp\tomcat-docbasexx\目录下
 *
 * 路径问题：
 * request.getSession().getServletContext().getRealPath("/")==request.getRealPath("/") 都为临时tomcat文件路径
 * System.getProperty("java.io.tmpdir")  为临时目录文件夹，但不含tomcat-docbasexxx..
 *
 * ResourceUtils.getURL("classpath:").getPath();项目根目录为target/classes下的目录
 * request.getServletPath() :  请求servlet路径  /fileDeal/importFile
 */
@Controller
@RequestMapping("/fileDeal")
public class ImportController {

    private static final Logger LOGGER= org.slf4j.LoggerFactory.getLogger(ImportController.class);

    @Autowired
    private HobbyIService hobbyIService;

    @RequestMapping("/fileIndex")
    public String fileIndex(){
        return "/view/fileDeal";//返回页面
    }

    @RequestMapping("/importFile")
    @ResponseBody
    public String importFile(@RequestParam(value="file") MultipartFile file, HttpServletRequest request){
        //此路径为c盘下用户tmp目录的路径
        String filePath=  request.getSession().getServletContext().getRealPath("/");
        String fileName=System.currentTimeMillis() + file.getOriginalFilename(); // 获取文件名
        System.out.println("filePath"+filePath);
        File newFile=null;
        try {
            if (!file.isEmpty()) {
                File dest = new File(filePath + fileName);
                // 检测是否存在目录
                if (!dest.getParentFile().exists()) {
                    dest.getParentFile().mkdirs();
                }
                file.transferTo(dest);
            }

            InputStream in = null;
            List<List<Object>> listob = null;

            newFile=new File(filePath,fileName);
            in=new FileInputStream(newFile);
         //   in = file.getInputStream();
            file.getContentType();
            listob = new ImportExcelUtil().getBankListByExcel(in, file.getOriginalFilename());
            for(int i=0;i<listob.size();i++){
                List<Object> lo = listob.get(i);
                String str0 = String.valueOf(lo.get(0)); //一次类推get(1/2/3...)
                System.out.println("第"+i+"行,第一个数据“"+str0);
            }
            return "ok";
        }  catch (Exception e){
            e.printStackTrace();
            LOGGER.error("导入文件异常",e);
            return "bad";
        } finally {
            if(newFile.exists()){
                newFile.delete();
            }

        }
    }
    @RequestMapping("/importFile2")
    @ResponseBody
    public String importFile2(@RequestParam(value="file") MultipartFile file, HttpServletRequest request){
        File newFile=null;
        try {
            //此路径为项目class目录
            String filePath=ResourceUtils.getURL("classpath:").getPath();
            String fileName=System.currentTimeMillis() + file.getOriginalFilename(); // 获取文件名
            System.out.println("filePath-->"+filePath);
            File dest = new File(filePath + fileName);
            file.transferTo(dest);
            newFile=new File(filePath,fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            if(newFile.exists()){
                newFile.delete();
            }

        }
        return "ok";
    }

    @RequestMapping("/export")
    public ModelAndView export(){
        Map<String, Object> map = new HashMap<String, Object>();
        List< HobbyGroup > hobbyGroupList=hobbyIService.findHobbyGroup();
        map.put("members",hobbyGroupList);
        map.put("name", "导出标题");
        ExcelViewUtil excelView = new HobbyExcelView();
        return new ModelAndView(excelView, map);

    }
}
