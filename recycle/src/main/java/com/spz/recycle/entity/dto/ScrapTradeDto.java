package com.spz.recycle.entity.dto;

import com.spz.recycle.entity.ScrapTrade;
import com.spz.recycle.entity.ScrapTradeDetail;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class ScrapTradeDto extends ScrapTrade {
    private List<ScrapTradeDetail> scrapTradeDetails;
}
