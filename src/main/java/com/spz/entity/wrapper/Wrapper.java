package com.spz.entity.wrapper;

import lombok.Data;

import java.util.List;

@Data
public class Wrapper {
    private List<Integer> scrapTradeIds;
    private Integer messageTradeId;

}  