package com.spz.entity.secondhand;

import lombok.Data;

@Data
public class SecondHandTradeDto extends SecondHandTrade{
    private String buyerUsername;
    private String buyerImage;
    private int buyerStatus;
    private String sellerUsername;
    private String sellerImage;
    private int sellerStatus;
    private int tradeStatus; // 1:创建 2:取消 3:完成 4:删除

    @Override
    public String toString() {
        return "SecondHandTradeDto{" +
                super.toString()+
                ", buyerUsername='" + buyerUsername + '\'' +
                ", buyerImage='" + buyerImage + '\'' +
                ", buyerStatus=" + buyerStatus +
                ", sellerUsername='" + sellerUsername + '\'' +
                ", sellerImage='" + sellerImage + '\'' +
                ", sellerStatus=" + sellerStatus +
                ", tradeStatus=" + tradeStatus +
                '}';
    }
}
