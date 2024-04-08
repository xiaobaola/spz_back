package com.spz.entity.communicate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class MessageTradeDto extends MessageTrade{
    private String number;
    private Integer price;
}
