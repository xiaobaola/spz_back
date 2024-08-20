package com.spz.communication.entity.message;


/*

create table message_scrap_trade(
        id int primary key auto_increment comment '用户ID',
        message_trade_id int not null comment ' 交易信息ID',
        scrap_trade_id int not null comment '交易ID'
        );

*/

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "交易时间信息与用户id的类")
public class MessageScrapTrade {
    private Integer id;
    private Integer messageTradeId;
    private Integer scrapTradeId;
    private Integer status;
}
