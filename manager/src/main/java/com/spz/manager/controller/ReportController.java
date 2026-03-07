package com.spz.manager.controller;

import com.spz.manager.entity.report.ReportData;
import com.spz.manager.entity.report.ReportType;
import com.spz.manager.service.ManagerReportService;
import com.spz.manager.service.ReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.*;

/**
 * 报表管理控制器
 */
@RestController
@RequestMapping("/spz/manager/report")
@Slf4j
@Api(tags = "报表管理模块")
public class ReportController {
    
    @Autowired
    private ReportService reportService;
    
    @Autowired
    private ManagerReportService managerReportService;
    
    @Autowired
    private SecondHandItemReportService secondHandItemReportService;
    
    /**
     * 导出管理员列表报表（实际业务）
     */
    @GetMapping("/export/managers")
    @ApiOperation("导出管理员列表报表")
    public void exportManagersReport(
            HttpServletResponse response,
            @RequestParam(defaultValue = "excel") String type,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {
        try {
            managerReportService.exportManagerReport(response, type, name, begin, end);
        } catch (Exception e) {
            log.error("导出管理员报表失败", e);
        }
    }
    
    /**
     * 导出二手物品报表（示例）
     */
    @GetMapping("/export/second-hand-items")
    @ApiOperation("导出二手物品报表")
    public void exportSecondHandItemsReport(
            HttpServletResponse response,
            @RequestParam(defaultValue = "excel") String type) {
        try {
            secondHandItemReportService.exportSecondHandItemReport(response, type);
        } catch (Exception e) {
            log.error("导出二手物品报表失败", e);
        }
    }
    
    /**
     * 通用报表导出接口
     */
    @PostMapping("/export")
    @ApiOperation("通用报表导出")
    public void exportReport(
            HttpServletResponse response,
            @RequestBody ReportExportRequest request) {
        try {
            log.info("导出报表，类型：{}, 文件名：{}", request.getType(), request.getFileName());
            
            ReportType reportType = ReportType.fromString(request.getType());
            reportService.exportReport(response, request.getReportData(), request.getFileName(), reportType);
            
        } catch (Exception e) {
            log.error("导出报表失败", e);
        }
    }
    
    /**
     * 报表导出请求对象
     */
    @lombok.Data
    public static class ReportExportRequest {
        /**
         * 报表类型：excel, csv, pdf
         */
        private String type = "excel";
        
        /**
         * 文件名（不含扩展名）
         */
        private String fileName = "报表";
        
        /**
         * 报表数据
         */
        private ReportData reportData;
    }
}
