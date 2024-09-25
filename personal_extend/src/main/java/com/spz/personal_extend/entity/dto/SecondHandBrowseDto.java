package com.spz.personal_extend.entity.dto;

import com.spz.secondHand.entity.SecondHandItem;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SecondHandBrowseDto extends SecondHandItem {
    private int count;
}
