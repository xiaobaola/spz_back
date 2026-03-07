package com.spz.manager.entity.report;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 报表列定义
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportColumn {
    /**
     * 列名（显示在表头）
     */
    private String columnName;
    
    /**
     * 对应的数据字段名
     */
    private String fieldName;
    
    /**
     * 列宽度（用于 PDF 和 Excel）
     */
    private Integer width = 100;
    
    /**
     * 是否导出
     */
    private Boolean export = true;
}
