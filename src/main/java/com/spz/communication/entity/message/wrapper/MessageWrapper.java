package com.spz.communication.entity.message.wrapper;

import lombok.Data;

import java.util.List;

@Data
public class MessageWrapper {
    private List<Integer> scrapTradeIds;
    private Integer messageTradeId;
//    private Integer integerType;
    private Integer userId;
    private Integer status;
}  