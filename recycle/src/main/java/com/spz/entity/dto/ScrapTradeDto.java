package com.spz.entity.dto;

import com.spz.entity.ScrapTrade;
import com.spz.entity.ScrapTradeDetail;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class ScrapTradeDto extends ScrapTrade {
    private List<ScrapTradeDetail> scrapTradeDetails;
}
