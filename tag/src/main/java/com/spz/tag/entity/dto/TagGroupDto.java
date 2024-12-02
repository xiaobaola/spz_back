package com.spz.tag.entity.dto;

import com.spz.tag.entity.Tag;
import com.spz.tag.entity.TagGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagGroupDto extends TagGroup {
    private List<Tag> tagList;
}
