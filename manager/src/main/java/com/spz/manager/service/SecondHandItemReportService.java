package com.spz.manager.service;

import cn.hutool.core.date.DateUtil;
import com.spz.manager.entity.report.ReportData;
import com.spz.manager.entity.report.ReportType;
import com.spz.manager.util.ReportBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 二手物品报表服务（示例）
 */
@Service
@Slf4j
public class SecondHandItemReportService {
    
    @Autowired
    private ReportService reportService;
    
    /**
     * 导出二手物品信息报表
     * @param response HTTP 响应
     * @param type 报表类型 (excel/csv/pdf)
     * @throws IOException IO 异常
     */
    public void exportSecondHandItemReport(HttpServletResponse response, String type) throws IOException {
        log.info("导出二手物品报表，type={}", type);
        
        // 示例数据 - 实际应从数据库查询
        List<Map<String, Object>> rows = new ArrayList<>();
        
        Map<String, Object> row1 = new HashMap<>();
        row1.put("id", 1);
        row1.put("name", "二手手机");
        row1.put("price", 1500);
        row1.put("status", "上架");
        row1.put("createTime", LocalDateTime.now());
        rows.add(row1);
        
        Map<String, Object> row2 = new HashMap<>();
        row2.put("id", 2);
        row2.put("name", "二手电脑");
        row2.put("price", 3500);
        row2.put("status", "已售");
        row2.put("createTime", LocalDateTime.now());
        rows.add(row2);
        
        // 使用构建器创建报表
        ReportData reportData = ReportBuilder.create()
                .setTitle("二手物品信息报表")
                .addColumn("ID", "id", 50)
                .addColumn("物品名称", "name", 150)
                .addColumn("价格", "price", 80)
                .addColumn("状态", "status", 100)
                .addColumn("创建时间", "createTime", 180)
                .addRows(rows)
                .setRemark("数据统计时间：" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .build();
        
        // 导出报表
        ReportType reportType = ReportType.fromString(type);
        String fileName = "二手物品报表_" + DateUtil.format(LocalDateTime.now(), "yyyyMMddHHmmss");
        reportService.exportReport(response, reportData, fileName, reportType);
    }
}
