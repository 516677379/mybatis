package com.mybatis.utils.ExcelView;

import com.mybatis.modles.HobbyGroup;
import com.mybatis.utils.ExcelViewUtil;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * Created by zyf on 2017/12/26.
 */
public class HobbyExcelView extends ExcelViewUtil {
    public void setRow(Sheet sheet, Map<String, Object> map) {
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("分类名称");
        header.getCell(0).setCellStyle(super.cellStyle);
        header.createCell(1).setCellValue("id");
        header.getCell(1).setCellStyle(super.cellStyle);
        //....

        List<HobbyGroup> hobbyGroupList=(List<HobbyGroup>)map.get("members");
        int rowCount = 1;
        for(HobbyGroup hobby:hobbyGroupList){
            Row userRow = sheet.createRow(rowCount++);
            userRow.createCell(0).setCellValue(hobby.getGroup_name());
            userRow.createCell(1).setCellValue(hobby.getGroup_id());
            //.....1 2 3 后续继续
        }
    }

    //设置样式 此处可提
    @Override
    protected void setStyle(Workbook workbook) {
        CellStyle cellStyle = workbook.createCellStyle();
        //设置字体
        Font font = workbook.createFont();
        font.setFontName("Arial");
        cellStyle.setFillForegroundColor(HSSFColor.BLUE.index);
        cellStyle.setFillPattern(FillPatternType.FINE_DOTS);
        font.setBold(true);
        font.setColor(HSSFColor.WHITE.index);
        cellStyle.setFont(font);
        super.cellStyle = cellStyle;

    }

    protected void buildExcelDocument(Map<String, Object> map,
                                      Workbook workbook,
                                      HttpServletRequest request,
                                      HttpServletResponse response) {
        try {
            String excelName = map.get("name").toString() + ".xls";
            String Agent = request.getHeader("User-Agent");
            if (null != Agent) {
                Agent = Agent.toLowerCase();
                if (Agent.indexOf("firefox") != -1) {
                    response.setHeader("content-disposition", String.format("attachment;filename*=utf-8'zh_cn'%s", URLEncoder.encode(excelName, "utf-8")));

                } else {
                    response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(excelName, "utf-8"));
                }
            }
            response.setContentType("application/ms-excel; charset=UTF-8");
            //创建一个sheet
            Sheet sheet = workbook.createSheet("分类爱好");
            sheet.setDefaultColumnWidth(30);
            this.setStyle(workbook);
            setRow(sheet, map);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
