package com.spz.entity.communicate;

import lombok.Data;

import java.util.List;

@Data
public class ScrapIdsWrapper {
    private List<Integer> scrapTradeIds;
    private Integer messageTradeId;
    // getters and setters  
}  