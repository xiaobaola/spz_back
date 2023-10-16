package com.spz.entity.dto;

import com.spz.entity.scrap.Scrap;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class ScrapDto extends Scrap {
    private String scrapTypeName;
}
