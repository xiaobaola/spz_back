package com.spz.recycle.entity.dto;

import com.spz.recycle.entity.Scrap;
import lombok.Data;

@Data
public class ScrapDto extends Scrap {
    private String scrapTypeName;
}
