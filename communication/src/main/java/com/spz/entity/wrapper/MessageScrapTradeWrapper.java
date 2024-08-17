package com.spz.entity.wrapper;

import lombok.Data;

import java.util.List;

@Data
public class MessageScrapTradeWrapper {
    private List<Integer> scrapTradeIds;
    private Integer messageTradeId;
    private Integer userId;
    private Integer status;
}  