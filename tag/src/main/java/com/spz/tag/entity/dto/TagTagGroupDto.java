package com.spz.tag.entity.dto;

import com.spz.tag.entity.TagTagGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagTagGroupDto extends TagTagGroup {
    private String tagName;
    private String tagGroupName;
}
