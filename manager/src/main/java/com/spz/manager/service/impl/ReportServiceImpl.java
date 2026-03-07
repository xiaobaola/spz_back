package com.spz.manager.service.impl;

import cn.hutool.core.date.DateUtil;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.spz.manager.entity.report.ReportColumn;
import com.spz.manager.entity.report.ReportData;
import com.spz.manager.entity.report.ReportType;
import com.spz.manager.service.ReportService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 报表服务实现类
 */
@Service
@Slf4j
public class ReportServiceImpl implements ReportService {
    
    // PDF 中文字体路径（需要确保系统中有该字体文件）
    private static final String CHINESE_FONT_PATH = "simsun.ttc,0"; // 宋体
    
    @Override
    public void exportExcel(HttpServletResponse response, ReportData reportData, String fileName) throws IOException {
        log.info("导出 Excel 报表，文件名：{}", fileName);
        
        // 创建工作簿
        try (Workbook workbook = new XSSFWorkbook()) {
            // 创建工作表
            Sheet sheet = workbook.createSheet("报表数据");
            
            // 设置列宽
            List<ReportColumn> columns = reportData.getColumns();
            for (int i = 0; i < columns.size(); i++) {
                ReportColumn column = columns.get(i);
                if (column.getExport()) {
                    sheet.setColumnWidth(i, column.getWidth() * 37); // POI 中宽度单位不同
                }
            }
            
            // 创建标题行
            Row titleRow = sheet.createRow(0);
            Cell titleCell = titleRow.createCell(0);
            titleCell.setCellValue(reportData.getTitle());
            titleCell.setCellStyle(createTitleStyle(workbook));
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, columns.size() - 1));
            
            // 创建列头
            Row headerRow = sheet.createRow(1);
            int colIndex = 0;
            for (ReportColumn column : columns) {
                if (column.getExport()) {
                    Cell cell = headerRow.createCell(colIndex++);
                    cell.setCellValue(column.getColumnName());
                    cell.setCellStyle(createHeaderStyle(workbook));
                }
            }
            
            // 填充数据
            int rowNum = 2;
            for (Map<String, Object> row : reportData.getRows()) {
                Row dataRow = sheet.createRow(rowNum++);
                colIndex = 0;
                for (ReportColumn column : columns) {
                    if (column.getExport()) {
                        Cell cell = dataRow.createCell(colIndex++);
                        Object value = row.get(column.getFieldName());
                        setCellValue(cell, value);
                    }
                }
            }
            
            // 设置响应头
            setupResponse(response, fileName, ReportType.EXCEL);
            
