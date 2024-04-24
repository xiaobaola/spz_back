package com.spz.entity.message;

import lombok.Data;

@Data
public class MessageTradeDto extends MessageTrade{
    private String number;
    private Integer price;
}
