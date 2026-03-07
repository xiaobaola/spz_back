package com.spz.manager.service;

import cn.hutool.core.date.DateUtil;
import com.spz.manager.entity.report.ReportColumn;
import com.spz.manager.entity.report.ReportData;
import com.spz.manager.entity.report.ReportType;
import com.spz.manager.mapper.ManagerMapper;
import com.spz.manager.util.ReportBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 管理员报表服务（示例）
 */
@Service
@Slf4j
public class ManagerReportService {
    
    @Autowired
    private ManagerMapper managerMapper;
    
    @Autowired
    private ReportService reportService;
    
    /**
     * 导出管理员信息报表
     * @param response HTTP 响应
     * @param type 报表类型 (excel/csv/pdf)
     * @param name 姓名（可选筛选条件）
     * @param begin 开始日期（可选筛选条件）
     * @param end 结束日期（可选筛选条件）
     * @throws IOException IO 异常
     */
    public void exportManagerReport(HttpServletResponse response, String type, 
                                    String name, LocalDate begin, LocalDate end) throws IOException {
        log.info("导出管理员报表，type={}, name={}, begin={}, end={}", type, name, begin, end);
        
        // 查询数据（这里复用已有的查询方法）
        List<Map<String, Object>> rows = new ArrayList<>();
        List<com.spz.manager.entity.Manager> managerList = managerMapper.selectList(name, begin, end);
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        for (com.spz.manager.entity.Manager manager : managerList) {
            Map<String, Object> row = new HashMap<>();
            row.put("id", manager.getId());
            row.put("name", manager.getName() != null ? manager.getName() : "");
            row.put("username", manager.getUsername() != null ? manager.getUsername() : "");
            row.put("phone", manager.getPhone() != null ? manager.getPhone() : "");
            row.put("authority", getAuthorityText(manager.getAuthority()));
            row.put("createTime", manager.getCreateTime() != null ? 
                    manager.getCreateTime().format(formatter) : "");
            row.put("updateTime", manager.getUpdateTime() != null ? 
                    manager.getUpdateTime().format(formatter) : "");
            rows.add(row);
        }
        
        // 使用构建器创建报表
        ReportData reportData = ReportBuilder.create()
                .setTitle("管理员信息报表")
                .addColumn("ID", "id", 50)
                .addColumn("姓名", "name", 100)
                .addColumn("用户名", "username", 120)
                .addColumn("手机号", "phone", 130)
                .addColumn("权限等级", "authority", 100)
                .addColumn("创建时间", "createTime", 180)
                .addColumn("更新时间", "updateTime", 180)
                .addRows(rows)
                .setRemark("数据统计时间：" + LocalDateTime.now().format(formatter))
                .build();
        
        // 导出报表
        ReportType reportType = ReportType.fromString(type);
        String fileName = "管理员报表_" + DateUtil.format(LocalDateTime.now(), "yyyyMMddHHmmss");
        reportService.exportReport(response, reportData, fileName, reportType);
    }
    
    /**
     * 获取权限等级文本
     */
    private String getAuthorityText(String authority) {
        if (authority == null) {
            return "";
        }
        switch (authority) {
            case "1":
                return "超级管理员";
            case "2":
                return "二手物品内容审核";
            case "3":
                return "二手物品价格审核";
            case "4":
                return "回收员";
            case "5":
                return "客服";
            case "6":
                return "好友申请审核";
            case "7":
                return "接单员";
            default:
                return authority;
        }
    }
}
