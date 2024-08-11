package com.spz.entity.wrapper;

import lombok.Data;

@Data
public class SecondHandWrapper {
    private int userId;
    private int itemId;
    private int tradeId;
    private int buyerId;
    private int sellerId;
}
