package com.spz.tag.entity.wrapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagWrapper {
    private Integer tagId;
    private String tagName;
    private Integer groupId;
    private String groupName;
}
