package com.spz.entity.wrapper;

import lombok.Data;

import java.util.List;

@Data
public class ScrapWrapper {
    private List<Integer> scrapTradeIds;
    private Integer status;
}  