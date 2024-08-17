package com.spz.communication.entity.message.dto;

import com.spz.communication.entity.message.MessageTrade;
import lombok.Data;

@Data
public class MessageTradeDto extends MessageTrade {
    private String number;
    private Integer price;
}