            // 输出到响应流
            try (OutputStream os = response.getOutputStream()) {
                workbook.write(os);
                os.flush();
            }
        }
    }
    
    @Override
    public void exportCsv(HttpServletResponse response, ReportData reportData, String fileName) throws IOException {
        log.info("导出 CSV 报表，文件名：{}", fileName);
        
        StringBuilder csvContent = new StringBuilder();
        
        // 添加标题
        csvContent.append(reportData.getTitle()).append("\n\n");
        
        // 添加列头
        List<ReportColumn> columns = reportData.getColumns();
        StringBuilder headerLine = new StringBuilder();
        for (int i = 0; i < columns.size(); i++) {
            ReportColumn column = columns.get(i);
            if (column.getExport()) {
                if (i > 0 && columns.get(i - 1).getExport()) {
                    headerLine.append(",");
                }
                headerLine.append(escapeCsvValue(column.getColumnName()));
            }
        }
        csvContent.append(headerLine).append("\n");
        
        // 添加数据行
        for (Map<String, Object> row : reportData.getRows()) {
            StringBuilder dataLine = new StringBuilder();
            for (int i = 0; i < columns.size(); i++) {
                ReportColumn column = columns.get(i);
                if (column.getExport()) {
                    if (i > 0 && columns.get(i - 1).getExport()) {
                        dataLine.append(",");
                    }
                    Object value = row.get(column.getFieldName());
                    dataLine.append(escapeCsvValue(value != null ? value.toString() : ""));
                }
            }
            csvContent.append(dataLine).append("\n");
        }
        
        // 添加备注
        if (reportData.getRemark() != null && !reportData.getRemark().isEmpty()) {
            csvContent.append("\n备注：").append(reportData.getRemark()).append("\n");
        }
        
        // 设置响应头
        setupResponse(response, fileName, ReportType.CSV);
        
        // 输出到响应流
        try (OutputStream os = response.getOutputStream()) {
            os.write(csvContent.toString().getBytes(StandardCharsets.UTF_8));
            os.flush();
        }
    }
    
    @Override
    public void exportPdf(HttpServletResponse response, ReportData reportData, String fileName) throws IOException {
        log.info("导出 PDF 报表，文件名：{}", fileName);
        
        Document document = new Document(PageSize.A4.rotate()); // 使用横向页面
        PdfWriter.getInstance(document, response.getOutputStream());
        
        document.open();
        
        // 添加标题
        Font titleFont = getChineseFont(18, Font.BOLD);
        Paragraph title = new Paragraph(reportData.getTitle(), titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(20);
        document.add(title);
        
        // 创建表格
        List<ReportColumn> columns = reportData.getColumns();
        long exportCount = columns.stream().filter(ReportColumn::getExport).count();
        
        PdfPTable table = new PdfPTable((int) exportCount);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10);
        
        // 设置列宽
        float[] columnWidths = new float[(int) exportCount];
        int index = 0;
        for (ReportColumn column : columns) {
            if (column.getExport()) {
                columnWidths[index++] = column.getWidth();
            }
        }
        table.setWidths(columnWidths);
        
        // 添加列头
        Font headerFont = getChineseFont(12, Font.BOLD);
        for (ReportColumn column : columns) {
            if (column.getExport()) {
                PdfPCell cell = new PdfPCell(new Phrase(column.getColumnName(), headerFont));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                table.addCell(cell);
            }
        }
        
        // 添加数据行
        Font dataFont = getChineseFont(10, Font.NORMAL);
        for (Map<String, Object> row : reportData.getRows()) {
            for (ReportColumn column : columns) {
                if (column.getExport()) {
                    Object value = row.get(column.getFieldName());
                    String valueStr = value != null ? value.toString() : "";
                    PdfPCell cell = new PdfPCell(new Phrase(valueStr, dataFont));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(cell);
                }
            }
        }
        
        document.add(table);
        
        // 添加生成时间和备注
        Font smallFont = getChineseFont(9, Font.NORMAL);
        if (reportData.getGenerateTime() != null) {
            Paragraph genTime = new Paragraph("生成时间：" + reportData.getGenerateTime(), smallFont);
            genTime.setSpacingBefore(20);
            document.add(genTime);
        }
        
        if (reportData.getRemark() != null && !reportData.getRemark().isEmpty()) {
            Paragraph remark = new Paragraph("备注：" + reportData.getRemark(), smallFont);
            remark.setSpacingBefore(5);
            document.add(remark);
        }
        
        document.close();
        
        // 设置响应头
        setupResponse(response, fileName, ReportType.PDF);
    }
    
    @Override
    public void exportReport(HttpServletResponse response, ReportData reportData, String fileName, ReportType reportType) throws IOException {
        switch (reportType) {
            case EXCEL:
                exportExcel(response, reportData, fileName);
                break;
            case CSV:
                exportCsv(response, reportData, fileName);
                break;
            case PDF:
                exportPdf(response, reportData, fileName);
                break;
            default:
                throw new IllegalArgumentException("不支持的报表类型：" + reportType);
        }
    }
    
    /**
     * 设置 HTTP 响应头
     */
    private void setupResponse(HttpServletResponse response, String fileName, ReportType reportType) throws IOException {
        response.setContentType(reportType.getContentType());
        response.setCharacterEncoding("UTF-8");
        String encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8.name()) + reportType.getExtension();
        response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + encodedFileName);
    }
    
    /**
     * 创建 Excel 标题样式
     */
    private CellStyle createTitleStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 16);
        font.setBold(true);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        return style;
    }
    
    /**
     * 创建 Excel 列头样式
     */
    private CellStyle createHeaderStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 11);
        font.setBold(true);
        style.setFont(font);
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        return style;
    }
    
    /**
     * 设置单元格值
     */
    private void setCellValue(Cell cell, Object value) {
        if (value == null) {
            cell.setCellValue("");
        } else if (value instanceof String) {
            cell.setCellValue((String) value);
        } else if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Double) {
            cell.setCellValue((Double) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else if (value instanceof LocalDateTime) {
            cell.setCellValue(DateUtil.formatDateTime((LocalDateTime) value));
        } else {
            cell.setCellValue(value.toString());
        }
    }
    
    /**
     * 转义 CSV 值
     */
    private String escapeCsvValue(String value) {
        if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
            value = value.replace("\"", "\"\"");
            value = "\"" + value + "\"";
        }
        return value;
    }
    
    /**
     * 获取中文字体
     */
    private Font getChineseFont(int size, int style) {
        try {
            BaseFont baseFont = BaseFont.createFont(CHINESE_FONT_PATH, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            return new Font(baseFont, size, style);
        } catch (Exception e) {
            log.error("加载字体失败，使用默认字体", e);
            return new Font(Font.FontFamily.UNDEFINED, size, style);
        }
    }
}
