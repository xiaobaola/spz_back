package com.spz.recycle.entity.dto;

import com.spz.recycle.entity.Scrap;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ScrapDto extends Scrap {
    private String scrapTypeName;
}
