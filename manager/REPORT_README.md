# 报表模块使用说明

## 概述

报表模块位于 `manager` 模块中，提供管理端报表生成功能，支持导出以下格式：
- **Excel** (.xlsx) - 使用 Apache POI
- **CSV** (.csv) - 逗号分隔值文件
- **PDF** (.pdf) - 使用 iText PDF

## 目录结构

```
manager/src/main/java/com/spz/manager/
├── entity/report/
│   ├── ReportColumn.java      # 报表列定义
│   ├── ReportData.java        # 报表数据封装
│   └── ReportType.java        # 报表类型枚举
├── service/
│   ├── ReportService.java          # 报表服务接口
│   ├── ReportServiceImpl.java      # 报表服务实现
│   └── ManagerReportService.java   # 管理员报表服务（示例）
├── controller/
│   └── ReportController.java       # 报表控制器
└── util/
    └── ReportBuilder.java          # 报表构建器工具类
```

## API 接口

### 1. 导出管理员报表

**接口**: `GET /spz/manager/report/export/managers`

**参数**:
- `type`: 报表类型 (excel/csv/pdf)，默认 excel
- `name`: 姓名（可选筛选）
- `begin`: 开始日期 yyyy-MM-dd（可选筛选）
- `end`: 结束日期 yyyy-MM-dd（可选筛选）

**示例**:
```
GET /spz/manager/report/export/managers?type=excel&name=张三&begin=2024-01-01&end=2024-12-31
```

### 2. 通用报表导出

**接口**: `POST /spz/manager/report/export`

**请求体**:
```json
{
  "type": "excel",
  "fileName": "自定义文件名",
  "reportData": {
    "title": "报表标题",
    "columns": [
      {"columnName": "列名", "fieldName": "字段名", "width": 100, "export": true}
    ],
    "rows": [
      {"字段名 1": "值 1", "字段名 2": "值 2"}
    ],
    "remark": "备注信息"
  }
}
```

## 使用示例

### 示例 1: 使用 ReportBuilder 快速创建报表

```java
@Autowired
private ReportService reportService;

public void exportCustomReport(HttpServletResponse response) throws IOException {
    // 准备数据
    List<Map<String, Object>> rows = new ArrayList<>();
    Map<String, Object> row = new HashMap<>();
    row.put("id", 1);
    row.put("name", "张三");
    row.put("age", 25);
    rows.add(row);
    
    // 使用构建器创建报表
    ReportData reportData = ReportBuilder.create()
            .setTitle("用户信息报表")
            .addColumn("ID", "id", 50)
            .addColumn("姓名", "name", 100)
            .addColumn("年龄", "age", 80)
            .addRows(rows)
            .setRemark("测试报表")
            .build();
    
    // 导出 Excel
    reportService.exportExcel(response, reportData, "用户报表");
}
```

### 示例 2: 创建自定义报表服务

```java
@Service
@Slf4j
public class CustomReportService {
    
    @Autowired
    private ReportService reportService;
    
    @Autowired
    private YourMapper yourMapper;
    
    public void exportYourReport(HttpServletResponse response, String type) throws IOException {
        // 1. 查询数据
        List<YourEntity> dataList = yourMapper.selectList();
        
        // 2. 转换为报表行
        List<Map<String, Object>> rows = new ArrayList<>();
        for (YourEntity entity : dataList) {
            Map<String, Object> row = new HashMap<>();
            row.put("field1", entity.getField1());
            row.put("field2", entity.getField2());
            rows.add(row);
        }
        
        // 3. 构建报表
        ReportData reportData = ReportBuilder.create()
                .setTitle("您的业务报表")
                .addColumn("字段 1", "field1", 120)
                .addColumn("字段 2", "field2", 150)
                .addRows(rows)
                .build();
        
        // 4. 导出
        ReportType reportType = ReportType.fromString(type);
        reportService.exportReport(response, reportData, "您的报表", reportType);
    }
}
```

## 扩展到其他业务模块

### 在 manager 模块中添加新报表

1. **创建报表服务类**（参考 `ManagerReportService.java`）
2. **在 Controller 中添加接口**（参考 `ReportController.java`）
3. **调用统一的 ReportService 进行导出**

### 为其他模块添加报表功能

如果需要为其他模块（如 recycle、second_hand 等）添加报表功能：

1. **在对应模块的 pom.xml 中添加依赖**（manager 模块已添加）
2. **复制报表相关类到对应模块**，或
3. **将报表模块独立为公共模块**供所有模块引用

## 注意事项

### 1. PDF 中文字体

PDF 导出需要系统中有宋体字体文件（simsun.ttc），通常位于：
- Windows: `C:\Windows\Fonts\simsun.ttc`
- Linux: 需要安装中文字体或使用其他方式配置

如果遇到字体问题，可以修改 `ReportServiceImpl.java` 中的 `CHINESE_FONT_PATH` 常量。

### 2. Excel 样式

当前 Excel 导出包含以下样式：
- 标题：16 号加粗字体，居中
- 列头：11 号加粗字体，灰色背景，边框
- 数据行：自动识别数据类型（字符串、数字、日期等）

### 3. CSV 编码

CSV 文件使用 UTF-8 编码，包含 BOM 头，可以直接用 Excel 打开而不会乱码。

### 4. 大数据量导出

对于大数据量导出（超过 10000 条），建议：
- 使用分页查询
- 考虑使用 SXSSFWorkbook 代替 XSSFWorkbook（POI 的大数据解决方案）
- 设置合理的超时时间

## 依赖说明

### Maven 依赖（已在 manager/pom.xml 中添加）

```xml
<!-- Apache POI for Excel -->
<dependency>
    <groupId>org.apache.poi</groupId>
    <artifactId>poi-ooxml</artifactId>
    <version>5.2.3</version>
</dependency>

<!-- iText for PDF -->
<dependency>
    <groupId>com.itextpdf</groupId>
    <artifactId>itext7-core</artifactId>
    <version>7.2.5</version>
    <type>pom</type>
</dependency>

<!-- iText PDF 中文支持 -->
<dependency>
    <groupId>com.itextpdf</groupId>
    <artifactId>itext-asian</artifactId>
    <version>5.2.0</version>
</dependency>
```

## 常见问题

### Q1: 导出的文件名乱码
A: 已使用 URLEncoder 处理，确保浏览器正确解析文件名

### Q2: PDF 中文显示为方框
A: 需要确保系统中有中文字体文件，或修改字体路径配置

### Q3: Excel 导出内存溢出
A: 大数据量时考虑使用分页或流式 API

### Q4: 如何添加新的报表类型
A: 在 `ReportType` 枚举中添加新类型，并在 `ReportServiceImpl` 中实现对应的导出逻辑

## 后续优化建议

1. **模板化**: 支持自定义报表模板
2. **异步导出**: 大数据量时使用异步任务
3. **缓存**: 对频繁生成的报表进行缓存
4. **权限控制**: 根据管理员权限限制可导出的报表
5. **审计日志**: 记录报表导出操作日志
