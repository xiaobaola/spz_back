package com.spz.entity.dto;

import com.spz.entity.scrap.ScrapTrade;
import com.spz.entity.scrap.ScrapTradeDetail;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class ScrapTradeDto extends ScrapTrade {
    private List<ScrapTradeDetail> scrapTradeDetails;
}
