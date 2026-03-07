package com.spz.manager.util;

import com.spz.manager.entity.report.ReportColumn;
import com.spz.manager.entity.report.ReportData;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 报表构建器工具类
 */
public class ReportBuilder {
    
    private String title;
    private List<ReportColumn> columns = new ArrayList<>();
    private List<Map<String, Object>> rows = new ArrayList<>();
    private String remark;
    
    /**
     * 设置报表标题
     */
    public ReportBuilder setTitle(String title) {
        this.title = title;
        return this;
    }
    
    /**
     * 添加列
     */
    public ReportBuilder addColumn(String columnName, String fieldName) {
        columns.add(new ReportColumn(columnName, fieldName, 100, true));
        return this;
    }
    
    /**
     * 添加列（自定义宽度）
     */
    public ReportBuilder addColumn(String columnName, String fieldName, int width) {
        columns.add(new ReportColumn(columnName, fieldName, width, true));
        return this;
    }
    
    /**
     * 添加列（完全自定义）
     */
    public ReportBuilder addColumn(ReportColumn column) {
        columns.add(column);
        return this;
    }
    
    /**
     * 添加数据行
     */
    public ReportBuilder addRow(Map<String, Object> row) {
        rows.add(row);
        return this;
    }
    
    /**
     * 批量添加数据行
     */
    public ReportBuilder addRows(List<Map<String, Object>> rows) {
        this.rows.addAll(rows);
        return this;
    }
    
    /**
     * 设置备注
     */
    public ReportBuilder setRemark(String remark) {
        this.remark = remark;
        return this;
    }
    
    /**
     * 构建报表数据对象
     */
    public ReportData build() {
        ReportData reportData = new ReportData();
        reportData.setTitle(title);
        reportData.setColumns(columns);
        reportData.setRows(rows);
        reportData.setGenerateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        reportData.setRemark(remark);
        return reportData;
    }
    
    /**
     * 创建新的报表构建器
     */
    public static ReportBuilder create() {
        return new ReportBuilder();
    }
}
