package com.mybatis.utils;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by 51667 on 2017/12/4.
 */
public abstract class ExcelViewUtil extends AbstractXlsView {
    public CellStyle cellStyle;

    /**
     * 设置样式
     *
     * @param workbook
     */
    protected abstract void setStyle(Workbook workbook);

    /**
     * 设置Row，由子类实现
     *
     * @param sheet
     * @param map
     */
    protected abstract void setRow(Sheet sheet, Map<String, Object> map);

    protected abstract void buildExcelDocument(Map<String, Object> map,
                                      Workbook workbook,
                                      HttpServletRequest request,
                                      HttpServletResponse response) ;
}
