package com.spz.manager.service;

import com.spz.manager.entity.report.ReportData;
import com.spz.manager.entity.report.ReportType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 报表服务接口
 */
public interface ReportService {
    
    /**
     * 导出 Excel 文件
     * @param response HTTP 响应对象
     * @param reportData 报表数据
     * @param fileName 文件名（不含扩展名）
     * @throws IOException IO 异常
     */
    void exportExcel(HttpServletResponse response, ReportData reportData, String fileName) throws IOException;
    
    /**
     * 导出 CSV 文件
     * @param response HTTP 响应对象
     * @param reportData 报表数据
     * @param fileName 文件名（不含扩展名）
     * @throws IOException IO 异常
     */
    void exportCsv(HttpServletResponse response, ReportData reportData, String fileName) throws IOException;
    
    /**
     * 导出 PDF 文件
     * @param response HTTP 响应对象
     * @param reportData 报表数据
     * @param fileName 文件名（不含扩展名）
     * @throws IOException IO 异常
     */
    void exportPdf(HttpServletResponse response, ReportData reportData, String fileName) throws IOException;
    
    /**
     * 通用导出方法（根据类型选择导出格式）
     * @param response HTTP 响应对象
     * @param reportData 报表数据
     * @param fileName 文件名（不含扩展名）
     * @param reportType 报表类型
     * @throws IOException IO 异常
     */
    void exportReport(HttpServletResponse response, ReportData reportData, String fileName, ReportType reportType) throws IOException;
}
