package com.spz.entity.dto;

import com.spz.entity.Scrap;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ScrapDto extends Scrap {
    private String scrapTypeName;
}
