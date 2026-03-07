package com.spz.manager.entity.report;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * 报表数据封装类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportData {
    /**
     * 报表标题
     */
    private String title;
    
    /**
     * 列定义
     */
    private List<ReportColumn> columns;
    
    /**
     * 数据行（每行是一个 Map，key 为 fieldName，value 为单元格值）
     */
    private List<Map<String, Object>> rows;
    
    /**
     * 报表生成时间
     */
    private String generateTime;
    
    /**
     * 备注信息
     */
    private String remark;
}
