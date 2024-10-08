package com.spz.personal.controller;

import com.spz.common.Res;
import com.spz.personal.entity.dto.SecondHandBrowseDto;
import com.spz.personal.entity.wrapper.UserItemWrapper;
import com.spz.personal.service.UserItemBrowseService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/spz/user/browse")
@RestController
@Slf4j
@Tag(name = "用户浏览物品模块")
@RequiredArgsConstructor
public class UserItemBrowseController {

    private final UserItemBrowseService browseService;

    // 后期采用page 前端要求比较高，前端要会异步，数据处理
    @GetMapping("/list")
    public Res<List<SecondHandBrowseDto>> list(@RequestParam int userId) {
        log.info("用户查询浏览物品记录，参数{}", userId);
        List<SecondHandBrowseDto> browseDtoList = browseService.listByUserId(userId);
//        log.info("用户查询浏览物品记录，结果{}", browseDtoList);
        return Res.success(browseDtoList);
    }

    // 新增浏览记录或count+1
    @PostMapping()
    public Res<String> add(@RequestBody UserItemWrapper wrapper) {
        log.info("用户新增浏览记录，参数{}", wrapper);
        // 需要userId和itemId来定位数据信息
        browseService.insertOrUpdateByUserIdAndItemId(wrapper.getUserId(), wrapper.getItemId());
        return Res.success("新增成功");
    }
}
