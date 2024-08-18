package com.spz.entity.dto;


import com.spz.entity.message.MessageTrade;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class MessageTradeDto extends MessageTrade {
    private String number;
    private Integer price;
}
