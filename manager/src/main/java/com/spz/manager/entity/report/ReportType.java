package com.spz.manager.entity.report;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 报表类型枚举
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public enum ReportType {
    /**
     * Excel 格式 (.xlsx)
     */
    EXCEL("excel", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", ".xlsx"),
    
    /**
     * CSV 格式
     */
    CSV("csv", "text/csv", ".csv"),
    
    /**
     * PDF 格式
     */
    PDF("pdf", "application/pdf", ".pdf");
    
    private final String type;
    private final String contentType;
    private final String extension;
    
    public static ReportType fromString(String type) {
        for (ReportType rt : ReportType.values()) {
            if (rt.type.equalsIgnoreCase(type)) {
                return rt;
            }
        }
        throw new IllegalArgumentException("不支持的报表类型：" + type);
    }
}
