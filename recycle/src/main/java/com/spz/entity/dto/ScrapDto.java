package com.spz.entity.dto;

import com.spz.entity.scrap.Scrap;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
public class ScrapDto extends Scrap {
    private String scrapTypeName;
}
