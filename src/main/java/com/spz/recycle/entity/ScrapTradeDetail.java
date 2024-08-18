package com.spz.recycle.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScrapTradeDetail {
    private Integer id; //回收品交易表明细唯一标识
    private Integer userId; //用户id
    private Integer scrapId; //回收品id
    private Integer scrapTradeId; //回收品交易表id
    private Integer status; //回收品交易状态 0: 上传 1: 接单 2: 确认 3: 完成 4: 取消
    private Integer count; //单品记录数,统计量
    private Integer price; //总价
    private LocalDateTime updateTime; //更新时间
    private LocalDateTime createTime; //创建时间
}
